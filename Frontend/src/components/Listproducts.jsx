import { useState, useEffect } from 'react'
import axios from 'axios'
import CardProduct from './Cardproduct'
import Pagination from 'react-bootstrap/Pagination'

const ProductsPerPage = 10 // Cantidad de productos por página

const ListProducts = () => {
  const [products, setProducts] = useState([])
  const [currentPage, setCurrentPage] = useState(1) // Página actual
  const [isLoadingProducts, setIsLoadingProducts] = useState(true) // Nuevo estado

  useEffect(() => {
    // Función para cargar los productos
    const fetchProducts = async () => {
      try {
        const response = await axios.get('http://52.53.159.48:8080/products')
        setProducts(response.data)
        setIsLoadingProducts(false) // Productos cargados, establece isLoadingProducts en false
      } catch (error) {
        console.error('Error al obtener los productos:', error)
      }
    }

    // Llama a la función para cargar los productos
    fetchProducts()
  }, [])

  // Calcula la cantidad total de páginas
  const totalPages = Math.ceil(products.length / ProductsPerPage)

  // Función para cambiar de página
  const handlePageChange = (newPage) => {
    setCurrentPage(newPage)
  }

  return (
    <div className='text-center '>
      {/* Centrar el contenido */}
      <div className='productcard  ' >
        {products
          .slice(
            (currentPage - 1) * ProductsPerPage,
            currentPage * ProductsPerPage
          )
          .map((product) => (
            <CardProduct product={product} key={product.id} />
          ))}
      </div>
      {/* Controles de paginación con React Bootstrap */}
      {!isLoadingProducts && (
        <Pagination
          className='justify-content-center pagination-container'
          style={{
            marginTop: '30px',
          }}
        >
          {' '}
          {/* Centrar la paginación */}
          <Pagination.Prev
            onClick={() => handlePageChange(currentPage - 1)}
            disabled={currentPage === 1}
            className='custom-pagination-arrow'
            style={{ backgroundColor: 'gray', color: 'white' }}
          />
          {Array.from({ length: totalPages }, (_, index) => (
            <Pagination.Item
              key={index + 1}
              active={index + 1 === currentPage}
              onClick={() => handlePageChange(index + 1)}
              style={{ backgroundColor: 'gray', color: 'white' }}
              className='custom-pagination-button'
            >
              {index + 1}
            </Pagination.Item>
          ))}
          <Pagination.Next
            onClick={() => handlePageChange(currentPage + 1)}
            disabled={currentPage === totalPages}
            className='custom-pagination-arrow'
            style={{ backgroundColor: 'gray', color: 'white' }}
          />
        </Pagination>
      )}
    </div>
  )
}

export default ListProducts
