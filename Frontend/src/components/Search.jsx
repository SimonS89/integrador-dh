import { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendarDays } from '@fortawesome/free-solid-svg-icons';
import { Button, Container } from 'react-bootstrap';
import { DateRange } from 'react-date-range';
import 'react-date-range/dist/styles.css'; // main css file
import 'react-date-range/dist/theme/default.css'; // theme css file
import { format } from "date-fns";
import axios from 'axios';
import { usecontextGlobal } from '../context/GlobalContext';
import { useNavigate, useLocation } from "react-router-dom";

const Search = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [searchError, setSearchError] = useState(null);
  const { productState, productDispatch } = usecontextGlobal();
  const [openDate, setOpenDate] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState('');
  const [productName, setProductName] = useState('');
  const apiUrl = 'http://52.53.159.48:8080/products/name';
  const [productos, setProductos] = useState([]);
  const [date, setDate] = useState([
    {
      startDate: new Date(),
      endDate: new Date(),
      key: 'selection'
    }
  ]);

  const handleCategoryChange = (event) => {
    setSelectedCategory(event.target.value);
  };

  const handleProductNameChange = (event) => {
    setProductName(event.target.value);
  };

  const handleSearch = () => {
    const searchTerm = productName.substring(0, 2);
    const startDate = format(date[0].startDate, 'yyyy-MM-dd');
    const endDate = format(date[0].endDate, 'yyyy-MM-dd');

    console.log('startDate:', startDate);
    console.log('endDate:', endDate);
    console.log('selectedCategory:', selectedCategory);
    console.log('productName:', productName);

    // Realizar la búsqueda si hay al menos 2 caracteres en el campo de búsqueda
    if (searchTerm.length >= 2) {
      // Limpiar el mensaje de error
      setSearchError(null);

      // Realizar la búsqueda
      axios
        .get(`${apiUrl}?search=${searchTerm}`)
        .then((response) => {
          console.log('Productos encontrados:', response.data);
          setProductos(response.data);
          productDispatch({ type: 'GET_PRODUCT', payload: response.data });

          // Redirigir solo si no estamos en la ruta 'allclothing'
          if (location.pathname !== '/allclothing') {
            navigate("/allclothing");
          }
        })
        .catch((error) => {
          console.error('Error al buscar productos:', error);
          setSearchError('Ocurrió un error al buscar productos.');
        });
    } else {
      // Mostrar mensaje de error si no hay al menos 2 caracteres
      setSearchError('Enter at least two characters.');
    }
  };

  return (
    <Container className="vh-10 d-flex align-items-center justify-content-center" style={{}}>
      <div className="headerSearch">
        {/* Input para ingresar el nombre del producto */}
        <div className="headerSearchItem">
          <input
            type="text"
            placeholder="Search product"
            value={productName}
            onChange={handleProductNameChange}
            className="headerSearchInput"
          />
          {/* Mostrar mensaje de error debajo del campo de búsqueda */}
          {searchError && (
            <p className="text-danger">{searchError}</p>
          )}
        </div>
        {/* Selector de fechas */}
        <div className="headerSearchItem">
          <FontAwesomeIcon icon={faCalendarDays} className="headerIcon" />
          {/* Muestra las fechas seleccionadas */}
          <span onClick={() => setOpenDate(!openDate)} className="headerSearchText">{`${format(date[0].startDate, "MM/dd/yyyy")} to ${format(date[0].endDate, "MM/dd/yyyy")}`}</span>
          {/* Muestra el selector de fechas si está abierto */}
          {openDate && <DateRange
            editableDateInputs={true}
            onChange={item => setDate([item.selection])}
            moveRangeOnFirstSelection={false}
            ranges={date}
            className="date"
          />}
        </div>
        {/* Selector de categoría */}
        {/* Botón para realizar la búsqueda */}
        <div className="headerSearchItem">
          <Button className='headerBtn btn-success' onClick={handleSearch}>
            {location.pathname === '/allclothing' ? 'Search' : 'Search '}
          </Button>
        </div>
      </div>
    </Container>
  );

};

export default Search;
