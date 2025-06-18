package ru.practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    //Поле, которое будет содержать наши подзадачи
    private final ArrayList<SubTask> subTasks;
    private LocalDateTime endTime;


    //Конструктор ru.practicum.model.Epic
    public Epic(String nameEpic, String description) {
        super(nameEpic, description);
        this.subTasks = new ArrayList<>();
    }

    //Создал метод, для добавления в Список наши подзадачи
    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
        updateStartAndEndTime();
    }

    //Создал геттер
    public ArrayList<SubTask> getSubTask() {
        return subTasks;
    }

    //Метод, который будет проверять статус Эпика
    public void checkStatus() {

        if (subTasks.stream().filter(subTask -> subTask.getStatusTask() == StatusTask.NEW)
                .count() == subTasks.size()) {
            this.setStatusTask(StatusTask.NEW);
        } else if (subTasks.stream().filter(subTask -> subTask.getStatusTask() == StatusTask.DONE)
                .count() == subTasks.size()) {
            this.setStatusTask(StatusTask.DONE);
        } else {
            this.setStatusTask(StatusTask.IN_PROGRESS);
        }
    }

    //Метод, который обновляет список Подзадач
    public void updateSubTask(SubTask subTask) {
        subTasks.remove(subTask);
        subTasks.add(subTask);

    }

    //Метод для удаления Подзадачи из Эпика
    public void removeSubTask(SubTask subTask) {
        subTasks.remove(subTask);
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    private void updateDuration() {
        duration = subTasks.stream()
                .map(SubTask::getDuration)
                .filter(Objects::nonNull)
                .reduce(Duration.ZERO, Duration::plus);

    }

    public void updateStartAndEndTime() {
        startTime = this.subTasks.stream()
                .map(SubTask::getStartTime)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo)
                .orElse(null);

        endTime = subTasks.stream()
                .map(timeOfSubTask -> {
                    if (timeOfSubTask.getStartTime() != null && timeOfSubTask.getDuration() != null) {
                        return timeOfSubTask.getStartTime().plus(timeOfSubTask.getDuration());
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null);
        updateDuration();
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Эпик № " + id + "\nНазвание эпика: " + nameTask +
                "\nОписание: " + description + "\n");
        subTasks.forEach(result::append);
        return result + "Статус Эпика: " + statusTask + "\n";
    }


}
