import { Form, Formik, Field, ErrorMessage } from "formik";

const AddProduct = () => {
  const initialValues = {
    productName: "",
    description: "",
  };

  const handleSubmit = (values) => {
    try {
      // Aquí podrías enviar los datos al servidor y manejar la base de datos
      console.log(values);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      <Formik initialValues={initialValues} onSubmit={handleSubmit}>
        <Form>
          <div>
            <label htmlFor="productName">Product name</label>
            <Field type="text" id="productName" name="productName" />
            <ErrorMessage name="productName" component="div" />
          </div>

          <div>
            <label htmlFor="description">Description</label>
            <Field as="textarea" id="description" name="description" />
            <ErrorMessage name="description" component="div" />
          </div>

          <button type="submit">Add product</button>
        </Form>
      </Formik>
    </div>
  );
};

export default AddProduct;
