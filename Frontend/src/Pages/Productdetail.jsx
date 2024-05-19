import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { Row, Col, Image, Container } from 'react-bootstrap'
import BackArrow from '../assets/flechaatras.png'
import AttributeProduct from '../components/AttributeProduct'
import ImgGalery from '../components/ImgGalery'
import Policies from '../components/Policies'
import Calendar from '../components/Calendar' // Importa el componente Calendar
import { format } from 'date-fns'
import axios from 'axios'


const ProductDetail = () => {
  const params = useParams()

  const [product, setProduct] = useState([])
  const navigate = useNavigate()


  const fetchProduct = async () => {
    try {
      const response = await fetch(
        `http://52.53.159.48:8080/products/${params.id}`
      )
      const data = await response.json()
      setProduct(data)
    } catch (error) {
      console.error('Error al obtener el producto:', error)
    }
  }

  useEffect(() => {
    fetchProduct()
  }, [])

  // Función para manejar la reserva
  const handleReserve = (productId, startDate, endDate) => {
    const startDatef = format(startDate, 'yyyy-MM-dd')
    const endDatef = format(endDate, 'yyyy-MM-dd')
    // Aquí puedes realizar la lógica de reserva
    // Por ejemplo, enviar una solicitud HTTP para crear la reserva en tu servidor
    console.log('productid:', productId)
    console.log('Reserva1:', startDatef)
    console.log('Reserva2:', endDatef)
    // ...

    // Convierte productId a un número entero
    const productIdAsNumber = parseInt(productId, 10) // El segundo argumento 10 especifica la base numérica (decimal).
    const userIdAsNumber = parseInt(localStorage.getItem('userId'), 10) // Asigna un valor válido a userId
      
    const reservationData = {
      userId: userIdAsNumber,
      productId: productIdAsNumber,
      startDate: startDatef,
      endDate: endDatef
    }
    console.log('reservaaaaaaa', reservationData)

    axios
      .post('http://52.53.159.48:8080/booking/create', reservationData, {
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then((response) => {
        console.log('Respuesta del servidor:', response.data) // Agrega esta línea
        console.log('statusssss', response.status)
        if (response.status === 201) {

          // La reserva se realizó con éxito

          navigate('/reservation-confirmation')
        } else {
          // Maneja cualquier otro código de estado de respuesta aquí
          alert('Hubo un problema al realizar la reserva.')
        }
      })
      .catch((error) => {
        console.error('Error al realizar la reserva:', error)
        alert('Hubo un error al realizar la reserva. Por favor, inténtalo de nuevo más tarde.')
      })

  }


  return (
    <div className="vh-75 d-flex align-items-center justify-content-center" style={{ marginTop: '150px', marginBottom: '50px' }}>
      <Container className='bg-secondary text-white container border rounded' style={{ padding: '50px' }}>
        <div className="d-flex justify-content-between align-items-center p-4 mt-n5">
          <h1 className="text-uppercase font-weight-bold">{product.name}</h1>
          <h4 onClick={() => navigate(-1)}>
            <Image
              src={BackArrow}
              alt="back"
              style={{ width: '50px', height: '50px', cursor: 'pointer' }}
            />
          </h4>
        </div>
        <Row>
          <Col md={4}>
            <div className="description-container">
              <p>{product.description}</p>
            </div>
          </Col>
          <Col md={4}>
            <div className="big-image">
              {product && product.images && product.images.length >= 1 && (
                <ImgGalery images={product.images.slice(0, 1)} />
              )}
            </div>
          </Col>
          <Col md={4}>
            <div className="image-grid">
              {product.images &&
                product.images.length >= 2 &&
                product.images.slice(1, 5).map((image, index) => (
                  <div key={index} className="small-image">
                    <Image src={image.url} alt={`Image ${index + 1}`} fluid />
                  </div>
                ))}
            </div>
          </Col>
        </Row>
        <Row>
          <Col md={6}>
            <div className="attribute mt-4">
              <AttributeProduct
                genre={product.genre}
                features={product.features}
                className="attribute"
              />
            </div>
          </Col>
          <Col md={6}>
            <div style={{ marginTop: '280px' }}>
              <Calendar
                productId={params.id}
                onReserve={handleReserve}
                className='calendario'
                productName={product.name}
                productImages={product.images}
                productPrice={product.price}
              />
            </div>
          </Col>
        </Row>

        <div className='row' style={{ marginTop: '30px' }}>
          {product.policy ? (
            <div className="col-md-12 border border-white rounded">
              <Policies policies={product.policy} />
            </div>
          ) : (
            <div className="col">
              <p>No hay políticas disponibles para este producto.</p>
            </div>
          )}
        </div>
      </Container>
    </div>
  )
}

export default ProductDetail
