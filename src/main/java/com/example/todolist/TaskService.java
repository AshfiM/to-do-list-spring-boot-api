package com.example.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    public Iterable<TaskEntity> getAllTask() {
        return taskRepository.findAll();
    }
    public Optional<TaskEntity> getTask(Long id) {
        return taskRepository.findById(id);
    }
    public TaskEntity insertTask(TaskEntity task) {
        return taskRepository.save(task);
    }
    public Optional<TaskEntity> updateTask(Long id, TaskEntity newTask) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(id);
        if (taskEntity.isPresent()) {
            TaskEntity task = taskEntity.get();
            task.setTaskName(newTask.getTaskName());
            task.setDate(newTask.getDate());
            taskRepository.save(taskEntity.get());
        };

        return taskEntity;
    }
    public Optional<TaskEntity> deleteTask(Long id) {
        Optional<TaskEntity> task = taskRepository.findById(id);
        task.ifPresent(ta -> {
            taskRepository.deleteById(id);
        });
        return task;

    }
}
