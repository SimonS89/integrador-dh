import Form from "react-bootstrap/Form";
import * as Yup from "yup";
import { useFormik } from "formik";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Button from "react-bootstrap/Button";
import { useState } from "react";

const Login = () => {
  const navigate = useNavigate();
  const [error, setError] = useState(null);

  const formik = useFormik({
    initialValues: {
      username: "",
      password: "",
    },
    validationSchema: Yup.object().shape({
      username: Yup.string().required("Username is required"),
      password: Yup.string().required("Password is required"),
    }),
    onSubmit: async (values, { resetForm }) => {
      try {
        const response = await axios.post(
          "http://52.53.159.48:8080/user/authenticate",
          values,
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        );
        console.log("User authenticated", response.data);
        console.log("User authenticated", response.data.token);
        if (response.data.error) {
          setError("Username or password are incorrect");
        } else {
          localStorage.setItem("token", response.data.token);
          localStorage.setItem("userId", response.data.id);
          resetForm();
          setError(null);
          navigate("/");
        }
      } catch (error) {
        setError("Username or password are incorrect");
        setTimeout(() => {
          setError(null);
        }, 3000);
      }
    },
  });

  return (
    <div className="LoginSection2">
      <Form onSubmit={formik.handleSubmit}>
        {error && (
          <div className="error-message" style={{ color: "red" }}>
            {error}
          </div>
        )}
        <Form.Group controlId="username">
          <Form.Label className="custom-label">Username</Form.Label>
          <Form.Control
            type="text"
            name="username"
            className="my-3"
            onChange={formik.handleChange}
            value={formik.values.username}
            isInvalid={formik.touched.username && !!formik.errors.username}
          />
          <Form.Control.Feedback type="invalid">
            {formik.errors.username}
          </Form.Control.Feedback>
        </Form.Group>

        <Form.Group controlId="password">
          <Form.Label className="custom-label">Password</Form.Label>
          <Form.Control
            type="password"
            name="password"
            className="my-3"
            onChange={formik.handleChange}
            value={formik.values.password}
            isInvalid={formik.touched.password && !!formik.errors.password}
          />
          <Form.Control.Feedback type="invalid">
            {formik.errors.password}
          </Form.Control.Feedback>
        </Form.Group>
        <div className="d-flex justify-content-center w-100">
          <Button
            type="submit"
            variant="primary"
            style={{ margin: "20px auto" }}
          >
            Login
          </Button>
        </div>
      </Form>
    </div>
  );
};

export default Login;
