package model.NetUtils;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * MarketDataAPI is a class that is used to pull stock data from the AlphaVantage API with the
 * provided API key. This API key is an individual key that has no access limitations for this
 * project.
 */
public class MarketDataAPI {

    /**
     * Constructor for the MarketDataAPI class. It initializes the AlphaVantage API with the
     * provided API key and is a singleton class. The choice of singleton came down to consistency
     * of API access and API key management. For scalability the singleton pattern would allow for
     * resource efficiency by avoiding multiple API calls.
     *
     * @param apiKey The API key for AlphaVantage
     */
    public MarketDataAPI(String apiKey) {
        Config config = Config.builder().key(apiKey).timeOut(10).build();
        AlphaVantage.api().init(config);
    }

    /**
     * Pulls stock data from the AlphaVantage API. It is compacted and set to daily so it will print
     * out the last 100 days of stock data. The total type of data supplied by the daily time series
     * is open, high, low, close, and volume.
     *
     * @param symbol The stock symbol to pull data for
     * @return TimeSeriesResponse The response from the API containing the stock data
     */
    public TimeSeriesResponse fetchStockData(String symbol) {
        return AlphaVantage.api().timeSeries().daily().forSymbol(symbol)
                .outputSize(OutputSize.COMPACT).fetchSync();
    }

    // /**
    // * Fetches the stock data for a specific day.
    // *
    // * @param symbol The stock symbol to pull data for
    // * @param date The date to pull the data for in the format "yyyy-MM-dd"
    // * @return StockUnit The stock data for the specified date or null if no data is available for
    // * that date
    // */
    // public StockUnit fetchStockDataForDate(String symbol, String date) {
    // TimeSeriesResponse response = fetchStockData(symbol);
    // List<StockUnit> stockUnits = response.getStockUnits();

    // for (StockUnit unit : stockUnits) {
    // if (unit.getDate().equals(date)) {
    // return unit;
    // }
    // }

    // return null;
    // }

    // /**
    // * Fetches the stock data for the current day.
    // *
    // * @param symbol The stock symbol to pull data for
    // * @return StockUnit The stock data for the current date or null if no data is available for
    // the
    // * current date
    // */
    // public StockUnit fetchStockDataForToday(String symbol) {
    // LocalDate today = LocalDate.now();
    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // String formattedDate = "2024-07-19";

    // return fetchStockDataForDate(symbol, formattedDate);
    // }

}
=======
}

