package ru.practicum.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import ru.practicum.manager.Managers;
import ru.practicum.manager.TaskManager;
import ru.practicum.server.adapters.DurationAdapter;
import ru.practicum.server.adapters.LocalDateAdapter;
import ru.practicum.server.handlers.*;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;


public class HttpTaskServer {
    private final static int PORT = 8080;
    private final TaskManager taskManager;
    private final Gson gson;
    private final HttpServer server;

    public HttpTaskServer() throws IOException {
        this.taskManager = Managers.getDefault();
        this.gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();
        this.server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TaskHandler(taskManager, gson));
        server.createContext("/subtasks", new SubtaskHandler(taskManager, gson));
        server.createContext("/epics", new EpicHandler(taskManager, gson));
        server.createContext("/history", new HistoryHandler(taskManager, gson));
        server.createContext("/prioritized", new PrioritizedHandler(taskManager, gson));
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer taskServer = new HttpTaskServer();
        taskServer.start();
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }


}
