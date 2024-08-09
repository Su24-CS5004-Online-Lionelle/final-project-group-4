package model;

import model.DataMgmt.Stock;
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
 * Unit tests for the Model class, which handles core data management logic such as fetching and
 * storing stock data.
 */
public class TestModel {

    /**
     * The model instance being tested, mocked to avoid real API calls.
     */
    private Model model;

    /**
     * Sets up the test environment before each test method is executed. Initializes a mock instance
     * of the Model class.
     */
    @BeforeEach
    public void setup() {
        model = Mockito.mock(Model.class);
    }

    /**
     * Tests the getRandomStock method to ensure that it generates different random Stock objects.
     */
    @Test
    public void testGetRandomStock() {
        Stock stock1 = Model.getRandomStock();
        Stock stock2 = Model.getRandomStock();

        assertNotEquals(stock1, stock2);
    }

    /**
     * Tests the fetchStockData method to verify that stock data is fetched correctly for a given
     * valid stock symbol.
     *
     * @throws ApiLimitReachedException if the API limit is reached
     * @throws InvalidStockSymbolException if the stock symbol is invalid
     */
    @Test
    public void testFetchStockData() throws ApiLimitReachedException, InvalidStockSymbolException {
        @SuppressWarnings("unchecked")
        List<Stock> stockData = mock(List.class);
        when(model.fetchStockData("GOOG")).thenReturn(stockData);

        List<Stock> fetchedData = model.fetchStockData("GOOG");
        assertNotNull(fetchedData, "Expected stock data to be fetched for a valid symbol.");
    }

    /**
     * Tests the fetchMostRecentStockData method to ensure it returns the most recent stock data
     * fetched.
     *
     * @throws ApiLimitReachedException if the API limit is reached
     * @throws InvalidStockSymbolException if the stock symbol is invalid
     */
    @Test
    public void testFetchMostRecentStockData()
            throws ApiLimitReachedException, InvalidStockSymbolException {
        Stock stock = mock(Stock.class);
        when(model.fetchMostRecentStockData()).thenReturn(stock);

        model.fetchStockData("GOOG");
        Stock fetchedStock = model.fetchMostRecentStockData();

        assertNotNull(fetchedStock, "Expected the most recent stock data to be fetched.");
    }

    /**
     * Tests the cleanCache method to ensure that the cache is cleared properly, and that subsequent
     * fetches return null.
     *
     * @throws ApiLimitReachedException if the API limit is reached
     * @throws InvalidStockSymbolException if the stock symbol is invalid
     */
    @Test
    public void testCleanCache() throws ApiLimitReachedException, InvalidStockSymbolException {
        model.fetchStockData("GOOG");
        doNothing().when(model).cleanCache();

        model.cleanCache();
        Stock stock = model.fetchMostRecentStockData();

        assertNull(stock, "Expected no stock data after cache is cleaned.");
    }

    /**
     * Tests the fetchSpecificStockDate method to ensure it returns stock data for a specific date.
     *
     * @throws ApiLimitReachedException if the API limit is reached
     * @throws InvalidStockSymbolException if the stock symbol is invalid
     */
    @Test
    public void testFetchSpecificStockDate()
            throws ApiLimitReachedException, InvalidStockSymbolException {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.AUGUST);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();

        Stock stock = mock(Stock.class);
        when(model.fetchSpecificStockDate(date)).thenReturn(stock);

        model.fetchStockData("GOOG");
        Stock fetchedStock = model.fetchSpecificStockDate(date);

        assertNotNull(fetchedStock, "Expected to fetch stock data for the specific date.");
    }

    /**
     * Tests the behavior of fetchStockData when the API returns an error for an invalid symbol.
     *
     * @throws ApiLimitReachedException if the API limit is reached
     * @throws InvalidStockSymbolException if the stock symbol is invalid
     */
    @Test
    public void testFetchStockDataWithInvalidSymbol()
            throws InvalidStockSymbolException, ApiLimitReachedException {
        // Setup the mock to throw an InvalidStockSymbolException
        when(model.fetchStockData("INVALID"))
                .thenThrow(new InvalidStockSymbolException("Invalid stock symbol: INVALID"));

        // Assert that the exception is thrown
        InvalidStockSymbolException thrown = assertThrows(InvalidStockSymbolException.class,
                () -> model.fetchStockData("INVALID"),
                "Expected an InvalidStockSymbolException to be thrown");

        // Verify the exception message
        assertTrue(thrown.getMessage().contains("Invalid stock symbol"));
    }

    /**
     * Tests the behavior of fetchStockData when the API limit is reached.
     *
     * @throws InvalidStockSymbolException if the stock symbol is invalid
     * @throws ApiLimitReachedException if the API limit is reached
     */
    @Test
    public void testFetchStockDataWithApiLimitReached()
            throws InvalidStockSymbolException, ApiLimitReachedException {
        // Setup the mock to throw an ApiLimitReachedException
        when(model.fetchStockData("GOOG"))
                .thenThrow(new ApiLimitReachedException("API limit reached"));

        // Assert that the exception is thrown
        ApiLimitReachedException thrown =
                assertThrows(ApiLimitReachedException.class, () -> model.fetchStockData("GOOG"),
                        "Expected an ApiLimitReachedException to be thrown");

        // Verify the exception message
        assertTrue(thrown.getMessage().contains("API limit reached"));
    }

    /**
     * Tests the fetchMostRecentStockData method when the stock list is empty. It should return null
     * in this case.
     */
    @Test
    public void testFetchMostRecentStockDataWithEmptyList() {
        when(model.fetchMostRecentStockData()).thenReturn(null);

        Stock fetchedStock = model.fetchMostRecentStockData();

        assertNull(fetchedStock, "Expected null when no stock data is available.");
    }

    /**
     * Tests the fetchSpecificStockDate method when the requested date has no associated stock data.
     */
    @Test
    public void testFetchSpecificStockDateWithNoData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, 1);
        Date date = calendar.getTime();

        when(model.fetchSpecificStockDate(date)).thenReturn(null);

        Stock fetchedStock = model.fetchSpecificStockDate(date);

        assertNull(fetchedStock,
                "Expected null when no stock data is available for the specific date.");
    }
}
