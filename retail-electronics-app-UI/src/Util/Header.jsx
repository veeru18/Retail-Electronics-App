import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import logo from '../Public/Resources/Electron_Logo.jpg'
import { MdOutlineStore } from 'react-icons/md';
import { BiSearch } from 'react-icons/bi';
import { IoCartOutline } from 'react-icons/io5';
import { LuBoxes } from 'react-icons/lu';
import { FaHeart, FaRegUserCircle } from 'react-icons/fa';
import { HiMiniBars3BottomLeft } from 'react-icons/hi2';

const Header = (props) => {

  let [isOpen, setIsOpen] = useState(false);

  const user = props?.userAuth;

  const { username, authenticated, role } = user;

  return (
    <nav className='bg-white shadow-md text-slate-100 py-2'>
      <div className="w-11/12 flex items-center justify-evenly">
        {/* logo and Link block */}
        <div className='flex h-6 justify-center items-center w-3/6'>
          {/* logo */}
          <Link to={'/'} className='w-40'>
            <img src={logo} alt='logo' className='rounded w-full' />
          </Link>

          {/* search bar */}
          <div className="bg-blue-100 w-full rounded-lg mx-10 flex justify-center items-center">
            <div className='text-slate-500 flex justify-center items-center w-7 text-2xl m-2 mr-0'><BiSearch /></div>
            <input
              type="search"
              placeholder="Search for Electronics, Brands and More .."
              className="p-2 bg-transparent w-full focus:outline-none text-slate-700 placeholder:text-slate-500"
            />
          </div>
        </div>

        {/* Nav Links */}
        <div className='text-slate-600 flex border-black justify-evenly items-center w-2/6'>
          <Link className='flex hover:bg-blue-500 p-3 rounded items-center' to={authenticated ? "/account" : "/login"}
            onMouseEnter={() => { setIsOpen((prev) => !prev) }} onMouseLeave={() => { setIsOpen((prev) => !prev) }}>
            <FaRegUserCircle className='mr-2' />
            {authenticated ?
              username
              : <div className=''>Login
                {isOpen && (
                  <div className='absolute p-2 mt-3 -ml-8'>
                    <HeaderLink path={'/register'} linkName={'New Customer? SignUp'} />
                  </div>
                  )
                }
              </div>
            }
          </Link>

          {
            (user != null && user != undefined) ?
              (authenticated && role === "CUSTOMER")
                ? <div className='flex'>
                  <HeaderLink icon={<FaHeart />} linkName={"Wishlist"} path={'/wishlist'} />
                  <HeaderLink icon={<IoCartOutline />} linkName={"Cart"} path={'/cart'} />
                </div>
                : (authenticated && role === "SELLER")
                  ? <HeaderLink icon={<LuBoxes />} linkName={"Orders"} path={'/orders'} />
                  : (!authenticated) &&
                  <HeaderLink linkName={"Become a Seller"} icon={<MdOutlineStore />} path={'/register'} />
              : <HeaderLink linkName={"Become a Seller"} icon={<MdOutlineStore />} path={'/register'} />
          }
        </div>

        <div className='flex'>
          <HiMiniBars3BottomLeft className='' />
          Hello
        </div>
      </div>
    </nav>
  )
}

export default Header

export const HeaderLink = ({ icon, linkName, path }) => {
  return (
    <div>
      <Link className='text-slate-600 flex justify-center items-center' to={path}>
        <div className='mr-2'>{icon}</div>
        <div className='mr-3'>{linkName}</div>
      </Link>
    </div>
  );
}
