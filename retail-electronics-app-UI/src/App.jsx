import React, { useEffect } from 'react'
import Header from './Util/Header'
import { Outlet } from 'react-router-dom'
import { useAuth } from './Auth/AuthProvider'

const App = (props) => {
  const { user, updateUser } = useAuth()
  // console.log(user)

  useEffect(()=>{

  },[])
  return (
      <div>
        <Header users={user} />
        <Outlet />
      </div>
  )
}

export default App
