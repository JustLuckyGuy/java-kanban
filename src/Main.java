public class Main {

    public static void main(String[] args) {
       TaskManager.addTask(new Task("Погулять с собакой"),"Купить хлеб по пути");
       TaskManager.addTask(new Task("Сходить попить"), null);
       TaskManager.addTask(new Task("dsadsaь"), "dsadas");
       TaskManager.addTask(new Task("fafa"), null);
       TaskManager.addTask(new Task("ewqeqw"), null);
       System.out.println(TaskManager.getTasks());
       System.out.println(TaskManager.getTaskById(5));
       TaskManager.removeTaskById(5);

       TaskManager.updateTask(1, new Task("Купить хлеб"), null, StatusTask.DONE);
       System.out.println(TaskManager.getTasks());
        TaskManager.removeAllTasks();
        System.out.println(TaskManager.getTasks());


    }
//    static Scanner in = new Scanner(System.in);
//
//
//    public static void main(String[] args) {
//
//        while (true){
//            printMenu();
//            int command = in.nextInt();
//            in.nextLine();
//            switch (command){
//                case 1:
//                    break;
//                case 2:
//                    TaskManager.addTasks(in);
//                    TaskManager.getTask();
//                    break;
//                case 3: break;
//                case 4: break;
//                case 5:
//                    System.out.println("Выход из приложения \n" + " ".repeat(3) + "До свидания!");
//                    return;
//            }
//            System.out.println("-".repeat(20));
//        }
//    }
//
//    public static void printMenu(){
//        System.out.println("Выберите операцию: ");
//        System.out.println("1 - Показать список всех задач");
//        System.out.println("2 - Добавить задачу");
//        System.out.println("3 - Добавить подзадачу");
//        System.out.println("4 - Добавить эпик");
//        System.out.println("5 - Выход");
//    }
}
