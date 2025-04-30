package ru.practicum.manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.practicum.model.*;

public class TaskTest {

    @Test
    void shouldReturnTrueWhenTasksEqualsById() {
        Task task1 = new Task("Epic Title", "Some description", StatusTask.NEW);
        Task task2 = new Task("Epic Title", "Some description", StatusTask.DONE); // статус может отличаться
        task1.setId(1);
        task2.setId(1);

        assertEquals(task1, task2);
    }

}
