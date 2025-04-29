package ru.practicum;

import ru.practicum.manager.InMemoryTaskManager;
import ru.practicum.manager.Managers;
import ru.practicum.manager.TaskManager;
import ru.practicum.model.*;

import java.util.ArrayList;

import static ru.practicum.manager.Managers.getDefault;


public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        //Создал 2 Задачи, 1 Эпик с 2-мя подзадачами, 1 Эпик с 1 подзадачей
        Task task1 = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);
        Task task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями", StatusTask.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        Epic epic1 = new Epic("Очень большая и важная задача", "Дедлайн до завтра", StatusTask.NEW);
        Epic epic2 = new Epic("Менее большая задача", "Дедлайн сегодня", StatusTask.NEW);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        SubTask subTask1 = new SubTask("Разработать план", "Подумать над планом", StatusTask.NEW, epic1.getId());
        SubTask subTask2 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.NEW, epic1.getId());
        SubTask subTask3 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", StatusTask.NEW, epic2.getId());
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);

        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getEpicById(3);
        task1.setDescription("Новое описание");
        taskManager.updateTask(task1);
        subTask2.setNameTask("Новывыфвфы");
        taskManager.updateSubTask(subTask2);
        taskManager.getSubTaskById(6);
        taskManager.getEpicById(3);
        taskManager.getTaskById(1);
        System.out.println(taskManager.showHistory());

        printAllTasks(taskManager);
        subTask1.setStatusTask(StatusTask.DONE);
        taskManager.updateSubTask(subTask1);

        printAllTasks(taskManager);
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
