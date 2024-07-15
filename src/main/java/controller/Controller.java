package controller;

import model.Model;
import model.DataMgmt.Stock;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import view.View;

import java.util.List;

/**
 * The Controller class handles the interactions between the view and the model.
 * It manages the flow of data and updates between the user interface and the
 * backend logic.
 */
public class Controller {
    private Model model; // Reference to the Model component

    /**
     * Constructs a Controller and initializes the Model with the provided API key.
     * API key will be supplied by the main driver.
     *
     * @param apiKey the API key used to access the AlphaVantage API
     */
    public Controller(String apiKey) {
        this.model = new Model(apiKey); // Initializes the Model with the API key
    }

    /**
     * Runs the main logic of the controller, including displaying the welcome
     * message and prompting the user to enter a stock symbol.
     */
    public void run() {
        View.welcome(); // Display the welcome message
        String symbol = View.getInput("Enter stock symbol: "); // Prompt user for a stock symbol input
        fetchAndDisplayStockData(symbol); // Fetch and display stock data for the entered symbol
        askForMoreStocks(); // Prompt user to check more stocks
    }

    /**
     * Fetches and displays the stock data for the given stock symbol.
     *
     * @param symbol the stock symbol to fetch data for
     */
    public void fetchAndDisplayStockData(String symbol) {
        TimeSeriesResponse response = model.fetchStockData(symbol); // Fetches stock data from the Model
        List<Stock> stocks = Stock.fromTimeSeriesResponse(response, symbol); // Converts response to Stock objects
        View.display(stocks); // Displays the list of Stock objects to the user
    }

    /**
     * Prompts the user if they want to check more stocks and handles the response.
     */
    private void askForMoreStocks() {
        if (View.askForMoreStocks()) { // Checks if the user wants to add more stocks
            String symbol = View.getInput("Enter another stock symbol: "); // Prompt user for another stock symbol
            fetchAndDisplayStockData(symbol); // Fetch and display data for the new symbol
        } else {
            View.goodbye(); // Displays a goodbye message if the user does not want to check more stocks
        }
    }
}

// These features were from Kangnings original implementation and I commented
// them out so I could focus on the core
// functionality of the program in regard to its interaction with the API. The
// methods below will need to be uncommented
// and updated based on how be flesh out the rest of the program in regards to
// features and functionality.

// public void addStockList(String name) {
// model.createStockList(name);
// }

// public void addStockToList(String listName, Stock stock) {
// model.addStockToList(listName, stock);
// }

// public void removeStockFromList(String listName, Stock stock) {
// model.removeStockFromList(listName, stock);
// }

// public void fetchAndDisplayList(String listName, String orderBy, String
// order) {
// List<Stock> records = model.getRecord(listName, orderBy, order);
// fetchAndUpdateStockData(records);
// View.display(records);
// }

// private void fetchAndUpdateStockData(List<Stock> stocks) {
// for (Stock stock : stocks) {
// TimeSeriesResponse response = model.fetchStockData(stock.getSymbol());
// stock.updateFromResponse(response);
// }
// }

// public void addStock(String stockToAdd) {
// model.addStock(stockToAdd);
// }

// public void rmStock(String stockToRm) {
// model.rmStock(stockToRm);
// }

// private String nextArg(List<String> strList, String arg) throws Exception {
// int index = strList.indexOf(arg);

// if (index == -1) {
// return null;
// }

// if (index + 1 < strList.size()) {
// strList.remove(index);
// return strList.remove(index);
// } else {
// throw new Exception("Missing Value:" + arg);
// }
// }
// }