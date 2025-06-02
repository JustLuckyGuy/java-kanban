package ru.practicum;

import ru.practicum.manager.FileBackedTaskManager;
import ru.practicum.model.*;


public class Main {

    public static void main(String[] args) {
        FileBackedTaskManager taskManager = new FileBackedTaskManager();

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
        SubTask subTask3 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", epic1.getId());
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);

        task1.setNameTask("Купить воды");
        taskManager.updateTask(task1);

        SubTask task = (SubTask) taskManager.fromStringToTask("5,SUBTASK,ывфв a plan,NEW,Think about a plan,3");
        System.out.println(task);


    }

}

