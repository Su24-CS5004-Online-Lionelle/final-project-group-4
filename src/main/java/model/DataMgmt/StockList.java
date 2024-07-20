package model.DataMgmt;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JacksonXmlRootElement(localName = "stockList")
public class StockList {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "stock")
    private List<Stock> stockList;

    String database = "bin/data.xml";



    public StockList() {

    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public void addStock(Stock stock) throws IOException {
        this.stockList.add(stock);
        save();
    }

    private void save() throws IOException {
        XmlMapper mapper = new XmlMapper();
        File dataFile = new File(database);
        mapper.writeValue(dataFile, this);
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

    public Stock getStockFromSymbol(String symbol) {
        for (Stock stock1 : stockList) {
            if (Objects.equals(stock1.getSymbol(), symbol)) {
                return stock1;
            }
        }
        return null;
    }
}
