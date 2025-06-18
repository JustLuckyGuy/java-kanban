package ru.practicum.manager;


import org.junit.jupiter.api.Test;
import ru.practicum.model.Task;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    protected InMemoryTaskManager createTask() {
        return new InMemoryTaskManager();
    }

    @Test
    void getPrioritizedTasksShouldReturnEmptySetWhenNoTasksAndIgnoreTaskWithoutTime() {
        InMemoryTaskManager testManager = createTask();
        TreeSet<Task> result = testManager.getPrioritizedTasks();
        assertTrue(result.isEmpty());

        task1.setStartTime(LocalDateTime.of(2025, Month.MAY, 12, 10, 0));
        testManager.createTask(task1);
        result = testManager.getPrioritizedTasks();
        assertEquals(1, result.size());
    }
}
