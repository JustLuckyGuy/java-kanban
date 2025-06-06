package ru.practicum.manager;

import ru.practicum.model.Task;

import java.util.List;

public interface HistoryManager {
    <T extends Task> void addHistory(T tasks);

    void removeHistoryById(int id);

    List<Task> getHistory();
}
