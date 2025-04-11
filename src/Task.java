import java.util.ArrayList;
import java.util.Objects;

public class Task {
    private final String nameTask;
    private static int counter = 1;
    private int idTask = 0;
    private ArrayList<String> description = new ArrayList<>();
    private final StatusTask statusTask;

    public Task(String nameTask) {
        this.nameTask = nameTask;
        statusTask = StatusTask.NEW;
        idTask = counter;
        counter++;
    }


    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public int getIdTask() {
        return idTask;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("   Название: " + nameTask + "\n   Описание: ");
        if(description.isEmpty()) {
            result = new StringBuilder("   Название: " + nameTask);
        } else {
            for (String res : description){
                result.append("\n").append(" ".repeat(5)).append(res);
            }
        }
        return result + "\n   Статус: " + statusTask + "\n";
    }
}
