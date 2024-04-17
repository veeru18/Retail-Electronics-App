import { useNavigate } from 'react-router-dom'
import React, { useEffect, useState } from 'react'
import logo from '../Public/Resources/Electron_Logo.jpg'
import { TfiMobile } from 'react-icons/tfi';
import { ImHeadphones } from 'react-icons/im';
import { AiOutlineLaptop } from 'react-icons/ai';
import axios from 'axios';

const Register = ({ role }) => {

  const navigate=useNavigate()
  let [formData, setFormData] = useState({})
  const [inputValue, setInputValue] = useState('');
  const [emailInputError, setEmailInputError] = useState(false);
  const [passInputError, setPassInputError] = useState(false);
  const [nameError, setNameError] = useState(false)

  const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$/;
  // regex to match atleast one uppercase, one special character, one number and must be 8 characters or more
  const emailRegex = /^[a-zA-Z0-9]+[a-zA-Z0-9._%+-]*@gmail\.com$/;
  const nameRegex = /^[a-zA-Z0-9]+/;

  const handleSubmit = async (event) => {
    event.preventDefault();
    let {email}=formData
    let {data}= await axios.post(`http://localhost:8080/api/re-v1/register`,formData);
    console.log(data,email)
    navigate("/verify-otp", { state: { data: email } });
  };
  const handleInputChange = ({ target: { name, value } }) => {
    //event object destructured here
    setInputValue(value);
    console.log(formData)

    if (name === 'name' && !nameRegex.test(value)) {
      setNameError(true);
    } else {
      setNameError(false);
      setFormData({ ...formData, [name]: value })
    }
    if (name === 'email' && !emailRegex.test(value)) {
      setEmailInputError(true);
    } else {
      setEmailInputError(false);
      setFormData({ ...formData, [name]: value })
    }
    if (name === 'password' && !passwordRegex.test(value)) {
      setPassInputError(true);
    } else {
      setPassInputError(false);
      setFormData({ ...formData, [name]: value })
    }
  };

  useEffect(() => {
    setFormData({ ...formData, ['role']: role });
  }, []);

  return (
    <div className="flex justify-center bg-gray-300 items-center h-dvh">
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
          className="w-[400px] bg-gray-100 rounded-r-md flex justify-around items-center h-[400px] "
        >
          <form onSubmit={handleSubmit}
            id="logsubsec"
            className=" h-[300px] w-[300px] flex flex-col justify-around "
          >
            <div id="textbox" className='py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7'>
              <div className='relative'>
                <input id='name'
                  name='name' key={'name'}
                  onChange={handleInputChange}
                  type="text"
                  placeholder="Enter the Name"
                  className="peer placeholder-transparent h-10 w-full border-b-2 border-gray-300 text-gray-900 focus:outline-none focus:borer-rose-600"
                />
                <label for="name" class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440
                           peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Username</label>
                {nameError && <p className="text-red-500 text-sm mt-1">Please enter alphanumeric name.</p>}
              </div>

              <div className='relative'>
                <input id='email'
                  name='email' key={'email'}
                  onChange={handleInputChange}
                  type="text"
                  placeholder="Enter the Email"
                  className="peer placeholder-transparent h-10 w-full border-b-2 border-gray-300 text-gray-900 focus:outline-none focus:borer-rose-600"
                />
                <label for="email" class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440
                           peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Email Address</label>
                {emailInputError && <p className="text-red-500 text-sm mt-1">Please enter a valid Gmail ID.</p>}
              </div>
              <div className='relative'>
                <input id='password'
                  name='password' key={'password'}
                  onChange={handleInputChange}
                  type="password"
                  placeholder="Enter the Password"
                  className="peer placeholder-transparent h-10 w-full border-b-2 border-gray-300 text-gray-900 focus:outline-none focus:borer-rose-600"
                />
                <label for="password" class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440
                           peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Password</label>
                {passInputError && <p className="text-red-500 text-sm mt-1">Password should have 8+ and atleast one uppercase, one special character, one number</p>}
              </div>

              {/* <span className='mt-auto'>Select Account type:</span>
              <div className='flex justify-evenly -ml-20 items-center p-2'>
                <span className='seller-radio'>
                  <input
                    id='seller' type='radio'
                    value='seller'
                    onChange={handleInputChange}
                    name='role'
                  />
                  <label className='ml-2' htmlFor='seller' >Seller</label>
                </span>
                <span className='customer-radio'>
                  <input
                    id='customer' type='radio'
                    value='customer'
                    onChange={handleInputChange}
                    name='role'
                  />
                  <label className='ml-2' htmlFor='customer' >Customer</label>
                </span>
              </div> */}

            </div>
            <div className="flex justify-around items-center">
              <button type='submit' className="p-2 bg-blue-600 hover:bg-blue-800 font-mono rounded-2xl w-[120px] text-white text-xl">
                Register
              </button>
            </div>
          </form>
        </div>
      </div >
    </div >
  )
}

export default Register
