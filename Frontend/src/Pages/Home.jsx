
import ListProducts from '../components/Listproducts'
// import NavBar from '../components/Navbar'
import Search from '../components/Search'
import header from '../assets/header.jpg'

const Home = () => {

  return (
    <>
      {/*<NavBar />*/}
      <div>
        <img src={header} style ={{maxWidth : '100%',marginTop:'100px'}} className="imgheader"/>
        
      </div>
      <Search/>
      
      <div className='HomeContainer' >
        
        <h1 className='h1home'>Discover Our Products</h1>
        <ListProducts /> 
      </div>
    </>
  )
}
export default Home