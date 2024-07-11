import org.junit.jupiter.api.Test;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

public class testAPI {

    @Test
    public void testAPI() {
        Stock stock = null;
        try {
            stock = YahooFinance.get("INTC");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BigDecimal price = stock.getQuote().getPrice();
        BigDecimal change = stock.getQuote().getChangeInPercent();
        BigDecimal peg = stock.getStats().getPeg();
        BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

        stock.print();
    }
}
