package ru.practicum;

import ru.practicum.manager.*;
import ru.practicum.model.*;

import java.io.File;

import static ru.practicum.manager.FileBackedTaskManager.loadFromFile;


public class Main {

    public static void main(String[] args) {

        FileBackedTaskManager taskManager = new FileBackedTaskManager(new File("YandexPracticum7.csv"));

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

        FileBackedTaskManager restored = loadFromFile(new File("YandexPracticum7.csv"));
        System.out.println(restored.getTasks());


    }

}


