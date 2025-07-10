package ru.practicum.model;

public class SubTask extends Task {
    //Поле для связывания с классом ru.practicum.model.Epic
    private final Integer idEpic;

    //Конструктор ru.practicum.model.SubTask
    public SubTask(String nameSubTask, String description, int idEpic) {
        super(nameSubTask, description);
        this.idEpic = idEpic;
    }

    //Создал геттер
    public Integer getIdEpic() {
        return idEpic;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    //Переопределение метода toString
    @Override
    public String toString() {
        return "Подзадача №" + id + "\n   Название: " + nameTask + "\n   Описание: " + description + "\n   Статус: " + statusTask + "\n";
    }

}
