package ru.practicum.manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.practicum.manager.Managers.getDefault;

public class ManagerTest {
    @Test
    void shouldReturnTrueWhenNeededClassIsCreated() {
        TaskManager taskManager = getDefault();
        assertEquals(InMemoryTaskManager.class, taskManager.getClass());

        HistoryManager historyManager = Managers.getDefaultHistory();
        assertEquals(InMemoryHistoryManager.class, historyManager.getClass());
    }
}
