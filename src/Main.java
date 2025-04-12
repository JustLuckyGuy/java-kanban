
public class Main {

    public static void main(String[] args) {
        //Создал 2 Задачи, 1 Эпик с 2-мя подзадачами, 1 Эпик с 1 подзадачей
        Task task1 = new Task("Прогулка");
        Task task2 = new Task("Посмотреть фильм");
        Epic epic1 = new Epic("Очень большая и важная задача");
        Epic epic2 = new Epic("Менее большая задача");
        SubTask subTask1 = new SubTask("Разработать план", epic1.getId());
        SubTask subTask2 = new SubTask("Проконсультироваться с коллективом", epic1.getId());
        SubTask subTask3 = new SubTask("Проверит свою работу на ошибки", epic2.getId());

        //Обновляю Задачи и Эпики и вывожу их на консоль
        TaskManager.createTask(task1);
        TaskManager.createTask(task2);
        TaskManager.updateTask(task1, "Взять с собой собаку", StatusTask.NEW);
        TaskManager.updateTask(task1, "Купить хлеба", StatusTask.NEW);
        System.out.println("Список задач:\n" + TaskManager.getTasks());

        TaskManager.createEpic(epic1);
        TaskManager.createEpic(epic2);
        TaskManager.createSubTask(subTask1);
        TaskManager.createSubTask(subTask2);
        TaskManager.createSubTask(subTask3);
        System.out.println("Список Подзадача:\n" + TaskManager.getSubTasks());
        System.out.println("Список Эпиков:\n" + TaskManager.getEpic());

        //Меняю статус Задачам и подзадачам и вывожу на консоль
        TaskManager.updateTask(task1, StatusTask.DONE);
        TaskManager.updateTask(task2, StatusTask.IN_PROGRESS);
        System.out.println("Список задач:\n" + TaskManager.getTasks());

        TaskManager.updateSubTask(subTask1, StatusTask.IN_PROGRESS);
        TaskManager.updateSubTask(subTask3, StatusTask.DONE);
        System.out.println("Список Эпиков:\n" + TaskManager.getEpic());

        //Удаляю по одной Задаче и Эпику и вывожу на консоль
        TaskManager.removeTaskById(task2.getId());
        TaskManager.removeEpicById(epic2.getId());
        System.out.println("Список задач:\n" + TaskManager.getTasks());
        System.out.println("Список Эпиков:\n" + TaskManager.getEpic());


    }
}
