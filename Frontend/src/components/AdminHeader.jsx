import { Link } from 'react-router-dom'
import logo from '../assets/logo.png'

const Header = () => {


  return (
    <>
      <div className='header'>

        <Link to='/'><img src={logo} alt='logo' className='MainLogo' /></Link>

      </div>
    </>
  )
}
// {/* <Navbar />  esto va en el home*/}

export default Header
