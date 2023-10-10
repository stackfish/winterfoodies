package com.food.winterfoodies2.controller;

import com.food.winterfoodies2.dto.TodoDto;
import com.food.winterfoodies2.service.TodoService;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin({"*"})
@RestController
@RequestMapping({"api/todos"})
@AllArgsConstructor
public class TodoController {
    private TodoService todoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto) {
        TodoDto savedTodo = this.todoService.addTodo(todoDto);// 27
        return new ResponseEntity(savedTodo, HttpStatus.CREATED);// 29
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping({"{id}"})
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId) {
        TodoDto todoDto = this.todoService.getTodo(todoId);// 36
        return new ResponseEntity(todoDto, HttpStatus.OK);// 37
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todos = this.todoService.getAllTodos();// 44
        return ResponseEntity.ok(todos);// 46
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"{id}"})
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto, @PathVariable("id") Long todoId) {
        TodoDto updatedTodo = this.todoService.updateTodo(todoDto, todoId);// 53
        return ResponseEntity.ok(updatedTodo);// 54
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping({"{id}"})
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId) {
        this.todoService.deleteTodo(todoId);// 61
        return ResponseEntity.ok("Todo deleted successfully!.");// 62
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping({"{id}/complete"})
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId) {
        TodoDto updatedTodo = this.todoService.completeTodo(todoId);// 69
        return ResponseEntity.ok(updatedTodo);// 70
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping({"{id}/in-complete"})
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long todoId) {
        TodoDto updatedTodo = this.todoService.inCompleteTodo(todoId);// 77
        return ResponseEntity.ok(updatedTodo);// 78
    }

}
