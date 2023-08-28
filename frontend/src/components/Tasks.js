import "../styles/Task.css";

export function Tasks({ tasks, updateTaskLists }) {
  function handleMoveTask(text, id, status) {
    const data = {
      text: text,
      status: status
    }

    fetch(`http://localhost:8080/api/tasks/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
        body: JSON.stringify(data)
      })
        .then((response) => response.json())
        .then((result) => {
          alert("Success in creating new task");
          updateTaskLists();
        })
        .catch((err) => {
          alert("New task save fails");
          console.log(err);
        });
  }

  function handleDeleteTask(id) {
    fetch(`http://localhost:8080/api/tasks/${id}`, {
      method: "DELETE"})
        .then((response) => response.json())
        .then((result) => {
          alert("Delete task succefull");
          updateTaskLists();
        })
        .catch((err) => {
          alert("Failed to delete task");
          console.log(err);
        });
  }
  
  return (
    <>
      {tasks.map((task, key) => {
        return (
          <div className="task" key={task.id}>
            <p>{task.text}</p>
            <div>
              <button onClick={() => handleMoveTask(task.text, task.id, "DOING")}>Doing</button>
              <button onClick={() => handleMoveTask(task.text, task.id, "DONE")}>Done</button>
              <button onClick={() => handleMoveTask(task.text, task.id, "TODO")}>Todo</button>
              <button onClick={() => handleDeleteTask(task.id)}>
                Delete task
              </button>
            </div>
          </div>
        );
      })}
    </>
  );
}