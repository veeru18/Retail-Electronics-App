import React from 'react'
import Header from './Util/Header'
import { Outlet } from 'react-router-dom'

const App = (props) => {
  const userAuth=props?.userAuth;
  return (
    <div>
      <Header userAuth={userAuth} />
      <Outlet/>
    </div>
  )
}

export default App
