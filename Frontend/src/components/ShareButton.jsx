import { useState } from 'react'
import { Button, Modal, Form } from 'react-bootstrap'
import { FacebookShareButton, TwitterShareButton } from 'react-share'
import { FaFacebookSquare, FaTwitterSquare, FaShareAlt, FaWhatsapp } from 'react-icons/fa'

const ShareProduct = ({ product }) => {
  const [showModal, setShowModal] = useState(false)
  const [customMessage, setCustomMessage] = useState('')

  const handleClose = () => {
    setShowModal(false)
  }

  const handleShow = () => {
    setShowModal(true)
  }

  const shareUrl = `https://52.53.159.48:8080/product/${product.id}`
  const title = product.name
  const description = product.description
  const imageUrl = product.images[0].url

  const handleCustomMessageChange = (e) => {
    setCustomMessage(e.target.value)
  }

  const handleShare = () => {
    // Aquí puedes utilizar el contenido personalizado de customMessage
    // junto con shareUrl, title, description, etc., para compartir en redes sociales
    // Puedes usar librerías de terceros o APIs de redes sociales para llevar a cabo la acción de compartir.
    // Por ejemplo, puedes usar la API de Facebook, Twitter o WhatsApp para compartir contenido.

    // Una vez que hayas implementado la lógica de compartir, cierra el modal.
    handleClose()
  }

  const handleWhatsAppShare = () => {
    // URL para compartir en WhatsApp
    const whatsappUrl = `https://api.whatsapp.com/send?text=${encodeURIComponent(
      `${customMessage}\n${shareUrl}`
    )}`

    // Abre una nueva ventana del navegador con la URL de WhatsApp
    window.open(whatsappUrl, '_blank')
  }

  return (
    <>
      <Button variant="primary" onClick={handleShow} className="btn-success">
        <FaShareAlt /> {/* Ícono de Font Awesome */}
      </Button>

      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Share Product</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div className="text-center">
            <p>{title}</p>
            <img
              src={imageUrl}
              alt={title}
              style={{ maxWidth: '100%', maxHeight: '300px' }}
            />
          </div>
          <p>{description}</p>
          <p>Link to Product : {shareUrl}</p>
          <Form.Group>
            <Form.Label>Personalized message</Form.Label>
            <Form.Control
              as='textarea'
              rows={3}
              value={customMessage}
              onChange={handleCustomMessageChange}
              placeholder='Write a personalized message...'
            />
          </Form.Group>
          <h5>Share in :</h5>
          <div className='text-center'>
            <FacebookShareButton url={shareUrl} quote={customMessage}>
              <FaFacebookSquare className='fa-3x mx-3' />
            </FacebookShareButton>
            <TwitterShareButton url={shareUrl} title={customMessage}>
              <FaTwitterSquare className='fa-3x mx-3' />
            </TwitterShareButton>
            <Button variant="grey" onClick={handleWhatsAppShare}>
              <FaWhatsapp className="fa-3x mx-3" />
            </Button>
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant='secondary' onClick={handleClose}>
            Close
          </Button>
          <Button variant='primary' onClick={handleShare}>
            Share
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  )
}

export default ShareProduct
