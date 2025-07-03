package ru.practicum.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.practicum.exeptions.ManagerIsIntersectException;
import ru.practicum.manager.TaskManager;
import ru.practicum.model.Task;
import ru.practicum.server.Endpoints;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


public class TaskHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public TaskHandler(TaskManager tasks, Gson gson){
        this.taskManager = tasks;
        this.gson = gson;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Endpoints endpoints = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

            switch (endpoints) {
                case GET_TASK -> handleGetTask(exchange);
                case GET_TASK_ID -> handleGetTaskByID(exchange);
                case POST_TASK -> handlePostTask(exchange);
                case DELETE_TASK -> handleDeleteTask(exchange);
                default -> sendNotFound(exchange);
            }
        }catch (Exception e){
            e.printStackTrace();
            sendText(exchange, "Internal server error: " + e.getMessage(), 500);
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
        Task task = gson.fromJson(body, Task.class);
        try {
            if (taskManager.getTaskById(task.getId()) == null) {
                taskManager.createTask(task);
            } else {
                taskManager.updateTask(task);
            }
            sendText(exchange, "Задача добавилась или обновилась", 201);
        } catch (ManagerIsIntersectException e){
            sendHasInteractions(exchange);
        }
    }

    private void handleDeleteTask(HttpExchange exchange) throws IOException {
        Optional<Integer> id = getTaskId(exchange);
        if (id.isEmpty()) {
            sendText(exchange, "Invalid post ID", 400);
        } else {
            taskManager.removeTaskById(id.get());
            sendText(exchange, "Задача удалена", 200);
        }
    }

    private Endpoints getEndpoint(String requestPath, String requestMethod) {
        String[] path = requestPath.split("/");

        switch (requestMethod) {
            case "GET" -> {
                if (path.length == 2 && path[1].equals("tasks")) {
                    return Endpoints.GET_TASK;
                } else if (path.length == 3 && path[1].equals("tasks")) {
                    return Endpoints.GET_TASK_ID;
                }
            }
            case "POST" -> {
                if(path.length == 2 && path[1].equals("tasks")) {
                    return Endpoints.POST_TASK;
                }

            }
            case "DELETE" -> {
                return Endpoints.DELETE_TASK;
            }
        }
        return Endpoints.UNKNOWN;
    }
}
