package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.StatusTask;
import ru.practicum.model.SubTask;
import ru.practicum.model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public abstract class TaskManagerTest<T extends TaskManager> {
    protected abstract T createTask();

    protected T tasks;
    protected Task task1;
    protected Task task2;
    protected Epic epic1;
    protected Epic epic2;
    protected SubTask subTask1;
    protected SubTask subTask2;
    protected SubTask subTask3;


    @BeforeEach
    void before() {
        tasks = createTask();
        task1 = new Task("Прогулка", "Взять с собой собаку");

        task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями");
        tasks.createTask(task1);
        tasks.createTask(task2);
        epic1 = new Epic("Очень большая и важная задача", "Дедлайн до завтра");
        epic2 = new Epic("Менее большая задача", "Дедлайн сегодня");
        tasks.createEpic(epic1);
        tasks.createEpic(epic2);
        subTask1 = new SubTask("Разработать план", "Подумать над планом", epic1.getId());
        subTask2 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", epic1.getId());
        subTask3 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", epic2.getId());
        tasks.createSubTask(subTask1);
        tasks.createSubTask(subTask2);
        tasks.createSubTask(subTask2);
    }

    @Test
    void shouldReturnTrueWhenTasksIsCreated() {
        assertEquals(2, tasks.getTasks().size());
    }

    @Test
    void shouldReturnTrueWhenSubTaskIsCreated() {
        assertEquals(3, tasks.getSubTasks().size());
    }

    @Test
    void shouldReturnTrueWhenEpicIsCreated() {
        assertEquals(2, tasks.getEpic().size());
    }

    @Test
    void getTaskByID() {
        Task takenTask = tasks.getTaskById(1);
        assertEquals(takenTask, tasks.getTaskById(task1.getId()));
    }

    @Test
    void getSubTaskByID() {
        SubTask takenSubTusk = tasks.getSubTaskById(5);
        assertEquals(takenSubTusk, tasks.getSubTaskById(subTask1.getId()));
    }

    @Test
    void getEpicByID() {
        Epic takenEpic = tasks.getEpicById(3);
        assertEquals(takenEpic, tasks.getEpicById(epic1.getId()));
    }

    @Test
    void shouldReturnTrueWhenTaskIsUpdated() {
        Task olddTask = new Task("Прогулка", "Взять с собой собаку", StatusTask.NEW);
        task1.setNameTask("Новая задача");
        task1.setDescription("Исправить все ошибки");
        tasks.updateTask(task1);
        assertNotEquals(tasks.getTaskById(task1.getId()), olddTask);
    }

    @Test
    void shouldReturnTrueWhenSubTaskIsUpdated() {
        SubTask oldEpic = new SubTask("Разработать план", "Подумать над планом", epic1.getId());
        subTask1.setNameTask("Новая задача");
        subTask1.setDescription("Исправить все ошибки");
        tasks.updateSubTask(subTask1);
        assertNotEquals(tasks.getSubTaskById(subTask1.getId()), oldEpic);
    }

    @Test
    void shouldReturnTrueWhenEpicIsUpdated() {
        Epic oldEpic = new Epic("Очень большая и важная задача", "Дедлайн до завтра");
        epic1.setNameTask("Новая задача");
        epic1.setDescription("Исправить все ошибки");
        tasks.updateEpic(epic1);
        assertNotEquals(tasks.getEpicById(epic1.getId()), oldEpic);
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
    void shouldNonConflictingValuesById() {
        Task task1 = new Task("Прогулка", "Взять с собой собаку");
        tasks.createTask(task1);
        Task task2 = new Task("Посмотреть фильм", "Выбрать фильм с друзьями");
        task2.setId(2);
        tasks.updateTask(task2);
        assertNotEquals(tasks.getTaskById(task1.getId()), tasks.getTaskById(task2.getId()));
        task2.setId(task1.getId());
        tasks.updateTask(task2);
        assertEquals(tasks.getTaskById(task1.getId()), tasks.getTaskById(task2.getId()));
    }

    @Test
    void shouldReturnSizeSubTasksFromEpic() {

        epic1 = new Epic("Очень большая и важная задача", "Дедлайн до завтра");
        epic2 = new Epic("Менее большая задача", "Дедлайн сегодня");
        tasks.createEpic(epic1);
        tasks.createEpic(epic2);
        subTask1 = new SubTask("Разработать план", "Подумать над планом", epic1.getId());
        subTask2 = new SubTask("Проконсультироваться с коллективом", "Поговорить с коллективом о плане", epic1.getId());
        subTask3 = new SubTask("Проверит свою работу на ошибки", "Проверить ошибки своей работы", epic2.getId());
        tasks.createSubTask(subTask1);
        tasks.createSubTask(subTask2);
        tasks.createSubTask(subTask3);

        assertEquals(2, tasks.getAllSubInEpic(epic1.getId()).size());
    }

    @Test
    void shouldReturnTrueWhenTaskIsRemovedFromTaskManagerAndHistory() {

        tasks.getTaskById(1);
        tasks.getTaskById(2);
        tasks.getTaskById(1);
        tasks.getEpicById(3);
        assertEquals(3, tasks.showHistory().size());

        tasks.removeTaskById(1);
        assertEquals(2, tasks.showHistory().size());
    }
}
