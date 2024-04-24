import React, { useEffect, useState } from 'react';
import { Navigate, useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Button from '../Util/Button';
import Input from '../Util/Input';
import Label from '../Util/Label';
import { useAuth } from '../Auth/AuthProvider';
import usePreviousPath from '../Routes/usePreviousPath';

const VerifyOTP = () => {
  const { user, setUser } = useAuth()
  const { state } = useLocation();
  const previousPath = usePreviousPath();
  const email = state && state?.data;
  const [otp, setOTP] = useState('');
  const [buttonChange, setButtonChange] = useState(false)
  const [otpLenError, setOtpLenError] = useState(false)

  const [otpData, setOtpData] = useState({})
  const navigate = useNavigate()
  const Otpregex = /^(\d{6})$/;

  const handleOTPChange = (name, value) => {
    setOTP(value);
    if (name === 'otp' && !Otpregex.test(value)) setOtpLenError(true);
    else {
      setOtpLenError(false)
      setOtpData({ ...otpData, [name]: value, 'email': email });
    }
  };
  // if (regex.test(value)) {
  const verifyOTPApi = async () => {
    try {
      if (previousPath !== "/register" || previousPath !== "/register-seller") {
        navigate("/", { replace: true });
      }
      const { data } = await axios.post(`http://localhost:8080/api/re-v1/verify-email`, otpData);
      // console.log(data);
      setButtonChange(true)
      navigate("/", { state: { success:"register", userResponse: data } });
    } catch (error) {
      setButtonChange(false)
      alert(error.data?.rootCause);
    }
  }

  useEffect(() => {

  }, []);

  return (
    <div className="flex justify-center bg-gray-300 items-center h-dvh">
      <div id="loginbody" className="w-[500px] h-[600px] flex flex-col">
        <div
          id="loginsec1"
          className="w-[500px] bg-gradient-to-b from-blue-500 to-slate-100 rounded-t-md flex justify-around items-center h-[200px] "
        >
          <p className='select-none text-lg italic font-medium'>Email Verification for :<br />{email} to register</p>
        </div>
        <div
          id="loginsec2"
          className="w-[500px] bg-gray-100 rounded-b-md flex justify-around items-center h-[300px] "
        >

          <div
            id="logsubsec"
            className=" h-[300px] w-[300px] flex flex-col justify-around "
          >
            <div id="textbox" className='py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7'>
              <div className='relative'>

                <Input id='otp'
                  name='otp'
                  type="text"
                  placeholder="Enter OTP"
                  onChange={handleOTPChange}
                  maxLength={6}
                />
                <Label label={'Enter OTP'} htmlFor={'otp'}
                />
                {otpLenError && <p className="text-red-500 text-sm mt-1">Please enter 6 digits OTP sent to your email</p>}
                <Button
                  onClick={verifyOTPApi}
                  text='Verify'
                  disabled={buttonChange}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default VerifyOTP;