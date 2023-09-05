import "../assets/styles/Global.css";

import { Link } from "react-router-dom";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

export default function Signup() {
  const { register, handleSubmit } = useForm();
  let navigate = useNavigate();

  useEffect(() => {
    if (localStorage.getItem("token")) return navigate("/");
  }, []);

  function onSubmit(credentials) {
    fetch("http://localhost:8080/api/auth/signup", {
      method: "POST",
      headers: {
        "Content-Type" : "application/json"
      },
      body: JSON.stringify(credentials)
    })
      .then((res) => {
        if (!res.ok) 
          throw new Error(`Error. HTTP Status: ${res.status}`);
        alert("Account succefully created");
        navigate("/auth/signin");
      })
      .catch((err) => {
        alert("Error while creating account");
        console.log(err);
        navigate("/auth/signup");
      });
  }

  return (
    <>
      <article>
        <h1>Create account</h1>
        <form onSubmit={handleSubmit(onSubmit)}>
          <label>Username</label>
          <input
            required
            placeholder="Username..."
            type="text"
            {...register("username")}
            className="inputSign"
            autoComplete="off"
          />
          <label>Password</label>
          <input
            required
            placeholder="Password..."
            type="password"
            {...register("password")}
            className="inputSign"
          />
          <input
            type="submit"
          />
        </form>
        <Link to="/auth/signin" className="link">Sign in</Link>
      </article>
    </>
  );
}
