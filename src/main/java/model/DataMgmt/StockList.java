package model.DataMgmt;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import controller.Controller;
import model.Model;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The StockList class represents a list of stock objects and provides methods to manage the list,
 * including adding, removing, sorting, and saving the list to an XML file.
 */
@JacksonXmlRootElement(localName = "stockList")
public class StockList {

    /**
     * List to store the stock objects.
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "stock")
    private List<Stock> stockList;

    /**
     * Path to the database XML file.
     */
    private static final String DATABASE_PATH = "bin/data/data.xml";

    /**
     * Default constructor that initializes the stock list.
     */
    public StockList() {
        this.stockList = new ArrayList<>();
    }

    /**
     * Gets the list of stocks.
     *
     * @return the list of stocks
     */
    public List<Stock> getStockList() {
        return stockList;
    }

    /**
     * Adds a stock to the list and saves to the XML file.
     *
     * @param stock the stock to add
     */
    public void addStock(Stock stock) {
        // Make sure all stock symbols are stored as uppercase
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

    /**
     * Removes a stock from the list by its index and saves the list.
     *
     * @param selectedRow the index of the stock to remove
     */
    public void removeById(int selectedRow) {
        this.stockList.remove(selectedRow);
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sorts the stock list by the specified option and saves the list.
     *
     * @param selectedOption the option to sort by (e.g., "Open", "Close", "High", "Low", "Volume",
     *        "Date", "Name")
     */
    public void sortBy(String selectedOption) {
        switch (selectedOption) {
            case "Open":
                Collections.sort(stockList,
                        (s1, s2) -> -Double.compare(s1.getOpen(), s2.getOpen()));
                break;
            case "Close":
                Collections.sort(stockList,
                        (s1, s2) -> -Double.compare(s1.getClose(), s2.getClose()));
                break;
            case "High":
                Collections.sort(stockList,
                        (s1, s2) -> -Double.compare(s1.getHigh(), s2.getHigh()));
                break;
            case "Low":
                Collections.sort(stockList, (s1, s2) -> -Double.compare(s1.getLow(), s2.getLow()));
                break;
            case "Volume":
                Collections.sort(stockList,
                        (s1, s2) -> -Double.compare(s1.getVolume(), s2.getVolume()));
                break;
            case "Date":
                Collections.sort(stockList, (s1,
                        s2) -> -String.CASE_INSENSITIVE_ORDER.compare(s1.getDate(), s2.getDate()));
                break;
            default:
                Collections.sort(stockList, (s1, s2) -> String.CASE_INSENSITIVE_ORDER
                        .compare(s1.getSymbol(), s2.getSymbol()));
                break;
        }

        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Clears all stocks from the list and saves the empty list.
     */
    public void clearAll() {
        this.stockList.clear();
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the stock list to an XML file.
     *
     * @throws IOException if an I/O error occurs while saving the file
     */
    private void save() throws IOException {
        XmlMapper mapper = new XmlMapper();
        File dataFile = new File(DATABASE_PATH);
        mapper.writeValue(dataFile, this);
    }

    /**
     * Outputs the stock list to the specified XML file.
     *
     * @param outputFile the file to write the stock list to
     * @throws IOException if an I/O error occurs while writing the file
     */
    public void output(File outputFile) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.writeValue(outputFile, this);
    }

    /**
     * Returns a string representation of the stock list.
     *
     * @return a string representation of the stock list
     */
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

    /**
     * Generate 12 default stocks in the stock list.
     */
    public void generateDefault() {
        for (int i = 0; i < 12; i++) {
            Stock stockToAdd = Model.getRandomStock();
            this.stockList.add(stockToAdd);
        }
    }
}
