import controller.Controller;
import model.DataMgmt.Stock;
import model.Exceptions.ApiLimitReachedException;
import model.Exceptions.InvalidStockSymbolException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.module.Configuration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestController {

    private Controller controller;

    @BeforeEach
    public void setup() {
        controller = Controller.getInstance();
    }

    @Test
    public void testLoadDB() {

        controller.loadDataFromDB();

        assertNotNull(controller.getStockList());
    }

    @Test
    public void testFetchStockData() throws ApiLimitReachedException, InvalidStockSymbolException {

        List<Stock> stockData = controller.fetchStockData("GOOG");

        assertNotNull(stockData);
    }

    @Test
    public void testFetchMostRecentStockData() throws ApiLimitReachedException, InvalidStockSymbolException {
        controller.fetchStockData("GOOG");
        Stock stock = controller.fetchMostRecentStockData();

        assertNotNull(stock);
    }

    @Test
    public void testFetchSpecificStockDate() throws ApiLimitReachedException, InvalidStockSymbolException {
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

        // use 2024/8/1 as test case
        controller.fetchStockData("GOOG");
        Stock stock = controller.fetchSpecificStockDate(date);

        assertNotNull(stock);
    }

    @Test
    public void testCleanCache() throws ApiLimitReachedException, InvalidStockSymbolException {
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

        // use 2024/8/1 as test case
        controller.fetchStockData("GOOG");

        // clean cache
        controller.cleanCache();
        Stock stock = controller.fetchSpecificStockDate(date);

        assertNull(stock);
    }
}
