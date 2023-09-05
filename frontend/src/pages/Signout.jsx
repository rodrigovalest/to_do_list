import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function Signout() {
  let navigate = useNavigate();

  useEffect(() => {
    localStorage.removeItem("token");
    return navigate("/auth/signin");
  }, []);
}
