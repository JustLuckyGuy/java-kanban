package ru.practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected String nameTask;
    protected String description;
    protected StatusTask statusTask;
    protected int id;
    protected Duration duration = Duration.ZERO;
    protected LocalDateTime startTime;

    //Конструктор Task
    public Task(String nameTask, String description, StatusTask statusTask) {
        this.nameTask = nameTask;
        this.statusTask = statusTask;
        this.description = description;
    }

    public Task(String nameTask, String description) {
        this(nameTask, description, StatusTask.NEW);
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

    public TaskType getType() {
        return TaskType.TASK;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime(){
        return startTime.plus(duration);
    }

    //Переопределил методы equals и hashcode
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    //Переопределение метода toString
    @Override
    public String toString() {
        return "Задача №" + id + "\n   Название: " + nameTask + "\n   Описание: " + description + "\n   Статус: " + statusTask + "\n";
    }

}
