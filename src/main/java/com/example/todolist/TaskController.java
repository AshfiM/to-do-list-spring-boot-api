package com.example.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/app")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController (TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/taskList")
    public List<TaskModel> getAllTask(){
        List<TaskModel> taskList = new ArrayList<>();
        for (TaskEntity task: taskService.getAllTask()) {
            TaskModel taskM = new TaskModel();
            taskM.setTask(task.getTaskName());
            taskM.setDate(task.getDate());
            taskM.setId(task.getId());
            taskList.add(taskM);
        }
        return ResponseEntity.ok(taskList).getBody();
    }

    @GetMapping("/task/{id}")
    public TaskModel getTaskById(@PathVariable Long id) {
        Optional<TaskEntity> task = taskService.getTask(id);
        TaskModel res = new TaskModel();
        if (task.isPresent()) {
            res.setId(task.get().getId());
            res.setTask(task.get().getTaskName());
            res.setDate(task.get().getDate());
        }
        return ResponseEntity.ok(res).getBody();
    }
    @PostMapping(value = "/addTask",
    consumes = "application/json",
    produces = "application/json")
    public TaskModel addTask(@RequestBody TaskModel taskModel) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setDate(taskModel.getDate());
        taskEntity.setTaskName(taskModel.getTask());
        TaskEntity task = taskService.insertTask(taskEntity);
        TaskModel res = new TaskModel(task.getId(), task.getTaskName(), task.getDate());
        return  ResponseEntity.ok(res).getBody();
    }

    @PutMapping(value = "/updateTask",
    consumes = "application/json",
    produces = "application/json")
    public ResponseEntity<TaskModel> updateTask(@RequestBody TaskModel taskModel) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setDate(taskModel.getDate());
        taskEntity.setTaskName(taskModel.getTask());
        return taskService.updateTask(taskModel.getId(), taskEntity)
                .map(
                task -> {
                    TaskModel newTask = new TaskModel();
                    newTask.setId(task.getId());
                    newTask.setDate(task.getDate());
                    newTask.setTask(task.getTaskName());
                    return ResponseEntity.ok(newTask);
                })
                .orElse(ResponseEntity.notFound().build());

    }
    @DeleteMapping(value = "/deleteTask/{id}",
    produces = "application/json")
    public ResponseEntity<TaskModel> deleteTask(@PathVariable Long id) {
        Optional<TaskEntity> task = taskService.deleteTask(id);
        TaskModel taskModel = new TaskModel();
        task.ifPresent(delTask -> {
            taskModel.setTask(delTask.getTaskName());
            taskModel.setId(delTask.getId());
            taskModel.setDate(delTask.getDate());
        });
        return ResponseEntity.ok(taskModel);
    }
}

