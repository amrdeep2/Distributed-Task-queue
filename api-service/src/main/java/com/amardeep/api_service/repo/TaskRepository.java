package com.amardeep.api_service.repo;

import com.amardeep.api_service.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}