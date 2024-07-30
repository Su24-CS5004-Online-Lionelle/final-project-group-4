package controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import model.DataMgmt.StockList;
import model.Model;
import model.DataMgmt.Stock;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import view.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Controller class handles the interactions between the view and the model. It manages the flow
 * of data and updates between the user interface and the backend logic.
 */
public class Controller {
    private Model model; // Reference to the Model component
    private static Controller instance;

    private StockList stockList;
    private View view; // Reference to the View component

    /**
     * Constructs a Controller and initializes the Model with the provided API key. API key will be
     * supplied by the main driver.
     *
     * @param apiKey the API key used to access the AlphaVantage API
     */
    public Controller(String apiKey) throws IOException {
        this.model = Model.getInstance(apiKey); // Initializes the Model with the API key
        this.view = new View(this); // Initializes the View with this Controller
        XmlMapper xmlMapper = new XmlMapper(); // Creates an XmlMapper instance for XML processing

        File database = new File("bin/data.xml"); // File object pointing to the data file

        // Debug prints to verify file reading, added to verify the database exists and what
        // formatted correctly which
        // it is Kangning but I left these there for you to see and also adjust why the terminal is
        // throwing an error
        // the program still runs as needed but it is something we need to have fixed.
        System.out.println("Database file exists: " + database.exists());
        System.out.println("Database file path: " + database.getAbsolutePath());
        System.out.println("Database file length: " + database.length());

        // Added error handling for reading the data file -- Kangning please adjust this to your
        // preference
        // I added some error handling to the file reading in the Controller class. I used a
        // try-catch block to catch
        // any IOExceptions that may occur when reading the file. If an IOException occurs, the
        // catch block will print
        // the stack trace of the exception. This will help you identify the cause of the error and
        // fix it. You can adjust
        // the error handling to your preference, such as displaying an error message to the user or
        // logging the error to a file.
        if (!database.exists() || database.length() == 0) {
            // If the file does not exist or is empty, create an empty StockList
            this.stockList = new StockList();
        } else {
            try {
                // Print the content of the XML file for debugging, last debug that goes with the
                // above to verify
                // the xml contents and formatting
                String xmlContent = new String(java.nio.file.Files.readAllBytes(database.toPath()));
                System.out.println("XML content:\n" + xmlContent);

                // Attempt to read the StockList from the XML file
                this.stockList = xmlMapper.readValue(database, StockList.class);
            } catch (IOException e) {
                // If there's an error parsing the file, log the error and create an empty StockList
                e.printStackTrace();
                this.stockList = new StockList();
            }
        }

        view.show(); // Show the GUI
    }

    /**
     * Returns the singleton instance of the Controller, initializing it if necessary.
     *
     * @param apiKey the API key used to access the AlphaVantage API
     * @return the singleton instance of the Controller
     * @throws IOException if there is an error reading the data file
     */
    public static synchronized Controller getInstance(String apiKey) throws IOException {
        if (instance == null) {
            instance = new Controller(apiKey); // Initializes the singleton instance if it does not
            // exist
        }
        return instance;
    }

    /**
     * Returns the singleton instance of the Controller, throwing an exception if it is not
     * initialized.
     *
     * @return the singleton instance of the Controller
     */
    public static synchronized Controller getInstance() {
        if (instance == null) {
            throw new RuntimeException("Controller is not instantiated"); // Throws an exception if
            // the instance is not
            // initialized
        }
        return instance;
    }

    /**
     * Fetches the stock data for the given stock symbol.
     *
     * @param symbol the stock symbol to fetch data for
     * @return the list of Stock objects containing the stock data
     */
    public List<Stock> fetchStockData(String symbol) {
        TimeSeriesResponse response = model.fetchStockData(symbol); // Fetches stock data for the
        // last 100 tradable days
        List<Stock> stocks = new ArrayList<>();
        if (response != null && response.getStockUnits() != null) {
            for (StockUnit unit : response.getStockUnits()) {
                stocks.add(new Stock(unit.getOpen(), unit.getHigh(), unit.getLow(), unit.getClose(),
                        unit.getVolume(), unit.getDate(), symbol));
            }
        }
        return stocks;
    }

    /**
     * Fetches and displays the stock data for the given stock symbol.
     *
     * @param symbol the stock symbol to fetch data for
     */
    public void fetchAndDisplayStockData(String symbol) {
        List<Stock> stocks = fetchStockData(symbol); // Fetches stock data for the last 100 tradable
        // days
        if (!stocks.isEmpty()) {
            view.display(stocks); // Displays the stock data
        } else {
            view.displayError("No data available for the specified symbol: " + symbol); // Displays
            // an error
            // message
            // if no
            // data is
            // available
        }
    }

    /**
     * Fetches and displays the most recent stock data for the given stock symbol.
     *
     * @param symbol the stock symbol to fetch data for
     */
    public void fetchAndDisplayMostRecentStockData(String symbol) {
        Stock mostRecentStock = model.fetchMostRecentStockData(symbol); // Fetches the most recent
                                                                        // stock data
        if (mostRecentStock != null) {
            view.display(List.of(mostRecentStock)); // Displays the most recent stock data
        } else {
            view.displayError("No data available for the specified symbol: " + symbol); // Displays
                                                                                        // an error
                                                                                        // message
                                                                                        // if no
                                                                                        // data is
                                                                                        // available
        }
    }
}

// Remove this entire method
// public void run() {
// View view = new View(); // Creates a new View instance
// view.welcome(); // Displays the welcome message

// while (true) {
// String symbol = view.getInput("Enter stock symbol: "); // Prompts the user to enter a
// // stock symbol
// fetchAndDisplayStockData(symbol); // Fetches and displays the stock data for the entered
// // symbol

// if (!view.askForMoreStocks()) { // Checks if the user wants to check more stocks
// view.goodbye(); // Displays a goodbye message
// break; // Exits the loop if the user does not want to check more stocks
// }
// }
// }

// Note: fetchAllStock method is not currently implemented in this prototype.
// /**
// * Fetches and returns all stock data for the given symbol.
// *
// * @param symbol the stock symbol to fetch data for
// * @return the StockList containing all fetched stock data
// * @throws IOException if there is an error reading the data file
// */
// public StockList fetchAllStock(String symbol) throws IOException {
// if (stockList.getStockFromSymbol(symbol) == null) {
// StockUnit stockUnit = model.fetchStockData(symbol); // Fetches stock data for the last 100
// tradable days
// if (stockUnit != null) {
// // Creates a Stock object from the fetched data and adds it to the StockList
// String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
// Stock stock = new Stock(stockUnit.getOpen(), stockUnit.getHigh(), stockUnit.getLow(),
// stockUnit.getClose(), stockUnit.getVolume(), today, symbol);
// this.stockList.addStock(stock); // Adds the stock to the StockList
// }
// }

// return stockList; // Returns the updated StockList
// }
