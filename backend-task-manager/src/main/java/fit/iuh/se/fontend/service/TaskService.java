package fit.iuh.se.fontend.service;

import fit.iuh.se.fontend.model.Task;
import fit.iuh.se.fontend.repository.TaskRepository;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class TaskService {
    private final TaskRepository repo;


    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }


    public List<Task> findAll() { return repo.findAll(); }
    public Task findById(Long id) { return repo.findById(id).orElse(null); }
    public Task save(Task task) { return repo.save(task); }
    public void deleteById(Long id) { repo.deleteById(id); }
    public Task markComplete(Long id) {
        Task t = repo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        t.setCompleted(true);
        return repo.save(t);
    }
}
