package com.virtusa.questor.controller;

import com.virtusa.questor.dto.TaskDTO;
import com.virtusa.questor.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/questor/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/addTask")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO tasksDTO){
        TaskDTO savedTask = taskService.saveTask(tasksDTO);
        return ResponseEntity.ok(savedTask);
    }

    @GetMapping("/getTask/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
        TaskDTO tasksDTO = taskService.getTaskById(id);
        return ResponseEntity.ok(tasksDTO);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskDTO> updateTaskById(@PathVariable Long id, @RequestBody TaskDTO tasksDTO) {
        return ResponseEntity.ok(taskService.updateTaskById(id, tasksDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO tasksDTO) {
        return ResponseEntity.ok(taskService.updateTask(tasksDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/byBoardId/{boardId}")
    public List<TaskDTO> getTasksByBoardId(@PathVariable Long boardId){
        return taskService.findTasksByBoardId(boardId);
    }

}
