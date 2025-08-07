import React from 'react'
import { Outlet } from 'react-router-dom'
import { Link } from 'react-router-dom';
import { useAuth } from '../../Auth/AuthProvider';

const EditProfile = () => {
  const { user, setUser } = useAuth()
  let { username, displayName } = user

  let linkNames = ["Home", "Edit Info", "Notifications", "Addresses", "Manage Payments","Seller Dashboard"]
  let links = ['/', '#', '#', '/add-address', '#' ,'/seller-dashboard']

  return (
    <div>
      <div className='flex'>
        <div className="w-1/6 sticky inset-0 hidden h-80 lg:h-auto overflow-x-hidden overflow-y-auto lg:overflow-y-hidden lg:block mt-0 border border-gray-400 lg:border-transparent bg-white shadow lg:shadow-none lg:bg-transparent z-20" style={{ top: '5em' }} id="menu-content">
          <div className='block m-2 font-semibold text-gray-700' ><h2>Hello, <i>{displayName}</i></h2></div>
          <UnorderedList linkNames={linkNames} links={links} />
        </div>
        {/* <Outlet /> */}
        {/* <AddAddress/> */}
      </div>
      {/* <Button text={'Add Address'} onClick={handleAddressLink} /> */}
    </div>
  )
}

export default EditProfile

export const UnorderedList = ({ linkNames, links }) => {
  return (
    <ul className="list-reset">
      {(linkNames.length === links.length) && linkNames.map((linkName, index) => (
        <li key={index} className="py-2 md:my-0 hover:bg-purple-100 lg:hover:bg-transparent">
          <Link to={links[index]} className="block pl-4 align-middle text-gray-700 no-underline hover:font-semibold hover:text-gray-500 border-l-4 border-transparent lg:hover:border-blue-500">
            <span className="pb-1 md:pb-0 text-sm">{linkName}</span>
          </Link>
        </li>
      ))
      }
    </ul>
  )
}
