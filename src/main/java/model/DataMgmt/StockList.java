package model.DataMgmt;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    // Method to add a stock to the list and save to XML
    public void addStock(Stock stock) {
        // Make sure all stock symbol stores as uppercase
        stock.setSymbol(stock.getSymbol().toUpperCase());
        if (this.stockList.contains(stock)) {
            return;
        }
        this.stockList.add(stock);

        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void removeById(int selectedRow) {
        this.stockList.remove(selectedRow);
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sortBy(String selectedOption) {
        if (selectedOption.equals("Open")) {
            Collections.sort(stockList, (s1, s2) -> -Double.compare(s1.getOpen(), s2.getOpen()));
        } else if (selectedOption.equals("Close")) {
            Collections.sort(stockList, (s1, s2) -> -Double.compare(s1.getClose(), s2.getClose()));
        } else if (selectedOption.equals("High")) {
            Collections.sort(stockList, (s1, s2) -> -Double.compare(s1.getHigh(), s2.getHigh()));
        } else if (selectedOption.equals("Low")) {
            Collections.sort(stockList, (s1, s2) -> -Double.compare(s1.getLow(), s2.getLow()));
        } else if (selectedOption.equals("Volume")) {
            Collections.sort(stockList, (s1, s2) -> -Double.compare(s1.getVolume(), s2.getVolume()));
        } else {
            Collections.sort(stockList, (s1, s2) -> -String.CASE_INSENSITIVE_ORDER.compare(s1.getDate(), s2.getDate()));
        }

        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearAll() {
        this.stockList.clear();
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Save the stock list to an XML file
    private void save() throws IOException {
        XmlMapper mapper = new XmlMapper();
        File dataFile = new File(database);
        mapper.writeValue(dataFile, this);
    }

    public void output(File outputFile) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.writeValue(outputFile, this);
    }
}
