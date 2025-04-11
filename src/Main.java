public class Main {

    public static void main(String[] args) {
       TaskManager.addTask(new Task("Погулять с собакой"));
       TaskManager.addDescription(1, "Взять поводок");
       TaskManager.addDescription(1, "Одеть собаку");
       TaskManager.addTask(new Task("Сходить попить"));
       System.out.println(TaskManager.getTasks());
       System.out.println(TaskManager.getTaskById(2));


        TaskManager.removeTaskById(1);
        TaskManager.updateTask(2, new Task("Купить хлеб"));
        System.out.println(TaskManager.getTasks());

        TaskManager.removeAllTasks();
        System.out.println("After!");
        TaskManager.getTasks();

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
