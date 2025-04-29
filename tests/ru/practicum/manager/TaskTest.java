package ru.practicum.manager;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.manager.Managers.getDefault;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.*;

public class TaskTest {
    public static TaskManager tasks;
    Task task1;
    Task task2;


    @BeforeEach
    void before() {
        tasks = getDefault();
        task1 = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);
        task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями", StatusTask.NEW);
        tasks.createTask(task1);
        tasks.createTask(task2);
    }

    @Test
    void shouldReturnTrueWhenTasksIsCreated() {
        assertEquals(2, tasks.getTasks().size());
    }

    @Test
    void getTaskByID() {
        Task takenTask = tasks.getTaskById(1);
        assertEquals(takenTask, tasks.getTaskById(task1.getId()));
    }

    @Test
    void shouldReturnTrueWhenTaskIsUpdated() {
        Task olddTask = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);
        task1.setNameTask("Новая задача");
        task1.setDescription("Исправить все ошибки");
        tasks.updateTask(task1);
        assertNotEquals(tasks.getTaskById(task1.getId()), olddTask);
    }

    @Test
    void shouldReturnTrueWhenTaskIsDeletedById() {
        tasks.removeTaskById(1);
        assertEquals(1, tasks.getTasks().size());
    }

    @Test
    void shouldReturnTrueWhenAllTasksIsDeleted() {
        tasks.removeAllTasks();
        assertEquals(0, tasks.getTasks().size());
    }

    @Test
    void shouldReturnTrueWhenTasksEqualsById() {
        Task sj = new Task("Посмотреть фильм", "Выбрать фильм с друзьями", StatusTask.NEW);
        sj.setId(task1.getId());
        tasks.updateTask(sj);
        assertEquals(tasks.getTaskById(sj.getId()), tasks.getTaskById(task1.getId()));
    }

    @Test
    void shouldNonConflictingValuesById() {
        Task task1 = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);
        tasks.createTask(task1);
        Task task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями", StatusTask.NEW);
        task2.setId(2);
        tasks.updateTask(task2);
        assertNotEquals(tasks.getTaskById(task1.getId()), tasks.getTaskById(task2.getId()));
        task2.setId(task1.getId());
        tasks.updateTask(task2);
        assertEquals(tasks.getTaskById(task1.getId()), tasks.getTaskById(task2.getId()));
    }
}
