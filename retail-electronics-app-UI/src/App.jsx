import React from 'react'
import Header from './Util/Header'
import { Outlet } from 'react-router-dom'

const App = () => {
  return (
    <div>
      <Header/>
      App
      <Outlet/>
    </div>
  )
}

export default App
