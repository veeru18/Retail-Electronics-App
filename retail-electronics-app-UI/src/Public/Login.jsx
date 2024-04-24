import React, { useEffect, useState } from 'react'
import logo from '../Public/Resources/Electron_Logo.jpg'
import { TfiMobile } from 'react-icons/tfi';
import { ImHeadphones } from 'react-icons/im';
import { AiOutlineLaptop } from 'react-icons/ai';
import Input from '../Util/Input';
import Label from '../Util/Label';
import Button from '../Util/Button';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../Auth/AuthProvider';
import axios from 'axios';

const Login = () => {
  const { user, updateUser } = useAuth()
  const navigate=useNavigate
  let [formData, setFormData] = useState({
    username: "",
    password: "",
  })
  const [usernameInputError, setUsernameInputError] = useState(false);
  const [passInputError, setPassInputError] = useState(false);

  const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$/;
  // regex to match atleast one uppercase, one special character, one number and must be 8 characters or more
  const usernameRegex = /^[a-zA-Z0-9]+[a-zA-Z0-9._%+-]*@gmail\.com$/;

  const handleSubmit = async () => {
    let { username } = formData
    try {
      let { data } = await axios.post(`http://localhost:8080/api/re-v1/login`, formData,
        {
          headers: {
            "Content-Type": "application/json"
          }
        });
      console.log(formData)
      console.log(data)
      // hook can't be navigated to another without being inside tags/with no operation done in normal method
      // navigate("/", { state: { data: username } });
    }
    catch (error) {
      console.log(error)
      alert(error.data?.data?.rootCause)
    }
  };
  const handleInputChange = (name, value) => {
    //event object destructured here
    if (name === 'username') {
      setFormData({ ...formData, username: value });
      if (!usernameRegex.test(value)) setUsernameInputError(true);
      else setUsernameInputError(false);
    }
    else if (name === 'password') {
      setFormData({ ...formData, password: value });
      if (!passwordRegex.test(value)) setPassInputError(true);
      else setPassInputError(false);
    }
  };

  useEffect(() => {

  }, []);

  return (
    <div className="flex justify-center bg-gray-300 items-center py-24">
      <div id="loginbody" className="w-[800px] h-[400px] flex ">
        <div
          id="loginsec1"
          className="w-[400px] h-[400px] flex flex-col justify-evenly rounded-l-md  items-center bg-blue-700 "
        >
          <div className='italic -ml-24 text-3xl font-bold'>Login</div>
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
          <div
            id="logsubsec"
            className=" h-[300px] w-[300px] flex flex-col justify-around "
          >
            <div id="textbox" className='py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7'>
              <div className='relative'>
                <Input id='username'
                  name='username'
                  onChange={handleInputChange}
                  type="text"
                  placeholder="Enter the Email or Username"
                />
                <Label htmlFor="username" label='Username/Email' />
                {usernameInputError && <p className="text-red-500 text-sm mt-1">Please enter a valid Gmail ID.</p>}
              </div>
              <div className='relative'>
                <Input id='password'
                  name='password'
                  onChange={handleInputChange}
                  type="password"
                  placeholder="Enter the Password"
                />
                <Label htmlFor="password" label='Password' />
                {passInputError && <p className="text-red-500 text-sm mt-1">Password should have 8+ and atleast one uppercase, one special character, one number</p>}
              </div>
            </div>
            <div className="flex justify-around items-center">
              <Button text='Login' onClick={handleSubmit} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login