import { createContext, useState, useEffect } from "react";
import axios from "axios"; // Import Axios library

const ProductContext = createContext();

const ProductProvider = ({ children }) => {
  const [products, setProducts] = useState([]);

  const fetchProducts = async () => {
    try {
      const response = await axios.get("http://52.53.159.48:8080/products"); // Use Axios to make a GET request
      setProducts(response.data); // Access response data using response.data
    } catch (error) {
      console.error("Error al obtener los productos:", error);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  return (
    <ProductContext.Provider value={products}>
      {children}
    </ProductContext.Provider>
  );
};

export { ProductContext, ProductProvider };
