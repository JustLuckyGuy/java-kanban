package ru.practicum.manager;

import ru.practicum.model.Epic;
import ru.practicum.model.SubTask;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TaskManager {
    private int counterID = 1;

    //Создал 3 структуры HashMap, которые будут хранить наши задачи и Эпики
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.Task
    public void createTask(Task task) {
        task.setId(counterID);
        tasks.put(task.getId(), task);
        counterID++;
    }

    //Метод, который позволит взять задачу из HashMap
    public Task getTaskById(int idTask) {
        return tasks.get(idTask);
    }


    //Метод, который будет обновлять нашу задачу, немного изменил предложенный вариант
    public void updateTask(Task task) {
        final Task savedTask = tasks.get(task.getId());
        if (savedTask == null) {
            return;
        }
        tasks.put(task.getId(), task);
    }

    //Метод, которые удаляет все Задачи из структуры tasks
    public void removeAllTasks() {
        tasks.clear();
    }

    //Метод, который удаляет Задачу по его идентификатору
    public void removeTaskById(int idTask) {
        tasks.remove(idTask);
    }

    //Метод, который выводит все Задачи
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.SubTask
    public void createSubTask(SubTask subTask) {
        if (epics.get(subTask.getIdEpic()) == null) {
            System.out.println(subTask.getIdEpic());
            return;
        }
        subTask.setId(counterID);
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getIdEpic());
        epic.addSubTask(subTask);
        epic.checkStatus();
        counterID++;
    }

    //Метод, который возвращает Подзадачу по его идентификатору.
    public SubTask getSubTaskById(int idSubTask) {
        return subTasks.get(idSubTask);
    }

    //Метод, который обновляет Подзадачу
    public void updateSubTask(SubTask subTask) {
        if (epics.get(subTask.getIdEpic()) == null) {
            return;
        }
        final SubTask savedSubTask = subTasks.get(subTask.getId());
        if (savedSubTask == null) {
            return;
        }
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getIdEpic());
        epic.updateSubTask(subTask);
        epic.checkStatus();
    }

    //Метод, который удаляет все Подзадачи из структуры subTasks
    public void removeAllSubTasks() {
        for (Epic epic : epics.values()){
            epic.getSubTask().clear();
            epic.checkStatus();
        }
        subTasks.clear();
    }

    //Метод, который удаляет Подзадачу по его идентификатору
    public void removeSubTaskById(int idSubTask) {
        if (!subTasks.containsKey(idSubTask)) {
            return;
        }
        Epic epic = epics.get(subTasks.get(idSubTask).getIdEpic());
        epic.removeSubTask(subTasks.get(idSubTask));
        subTasks.remove(idSubTask);
        epic.checkStatus();
    }


    //Метод, который выводит все Подзадачи из структуры
    public List<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.Epic
    public void createEpic(Epic epic) {
        epic.setId(counterID);
        epics.put(epic.getId(), epic);
        counterID++;
    }

    //Метод, который возвращает Эпик по его идентификатору
    public Epic getEpicById(int idEpic) {
        return epics.get(idEpic);
    }

    //Метод, который обновляет Эпик
    public void updateEpic(Epic epic) {
        if (epics.get(epic.getId()) == null) {
            return;
        }
        epics.put(epic.getId(), epic);
    }

    //Метод, который удаляет все Эпики из структуры
    public void removeAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    //Метод, который удаляет Эпик по его идентификатору
    public void removeEpicById(int idEpic) {
        ArrayList<SubTask> removeSubTasks = epics.get(idEpic).getSubTask();
        for (SubTask subTask : removeSubTasks) {
            if (subTasks.containsValue(subTask)) {
                subTasks.remove(subTask.getId());
            }
        }
        epics.remove(idEpic);
    }

    //Метод, который выводит все Эпики
    public List<Epic> getEpic() {
        return new ArrayList<>(epics.values());
    }

    //Метод, который возвращает все подзадачи из нужного Эпика
    public List<SubTask> getAllSubInEpic(int idEpic) {
        if (epics.get(idEpic) == null) {
            return null;
        }
        Epic epic = epics.get(idEpic);
        return new ArrayList<>(epic.getSubTask());
    }
}


