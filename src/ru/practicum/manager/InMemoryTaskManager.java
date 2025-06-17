package ru.practicum.manager;

import ru.practicum.exeptions.ManagerIsIntersectException;
import ru.practicum.model.Epic;
import ru.practicum.model.SubTask;
import ru.practicum.model.Task;


import java.util.*;


import static ru.practicum.manager.Managers.getDefaultHistory;


public class InMemoryTaskManager implements TaskManager {
    protected int counterID = 1;

    //Создал 3 структуры HashMap, которые будут хранить наши задачи и Эпики
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();

    private final HistoryManager historyManager = getDefaultHistory();


    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.Task
    @Override
    public void createTask(Task task) {
        if (isTaskIntersectInManager(task)) {
            throw new ManagerIsIntersectException("Задача пересекается по времени с другой задачей");
        }
        task.setId(counterID);
        tasks.put(task.getId(), task);
        counterID++;
    }

    //Метод, который позволит взять задачу из HashMap
    @Override
    public Task getTaskById(int idTask) {
        Optional.ofNullable(tasks.get(idTask))
                .ifPresent(historyManager::addHistory);

        return tasks.get(idTask);
    }


    //Метод, который будет обновлять нашу задачу, немного изменил предложенный вариант
    @Override
    public void updateTask(Task task) {
        if (isTaskIntersectInManager(task) && !tasks.containsKey(task.getId())) {
            throw new ManagerIsIntersectException("Задача пересекается по времени с другой задачей");
        }

        Optional.ofNullable(tasks.get(task.getId()))
                .ifPresent(savedTask -> tasks.put(task.getId(), task));
    }

    //Метод, которые удаляет все Задачи из структуры tasks
    @Override
    public void removeAllTasks() {
        tasks.keySet().forEach(historyManager::removeHistoryById);
        tasks.clear();
    }

    //Метод, который удаляет Задачу по его идентификатору
    @Override
    public void removeTaskById(int idTask) {

        Optional.ofNullable(tasks.get(idTask))
                .ifPresent(task -> {
                    historyManager.removeHistoryById(idTask);
                    tasks.remove(idTask);
                });

    }

    //Метод, который выводит все Задачи
    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.SubTask
    @Override
    public void createSubTask(SubTask subTask) {
        if (isTaskIntersectInManager(subTask)) {
            throw new ManagerIsIntersectException("Задача пересекается по времени с другой задачей");
        }

        Optional.ofNullable(epics.get(subTask.getIdEpic()))
                .ifPresent(savedSubTask -> {
                    subTask.setId(counterID);
                    subTasks.put(subTask.getId(), subTask);
                });

        Epic epic = epics.get(subTask.getIdEpic());
        epic.addSubTask(subTask);
        epic.checkStatus();
        epic.updateDuration();
        counterID++;
    }

    //Метод, который возвращает Подзадачу по его идентификатору.
    @Override
    public SubTask getSubTaskById(int idSubTask) {
        Optional.ofNullable(subTasks.get(idSubTask))
                .ifPresent(historyManager::addHistory);
        return subTasks.get(idSubTask);
    }

    //Метод, который обновляет Подзадачу
    @Override
    public void updateSubTask(SubTask subTask) {
        if (isTaskIntersectInManager(subTask) && !subTasks.containsKey(subTask.getId())) {
            throw new ManagerIsIntersectException("Задача пересекается по времени с другой задачей");
        }

        Optional.ofNullable(subTasks.get(subTask.getId()))
                .ifPresent(savedSubTask -> subTasks.put(subTask.getId(), subTask));

        Optional.ofNullable(epics.get(subTask.getIdEpic()))
                .ifPresent(epic -> {
                    Epic epic1 = epics.get(subTask.getIdEpic());
                    epic1.updateSubTask(subTask);
                    epic1.checkStatus();
                    epic1.updateDuration();
                });

    }

    //Метод, который удаляет все Подзадачи из структуры subTasks
    @Override
    public void removeAllSubTasks() {
        epics.values().forEach(epic -> {
            removeSubFromEpicHistory(epic.getId());
            epic.getSubTask().clear();
            epic.checkStatus();
        });
        subTasks.clear();
    }

    //Метод, который удаляет Подзадачу по его идентификатору
    @Override
    public void removeSubTaskById(int idSubTask) {

        SubTask subTask = subTasks.get(idSubTask);
        if (subTask != null) historyManager.removeHistoryById(idSubTask);
        Epic epic = epics.get(subTasks.get(idSubTask).getIdEpic());
        epic.removeSubTask(subTasks.get(idSubTask));
        subTasks.remove(idSubTask);
        epic.checkStatus();
        epic.updateDuration();
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
        Optional.ofNullable(epics.get(idEpic))
                .ifPresent(historyManager::addHistory);
        return epics.get(idEpic);
    }

    //Метод, который обновляет Эпик
    @Override
    public void updateEpic(Epic epic) {
        Optional.ofNullable(epics.get(epic.getId()))
                .ifPresent(savedepic -> {
                    savedepic.setNameTask(epic.getNameTask());
                    savedepic.setDescription(epic.getDescription());
                    epics.put(savedepic.getId(), savedepic);
                });
    }

    //Метод, который удаляет все Эпики из структуры
    @Override
    public void removeAllEpics() {
        epics.keySet().forEach(id -> {
            removeSubFromEpicHistory(id);
            historyManager.removeHistoryById(id);
        });
        subTasks.clear();
        epics.clear();
    }

    //Метод, который удаляет Эпик по его идентификатору
    @Override
    public void removeEpicById(int idEpic) {

        Optional.ofNullable(epics.get(idEpic))
                .ifPresent(epic -> {
                    epic.getSubTask().stream()
                            .map(SubTask::getId)
                            .forEach(subTasks::remove);

                    removeSubFromEpicHistory(idEpic);
                    historyManager.removeHistoryById(idEpic);
                    epics.remove(idEpic);
                });

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

        return new ArrayList<>(epics.get(idEpic).getSubTask());
    }

    @Override
    //Метод, который возвращает список истории
    public List<Task> showHistory() {
        return historyManager.getHistory();
    }

    //Метод, который удаляет SubTask эпиков из истории
    protected void removeSubFromEpicHistory(int idEpic) {
        epics.get(idEpic).getSubTask().stream()
                .map(SubTask::getId)
                .forEach(historyManager::removeHistoryById);
    }

    public TreeSet<Task> getPrioritizedTasks() {
        TreeSet<Task> priorityTaskOfData = new TreeSet<>(Comparator.comparing(Task::getStartTime).thenComparingInt(Task::getId));

        tasks.values().stream()
                .map(task -> task.getStartTime() != null ? task : null)
                .filter(Objects::nonNull)
                .forEach(priorityTaskOfData::add);
        subTasks.values().stream()
                .map(subTask -> subTask.getStartTime() != null ? subTask : null)
                .filter(Objects::nonNull)
                .forEach(priorityTaskOfData::add);

        return priorityTaskOfData;
    }

    protected boolean isTaskIntersect(Task addedTask, Task comparableTask) {
        return addedTask.getEndTime().isAfter(comparableTask.getStartTime()) && addedTask.getStartTime().isBefore(comparableTask.getEndTime());
    }

    protected boolean isTaskIntersectInManager(Task addedTask) {
        return getPrioritizedTasks().stream()
                .anyMatch(task -> isTaskIntersect(addedTask, task));
    }

}


