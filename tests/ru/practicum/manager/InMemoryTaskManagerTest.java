package ru.practicum.manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.*;

import java.util.ArrayList;


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
    void before() {
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
        subTask2 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.NEW, epic1.getId());
        subTask3 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", StatusTask.NEW, epic2.getId());
        tasks.createSubTask(subTask1);
        tasks.createSubTask(subTask2);
        tasks.createSubTask(subTask2);
    }

    @Test
    void shouldReturnTrueWhenTasksIsCreated() {
        assertEquals(2, tasks.getTasks().size());
    }

    @Test
    void shouldReturnTrueWhenEpicIsCreated() {
        assertEquals(2, tasks.getEpic().size());
    }

    @Test
    void shouldReturnTrueWhenSubTaskIsCreated() {
        assertEquals(3, tasks.getSubTasks().size());
    }

    @Test
    void getTaskByID() {
        Task takenTask = tasks.getTaskById(1);
        assertEquals(takenTask, tasks.getTaskById(task1.getId()));
    }

    @Test
    void getEpicByID() {
        Epic takenEpic = tasks.getEpicById(3);
        assertEquals(takenEpic, tasks.getEpicById(epic1.getId()));
    }

    @Test
    void getSubTaskByID() {
        SubTask takenSubTusk = tasks.getSubTaskById(5);
        assertEquals(takenSubTusk, tasks.getSubTaskById(subTask1.getId()));
    }

    @Test
    void shouldReturnTrueWhenTaskIsUpdated() {
        Task olddTask = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);
        ;
        task1.setNameTask("Новая задача");
        task1.setDescription("Исправить все ошибки");
        tasks.updateTask(task1);
        assertNotEquals(tasks.getTaskById(task1.getId()), olddTask);
    }

    @Test
    void shouldReturnTrueWhenEpicIsUpdated() {
        Epic oldEpic = new Epic("Очень большая и важная задача", "Дедлайн до завтра", StatusTask.NEW);
        epic1.setNameTask("Новая задача");
        epic1.setDescription("Исправить все ошибки");
        tasks.updateEpic(epic1);
        assertNotEquals(tasks.getEpicById(epic1.getId()), oldEpic);
    }

    @Test
    void shouldReturnTrueWhenSubTaskIsUpdated() {
        SubTask oldEpic = new SubTask("Разработать план", "Подумать над планом", StatusTask.NEW, epic1.getId());
        subTask1.setNameTask("Новая задача");
        subTask1.setDescription("Исправить все ошибки");
        tasks.updateSubTask(subTask1);
        assertNotEquals(tasks.getSubTaskById(subTask1.getId()), oldEpic);
    }

    @Test
    void shouldReturnINPROGRSSStatusWhenSubtaskIsChangedStatus() {
        subTask1.setStatusTask(StatusTask.DONE);
        tasks.updateSubTask(subTask1);
        assertEquals(StatusTask.IN_PROGRESS, tasks.getEpicById(epic1.getId()).getStatusTask());
    }

    @Test
    void shouldReturnDONEStatusWhenAllSubtaskIsDONE() {
        subTask1.setStatusTask(StatusTask.DONE);
        subTask2.setStatusTask(StatusTask.DONE);
        tasks.updateSubTask(subTask1);
        tasks.updateSubTask(subTask2);
        assertEquals(StatusTask.DONE, tasks.getEpicById(epic1.getId()).getStatusTask());
    }

    @Test
    void shouldReturnTrueWhenTaskIsDeletedById() {
        tasks.removeTaskById(1);
        assertEquals(1, tasks.getTasks().size());
    }

    @Test
    void shouldReturnTrueWhenSubTaskIsDeletedById() {
        tasks.removeSubTaskById(5);
        assertEquals(2, tasks.getSubTasks().size());
    }

    @Test
    void shouldReturnTrueWhenEpicIsDeletedById() {
        tasks.removeEpicById(3);
        assertEquals(1, tasks.getEpic().size());
        assertEquals(1, tasks.getSubTasks().size());
    }

    @Test
    void shouldReturnTrueWhenAllTasksIsDeleted() {
        tasks.removeAllTasks();
        assertEquals(0, tasks.getTasks().size());
    }

    @Test
    void shouldReturnTrueWhenAllSubTasksIsDeleted() {
        tasks.removeAllSubTasks();
        assertEquals(0, tasks.getSubTasks().size());
    }

    @Test
    void shouldReturnTrueWhenAllEpicsIsDeleted() {
        tasks.removeAllEpics();
        assertEquals(0, tasks.getEpic().size());
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
    void shouldNonConflictingValuesById() {
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

    @Test
    void shouldReturnTrueWhenNeededClassIsCreated(){
        TaskManager taskManager = Managers.getDefault();
        assertEquals(InMemoryTaskManager.class, taskManager.getClass());

        HistoryManager historyManager = Managers.getDefaultHistory();
        assertEquals(InMemoryHistoryManager.class, historyManager.getClass());
    }

    @Test
    void checkHistory(){
        tasks.getTaskById(1);
        tasks.getTaskById(2);
        tasks.getEpicById(3);
        task1.setDescription("Новое описание");
        tasks.updateTask(task1);
        tasks.getTaskById(1);
        tasks.showHistory();
        assertNotEquals(tasks.showHistory().getFirst(), task1);
    }



}