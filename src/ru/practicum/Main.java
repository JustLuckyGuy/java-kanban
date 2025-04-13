package ru.practicum;

import ru.practicum.manager.TaskManager;
import ru.practicum.model.*;


public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        //Создал 2 Задачи, 1 Эпик с 2-мя подзадачами, 1 Эпик с 1 подзадачей
        Task task1 = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);
        Task task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями", StatusTask.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        Epic epic1 = new Epic("Очень большая и важная задача", StatusTask.NEW);
        Epic epic2 = new Epic("Менее большая задача", StatusTask.NEW);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        SubTask subTask1 = new SubTask("Разработать план", "Подумать над планом", StatusTask.NEW, epic1.getId());
        SubTask subTask2 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.DONE, epic1.getId());
        SubTask subTask3 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", StatusTask.NEW, epic2.getId());
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);

        System.out.println("Список задач:\n" + taskManager.getTasks());
        System.out.println("Список Подзадач:\n" + taskManager.getSubTasks());
        System.out.println("Список Эпиков:\n" + taskManager.getEpic());

        //Обновляю Задачи и Эпики и вывожу их на консоль
        task1 = new Task("Посмотреть фильм", "Взять с собой собаку", StatusTask.DONE);
        taskManager.updateTask(taskManager.getTaskById(1).getId(), task1);
        taskManager.updateEpic(epic1.getId(), "Не очень большая задача");
        subTask2 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.IN_PROGRESS, epic1.getId());
        taskManager.updateSubTask(taskManager.getSubTaskById(2).getId(), subTask2);

        System.out.println("Список задач:\n" + taskManager.getTasks());
        System.out.println("Список Подзадач:\n" + taskManager.getSubTasks());
        System.out.println("Список Эпиков:\n" + taskManager.getEpic());


        //Удаляю по одной Задаче и Эпику и вывожу на консоль
        taskManager.removeTaskById(task2.getId());
        taskManager.removeEpicById(epic1.getId());
        System.out.println("Список задач:\n" + taskManager.getTasks());
        System.out.println("Список Эпиков:\n" + taskManager.getEpic());
        System.out.println("Список Подзадач:\n" + taskManager.getSubTasks());

    }
}
