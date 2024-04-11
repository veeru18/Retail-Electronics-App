import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import logo from '../Public/Resources/Electron_Logo.jpg'
import user from '../Public/Resources/userlogo.png'
import search from '../Public/Resources/search.png'

function Header() {
  let [isOpen,setIsOpen]=useState(false);
  let list=["SignUp"]

  return (
    <div className='flex text-lg font-light border-b-2 rounded-sm' >
      <Link className='flex-wrap ml-20 p-2' to={"/"}>
        <img className='rounded-lg' src={logo} style={{height:'50px',width:'100px'}} alt="logo"/>
      </Link>
      <div className='flex ml-8 mt-2 mb-2 bg-gray-200 rounded'>
        <img className='absolute ml-2 mt-3 w-6 h-6' src={search} alt="search" />
        <input autoFocus  style={{width:'40vw'}} className='flex-wrap rounded pl-3 p-1 ml-10 bg-gray-200' placeholder='Search for Products, Brands & more' type="text" name="search" id="searchbox" />
      </div>
      <div className=' hover:bg-blue-500 ml-7 pl-3 pt-2 flex-wrap justify-center rounded'>
        <Link to={"/login"}>
          <img className='ml-1 mt-3 w-5 h-5' src={user} alt="" />
        </Link>
        <div className='font-medium -mt-8 ml-5 p-2 mr-4' onMouseEnter={()=>{setIsOpen((prev)=>!prev)}} onMouseLeave={()=>{setIsOpen((prev)=>prev)}}>
        <Link to={"/login"}>Login</Link>
          {isOpen && ( 
            <div className=''>
            {list.map((item,i)=>(
              <div className=' font-thin absolute -ml-10 pl-7 p-2 mt-5 w-full border-b-4 rounded' onMouseLeave={()=>{setIsOpen((prev)=>!prev)}} onMouseEnter={()=>{setIsOpen((prev)=>true)}}>
                <Link className='' to={"/register"}>New Customer? </Link> 
                <Link className='pl-2 font-medium text-blue-700' to={"/register"}>{item}</Link>
              </div>
            ))}
            </div>
          )
        }
        </div>
      </div>
     </div>
  )
}

export default Header
