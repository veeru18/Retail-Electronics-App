import React from 'react'

const Button = ({text,onClick,disabled}) => {
  return (
    <button
        type='button'
        onClick={onClick}
        className='px-8 py-2 my-4 rounded-md text-white bg-blue-600 active:bg-blue-800 w-full text-xl font-semibold'
        disabled={disabled}
        >
        {text}
    </button>
  )
}
export default Button