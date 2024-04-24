import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import logo from '../Public/Resources/Electron_Logo.jpg'
import { MdOutlineStore } from 'react-icons/md';
import { BiSearch } from 'react-icons/bi';
import { IoCartOutline } from 'react-icons/io5';
import { LuBoxes } from 'react-icons/lu';
import { FaHeart, FaRegUserCircle } from 'react-icons/fa';
import { HiMiniBars3BottomLeft } from 'react-icons/hi2';
import { useAuth } from '../Auth/AuthProvider';
import { RxExit } from 'react-icons/rx';

const Header = ({users}) => {

  let name=null
  if(users) {
    const {displayName}=users;
    console.log(displayName,users)
    name=displayName
  }
  let [isOpen, setIsOpen] = useState(false);
  let [isMoreOpen, setIsMoreOpen] = useState(false);
  const links = ["Contact Us", "Terms & Conditions"]

  const { user, updateUser } = useAuth()

  const { username, authenticated, role } = user;

  return (
    <nav className='bg-white shadow-md text-slate-100 py-2'>
      <div className="w-11/12 flex items-center justify-evenly">
        {/* logo and Link block */}
        <div className='flex h-6 justify-around items-center w-4/6'>
          {/* logo */}
          <Link to={'/'} className='ml-6 w-40'>
            <img title='Homepage' src={logo} alt='logo' className='rounded w-full' />
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
          <div className='flex hover:bg-blue-500 p-3 rounded items-center'
            onMouseEnter={() => { setIsOpen((prev) => !prev) }} onMouseLeave={() => { setIsOpen((prev) => !prev) }}>
            <HeaderLink icon={<FaRegUserCircle title='User' className='-mr-2' />} path={authenticated ? "/account" : "/login"} />
            {authenticated ?
              <div>
                <Link to={'/account'} className='-ml-1'>{name}</Link>
                {isOpen && (
                  <Link to={'/'} className='absolute p-2 mt-7 -ml-[88px]' >
                    <p className='flex p-2'>
                      <RxExit className='mt-1 mr-2' title='Logout' />Logout
                    </p>
                  </Link>
                )}
              </div>
              : <Link to={'login'} className='-ml-1'>Login
                {isOpen && (
                  <div className='absolute p-2 mt-3 -ml-16'>
                    <HeaderLink path={'/register'} linkName={'New Customer? SignUp'} />
                  </div>
                )
                }
              </Link>
            }
          </div>

          {
            (user != null && user != undefined) ?
              (authenticated && role === "CUSTOMER")
                ? <div className='flex'>
                  <HeaderLink icon={<FaHeart title='Wishlist' />} linkName={"Wishlist"} path={'/wishlist'} />
                  <HeaderLink icon={<IoCartOutline title='Cart' />} linkName={"Cart"} path={'/cart'} />
                </div>
                : (authenticated && role === "SELLER")
                  ? <HeaderLink icon={<LuBoxes title='Orders' />} linkName={"Orders"} path={'/orders'} />
                  : (!authenticated) &&
                  <HeaderLink linkName={"Become a Seller"} icon={<MdOutlineStore />} path={'/register-seller'} />
              : <HeaderLink linkName={"Become a Seller"} icon={<MdOutlineStore />} path={'/register-seller'} />
          }
          <div className='flex hover:bg-blue-500 p-3 rounded items-center'
            onMouseEnter={() => { setIsMoreOpen((prev) => !prev) }} onMouseLeave={() => { setIsMoreOpen((prev) => !prev) }}>
            <HiMiniBars3BottomLeft title='More'/>
            <div className='absolute p-2 mt-28 -ml-7'>
              {isMoreOpen && links.map((item) => (
                <div>
                  <HeaderLink key={item} linkName={item} path={'/'} />
                </div>
              ))
              }
            </div>
          </div>
        </div>
      </div>
    </nav>
  )
}

export default Header

export const HeaderLink = ({ icon, linkName, path }) => {
  return (
    <div>
      <Link className='text-slate-600 flex justify-start items-center' to={path}>
        <div className='p-1 mr-1'>{icon}</div>
        <div title={linkName} className='p-1 mr-2'>{linkName}</div>
      </Link>
    </div>
  );
}
