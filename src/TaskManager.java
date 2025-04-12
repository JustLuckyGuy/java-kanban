import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {
    //Создал 3 структуры HashMap, которые будут хранить наши задачи и Эпики
    private static final HashMap<Integer, Task> taskManager = new HashMap<>();
    private static final HashMap<Integer, SubTask> subTaskManager = new HashMap<>();
    private static final HashMap<Integer, Epic> epicManager = new HashMap<>();

    //Начал с методов для класса Task
    //Метод, который добавляет в структуру HashMap, новый объект класса Task
    public static void createTask(Task task) {
        taskManager.put(task.getId(), task);
    }

    //Метод, который позволит взять задачу из HashMap
    public static Task getTaskById(int idTask) {
        return taskManager.get(idTask);
    }

    //Метод, который будет обновлять нашу задачу
    public static void updateTask(Task task, String description, StatusTask statusTask) {
        ArrayList<String> newDescription = task.getDescription();
        if (newDescription != null) {
            newDescription.add(description);
        } else {
            newDescription = new ArrayList<>();
            newDescription.add(description);
        }
        task.setDescription(newDescription);
        task.setStatusTask(statusTask);
        taskManager.put(task.getId(), task);
    }

    public static void updateTask(Task task, StatusTask statusTask) {
        task.setStatusTask(statusTask);
        taskManager.put(task.getId(), task);
    }

    //Метод, которые удаляет все Задачи из структуры taskManager
    public static void removeAllTasks() {
        taskManager.clear();
    }

    //Метод, который удаляет Задачу по его идентификатору
    public static void removeTaskById(int idTask) {
        taskManager.remove(idTask);
    }

    //Метод, который выводит все Задачи
    public static StringBuilder getTasks() {
        StringBuilder result = new StringBuilder();
        for (Integer i : taskManager.keySet()) {
            result.append(taskManager.get(i));
        }
        return result;
    }

    //Методы для SubTask
    //Метод, который добавляет в структуру HashMap, новый объект класса SubTask
    public static void createSubTask(SubTask subTask) {
        subTaskManager.put(subTask.getId(), subTask);
        Epic epic = epicManager.get(subTask.getIdEpic());
        if (epic != null) {
            epic.addSubTask(subTask);
            epic.checkStatus();
        }
    }

    //Метод, который возвращает Подзадачу по его идентификатору.
    public static SubTask getSubTaskById(int idSubTask) {
        return subTaskManager.get(idSubTask);
    }

    //Метод, который обновляет Подзадачу
    public static void updateSubTask(SubTask subTask, StatusTask statusTask) {
        subTask.setStatusTask(statusTask);
        subTaskManager.put(subTask.getId(), subTask);
        Epic epic = epicManager.get(subTask.getIdEpic());
        if (epic != null) {
            epic.checkStatus();
        }
    }

    //Метод, который удаляет все Подзадачи из структуры subTaskManager
    public static void removeAllSubTasks() {
        subTaskManager.clear();
    }

    //Метод, который удаляет Подзадачу по его идентификатору
    public static void removeSubTaskById(int idSubTask) {
        subTaskManager.remove(idSubTask);
    }


    //Метод, который выводит все Подзадачи из структуры
    public static StringBuilder getSubTasks() {
        StringBuilder result = new StringBuilder();
        for (Integer i : subTaskManager.keySet()) {
            result.append("\nПодзадача №").append(i).append(subTaskManager.get(i));
        }
        return result;
    }


    //Методы для Epic
    //Метод, который добавляет в структуру HashMap, новый объект класса Epic
    public static void createEpic(Epic epic) {
        epicManager.put(epic.getId(), epic);
    }

    //Метод, который возвращает Эпик по его идентификатору
    public static Epic getEpicById(int idEpic) {
        return epicManager.get(idEpic);
    }

    //Метод, который обновляет Эпик
    public static void updateEpic(Epic oldEpic, Epic epic) {
        epicManager.put(oldEpic.getId(), epic);
        epic.checkStatus();
    }

    //Метод, который удаляет все Эпики из структуры
    public static void removeAllEpics() {
        epicManager.clear();
    }

    //Метод, который удаляет Эпик по его идентификатору
    public static void removeEpicById(int idEpic) {
        epicManager.remove(idEpic);
    }

    //Метод, который выводит все Эпики
    public static StringBuilder getEpic() {
        StringBuilder result = new StringBuilder();
        for (Integer i : epicManager.keySet()) {
            result.append("Эпик № ").append(i).append("\n  ").append(epicManager.get(i));
        }
        return result;
    }

    public static String getAllSubInEpic(int idEpic) {
        StringBuilder result = new StringBuilder();
        if (epicManager.get(idEpic) == null) {
            return result.toString();
        }
        Epic epic = epicManager.get(idEpic);
        for (SubTask s : epic.getSubTask()) {
            result.append(s);
        }
        return result.toString();
    }
}


