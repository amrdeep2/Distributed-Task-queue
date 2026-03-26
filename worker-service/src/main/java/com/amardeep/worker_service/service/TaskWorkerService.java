package com.amardeep.worker_service.service;


import com.amardeep.worker_service.model.Task;
import com.amardeep.worker_service.model.TaskStatus;
import com.amardeep.worker_service.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;

@Service
@RequiredArgsConstructor
public class TaskWorkerService {

    private final ListOperations<String, String> listOperations;
    private final TaskRepository taskRepository;

    private static final String QUEUE_NAME = "task-queue";

    @Scheduled(fixedDelay = 3000)
    public void pollQueue() {
        String taskId = listOperations.leftPop(QUEUE_NAME);

        if (taskId == null) {
            System.out.println("No task found in queue");
            return;
        }

        System.out.println("Picked task id from Redis: " + taskId);

        Task task = taskRepository.findById(Long.parseLong(taskId))
                .orElseThrow(() -> new RuntimeException("Task not found: " + taskId));

        try {
            task.setStatus(TaskStatus.PROCESSING);
            taskRepository.save(task);
            System.out.println("Task marked PROCESSING: " + task.getId());
            processTask(task);

            task.setStatus(TaskStatus.DONE);
            taskRepository.save(task);
            System.out.println("Task marked DONE: " + task.getId());

        } catch (Exception e) {
            task.setStatus(TaskStatus.FAILED);
            taskRepository.save(task);
            System.out.println("Task marked FAILED: " + task.getId());
            e.printStackTrace();
        }
    }
    private void processTask(Task task) throws Exception {
        String type = task.getType();

        if ("EMAIL".equalsIgnoreCase(type)) {
            processEmailTask(task);
        } else if ("REPORT".equalsIgnoreCase(type)) {
            processReportTask(task);
        } else if ("NOTIFICATION".equalsIgnoreCase(type)) {
            processNotificationTask(task);
        } else {
            throw new RuntimeException("Unsupported task type: " + type);
        }
    }

    private void processEmailTask(Task task) throws InterruptedException {
        System.out.println("Sending EMAIL task: " + task.getPayload());
        Thread.sleep(3000);
        System.out.println("EMAIL task completed for task id: " + task.getId());
    }

    private void processNotificationTask(Task task) throws InterruptedException {
        System.out.println("Sending NOTIFICATION task: " + task.getPayload());
        Thread.sleep(3000);
        System.out.println("NOTIFICATION task completed for task id: " + task.getId());
    }

    private void processReportTask(Task task) throws Exception {
        System.out.println("Generating REPORT task: " + task.getPayload());

        File folder = new File("generated-reports");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, "report-task-" + task.getId() + ".txt");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Report for task id: " + task.getId() + "\n");
            writer.write("Task type: " + task.getType() + "\n");
            writer.write("Payload: " + task.getPayload() + "\n");
            writer.write("Status: GENERATED\n");
        }

        System.out.println("REPORT file created: " + file.getAbsolutePath());
    }
}
