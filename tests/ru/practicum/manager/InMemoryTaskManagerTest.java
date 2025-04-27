package ru.practicum.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.StatusTask;
import ru.practicum.model.SubTask;
import ru.practicum.model.Task;

import static ru.practicum.manager.Managers.getDefault;

class InMemoryTaskManagerTest {
    public static final TaskManager tasks = getDefault();

    @BeforeEach
    public void before(){

        //Создал 2 Задачи, 1 Эпик с 2-мя подзадачами, 1 Эпик с 1 подзадачей
        Task task1 = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);
        Task task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями", StatusTask.NEW);
        tasks.createTask(task1);
        tasks.createTask(task2);
        Epic epic1 = new Epic("Очень большая и важная задача", "Дедлайн до завтра", StatusTask.NEW);
        Epic epic2 = new Epic("Менее большая задача", "Дедлайн сегодня", StatusTask.NEW);
        tasks.createEpic(epic1);
        tasks.createEpic(epic2);
        SubTask subTask1 = new SubTask("Разработать план", "Подумать над планом", StatusTask.NEW, epic1.getId());
        SubTask subTask2 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.DONE, epic1.getId());
        SubTask subTask3 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", StatusTask.NEW, epic2.getId());
        tasks.createSubTask(subTask1);
        tasks.createSubTask(subTask2);
        tasks.createSubTask(subTask3);
    }

    @Test
    void shouldReturnList() {
        tasks.getTaskById(1);
        tasks.getSubTaskById(2);
        tasks.getEpicById(1);

        Assertions.assertNotNull(tasks);
    }
}