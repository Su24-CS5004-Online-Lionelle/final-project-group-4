package controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import model.DataMgmt.Stock;
import model.DataMgmt.StockList;
import model.Model;
import model.Exceptions.ApiLimitReachedException;
import model.Exceptions.InvalidStockSymbolException;
import view.View;
import view.helpers.TableHelper;

import javax.swing.table.DefaultTableModel;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.List;

/**
 * The Controller class handles the interactions between the view and the model. It manages the flow
 * of data and updates between the user interface and the backend logic.
 */
public class Controller {

    /**
     * Reference to the Model component.
     */
    private Model model;

    /**
     * Singleton instance of the Controller class.
     */
    private static Controller instance;

    /**
     * StockList to manage the list of stocks.
     */
    private StockList stockList;

    /**
     * Reference to the View component.
     */
    private View view;

    /**
     * Constructs a Controller and initializes the Model.
     */
    private Controller() {
        this.model = Model.getInstance(); // Initializes the Model
        this.view = new View(this); // Initializes the View with this Controller
        this.stockList = new StockList(); // Initialize a new StockList to ensure it's empty
        view.show(); // Show the GUI
    }

    /**
     * Returns the singleton instance of the Controller, initializing it if necessary.
     *
     * @return the singleton instance of the Controller
     */
    public static synchronized Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * Loads stock data from the database (XML file).
     */
    public void loadDataFromDB() {
        XmlMapper xmlMapper = new XmlMapper(); // Creates an XmlMapper instance for XML processing
        File database = new File("bin/data/data.xml"); // File object pointing to the data file

        // If the database file does not exist or is empty, initialize a new StockList
        if (!database.exists() || database.length() == 0) {
            this.stockList = new StockList();
        } else {
            try {
                // Read the stock data from the XML file and initialize StockList
                this.stockList = xmlMapper.readValue(database, StockList.class);
            } catch (IOException e) {
                e.printStackTrace();
                this.stockList = new StockList();
            }
        }
    }

    /**
     * Gets the StockList managed by the Controller.
     *
     * @return the StockList
     */
    public StockList getStockList() {
        return stockList;
    }

    /**
     * Fetches the stock data for the given stock symbol and returns it to the view.
     *
     * @param symbol the stock symbol to fetch data for
     * @return the list of Stock objects containing the stock data
     * @throws InvalidStockSymbolException if the stock symbol is invalid
     * @throws ApiLimitReachedException if the API limit is reached
     */
    public List<Stock> fetchStockData(String symbol)
            throws InvalidStockSymbolException, ApiLimitReachedException {
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

    /**
     * Fetches the stock data for a specific date.
     *
     * @param value the date to fetch data for
     * @return the Stock object for the specified date, or null if not found
     */
    public Stock fetchSpecificStockDate(Date value) {
        return model.fetchSpecificStockDate(value);
    }

    /**
     * Clears the cache by removing all stored stock data.
     */
    public void cleanCache() {
        model.cleanCache();
    }

    /**
     * Imports stock data from a file and updates the database.
     *
     * @param selectedFile the file to import
     * @param tableModel the table model to update the table
     */
    public void importStockDataFromFile(File selectedFile, DefaultTableModel tableModel) {
        try {
            model.importStockDataFromFile(selectedFile);
            loadDataFromDB(); // Reload the data after importing
            TableHelper.updateTableModel(tableModel); // Update the table model
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the stock data to a temporary XML file for parsing by the PromptDatePicker.
     *
     * @param stockData the list of stock data to write
     */
    public void writeTempStockDataToXML(List<Stock> stockData) {
        model.writeTempStockDataToXML(stockData);
    }

    /**
     * Reads the temporary stock data XML file and returns the list of stock data.
     *
     * @return the list of Stock objects read from the XML file
     */
    public List<Stock> readTempStockDataFromXML() {
        return model.readTempStockDataFromXML();
    }
}
