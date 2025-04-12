import java.util.ArrayList;
import java.util.Objects;

public class Task {
    private static int counterTask = 1;
    private final int id;
    protected final String nameTask;
    private ArrayList<String> description;
    protected StatusTask statusTask;

    //Конструктор Task
    public Task(String nameTask) {
        this.nameTask = nameTask;
        this.statusTask = StatusTask.NEW;
        this.id = counterTask++;
        this.description = new ArrayList<>();
    }

    //Создал, необходимые геттеры и сеттеры
    public int getId() {
        return id;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public void setStatusTask(StatusTask statusTask) {
        this.statusTask = statusTask;
    }

    public StatusTask getStatusTask() {
        return statusTask;
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
        StringBuilder result = new StringBuilder("Задача №" + id + "\n   Название: " + nameTask + "\n   Описание: ");
        if (description.isEmpty()) {
            result = new StringBuilder("Задача №" + id + "\n   Название: " + nameTask);
        } else {
            for (int i = 0; i < description.size(); i++) {
                if (i == description.size() - 1) {
                    result.append(description.get(i));
                } else {
                    result.append(description.get(i)).append(", ");
                }
            }
        }
        return result + "\n   Статус: " + statusTask + "\n";
    }
}
