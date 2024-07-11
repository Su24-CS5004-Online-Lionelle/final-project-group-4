package model.DataMgmt;

import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;

import java.util.ArrayList;
import java.util.List;

// This class is demo and it needs to be decided by the group what content based on API we want to give to the user and
// how we want to display it. The team member that is held responsible for Data Management will be responsible for this class.

/**
 * The Stock class represents a stock with its symbol, price, volume, and date.
 * It provides methods to convert from AlphaVantage TimeSeriesResponse to a list
 * of Stock objects.
 */
public class Stock {
    private String symbol;
    private double price;
    private long volume;
    private String date;

    /**
     * Constructs a Stock object with the specified symbol, price, volume, and date.
     *
     * @param symbol the stock symbol
     * @param price  the stock price
     * @param volume the stock trading volume
     * @param date   the date of the stock data
     */
    public Stock(String symbol, double price, long volume, String date) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
        this.date = date;
    }

    // Getters and setters

    /**
     * Returns the stock symbol.
     *
     * @return the stock symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the stock symbol.
     *
     * @param symbol the stock symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the stock price.
     *
     * @return the stock price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the stock price.
     *
     * @param price the stock price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the stock trading volume.
     *
     * @return the stock trading volume
     */
    public long getVolume() {
        return volume;
    }

    /**
     * Sets the stock trading volume.
     *
     * @param volume the stock trading volume
     */
    public void setVolume(long volume) {
        this.volume = volume;
    }

    /**
     * Returns the date of the stock data.
     *
     * @return the date of the stock data
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the stock data.
     *
     * @param date the date of the stock data
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Converts a TimeSeriesResponse from AlphaVantage API to a list of Stock
     * objects.
     *
     * @param response the TimeSeriesResponse from AlphaVantage API
     * @param symbol   the stock symbol
     * @return a list of Stock objects
     */
    public static List<Stock> fromTimeSeriesResponse(TimeSeriesResponse response, String symbol) {
        List<Stock> stocks = new ArrayList<>(); // Initialize a list to hold Stock objects
        for (StockUnit unit : response.getStockUnits()) { // Loop through each StockUnit in the response
            // Create a new Stock object and add it to the list
            stocks.add(new Stock(symbol, unit.getClose(), unit.getVolume(), unit.getDate()));
        }
        return stocks; // Return the list of Stock objects
    }

    /**
     * Returns a string representation of the Stock object.
     *
     * @return a string representation of the Stock object
     */
    @Override
    public String toString() { // we need to decide how we want to display the stock data, don't just accept
                               // this method as is
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                ", volume=" + volume +
                ", date='" + date + '\'' +
                '}';
    }
}