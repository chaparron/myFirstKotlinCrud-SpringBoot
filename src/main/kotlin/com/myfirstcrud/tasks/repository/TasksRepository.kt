package com.myfirstcrud.tasks.repository

import com.myfirstcrud.tasks.model.Task
import org.springframework.stereotype.Repository

@Repository
class TasksRepository{

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