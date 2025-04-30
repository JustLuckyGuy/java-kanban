package ru.practicum.manager;

import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.StatusTask;
import ru.practicum.model.SubTask;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicTest {
    @Test
    void shouldNotAllowAddingItselfAsSubtask() {
        //Возможно я не правильно понял условие, но если нам необходимо проверить, может ли epic добавит самого себя, как подзадачу,
        //то как это можно сделать? Ведь еще на стадии компиляции у меня уже будет ошибка, что я пытаюсь засунуть Epic в список с типами
        //данных SubTask
        Epic epic = new Epic("Очень большая и важная задача", "Дедлайн до завтра", StatusTask.NEW);
        epic.setId(1);

        SubTask fakeEpic = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.NEW, epic.getId());
        fakeEpic.setId(epic.getId());

        epic.addSubTask(fakeEpic);
        assertEquals(0, epic.getSubTask().size());
    }

    @Test
    void shouldReturnTrueWhenEpicEqualsById() {
        Epic epic1 = new Epic("Очень большая и важная задача", "Дедлайн до завтра", StatusTask.NEW);
        Epic epic2 = new Epic("Очень большая и важная задача", "Дедлайн до завтра", StatusTask.NEW); // статус может отличаться
        epic1.setId(1);
        epic2.setId(1);

        assertEquals(epic1, epic2);
    }

    @Test
    void shouldReturnNEWStatusWhenEpicIsWithoutSubTask() {
        Epic epic = new Epic("Очень большая и важная задача", "Дедлайн до завтра", StatusTask.DONE);
        epic.setId(1);
        epic.checkStatus();
        assertEquals(StatusTask.NEW, epic.getStatusTask());
    }

    @Test
    void shouldReturnINPROGRSSStatusWhenSubtaskIsChangedStatus() {
        Epic epic = new Epic("Очень большая и важная задача", "Дедлайн до завтра", StatusTask.NEW);
        epic.setId(1);
        SubTask subTask1 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.NEW, epic.getId());
        SubTask subTask2 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", StatusTask.NEW, epic.getId());
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);
        subTask2.setStatusTask(StatusTask.DONE);
        epic.updateSubTask(subTask2);
        epic.checkStatus();
        assertEquals(StatusTask.IN_PROGRESS, epic.getStatusTask());
    }

    @Test
    void shouldReturnDONEStatusWhenAllSubtaskIsDONE() {
        Epic epic = new Epic("Очень большая и важная задача", "Дедлайн до завтра", StatusTask.NEW);
        epic.setId(1);
        SubTask subTask1 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.NEW, epic.getId());
        SubTask subTask2 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", StatusTask.NEW, epic.getId());
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
