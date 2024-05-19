import { Link } from 'react-router-dom'
import { routes } from '../utils/routes'

const NavBar =()=> {
  return (
    <nav className="nav">
      <Link className='LinkNav' to={routes.clothingcategory}>All Clothing</Link>
      <Link className='LinkNav' to={routes.contact}>Contact</Link>
      <Link className='LinkNav' to={routes.aboutus}>About</Link>
    </nav>
  )
}

export default NavBar