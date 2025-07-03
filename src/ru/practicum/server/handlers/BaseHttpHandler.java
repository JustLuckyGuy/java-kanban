package ru.practicum.server.handlers;


import com.sun.net.httpserver.HttpExchange;


import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class BaseHttpHandler {

    protected void sendText(HttpExchange exchange, String responseString, int responseCode) throws IOException {
        byte[] resp = responseString.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(responseCode, resp.length);
        exchange.getResponseBody().write(resp);
        exchange.close();
    }

    protected void sendNotFound(HttpExchange exchange) throws IOException {
        sendText(exchange, "Not Found", 404);
    }

    protected void sendHasInteractions(HttpExchange exchange) throws IOException {
        sendText(exchange, "Not Acceptable", 406);
    }

    protected void sendServerError(HttpExchange exchange) throws IOException {
        sendText(exchange, "Internal Server Error", 500);
    }

    protected Optional<Integer> getTaskId(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        try {
            if(Integer.parseInt(pathParts[2]) < 0) throw new NumberFormatException();
            return Optional.of(Integer.parseInt(pathParts[2]));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }





}
