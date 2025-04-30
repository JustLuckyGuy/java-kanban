package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static ru.practicum.manager.Managers.getDefault;

public class SubTaskTest {

    //Еще вопрос, как правильно реализовать этот тест? Просто в данном случае идет проверка объектов благодаря переопределенному методу equals
    //и если мы будем сравнивать только по id, то у нас нет гарантий, что внутренние данные, вроде name или description, будут изменены. А если мы
    //проверяем каждое поле, как в моем случае, то объекты будут равны не только когда id у них одинаковый, но и содержимое внутри.
    @Test
    void shouldReturnTrueWhenSubTaskEqualsById() {
        SubTask sj = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.NEW, 1);
        SubTask sj2 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", StatusTask.DONE, 1);
        sj.setId(1);
        sj2.setId(1);
        assertEquals(sj, sj2);
    }

}
