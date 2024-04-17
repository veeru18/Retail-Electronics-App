import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const VerifyOTP = () => {
  const { state } = useLocation();
  const email = state && state?.data;
  const [otp, setOTP] = useState('');
  const [otpLenError, setOtpLenError] = useState(false)

  const [otpData, setOtpData] = useState({})
  const navigate = useNavigate()
  const Otpregex = /^(\d{6})$/;
  const handleOTPChange = ({ target: { name, value } }) => {
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
      const { data } = await axios.post(`http://localhost:8080/api/re-v1/verify-email`, otpData);
      // console.log(data);
      navigate("/", { state: { data: data } });
    } catch (error) {
      console.error(error);
    }
  }

  useEffect(() => {
    console.log("otp verify page rendered")
  }, [])

  return (
    <div className="flex flex-col justify-center bg-gray-300 items-center h-dvh">
      <form onSubmit={verifyOTPApi} className='w-[400px] bg-gradient-to-r from-violet-600 to-blue-400 rounded-lg flex flex-col justify-evenly items-center h-[400px] '>
        <fieldset>
          <legend>OTP Verification</legend>
          <p className='mt-14 text-lg italic font-medium'>Please enter the 6-digit code<br /> sent to your email:</p>
          <input className='w-7/12 rounded-md focus:outline-blue-500 sm:text-sm h-10 placeholder:italic placeholder:text-gray-400 block pr-3 shadow-sm font-mono text-2'
            id='otp' name='otp' type="text" placeholder="Enter OTP" onChange={handleOTPChange} maxLength={6} />
          {otpLenError && <p className="text-red-500 text-sm mt-1">Please enter 6 digits OTP sent to your email</p>}
          <button type='submit' className="mb-14 p-2 bg-blue-800 hover:bg-cyan-500 font-mono rounded-2xl w-[120px] text-white text-xl">
            Verify
          </button>
        </fieldset>
      </form>
    </div>
  );
};

export default VerifyOTP;