import { createContext, useContext, useEffect, useState } from "react";
import useRefreshAuth from "./useRefreshAuth";

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
  const { auth } = useRefreshAuth();

  const [user, setUser] = useState({
    userId: "",
    username: "",
    roles: ["CUSTOMER"],
    accessExpiration: null,
    refreshExpiration: null,
    authenticated: false,
  });
  // const [user, setUser] = useState(() => {
  //   const storedUser = localStorage.getItem("user");
  //   return storedUser? 
  //     JSON.parse(storedUser)
  //      : {
  //         userId: "",
  //         userRole: "CUSTOMER",
  //         username: "",
  //         authenticated: false,
  //         accessExpiration: 0,
  //         refreshExpiration: 0,
  //       };
  // });

  useEffect(() => {
    if (auth?.userId) setAuth(auth);
  }, [auth]);

  return (
    <AuthContext.Provider value={{ user, setUser }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;

// Custom Hook
export const useAuth = () => useContext(AuthContext);