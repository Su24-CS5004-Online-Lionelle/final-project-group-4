package model;

import model.DataMgmt.Stock;
import model.NetUtils.MarketDataAPI;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The Model class represents the core data management logic of the application. It interacts with
 * the MarketDataAPI to fetch stock data and provides methods to retrieve and sort stock records.
 */
public class Model {

    private MarketDataAPI marketDataAPI;
    private static Model instance;

    /**
     * Constructs a Model and initializes the MarketDataAPI with the provided API key.
     *
     * @param apiKey the API key used to access the AlphaVantage API
     */
    public Model(String apiKey) {

        this.marketDataAPI = new MarketDataAPI(apiKey);
    }

    /**
     * Returns the singleton instance of the Model class.
     *
     * @param apiKey the API key used to initialize the MarketDataAPI
     * @return the singleton instance of the Model
     */
    public static synchronized Model getInstance(String apiKey) {
        if (instance == null) {
            instance = new Model(apiKey);
        }
        return instance;
    }


    /**
     * Fetches the stock data for the given symbol from the MarketDataAPI.
     *
     * @param symbol the stock symbol to fetch data for
     * @return the TimeSeriesResponse containing the stock data
     */
    public TimeSeriesResponse fetchStockData(String symbol) {
        return marketDataAPI.fetchStockData(symbol);
    }

    /**
     * Fetches the stock data for the given symbol and date from the MarketDataAPI.
     *
     * @param symbol the stock symbol to fetch data for
     * @param date the date to fetch data for in the format "yyyy-MM-dd"
     * @return the StockUnit containing the stock data for the specified date
     */
    public StockUnit fetchStockDataForDate(String symbol, String date) {
        return marketDataAPI.fetchStockDataForDate(symbol, date);
    }

    /**
     * Fetches the stock data for the given symbol for the current date from the MarketDataAPI.
     *
     * @param symbol the stock symbol to fetch data for
     * @return the StockUnit containing the stock data for the current date
     */
    public StockUnit fetchStockDataForToday(String symbol) {
        return marketDataAPI.fetchStockDataForToday(symbol);
    }

    /**
     * Retrieves and sorts the stock records based on the query symbol, orderBy field, and order
     * direction.
     *
     * @param query the stock symbol to query
     * @param orderBy the field to sort by (e.g., "price", "volume", "date")
     * @param order the order direction ("asc" for ascending, "desc" for descending)
     * @return a list of Stock objects sorted according to the specified order
     */
//    public List<Stock> getRecord(String query, String orderBy, String order) {
//        // Fetch the stock data for the given query symbol
//        TimeSeriesResponse response = fetchStockData(query);
//        // Convert the response to a list of Stock objects
//        List<Stock> stocks = Stock.fromTimeSeriesResponse(response);
//
//        // Add sorting logic based on orderBy and order if needed
//        if (orderBy != null && order != null) {
//            // Get the comparator based on the orderBy field
//            Comparator<Stock> comparator = getComparator(orderBy);
//            // Reverse the comparator if the order is "desc"
//            if ("desc".equalsIgnoreCase(order)) {
//                comparator = comparator.reversed();
//            }
//            // Sort the list of stocks using the comparator
//            stocks.sort(comparator);
//        }
//
//        // Return the sorted list of stocks
//        return stocks;
//    }

    /**
     * Returns a comparator for the specified orderBy field.
     *
     * @param orderBy the field to sort by (e.g., "price", "volume", "date")
     * @return a Comparator for the specified field
     */
    private Comparator<Stock> getComparator(String orderBy) {
        switch (orderBy.toLowerCase()) {
            case "open":
                return Comparator.comparing(Stock::getOpen); // Compare by open price
            case "high":
                return Comparator.comparing(Stock::getHigh); // Compare by high price
            case "low":
                return Comparator.comparing(Stock::getLow); // Compare by low price
            case "close":
                return Comparator.comparing(Stock::getClose); // Compare by close price
            case "volume":
                return Comparator.comparing(Stock::getVolume); // Compare by volume
            case "date":
                return Comparator.comparing(Stock::getDate); // Compare by date
            default:
                throw new IllegalArgumentException("Invalid orderBy field: " + orderBy);
        }
    }
}
