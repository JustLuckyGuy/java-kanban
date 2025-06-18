package ru.practicum.manager;

import ru.practicum.model.Epic;
import ru.practicum.model.SubTask;
import ru.practicum.model.Task;

import java.util.List;
import java.util.TreeSet;

public interface TaskManager {
    //методы для Task
    void createTask(Task task);

    Task getTaskById(int idTask);

    void updateTask(Task task);

    void removeAllTasks();

    void removeTaskById(int idTask);

    List<Task> getTasks();

    //методы для Subtask
    void createSubTask(SubTask subTask);

    SubTask getSubTaskById(int idSubTask);

    void updateSubTask(SubTask subTask);

    void removeAllSubTasks();

    void removeSubTaskById(int idSubTask);

    List<SubTask> getSubTasks();

    //методы для Epic
    void createEpic(Epic epic);

    Epic getEpicById(int idEpic);

    void updateEpic(Epic epic);

    void removeAllEpics();

    void removeEpicById(int idEpic);

    List<Epic> getEpic();

    List<SubTask> getAllSubInEpic(int idEpic);

    List<Task> showHistory();

    TreeSet<Task> getPrioritizedTasks();

}
