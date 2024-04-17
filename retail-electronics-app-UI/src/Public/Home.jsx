import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';

const Home = () => {
  const { state } = useLocation();
  const user = state && state?.data?.data;
  console.log(user)
  return (
    <div>
      Home
    </div>
  )
}

export default Home
