package ru.practicum.manager;

import org.junit.jupiter.api.*;
import ru.practicum.exeptions.ManagerLoadException;
import ru.practicum.model.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.manager.FileBackedTaskManager.loadFromFile;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    private File tempFile;


    @Override
    protected FileBackedTaskManager createTask() {
        try {
            tempFile = File.createTempFile("task_test", ".csv");
            tempFile.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new FileBackedTaskManager(tempFile);
    }


    //Тест с проверкой пустого файла
    @Test
    void shouldSaveAndLoadEmptyFileCorrectly() throws IOException {
        File newTempFile = File.createTempFile("task_test", ".csv");
        newTempFile.deleteOnExit();

        FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(newTempFile);

        assertTrue(manager.getTasks().isEmpty(), "Список задач должен быть пуст");
        assertTrue(manager.getEpic().isEmpty(), "Список эпиков должен быть пуст");
        assertTrue(manager.getSubTasks().isEmpty(), "Список подзадач должен быть пуст");
    }

    //Тест с сохранением и загрузкой файла
    @Test
    void shouldSaveAndLoadTasksCorrectly() {
        FileBackedTaskManager taskManager = createTask();
        Task task1 = new Task("Задача 1", "Описание 1");
        Task task2 = new Task("Задача 2", "Описание 2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        task2.setId(10);
        task2.setStatusTask(StatusTask.IN_PROGRESS);
        taskManager.updateTask(task2);

        FileBackedTaskManager restored = loadFromFile(tempFile);

        List<Task> restoredTasks = restored.getTasks();
        assertEquals(2, restoredTasks.size(), "Должно быть 2 задачи после загрузки");

        Task task = restoredTasks.getFirst();
        assertEquals("Задача 1", task.getNameTask());
        assertEquals(StatusTask.NEW, task.getStatusTask());

        Task fullFieldsTask = restoredTasks.get(1);
        assertEquals(10, fullFieldsTask.getId(), "ID должны совпадать");
        assertEquals("Задача 2", fullFieldsTask.getNameTask(), "Имена должны совпадать");
        assertEquals("Описание 2", fullFieldsTask.getDescription(), "Описания должны совпадать");
        assertEquals(StatusTask.IN_PROGRESS, fullFieldsTask.getStatusTask(), "Статусы должны совпадать");

    }

    //Тест с несколькими задачами
    @Test
    void shouldLoadTasksCorrectly() {
        FileBackedTaskManager manager = createTask();

        Task task = new Task("Задача 1", "Задача 2");
        manager.createTask(task);

        Epic epic = new Epic("Эпик 1", "Описание эпика");
        manager.createEpic(epic);

        SubTask subTask1 = new SubTask("Подзадача 1", "Описание подзадачи", epic.getId());
        SubTask subTask2 = new SubTask("Подзадача 2", "Ещё описание", epic.getId());
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask2);

        FileBackedTaskManager restored = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(1, restored.getEpic().size(), "Должен загрузиться один эпик");
        assertEquals(2, restored.getSubTasks().size(), "Должны загрузиться две подзадачи");
        assertEquals(2, epic.getSubTask().size(), "Должен загрузить все подзадачи в свой эпик");

        Task taskManager = manager.getTaskById(task.getId());
        Task taskRestored = restored.getTaskById(task.getId());

        assertEquals(taskManager.getId(), taskRestored.getId(), "ID задач должны совпадать");
        assertEquals(taskManager.getNameTask(), taskRestored.getNameTask(), "Имена задач должны совпадать");
        assertEquals(taskManager.getDescription(), taskRestored.getDescription(), "Описания задач должны совпадать");
        assertEquals(taskManager.getStatusTask(), taskRestored.getStatusTask(), "Статусы задач должны совпадать");
        assertEquals(taskManager.getStartTime(), taskRestored.getStartTime(), "Время старта выполнения задачи должны совпадать");
        assertEquals(taskManager.getEndTime(), taskRestored.getEndTime(), "Время окончания задачи должны совпадать");
        assertEquals(taskManager.getDuration(), taskRestored.getDuration(), "Продолжительность задачи должны совпадать");

        Epic epicManager = manager.getEpicById(epic.getId());
        Epic epicRestored = restored.getEpicById(epic.getId());

        assertEquals(epicManager.getId(), epicRestored.getId(), "ID эпиков должны совпадать");
        assertEquals(epicManager.getNameTask(), epicRestored.getNameTask(), "Имена эпиков должны совпадать");
        assertEquals(epicManager.getDescription(), epicRestored.getDescription(), "Описания эпиков должны совпадать");
        assertEquals(epicManager.getStatusTask(), epicRestored.getStatusTask(), "Статусы эпиков должны совпадать");
        assertEquals(epicManager.getSubTask().size(), epicRestored.getSubTask().size(), "Размеры списков подзадач у эпиков должны совпадать");
        assertEquals(epicManager.getStartTime(), epicRestored.getStartTime(), "Время старта выполнения задачи должны совпадать");
        assertEquals(epicManager.getEndTime(), epicRestored.getEndTime(), "Время окончания задачи должны совпадать");
        assertEquals(epicManager.getDuration(), epicRestored.getDuration(), "Продолжительность задачи должны совпадать");

        for (int i = 0; i < epicManager.getSubTask().size(); i++) {
            SubTask subTaskManager = epicManager.getSubTask().get(i);
            SubTask subTaskRestored = epicRestored.getSubTask().get(i);

            assertEquals(subTaskManager.getId(), subTaskRestored.getId(), "ID подзадач должны совпадать");
            assertEquals(subTaskManager.getNameTask(), subTaskRestored.getNameTask(), "Имена подзадач должны совпадать");
            assertEquals(subTaskManager.getDescription(), subTaskRestored.getDescription(), "Описания подзадач должны совпадать");
            assertEquals(subTaskManager.getStatusTask(), subTaskRestored.getStatusTask(), "Статусы подзадач должны совпадать");
            assertEquals(subTaskManager.getIdEpic(), subTaskRestored.getIdEpic(), "Подзадачи должны ссылаться на один и тот же эпик");
            assertEquals(subTaskManager.getStartTime(), subTaskRestored.getStartTime(), "Время старта выполнения задачи должны совпадать");
            assertEquals(subTaskManager.getEndTime(), subTaskRestored.getEndTime(), "Время окончания задачи должны совпадать");
            assertEquals(subTaskManager.getDuration(), subTaskRestored.getDuration(), "Продолжительность задачи должны совпадать");
        }

    }

    //Тест на исключения
    @Test
    void shouldReturnExceptionWhenFileNonExists() {
        assertThrows(ManagerLoadException.class, () -> FileBackedTaskManager.loadFromFile(new File("dsadas.csv")));
    }

    @Test
    void shouldReturn5WhenPrioritizedListIsLoaded() {
        FileBackedTaskManager taskManager = FileBackedTaskManager.loadFromFile(tempFile);
        assertEquals(5, taskManager.getPrioritizedTasks().size());
    }


}
