package com.myfirstcrud.tasks.handler

import com.myfirstcrud.tasks.model.Task
import com.myfirstcrud.tasks.service.TasksService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping()
class TasksHandler (private val service: TasksService){

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getTasks(): Collection<Task> = service.getTasks()

    @GetMapping("/{id}")
    fun getTask(@PathVariable id: Int): Task = service.getTask(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addTask(@RequestBody task: Task): Task = service.addTask(task)

    @PatchMapping
    fun updateTask(@RequestBody task: Task): Task = service.updateTask(task)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTask(@PathVariable id: Int): Unit = service.deleteTask(id)
}
