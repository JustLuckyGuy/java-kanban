import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {

    private static final HashMap<Integer, Task> tm = new HashMap<>();

    //d
    public static void addTask(Task task){
        tm.put(task.getIdTask(), task);
    }

    //b
    public static void removeAllTasks(){
        tm.clear();
    }

    //f
    public static void removeTaskById(int idTask){
        tm.remove(idTask);
    }

    //c
    public static Task getTaskById(int idTask){
        if(tm.containsKey(idTask)){
            return tm.get(idTask);
        }
        return null;
    }

    //e
    public static void updateTask(int idTask, Task newTask){
        tm.replace(idTask, tm.get(idTask), newTask);

    }

    public static void addDescription(int idTask, String nameDescription){
        Task task = tm.get(idTask);

        ArrayList<String> newDescription = task.getDescription();
        newDescription.add(nameDescription);

        task.setDescription(newDescription);

    }

    //a
    public static StringBuilder getTasks(){
        StringBuilder result = new StringBuilder();
        for(Integer i : tm.keySet()){
            result.append("Задача №").append(tm.get(i).getIdTask()).append("\n").append(tm.get(i));
        }
        return result;
    }


}
