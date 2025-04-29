package ru.practicum.manager;

import ru.practicum.model.Epic;
import ru.practicum.model.SubTask;
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

        Task taskCopy;

        if (tasks instanceof SubTask) {
            SubTask original = (SubTask) tasks;
            SubTask subTaskCopy = new SubTask(original.getNameTask(), original.getDescription(), original.getStatusTask(), original.getIdEpic());
            subTaskCopy.setId(original.getId());
            taskCopy = subTaskCopy;

        } else if (tasks instanceof Epic) {
            Epic original = (Epic) tasks;
            Epic epicCopy = new Epic(original.getNameTask(), original.getDescription(), original.getStatusTask());
            epicCopy.setId(original.getId());


            for (SubTask sub : original.getSubTask()) {
                SubTask subTaskCopy = new SubTask(sub.getNameTask(), sub.getDescription(), sub.getStatusTask(), sub.getIdEpic());
                subTaskCopy.setId(sub.getId());
                epicCopy.addSubTask(subTaskCopy);
            }

            taskCopy = epicCopy;

        } else {
            taskCopy = new Task(tasks.getNameTask(), tasks.getDescription(), tasks.getStatusTask());
            taskCopy.setId(tasks.getId());
        }

        history.add(taskCopy);

    }
}
