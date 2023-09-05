import "../assets/styles/Global.css";
import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import { Tasks } from "../components/Tasks";
import { NewTaskForm } from "../components/NewTaskForm";

export default function Home() {
  const [todoTasks, setTodoTasks] = useState([]);
  const [doingTasks, setDoingTasks] = useState([]);
  const [doneTasks, setDoneTasks] = useState([]);

  useEffect(() => {
    updateTaskLists();
  }, []);

  const updateTaskLists = () => {
    fetch("http://localhost:8080/api/tasks", {
      method: "GET",
      headers: {
        "Authorization": localStorage.getItem("token")
      }
    })
      .then((res) => res.json())
      .then((data) => {
        const newTodoTasks = [];
        const newDoingTasks = [];
        const newDoneTasks = [];

        data.data.forEach((task) => {
          if (task.status === "TODO") {
            newTodoTasks.push(task);
          } else if (task.status === "DOING") {
            newDoingTasks.push(task);
          } else {
            newDoneTasks.push(task);
          }
        });

        setTodoTasks(newTodoTasks);
        setDoingTasks(newDoingTasks);
        setDoneTasks(newDoneTasks);
      })
      .catch((err) => console.log(err));
  };

  return (
    <>
      <nav>
        <Link to="/auth/signout" className="link">Sign out 👋</Link>
      </nav>
      <article>
        <header>
          <h1>TODO list 📝</h1>
          <NewTaskForm updateTaskLists={updateTaskLists} />
        </header>
        <section>
          <h2>TODO 🏃</h2>
          <Tasks updateTaskLists={updateTaskLists} tasks={todoTasks} />
        </section>
        <section>
          <h2>DOING 🛠️</h2>
          <Tasks updateTaskLists={updateTaskLists} tasks={doingTasks} />
        </section>
        <section>
          <h2>DONE ✅</h2>
          <Tasks updateTaskLists={updateTaskLists} tasks={doneTasks} />
        </section>
      </article>
    </>
  );
}
