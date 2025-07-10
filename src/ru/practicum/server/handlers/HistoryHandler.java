package ru.practicum.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.practicum.manager.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {

    public HistoryHandler(TaskManager tasks, Gson gson) {
        super(tasks, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            sendText(exchange, gson.toJson(taskManager.showHistory()), 200);
        } else {
            sendNotFound(exchange);
        }
    }
}
