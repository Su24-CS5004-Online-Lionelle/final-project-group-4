package model;

import model.DataMgmt.Stock;
import model.NetUtils.MarketDataAPI;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import java.util.Comparator;
import java.util.List;

/**
 * The Model class represents the core data management logic of the application.
 * It interacts with the MarketDataAPI to fetch stock data and provides methods
 * to
 * retrieve and sort stock records.
 */
public class Model {
    private MarketDataAPI marketDataAPI;

    /**
     * Constructs a Model and initializes the MarketDataAPI with the provided API
     * key.
     *
     * @param apiKey the API key used to access the AlphaVantage API
     */
    public Model(String apiKey) {
        this.marketDataAPI = new MarketDataAPI(apiKey);
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
     * Retrieves and sorts the stock records based on the query symbol, orderBy
     * field, and order direction.
     *
     * @param query   the stock symbol to query
     * @param orderBy the field to sort by (e.g., "price", "volume", "date")
     * @param order   the order direction ("asc" for ascending, "desc" for
     *                descending)
     * @return a list of Stock objects sorted according to the specified order
     */
    public List<Stock> getRecord(String query, String orderBy, String order) {
        // Fetch the stock data for the given query symbol
        TimeSeriesResponse response = fetchStockData(query);
        // Convert the response to a list of Stock objects
        List<Stock> stocks = Stock.fromTimeSeriesResponse(response, query);

        // Add sorting logic based on orderBy and order if needed
        if (orderBy != null && order != null) {
            // Get the comparator based on the orderBy field
            Comparator<Stock> comparator = getComparator(orderBy);
            // Reverse the comparator if the order is "desc"
            if ("desc".equalsIgnoreCase(order)) {
                comparator = comparator.reversed();
            }
            // Sort the list of stocks using the comparator
            stocks.sort(comparator);
        }

        // Return the sorted list of stocks
        return stocks;
    }

    /**
     * Returns a comparator for the specified orderBy field.
     *
     * @param orderBy the field to sort by (e.g., "price", "volume", "date")
     * @return a Comparator for the specified field
     */
    private Comparator<Stock> getComparator(String orderBy) {
        switch (orderBy.toLowerCase()) {
            case "price":
                return Comparator.comparing(Stock::getPrice); // Compare by price
            case "volume":
                return Comparator.comparing(Stock::getVolume); // Compare by volume
            case "date":
                return Comparator.comparing(Stock::getDate); // Compare by date
            default:
                return Comparator.comparing(Stock::getSymbol); // Compare by symbol
        }
    }
}

// public void createStockList(String name) {
// StockList newList = new StockList(name);
// stockLists.add(newList);
// }

// public StockList getStockList(String name) {
// return stockLists.stream()
// .filter(list -> list.getName().equalsIgnoreCase(name))
// .findFirst()
// .orElse(null);
// }

// public void addStockToList(String listName, Stock stock) {
// StockList list = getStockList(listName);
// if (list != null) {
// list.addStock(stock);
// }
// }

// public void removeStockFromList(String listName, Stock stock) {
// StockList list = getStockList(listName);
// if (list != null) {
// list.removeStock(stock);
// }
// }

// public void addStock(String stockToAdd) {
// // Implementation for adding stock to a persistent store (e.g., database,
// file,
// // etc.)
// System.out.println("Adding stock: " + stockToAdd);
// }

// public void rmStock(String stockToRm) {
// // Implementation for removing stock from a persistent store (e.g., database,
// // file, etc.)
// System.out.println("Removing stock: " + stockToRm);
// }
