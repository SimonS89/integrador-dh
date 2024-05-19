import { Routes, Route, BrowserRouter } from 'react-router-dom'
import Contact from './Pages/Contact'
import Home from './Pages/Home'
import ImgGalery from './components/ImgGalery'
import AdminPanel from './Pages/Adminpanel'
import AllClothing from './Pages/Allclothing'
import ProductDetail from './Pages/Productdetail'
import Layout from './layout/Layout'
import AdminLayout from './layout/AdminLayout'
import CreateProduct from './Pages/CreateProduct'
import AdminListarProductos from './components/AdminListarProductos'
import CreateCategory from './Pages/CreateCategory'
import CreateFeature from './Pages/CreateFeature'
import Favs from './components/Favs'
import LoginLayout from './layout/LoginLayout'
import CreateUser from './components/CreateUser'
import Login from './components/Login'
import ReservationConfirmation from './Pages/ReservationConfirmation'


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />}></Route>
          <Route path="contact" element={<Contact />}></Route>
          <Route path="allclothing" element={<AllClothing />}></Route>
          <Route path="/product/:id" element={<ProductDetail />} />
          <Route path="/product/:id/img" element={<ImgGalery />} />
          <Route path="favoritos" element={<Favs />} />
          <Route path="/reservation-confirmation" element={<ReservationConfirmation />} />

        </Route>

        <Route element={<AdminLayout />}>
          <Route path='admin' element={<AdminPanel />} >
            <Route path="admin-list" element={<AdminListarProductos />} />
            <Route path="create" element={<CreateProduct />} />
            <Route path="categories" element={<CreateCategory />} />
            <Route path="features" element={<CreateFeature />} />
            <Route path="favoritos" element={<Favs />} />
          </Route>
        </Route>

        <Route element={<LoginLayout />}>
          <Route path='login-user' element={<Login />}></Route>
          <Route path='create-user' element={<CreateUser />}></Route>
        </Route>

      </Routes>
    </BrowserRouter>
  )
}

export default App
