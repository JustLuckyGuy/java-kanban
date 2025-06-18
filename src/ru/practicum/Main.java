package ru.practicum;

import ru.practicum.manager.*;
import ru.practicum.model.*;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static ru.practicum.manager.FileBackedTaskManager.loadFromFile;


public class Main {

    public static void main(String[] args) {

        FileBackedTaskManager taskManager = new FileBackedTaskManager(new File("YandexPracticum7.csv"));

        Task task1 = new Task("Прогулка", "Взять с собой собаку");
        task1.setStartTime(LocalDateTime.now());
        task1.setDuration(Duration.ofMinutes(100));
        Task task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями");
        task2.setStartTime(LocalDateTime.of(2025, Month.MAY, 18, 20, 1));
        task2.setDuration(Duration.ZERO);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        Epic epic1 = new Epic("Очень большая и важная задача", "Дедлайн до завтра");
        Epic epic2 = new Epic("Менее большая задача", "Дедлайн сегодня");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        SubTask subTask1 = new SubTask("Разработать план", "Подумать над планом", epic1.getId());
        subTask1.setStartTime(LocalDateTime.of(2025, Month.JULY, 20, 20, 0));
        subTask1.setDuration(Duration.ofMinutes(3000));
        SubTask subTask2 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", epic1.getId());
        subTask2.setStartTime(LocalDateTime.of(2025, Month.MAY, 10, 10, 15));
        subTask2.setDuration(Duration.ofDays(2));
        SubTask subTask3 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", epic2.getId());
        subTask3.setStartTime(LocalDateTime.of(2025, Month.JULY, 3, 20, 12));
        subTask3.setDuration(Duration.ofMinutes(20));
        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);
        taskManager.createSubTask(subTask3);

        System.out.println(taskManager.getPrioritizedTasks());
        SubTask subtaskUpdate = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", epic2.getId());
        subtaskUpdate.setId(subTask2.getId());
        subtaskUpdate.setStartTime(LocalDateTime.of(2025, Month.AUGUST, 10, 15, 20));
        taskManager.updateSubTask(subtaskUpdate);
        System.out.println(taskManager.getPrioritizedTasks());


        FileBackedTaskManager restored = loadFromFile(new File("YandexPracticum7.csv"));
        System.out.println(restored.getPrioritizedTasks());

    }

}


