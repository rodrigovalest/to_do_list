import "../assets/styles/Global.css";
import "../assets/styles/Sign.css";

import { Link } from "react-router-dom";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

export default function Signin() {
  const { register, handleSubmit } = useForm();
  let navigate = useNavigate();

  useEffect(() => {
    if (localStorage.getItem("token")) return navigate("/");
  }, []);

  function onSubmit(credentials) {
    fetch("http://localhost:8080/api/auth/signin", {
      method: "POST",
      headers: {
        "Content-Type" : "application/json"
      },
      body: JSON.stringify(credentials)
    })
      .then((res) => res.json())
      .then((data) => {
        localStorage.setItem("token", `Bearer ${data.token}`);
        navigate("/");
      })
      .catch((err) => {
        alert("Invalid credentials");
        console.log(err);
      });
  }

  return (
    <>
      <article>
        <h1>Sign in</h1>
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
            id="password"
            {...register("password")}
            className="inputSign"
          />
          <input
            type="submit"
          />
        </form>
        <Link to="/auth/signup" className="link">Create account</Link>
      </article>
    </>
  );
}
