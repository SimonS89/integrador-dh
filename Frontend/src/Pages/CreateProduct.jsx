import axios from 'axios'
import { useFormik } from 'formik'
import { useEffect, useState } from 'react'
import { Dropdown } from 'react-bootstrap'
import Form from 'react-bootstrap/Form'
import * as Yup from 'yup'
import InputFile from '../components/InputFile'
import { handleInputFile } from '../utils/InputFile'
import PickImg from '../components/PickImg'

const CreateProduct = () => {

  const [categories, setCategories] = useState([])
  const [selectedCategory, setSelectedCategory] = useState(null)
  const [features, setFeatures] = useState([])
  const [selectedFeatures, setSelectedFeatures] = useState([])

  let categoryID = selectedCategory !== null ? Number(selectedCategory) : ''
  const featureIds = selectedFeatures.map((feature) => feature.id)
  const formik = useFormik({
    initialValues: {
      name: '',
      description: '',
      categoryId: '',
      price: '',
      stock: '',
      genre: '',
      featuresIds: [],
      images: [],
    },
    validateOnChange: false,
    validationSchema: Yup.object().shape({
      name: Yup.string()
        .required('Product name is required'),
      description: Yup.string()
        .required('Product description is required'),
      categoryId: Yup.number()
        .required('Product category is required'),
      price: Yup.string()
        .required('Product price is required'),
      stock: Yup.string()
        .required('Amount of stock is required'),
      genre: Yup.string()
        .required('Genre is required'),
      features: Yup.array()
        .required('Features is required'),
    }),
    onSubmit: async ({
      name,
      description,
      categoryId,
      price,
      stock,
      genre,
      featuresIds,
      images,
    }) => {
      const formdata = new FormData()
      formdata.append('name', name)
      formdata.append('description', description)
      formdata.append('categoryId', categoryId)
      formdata.append('price', price)
      formdata.append('stock', stock)
      formdata.append('genre', genre)
      formdata.append('featuresIds', featuresIds)
      formdata.append('images', images)

      try {
        const response = await axios.post('http://52.53.159.48:8080/products/create', formdata, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })

        console.log('Producto creado:', response.data)
      } catch (error) {
        console.error('Error al crear el producto:', error)
      }
    },
  })

  const getCategories = async () => {
    try {
      const response = await axios.get('http://52.53.159.48:8080/categories')
      setCategories(response.data)
    } catch (error) {
      console.error('Error al obtener los productos:', error)
    }
  }
  const getFeatures = async () => {
    try {
      const response = await axios.get('http://52.53.159.48:8080/feature')
      setFeatures(response.data)
    } catch (error) {
      console.error('Error al obtener los productos:', error)
    }
  }

  useEffect(() => {
    getCategories()
    getFeatures()
  }, [])

  useEffect(() => {
    formik.setFieldValue('categoryId', categoryID)
    formik.setFieldValue('featuresIds', featureIds)
  }, [categoryID, selectedFeatures])

  const handleDiscardChanges = () => {
    formik.resetForm()
    setSelectedFeatures([])
    setSelectedCategory(null)
    formik.setFieldValue('images', [])
  }

  const handleCategorySelect = (categoryID) => {
    setSelectedCategory(categoryID)
  }

  const handleFeatureChange = (feature) => {
    if (selectedFeatures.some((selectedFeature) => selectedFeature.id === feature.id)) {
      setSelectedFeatures(selectedFeatures.filter((selectedFeature) => selectedFeature.id !== feature.id))
    } else {
      setSelectedFeatures([...selectedFeatures, { id: feature.id }])
    }
  }

  console.log(formik.values)
  return (
    <>
      <h3 className='mt-5'>Add products</h3>
      <div className='FormCreate'>

        <Form className="w-100 my-2" onSubmit={formik.handleSubmit}>
          <Form.Group
            className="position-relative mb-4 fw-bold"
            controlId="name"
          >
            <Form.Label>Product name</Form.Label>
            <Form.Control
              type="string"
              placeholder="Enter product name"
              name="name"
              value={formik.values.name}
              onChange={formik.handleChange}
              isInvalid={formik.errors.name}
            />
            <Form.Control.Feedback
              className="position-absolute mt-0 text-5"
              type="invalid"
            >
              {formik.errors.name}
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group
            className="position-relative mb-4 fw-bold"
            controlId="description"
          >
            <Form.Label>Product description</Form.Label>
            <Form.Control
              type="string"
              placeholder="Enter product description"
              name="description"
              value={formik.values.description}
              onChange={formik.handleChange}
              isInvalid={formik.errors.description}
            />
            <Form.Control.Feedback
              className="position-absolute mt-0 text-5"
              type="invalid"
            >
              {formik.errors.description}
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group
            className="position-relative mb-4 fw-bold"
            controlId="price"
          >
            <Form.Label>Product price</Form.Label>
            <Form.Control
              type="number"
              placeholder="Enter produce price"
              name="price"
              value={formik.values.price}
              onChange={formik.handleChange}
              isInvalid={formik.errors.price}
            />
            <Form.Control.Feedback
              className="position-absolute mt-0 text-5"
              type="invalid"
            >
              {formik.errors.price}
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group
            className="position-relative mb-4 fw-bold"
            controlId="stock"
          >
            <Form.Label>Stock</Form.Label>
            <Form.Control
              type="number"
              placeholder="Enter product stock"
              name="stock"
              value={formik.values.stock}
              onChange={formik.handleChange}
              isInvalid={formik.errors.stock}
            />
            <Form.Control.Feedback
              className="position-absolute mt-0"
              type="invalid"
            >
              {formik.errors.stock}
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group
            className="position-relative mb-4 fw-bold"
            controlId="categoryId"
          >
            <Form.Label>Genre</Form.Label>
            <Form.Control
              type="string"
              placeholder="Enter product genre"
              name="genre"
              value={formik.values.genre}
              onChange={(e) => {
                const uppercaseValue = e.target.value.toUpperCase()
                formik.setFieldValue('genre', uppercaseValue)
              }}
              isInvalid={formik.errors.genre}
            />
            <Form.Control.Feedback
              className="position-absolute mt-0"
              type="invalid"
            >
              {formik.errors.genre}
            </Form.Control.Feedback>
          </Form.Group>
          <div className='d-flex align-items-center gap-4 my-4'>
            {categories && categories.length !== 0 && (
              <Dropdown onSelect={handleCategorySelect}>
                <Dropdown.Toggle variant="outlined" id="dropdown-basic">
                  Categories
                </Dropdown.Toggle>

                <Dropdown.Menu>
                  {categories.map((category) => (
                    <>
                      <Dropdown.Item key={category.id} eventKey={category.id}>{category.title}</Dropdown.Item>
                    </>
                  ))
                  }
                </Dropdown.Menu>
              </Dropdown>
            )}
            {selectedCategory && (
              <div className='d-flex align-items-center pt-3'>
                <p><span style={{ 'fontWeight': 'bold' }}>Category: </span>{categories.find(category => category.id === Number(selectedCategory))?.title}</p>
              </div>
            )}
          </div>
          <div className='d-flex flex-column gap-3'>
            {features && features.length !== 0 && (
              <>
                <Form.Group controlId='formBasicCheckbox' className='d-flex flex-column'>
                  <p style={{ 'fontWeight': 'bold' }}>Features:</p>
                  {features.map((feature) => (
                    <Form.Check
                      key={feature.id}
                      type='checkbox'
                      inline
                      label={feature.name}
                      checked={selectedFeatures.some((selectedFeature) => selectedFeature.id === feature.id)}
                      onChange={() => handleFeatureChange(feature)}
                    />

                  ))}
                </Form.Group>
              </>
            )}
          </div>

          <div className='d-flex flex-column gap-0'>

            <h6 className='my-5 pb-0'>Images</h6>
            <InputFile
              label="Upload images"
              name="images"
              multiple
              className='InputFileContainer'
              accept={[
                'image/apng',
                'image/png',
                'image/jpg',
                'image/jpeg',
                'image/webp',
                'image/gif',
              ]}
              onChange={(event) =>
                handleInputFile(
                  {
                    event,
                    fileHandler: (file) => {
                      formik.setFieldValue('images', [...formik.values.images, file])
                    },
                    fileTypes: [
                      'image/apng',
                      'image/png',
                      'image/jpg',
                      'image/jpeg',
                      'image/webp',
                      'image/gif',
                    ],
                    fileMaxSize: 15
                  })
              }
            >
              <>
                {formik.values.images.length !== 0 ? (
                  <div className='ImagesContainer'>
                    <p className="mb-1 fw-bold">{formik.values.images.length} Images selected</p>
                  </div>
                ) : (
                  <div className='EmptyImageContainer'>
                    <div className='EmptyImage'>
                      <PickImg />
                    </div>
                    <div className='TextImageContainer'>
                      <p className="mb-1 fw-bold">Añadir Imágenes</p>
                      <p className="mb-1 fw-light">
                        Dimensiones recomendadas 16:9
                      </p>
                      <p className="mb-1 fw-light">Tamaño máximo: 15 MB.</p>
                    </div>
                  </div>
                )}
              </>
              {/* {formik.values.images.length !== 0 ? (
                <>
                  {formik.values.images.map((image, index) => (
                    <div key={index} className='ImageContainer'>
                      <img
                        src={image}
                        alt={`Image ${index + 1}`}
                        className='ImageContainer'
                      />
                      <button
                        type="button"
                        className="btn-close CloseBtn"
                        aria-label={`Close Image ${index + 1}`}
                      // onClick={(e) => clearImage(e)}
                      ></button>
                    </div>
                  ))}
                </>
              ) : (
                <>
                  <div className='EmptyImageContainer'>
                    <div className='EmptyImage'>
                      <PickImg />
                    </div>
                    <div className='TextImageContainer'>
                      <p className="mb-1 fw-bold">Add Images</p>
                      <p className="mb-1 fw-light">
                        Recommended dimensions 16:9
                      </p>
                      <p className="mb-1 fw-light">Max size: 15 MB.</p>
                    </div>
                  </div>
                </>
              )} */}
            </InputFile>
          </div>
          {/* {saveState !== '' &&
          <p className='m-3'>{saveState === 'success' ? 'Changes saved successfully' : 'Something went wrong'}</p>
        } */}
          <div className="d-flex justify-content-end gap-3">
            <button
              type="button"
              className="BtnStyle btn btn-outline-secondary"
              onClick={handleDiscardChanges}
            >
              Discard changes
            </button>
            <button type="submit" className="BtnStyle btn btn-primary">
              Save changes
            </button>
          </div>
        </Form >
      </div >
    </>
  )
}
export default CreateProduct
