import React from 'react'
import logo from '../Public/Resources/Electron_Logo.jpg'
import { TfiMobile } from 'react-icons/tfi';
import { ImHeadphones } from 'react-icons/im';
import { AiOutlineLaptop } from 'react-icons/ai';

const Register = () => {
  return (
    <div className="flex justify-center bg-gray-300 items-center py-24">
      <div id="loginbody" className="w-[800px] h-[400px]  flex ">
        <div
          id="loginsec1"
          className="w-[400px] h-[400px] flex flex-col justify-evenly rounded-l-md  items-center bg-blue-700 "
        >
          <div className='italic -ml-16 mb-20 text-3xl font-bold'>Register</div>
          <div className='flex'>
            <img className='rounded ' src={logo} alt="" height="100px" width="100px" />
            <AiOutlineLaptop className='mt-auto h-8 w-8' />
            <ImHeadphones className='mt-auto mb-1 h-6 w-6' />
            <TfiMobile className='mt-auto mb-1 h-6 w-6' />
          </div>
        </div>
        <div
          id="loginsec2"
          className="w-[400px] bg-gray-100 flex justify-around items-center h-[400px] "
        >
          <div
            id="logsubsec"
            className=" h-[300px] w-[300px] flex flex-col justify-around "
          >
            <div id="loginpara" className=" text-center text-lg font-bold">
              <p>LogIn</p>
            </div>
            <div id="textbox">
              <p className="font-mono">Enter the Username</p>
              <input
                type="text"
                placeholder="example@gmail.com"
                className="w-full rounded-md  border-blue-300 border-2 focus:outline-blue-500 sm:text-sm h-10 placeholder:italic placeholder:text-gray-400 block pr-3 shadow-sm font-mono text-2"
              />
              <p className="mt-2 font-mono">Enter the Password</p>
              <input
                type="text"
                className="w-full rounded-md  border-blue-300 border-2 focus:outline-blue-500 sm:text-sm h-10 placeholder:italic placeholder:text-gray-400 block pr-3 shadow-sm"
                placeholder="***********"
              />
            </div>
            <div className="flex justify-around items-center">
              <button className="bg-blue-600 hover:bg-blue-800 font-mono rounded-full w-[70px] text-white text-xl">
                Login
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Register
