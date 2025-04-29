package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static ru.practicum.manager.Managers.getDefault;

public class SubTaskTest {
    public static TaskManager tasks;
    Epic epic1;
    Epic epic2;
    SubTask subTask1;
    SubTask subTask2;
    SubTask subTask3;


    @BeforeEach
    void before() {
        tasks = getDefault();
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
    void shouldReturnTrueWhenSubTaskIsCreated() {
        assertEquals(3, tasks.getSubTasks().size());
    }

    @Test
    void getSubTaskByID() {
        SubTask takenSubTusk = tasks.getSubTaskById(3);
        assertEquals(takenSubTusk, tasks.getSubTaskById(subTask1.getId()));
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
    void shouldReturnTrueWhenSubTaskIsDeletedById() {
        tasks.removeSubTaskById(5);
        assertEquals(2, tasks.getSubTasks().size());
    }

    @Test
    void shouldReturnTrueWhenAllSubTasksIsDeleted() {
        tasks.removeAllSubTasks();
        assertEquals(0, tasks.getSubTasks().size());
    }

    @Test
    void shouldReturnTrueWhenSubTaskEqualsById() {
        SubTask sj = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.DONE, epic1.getId());
        sj.setId(subTask1.getId());
        tasks.updateSubTask(sj);
        assertEquals(tasks.getSubTaskById(subTask1.getId()), tasks.getSubTaskById(sj.getId()));
    }
}
