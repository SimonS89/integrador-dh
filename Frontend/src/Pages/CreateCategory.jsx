import { useFormik } from 'formik'
import { Form } from 'react-bootstrap'
import * as Yup from 'yup'
import axios from 'axios'

const CreateCategory = () => {

  const formik = useFormik({
    initialValues: {
      title: '',
      description: '',
      icon: '',
    },
    validateOnChange: false,
    validationSchema: Yup.object().shape({
      title: Yup.string().required('Category title is required'),
      description: Yup.string().required('Product description is required'),
      icon: Yup.string().required('Category icon is required'),
    }),
    onSubmit: async ({
      title,
      description,
      icon,

    }) => {
      const formdata = new FormData()
      formdata.append('title', title)
      formdata.append('description', description)
      formdata.append('icon', icon)
      try {
        const response = await axios.post('http://52.53.159.48:8080/categories/create', formdata, {
          headers: {
            'Content-Type': 'application/json'
          },
        })

        console.log('Producto creado:', response.data)
      } catch (error) {
        console.error('Error al crear el producto:', error)
      }
    },
  })


  const handleDiscardChanges = () => {
    formik.resetForm()
  }

  return (
    <>
      <h3 className='mt-5'>Create category</h3>
      <div className='FormCreate'>

        <Form className="w-100 my-2" onSubmit={formik.handleSubmit}>
          <Form.Group
            className="position-relative mb-4 fw-bold"
            controlId="title"
          >
            <Form.Label>Category title</Form.Label>
            <Form.Control
              type="string"
              placeholder="Enter category title"
              name="title"
              value={formik.values.title}
              onChange={formik.handleChange}
              isInvalid={formik.errors.title}
            />
            <Form.Control.Feedback
              className="position-absolute mt-0 text-5"
              type="invalid"
            >
              {formik.errors.title}
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group
            className="position-relative mb-4 fw-bold"
            controlId="description"
          >
            <Form.Label>Category description</Form.Label>
            <Form.Control
              type="string"
              placeholder="Enter category description"
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
            controlId="icon"
          >
            <Form.Label>Category icon</Form.Label>
            <div
              className="d-inline text-6 mb-3"
              style={{ 'fontWeight': 'normal' }}
            >
              <br />Example: <br /> {'<'}svg xmlns=&quot;http://www.../svg&quot;
              height=&quot;1em&quot;
              viewBox=&quot;0 0 512 512&quot;
              path d=&quot;M132. (...) 64z&quot;/ {'<'}/svg{'>'}`
            </div>
            <Form.Control
              type="string"
              placeholder="Enter icon link"
              name="icon"
              value={formik.values.icon}
              onChange={formik.handleChange}
              isInvalid={formik.errors.icon}
              className='mt-3'
            />
            <Form.Control.Feedback
              className="position-absolute mt-0 text-5"
              type="invalid"
            >
              {formik.errors.icon}
            </Form.Control.Feedback>
          </Form.Group>
          <div className='d-flex justify-content-end gap-3'>
            <button type="button" className="BtnStyle btn btn-outline-secondary" onClick={handleDiscardChanges}>Discard changes</button>
            <button type="submit" className="BtnStyle btn btn-primary" >Save changes</button>
          </div>
        </Form>
      </div >
    </>
  )
}

export default CreateCategory
