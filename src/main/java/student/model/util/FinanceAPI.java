package student.model.util;

import student.model.entity.Stock;

public class FinanceAPI {
    public static Stock getStock(String stockToAdd) {

        // TODO: Jubal

        Stock stock = new Stock();

        // test code below
        stock.setCode("INTC");
        stock.setPrice("32.2485");
        stock.setChange("2.019");
        stock.setPeg("1.74");
        stock.setDividend("2014");

        return stock;
    }
}
