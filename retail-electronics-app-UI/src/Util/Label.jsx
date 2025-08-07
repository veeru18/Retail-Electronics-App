import React from 'react'

const Label = ({htmlFor,label}) => {
  return (
    <label 
        className='p-1 absolute left-0 -top-6 text-gray-500 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-400
        peer-placeholder-shown:top-1 transition-all peer-focus:-top-6 peer-focus:text-gray-500 peer-focus:text-sm'
        htmlFor={htmlFor}
        >
      {label}
    </label>
  )
}

export default Label
