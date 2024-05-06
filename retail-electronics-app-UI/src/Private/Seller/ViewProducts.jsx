import axios from 'axios';
import React, { useEffect, useState } from 'react'

const ViewProducts = () => {
    const [isOpen,setIsOpen] = useState(false)
    const [products, setProducts] = useState([])
    useEffect(() => {
      try {
          let { data: { data } } = axios.get(`http://localhost:8080/api/re-v1/seller/products`, {}, {
              withCredentials: true
          });
          data && setProducts(data)
          console.log(data)
      }
      catch (error) {
          console.log(error)
      }
    }, [])

    return (
        <div>
            <nav className="bg-white shadow-md text-slate-100 py-2">
                <div className="w-11/12 flex items-center justify-evenly">
                {isOpen && <Filters/>}
                </div>
            </nav>
        </div>
    )
}

export default ViewProducts

export const Filters = ({ filterNames, filters }) => {
    return (
      <ul className="list-reset">
        {(filterNames.length === filters.length) && filterNames.map((filterName, index) => (
          <li key={index} className="py-2 md:my-0 hover:bg-purple-100 lg:hover:bg-transparent">
            {/* <Link to={filters[index]} className="block pl-4 align-middle text-gray-700 no-underline hover:font-semibold hover:text-gray-500 border-l-4 border-transparent lg:hover:border-blue-500"> */}
              <span className="pb-1 md:pb-0 text-sm" onMouseEnter={()=>{}}>
                {filterName}
              </span>

            {/* </Link> */}
          </li>
        ))
        }
      </ul>
    )
  }