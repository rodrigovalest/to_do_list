import "../styles/TaskForm.css";
import { useState } from "react";

export function TaskForm({ updateTaskLists }) {
  const [newTask, setNewTask] = useState("");
    
  function onChangeNewTask(event) {
      setNewTask(event.target.value);
  }

  function handleSubmitNewTask(event) {
    event.preventDefault();

    if (newTask == null || newTask == "") {
      return;
    }
        
    const data = {
      text: newTask,
      status: "TODO"
    }
        
    setNewTask("");

    fetch("http://localhost:8080/api/tasks", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
        body: JSON.stringify(data)
      })
        .then(response => response.json())
        .then(result => {
          updateTaskLists();
          alert("New task succefully saved");
        })
        .catch(error => {
          alert("New task save fails");
        });
    }

  return (
    <div className="formNewTask">
      <input 
        type="text" 
        name="text" 
        required 
        autoComplete="off" 
        value={newTask}
        onChange={onChangeNewTask} 
      />
      <button className="submit" onClick={handleSubmitNewTask}>Submit</button>
    </div>
  );
}