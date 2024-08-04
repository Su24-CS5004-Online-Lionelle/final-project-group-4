package model.DataMgmt;

import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private String symbol;

    /**
     * Constructs a Stock object with the specified open, high, low, close, volume, date, and
     * symbol.
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

    /**
     * Default constructor for the Stock class.
     */
    public Stock() {}

    /**
     * Gets the stock opening price.
     *
     * @return the opening price
     */
    public double getOpen() {
        return open;
    }

    /**
     * Sets the stock opening price.
     *
     * @param open the opening price
     */
    public void setOpen(double open) {
        this.open = open;
    }

    /**
     * Gets the stock highest price.
     *
     * @return the highest price
     */
    public double getHigh() {
        return high;
    }

    /**
     * Sets the stock highest price.
     *
     * @param high the highest price
     */
    public void setHigh(double high) {
        this.high = high;
    }

    /**
     * Gets the stock lowest price.
     *
     * @return the lowest price
     */
    public double getLow() {
        return low;
    }

    /**
     * Sets the stock lowest price.
     *
     * @param low the lowest price
     */
    public void setLow(double low) {
        this.low = low;
    }

    /**
     * Gets the stock closing price.
     *
     * @return the closing price
     */
    public double getClose() {
        return close;
    }

    /**
     * Sets the stock closing price.
     *
     * @param close the closing price
     */
    public void setClose(double close) {
        this.close = close;
    }

    /**
     * Gets the stock trading volume.
     *
     * @return the trading volume
     */
    public long getVolume() {
        return volume;
    }

    /**
     * Sets the stock trading volume.
     *
     * @param volume the trading volume
     */
    public void setVolume(long volume) {
        this.volume = volume;
    }

    /**
     * Gets the date of the stock data.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the stock data.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the stock symbol.
     *
     * @return the stock symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the stock symbol.
     *
     * @param symbol the stock symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Parses the date string to a Date object.
     *
     * @return the Date object parsed from the date string
     */
    public Date getDateAsDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts a TimeSeriesResponse from AlphaVantage API to a list of Stock objects.
     *
     * @param response the TimeSeriesResponse from AlphaVantage API
     * @return a list of Stock objects
     */
    public List<Stock> fromTimeSeriesResponse(TimeSeriesResponse response) {
        List<Stock> stocks = new ArrayList<>();
        List<StockUnit> stockUnits = response.getStockUnits();

        for (StockUnit unit : stockUnits) {
            String date = unit.getDate();
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
        StringBuilder sb = new StringBuilder();
        sb.append("Date: ").append(date).append("\n");
        sb.append("Symbol: ").append(symbol).append("\n");
        sb.append("Open: ").append(open).append("\n");
        sb.append("High: ").append(high).append("\n");
        sb.append("Low: ").append(low).append("\n");
        sb.append("Close: ").append(close).append("\n");
        sb.append("Volume: ").append(volume).append("\n");
        sb.append("----").append("\n");
        return sb.toString();
    }

    /**
     * Compares this Stock object to the specified object. The result is true if and only if the
     * argument is not null and is a Stock object that has the same symbol and date as this object.
     *
     * @param obj the object to compare this Stock against
     * @return true if the given object represents a Stock equivalent to this stock, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Stock stock = (Stock) obj;
        return symbol.equals(stock.getSymbol()) && date.equals(stock.getDate());
    }

    /**
     * Returns a hash code value for this Stock object.
     *
     * @return a hash code value for this stock
     */
    @Override
    public int hashCode() {
        return Objects.hash(symbol, date);
    }
}
