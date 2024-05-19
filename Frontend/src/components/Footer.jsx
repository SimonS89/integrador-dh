import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import logo from "../assets/logo.png";

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="footer mt-auto py-3">
      <Container>
        <Row>
          <Col xs={12} md={6} className="d-flex justify-content-start align-items-center">
            <div className="IsoLogo">
              <img src={logo} alt="logo" className="ImgIsoLogo" />
            </div>
          </Col>
          <Col xs={12} md={6} className="text-center text-md-right">
            <div className="d-flex justify-content-end align-items-center h-100">
              <p style={{ color: "aliceblue", margin: 0 }}>
                &copy; {currentYear} Workout Wonderland. All Rights Reserved.
              </p>
            </div>
          </Col>
        </Row>
      </Container>
    </footer>
  );
};

export default Footer;
