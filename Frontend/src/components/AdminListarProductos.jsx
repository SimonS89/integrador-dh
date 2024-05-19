import Table from 'react-bootstrap/Table'
import { useEffect, useState } from 'react'
import axios from 'axios'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPenToSquare, faTrash } from '@fortawesome/free-solid-svg-icons'
import { Button, Modal } from 'react-bootstrap'


const AdminListarProductos = () => {

  const [products, setProducts] = useState([])
  const [showDeleteModal, setShowDeleteModal] = useState(false)
  const [productIdToDelete, setProductIdToDelete] = useState(null)
  const [showSuccessModal, setShowSuccessModal] = useState(false)

  const handleCloseDeleteModal = () => setShowDeleteModal(false)
  const handleShowDeleteModal = () => setShowDeleteModal(true)
  const handleCloseSuccessModal = () => setShowSuccessModal(false)


  const fetchProducts = async () => {
    try {
      const response = await axios.get('http://52.53.159.48:8080/products')
      setProducts(response.data)
    } catch (error) {
      console.error('Error al obtener los productos:', error)
    }
  }

  useEffect(() => {
    fetchProducts()
  }, [])

  const deleteProduct = async (productId) => {
    try {
      await axios.delete(`http://52.53.159.48:8080/products/${productId}`)
      console.log(`Producto con ID ${productId} eliminado con éxito.`)
      handleCloseDeleteModal()
      fetchProducts()
      setShowSuccessModal(true)
      setTimeout(() => {
        setShowSuccessModal(false)
      }, 3000)
    } catch (error) {
      console.error(`Error al eliminar el producto con ID ${productId}:`, error)
    }
  }

  const handleDeleteButtonClick = (productId) => {
    setProductIdToDelete(productId)
    handleShowDeleteModal()
  }

  const handleConfirmDelete = () => {
    if (productIdToDelete) {
      deleteProduct(productIdToDelete)
    }
  }


  return (
    <>
      <div className="AdminContainer d-flex flex-column gap-3">
        <div className='d-flex flex-column gap-4 mx-auto' >
          <h3 className='mx-auto mt-5'>Admin / List</h3>
        </div>
        {products && products.length !== 0 ? (
          <div className='mx-auto'>
            <Table striped bordered hover variant="dark" className='customTable'>
              <thead>
                <tr>
                  <th className='customTable'>ID</th>
                  <th className='customTable'>Image</th>
                  <th className='customTable'>Name</th>
                  <th className='customTable'>Description</th>
                  <th className='customTable'>Genre</th>
                  <th className='customTable'>Category</th>
                  <th className='customTable'>Price</th>
                  <th className='customTable'>Stock</th>
                  <th className='customTable'></th>
                </tr>
              </thead>
              <tbody>
                {products.map((item) => (
                  <>
                    <tr key={item.id}>
                      <td>{item.id}</td>
                      <td>
                        {item.images.length > 0 && (
                          <img
                            src={item.images[0].url}
                            alt={`Image for ${item.name}`}
                            style={{ 'width': '100px', 'height': 'auto' }}
                          />
                        )}
                      </td>
                      <td style={{ 'maxWidth': '250px' }}>{item.name}</td>
                      <td style={{ 'maxWidth': '250px' }}>{item.description}</td>
                      <td>{item.genre}</td>
                      <td>{item.category.title}</td>
                      <td>{item.price}</td>
                      <td>{item.stock}</td>
                      <td>
                        <div className='IconsContainer'>
                          <FontAwesomeIcon icon={faTrash} onClick={() => handleDeleteButtonClick(item.id)} />
                          <FontAwesomeIcon icon={faPenToSquare} />
                        </div>
                      </td>
                    </tr>
                  </>
                ))}
              </tbody>
            </Table>
          </div>
        ) : (
          <div className='d-flex w-100 justify-content-center' style={{ 'minWidth': '300px' }}>
            <h3>Loading ... </h3>
          </div>
        )}
      </div>
      <Modal show={showDeleteModal} onHide={handleCloseDeleteModal}>
        <Modal.Header closeButton>
          <Modal.Title>Are you sure you want to delete the product?</Modal.Title>
        </Modal.Header>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseDeleteModal}>
            No
          </Button>
          <Button variant="danger" onClick={handleConfirmDelete}>
            Yes
          </Button>
        </Modal.Footer>
      </Modal>
      <Modal
        size="sm"
        show={showSuccessModal}
        onHide={handleCloseSuccessModal}
        aria-labelledby="contained-modal-title-vcenter"
      >
        <Modal.Body className='ModalRemoved'>Product removed successfully!</Modal.Body>
      </Modal>
    </>
  )
}

export default AdminListarProductos
