import React from 'react'

const Input = ({id,name,placeholder,value,onChange,type,maxLength}) => {
  return (
    <input id={id}
        className='p-1 peer placeholder-transparent rounded-sm h-10 w-full border-b-2 border-gray-300 text-gray-900 focus:outline-none focus:borer-rose-600'
        type={type}
        name={name} maxLength={maxLength}
        placeholder={placeholder}
        value={value}
        onChange={(event)=>{
            onChange(name,event.target.value)
        }}  
        />
  )
}

export default Input
