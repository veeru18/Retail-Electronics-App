import React, { useState, useEffect, useContext, createContext } from "react";

//context holding the auth user details
export const authContext=createContext({})

//component then returns the AuthContext by enclosing its child components within the context
const AuthProvider = ({children}) => {
    const [user, setUser] = useState({
        userId:"",
        role:"CUSTOMER",
        username:"",
        displayName:"",
        authenticated:false,
        accessExpiration:0,
        refreshExpiration:0
    });

    useEffect(() => {
        // Load user authentication details from local storage or an API
        sessionStorage.setItem("user", JSON.stringify(user));
        // const storedUser = JSON.parse(localStorage.getItem("user"));
        // if (storedUser) {
        //     setUser(storedUser);
        // }
    }, []);

    const updateUser=(newUser)=>{
        sessionStorage.setItem("user", JSON.stringify(newUser));
        setUser(newUser)
    }

    const logout = () => {
        sessionStorage.removeItem("user");
        setUser(null);
    };

    return (
        <authContext.Provider value={{user,setUser,updateUser,logout}}>
            {children}
        </authContext.Provider>
    );
};

export default AuthProvider;

export const useAuth=()=>useContext(authContext)