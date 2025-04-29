package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.StatusTask;
import ru.practicum.model.SubTask;
import ru.practicum.model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static ru.practicum.manager.Managers.getDefault;

public class EpicTest {
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
        tasks = getDefault();
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
    void shouldReturnTrueWhenEpicIsCreated() {
        assertEquals(2, tasks.getEpic().size());
    }

    @Test
    void getEpicByID() {
        Epic takenEpic = tasks.getEpicById(3);
        assertEquals(takenEpic, tasks.getEpicById(epic1.getId()));
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
    void shouldReturnTrueWhenEpicIsDeletedById() {
        tasks.removeEpicById(3);
        assertEquals(1, tasks.getEpic().size());
        assertEquals(1, tasks.getSubTasks().size());
    }

    @Test
    void shouldReturnTrueWhenAllEpicsIsDeleted() {
        tasks.removeAllEpics();
        assertEquals(0, tasks.getEpic().size());
    }

    @Test
    void shouldReturnTrueWhenEpicEqualsById() {
        Epic sj = new Epic("Менее большая задача", "Дедлайн сегодня", StatusTask.NEW);
        sj.setId(epic1.getId());
        tasks.updateEpic(sj);
        assertEquals(tasks.getEpicById(epic1.getId()), tasks.getEpicById(sj.getId()));
    }
}
