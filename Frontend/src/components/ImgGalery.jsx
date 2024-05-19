
const ImgGalery = ({ images }) => {

  return (
    <div className='image-gallery'>

      {images && images.map((image) => (
        <img key={image.id} src={image.url} className='img-product' />
      ))}
    </div>
  )
}

export default ImgGalery