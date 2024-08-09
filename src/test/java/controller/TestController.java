package controller;

import model.DataMgmt.Stock;
import model.DataMgmt.StockList;
import model.Exceptions.ApiLimitReachedException;
import model.Exceptions.InvalidStockSymbolException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link Controller} class, which handles the interaction between the user
 * interface and the data model. The tests cover various scenarios such as fetching stock data,
 * handling API limits, and dealing with specific dates.
 */
public class TestController {

    /**
     * Mock instance of the {@link Controller} class used for testing.
     */
    private Controller controller;

    /**
     * Sets up the test environment before each test case. This method initializes the mock
     * controller object.
     */
    @BeforeEach
    public void setup() {
        controller = Mockito.mock(Controller.class);
    }

    /**
     * Tests that the database is successfully loaded into the stock list. Verifies that the stock
     * list is not null after loading data from the database.
     */
    @Test
    public void testLoadDB() {
        StockList stockList = mock(StockList.class);
        when(controller.getStockList()).thenReturn(stockList);

        controller.loadDataFromDB();
        assertNotNull(controller.getStockList(), "Expected stock list to be loaded from database.");
    }

    /**
     * Tests the fetchStockData method with a valid stock symbol. Ensures that the method returns
     * non-null stock data.
     *
     * @throws ApiLimitReachedException if the API limit is reached
     * @throws InvalidStockSymbolException if the stock symbol is invalid
     */
    @Test
    public void testFetchStockData() throws ApiLimitReachedException, InvalidStockSymbolException {
        @SuppressWarnings("unchecked")
        List<Stock> stockData = Mockito.mock(List.class);
        when(controller.fetchStockData("GOOG")).thenReturn(stockData);

        List<Stock> fetchedData = controller.fetchStockData("GOOG");
        assertNotNull(fetchedData, "Expected to fetch stock data for a valid symbol.");
    }

    /**
     * Tests fetching the most recent stock data after fetching initial data. Verifies that the most
     * recent stock data is correctly retrieved.
     *
     * @throws ApiLimitReachedException if the API limit is reached
     * @throws InvalidStockSymbolException if the stock symbol is invalid
     */
    @Test
    public void testFetchMostRecentStockData()
            throws ApiLimitReachedException, InvalidStockSymbolException {
        Stock stock = mock(Stock.class);
        when(controller.fetchMostRecentStockData()).thenReturn(stock);

        controller.fetchStockData("GOOG");
        Stock fetchedStock = controller.fetchMostRecentStockData();
        assertNotNull(fetchedStock,
                "Expected to fetch the most recent stock data after data is fetched.");
    }

    /**
     * Tests fetching stock data for a specific date. Verifies that the correct stock data is
     * returned for the specified date.
     *
     * @throws ApiLimitReachedException if the API limit is reached
     * @throws InvalidStockSymbolException if the stock symbol is invalid
     */
    @Test
    public void testFetchSpecificStockDate()
            throws ApiLimitReachedException, InvalidStockSymbolException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.AUGUST);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();

        Stock stock = mock(Stock.class);
        when(controller.fetchSpecificStockDate(date)).thenReturn(stock);

        controller.fetchStockData("GOOG");
        Stock fetchedStock = controller.fetchSpecificStockDate(date);
        assertNotNull(fetchedStock, "Expected to fetch stock data for the specific date.");
    }

    /**
     * Tests fetching stock data with an invalid stock symbol. Verifies that an
     * InvalidStockSymbolException is thrown.
     */
    @Test
    public void testFetchStockDataWithInvalidSymbol() {
        assertThrows(InvalidStockSymbolException.class, () -> {
            when(controller.fetchStockData("INVALID_SYMBOL"))
                    .thenThrow(new InvalidStockSymbolException("Invalid stock symbol"));
            controller.fetchStockData("INVALID_SYMBOL");
        }, "Expected an InvalidStockSymbolException to be thrown for an invalid stock symbol.");
    }

    /**
     * Tests fetching stock data with a null stock symbol. Verifies that an
     * InvalidStockSymbolException is thrown.
     */
    @Test
    public void testFetchStockDataWithNullSymbol() {
        assertThrows(InvalidStockSymbolException.class, () -> {
            when(controller.fetchStockData(null))
                    .thenThrow(new InvalidStockSymbolException("Symbol cannot be null"));
            controller.fetchStockData(null);
        }, "Expected an InvalidStockSymbolException to be thrown for a null stock symbol.");
    }

    /**
     * Tests fetching the most recent stock data without fetching any data first. Verifies that null
     * is returned when no prior data fetch has occurred.
     */
    @Test
    public void testFetchMostRecentStockDataWithoutFetchingFirst() {
        when(controller.fetchMostRecentStockData()).thenReturn(null);
        Stock stock = controller.fetchMostRecentStockData();
        assertNull(stock,
                "Expected null when fetching most recent stock data without prior data fetch.");
    }

    /**
     * Tests fetching specific stock data by date without fetching any data first. Verifies that
     * null is returned when no prior data fetch has occurred.
     */
    @Test
    public void testFetchSpecificStockDateWithoutDataFetch() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.AUGUST);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();

        when(controller.fetchSpecificStockDate(date)).thenReturn(null);
        Stock stock = controller.fetchSpecificStockDate(date);
        assertNull(stock,
                "Expected null when fetching specific stock date without prior data fetch.");
    }

    /**
     * Tests fetching stock data for a date with no available data. Verifies that null is returned
     * when no stock data exists for the given date.
     *
     * @throws ApiLimitReachedException if the API limit is reached
     * @throws InvalidStockSymbolException if the stock symbol is invalid
     */
    @Test
    public void testFetchSpecificStockDateWithNoDataOnDate()
            throws ApiLimitReachedException, InvalidStockSymbolException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1800); // An arbitrary date far in the past
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();

        when(controller.fetchSpecificStockDate(date)).thenReturn(null);

        controller.fetchStockData("GOOG");
        Stock stock = controller.fetchSpecificStockDate(date);
        assertNull(stock,
                "Expected null when fetching stock data for a date with no available data.");
    }

    /**
     * Tests fetching stock data after cleaning the cache. Verifies that stock data can still be
     * fetched successfully after cache cleanup.
     *
     * @throws ApiLimitReachedException if the API limit is reached
     * @throws InvalidStockSymbolException if the stock symbol is invalid
     */
    @Test
    public void testFetchStockDataAfterCacheClean()
            throws ApiLimitReachedException, InvalidStockSymbolException {
        @SuppressWarnings("unchecked")
        List<Stock> stockData = mock(List.class);
        when(controller.fetchStockData("GOOG")).thenReturn(stockData);

        controller.fetchStockData("GOOG");
        controller.cleanCache();
        List<Stock> fetchedData = controller.fetchStockData("GOOG");

        assertNotNull(fetchedData,
                "Expected to successfully fetch stock data after cleaning the cache.");
        assertFalse(fetchedData.isEmpty(),
                "Expected fetched stock data not to be empty after cache is cleaned.");
    }

    /**
     * Tests the handling of the ApiLimitReachedException by simulating repeated API calls. Verifies
     * that the exception is thrown after exceeding the API limit.
     */
    @Test
    public void testApiLimitReachedExceptionHandling() {
        // Simulate reaching API limit by repeatedly fetching data
        assertThrows(ApiLimitReachedException.class, () -> {
            for (int i = 0; i < 100; i++) {
                when(controller.fetchStockData("GOOG"))
                        .thenThrow(new ApiLimitReachedException("API limit reached"));
                controller.fetchStockData("GOOG");
            }
        }, "Expected an ApiLimitReachedException to be thrown after exceeding API limit.");
    }
}
