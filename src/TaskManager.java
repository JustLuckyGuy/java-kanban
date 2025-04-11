import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {
    private static int counterTask = 1;
    private static final HashMap<Integer, Task> taskManager = new HashMap<>();

    //d
    public static void addTask(Task task, String description){
        for(Integer id : taskManager.keySet()){
            if(taskManager.get(id).equals(task)){
                return;
            }
        }
        int idTasks = counterTask;
        task.setDescription(description);
        taskManager.put(idTasks, task);
        counterTask++;
    }

    //b
    public static void removeAllTasks(){
        taskManager.clear();
    }

    //f
    public static void removeTaskById(int idTask){
        taskManager.remove(idTask);
    }

    //c
    public static Task getTaskById(int idTask){
        if(taskManager.containsKey(idTask)){
            System.out.println("Нашли задачу №" + idTask);
            return taskManager.get(idTask);
        }
        return null;
    }

    //e
    public static void updateTask(int idTask, Task newTask, String description, StatusTask statusTask){
        newTask.setStatusTask(statusTask);
        newTask.setDescription(description);
        taskManager.put(idTask, newTask);
    }


    //a
    public static StringBuilder getTasks(){
        StringBuilder result = new StringBuilder();
        for(Integer i : taskManager.keySet()){
            result.append("Задача №").append(i).append("\n").append(taskManager.get(i));
        }
        return result;
    }
}
