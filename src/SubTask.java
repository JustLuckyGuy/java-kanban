import java.util.ArrayList;

public class SubTask extends Task {
    //Поле для связывания с классом Epic
    private int idEpic;

    //Конструктор SubTask
    public SubTask(String nameSubTask, int idEpic) {
        super(nameSubTask);
        this.idEpic = idEpic;
    }
    //Создал геттер
    public int getIdEpic() {
        return idEpic;
    }

    //Переопределение метода toString
    @Override
    public String toString() {
        return "\n   Название: " + nameTask +
                "\n   Статус: " + statusTask + "\n";
    }
}
