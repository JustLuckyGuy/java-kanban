package ru.practicum.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ru.practicum.exeptions.ManagerIsIntersectException;
import ru.practicum.manager.TaskManager;
import ru.practicum.model.Task;
import ru.practicum.server.Endpoints;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


public class TaskHandler extends BaseHttpHandler {

    public TaskHandler(TaskManager tasks, Gson gson) {
        super(tasks, gson);
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Endpoints endpoints = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

            switch (endpoints) {
                case GET -> handleGetTask(exchange);
                case GET_ID -> handleGetTaskByID(exchange);
                case POST -> handlePostTask(exchange);
                case DELETE -> handleDeleteTask(exchange);
                default -> sendNotFound(exchange);
            }
        } catch (Exception e) {
            sendHasInteractions(exchange);
        }
    }

    private void handleGetTask(HttpExchange exchange) throws IOException {
        sendText(exchange, gson.toJson(taskManager.getTasks()), 200);
    }

    private void handleGetTaskByID(HttpExchange exchange) throws IOException {
        Optional<Integer> id = getTaskId(exchange);
        if (id.isEmpty()) {
            sendText(exchange, "Invalid post ID", 400);
        } else if (taskManager.getTaskById(id.get()) == null) {
            sendNotFound(exchange);
        } else {
            sendText(exchange, gson.toJson(taskManager.getTaskById(id.get())), 200);
        }
    }

    private void handlePostTask(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        if (body.isBlank()) {
            sendText(exchange, "An empty request body was received", 400);
        }

        Task task = gson.fromJson(body, Task.class);
        try {
            if (taskManager.getTaskById(task.getId()) == null) {
                taskManager.createTask(task);
            } else {
                taskManager.updateTask(task);
            }
            sendText(exchange, "Task is added or updated successfully", 201);
        } catch (ManagerIsIntersectException e) {
            sendHasInteractions(exchange);
        }
    }

    private void handleDeleteTask(HttpExchange exchange) throws IOException {
        Optional<Integer> id = getTaskId(exchange);
        if (id.isEmpty()) {
            sendText(exchange, "Invalid post ID", 400);
        } else {
            taskManager.removeTaskById(id.get());
            sendText(exchange, "Task is deleted successfully", 200);
        }
    }

    private Endpoints getEndpoint(String requestPath, String requestMethod) {
        String[] path = requestPath.split("/");

        switch (requestMethod) {
            case "GET" -> {
                if (path.length == 2 && path[1].equals("tasks")) {
                    return Endpoints.GET;
                } else if (path.length == 3 && path[1].equals("tasks")) {
                    return Endpoints.GET_ID;
                }
            }
            case "POST" -> {
                if (path.length == 2 && path[1].equals("tasks")) {
                    return Endpoints.POST;
                }

            }
            case "DELETE" -> {
                return Endpoints.DELETE;
            }
        }
        return Endpoints.UNKNOWN;
    }
}
