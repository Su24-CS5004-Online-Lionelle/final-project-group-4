package model.DataMgmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StockList {

    private List<Stock> stockList;

    public StockList() {
        this.stockList = new ArrayList<>();
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public void addStock(Stock stock) {
        for (Stock stock1 : stockList) {
            if (Objects.equals(stock1.getSymbol(), stock.getSymbol())) {
                return;
            }
        }

        this.stockList.add(stock);
    }

    @Override
    public String toString() {
        if (stockList.isEmpty()) {
            return "Thank you for using Alpha Vantage! Our standard API rate limit is 25 requests per day. \n" +
                    "Please subscribe to any of the premium plans at \n" +
                    "https://www.alphavantage.co/premium/ to instantly remove all daily rate limits.\n";
        }

        StringBuilder sb = new StringBuilder();
        for (Stock stock : stockList) {
            sb.append(stock.toString());
        }

        return sb.toString();
    }
}
