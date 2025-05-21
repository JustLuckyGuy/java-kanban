package ru.practicum.manager;

import ru.practicum.model.Epic;
import ru.practicum.model.SubTask;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.practicum.manager.Managers.getDefaultHistory;


public class InMemoryTaskManager implements TaskManager {
    private int counterID = 1;

    //Создал 3 структуры HashMap, которые будут хранить наши задачи и Эпики
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    private final HistoryManager historyManager = getDefaultHistory();


    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.Task
    @Override
    public void createTask(Task task) {
        task.setId(counterID);
        tasks.put(task.getId(), task);
        counterID++;
    }

    //Метод, который позволит взять задачу из HashMap
    @Override
    public Task getTaskById(int idTask) {
        historyManager.addHistory(tasks.get(idTask));
        return tasks.get(idTask);
    }


    //Метод, который будет обновлять нашу задачу, немного изменил предложенный вариант
    @Override
    public void updateTask(Task task) {

        final Task savedTask = tasks.get(task.getId());
        if (savedTask == null) {
            return;
        }
        tasks.put(task.getId(), task);

    }

    //Метод, которые удаляет все Задачи из структуры tasks
    @Override
    public void removeAllTasks() {
        for (Integer id : tasks.keySet()) {
            historyManager.removeHistoryById(id);
        }
        tasks.clear();
    }

    //Метод, который удаляет Задачу по его идентификатору
    @Override
    public void removeTaskById(int idTask) {
        if (!tasks.containsKey(idTask)) {
            return;
        }
        historyManager.removeHistoryById(idTask);
        tasks.remove(idTask);
    }

    //Метод, который выводит все Задачи
    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.SubTask
    @Override
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
    @Override
    public SubTask getSubTaskById(int idSubTask) {
        historyManager.addHistory(subTasks.get(idSubTask));
        return subTasks.get(idSubTask);
    }

    //Метод, который обновляет Подзадачу
    @Override
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
    @Override
    public void removeAllSubTasks() {
        for (Epic epic : epics.values()) {
            removeSubFromEpicHistory(epic.getId());
            epic.getSubTask().clear();
            epic.checkStatus();
        }
        subTasks.clear();
    }

    //Метод, который удаляет Подзадачу по его идентификатору
    @Override
    public void removeSubTaskById(int idSubTask) {
        if (!subTasks.containsKey(idSubTask)) {
            return;
        }
        historyManager.removeHistoryById(idSubTask);
        Epic epic = epics.get(subTasks.get(idSubTask).getIdEpic());
        epic.removeSubTask(subTasks.get(idSubTask));
        subTasks.remove(idSubTask);
        epic.checkStatus();
    }


    //Метод, который выводит все Подзадачи из структуры
    @Override
    public List<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.Epic
    @Override
    public void createEpic(Epic epic) {
        epic.setId(counterID);
        epics.put(epic.getId(), epic);
        counterID++;
    }

    //Метод, который возвращает Эпик по его идентификатору
    @Override
    public Epic getEpicById(int idEpic) {
        historyManager.addHistory(epics.get(idEpic));
        return epics.get(idEpic);
    }

    //Метод, который обновляет Эпик
    @Override
    public void updateEpic(Epic epic) {
        if (epics.get(epic.getId()) == null) {
            return;
        }
        final Epic savedEpic = epics.get(epic.getId());
        savedEpic.setNameTask(epic.getNameTask());
        savedEpic.setDescription(epic.getDescription());
        epics.put(savedEpic.getId(), savedEpic);
    }

    //Метод, который удаляет все Эпики из структуры
    @Override
    public void removeAllEpics() {
        for (Integer id : epics.keySet()) {
            removeSubFromEpicHistory(id);
            historyManager.removeHistoryById(id);
        }
        subTasks.clear();
        epics.clear();
    }

    //Метод, который удаляет Эпик по его идентификатору
    @Override
    public void removeEpicById(int idEpic) {
        if (!epics.containsKey(idEpic)) {
            return;
        }
        ArrayList<SubTask> removeSubTasks = epics.get(idEpic).getSubTask();
        for (SubTask subTask : removeSubTasks) {
            if (subTasks.containsValue(subTask)) {
                subTasks.remove(subTask.getId());
            }
        }
        removeSubFromEpicHistory(idEpic);
        historyManager.removeHistoryById(idEpic);
        epics.remove(idEpic);
    }

    //Метод, который выводит все Эпики
    @Override
    public List<Epic> getEpic() {
        return new ArrayList<>(epics.values());
    }

    //Метод, который возвращает все подзадачи из нужного Эпика
    @Override
    public List<SubTask> getAllSubInEpic(int idEpic) {
        if (epics.get(idEpic) == null) {
            return null;
        }
        Epic epic = epics.get(idEpic);
        return new ArrayList<>(epic.getSubTask());
    }

    @Override
    //Метод, который возвращает список истории
    public List<Task> showHistory() {
        return historyManager.getHistory();
    }

    //Метод, который удаляет SubTask эпиков из истории
    private void removeSubFromEpicHistory(int idEpic) {
        ArrayList<SubTask> subOfEpic = epics.get(idEpic).getSubTask();
        for (SubTask sub : subOfEpic) {
            historyManager.removeHistoryById(sub.getId());
        }
    }

}


