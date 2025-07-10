package ru.practicum.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ru.practicum.exeptions.ManagerIsIntersectException;
import ru.practicum.manager.TaskManager;
import ru.practicum.model.Epic;
import ru.practicum.server.Endpoints;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class EpicHandler extends BaseHttpHandler {

    public EpicHandler(TaskManager tasks, Gson gson) {
        super(tasks, gson);
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Endpoints endpoints = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

            switch (endpoints) {
                case GET -> handleGetEpic(exchange);
                case GET_ID -> handleGetEpicByID(exchange);
                case POST -> handlePostEpic(exchange);
                case DELETE -> handleDeleteEpic(exchange);
                case GET_LIST_SUBTASKS -> handleGetSubtasksInEpics(exchange);
                default -> sendNotFound(exchange);
            }
        } catch (Exception e) {
            sendServerError(exchange);
        }
    }

    private void handleGetEpic(HttpExchange exchange) throws IOException {
        sendText(exchange, gson.toJson(taskManager.getEpic()), 200);
    }

    private void handleGetEpicByID(HttpExchange exchange) throws IOException {
        Optional<Integer> id = getTaskId(exchange);
        if (id.isEmpty()) {
            sendText(exchange, "Invalid post ID", 400);
        } else if (taskManager.getEpicById(id.get()) == null) {
            sendNotFound(exchange);
        } else {
            sendText(exchange, gson.toJson(taskManager.getEpicById(id.get())), 200);
        }
    }

    private void handleGetSubtasksInEpics(HttpExchange exchange) throws IOException {
        Optional<Integer> id = getTaskId(exchange);
        if (id.isEmpty()) {
            sendText(exchange, "Invalid post ID", 400);
        } else if (taskManager.getEpicById(id.get()) == null) {
            sendNotFound(exchange);
        } else {
            sendText(exchange, gson.toJson(taskManager.getEpicById(id.get()).getSubTask()), 200);
        }
    }


    private void handlePostEpic(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        if (body.isBlank()) {
            sendText(exchange, "An empty request body was received", 400);
        }
        Epic task = gson.fromJson(body, Epic.class);

        try {
            if (taskManager.getTaskById(task.getId()) != null) {
                sendText(exchange, "Epic already exist", 400);
            } else {
                taskManager.createEpic(task);
            }
            sendText(exchange, "Epic is added or updated successfully", 201);
        } catch (ManagerIsIntersectException e) {
            sendHasInteractions(exchange);
        }
    }

    private void handleDeleteEpic(HttpExchange exchange) throws IOException {
        Optional<Integer> id = getTaskId(exchange);
        if (id.isEmpty()) {
            sendText(exchange, "Invalid post ID", 400);
        } else {
            taskManager.removeEpicById(id.get());
            sendText(exchange, "Epic is deleted successfully", 200);
        }
    }

    private Endpoints getEndpoint(String requestPath, String requestMethod) {
        String[] path = requestPath.split("/");

        switch (requestMethod) {
            case "GET" -> {
                if (path.length == 2 && path[1].equals("epics")) {
                    return Endpoints.GET;
                } else if (path.length == 3 && path[1].equals("epics")) {
                    return Endpoints.GET_ID;
                } else if (path.length == 4 && path[3].equals("subtasks")) {
                    return Endpoints.GET_LIST_SUBTASKS;
                }
            }
            case "POST" -> {
                if (path.length == 2 && path[1].equals("epics")) {
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
