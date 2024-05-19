import { useState, useEffect } from 'react'
import axios from 'axios'
import Cardproduct from './Cardproduct'
import { usecontextGlobal } from '../context/GlobalContext'
import Search from './Search'
import { Row, Col, Button } from 'react-bootstrap'


const Categories = () => {
  const { productState } = usecontextGlobal()
  const [categories, setCategories] = useState([])
  const [categoryProducts, setCategoryProducts] = useState([])
  const [totalProducts, setTotalProducts] = useState(0)
  const [totalFilteredProducts, setTotalFilteredProducts] = useState(0)
  const [showCategories, setShowCategories] = useState(false)

  useEffect(() => {

    console.log('cambio productList')
    const updateSelectedCategories = categories.map(category => {
      return {
        ...category,
        selected: false
      }
    })
    console.log('categories :', updateSelectedCategories)

    setCategories(updateSelectedCategories)
    setShowCategories(false)


  }, [productState.productList])


  useEffect(() => {
    const getCategories = async () => {
      const response = await axios.get('http://52.53.159.48:8080/categories')
      const addSelectedToCategories = response.data.map((e) => ({
        ...e,
        selected: false,
      }))
      setCategories(addSelectedToCategories)
    }

    getCategories()
  }, [])

  useEffect(() => {
    const fetchProductsByCategories = async () => {
      try {
        if (categories.length > 0) {
          let categoryIds = categories
            .filter((category) => category.selected)
            .map((e) => e.id)
            .join(',')

          if (categoryIds.length === 0) {
            categoryIds = categories.map((e) => e.id).join(',')
          }

          const response = await axios.get(
            `http://52.53.159.48:8080/products/by_categories?categoriesIds=${categoryIds}`
          )
          setCategoryProducts(response.data.filteredProducts)
          setTotalProducts(response.data.totalProducts)
          setTotalFilteredProducts(response.data.totalFilteredProducts)
        }
      } catch (error) {
        console.error('Error al obtener los productos:', error)
      }
    }

    fetchProductsByCategories()
  }, [categories])

  const handleOnCheckbox = (e) => {
    const updatedCategories = categories.map((category) => {
      if (category.id == e.target.value) {
        category.selected = e.target.checked
      }
      return category
    })

    setCategories(updatedCategories)
  }

  const showCategoriesHandler = () => {
    setShowCategories(true)
  }

  return (
    <div className="container categories-container">
      <Row>
        <Col md={12}>
          <Search />
        </Col>
      </Row>
      <Row>
        <Col  >
          <h1>Categories</h1>
          <div className="checkbox-container ">
            {showCategories ? (
              categories.map((category) => (
                <div key={category.id} className="mb-3">
                  <div className="form-check">
                    <input
                      onChange={handleOnCheckbox}
                      type="checkbox"
                      className="form-check-input"
                      name="categories"
                      value={category.id}
                      id={category.id}
                      checked={category.selected}
                    />
                    <label htmlFor={category.title} className="form-check-label input-Custom">
                      {category.title}
                    </label>
                  </div>
                </div>
              ))
            ) : (
              <Button onClick={showCategoriesHandler} className="btn-success">
                Show categories
              </Button>
            )}
            {showCategories && (
              <div>
                <p className="text-muted bg-light  rounded small">
                  <span className="category-title">Quantity of products: {totalProducts}</span>

                </p>
                <p className="text-muted bg-light  rounded small">
                  <span className="category-title">Filtered Products:  {totalFilteredProducts}</span>

                </p>
              </div>
            )}
          </div>
        </Col>
        <Col md={9} className="ms-6">
          <h2 className="text-center">Products</h2>
          <div className="row">
            {showCategories
              ? categoryProducts.map((product) => (
                <div
                  className="col-md-6 mb-3 productcard text-center"
                  key={product.id}
                >
                  <Cardproduct product={product} />
                </div>
              ))
              : productState?.productList.map((product) => (
                <div
                  className="col-md-6 mb-3 productcard text-center"
                  key={product.id}
                >
                  <Cardproduct product={product} />
                </div>
              ))}
          </div>
        </Col>
      </Row>
    </div>
  )
}

export default Categories
