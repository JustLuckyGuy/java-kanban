package ru.practicum.manager;

import org.junit.jupiter.api.Test;
import ru.practicum.exeptions.ManagerIsIntersectException;
import ru.practicum.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;


public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

    @Override
    protected InMemoryTaskManager createTask() {
        return new InMemoryTaskManager();
    }


    @Test
    void getPrioritizedTasksShouldReturnEmptySetWhenNoTasksAndIgnoreTaskWithoutTime() {
        TreeSet<Task> result = tasks.getPrioritizedTasks();
        assertTrue(result.isEmpty());

        task1.setStartTime(LocalDateTime.of(2025, Month.MAY, 12, 10, 0));
        tasks.updateTask(task1);
        result = tasks.getPrioritizedTasks();
        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnExceptionWhenTasksIsIntersect(){
        task1.setStartTime(LocalDateTime.of(2025, Month.MAY, 12, 10, 0));
        task1.setDuration(Duration.ofMinutes(30));
        tasks.updateTask(task1);
        Task tempTask = new Task("A", "B");
        tempTask.setStartTime(LocalDateTime.of(2025, Month.MAY, 12, 10, 10));
        tempTask.setDuration(Duration.ofMinutes(10));

        assertThrows(ManagerIsIntersectException.class, () -> tasks.createTask(tempTask));

    }

}
