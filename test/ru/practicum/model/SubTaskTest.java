package ru.practicum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubTaskTest {

    @Test
    void shouldReturnTrueWhenSubTaskEqualsById() {
        SubTask subTask = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", 1);
        SubTask subTask2 = new SubTask("Разработать план", "Подумать над планом", 1);
        subTask.setId(1);
        subTask2.setId(1);
        assertEquals(subTask, subTask2);
    }

}
