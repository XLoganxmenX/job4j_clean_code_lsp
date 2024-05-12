package ru.job4j.ood.isp.menu;

import java.util.Optional;
import java.util.Scanner;

public class TodoApp {
    private static final String WELCOME_TEXT_MENU = "Вы в приложении TodoApp! Выберите соответствующее действие:";
    private static final String ROOT_ADD_ACTION_TEXT_MENU = "1. Добавить элемент в корень меню";
    private static final String PARENT_ADD_ACTION_TEXT_MENU = "2. Добавить элемент к родительскому элементу";
    private static final String RUN_ACTION_TEXT_MENU = "3. Вызвать действие меню";
    private static final String VIEW_ALL_TEXT_MENU = "4. Вывести меню в консоль";
    private static final String EXIT_TEXT_MENU = "5. Закрыть приложение";
    private static final ActionDelegate DEFAULT_ACTION = () -> System.out.println("Some action");
    private final Menu backMenu;
    private final Printer printer;

    public TodoApp() {
        this.backMenu = new SimpleMenu();
        this.printer = new Printer();
    }

    public void run() {
        boolean isRun = true;

        while (isRun) {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            int userInput = Integer.parseInt(scanner.nextLine());

            if (1 == userInput) {
                handleAddToRootNewItemMenu(scanner);
            } else if (2 == userInput) {
                handleAddToParentNewItemMenu(scanner);
            } else if (3 == userInput) {
                handleExecuteAction(scanner);
            } else if (4 == userInput) {
                printer.print(backMenu);
            } else if (5 == userInput) {
                isRun = false;
            }
        }
    }

    private void printMenu() {
        String separator = System.lineSeparator();
        System.out.print(WELCOME_TEXT_MENU + separator
                + ROOT_ADD_ACTION_TEXT_MENU + separator
                + PARENT_ADD_ACTION_TEXT_MENU + separator
                + RUN_ACTION_TEXT_MENU + separator
                + VIEW_ALL_TEXT_MENU + separator
                + EXIT_TEXT_MENU + separator
        );
    }

    private void handleAddToRootNewItemMenu(Scanner scanner) {
        System.out.println("Введите название нового элемента меню:");
        String itemName = scanner.nextLine();
        boolean resultAdd = addToRootNewItemMenu(itemName);
        System.out.println(resultAdd ? "Меню успешно добавлено" : "Меню не добавлено");
    }

    private void handleAddToParentNewItemMenu(Scanner scanner) {
        System.out.println("Введите название существующего родительского элемента меню:");
        String parent = scanner.nextLine();
        System.out.println("Введите название нового элемента меню:");
        String itemName = scanner.nextLine();
        boolean resultAdd = addToParentNewItemMenu(parent, itemName);
        System.out.println(resultAdd ? "Меню успешно добавлено" : "Меню не добавлено");
    }

    private void handleExecuteAction(Scanner scanner) {
        System.out.println("Введите название элемента меню:");
        String itemName = scanner.nextLine();
        boolean resultAdd = executeAction(itemName);
        System.out.println(resultAdd ? "Действие выполнено" : "Действие не выполнено");
    }

    private boolean addToRootNewItemMenu(String itemName) {
        return backMenu.add(Menu.ROOT, itemName, DEFAULT_ACTION);
    }

    private boolean addToParentNewItemMenu(String parent, String itemName) {
        return backMenu.add(parent, itemName, DEFAULT_ACTION);
    }

    private boolean executeAction(String itemName) {
        boolean result = false;
        Optional<Menu.MenuItemInfo> menuItemInfo = backMenu.select(itemName);
        if (menuItemInfo.isPresent()) {
            menuItemInfo.get().getActionDelegate().delegate();
            result = true;
        }
        return result;
    }

    public static void main(String[] args) {
        TodoApp app = new TodoApp();
        app.run();
    }
}
