
export const handleInputFile = ({
  event,
  fileHandler,
  fileListMaxLength,
  fileTypes,
  fileMaxSize //MB
}) => {
  const input = event.target
  if (!input.files) return

  const fileList = Array.from(input.files)

  fileList.slice(0, fileListMaxLength).forEach((file) => {
    if (fileTypes && !fileTypes.includes(file['type'])) {
      console.log(file['type'])
      return console.log('the file type is not accepted')
    }

    if (fileMaxSize && file.size > fileMaxSize * 1024 ** 2)
      return console.log(
        `The file is very large (${Math.floor(
          file.size / 1024 ** 2
        )}MB), max size is ${fileMaxSize}MB`
      )

    fileHandler(file)
  })

  input.files = new DataTransfer().files
}

export const getImageString = (data, callback) => {
  const imageReader = new FileReader()

  imageReader.readAsDataURL(data)

  imageReader.onload = () => {
    if (imageReader.readyState == 2) {
      const imageToShow = imageReader.result?.toString()
      if (imageToShow) {
        callback(data, imageToShow)
      }
    }
  }
}

