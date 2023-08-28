import "../styles/Task.css";

export function Tasks({ tasks, updateTaskLists }) {
  function moveTask(text, id, status) {
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
          updateTaskLists();
        })
        .catch((err) => {
          alert("New task save fails");
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
              <button onClick={() => moveTask(task.text, task.id, "DOING")}>Doing</button>
              <button onClick={() => moveTask(task.text, task.id, "DONE")}>Done</button>
              <button onClick={() => moveTask(task.text, task.id, "TODO")}>Todo</button>
            </div>
          </div>
        );
      })}
    </>
  );
}
