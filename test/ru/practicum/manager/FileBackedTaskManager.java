package ru.practicum.manager;

import org.junit.jupiter.api.*;
import ru.practicum.exeptions.ManagerLoadException;
import ru.practicum.model.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.manager.FileBackedTaskManager.loadFromFile;

class FileBackedTaskManagerTest {

    private File tempFile;

    @BeforeEach
    void setup() throws IOException {
        tempFile = File.createTempFile("task_test", ".csv");
        tempFile.deleteOnExit(); // Файл удалится автоматически после завершения JVM
    }

    //Тест с проверкой пустого файла
    @Test
    void shouldSaveAndLoadEmptyFileCorrectly() {
        FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(tempFile);

        assertTrue(manager.getTasks().isEmpty(), "Список задач должен быть пуст");
        assertTrue(manager.getEpic().isEmpty(), "Список эпиков должен быть пуст");
        assertTrue(manager.getSubTasks().isEmpty(), "Список подзадач должен быть пуст");
    }

    //Тест с сохранением и загрузкой файла
    @Test
    void shouldSaveMultipleTasksCorrectly() {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(tempFile.getAbsolutePath());
        Task task1 = new Task("Задача 1", "Описание 1");
        Task task2 = new Task("Задача 2", "Описание 2");

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        FileBackedTaskManager restored = loadFromFile(tempFile);

        List<Task> restoredTasks = restored.getTasks();

        assertEquals(2, restoredTasks.size(), "Должно быть 2 задачи после загрузки");

        Task task = restoredTasks.getFirst();
        assertEquals("Задача 1", task.getNameTask());
        assertEquals(StatusTask.NEW, task.getStatusTask());

    }

    //Тест с несколькими задачами
    @Test
    void shouldLoadMultipleTasksFromFile() {
        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.getAbsolutePath());

        Epic epic = new Epic("Эпик 1", "Описание эпика");
        manager.createEpic(epic);

        SubTask subTask1 = new SubTask("Подзадача 1", "Описание подзадачи", epic.getId());
        SubTask subTask2 = new SubTask("Подзадача 2", "Ещё описание", epic.getId());
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask2);

        FileBackedTaskManager restored = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(1, restored.getEpic().size(), "Должен загрузиться один эпик");
        assertEquals(2, restored.getSubTasks().size(), "Должны загрузиться две подзадачи");
    }

    //Тест на исключения
    @Test
    void shouldReturnExceptionWhenFileNonExists() {
        assertThrows(ManagerLoadException.class, () -> FileBackedTaskManager.loadFromFile(new File("dsadas.csv")));
    }
}
