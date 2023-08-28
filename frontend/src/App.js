import "./styles/Index.css";
import { useState, useEffect } from "react";
import { Tasks } from "./components/Tasks.js";
import { TaskForm } from "./components/TaskForm.js";

export default function App() {
  const [ todoTasks, setTodoTasks ] = useState([]);
  const [ doingTasks, setDoingTasks ] = useState([]);
  const [ doneTasks, setDoneTasks ] = useState([]);

  useEffect(() => {
    updateTaskLists();
  }, []);

  const updateTaskLists = () => {
    fetch("http://localhost:8080/api/tasks")
      .then((response) => response.json())
      .then((result) => {
        const newTodoTasks = [];
        const newDoingTasks = [];
        const newDoneTasks = [];

        result.data.forEach((task) => {
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
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <>
      <article>
        <header>
          <h1>TODO list 📝</h1>
          <TaskForm updateTaskLists={updateTaskLists} />
        </header>
        <section>
          <h2>TODO 🏃</h2>
          <Tasks updateTaskLists={updateTaskLists} tasks={todoTasks}/>
        </section>
        <section>
          <h2>DOING 🛠️</h2>
          <Tasks updateTaskLists={updateTaskLists} tasks={doingTasks}/>
        </section>
        <section>
          <h2>DONE ✅</h2>
          <Tasks updateTaskLists={updateTaskLists} tasks={doneTasks}/>
        </section>
      </article>
    </>
  );
}
