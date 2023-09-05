import "../assets/styles/NewTaskForm.css";
import { useState } from "react";

export function NewTaskForm({ updateTaskLists }) {
  const [newTask, setNewTask] = useState("");
    
  function onChangeNewTask(event) {
      setNewTask(event.target.value);
  }

  function handleSubmitNewTask(event) {
    event.preventDefault();

    if (newTask == null || newTask == "") return;

    const data = {
      text: newTask,
      status: "TODO"
    }
        
    setNewTask("");

    fetch("http://localhost:8080/api/tasks", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": localStorage.getItem("token")
      },
        body: JSON.stringify(data)
      })
        .then((res) => res.json())
        .then((data) => {
          updateTaskLists();
          alert("New task succefully saved");
        })
        .catch((err) => {
          alert("New task save fails");
          console.log(err);
        });
    }

  return (
    <div className="divFormNewTask">
      <input 
        type="text" 
        name="text" 
        required 
        autoComplete="off" 
        value={newTask}
        onChange={onChangeNewTask} 
        className="formNewTask"
      />
      <button className="buttonSubmit" onClick={handleSubmitNewTask}>Submit</button>
    </div>
  );
}