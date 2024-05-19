import Form from 'react-bootstrap/Form'
import * as Yup from 'yup'
import { useFormik } from 'formik'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'

const CreateUser = () => {
  const navigate = useNavigate()

  const formik = useFormik({
    initialValues: {
      name: '',
      lastname: '',
      username: '',
      password: '',
    },
    validateOnChange: false,
    validationSchema: Yup.object().shape({
      name: Yup.string()
        .required('Name is required'),
      lastname: Yup.string()
        .required('Lastname is required'),
      username: Yup.string()
        .email('Invalid email format')
        .required('Email is required'),
      password: Yup.string()
        .required('Password is required'),
    }),
    onSubmit: async (values, { resetForm }) => {
      console.log(values)
      try {
        const response = await axios.post('http://52.53.159.48:8080/user/create', values, {
          headers: {
            'Content-Type': 'application/json'
          },
        })
        resetForm()
        navigate('/login-user')
        console.log('User created', response.data)
      } catch (error) {
        console.error('Error al crear el producto:', error)
      }
    },
  })

  return (
    <>
      <section className='LoginSection'>
        <Form onSubmit={formik.handleSubmit}>
          <Form.Group controlId='name'>
            <Form.Label className='custom-label'>Name</Form.Label>
            <Form.Control
              type='string'
              name='name'
              className='my-3'
              onChange={formik.handleChange}
              value={formik.values.name}
              isInvalid={formik.errors.name}
            />
            <Form.Control.Feedback type='invalid'>
              {formik.errors.name}
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group controlId='lastname'>
            <Form.Label className='custom-label'>Lastname</Form.Label>
            <Form.Control
              type='string'
              name='lastname'
              className='my-3'
              onChange={formik.handleChange}
              value={formik.values.lastname}
              isInvalid={formik.errors.lastname}
            />
            <Form.Control.Feedback type='invalid'>
              {formik.errors.lastname}
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group controlId='username'>
            <Form.Label className='custom-label'>Email</Form.Label>
            <Form.Control
              type='email'
              name='username'
              className='my-3'
              onChange={formik.handleChange}
              value={formik.values.username}
              isInvalid={formik.errors.username}
            />
            <Form.Control.Feedback type='invalid'>
              {formik.errors.username}
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group controlId='password'>
            <Form.Label className='custom-label'>Password</Form.Label>
            <Form.Control
              type='password'
              name='password'
              className='my-3'
              onChange={formik.handleChange}
              value={formik.values.password}
              isInvalid={formik.errors.password}
            />
            <Form.Control.Feedback type='invalid'>
              {formik.errors.password}
            </Form.Control.Feedback>
          </Form.Group>
          {/* <Form.Group controlId='repeatPassword'>
            <Form.Label>Repeat Password</Form.Label>
            <Form.Control
              type='password'
              name='repeatPassword'
              className='my-3'
              onChange={(e) => {
                formik.setFieldValue('repeatPassword', e.target.value)
              }}
              value={formik.values.repeatPassword}
              isInvalid={formik.touched.repeatPassword && !!formik.errors.repeatPassword}
            />
            <Form.Control.Feedback type='invalid'>
              {formik.touched.repeatPassword && formik.errors.repeatPassword}
            </Form.Control.Feedback>
          </Form.Group> */}
          <div className='d-flex justify-content-center w-100'>
            <button type='submit' className='btn btn-primary' style={{ 'margin': '10px auto' }}>
              Create User
            </button>
          </div>
        </Form>
      </section>

    </>
  )
}

export default CreateUser