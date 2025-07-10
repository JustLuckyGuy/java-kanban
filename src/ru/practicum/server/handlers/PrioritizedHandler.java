package ru.practicum.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ru.practicum.manager.TaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler {

    public PrioritizedHandler(TaskManager tasks, Gson gson) {
        super(tasks, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            sendText(exchange, gson.toJson(taskManager.getPrioritizedTasks()), 200);
        } else {
            sendNotFound(exchange);
        }
    }
}
