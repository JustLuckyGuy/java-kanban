package ru.practicum.manager;

import ru.practicum.exeptions.ManagerCollectionTaskExeption;
import ru.practicum.exeptions.ManagerLoadException;
import ru.practicum.exeptions.ManagerSaveException;
import ru.practicum.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private final static Path pathToProject = Paths.get("C:\\Users\\Kiruxa\\Desktop\\Practicum\\java-kanban\\");
    File file1;

    //Создал конструктор, который поможет ссылаться на один и тот же объект
    public FileBackedTaskManager(String filename) {
        File file = new File(filename);
        this.file1 = file.isAbsolute() ? file : new File(pathToProject.toFile() + File.separator + filename);
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeTaskById(int idTask) {
        super.removeTaskById(idTask);
        save();
    }

    @Override
    public void createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        save();
    }

    @Override
    public void removeSubTaskById(int idSubTask) {
        super.removeSubTaskById(idSubTask);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeEpicById(int idEpic) {
        super.removeEpicById(idEpic);
        save();
    }

    //Метод, который сохраняет в текущий файл, новые задачи
    private void save() {
        try (BufferedWriter saveTasksInFile = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file1, false), StandardCharsets.UTF_8)
        )) {
            if(!file1.exists()){
                throw new FileNotFoundException();
            }
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

        } catch (FileNotFoundException e) {
            throw new ManagerSaveException("Файл не найден или используется в данный момент");
        } catch (IOException e) {
            throw new ManagerSaveException("Возникла ошибка при сохранении задачи в файл");
        }
    }

    //Метод, который будет преобразовываться строку в объект Task
    private Task fromStringToTask(String value) {
        String[] taskFields = value.split(",");

        try {
            int id_Task = Integer.parseInt(taskFields[0]);
            if (id_Task < 0) {
                throw new IllegalArgumentException("ID задачи не может быть отрицательны, проверьте ID задачи на корректность");
            }
            TaskType taskType = TaskType.valueOf(taskFields[1]);
            String taskName = taskFields[2];
            StatusTask statusTask = StatusTask.valueOf(taskFields[3]);
            String taskDescription = taskFields[4];

            switch (taskType) {
                case TASK -> {
                    Task task = new Task(taskName, taskDescription, statusTask);
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
            throw new ManagerCollectionTaskExeption("Неверно введен id в файле, проверьте пожалуйста файл" + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ManagerCollectionTaskExeption(e.getMessage());
        }
        return null;
    }

    //Метод, который будет выгружать задачи из файла в FileBackedTaskManager
    private void loadTasks(File file) {
        try (BufferedReader readFromFile = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), StandardCharsets.UTF_8
                )
        )) {
            if (!file.isAbsolute()) {
                file1 = new File(pathToProject + file.getName());
            }
            String line;
            readFromFile.readLine();
            while (readFromFile.ready()) {
                line = readFromFile.readLine();

                if (line.isBlank()) {
                    continue;
                }

                Task task = fromStringToTask(line);
                assert task != null;
                if (task instanceof Epic) {
                    super.createEpic((Epic) task);
                } else if (task instanceof SubTask) {
                    super.createSubTask((SubTask) task);
                } else {
                    super.createTask(task);
                }
            }
        } catch (FileNotFoundException e) {
            throw new ManagerLoadException("Файл не найден" + e.getMessage());
        } catch (IOException e) {
            throw new ManagerLoadException("Произошла ошибка при чтении файла" + e.getMessage());
        } catch (AssertionError e) {
            throw new ManagerLoadException("Произошла ошибка с чтением задачи" + e.getMessage());
        }
    }

    //Метод, который позволит выгружать задачи из файла при создании объекта FileBackedManager
    public static FileBackedTaskManager loadFromFile(File file) {

        if (!file.isAbsolute()) {
            file = new File(pathToProject.toFile(), file.getName());
        }


        FileBackedTaskManager manager = new FileBackedTaskManager(file.getName());
        manager.loadTasks(file);
        return manager;
    }

    //Перегруженный метод toString
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

        if (taskType == TaskType.SUBTASK) idOfEpicInSubtask = String.valueOf(((SubTask) task).getIdEpic());

        return idOfEpicInSubtask.isEmpty() ? String.format("%d,%s,%s,%s,%s\n", task.getId(), taskType, task.getNameTask(),
                task.getStatusTask(), task.getDescription())
                : String.format("%d,%s,%s,%s,%s,%s\n", task.getId(), taskType, task.getNameTask(),
                task.getStatusTask(), task.getDescription(), idOfEpicInSubtask);
    }

}
