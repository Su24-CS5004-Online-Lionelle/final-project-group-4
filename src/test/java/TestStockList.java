import model.DataMgmt.Stock;
import model.DataMgmt.StockList;
import model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStockList {

    private StockList stockList;


    @BeforeEach
    public void setup() {
        stockList = new StockList();
    }

    @Test
    public void testAddStock() {
        Stock stock = Model.getRandomStock();
        stockList.addStock(stock);
        assertEquals(1, stockList.getStockList().size());
    }

    @Test
    public void testRemoveById() {
        Stock stock = Model.getRandomStock();
        stockList.addStock(stock);
        stockList.removeById(0);
        assertEquals(0, stockList.getStockList().size());
    }

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
}
