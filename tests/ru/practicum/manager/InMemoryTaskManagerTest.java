package ru.practicum.manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.*;


class InMemoryTaskManagerTest {
    public static TaskManager tasks;
    Task task1;
    Task task2;
    Epic epic1;
    Epic epic2;
    SubTask subTask1;
    SubTask subTask2;
    SubTask subTask3;


    @BeforeEach
    void before(){
        tasks = Managers.getDefault();
        task1 = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);
        task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями", StatusTask.NEW);
        tasks.createTask(task1);
        tasks.createTask(task2);
        epic1 = new Epic("Очень большая и важная задача", "Дедлайн до завтра", StatusTask.NEW);
        epic2 = new Epic("Менее большая задача", "Дедлайн сегодня", StatusTask.NEW);
        tasks.createEpic(epic1);
        tasks.createEpic(epic2);
        subTask1 = new SubTask("Разработать план", "Подумать над планом", StatusTask.NEW, epic1.getId());
        subTask2 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.DONE, epic1.getId());
        subTask3 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", StatusTask.NEW, epic2.getId());
        tasks.createSubTask(subTask1);
        tasks.createSubTask(subTask2);
        tasks.createSubTask(subTask2);
    }
    @Test
    void shouldReturnTrueWhenTasksIsCreated(){
        assertEquals(2, tasks.getTasks().size());
    }
    @Test
    void shouldReturnTrueWhenEpicIsCreated(){
        assertEquals(2, tasks.getEpic().size());
    }
    @Test
    void shouldReturnTrueWhenSubTaskIsCreated(){
        assertEquals(3, tasks.getSubTasks().size());
    }

    @Test
    void getTaskByID(){
        Task takenTask = tasks.getTaskById(1);
        assertEquals(takenTask, tasks.getTaskById(task1.getId()));
    }
    @Test
    void getEpicByID(){
        Epic takenEpic = tasks.getEpicById(3);
        assertEquals(takenEpic, tasks.getEpicById(epic1.getId()));
    }
    @Test
    void getSubTaskByID(){
        SubTask takenSubTusk = tasks.getSubTaskById(5);
        assertEquals(takenSubTusk, tasks.getSubTaskById(subTask1.getId()));
    }

    @Test
    void shouldReturnTrueWhenTaskIsUpdated(){
        Task savedTask = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);;
        task1.setNameTask("Новая задача");
        task1.setDescription("Исправить все ошибки");
        tasks.updateTask(task1);
        assertNotEquals(tasks.getTaskById(task1.getId()), savedTask);
    }



    @Test
    void shouldReturnTrueWhenTaskEqualsById() {
        Task sj = new Task("Посмотреть фильм", "Выбрать фильм с друзьями", StatusTask.NEW);
        sj.setId(task1.getId());
        tasks.updateTask(sj);
        assertEquals(tasks.getTaskById(sj.getId()), tasks.getTaskById(task1.getId()));
    }
    @Test
    void shouldReturnTrueWhenSubTaskEqualsById() {
        SubTask sj = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.DONE, epic1.getId());
        sj.setId(subTask1.getId());
        tasks.updateSubTask(sj);
        assertEquals(tasks.getSubTaskById(subTask1.getId()), tasks.getSubTaskById(sj.getId()));
    }
    @Test
    void shouldReturnTrueWhenEpicEqualsById() {
        Epic sj = new Epic("Менее большая задача", "Дедлайн сегодня", StatusTask.NEW);
        sj.setId(epic1.getId());
        tasks.updateEpic(sj);
        assertEquals(tasks.getEpicById(epic1.getId()), tasks.getEpicById(sj.getId()));
    }



    @Test
    void as(){
        Task task1 = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);
        tasks.createTask(task1);
        Task task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями", StatusTask.NEW);
        task2.setId(2);
        tasks.updateTask(task2);
        assertNotEquals(tasks.getTaskById(task1.getId()), tasks.getTaskById(task2.getId()));

        task2.setId(task1.getId());
        tasks.updateTask(task2);
        assertEquals(tasks.getTaskById(task1.getId()), tasks.getTaskById(task2.getId()));
    }



}