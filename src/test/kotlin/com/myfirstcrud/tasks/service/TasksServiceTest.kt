package com.myfirstcrud.tasks.service

import com.myfirstcrud.tasks.model.Task
import com.myfirstcrud.tasks.repository.TasksRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class TasksServiceTest {

    @Mock
    private lateinit var repository: TasksRepository
    @InjectMocks
    private lateinit var sut: TasksService

    @Test
    fun `can get tasks`() {
        val tasks = mutableListOf(
            Task(1, "Fregar los platos", false),
            Task(2, "Hacer ejercicio", true)
        )
        whenever(repository.retrieveTasks()).thenReturn(tasks)

        val result = sut.getTasks()

        assertEquals(tasks, result)
        verify(repository).retrieveTasks()
    }
}