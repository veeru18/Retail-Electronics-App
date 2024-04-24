import React, { useState, useEffect, useContext, createContext } from "react";

//context holding the auth user details
export const authContext=createContext({})

//component then returns the AuthContext by enclosing its child components within the context
const AuthProvider = ({children}) => {
    const [user, setUser] = useState({
        userId:"",
        userRole:"CUSTOMER",
        username:"",
        authenticated:false,
        accessExpiration:0,
        refreshExpiration:0
    });

    useEffect(() => console.log(user), [user])

    return (
        <authContext.Provider value={{user,setUser}}>
            {children}
        </authContext.Provider>
    );
};

export default AuthProvider;

export const useAuth=()=>useContext(authContext)