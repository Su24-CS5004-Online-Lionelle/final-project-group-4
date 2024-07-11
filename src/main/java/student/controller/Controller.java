package student.controller;

import student.model.Model;
import student.model.entity.Stock;
import student.model.entity.StockList;
import student.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {
    private String query;
    private String order;
    private String orderBy;
    private String stockToAdd;
    private String stockToRm;

    public void run() throws Exception {

        // handle user input
        View.welcome();
        String input = View.getInput();
        parseInput(input);

        // init model
        Model model = new Model();

        // data manipulation
        if (query != null) {
            List<Stock> records = model.getRecord(query, orderBy, order);
            View.display(records);
        } else if (stockToAdd != null) {
            model.addStock(stockToAdd);
        } else if (stockToRm != null) {
            model.rmStock(stockToRm);
        } else {
            throw new Exception("Invalid Input");
        }
    }
    
    private void parseInput(String input) throws Exception {
        String[] strArray = input.split("\\s+");

        List<String> strList = new ArrayList<>(Arrays.asList(strArray));

        if (strList.contains("help")) {
            throw new Exception("Show Helper Message");
        }

        checkShow(strList);
        checkOrderBy(strList);
        checkOrder(strList);
        checkAdd(strList);
        checkRm(strList);
    }

    private void checkShow(List<String> strList) throws Exception {
        String showInput = nextArg(strList, "show");

        if (showInput == null) {
            return;
        }

        this.query = showInput;
    }

    private void checkOrderBy(List<String> strList) throws Exception {
        String input = nextArg(strList, "-a");

        if (input == null) {
            return;
        }

        this.orderBy = input;
    }

    private void checkOrder(List<String> strList) throws Exception {
        String input = nextArg(strList, "-o");

        if (input == null) {
            return;
        }

        this.order = input;
    }

    private void checkAdd(List<String> strList) throws Exception {
        String input = nextArg(strList, "add");

        if (input == null) {
            return;
        }

        this.stockToAdd = input;
    }

    private void checkRm(List<String> strList) throws Exception {
        String input = nextArg(strList, "rm");

        if (input == null) {
            return;
        }

        this.stockToRm = input;
    }

    private String nextArg(List<String> strList, String arg) throws Exception {
        int index = strList.indexOf(arg);

        if (index == -1) {
            return null;
        }

        if (index + 1 < strList.size()) {
            strList.remove(index);
            return strList.remove(index);
        } else {
            throw new Exception("Missing Value:" + arg);
        }
    }
}
