package ru.practicum.manager;

import ru.practicum.model.Epic;
import ru.practicum.model.SubTask;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class TaskManager {
    private static int counterIdForTask = 1;
    private static int counterIdForSubTask = 1;
    private static int counterIdForEpic = 1;

    //Создал 3 структуры HashMap, которые будут хранить наши задачи и Эпики
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    //Начал с методов для класса ru.practicum.model.Task
    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.Task
    public void createTask(Task task) {
        task.setId(counterIdForTask);
        tasks.put(task.getId(), task);
        counterIdForTask++;
    }

    //Метод, который позволит взять задачу из HashMap
    public Task getTaskById(int idTask) {
        return tasks.get(idTask);
    }

    //Метод, который будет обновлять нашу задачу, немного изменил предложенный вариант
    //так как в условиях задачи было указано, что в параметр передается не только новая версия, но и идентификатор.
    //Или же я не понял условия задачи. Нам все таки сначала нужно новую версию добавить в HashMap и мы заменяем старую версию на новую?
    //Или же мы сразу должны заменять старую версию на новую? Просто если в первом случае, то у нас будут проблемы с идентификаторами, потому что
    //генерация не будет останавливаться и получится так, что если пользователь добавит новую task она уже будет иметь хаотичный идентификатор.
    //А во втором случае, нам тогда необходимо ссылаться на идентификатор старой версии.
    //Еще в процессе исправлений ошибок задался вопросом, если в параметрах нужен лишь новый объект Task, то как вообще мы можем ссылаться по id, которого нет
    //Ведь id задается только при добавлении в HASHMAP, это получается нужна еще проверка на стадии добавления Task в HashMap,
    public void updateTask(int id, Task task) {
        final Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        task.setId(id);
        tasks.put(id, task);
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
        List<Task> result = new ArrayList<>();
        for (Integer i : tasks.keySet()) {
            result.add(tasks.get(i));
        }
        return result;
    }

    //Методы для ru.practicum.model.SubTask
    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.SubTask
    public void createSubTask(SubTask subTask) {
        if (epics.get(subTask.getIdEpic()) == null) {
            System.out.println(subTask.getIdEpic());
            return;
        }
        subTask.setId(counterIdForSubTask);
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getIdEpic());
        epic.addSubTask(subTask);
        epic.checkStatus();
        counterIdForSubTask++;
    }

    //Метод, который возвращает Подзадачу по его идентификатору.
    public SubTask getSubTaskById(int idSubTask) {
        return subTasks.get(idSubTask);
    }

    //Метод, который обновляет Подзадачу
    public void updateSubTask(int id, SubTask subTask) {
        if (epics.get(subTask.getIdEpic()) == null) {
            return;
        }
        final SubTask savedSubTask = subTasks.get(id);
        if (savedSubTask == null) {
            return;
        }
        subTask.setId(id);
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getIdEpic());
        ArrayList<SubTask> newSubs = epic.getSubTask();
        if (newSubs.contains(subTask)) {
            newSubs.remove(subTask);
            newSubs.add(subTask);
        }
        epic.setSubTasks(newSubs);
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
        ArrayList<SubTask> removeSub = epic.getSubTask();
        removeSub.remove(subTasks.get(idSubTask));
        subTasks.remove(idSubTask);
        epic.checkStatus();
    }


    //Метод, который выводит все Подзадачи из структуры
    public List<SubTask> getSubTasks() {
        List<SubTask> result = new ArrayList<>();
        for (Integer i : subTasks.keySet()) {
            result.add(subTasks.get(i));
        }
        return result;
    }


    //Методы для ru.practicum.model.Epic
    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.Epic
    public void createEpic(Epic epic) {
        epic.setId(counterIdForEpic);
        epics.put(epic.getId(), epic);
        counterIdForEpic++;
    }

    //Метод, который возвращает Эпик по его идентификатору
    public Epic getEpicById(int idEpic) {
        return epics.get(idEpic);
    }

    //Метод, который обновляет Эпик
    public void updateEpic(int id, String newNameForEpic) {
        if (epics.get(id) == null) {
            return;
        }
        Epic epic = epics.get(id);
        epic.setNameTask(newNameForEpic);
        epics.put(epic.getId(), epic);
    }

    //Метод, который удаляет все Эпики из структуры
    public void removeAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    //Метод, который удаляет Эпик по его идентификатору
    public void removeEpicById(int idEpic) {

        Iterator<Integer> iterator = subTasks.keySet().iterator();
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            SubTask subTask = subTasks.get(key);
            if (subTask.getIdEpic() == idEpic) {
                iterator.remove();
            }
        }
        epics.remove(idEpic);
    }

    //Метод, который выводит все Эпики
    public List<Epic> getEpic() {
        List<Epic> result = new ArrayList<>();
        for (Integer i : epics.keySet()) {
            result.add(epics.get(i));
        }
        return result;
    }

    public List<SubTask> getAllSubInEpic(int idEpic) {
        if (epics.get(idEpic) == null) {
            return null;
        }
        Epic epic = epics.get(idEpic);
        return new ArrayList<>(epic.getSubTask());
    }
}


