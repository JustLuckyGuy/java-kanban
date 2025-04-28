package ru.practicum.model;

import java.util.Objects;

public class Task {
    protected String nameTask;
    protected String description;
    protected StatusTask statusTask;
    protected int id;

    //Конструктор Task
    public Task(String nameTask, String description, StatusTask statusTask) {
        this.nameTask = nameTask;
        this.statusTask = statusTask;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StatusTask getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(StatusTask statusTask) {
        this.statusTask = statusTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameTask() {
        return nameTask;
    }

    public String getDescription() {
        return description;
    }

    //Переопределил методы equals и hashcode


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return id == task.id && Objects.equals(nameTask, task.nameTask) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameTask, description, id);
    }

    //Переопределение метода toString
    @Override
    public String toString() {
        return "Задача №" + id + "\n   Название: " + nameTask + "\n   Описание: " + description + "\n   Статус: " + statusTask + "\n";
    }
}
