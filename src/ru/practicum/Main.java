package ru.practicum;


import ru.practicum.manager.Managers;
import ru.practicum.manager.TaskManager;
import ru.practicum.model.*;


public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Прогулка", "Взять с собой собаку");
        Task task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        Epic epic1 = new Epic("Очень большая и важная задача", "Дедлайн до завтра");
        Epic epic2 = new Epic("Менее большая задача", "Дедлайн сегодня");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        SubTask subTask1 = new SubTask("Разработать план", "Подумать над планом", epic1.getId());
        SubTask subTask2 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", epic1.getId());
        SubTask subTask3 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", epic2.getId());
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);

        taskManager.getTaskById(task1.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getSubTaskById(subTask1.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getEpicById(epic1.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getEpicById(epic2.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getSubTaskById(subTask3.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getTaskById(task1.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getSubTaskById(subTask1.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getTaskById(task1.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getEpicById(epic2.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getTaskById(task1.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getEpicById(epic1.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getSubTaskById(subTask1.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getTaskById(task1.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getEpicById(epic2.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getSubTaskById(subTask1.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getEpicById(epic1.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getTaskById(task1.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getEpicById(epic2.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getSubTaskById(subTask2.getId());
        System.out.println(taskManager.showHistory());
        taskManager.getSubTaskById(subTask3.getId());
        System.out.println(taskManager.showHistory());


        taskManager.removeTaskById(task1.getId());
        System.out.println(taskManager.showHistory());

        taskManager.removeSubTaskById(subTask2.getId());
        System.out.println(taskManager.showHistory());

        taskManager.removeEpicById(epic1.getId());
        System.out.println(taskManager.showHistory());

    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getEpic()) {
            System.out.println(epic);

        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubTasks()) {
            System.out.println(subtask);
        }

    }
}
