import "../styles/Task.css";

export function Tasks({ tasks, updateTaskLists }) {
  function moveToDoingTask(text, id) {
    const data = {
      text: text,
      status: "DOING"
    }

    fetch(`http://localhost:8080/api/tasks/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
        body: JSON.stringify(data)
      })
        .then(response => response.json())
        .then(result => {
          updateTaskLists();
        })
        .catch(error => {
          alert("New task save fails");
        });
  }
  
  return (
    <>
      {tasks.map((task, key) => {
        return (
          <div className="task" key={task}>
            <p>{task.taskText}</p>
            <div>
              <button onClick={() => moveToDoingTask(task.taskText, task.id)}>Doing</button>
              <button>Done</button>
              <button>X</button>
            </div>
          </div>
        );
      })}
    </>
  );
}
