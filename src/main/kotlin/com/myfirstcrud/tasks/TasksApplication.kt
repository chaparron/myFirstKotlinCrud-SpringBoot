package com.myfirstcrud.tasksv

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@SpringBootApplication
class TodoApplication

fun main(args: Array<String>) {
	runApplication<TodoApplication>(*args)
}

data class Task(
	val id: Int,
	val name: String,
	val done: Boolean
)
@Repository
class TaskDataSource{

	val tasks = mutableListOf(
		Task(1, "Fregar los platos", false),
		Task(2, "Hacer ejercicio", true),
		Task(3, "Programar", false)
	)

	fun retrieveTasks(): Collection<Task> = tasks

	fun retrieveTask(id: Int): Task =
		tasks.firstOrNull() { it.id == id }
			?: throw NoSuchElementException("Could not find a task with id $id")

	fun createTask(task: Task): Task {
		if (tasks.any { it.id == task.id }) {
			throw IllegalArgumentException("Task with id ${task.id} already exists.")
		}
		tasks.add(task)
		return task
	}

	fun updateTask(task: Task): Task {
		val currentTask = tasks.firstOrNull { it.id == task.id}
			?: throw NoSuchElementException("Could not find a bank with id ${task.id}")
		tasks.remove(currentTask)
		tasks.add(task)
		return task

	}

	fun deleteTask(id: Int) {
		val currentTask = tasks.firstOrNull { it.id == id }
			?: throw NoSuchElementException("Could not find a task with id $id")
		tasks.remove(currentTask)
	}
}
@Service
class TaskService (private val dataSource: TaskDataSource) {
	fun getTasks(): Collection<Task> = dataSource.retrieveTasks()
	fun getTask(id: Int): Task = dataSource.retrieveTask(id)
	fun addTask(task: Task):Task = dataSource.createTask(task)
	fun updateTask(task: Task): Task = dataSource.updateTask(task)
	fun deleteTask(id: Int): Unit = dataSource.deleteTask(id)

}
@RestController
@RequestMapping()
class TaskController (private val service: TaskService){

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
