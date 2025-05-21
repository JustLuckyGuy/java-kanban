package ru.practicum.manager;

import ru.practicum.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Node<? extends Task> head;
    private Node<? extends Task> tail;

    Map<Integer, Node<? extends Task>> history = new LinkedHashMap<>();


    //Метод, который возвращает список истории
    @Override
    public List<Task> getHistory() {
        List<Task> tasks = new ArrayList<>();
        Node<? extends Task> current = head;
        while (current != null) {
            tasks.add(current.data);
            current = current.next;
        }
        return tasks;
    }


    @Override
    //Метод для добавления Задач в список историй
    public <T extends Task> void addHistory(T tasks) {
        if (tasks == null) {
            return;
        }
        removeHistoryById(tasks.getId());
        linkLast(tasks);
    }

    //Метод, который добавляет узел в конец
    private <T extends Task> void linkLast(T task) {
        Node<? extends Task> newNode = new Node(tail, task, null);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        history.put(task.getId(), newNode);
    }

    //Метод, который удаляет связи с узлом
    private void removeNode(Node<? extends Task> node) {
        Node<? extends Task> prev = node.prev;
        Node<? extends Task> next = node.next;

        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }

        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }
    }

    //Метод, который удаляет задачу из истории по id
    @Override
    public void removeHistoryById(int id) {
        Node<? extends Task> node = history.remove(id);
        if (node != null) {
            removeNode(node);
        }
    }

    //Внутренний класс Node, для создания узла
    static class Node<T extends Task> {
        T data;
        Node<? extends Task> prev;
        Node<? extends Task> next;

        Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
}
