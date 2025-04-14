package ru.practicum.model;

import java.util.ArrayList;

public class Epic extends Task {
    //Поле, которое будет содержать наши подзадачи
    private final ArrayList<SubTask> subTasks;

    //Конструктор ru.practicum.model.Epic
    public Epic(String nameEpic, String description, StatusTask statusTask) {
        super(nameEpic, description, statusTask);
        this.subTasks = new ArrayList<>();
    }

    //Создал метод, для добавления в Список наши подзадачи
    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }

    //Создал геттер
    public ArrayList<SubTask> getSubTask() {
        return subTasks;
    }

    //Метод, который будет проверять статус Эпика
    public void checkStatus() {
        int counterOfNew = 0;
        int counterOfDone = 0;
        for (SubTask s : subTasks) {
            if (s.getStatusTask() == StatusTask.NEW) {
                counterOfNew++;
            } else if (s.getStatusTask() == StatusTask.DONE) {
                counterOfDone++;
            }
        }

        if (counterOfNew == subTasks.size()) {
            this.setStatusTask(StatusTask.NEW);
        } else if (counterOfDone == subTasks.size()) {
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
    public String toString() {
        StringBuilder result = new StringBuilder("Эпик № " + id + "\nНазвание эпика: " + nameTask +
                "\nОписание: " + description + "\n");
        for (SubTask subTask : subTasks) {
            result.append(subTask);
        }
        return result + "Статус Эпика: " + statusTask + "\n";
    }

}
