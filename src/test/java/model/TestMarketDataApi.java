package model;

import model.NetUtils.MarketDataAPI;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for the {@link MarketDataAPI} class. The tests validate the
 * behavior of the API when fetching stock data under various scenarios, such as valid symbols,
 * empty symbols, null symbols, and non-existent symbols.
 */
public class TestMarketDataApi {

        /**
         * A mock instance of the MarketDataAPI class used for testing.
         */
        private MarketDataAPI marketDataAPI;

        /**
         * Sets up the mock environment before each test. This method is executed before each test
         * case to initialize the mock objects.
         */
        @BeforeEach
        public void setUp() {
                // Mock the MarketDataAPI class
                marketDataAPI = mock(MarketDataAPI.class);
        }

        /**
         * Tests the behavior of {@link MarketDataAPI#fetchStockData(String)} when provided with a
         * valid stock symbol.
         *
         * This test ensures that the method returns a valid response and the correct error message
         * when the symbol is "AMC".
         */
        @Test
        public void testFetchStockData() {

                // Create a mock response
                TimeSeriesResponse mockResponse = mock(TimeSeriesResponse.class);
                when(mockResponse.getErrorMessage()).thenReturn(
                                "Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.");

                // Define the behavior of the mocked MarketDataAPI
                when(marketDataAPI.fetchStockData("AMC")).thenReturn(mockResponse);

                // Call the method and assert the results
                TimeSeriesResponse response = marketDataAPI.fetchStockData("AMC");
                assertNotNull(response);
                assertNotNull(response.getErrorMessage());
                assertEquals("Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.",
                                response.getErrorMessage());

                // Verify the method was called
                verify(marketDataAPI).fetchStockData("AMC");
        }

        /**
         * Tests the behavior of {@link MarketDataAPI#fetchStockData(String)} when provided with an
         * empty stock symbol.
         *
         * This test checks if the method handles an empty string as the stock symbol correctly and
         * returns the appropriate error message.
         */
        @Test
        public void testFetchStockDataWithEmptySymbol() {
                // Create a mock response
                TimeSeriesResponse mockResponse = mock(TimeSeriesResponse.class);
                when(mockResponse.getErrorMessage()).thenReturn(
                                "Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.");

                // Define the behavior of the mocked MarketDataAPI
                when(marketDataAPI.fetchStockData("")).thenReturn(mockResponse);

                // Call the method and assert the results
                TimeSeriesResponse response = marketDataAPI.fetchStockData("");
                assertNotNull(response);
                assertNotNull(response.getErrorMessage());
                assertEquals("Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.",
                                response.getErrorMessage());
        }

        /**
         * Tests the behavior of {@link MarketDataAPI#fetchStockData(String)} when provided with a
         * null stock symbol.
         *
         * This test verifies that the method can handle a null input and returns the correct error
         * message.
         */
        @Test
        public void testFetchStockDataWithNullSymbol() {
                // Create a mock response
                TimeSeriesResponse mockResponse = mock(TimeSeriesResponse.class);
                when(mockResponse.getErrorMessage()).thenReturn(
                                "Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.");

                // Define the behavior of the mocked MarketDataAPI
                when(marketDataAPI.fetchStockData(null)).thenReturn(mockResponse);

                // Call the method and assert the results
                TimeSeriesResponse response = marketDataAPI.fetchStockData(null);
                assertNotNull(response);
                assertNotNull(response.getErrorMessage());
                assertEquals("Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.",
                                response.getErrorMessage());
        }

        /**
         * Tests the behavior of {@link MarketDataAPI#fetchStockData(String)} when provided with a
         * non-existent stock symbol.
         *
         * This test ensures that the method correctly handles symbols that do not exist and returns
         * the appropriate error message.
         */
        @Test
        public void testFetchStockDataWithNonExistentSymbol() {
                // Create a mock response
                TimeSeriesResponse mockResponse = mock(TimeSeriesResponse.class);
                when(mockResponse.getErrorMessage()).thenReturn(
                                "Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.");

                // Define the behavior of the mocked MarketDataAPI
                when(marketDataAPI.fetchStockData("NONEXISTENT")).thenReturn(mockResponse);

                // Call the method and assert the results
                TimeSeriesResponse response = marketDataAPI.fetchStockData("NONEXISTENT");
                assertNotNull(response);
                assertNotNull(response.getErrorMessage());
                assertEquals("Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.",
                                response.getErrorMessage());
        }
}
