package com.myfirstcrud.tasks.service

import com.myfirstcrud.tasks.model.Task
import com.myfirstcrud.tasks.repository.TasksRepository
import org.springframework.stereotype.Service

@Service
class TasksService (private val repository: TasksRepository) {
    fun getTasks(): Collection<Task> = repository.retrieveTasks()
    fun getTask(id: Int): Task = repository.retrieveTask(id)
    fun addTask(task: Task):Task = repository.createTask(task)
    fun updateTask(task: Task): Task = repository.updateTask(task)
    fun deleteTask(id: Int): Unit = repository.deleteTask(id)

}