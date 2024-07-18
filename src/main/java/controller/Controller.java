package controller;

import model.Model;
import model.DataMgmt.Stock;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import view.View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The Controller class handles the interactions between the view and the model. It manages the flow
 * of data and updates between the user interface and the backend logic.
 */
public class Controller {
    private Model model; // Reference to the Model component

    /**
     * Constructs a Controller and initializes the Model with the provided API key. API key will be
     * supplied by the main driver.
     *
     * @param apiKey the API key used to access the AlphaVantage API
     */
    public Controller(String apiKey) {
        this.model = new Model(apiKey); // Initializes the Model with the API key
    }

    /**
     * Runs the main logic of the controller, including displaying the welcome message and prompting
     * the user to enter a stock symbol.
     */
    public void run() {
        View.welcome(); // Display the welcome message
        String symbol = View.getInput("Enter stock symbol: "); // Prompt user for a stock symbol
                                                               // input
        String dateOption = View
                .getInput("Do you want to fetch data for today or a specific date? (today/date): ");

        if (dateOption.equalsIgnoreCase("today")) {
            fetchAndDisplayStockDataForToday(symbol); // Fetch and display stock data for today
        } else {
            String date = View.getInput("Enter date (yyyy-MM-dd): "); // Prompt user for a specific
                                                                      // date
            fetchAndDisplayStockDataForDate(symbol, date); // Fetch and display stock data for the
                                                           // entered date
        }

        askForMoreStocks(); // Prompt user to check more stocks
    }

    /**
     * Fetches and displays the stock data for the given stock symbol and date.
     *
     * @param symbol the stock symbol to fetch data for
     * @param date the date to fetch data for
     */
    public void fetchAndDisplayStockDataForDate(String symbol, String date) {
        StockUnit stockUnit = model.fetchStockDataForDate(symbol, date); // Fetches stock data for
                                                                         // the given date
        if (stockUnit != null) {
            Stock stock = new Stock(stockUnit.getOpen(), stockUnit.getHigh(), stockUnit.getLow(),
                    stockUnit.getClose(), stockUnit.getVolume(), date, symbol);
            View.display(List.of(stock)); // Displays the stock data for the specified date
        } else {
            View.displayError("No data available for the specified date: " + date);
        }
    }

    /**
     * Fetches and displays the stock data for the given stock symbol for today.
     *
     * @param symbol the stock symbol to fetch data for
     */
    public void fetchAndDisplayStockDataForToday(String symbol) {
        StockUnit stockUnit = model.fetchStockDataForToday(symbol); // Fetches stock data for today
        if (stockUnit != null) {
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Stock stock = new Stock(stockUnit.getOpen(), stockUnit.getHigh(), stockUnit.getLow(),
                    stockUnit.getClose(), stockUnit.getVolume(), today, symbol);
            View.display(List.of(stock)); // Displays the stock data for today
        } else {
            View.displayError("No data available for today.");
        }
    }

    /**
     * Prompts the user if they want to check more stocks and handles the response.
     */
    private void askForMoreStocks() {
        if (View.askForMoreStocks()) { // Checks if the user wants to check more stocks
            String symbol = View.getInput("Enter another stock symbol: "); // Prompt user for
                                                                           // another stock symbol
            String dateOption = View.getInput(
                    "Do you want to fetch data for today or a specific date? (today/date): ");

            if (dateOption.equalsIgnoreCase("today")) {
                fetchAndDisplayStockDataForToday(symbol); // Fetch and display stock data for today
            } else {
                String date = View.getInput("Enter date (yyyy-MM-dd): "); // Prompt user for a
                                                                          // specific date
                fetchAndDisplayStockDataForDate(symbol, date); // Fetch and display stock data for
                                                               // the entered date
            }
            askForMoreStocks(); // Recursively ask if the user wants to check more stocks
        } else {
            View.goodbye(); // Displays a goodbye message if the user does not want to check more
                            // stocks
        }
    }
}
