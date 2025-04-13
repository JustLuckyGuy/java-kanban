package ru.practicum.model;

public class SubTask extends Task {
    //Поле для связывания с классом ru.practicum.model.Epic
    private final int idEpic;

    //Конструктор ru.practicum.model.SubTask
    public SubTask(String nameSubTask, String description, StatusTask statusTask, int idEpic) {
        super(nameSubTask, description, statusTask);
        this.idEpic = idEpic;
    }

    //Создал геттер
    public int getIdEpic() {
        return idEpic;
    }

    //Переопределение метода toString
    @Override
    public String toString() {
        return "Подзадача №" + id + "\n   Название: " + nameTask + "\n   Описание: " + description + "\n   Статус: " + statusTask + "\n";
    }
}
