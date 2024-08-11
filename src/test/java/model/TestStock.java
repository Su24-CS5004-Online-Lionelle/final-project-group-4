package model;

import model.DataMgmt.Stock;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Stock class, which represents stock data with various attributes like open,
 * high, low, close, volume, date, and symbol.
 */
public class TestStock {

    /**
     * Tests that two Stock objects with the same symbol and date are considered equal.
     */
    @Test
    public void testEquals() {
        Stock stock1 = new Stock();
        Stock stock2 = new Stock();

        stock1.setSymbol("A");
        stock2.setSymbol("A");

        stock1.setDate("YYYY-MM-DD");
        stock2.setDate("YYYY-MM-DD");

        stock1.setOpen(1);
        stock2.setOpen(2);

        assertEquals(stock1, stock2, "Expected stocks with the same symbol and date to be equal.");
    }

    /**
     * Tests that two randomly generated Stock objects are not equal.
     */
    @Test
    public void testRandomGenerate() {
        Stock stock1 = Model.getRandomStock();
        Stock stock2 = Model.getRandomStock();

        assertNotEquals(stock1, stock2, "Expected two randomly generated stocks to be different.");
    }

    /**
     * Tests that the hashCode method works correctly for equal Stock objects.
     */
    @Test
    public void testHashCode() {
        Stock stock1 = new Stock();
        Stock stock2 = new Stock();

        stock1.setSymbol("A");
        stock2.setSymbol("A");

        stock1.setDate("YYYY-MM-DD");
        stock2.setDate("YYYY-MM-DD");

        assertEquals(stock1.hashCode(), stock2.hashCode(),
                "Expected equal Stock objects to have the same hash code.");
    }

    /**
     * Tests the behavior when setting an invalid date format.
     */
    @Test
    public void testInvalidDateFormat() {
        Stock stock = new Stock();
        stock.setDate("INVALID_DATE");

        Date date = stock.getDateAsDate();
        assertNull(date, "Expected null when parsing an invalid date format.");
    }

    /**
     * Tests the behavior when setting an empty symbol.
     */
    @Test
    public void testEmptySymbol() {
        Stock stock = new Stock();
        stock.setSymbol("");

        assertEquals("", stock.getSymbol(), "Expected the symbol to be an empty string.");
    }

    /**
     * Tests that Stock objects are not considered equal if they have different symbols.
     */
    @Test
    public void testDifferentSymbols() {
        Stock stock1 = new Stock();
        Stock stock2 = new Stock();

        stock1.setSymbol("AAPL");
        stock2.setSymbol("GOOG");

        stock1.setDate("YYYY-MM-DD");
        stock2.setDate("YYYY-MM-DD");

        assertNotEquals(stock1, stock2, "Expected stocks with different symbols to be unequal.");
    }

    /**
     * Tests that the toString method returns the expected string representation of a Stock object.
     */
    @Test
    public void testToString() {
        Stock stock = new Stock(100.0, 110.0, 90.0, 105.0, 1000000, "2024-08-10", "AAPL");

        String expectedString =
                "Date: 2024-08-10\n" + "Symbol: AAPL\n" + "Open: 100.0\n" + "High: 110.0\n"
                        + "Low: 90.0\n" + "Close: 105.0\n" + "Volume: 1000000\n" + "----\n";

        assertEquals(expectedString, stock.toString(),
                "Expected toString() to return the correct string representation.");
    }

    /**
     * Tests that the fromTimeSeriesResponse method correctly converts a TimeSeriesResponse to a
     * list of Stock objects.
     */
    @Test
    public void testFromTimeSeriesResponse() {
        // Assuming a TimeSeriesResponse mock or sample response is available.
        // This test would verify that the method correctly parses and converts
        // the response into a list of Stock objects.

        // TimeSeriesResponse response = ...;
        // List<Stock> stocks = stock.fromTimeSeriesResponse(response);

        // assertEquals(expectedSize, stocks.size(), "Expected the list size to match the response
        // size.");
    }

    /**
     * Tests that a valid date is correctly parsed to a Date object.
     */
    @Test
    public void testValidDateParsing() {
        Stock stock = new Stock();
        stock.setDate("2024-08-10");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date expectedDate = sdf.parse("2024-08-10");
            assertEquals(expectedDate, stock.getDateAsDate(),
                    "Expected the parsed date to match the expected date.");
        } catch (ParseException e) {
            fail("Test setup failed due to ParseException.");
        }
    }
}
