import { useNavigate } from 'react-router-dom'
import React, { useEffect, useState } from 'react'
import logo from '../Public/Resources/Electron_Logo.jpg'
import { TfiMobile } from 'react-icons/tfi';
import { ImHeadphones } from 'react-icons/im';
import { AiOutlineLaptop } from 'react-icons/ai';
import axios from 'axios';
import Input from './../Util/Input';
import Button from './../Util/Button';
import Label from './../Util/Label';
import { useAuth } from '../Auth/AuthProvider';

const Register = ({ role }) => {
  const { user, updateUser } = useAuth()
  const navigate = useNavigate()
  let [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    role: role
  })
  const [emailInputError, setEmailInputError] = useState(false);
  const [passInputError, setPassInputError] = useState(false);
  const [nameInputError, setNameInputError] = useState(false)
  const [buttonChange, setButtonChange] = useState(false)

  const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$/;
  // regex to match atleast one uppercase, one special character, one number and must be 8 characters or more
  const emailRegex = /^[a-zA-Z0-9]+[a-zA-Z0-9._%+-]*@gmail\.com$/;
  const nameRegex = /^[a-zA-Z0-9]+/;

  const handleSubmit = async (event) => {
    alert("submitted")
    let { email } = formData
    try {
      let { data } = await axios.post(`http://localhost:8080/api/re-v1/register`, formData,
        {
          headers: {
            "Content-Type": "application/json"
          }
        });
      setButtonChange(true)
      console.log(data, email)
      updateUser({ ...user, username: email.split("@gmail.com") })
      navigate("/verify-otp", { state: { data: email } });
    }
    catch (error) {
      console.log(error)
      setButtonChange(false)
      alert(error.data?.data?.rootCause)
    }
  };
  const handleInputChange = (name, value) => {
    //event object destructured here
    if (name === 'name') {
      setFormData({ ...formData, name: value });
      if (!nameRegex.test(value)) setNameInputError(true)
      else {
        setNameInputError(false);

      }
    }
    else if (name === 'email') {
      setFormData({ ...formData, email: value });
      if (!emailRegex.test(value)) setEmailInputError(true);
      else {
        setEmailInputError(false);

      }
    }
    else if (name === 'password') {
      setFormData({ ...formData, password: value });
      if (!passwordRegex.test(value)) setPassInputError(true);
      else {
        setPassInputError(false);

      }
    }
  };

  useEffect(() => {

  }, []);

  return (
    <div className="flex justify-center bg-gray-300 items-center h-dvh">
      <div id="loginbody" className="w-[800px] h-[400px]  flex ">
        <div
          id="loginsec1"
          className="w-[400px] h-[400px] flex flex-col justify-evenly rounded-l-md  items-center bg-blue-700 "
        >
          <div className='italic text-3xl font-bold'>Register as {role.toLowerCase()}</div>
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
                <Input id={'name'}
                  name={'name'}
                  onChange={handleInputChange}
                  type={"text"}
                  value={formData?.name}
                  placeholder={"Enter the Name"}
                />
                <Label htmlFor={"name"}
                  label={'Username'}
                />
                {nameInputError && <p className="text-red-500 text-sm mt-1">Please enter alphanumeric name.</p>}
              </div>

              <div className='relative'>
                <Input id={'email'}
                  name={'email'}
                  onChange={handleInputChange}
                  type={"text"}
                  value={formData?.email}
                  placeholder={"Enter the Email"}
                />
                <Label htmlFor={"email"}
                  label={'Email Address'}
                />
                {emailInputError && <p className="text-red-500 text-sm mt-1">Please enter a valid Gmail ID.</p>}
              </div>
              <div className='relative'>
                <Input id={'password'}
                  name={'password'}
                  onChange={handleInputChange}
                  type={"password"}
                  value={formData?.password}
                  placeholder={"Enter the Password"}
                />
                <Label htmlFor={"password"}
                  label={'Password'}
                />
                {passInputError && <p className="text-red-500 text-sm mt-1">Password should have 8+ and atleast one uppercase, one special character, one number</p>}
              </div>
            </div>
            <div className="flex justify-around items-center">
              <Button
                text={'Register'}
                onClick={handleSubmit}
                disabled={buttonChange}
              />
            </div>
          </div>
        </div>
      </div >
    </div >
  )
}

export default Register
