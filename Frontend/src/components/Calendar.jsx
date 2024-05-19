import { useState, useEffect } from 'react'
import { DateRange } from 'react-date-range'
import 'react-date-range/dist/styles.css'
import 'react-date-range/dist/theme/default.css'
import { Button, Container, Modal, Image } from 'react-bootstrap'
//import { useNavigate } from "react-router-dom"

const Calendar = ({ productId, productName, onReserve, productImages, productPrice }) => {
  const token = localStorage.getItem('token')
  const [unavailableDates, setUnavailableDates] = useState([])
  const [selectedRange, setSelectedRange] = useState({
    startDate: new Date(),
    endDate: new Date(),
    key: 'selection',
  })

  const [totalCost, setTotalCost] = useState(0)
  const productIdAsNumber = parseInt(productId, 10)
  const [showModal, setShowModal] = useState(false)
  //const navigate = useNavigate()

  const handleReserveClick = () => {

    //navigate('/reservation-confirmation')

    const startDate = selectedRange.startDate
    const endDate = selectedRange.endDate
    const durationInDays = Math.ceil((endDate - startDate) / (1000 * 60 * 60 * 24))
    const cost = productPrice * durationInDays
    setTotalCost(cost)
    onReserve(productIdAsNumber, startDate, endDate)

    // Oculta el modal después de hacer la reserva
    setSelectedRange({ startDate, endDate })
    setShowModal(false)
  }

  useEffect(() => {
    fetch(`http://52.53.159.48:8080/booking/by_product/${productIdAsNumber}`)
      .then((response) => response.json())
      .then((data) => {
        if (Array.isArray(data)) {
          // Ajustar las fechas recuperadas del servidor
          const localDates = data.map((dateRange) => ({
            startDate: new Date(dateRange.startDate + 'Z'),
            endDate: new Date(dateRange.endDate + 'Z'),
          }))

          // Añadir un día a las fechas para corregir la diferencia de zona horaria
          const correctedDates = localDates.map((dateRange) => ({
            startDate: new Date(dateRange.startDate.getTime() + 24 * 60 * 60 * 1000), // Agregar un día en milisegundos
            endDate: new Date(dateRange.endDate.getTime() + 24 * 60 * 60 * 1000), // Agregar un día en milisegundos
          }))

          setUnavailableDates(correctedDates)
        } else {
          console.error('Las fechas no disponibles no son un array válido:', data)
        }
      })
      .catch((error) => {
        console.error('Error al obtener las fechas no disponibles:', error)
      })
  }, [productIdAsNumber])

  return (
    <Container>
      <div className="d-flex align-items-center justify-content-between">
        {token && (
          <>
            <div className="calendario">
              <DateRange
                editableDateInputs={true}
                onChange={(item) => {
                  setSelectedRange(item.selection)
                  const startDate = item.selection.startDate
                  const endDate = item.selection.endDate
                  const durationInDays = Math.ceil((endDate - startDate) / (1000 * 60 * 60 * 24) + 1)
                  const cost = productPrice * durationInDays
                  setTotalCost(cost)
                }}
                moveRangeOnFirstSelection={false}
                ranges={[
                  {
                    ...selectedRange,
                  },
                  ...(Array.isArray(unavailableDates)
                    ? unavailableDates.map((dateRange) => ({
                      startDate: dateRange.startDate,
                      endDate: dateRange.endDate,
                      key: 'unavailable',
                      color: 'red',
                      selectable: false,
                    }))
                    : [])
                ]}
                className="date"
                showDateDisplay={false}
              />
            </div>
            <Button className="mr-2 btn-success" style={{ marginTop: '80px' }} onClick={() => setShowModal(true)}>Book</Button>
          </>
        )}
      </div>
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Detalles del Producto</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <h3>{productName}</h3>
          {productImages && productImages.length > 0 && (
            <Image
              src={productImages[0].url}
              alt={productName}
              fluid
              style={{ maxWidth: '100%', maxHeight: '200px' }}

            />

          )}
          <p>Fecha de inicio: {selectedRange.startDate.toDateString()}</p>
          <p>Fecha de fin: {selectedRange.endDate.toDateString()}</p>
          <p>Costo total: ${totalCost}</p>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            Cerrar
          </Button>
          <Button variant="primary" onClick={handleReserveClick}>
            Book
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  )
}

export default Calendar
