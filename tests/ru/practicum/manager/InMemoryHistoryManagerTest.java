package ru.practicum.manager;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.manager.Managers.getDefault;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.*;



class InMemoryHistoryManagerTest {
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
    void checkHistory() {
        tasks.getTaskById(1);
        tasks.getTaskById(2);
        tasks.getEpicById(3);
        task1.setDescription("Новое описание");
        tasks.updateTask(task1);
        tasks.getTaskById(1);
        tasks.showHistory();
        assertNotEquals(tasks.showHistory().getFirst(), task1);
    }

    @Test
    void shouldReturn10WhenTasksMoreThen10(){
        tasks.getTaskById(1);
        tasks.getTaskById(2);
        tasks.getTaskById(1);
        tasks.getTaskById(2);
        tasks.getTaskById(1);
        tasks.getTaskById(2);
        tasks.getTaskById(1);
        tasks.getTaskById(2);
        tasks.getTaskById(1);
        tasks.getTaskById(2);
        tasks.getTaskById(1);
        tasks.getTaskById(2);

        assertEquals(10, tasks.showHistory().size());
    }


}