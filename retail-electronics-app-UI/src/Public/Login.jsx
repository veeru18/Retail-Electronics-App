import React, { useEffect, useState } from 'react'
import logo from '../Public/Resources/Electron_Logo.jpg'
import { TfiMobile } from 'react-icons/tfi';
import { ImHeadphones } from 'react-icons/im';
import { AiOutlineLaptop } from 'react-icons/ai';
import Input from '../Util/Input';
import Label from '../Util/Label';
import Button from '../Util/Button';
import { useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../Auth/AuthProvider';
import axios from 'axios';

const Login = () => {
  const { user, setUser } = useAuth()
  const navigate = useNavigate()
  // const { state } = useLocation();
  // let response=null
  // if(state?.success==="register")
  //   response=state&&state?.userResponse
  // if(response){
  //   let {userId,username,isEmailVerified,userRole}=response
  //   setUser({
  //     ...user,
  //     userId:userId,
  //     username:username,
  //     userRole:userRole,
  //     authenticated:isEmailVerified,
  //     accessExpiration:0,
  //     refreshExpiration:0
  //   })
  // }
  let [formData, setFormData] = useState({
    username: "",
    password: "",
  })
  const [usernameInputError, setUsernameInputError] = useState(false);
  const [passInputError, setPassInputError] = useState(false);

  const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$/;
  // regex to match atleast one uppercase, one special character, one number and must be 8 characters or more
  const usernameRegex = /^[a-zA-Z0-9]+[a-zA-Z0-9._%+-]*@gmail\.com$/;

  const [showPassword, setShowPassword] = useState(false);

  const showOrHidePassHandle = () => {
    setShowPassword((prevShowPassword) => !prevShowPassword);
  };

  const handleSubmit = async () => {
    try {
      console.log(formData)
      let { data: { data } } = await axios.post(`http://localhost:8080/api/re-v1/login`, formData,
        {
          headers: {
            "Content-Type": "application/json"
          },
          withCredentials: true
        });
      console.log(data)
      setUser({ ...data, authenticated: true })
      // hook can't be navigated to another without being inside tags/with no operation done in normal method
      navigate("/", { state: { success: "login", authResponse: data } });
    }
    catch (error) {
      // console.log(error)
      alert(error)
    }
  };
  const handleInputChange = (name, value) => {
    //event object destructured here
    if (name === 'username') {
      setFormData({ ...formData, username: value });
      if (value==='') setUsernameInputError(false);
      else if (!usernameRegex.test(value)) setUsernameInputError(true);
      else setUsernameInputError(false);
    }
    else if (name === 'password') {
      setFormData({ ...formData, password: value });
      if (value==='') setPassInputError(false);
      else if (!passwordRegex.test(value)) setPassInputError(true);
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
                <Input
                  id="password"
                  name="password"
                  onChange={handleInputChange}
                  type={showPassword ? "text" : "password"}
                  placeholder="Enter the Password"
                />
                <Label htmlFor="password" label="Password" />
                <div className="mt-4 flex items-center text-gray-500">
                  <input
                    onClickCapture={showOrHidePassHandle}
                    type="checkbox"
                    id="showpassword"
                    name="showpassword"
                    className="mr-2"
                  />
                  <label className="text-sm" htmlFor="showpassword">
                    Show password
                  </label>
                </div>
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