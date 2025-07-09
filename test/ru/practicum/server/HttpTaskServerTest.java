package ru.practicum.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.*;
import ru.practicum.model.*;
import ru.practicum.server.adapters.DurationAdapter;
import ru.practicum.server.adapters.LocalDateAdapter;


import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTest {
    private HttpTaskServer server;
    private static final Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();
    private final HttpClient client = HttpClient.newHttpClient();
    Task exampleTask;


    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        server = new HttpTaskServer();
        server.start();

        exampleTask = new Task("Test Task", "Test Description");
        exampleTask.setId(1);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(exampleTask)))
                .header("Content-Type", "application/json")
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @AfterEach
    public void tearDown() {
        server.stop();
    }

    @Test
    public void shouldReturnTaskById() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/1"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        Task returnedTask = gson.fromJson(response.body(), Task.class);
        assertEquals(exampleTask.getNameTask(), returnedTask.getNameTask());
        assertEquals(exampleTask.getDescription(), returnedTask.getDescription());
        assertNull(returnedTask.getStartTime());

        HttpRequest notExistTaskRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/3"))
                .GET()
                .build();

        response = client.send(notExistTaskRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
    }

    @Test
    void shouldReturn2WhenNewTaskIsCreated() throws IOException, InterruptedException {
        Task newTask = new Task("Задача 2", "Тут будет описание");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(newTask)))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listTask = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> tasks = gson.fromJson(response.body(), listTask);

        assertEquals(2, tasks.size());
        assertEquals("Задача 2", tasks.get(1).getNameTask());
    }

    @Test
    void shouldReturnTrueWhenTaskIsUpdated() throws IOException, InterruptedException {
        Task updateTask = new Task("Обновленная задача", "Тут описание");
        updateTask.setId(1);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(updateTask)))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/1"))
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        Task returnedTask = gson.fromJson(response.body(), Task.class);
        assertEquals("Обновленная задача", returnedTask.getNameTask());
        assertEquals("Тут описание", returnedTask.getDescription());
    }

    @Test
    void shouldReturnTrueWhenResponseIsEmpty() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/-1"))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/1"))
                .DELETE()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type taskListType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> tasks = gson.fromJson(response.body(), taskListType);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldReturnEpicWhenNewEpicIsCreated() throws IOException, InterruptedException {
        Epic epic = new Epic("Это Эпик", "Это описание Эпика");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic)))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics/2"))
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());


        Epic returnedEpic = gson.fromJson(response.body(), Epic.class);
        assertEquals("Это Эпик", returnedEpic.getNameTask());
        assertTrue(returnedEpic.getSubTask().isEmpty());
    }

    @Test
    void shouldReturnSubtaskWhenNewSubtaskIsCreated() throws IOException, InterruptedException {
        Epic epic = new Epic("Это Эпик", "Это описание Эпика");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic)))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> responseEpic = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, responseEpic.statusCode());

        SubTask subTask = new SubTask("Это Подзадача", "А это описание", 2);
        subTask.setStartTime(LocalDateTime.of(2025, Month.MAY, 11, 10, 15));
        subTask.setDuration(Duration.ofMinutes(100));

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subTask)))
                .build();

        HttpResponse<String> responseSubtask = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, responseSubtask.statusCode());

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics/2"))
                .GET()
                .build();
        responseEpic = client.send(request, HttpResponse.BodyHandlers.ofString());
        Epic returnedEpic = gson.fromJson(responseEpic.body(), Epic.class);
        assertEquals("Это Эпик", returnedEpic.getNameTask());
        assertEquals(1, returnedEpic.getSubTask().size());
        assertEquals(LocalDateTime.of(2025, Month.MAY, 11, 10, 15), returnedEpic.getStartTime());
        assertEquals(LocalDateTime.of(2025, Month.MAY, 11, 11, 55), returnedEpic.getEndTime());

    }

    @Test
    void shouldReturnTrueWhenEpicIsNotExistForSubtask() throws IOException, InterruptedException {
        SubTask subTask = new SubTask("Это Подзадача", "А это описание", 5);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subTask)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

}
