package ru.practicum.manager;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.manager.Managers.getDefaultHistory;

import org.junit.jupiter.api.Test;
import ru.practicum.model.*;



class InMemoryHistoryManagerTest {
    public HistoryManager tasks = getDefaultHistory();

    @Test
    void checkHistory() {
        Task task1 = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);
        Task task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями", StatusTask.NEW);
        tasks.addHistory(task1);
        tasks.addHistory(task2);
        tasks.addHistory(task1);
        tasks.addHistory(task2);
        task1.setDescription("Новое описание");

        assertNotEquals(tasks.getHistory().getFirst(), task1);
    }

    @Test
    void shouldReturn10WhenTasksMoreThen10(){
        Task task1 = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);
        Task task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями", StatusTask.NEW);
        Epic epic1 = new Epic("Очень большая и важная задача", "Дедлайн до завтра", StatusTask.NEW);
        Epic epic2 = new Epic("Менее большая задача", "Дедлайн сегодня", StatusTask.NEW);
        tasks.addHistory(task1);
        tasks.addHistory(task2);
        tasks.addHistory(task1);
        tasks.addHistory(epic1);
        tasks.addHistory(epic2);
        tasks.addHistory(epic1);
        tasks.addHistory(epic1);
        tasks.addHistory(task1);
        tasks.addHistory(task1);
        tasks.addHistory(task2);
        tasks.addHistory(task2);
        tasks.addHistory(epic2);
        assertEquals(10, tasks.getHistory().size());
    }
}