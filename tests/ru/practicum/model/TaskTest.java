package ru.practicum.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    void shouldReturnTrueWhenTasksEqualsById() {
        Task task1 = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);
        Task task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями", StatusTask.NEW);
        task1.setId(1);
        task2.setId(1);

        assertEquals(task1, task2);
    }

}
