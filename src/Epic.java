import java.util.ArrayList;

public class Epic extends Task {
    //Поле, которое будет содержать наши подзадачи
    private final ArrayList<SubTask> subTasks;

    //Конструктор Epic
    public Epic(String nameEpic) {
        super(nameEpic);
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

    //Переопределил метод toString
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Название эпика: " + nameTask + "\n");
        for (int i = 0; i < subTasks.size(); i++) {
            result.append("  Подзадача № ").append(i + 1).append(subTasks.get(i));
        }
        return result + "Статус Эпика: " + statusTask + "\n";
    }

}
