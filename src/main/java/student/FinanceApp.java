package student;

import student.controller.Controller;
import student.view.View;

public final class FinanceApp {

    /**
     * Private constructor to prevent instantiation.
     */
    private FinanceApp() {

    }

    public static void main(String[] args) {

        Controller controller = new Controller();

        try {
            controller.run();
        } catch (Exception e) {
            View.printHelp(e);
        }
    }
}
