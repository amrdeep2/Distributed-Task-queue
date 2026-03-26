package com.amardeep.worker_service.repo;

import com.amardeep.worker_service.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}