import { Link } from "react-router-dom";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import NavDropdown from "react-bootstrap/NavDropdown";
import logo from "../assets/logo.png";
import Button from "react-bootstrap/Button"; // Importa el componente Button de Bootstrap
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars } from "@fortawesome/free-solid-svg-icons"; // Importa el ícono de barras

const Header = () => {
  const token = localStorage.getItem("token");

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    window.location.reload();
  };
  return (
    <Navbar
      expand="md"
      className="header"
      fixed="top"
      style={{ minHeight: "10px" }} // Mantener la altura constante
    >
      <div className="container-fluid">
        <Navbar.Brand>
          <Link to="/">
            <img src={logo} alt="logo" className="MainLogo img-fluid" />
          </Link>
        </Navbar.Brand>

        <Nav className="ml-auto">
          {!token && (
            <>
              <Link to="/login-user" className="nav-link d-none d-md-block">
                <Button variant="primary" className="mr-2 btn-success">
                  Log in
                </Button>
              </Link>
              <Link to="/create-user" className="nav-link d-none d-md-block">
                <Button variant="secondary">Sign up</Button>
              </Link>
            </>
          )}
          {token && (
            <>
              <Button variant="danger" className="px-3" onClick={handleLogout}>
                Log out
              </Button>
              <Link to="/favoritos" className="nav-link d-none d-md-block">
                <Button variant="secondary">Favs</Button>
              </Link>
            </>
          )}

          <NavDropdown
            title={
              <span style={{ marginLeft: "100px" }}>
                <FontAwesomeIcon
                  icon={faBars}
                  className="mr-2"
                  style={{ fontSize: "2rem", color: "white" }}
                />
              </span>
            }
            id="basic-nav-dropdown"
            alignRight
            className="d-md-none"
            style={{
              position: "absolute",
              right: "155px",
              width: "10px",
              marginTop: "-20px",
              left: "auto",
            }} // Estilo para fijar el menú en su lugar
          >
            {token && (
              <>
                <Link to="/favoritos" className="dropdown-item">
                  Favs
                </Link>
                <Link className="dropdown-item" onClick={handleLogout}></Link>
              </>
            )}
            {!token && (
              <>
                <Link to="/create-user" className="dropdown-item">
                  Sing Up
                </Link>
                <Link to="/login-user" className="dropdown-item">
                  Log in
                </Link>
              </>
            )}
            {/* Agrega más elementos del menú aquí */}
          </NavDropdown>
        </Nav>
      </div>
    </Navbar>
  );
};

export default Header;
