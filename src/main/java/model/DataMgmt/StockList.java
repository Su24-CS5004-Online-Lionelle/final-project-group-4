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

    // Default constructor
    public StockList() {
        this.stockList = new ArrayList<>();
    }

    // Getter for stockList
    public List<Stock> getStockList() {
        return stockList;
    }

    // Setter for stockList
    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    // Method to add a stock to the list and save to XML
    public void addStock(Stock stock) throws IOException {
        this.stockList.add(stock);
        save();
    }

    // Save the stock list to an XML file
    private void save() throws IOException {
        XmlMapper mapper = new XmlMapper();
        File dataFile = new File(database);
        mapper.writeValue(dataFile, this);
    }

    // Load stock list from an XML file
    public void loadStockFromXML() {
        XmlMapper mapper = new XmlMapper();
        File dataFile = new File(database);
        try {
            this.stockList = mapper.readValue(dataFile,
                    mapper.getTypeFactory().constructCollectionType(List.class, Stock.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get a stock by its symbol
    public Stock getStockFromSymbol(String symbol) {
        for (Stock stock : stockList) {
            if (Objects.equals(stock.getSymbol(), symbol)) {
                return stock;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        if (stockList.isEmpty()) {
            return "Thank you for using Alpha Vantage! Our standard API rate limit is 25 requests per day. \n"
                    + "Please subscribe to any of the premium plans at \n"
                    + "https://www.alphavantage.co/premium/ to instantly remove all daily rate limits.\n";
        }

        StringBuilder sb = new StringBuilder();
        for (Stock stock : stockList) {
            sb.append(stock.toString());
        }

        return sb.toString();
    }
}
