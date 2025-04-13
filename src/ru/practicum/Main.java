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
        Epic epic1 = new Epic("Очень большая и важная задача", "Дедлайн до завтра", StatusTask.NEW);
        Epic epic2 = new Epic("Менее большая задача", "Дедлайн сегодня", StatusTask.NEW);
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
        task1.setNameTask("Сходить на пробежку");
        task1.setStatusTask(StatusTask.IN_PROGRESS);
        task2.setDescription("Узнать все ли пойдут смотреть фильм");
        taskManager.updateTask(task1);
        taskManager.updateTask(task2);
        subTask1.setNameTask("Узнать у эксперта, как составлять план");
        subTask2.setStatusTask(StatusTask.DONE);
        subTask3.setNameTask("Реализовать план");
        taskManager.updateSubTask(subTask1);
        taskManager.updateSubTask(subTask2);
        taskManager.updateSubTask(subTask3);
        taskManager.updateEpic(epic1, "Супер-пупер прям мега большая задача", "Ее нужно срочно решить!!!");
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
