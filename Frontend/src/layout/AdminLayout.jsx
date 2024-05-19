import Footer from '../components/Footer'
// import AdminHeader from '../components/AdminHeader'
import { Outlet } from 'react-router-dom'
import AdminHeader from '../components/AdminHeader'

const AdminLayout = () => {
  return (
    <>
      <AdminHeader />
      <Outlet />
      <Footer />
    </>
  )
}

export default AdminLayout