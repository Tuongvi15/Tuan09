package fit.iuh.se.fontend.controller;

import fit.iuh.se.fontend.dto.TaskDto;
import fit.iuh.se.fontend.model.Task;
import fit.iuh.se.fontend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*") // cho phép FE tách riêng gọi API
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    // Lấy danh sách tất cả task
    @GetMapping
    public List<TaskDto> getAll() {
        return service.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Lấy task theo ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getOne(@PathVariable Long id) {
        Task t = service.findById(id);
        if (t == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDto(t));
    }

    // Tạo task mới
    @PostMapping
    public ResponseEntity<TaskDto> create(@Valid @RequestBody TaskDto dto) {
        Task t = new Task();
        t.setTitle(dto.getTitle());
        t.setDescription(dto.getDescription());
        t.setCompleted(dto.isCompleted());
        Task saved = service.save(t);
        return ResponseEntity.created(URI.create("/api/tasks/" + saved.getId()))
                .body(toDto(saved));
    }

    // Cập nhật task theo ID
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> update(@PathVariable Long id,
                                          @Valid @RequestBody TaskDto dto) {
        Task existing = service.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setCompleted(dto.isCompleted());
        Task saved = service.save(existing);
        return ResponseEntity.ok(toDto(saved));
    }

    // Xóa task theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Đánh dấu task là completed
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskDto> complete(@PathVariable Long id) {
        Task t = service.findById(id);
        if (t == null) return ResponseEntity.notFound().build();
        t.setCompleted(true);
        Task saved = service.save(t);
        return ResponseEntity.ok(toDto(saved));
    }

    // Chuyển Task sang DTO
    private TaskDto toDto(Task t) {
        TaskDto dto = new TaskDto();
        dto.setId(t.getId());
        dto.setTitle(t.getTitle());
        dto.setDescription(t.getDescription());
        dto.setCompleted(t.isCompleted());
        return dto;
    }
}
