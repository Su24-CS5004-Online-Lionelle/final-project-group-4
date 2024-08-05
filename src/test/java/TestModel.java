import model.DataMgmt.Stock;
import model.Exceptions.ApiLimitReachedException;
import model.Exceptions.InvalidStockSymbolException;
import model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestModel {

    private Model model;

    @BeforeEach
    public void setup() {
        model = Model.getInstance();
    }

    @Test
    public void testGetRandomStock() {

        Stock stock1 = Model.getRandomStock();
        Stock stock2 = Model.getRandomStock();

        assertNotEquals(stock1, stock2);
    }

    @Test
    public void testFetchStockData() throws ApiLimitReachedException, InvalidStockSymbolException {

        List<Stock> stockData = model.fetchStockData("GOOG");

        assertNotNull(stockData);
    }

    @Test
    public void testFetchMostRecentStockData() throws ApiLimitReachedException, InvalidStockSymbolException {
        model.fetchStockData("GOOG");
        Stock stock = model.fetchMostRecentStockData();

        assertNotNull(stock);
    }

    @Test
    public void testCleanCache() throws ApiLimitReachedException, InvalidStockSymbolException {
        model.fetchStockData("GOOG");

        model.cleanCache();
        Stock stock = model.fetchMostRecentStockData();

        assertNull(stock);
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

        model.fetchStockData("GOOG");
        Stock stock = model.fetchSpecificStockDate(date);

        assertNotNull(stock);
    }
}
