import React from "react";
import CardProduct from "../components/Cardproduct";
import { usecontextGlobal } from "../context/GlobalContext";

function Favs() {
  const { productState } = usecontextGlobal();

  // Agrega un console.log para verificar el contenido de productState.productLike
  //console.log('Contenido de productState.productLike:', productState.productLike);

  return (
    <div className="fav">
      <h1 style={{ marginTop: "100px", color: "white" }}>Favorites</h1>

      <div className="two-column-container productcard text-center">
        {productState?.productLike.map((product, index) => (
          // Agrega un console.log para verificar el contenido de cada producto
          //console.log('Contenido del producto:', product),
          <CardProduct product={product} key={index} />
        ))}
      </div>
    </div>
  );
}

export default Favs;
