package ru.practicum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicTest {

    @Test
    void shouldReturnTrueWhenEpicEqualsById() {
        Epic epic1 = new Epic("Очень большая и важная задача", "Дедлайн до завтра");
        Epic epic2 = new Epic("Менее большая задача", "Дедлайн сегодня");
        epic1.setId(1);
        epic2.setId(1);

        assertEquals(epic1, epic2);
    }

    @Test
    void shouldReturnNEWStatusWhenEpicIsWithoutSubTask() {
        Epic epic = new Epic("Очень большая и важная задача", "Дедлайн до завтра");
        epic.setStatusTask(StatusTask.DONE);
        epic.setId(1);
        epic.checkStatus();
        assertEquals(StatusTask.NEW, epic.getStatusTask());
    }

    @Test
    void shouldReturnINPROGRSSStatusWhenSubtaskIsChangedStatus() {
        Epic epic = new Epic("Очень большая и важная задача", "Дедлайн до завтра");
        epic.setId(1);
        SubTask subTask1 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", epic.getId());
        subTask1.setId(1);
        SubTask subTask2 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", epic.getId());
        subTask2.setId(2);
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);
        subTask2.setStatusTask(StatusTask.DONE);
        epic.updateSubTask(subTask2);
        epic.checkStatus();
        assertEquals(StatusTask.IN_PROGRESS, epic.getStatusTask());
    }

    @Test
    void shouldReturnDONEStatusWhenAllSubtaskIsDONE() {
        Epic epic = new Epic("Очень большая и важная задача", "Дедлайн до завтра");
        epic.setId(1);
        SubTask subTask1 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", epic.getId());
        SubTask subTask2 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", epic.getId());
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);
        subTask1.setStatusTask(StatusTask.DONE);
        subTask2.setStatusTask(StatusTask.DONE);
        epic.updateSubTask(subTask1);
        epic.updateSubTask(subTask2);
        epic.checkStatus();
        assertEquals(StatusTask.DONE, epic.getStatusTask());
    }

}
