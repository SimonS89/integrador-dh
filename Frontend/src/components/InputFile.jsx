import classNames from 'classnames'
// import PropTypes from 'prop-types'

export default function InputFile({
  className,
  children,
  name,
  accept = ['image/png', 'image/jpg', 'image/jpeg', 'image/webp'],
  error,
  multiple,
  ...props
}) {

  return (
    <div
      className='InputFile_Container'
    >
      <label
        className={classNames('InputFile', className)}
        tabIndex={0}
        {...props}
      >
        {children}
        <input
          id={name}
          name={name}
          type="file"
          multiple={multiple}
          accept={accept.reduce((acc, cur, index) => acc + (index === 0 ? cur : `, ${cur}`), '')}
          style={{ display: 'none' }}
        />
      </label>

      {error ? (
        <div
          className="Input__Error"
          style={{ height: 18, opacity: 1 }}
          dangerouslySetInnerHTML={{ __html: error }}
        />
      ) : (
        <></>
      )}
    </div>
  )
}

// InputFile.propTypes = {
//   className: PropTypes.string,
//   children: PropTypes.node,
//   name: PropTypes.string.isRequired,
//   accept: PropTypes.arrayOf(PropTypes.string),
//   error: PropTypes.string,
//   multiple: PropTypes.bool,
// }