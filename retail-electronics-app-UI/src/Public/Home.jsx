import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';
import { useAuth } from '../Auth/AuthProvider';

const Home = () => {
  const { user, updateUser } = useAuth()
  const { state } = useLocation();
  const userResponse = state && state?.userResponse?.data;
  // console.log(userResponse)

  useEffect(() => {

    if (userResponse) {
      const { userId, username, displayName, userRole, email, isDeleted, isEmailVerified } = userResponse
      updateUser({
        ...user,
        userId: userId,
        role: userRole,
        authenticated: true,
        username: username,
        displayName: displayName,
        accessExpiration: 3600,
        refreshExpiration: 129600
      })
    } else {
      updateUser({
        ...user,
        userId: '',
        role: '',
        authenticated: false,
        username: '',
        displayName: '',
        accessExpiration: 0,
        refreshExpiration: 0
      })
    }
  }, [userResponse])
  return (
    <div className="flex justify-center bg-gray-300 items-start h-dvh">
      <div id="homebody" className="w-full h-[100px] flex flex-col">
        <div
          id="home"
          className="w-full bg-gradient-to-t from-blue-200 to-slate-50 rounded-b-2xl flex flex-col justify-evenly items-start pl-2 h-[150px] "
        >
          Home
          {userResponse !== null ?
            <h1>hello from {userResponse?.displayName}</h1>
            : <h3 className='text-red-400'>User isn't authenticated yet</h3>}
        </div>
      </div>
    </div>
  )
}

export default Home
