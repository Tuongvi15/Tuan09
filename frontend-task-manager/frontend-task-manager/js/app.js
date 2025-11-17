const api = 'http://localhost:8080/api/tasks';

// Load tasks
function fetchTasks() {
    axios.get(api).then(res => {
        const tasks = res.data;
        const tbody = document.querySelector('#tasks-table tbody');
        tbody.innerHTML = '';
        tasks.forEach(t => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
        <td>${t.id}</td>
        <td>${t.title}</td>
        <td>${t.completed ? 'Done' : 'Pending'}</td>
        <td>
          <button class="btn btn-sm btn-warning me-1" onclick="editTask(${t.id})">Edit</button>
          <button class="btn btn-sm btn-danger me-1" onclick="deleteTask(${t.id})">Delete</button>
          ${t.completed ? '' : `<button class="btn btn-sm btn-success" onclick="completeTask(${t.id})">Mark Done</button>`}
        </td>
      `;
            tbody.appendChild(tr);
        });
    });
}

function editTask(id) { window.location.href = `task-form.html?id=${id}`; }

function deleteTask(id) {
    if (!confirm(`Xác nhận xóa task #${id}?`)) return;
    axios.delete(`${api}/${id}`).then(fetchTasks);
}

function completeTask(id) {
    axios.patch(`${api}/${id}/complete`).then(fetchTasks);
}

// Thêm/sửa task
document.getElementById('task-form')?.addEventListener('submit', function(e){
    e.preventDefault();
    const id = document.getElementById('task-id').value;
    const data = {
        title: document.getElementById('title').value,
        description: document.getElementById('description').value,
        completed: document.getElementById('completed').checked
    };
    if (id) {
        axios.put(`${api}/${id}`, data).then(() => window.location.href='index.html');
    } else {
        axios.post(api, data).then(() => window.location.href='index.html');
    }
});

// Load tasks nếu đang ở index.html
if (document.querySelector('#tasks-table')) fetchTasks();