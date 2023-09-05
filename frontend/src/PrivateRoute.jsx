import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

export default function PrivateRoute({ children }) {
  const navigate = useNavigate();
  const [isTokenValid, setIsTokenValid] = useState(null);

  useEffect(() => {
    validateToken();
  }, []);

  const validateToken = () => {
    fetch("http://localhost:8080/api/auth/validate", {
      method: "POST",
      headers: {
        "Authorization": localStorage.getItem("token"),
      },
    })
      .then((res) => {
        if (!res.ok) 
          throw new Error(`Error. HTTP Status: ${res.status}`);
        setIsTokenValid(true);
      })
      .catch((err) => {
        console.log(err);
        setIsTokenValid(false);
      });
  };

  if (isTokenValid) 
    return children;
  else
    return navigate("/auth/signin");
}
