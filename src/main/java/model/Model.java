package model;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import model.NetUtils.MarketDataAPI;
import model.DataMgmt.Stock;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;

import controller.Controller;

/**
 * The Model class represents the core data management logic of the application. It interacts with
 * the MarketDataAPI to fetch stock data and provides methods to retrieve and sort stock records.
 */
public class Model {

    /**
     * Instance of MarketDataAPI used to interact with the Alpha Vantage API.
     */
    private MarketDataAPI marketDataAPI;

    /**
     * Singleton instance of the Model class.
     */
    private static Model instance;

    /**
     * API key used for authenticating with the Alpha Vantage API.
     */
    private final String apiKey = "SZPBC0GPHK788VZT";

    /**
     * List to store stock data fetched from the API.
     */
    private List<Stock> stocks;

    /**
     * Path to the temporary XML file used for storing fetched stock data.
     */
    private static final String TEMP_XML_PATH = "temp_stock_data.xml";

    /**
     * Constructs a Model and initializes the MarketDataAPI with the provided API key. Private
     * constructor for singleton pattern.
     */
    private Model() {
        // Initialize the MarketDataAPI with the API key
        this.marketDataAPI = new MarketDataAPI(apiKey);
        // Initialize the list to store stock data
        this.stocks = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of the Model class.
     *
     * @return the singleton instance of the Model
     */
    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    /**
     * Generates a random Stock object for demonstration purposes.
     *
     * @return a randomly generated Stock object
     */
    public static Stock getRandomStock() {
        // Create a new Stock object
        Stock randomStock = new Stock();
        Random rand = new Random();

        // Set random values for the stock attributes
        randomStock.setDate(LocalDate.now().toString());
        randomStock.setHigh(rand.nextInt(300));
        randomStock.setLow(rand.nextInt(300));
        randomStock.setClose(rand.nextInt(300));
        randomStock.setOpen(rand.nextInt(300));
        randomStock.setSymbol("Rand" + UUID.randomUUID().toString());
        randomStock.setVolume(rand.nextLong(3000));
        return randomStock;
    }

    /**
     * Fetches the stock data for the given symbol from the MarketDataAPI.
     *
     * @param symbol the stock symbol to fetch data for
     * @return the list of Stock objects containing the stock data
     * @throws InvalidStockSymbolException if the stock symbol is invalid
     * @throws ApiLimitReachedException if the API limit is reached
     */
    public List<Stock> fetchStockData(String symbol)
            throws InvalidStockSymbolException, ApiLimitReachedException {
        // Fetch stock data from the MarketDataAPI
        TimeSeriesResponse response = marketDataAPI.fetchStockData(symbol);

        // Check if the response contains an error message indicating an invalid API call
        if (response.getErrorMessage() != null
                && response.getErrorMessage().contains("Invalid API call")) {
            throw new InvalidStockSymbolException("Invalid stock symbol: " + symbol);
        }

        // Check if the response contains an error message indicating the API limit is reached
        if (response.getErrorMessage() != null && response.getErrorMessage().contains("limit")) {
            throw new ApiLimitReachedException("API limit reached");
        }

        // Check if the response is null or contains no stock units
        if (response == null || response.getStockUnits() == null) {
            throw new InvalidStockSymbolException("Invalid stock symbol: " + symbol);
        }

        // Clear the existing stocks before adding new data
        stocks.clear();
        // Convert the stock units from the response to Stock objects and add them to the list
        for (StockUnit unit : response.getStockUnits()) {
            stocks.add(new Stock(unit.getOpen(), unit.getHigh(), unit.getLow(), unit.getClose(),
                    unit.getVolume(), unit.getDate(), symbol));
        }
        // Write the fetched stock data to a temporary XML file
        writeTempStockDataToXML(stocks);
        return stocks;
    }

    /**
     * Fetches and filters the stock data to return the most recent date's data.
     *
     * @return the most recent Stock object
     */
    public Stock fetchMostRecentStockData() {
        // Find the stock with the most recent date
        return stocks.stream().max(Comparator.comparing(Stock::getDate)).orElse(null);
    }

    /**
     * Clears the cache by removing all stored stock data.
     */
    public void cleanCache() {
        // Clear the list of stored stock data
        stocks.clear();
        // Clear the temporary XML file
        clearTempStockData();
    }

    /**
     * Writes the given content to the database file.
     *
     * @param content the content to write to the database
     */
    public void writeDataToDB(String content) {
        String filePath = "bin/data/data.xml";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the content to the file
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reload the data from the database
        Controller.getInstance().loadDataFromDB();
    }

    /**
     * Imports stock data from a file.
     *
     * @param selectedFile the file to import
     */
    public void importStockDataFromFile(File selectedFile) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(selectedFile.getPath())));
        writeDataToDB(content);
    }

    /**
     * Fetches the stock data for a specific date.
     *
     * @param specificDate the date to fetch data for
     * @return the Stock object for the specified date, or null if not found
     */
    public Stock fetchSpecificStockDate(Date specificDate) {
        // Format the specific date to a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String specificDateString = dateFormat.format(specificDate);

        // Find the stock with the specified date
        Optional<Stock> opStock = stocks.stream()
                .filter(stock -> stock.getDate().equals(specificDateString)).findFirst();
        return opStock.orElse(null);
    }

    /**
     * Writes the stock data to a temporary XML file for parsing by the PromptDatePicker.
     *
     * @param stockData the list of stock data to write
     */
    public void writeTempStockDataToXML(List<Stock> stockData) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(new File(TEMP_XML_PATH), stockData);
            // Log the stock data being written to the file
            System.out.println("Writing to temp XML:");
            stockData.forEach(stock -> System.out.println(stock.getDate()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the temporary XML file.
     */
    private void clearTempStockData() {
        File tempFile = new File(TEMP_XML_PATH);
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    /**
     * Reads the temporary stock data XML file and returns the list of stock data.
     *
     * @return the list of Stock objects read from the XML file
     */
    public List<Stock> readTempStockDataFromXML() {
        XmlMapper xmlMapper = new XmlMapper();
        List<Stock> stockData = new ArrayList<>();
        try {
            stockData = xmlMapper.readValue(new File(TEMP_XML_PATH),
                    new TypeReference<List<Stock>>() {});
            // Log the stock data being read from the file
            System.out.println("Reading from temp XML:");
            stockData.forEach(stock -> System.out.println(stock.getDate()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockData;
    }

    /**
     * Custom exception for invalid stock symbol.
     */
    public static class InvalidStockSymbolException extends Exception {
        public InvalidStockSymbolException(String message) {
            super(message);
        }
    }

    /**
     * Custom exception for API limit reached.
     */
    public static class ApiLimitReachedException extends Exception {
        public ApiLimitReachedException(String message) {
            super(message);
        }
    }
}
