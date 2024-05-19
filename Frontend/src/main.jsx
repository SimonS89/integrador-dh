import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
// import { BrowserRouter } from 'react-router-dom'
import './styles/index.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import GlobalContext from './context/GlobalContext'
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    {/* <BrowserRouter> */}
    <GlobalContext>

                    <App/>

            </GlobalContext>
    {/* </BrowserRouter> */}
  </React.StrictMode>
)
