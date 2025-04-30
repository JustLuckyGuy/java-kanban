package ru.practicum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubTaskTest {

    @Test
    void shouldReturnTrueWhenSubTaskEqualsById() {
        SubTask subTask = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.NEW, 1);
        SubTask subTask2 = new SubTask("Разработать план", "Подумать над планом", StatusTask.NEW, 1);
        subTask.setId(1);
        subTask2.setId(1);
        assertEquals(subTask, subTask2);
    }

}
