package com.amardeep.api_service.service;

import com.amardeep.api_service.dto.TaskRequest;
import com.amardeep.api_service.model.Task;
import com.amardeep.api_service.model.TaskStatus;
import com.amardeep.api_service.repo.TaskRepository;
import com.amardeep.api_service.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ListOperations<String, String> listOperations;

    private static final String QUEUE_NAME = "task-queue";

    public Task createTask(TaskRequest request) {
        Task task = Task.builder()
                .type(request.getType())
                .payload(request.getPayload())
                .status(TaskStatus.PENDING)
                .maxRetries(3)
                .retryCount(0)
                .build();
        System.out.println(listOperations);
        System.out.println("Before pushing to Redis");
        Task savedTask = taskRepository.save(task);
        System.out.println("After pushing to Redis");
        listOperations.rightPush(QUEUE_NAME, String.valueOf(savedTask.getId()));

        return savedTask;
    }
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
    } }