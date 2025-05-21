package ru.practicum.manager;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.manager.Managers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.*;

import java.util.List;


class InMemoryHistoryManagerTest {
    public HistoryManager tasksHistory = getDefaultHistory();
    Task task1;
    Task task2;
    Epic epic1;
    Epic epic2;

    @BeforeEach
    void before() {
        task1 = new Task("Прогулка", "Взять с собой собаку");
        task1.setId(1);
        task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями");
        task2.setId(2);
        epic1 = new Epic("Очень большая и важная задача", "Дедлайн до завтра");
        epic1.setId(3);
        epic2 = new Epic("Менее большая задача", "Дедлайн сегодня");
        epic2.setId(4);

    }

    @Test
    void checkHistory() {
        tasksHistory.addHistory(task1);
        tasksHistory.addHistory(task2);
        tasksHistory.addHistory(task1);
        tasksHistory.addHistory(task2);
        List<? extends Task> arr = tasksHistory.getHistory();

        assertEquals(2, arr.size());
    }

    @Test
    void shouldReturnTrueWhenWithoutDuplicate() {
        tasksHistory.addHistory(task1);
        tasksHistory.addHistory(task2);
        tasksHistory.addHistory(task1);
        tasksHistory.addHistory(epic1);
        tasksHistory.addHistory(epic2);
        tasksHistory.addHistory(epic1);
        tasksHistory.addHistory(epic1);
        tasksHistory.addHistory(task1);
        tasksHistory.addHistory(task1);
        tasksHistory.addHistory(task2);
        tasksHistory.addHistory(task2);
        tasksHistory.addHistory(epic2);
        assertEquals(4, tasksHistory.getHistory().size());
    }

    @Test
    void shouldReturn3WhenTaskInHistoryIsRemoved() {
        tasksHistory.addHistory(task1);
        tasksHistory.addHistory(task2);
        tasksHistory.addHistory(task1);
        tasksHistory.addHistory(epic1);
        tasksHistory.addHistory(epic2);
        tasksHistory.removeHistoryById(task1.getId());
        assertEquals(3, tasksHistory.getHistory().size());
    }

}