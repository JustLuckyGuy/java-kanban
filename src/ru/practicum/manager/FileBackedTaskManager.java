package ru.practicum.manager;

import ru.practicum.exeptions.ManagerCollectionTaskException;
import ru.practicum.exeptions.ManagerLoadException;
import ru.practicum.exeptions.ManagerSaveException;
import ru.practicum.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private static final String BOM = "\uFEFF";
    private static final String HEADER = "id,type,name,status,description,dateStart,duration,epic,";
    private final File workedFile;


    //Создал конструктор, который поможет ссылаться на один и тот же файл
    public FileBackedTaskManager(File file) {
        this.workedFile = file;
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
                new OutputStreamWriter(new FileOutputStream(workedFile, false), StandardCharsets.UTF_8))) {

            if (!workedFile.exists()) {
                throw new ManagerSaveException("Файл не найден или используется в данный момент");
            }
            saveTasksInFile.write(BOM);
            saveTasksInFile.write(HEADER);
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

        } catch (IOException e) {
            throw new ManagerSaveException("Возникла ошибка при сохранении задачи в файл");
        }

    }

    //Метод, который будет преобразовываться строку в объект Task
    private Task fromStringToTask(String value) {
        String[] taskFields = value.split(",");

        int idTask = Integer.parseInt(taskFields[0]);
        if (idTask < 0) {
            throw new ManagerCollectionTaskException("ID задачи не может быть отрицательны, проверьте ID задачи на корректность");
        }

        TaskType taskType = TaskType.valueOf(taskFields[1]);
        String taskName = taskFields[2];
        StatusTask statusTask = StatusTask.valueOf(taskFields[3]);
        String taskDescription = taskFields[4];
        LocalDateTime startTime = !taskFields[5].isBlank() ? LocalDateTime.parse(taskFields[5], formatter) : null;
        Duration duration = !taskFields[6].isBlank() ? Duration.ofMinutes(Long.parseLong(taskFields[6])) : null;

        switch (taskType) {
            case TASK -> {
                Task task = new Task(taskName, taskDescription, statusTask);
                task.setId(idTask);
                task.setStartTime(startTime);
                task.setDuration(duration);
                return task;
            }
            case EPIC -> {
                Epic epic = new Epic(taskName, taskDescription);
                epic.setStatusTask(statusTask);
                epic.setId(idTask);
                epic.setStartTime(startTime);
                epic.setDuration(duration);
                return epic;
            }
            case SUBTASK -> {
                int idEpic = Integer.parseInt(taskFields[7]);
                SubTask subTask = new SubTask(taskName, taskDescription, idEpic);
                subTask.setStatusTask(statusTask);
                subTask.setId(idTask);
                subTask.setStartTime(startTime);
                subTask.setDuration(duration);
                return subTask;
            }
        }
        return null;

    }

    //Метод, который будет выгружать задачи из файла в FileBackedTaskManager
    private void loadTasks(File file) {
        try (BufferedReader readFromFile = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            int currentMaxId = 0;
            String line;
            readFromFile.readLine();
            while (readFromFile.ready()) {
                line = readFromFile.readLine();

                if (line.isBlank()) {
                    continue;
                }
                Task task = fromStringToTask(line);

                if (task == null) {
                    throw new ManagerLoadException("Не найдена задача");
                }
                if (currentMaxId < task.getId()) {
                    currentMaxId = task.getId();
                }
                switch (task.getType()) {
                    case TASK -> {
                        tasks.put(task.getId(), task);
                        if (task.getStartTime() != null) priorityTaskOfData.add(task);
                    }
                    case EPIC -> epics.put(task.getId(), (Epic) task);
                    case SUBTASK -> {
                        subTasks.put(task.getId(), (SubTask) task);
                        if (task.getStartTime() != null) priorityTaskOfData.add(task);
                        Epic epic = epics.get(((SubTask) task).getIdEpic());
                        epic.addSubTask((SubTask) task);
                    }
                }
            }
            counterID = currentMaxId + 1;
        } catch (FileNotFoundException e) {
            throw new ManagerLoadException("Файл не найден" + e.getMessage());
        } catch (IOException e) {
            throw new ManagerLoadException("Произошла ошибка при чтении файла " + e.getMessage());
        } catch (Exception e) {
            throw new ManagerLoadException("Произошла ошибка с чтением задачи " + e.getMessage());
        }

    }

    //Метод, который позволит выгружать задачи из файла при создании объекта FileBackedManager
    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        manager.loadTasks(file);
        return manager;

    }

    //Перегруженный метод toString
    private String toString(Task task) {

        TaskType taskType = task.getType();
        String idOfEpicInSubtask = "";

        if (taskType == TaskType.SUBTASK) idOfEpicInSubtask = String.valueOf(((SubTask) task).getIdEpic());

        return String.format("%d,%s,%s,%s,%s,%s,%s,%s\n",
                task.getId(),
                taskType,
                task.getNameTask(),
                task.getStatusTask(),
                task.getDescription(),
                task.getStartTime() != null ? task.getStartTime().format(formatter) : "",
                task.getDuration() != null ? task.getDuration().toMinutes() : "",
                idOfEpicInSubtask);

    }


}
