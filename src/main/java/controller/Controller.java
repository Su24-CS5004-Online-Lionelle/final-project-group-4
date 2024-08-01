package controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import model.DataMgmt.StockList;
import model.Model;
import model.DataMgmt.Stock;
import view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
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
     */
    private Controller() {
        this.model = Model.getInstance(); // Initializes the Model with the API key
        this.view = new View(this); // Initializes the View with this Controller
        loadDataFromDB();
        view.show(); // Show the GUI
    }

    public void loadDataFromDB() {
        XmlMapper xmlMapper = new XmlMapper(); // Creates an XmlMapper instance for XML processing

        File database = new File("bin/data.xml"); // File object pointing to the data file

        if (!database.exists() || database.length() == 0) {
            this.stockList = new StockList();
        } else {
            try {
                this.stockList = xmlMapper.readValue(database, StockList.class);
            } catch (IOException e) {
                e.printStackTrace();
                this.stockList = new StockList();
            }
        }
    }

    /**
     * Returns the singleton instance of the Controller, initializing it if necessary.
     *
     * @return the singleton instance of the Controller
     * @throws IOException if there is an error reading the data file
     */
    public static synchronized Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public StockList getStockList() {
        return stockList;
    }


    /**
     * Fetches the stock data for the given stock symbol and returns it to the view.
     *
     * @param symbol the stock symbol to fetch data for
     * @return the list of Stock objects containing the stock data
     */
    public List<Stock> fetchStockData(String symbol) {
        return model.fetchStockData(symbol);
    }

    /**
     * Fetches and returns the most recent stock data for the given stock symbol.
     *
     * @return the most recent Stock object
     */
    public Stock fetchMostRecentStockData() {
        return model.fetchMostRecentStockData();
    }

    public void cleanCache() {
        model.cleanCache();
    }

    public Stock fetchSpecificStockDate(Date value) {
        return model.fetchSpecificStockDate(value);
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
