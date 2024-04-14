import React, { useEffect, useState } from 'react';

const VerifyOTP = ({ location }) => {
  // const {state}=location
  // const email= state&&state?.email;
  const [otp, setOTP] = useState('');

  const handleOTPChange = ({ target: { value } }) => {
    const regex = /^(\d{6})$/;

    if (regex.test(value)) {
      setOTP(value);
    }
    console.log(otp)
  };

  useEffect(() => {

  }, [])

  return (
    <div className="flex flex-col justify-center bg-gray-300 items-center h-dvh">
      <form onSubmit={handleOTPChange} className='w-[400px] bg-gradient-to-r from-violet-600 to-blue-400 rounded-lg flex flex-col justify-evenly items-center h-[400px] '>
        <fieldset>
          <legend>OTP Verification</legend>
          <p className='mt-14 text-lg italic font-medium'>Please enter the 6-digit code<br /> sent to your email:</p>
          <input className='w-7/12 rounded-md focus:outline-blue-500 sm:text-sm h-10 placeholder:italic placeholder:text-gray-400 block pr-3 shadow-sm font-mono text-2'
            id='otp' type="text" placeholder="Enter OTP" onChange={handleOTPChange} maxLength={6} />
          <button type='submit' className="mb-14 p-2 bg-blue-800 hover:bg-cyan-500 font-mono rounded-2xl w-[120px] text-white text-xl">
            Verify
          </button>
        </fieldset>
      </form>
    </div>
  );
};

export default VerifyOTP;