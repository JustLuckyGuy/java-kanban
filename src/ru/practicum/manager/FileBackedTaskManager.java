package ru.practicum.manager;

import ru.practicum.exeptions.ManagerCollectionTaskExeption;
import ru.practicum.exeptions.ManagerSaveException;
import ru.practicum.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileBackedTaskManager extends InMemoryTaskManager {
    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.Task
    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    //Метод, который будет обновлять нашу задачу, немного изменил предложенный вариант
    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    //Метод, которые удаляет все Задачи из структуры tasks
    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    //Метод, который удаляет Задачу по его идентификатору
    @Override
    public void removeTaskById(int idTask) {
        super.removeTaskById(idTask);
        save();
    }

    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.SubTask
    @Override
    public void createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save();
    }

    //Метод, который обновляет Подзадачу
    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    //Метод, который удаляет все Подзадачи из структуры subTasks
    @Override
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        save();
    }

    //Метод, который удаляет Подзадачу по его идентификатору
    @Override
    public void removeSubTaskById(int idSubTask) {
        super.removeSubTaskById(idSubTask);
        save();
    }

    //Метод, который добавляет в структуру HashMap, новый объект класса ru.practicum.model.Epic
    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    //Метод, который обновляет Эпик
    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    //Метод, который удаляет все Эпики из структуры
    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    //Метод, который удаляет Эпик по его идентификатору
    @Override
    public void removeEpicById(int idEpic) {
        super.removeEpicById(idEpic);
        save();
    }

    private void save() {
        try (BufferedWriter saveTasksInFile = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("C:\\Users\\Kiruxa\\Desktop\\YanPrac7.csv"), StandardCharsets.UTF_8)
        )) {
            saveTasksInFile.write("\uFEFF");
            saveTasksInFile.write("id,type,name,status,description,epic");
            saveTasksInFile.newLine();

            for (Task task : getTasks()) {
                saveTasksInFile.write(toString(task));
            }
            for (Epic epic : getEpic()) {
                saveTasksInFile.write(toString(epic));
            }
            for (SubTask subTask : getSubTasks()) {
                saveTasksInFile.write(toString(subTask));
            }

        }catch (FileNotFoundException e){
            throw new ManagerSaveException("Файл не найден или используется в данный момент");
        } catch (IOException e) {
            throw new ManagerSaveException("Возникла ошибка при сохранении задачи в файл");
        }
    }

    private String toString(Task task) {
        TaskType taskType;
        String idOfEpicInSubtask = "";
        if (task instanceof Epic) {
            taskType = TaskType.EPIC;
        } else if (task instanceof SubTask) {
            taskType = TaskType.SUBTASK;
        } else {
            taskType = TaskType.TASK;
        }

        if(taskType == TaskType.SUBTASK) idOfEpicInSubtask = String.valueOf(((SubTask) task).getIdEpic());

        return idOfEpicInSubtask.isEmpty() ? String.format("%d,%s,%s,%s,%s\n", task.getId(), taskType, task.getNameTask(),
                task.getStatusTask(), task.getDescription())
                : String.format("%d,%s,%s,%s,%s,%s\n", task.getId(), taskType, task.getNameTask(),
                task.getStatusTask(), task.getDescription(), idOfEpicInSubtask);

    }

    public Task fromStringToTask(String value){
        if(value.isBlank()){
            return null;
        }
        String[] taskFields = value.split(",");


        try {
            int id_Task = Integer.parseInt(taskFields[0]);
            if(id_Task<0){
                throw new IllegalArgumentException("ID задачи не может быть отрицательны, проверьте ID задачи на корректность");
            }
            TaskType taskType = TaskType.valueOf(taskFields[1]);
            String taskName = taskFields[2];
            StatusTask statusTask = StatusTask.valueOf(taskFields[3]);
            String taskDescription = taskFields[4];

            switch(taskType){
                case TASK -> {
                    Task task = new Task(taskName,taskDescription,statusTask);
                    task.setId(id_Task);
                    return task;
                }
                case EPIC -> {
                    Epic epic = new Epic(taskName, taskDescription);
                    epic.setStatusTask(statusTask);
                    epic.setId(id_Task);
                    return epic;
                }
                case SUBTASK -> {
                    int idEpic = Integer.parseInt(taskFields[5]);
                    SubTask subTask = new SubTask(taskName, taskDescription, idEpic);
                    subTask.setStatusTask(statusTask);
                    subTask.setId(id_Task);
                    return subTask;
                }
            }

        } catch (NumberFormatException e) {
            throw new ManagerCollectionTaskExeption("Неверно введен id в файле, проверьте пожалуйста файл");
        } catch (IllegalArgumentException e){
            throw new ManagerCollectionTaskExeption(e.getMessage());
        }
        return null;
    }

}
