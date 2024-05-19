/* eslint-disable react/prop-types */
/* eslint-disable react-hooks/rules-of-hooks */
/* eslint-disable no-unused-vars */

import React from 'react';
import { useReducer } from 'react';
import { createContext, useContext, useEffect } from 'react';
import axios from 'axios';


const contextGlobal = createContext();

const initialProductState = {

  productLike: JSON.parse(localStorage.getItem('productLikeStorage')) || [],

  productList:[],
  
};

const productReducer = (state, action) => {
  switch (action.type) {
    // case 'GET_LIST':
    //   return { ...state, productsList: action.payload };
     case 'GET_PRODUCT':
     return { ...state, productList: action.payload };

    case 'PRODUCT_LIKE':
      return { ...state, productLike: [...state.productLike, action.payload] };

    case 'PRODUCT_DELETE':
    return { ...state, productLike: action.payload };
   
    default:
      throw new Error();
  }
};

const Context = ({ children }) => {
  const [productState, productDispatch] = useReducer(productReducer, initialProductState);

  // const urlList = 'http://localhost:8080/productos';

  // useEffect(() => {
  //   axios(urlList).then((res) => productDispatch({ type: 'GET_LIST', payload: res.data }));
  // }, [urlList]);

  useEffect(() => {
    localStorage.setItem('productLikeStorage', JSON.stringify(productState.productLike));
  }, [productState.productLike]);

  return (
    <contextGlobal.Provider value={{ productState, productDispatch }}>
      {children}
    </contextGlobal.Provider>
  );

};






export default Context;

export const usecontextGlobal = () => useContext(contextGlobal);