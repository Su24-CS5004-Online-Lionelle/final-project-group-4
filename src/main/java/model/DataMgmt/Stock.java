package model.DataMgmt;

import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Stock class represents a stock with its open, high, low, close, volume, and date. It provides
 * methods to convert from AlphaVantage TimeSeriesResponse to a list of Stock objects and to convert
 * Stock objects to JSON format.
 */
@JacksonXmlRootElement(localName = "stock")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {

    @JsonProperty("1. open")
    @JacksonXmlProperty(localName = "open")
    private double open;

    @JsonProperty("2. high")
    @JacksonXmlProperty(localName = "high")
    private double high;

    @JsonProperty("3. low")
    @JacksonXmlProperty(localName = "low")
    private double low;

    @JsonProperty("4. close")
    @JacksonXmlProperty(localName = "close")
    private double close;

    @JsonProperty("5. volume")
    @JacksonXmlProperty(localName = "volume")
    private long volume;

    @JacksonXmlProperty(localName = "date")
    private String date;

    @JacksonXmlProperty(localName = "symbol")
    private String symbol; // Add the symbol field

    /**
     * Constructs a Stock object with the specified open, high, low, close, volume, and date.
     *
     * @param open the stock opening price
     * @param high the stock highest price
     * @param low the stock lowest price
     * @param close the stock closing price
     * @param volume the stock trading volume
     * @param date the date of the stock data
     * @param symbol the stock symbol
     */
    public Stock(double open, double high, double low, double close, long volume, String date,
            String symbol) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.date = date;
        this.symbol = symbol;
    }

    public Stock() {
    }

    // Getters and setters

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Converts a TimeSeriesResponse from AlphaVantage API to a list of Stock objects.
     *
     * @param response the TimeSeriesResponse from AlphaVantage API
     * @return a list of Stock objects
     */
    public List<Stock> fromTimeSeriesResponse(TimeSeriesResponse response) {
        List<Stock> stocks = new ArrayList<>();
        Map<String, StockUnit> stockData = (Map<String, StockUnit>) response.getStockUnits();

        for (Map.Entry<String, StockUnit> entry : stockData.entrySet()) {
            String date = entry.getKey();
            StockUnit unit = entry.getValue();
            stocks.add(new Stock(unit.getOpen(), unit.getHigh(), unit.getLow(), unit.getClose(),
                    unit.getVolume(), date, symbol));
        }

        return stocks;
    }

    /**
     * Converts a list of Stock objects to JSON format.
     *
     * @param stocks the list of Stock objects
     * @return JSON representation of the stock data
     */
    public static String toJson(List<Stock> stocks) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        for (Stock stock : stocks) {
            ObjectNode stockNode = mapper.createObjectNode();
            stockNode.put("1. open", stock.getOpen());
            stockNode.put("2. high", stock.getHigh());
            stockNode.put("3. low", stock.getLow());
            stockNode.put("4. close", stock.getClose());
            stockNode.put("5. volume", stock.getVolume());
            stockNode.put("symbol", stock.getSymbol());
            rootNode.set(stock.getDate(), stockNode);
        }

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a string representation of the Stock object.
     *
     * @return a string representation of the Stock object
     */
    @Override
    public String toString() {
        // 使用 StringBuilder 来构建完整的字符串
        StringBuilder sb = new StringBuilder();

        sb.append("Date: ").append(date).append("\n");
        sb.append("Symbol: ").append(symbol).append("\n");
        sb.append("Open: ").append(open).append("\n");
        sb.append("High: ").append(high).append("\n");
        sb.append("Low: ").append(low).append("\n");
        sb.append("Close: ").append(close).append("\n");
        sb.append("Volume: ").append(volume).append("\n");
        sb.append("----").append("\n"); // Separator for each stock

        // 将 StringBuilder 转换为 String
        String result = sb.toString();
        return result;
    }

}
