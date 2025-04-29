package ru.practicum.manager;

public class Managers {

    //Метод, который возвращает новый объект InMemoryTaskManager
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    //Метод, который возвращает новый объект InMemoryHistoryManager
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
