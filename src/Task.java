import java.util.Objects;

public class Task {
    private final String nameTask;
    private String description;
    private StatusTask statusTask;

    public Task(String nameTask) {
        this.nameTask = nameTask;
        statusTask = StatusTask.NEW;
    }


    public void setDescription(String description) {
        this.description = Objects.requireNonNullElse(description, "");

    }

    public void setStatusTask(StatusTask statusTask) {
        this.statusTask = statusTask;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return Objects.equals(nameTask, task.nameTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameTask);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("   Название: " + nameTask + "\n   Описание: ");
        if (description.isEmpty()) {
            result = new StringBuilder("   Название: " + nameTask);
        } else {
            result.append(description);
        }
        return result + "\n   Статус: " + statusTask + "\n";
    }
}
