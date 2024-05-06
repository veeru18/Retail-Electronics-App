import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

const useRefreshAuth = () => {
  const [auth, setAuth] = useState(null); // Initialize with null instead of empty object
  const navigate = useNavigate();

  const clearData = () => {
    localStorage.removeItem("user");
    setAuth(null); // Set auth to null instead of an object
  };

  useEffect(() => {
    if (auth) localStorage.setItem("user", JSON.stringify(auth));
  }, [auth]);

  const refresh = async () => {
    try {
      console.log('refreshed tokens')
      const response = await axios.get(`http://localhost:8080/api/re-v1/refresh-access`, {
        withCredentials: true
      });
      if (response.status === 200) {
        const { accessExpiration, refreshExpiration,...data } = response.data.data;
        setAuth({
         ...data,
          accessExpiration: new Date(new Date().getTime() + accessExpiration * 1000),
          refreshExpiration: new Date(new Date().getTime() + refreshExpiration * 1000)
        });
      } else {
        console.log(response.data);
      }
    } catch (error) {
      if (error.response.status === 403) {
        console.log("Server responded with unauthorized/forbidden", error);
      } else {
        console.error("Error refreshing login:", error);
      }
      clearData();
      navigate("/");
    }
  };

  const handleRefresh = async () => {
    const user = localStorage.getItem("user");
    if (user) {
      try {
        const userData = JSON.parse(user);
        if (
          userData &&
          userData.userId &&
          userData.accessExpiration &&
          userData.refreshExpiration
        ) {
          const { accessExpiration, refreshExpiration } = userData;
          if (new Date(refreshExpiration) > new Date()) {
            console.log("refresh not expired!");
            if (new Date(accessExpiration) > new Date()) {
              console.log("access not expired!");
              setAuth(userData);
            } else refresh();
          } else {
            clearData();
            console.log("User login expired...");
            navigate("/");
          }
        } else {
          clearData();
          console.log("User not logged in...");
          navigate("/");
        }
      } catch (error) {
        console.error("Error parsing user data:", error);
        clearData();
        navigate("/");
      }
    } else {
      clearData();
      console.log("User not logged in...");
      navigate("/");
    }
  };

  let refreshing = false;

  useEffect(() => {
    if (!refreshing) {
      console.log("Refreshing...");
      refreshing = true;
      handleRefresh();
    }
  }, []);

  return { auth };
};

export default useRefreshAuth;