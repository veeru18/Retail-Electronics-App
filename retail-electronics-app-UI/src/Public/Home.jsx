import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';
import { useAuth } from '../Auth/AuthProvider';
import useRefreshAuth from '../Auth/useRefreshAuth';

const Home = () => {
    const { user, setUser } = useAuth()
    let { userId, role, username, displayName, authenticated, accessExpiration, refreshExpiration } = user
    const { state } = useLocation();

    const { auth } = useRefreshAuth()
    // useEffect(() => {
    //     if (authenticated) {
    //         let { accessExpiration, refreshExpiration } = auth

    //         setUser(() => ({
    //             ...user, accessExpiration: new Date(new Date().getTime() + accessExpiration),
    //             refreshExpiration: new Date(new Date().getTime() + refreshExpiration)
    //         }))

    //         localStorage.setItem("user", JSON.stringify(user));
    //         console.log(user)
    //     }
    // }, [authenticated])

    // useEffect(() => {
    //     if (user.authenticated && user.accessExpiration < new Date()) {
    //       // Token has expired, refresh it
    //       refresh()
    //     }
    //   }, [])

    return (
        <div className="flex justify-center bg-gray-300 items-start h-dvh">
            <div id="homebody" className="w-full h-[100px] flex flex-col">
                <div id="home"
                    className="w-full bg-gradient-to-t from-blue-200 to-slate-50 rounded-b-2xl 
                        flex flex-col justify-evenly items-start pl-2 h-[150px] ">
                    {
                        (authenticated) ?
                            <div>{username} is logged in successfully</div> : <div>Homepage</div>
                    }
                </div>
            </div>
        </div>
    )
}

export default Home
