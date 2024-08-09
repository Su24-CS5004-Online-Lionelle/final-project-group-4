package model;

import model.DataMgmt.Stock;
import model.DataMgmt.StockList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.io.IOException;

/**
 * Unit tests for the StockList class, which manages a list of Stock objects.
 */
public class TestStockList {

    private StockList stockList;

    /**
     * Sets up a new StockList instance before each test.
     */
    @BeforeEach
    public void setup() {
        stockList = new StockList();
    }

    /**
     * Tests that a stock can be successfully added to the StockList.
     */
    @Test
    public void testAddStock() {
        Stock stock = Model.getRandomStock();
        stockList.addStock(stock);
        assertEquals(1, stockList.getStockList().size());
    }

    /**
     * Tests that a stock can be removed from the StockList by its index.
     */
    @Test
    public void testRemoveById() {
        Stock stock = Model.getRandomStock();
        stockList.addStock(stock);
        stockList.removeById(0);
        assertEquals(0, stockList.getStockList().size());
    }

    /**
     * Tests that the StockList can be sorted by the "Open" price in descending order.
     */
    @Test
    public void testSortBy() {
        Stock stock1 = Model.getRandomStock();
        Stock stock2 = Model.getRandomStock();
        Stock stock3 = Model.getRandomStock();

        stock1.setOpen(1);
        stock2.setOpen(2);
        stock3.setOpen(3);

        stockList.addStock(stock1);
        stockList.addStock(stock2);
        stockList.addStock(stock3);

        stockList.sortBy("Open");
        assertEquals(stock3, stockList.getStockList().get(0));
        assertEquals(stock2, stockList.getStockList().get(1));
        assertEquals(stock1, stockList.getStockList().get(2));
    }

    /**
     * Tests that adding a duplicate stock does not increase the size of the StockList.
     */
    @Test
    public void testAddDuplicateStock() {
        Stock stock = Model.getRandomStock();
        stockList.addStock(stock);
        stockList.addStock(stock);
        assertEquals(1, stockList.getStockList().size(),
                "Expected only one instance of the stock in the list.");
    }

    /**
     * Tests that stocks with case-insensitive symbols are treated as the same stock.
     */
    @Test
    public void testAddStockCaseInsensitive() {
        Stock stock1 = Model.getRandomStock();
        stock1.setSymbol("aapl");
        Stock stock2 = Model.getRandomStock();
        stock2.setSymbol("AAPL");

        stockList.addStock(stock1);
        stockList.addStock(stock2);
        assertEquals(1, stockList.getStockList().size(),
                "Expected stocks with case-insensitive symbols to be considered the same.");
    }

    /**
     * Tests that the clearAll method correctly clears all stocks from the StockList.
     */
    @Test
    public void testClearAll() {
        Stock stock1 = Model.getRandomStock();
        Stock stock2 = Model.getRandomStock();

        stockList.addStock(stock1);
        stockList.addStock(stock2);

        stockList.clearAll();
        assertEquals(0, stockList.getStockList().size(),
                "Expected the stock list to be empty after clearAll() is called.");
    }

    /**
     * Tests sorting of the StockList by the "Close" price in descending order.
     */
    @Test
    public void testSortByClose() {
        Stock stock1 = Model.getRandomStock();
        Stock stock2 = Model.getRandomStock();
        Stock stock3 = Model.getRandomStock();

        stock1.setClose(100);
        stock2.setClose(200);
        stock3.setClose(150);

        stockList.addStock(stock1);
        stockList.addStock(stock2);
        stockList.addStock(stock3);

        stockList.sortBy("Close");

        assertEquals(stock2, stockList.getStockList().get(0),
                "Expected stock2 to be first after sorting by 'Close'.");
        assertEquals(stock3, stockList.getStockList().get(1),
                "Expected stock3 to be second after sorting by 'Close'.");
        assertEquals(stock1, stockList.getStockList().get(2),
                "Expected stock1 to be last after sorting by 'Close'.");
    }

    /**
     * Tests the output of the StockList to an XML file and ensures the file content matches the
     * StockList content.
     *
     * @throws IOException if an I/O error occurs during file operations
     */
    @Test
    public void testOutputToFile() throws IOException {
        Stock stock = Model.getRandomStock();
        stockList.addStock(stock);

        File tempFile = File.createTempFile("test", ".xml");
        stockList.output(tempFile);

        XmlMapper mapper = new XmlMapper();
        StockList readStockList = mapper.readValue(tempFile, StockList.class);

        assertEquals(stockList.getStockList().size(), readStockList.getStockList().size(),
                "Expected the saved and read stock lists to have the same size.");
    }

    /**
     * Tests the toString method when the StockList is empty, ensuring it returns the expected
     * message.
     */
    @Test
    public void testEmptyStockListToString() {
        String expectedMessage =
                "Thank you for using Alpha Vantage! Our standard API rate limit is 25 requests per day. \n"
                        + "Please subscribe to any of the premium plans at \n"
                        + "https://www.alphavantage.co/premium/ to instantly remove all daily rate limits.\n";

        assertEquals(expectedMessage, stockList.toString(),
                "Expected the toString() message for an empty list to match.");
    }

    /**
     * Tests the behavior of the sortBy method when an invalid sorting option is provided. The
     * default sorting by symbol should be applied.
     */
    @Test
    public void testInvalidSortOption() {
        Stock stock1 = Model.getRandomStock();
        Stock stock2 = Model.getRandomStock();
        stock1.setSymbol("B");
        stock2.setSymbol("A");

        stockList.addStock(stock1);
        stockList.addStock(stock2);

        stockList.sortBy("InvalidOption");
        assertEquals(stock2, stockList.getStockList().get(0),
                "Expected default sorting by symbol when an invalid option is given.");
    }
}
