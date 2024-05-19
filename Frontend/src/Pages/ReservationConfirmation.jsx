import React from "react"
import { Link } from "react-router-dom"
import { Container, Row, Col } from 'react-bootstrap';
const ReservationConfirmation =()=>{

return (

    <Container fluid>
    <Row className="justify-content-center align-items-center vh-100">
        <Col xs={12} sm={8} md={6} lg={4} xl={3}>
            <div className="text-center">
                <h2>Your reservation was successful</h2>
                <p>We will send you all the details to your email.</p>
               
                <Link to="/">Ir a la página de inicio</Link>
            </div>
        </Col>
    </Row>
    </Container>


)


}
export default ReservationConfirmation