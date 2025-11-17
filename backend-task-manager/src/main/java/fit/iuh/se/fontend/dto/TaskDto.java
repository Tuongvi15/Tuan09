package fit.iuh.se.fontend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class TaskDto {
    private Long id;


    @NotBlank(message = "Title không được để trống")
    @Size(max = 255)
    private String title;


    @Size(max = 2000)
    private String description;


    private boolean completed;


    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}