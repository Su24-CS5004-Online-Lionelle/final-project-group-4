import model.DataMgmt.Stock;
import model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestStock {


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

        assertEquals(stock1, stock2);
    }

    @Test
    public void testRandomGenerate() {

        Stock stock1 = Model.getRandomStock();
        Stock stock2 = Model.getRandomStock();

        assertNotEquals(stock1, stock2);
    }
}
