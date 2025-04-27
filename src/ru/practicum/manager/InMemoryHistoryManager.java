package ru.practicum.manager;

import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    List<Task> history = new ArrayList<>(10);
    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

    //Метод для добавления Задач в список историй
    public <T extends  Task> void addHistory(T tasks){
        if(history.size() == 10){
            history.removeFirst();
        }
        history.add(tasks);
    }
}
