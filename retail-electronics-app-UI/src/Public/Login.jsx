import React, { useState } from 'react'
import logo from '../Public/Resources/Electron_Logo.jpg'
import { TfiMobile } from 'react-icons/tfi';
import { ImHeadphones } from 'react-icons/im';
import { AiOutlineLaptop } from 'react-icons/ai';

const Login = () => {
  let [ formData, setFormData ] = useState({})
  const [inputValue, setInputValue] = useState('');
  const [inputError, setInputError] = useState(false);

  const regex = /^[a-zA-Z0-9]+$/; // regex to match only alphanumeric characters

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(formData);
  };
  const handleInputChange = ({target:{name,value}}) => {
    //event object destructured here
    setInputValue(value);

    if (!regex.test(value)) {
      setInputError(true);
    } else {
      setInputError(false);
    }
    console.log(inputValue,inputError)
    setFormData({...formData,[name]:value})
  };

  return (
    <div className="flex justify-center bg-gray-300 items-center py-24">
      <div id="loginbody" className="w-[800px] h-[400px] flex ">
        <div
          id="loginsec1"
          className="w-[400px] h-[400px] flex flex-col justify-evenly rounded-l-md  items-center bg-blue-700 "
        >
          <div className='italic -ml-24 mb-20 text-3xl font-bold'>Login</div>
          <div className='flex'>
            <img className='rounded ' src={logo} alt="" height="100px" width="100px" />
            <AiOutlineLaptop className='mt-auto h-8 w-8' />
            <ImHeadphones className='mt-auto mb-1 h-6 w-6' />
            <TfiMobile className='mt-auto mb-1 h-6 w-6' />
          </div>
        </div>
        <div
          id="loginsec2"
          className="w-[400px] bg-gray-100 rounded-r-md flex justify-around items-center h-[400px] "
        >
          <form onSubmit={handleSubmit}
            id="logsubsec"
            className=" h-[300px] w-[300px] flex flex-col justify-around "
          >
            <div id="textbox">
              <span className="floating-label">Enter the Email</span>
              <input
                name='email'
                onChange={handleInputChange}
                type="text"
                placeholder="Enter the Email"
                className="w-full rounded-md focus:outline-blue-500 sm:text-sm h-10 placeholder:italic placeholder:text-gray-400 block pr-3 shadow-sm font-mono text-2"
              />

              <span className="mt-2">Enter the Password</span>
              <input
                name='password'
                onChange={handleInputChange}
                type="password"
                className="w-full rounded-md focus:outline-blue-500 sm:text-sm h-10 placeholder:italic placeholder:text-gray-400 block pr-3 shadow-sm font-mono text-2"
                placeholder="Enter the Password"
              />

            </div>
            <div className="flex justify-around items-center">
              <button type='submit' className="p-2 bg-blue-600 hover:bg-blue-800 font-mono rounded-2xl w-[120px] text-white text-xl">
                Login
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Login