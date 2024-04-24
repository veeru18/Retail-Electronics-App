// usePreviousPath.js
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

const usePreviousPath = () => {
  const [previousPath, setPreviousPath] = useState("");
  const location = useLocation();

  useEffect(() => {
    setPreviousPath(location.pathname);
  }, [location]);

  return previousPath;
};

export default usePreviousPath;