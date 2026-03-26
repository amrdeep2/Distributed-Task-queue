package com.amardeep.api_service.controller;

import com.amardeep.api_service.dto.TaskRequest;
import com.amardeep.api_service.model.Task;
import com.amardeep.api_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest request) {
        Task createdTask = taskService.createTask(request);
        return ResponseEntity.ok(createdTask);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }


}