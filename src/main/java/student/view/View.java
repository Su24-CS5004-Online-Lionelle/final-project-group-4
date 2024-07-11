package student.view;

import student.model.entity.Stock;
import student.model.entity.StockList;

import java.util.List;
import java.util.Scanner;

public class View {

    private static String helpMessage = """
                help message
                help message
                help message
                """;

    private static String welcomeMessage = """
                Welcome to use our FinanceApp!
                Example input:
                show [stock | category | all] [-a price] [-o asc]
                add [stock]
                rm [stock]
                
                Your input:
            """;

    public static void printHelp(Exception e) {
        System.err.println(e.getMessage());
        System.out.println(helpMessage);
    }

    public static void welcome() {
        System.out.println(welcomeMessage);
    }

    public static String getInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        return input;
    }

    public static void display(List<Stock> records) {
    }
}
