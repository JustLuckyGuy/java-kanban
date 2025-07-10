package ru.practicum.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ru.practicum.exeptions.ManagerIsIntersectException;
import ru.practicum.manager.TaskManager;
import ru.practicum.model.SubTask;
import ru.practicum.server.Endpoints;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class SubtaskHandler extends BaseHttpHandler {


    public SubtaskHandler(TaskManager tasks, Gson gson) {
        super(tasks, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Endpoints endpoints = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

            switch (endpoints) {
                case GET -> handleGetSubtask(exchange);
                case GET_ID -> handleGetSubtaskByID(exchange);
                case POST -> handlePostSubtask(exchange);
                case DELETE -> handleDeleteSubtask(exchange);
                default -> sendNotFound(exchange);
            }
        } catch (Exception e) {
            sendHasInteractions(exchange);
        }
    }

    private void handleGetSubtask(HttpExchange exchange) throws IOException {
        sendText(exchange, gson.toJson(taskManager.getSubTasks()), 200);
    }

    private void handleGetSubtaskByID(HttpExchange exchange) throws IOException {
        Optional<Integer> id = getTaskId(exchange);
        if (id.isEmpty()) {
            sendText(exchange, "Invalid post ID", 400);
        } else if (taskManager.getSubTaskById(id.get()) == null) {
            sendNotFound(exchange);
        } else {
            sendText(exchange, gson.toJson(taskManager.getSubTaskById(id.get())), 200);
        }
    }

    private void handlePostSubtask(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        if (body.isBlank()) {
            sendText(exchange, "An empty request body was received", 400);
        }

        SubTask task = gson.fromJson(body, SubTask.class);
        try {
            if (task.getIdEpic() == null || taskManager.getEpicById(task.getIdEpic()) == null) {
                sendText(exchange, "Subtask cannot exist without an Epic", 400);
            }
            if (taskManager.getSubTaskById(task.getId()) == null) {
                taskManager.createSubTask(task);
            } else {
                taskManager.updateSubTask(task);
            }
            sendText(exchange, "Subtask is added or updated successfully", 201);
        } catch (ManagerIsIntersectException e) {
            sendHasInteractions(exchange);
        }
    }

    private void handleDeleteSubtask(HttpExchange exchange) throws IOException {
        Optional<Integer> id = getTaskId(exchange);
        if (id.isEmpty()) {
            sendText(exchange, "Invalid post ID", 400);
        } else {
            taskManager.removeSubTaskById(id.get());
            sendText(exchange, "Subtask is deleted successfully", 200);
        }
    }

    private Endpoints getEndpoint(String requestPath, String requestMethod) {
        String[] path = requestPath.split("/");

        switch (requestMethod) {
            case "GET" -> {
                if (path.length == 2 && path[1].equals("subtasks")) {
                    return Endpoints.GET;
                } else if (path.length == 3 && path[1].equals("subtasks")) {
                    return Endpoints.GET_ID;
                }
            }
            case "POST" -> {
                if (path.length == 2 && path[1].equals("subtasks")) {
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
