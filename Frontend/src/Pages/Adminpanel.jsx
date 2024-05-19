import { Link, Outlet } from 'react-router-dom'

const AdminPanel = () => {


  return (
    <>
      <div className="Container">
        <div className="TituloAdmin">
          <h1>Admin panel</h1>
        </div>
        <section className="SectionCard">

          <div className="AdminCardsContainer">
            <Link to="admin-list" className="AdminCard" style={{ textDecoration: 'none', color: 'black' }}>
              <h6>List products</h6>
            </Link>
            <Link to="create" className="AdminCard" style={{ textDecoration: 'none', color: 'black' }}>
              <h6>Add products</h6>
            </Link>
            <Link to="categories" className="AdminCard" style={{ textDecoration: 'none', color: 'black' }}>
              <h6>Manage categories</h6>
            </Link>
            <Link to="features" className="AdminCard" style={{ textDecoration: 'none', color: 'black' }}>
              <h6>Manage features</h6>
            </Link>
          </div>
          <Outlet />
        </section>
      </div>

    </>
  )
}

export default AdminPanel