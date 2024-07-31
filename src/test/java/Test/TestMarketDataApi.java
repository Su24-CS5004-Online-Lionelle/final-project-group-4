package Test;

import model.NetUtils.MarketDataAPI;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMarketDataApi {

    private MarketDataAPI marketDataAPI;

    @BeforeEach
    public void setUp() {
        // Initialize MarketDataAPI with an invalid API key
        marketDataAPI = new MarketDataAPI("INVALID_API_KEY");
    }

    @Test
    public void testFetchStockData() {
        TimeSeriesResponse response = marketDataAPI.fetchStockData("AMA");
        assertNotNull(response);
        assertNotNull(response.getErrorMessage());
        assertEquals(
                "Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.",
                response.getErrorMessage());
    }

    @Test
    public void testFetchStockDataWithEmptySymbol() {
        TimeSeriesResponse response = marketDataAPI.fetchStockData("");
        assertNotNull(response);
        assertNotNull(response.getErrorMessage());
        assertEquals(
                "Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.",
                response.getErrorMessage());
    }

    @Test
    public void testFetchStockDataWithNullSymbol() {
        TimeSeriesResponse response = marketDataAPI.fetchStockData(null);
        assertNotNull(response);
        assertNotNull(response.getErrorMessage());
        assertEquals(
                "Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.",
                response.getErrorMessage());
    }

    @Test
    public void testFetchStockDataWithNonExistentSymbol() {
        TimeSeriesResponse response = marketDataAPI.fetchStockData("NONEXISTENT");
        assertNotNull(response);
        assertNotNull(response.getErrorMessage());
        assertEquals(
                "Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.",
                response.getErrorMessage());
    }
}
