import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { usecontextGlobal } from "../context/GlobalContext";
import { Card, Button, Image } from 'react-bootstrap';

import ShareProduct from './ShareButton';
import Corazonb from '../assets/corazonblanco.jpeg';
import Corazonr from '../assets/corazonrojo.jpeg';

const CardProduct = ({ product }) => {
  const { productState, productDispatch } = usecontextGlobal();
  const findFav = productState.productLike.find((fav) => fav.id === product.id);

  function addFav() {
    if (!findFav) {
      productDispatch({ type: 'PRODUCT_LIKE', payload: product });
    } else {
      const filteredFavs = productState.productLike.filter((fav) => fav.id !== product.id);
      productDispatch({ type: 'PRODUCT_DELETE', payload: filteredFavs });
    }
  }

  return (
    <Card style={{ width: '300px', boxShadow: '0 0 10px rgba(0, 0, 0, 0.09)' }}>
      <div className="d-flex flex-column">
        <div className="imgContainer">
          <Image src={product.images?.length > 0 ? product.images[0].url : ''} alt="" fluid />
          <div className="position-absolute top-0 end-0"> {/* Alinea los botones en la esquina superior derecha */}
            <Button onClick={addFav} style={{ backgroundColor: 'white', borderRadius: '15px',borderColor:'white', marginRight: '5px',boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)' }} >
              {
                !findFav ?
                  <Image src={Corazonb} style={{ width: '30px', height: '30px' }} />
                  : <Image src={Corazonr} style={{ width: '40px', height: '40px' }} />
              }
            </Button>
            <ShareProduct product={product} />
          </div>
        </div>
        <Link to={`/product/${product.id}`} className="Link">
          <Card.Title style={{ fontSize: '1.25rem', fontWeight: 'bold', marginBottom: '10px' }}>{product.name}</Card.Title>
          <Card.Text style={{ fontSize: '1rem' }}>{product.description}</Card.Text>
        </Link>
      </div>
    </Card>
  );
}

export default CardProduct;
