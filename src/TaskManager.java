import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {
    private static int idTasks = 1;
    private static final HashMap<Integer, Task> tm = new HashMap<>();

    public static void addTasks(Scanner in){
        System.out.println("Вводите задачи, разделяя символом переноса строки (enter). Для завершения ввода введите пустую строку");
        while (true) {
            String task = in.nextLine();

            if(task.isEmpty()){
                return;
            }
            tm.put(idTasks, new Task(task));
            addDescription(idTasks, in);
            idTasks++;

        }

    }

    public static void addDescription(int idTasks, Scanner in){
        Task newDescriptionForTask = tm.get(idTasks);
        ArrayList<String> newDescription = new ArrayList<>();
        System.out.println("Вводите описания вашей задачи");
        while (true){
            String description = in.nextLine();
            if(description.isEmpty()){
                break;
            }
            newDescription.add(description);
        }
        newDescriptionForTask.setDescription(newDescription);
    }

    public static void getTask(){
        for(Integer i : tm.keySet()){
            System.out.println("Задача №"+ i + ":\n" + tm.get(i));
        }
    }


}
