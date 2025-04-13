package ru.practicum.manager;

import ru.practicum.model.Epic;
import ru.practicum.model.SubTask;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TaskManager {
    private static int counterID = 1;

    //Создал 3 структуры HashMap, которые будут хранить наши задачи и Эпики
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    //Начал с методов для класса ru.practicum.model.Task
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

    //Методы для ru.practicum.model.SubTask
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
        //Добавил в класс Epic метод, который будет проверять, есть ли похожие подзадачи или нет
        // и в случае, если таковых нет, то добавит в список подзадач, новую подзадачу
        epic.checkSubTask(subTask);
        epic.checkStatus();
    }

    //Метод, который удаляет все Подзадачи из структуры subTasks
    public void removeAllSubTasks() {
        for (Integer idEpic : epics.keySet()) {
            epics.get(idEpic).getSubTask().clear();
            epics.get(idEpic).checkStatus();
        }
        subTasks.clear();
    }

    //Метод, который удаляет Подзадачу по его идентификатору
    public void removeSubTaskById(int idSubTask) {
        if (subTasks.get(idSubTask) == null) {
            return;
        }
        Epic epic = epics.get(subTasks.get(idSubTask).getIdEpic());
        //Добавил в класс Epic метод removeSubTask, который будет удалять подзадачу в нашем эпике
        epic.removeSubTask(subTasks.get(idSubTask));
        subTasks.remove(idSubTask);
        epic.checkStatus();
    }


    //Метод, который выводит все Подзадачи из структуры
    public List<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }


    //Методы для ru.practicum.model.Epic
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
    public void updateEpic(Epic epic, String newNameForEpic, String newDescription) {
        if (epics.get(epic.getId()) == null) {
            return;
        }
        epic.setNameTask(newNameForEpic);
        //Добавил сеттер, который будет менять описание Эпика
        epic.setDescription(newDescription);
        epics.put(epic.getId(), epic);
    }

    //Метод, который удаляет все Эпики из структуры
    public void removeAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    //Метод, который удаляет Эпик по его идентификатору
    public void removeEpicById(int idEpic) {
        //Изменил реализацию метода, теперь имея список подзадач выбранного эпика, находит в subTask
        //похожие и удаляет их
        ArrayList<SubTask> removeSubTasks = epics.get(idEpic).getSubTask();
        for (SubTask subTask : removeSubTasks) {
            if (subTasks.containsValue(subTask)) {
                subTasks.remove(subTask.getId());
            }
        }
        // и затем просто удаляет эпик
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


