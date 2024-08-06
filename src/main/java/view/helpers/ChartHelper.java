package view.helpers;

import model.DataMgmt.Stock;

import org.knowm.xchart.*;
import org.knowm.xchart.style.XYStyler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import model.DataMgmt.Stock;
import org.knowm.xchart.*;
import org.knowm.xchart.style.OHLCStyler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import model.DataMgmt.Stock;
import org.knowm.xchart.*;
import org.knowm.xchart.style.OHLCStyler;
import org.knowm.xchart.style.XYStyler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class for creating and displaying charts based on stock data.
 */
public class ChartHelper {

    /**
     * Creates and returns a chart panel with the provided stock data.
     *
     * @param stockData the list of Stock objects to display in the chart
     * @return a JPanel containing the chart
     */
    /*public static JPanel createChartPanel(List<Stock> stockData) {
        // If stockData is null or empty, return an empty JPanel
        if (stockData == null || stockData.isEmpty()) {
            return new JPanel(); // Return an empty panel if there's no data
        }

        // Extract dates and close prices from the stock data
        // xData will hold the dates and yData will hold the corresponding close prices
        List<Date> xData =
                stockData.stream().map(Stock::getDateAsDate).collect(Collectors.toList());
        List<Double> yData = stockData.stream().map(Stock::getClose).collect(Collectors.toList());

        // Create a chart with specific dimensions and titles for the axes
        XYChart chart = new XYChartBuilder().width(800).height(400)
                .title(stockData.get(0).getSymbol().toUpperCase()).xAxisTitle("Date")
                .yAxisTitle("Close Price").build();

        // Customize the chart style
        XYStyler styler = chart.getStyler();
        styler.setLegendVisible(false); // Hide the legend
        styler.setChartBackgroundColor(Color.WHITE); // Set chart background color to white
        styler.setPlotBackgroundColor(Color.WHITE); // Set plot background color to white
        styler.setPlotBorderVisible(false); // Hide plot border
        styler.setDatePattern("yyyy-MM-dd"); // Set date format on the x-axis

        // Add a series to the chart with xData and yData
        XYSeries series = chart.addSeries("Stock Prices", xData, yData);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line); // Set the series to line
        series.setMarker(SeriesMarkers.NONE); // Remove markers from the line
        series.setLineWidth(1); // Adjust line width for better visibility
        series.setLineColor(Color.BLUE); // Set the line color to blue

        // Create a JPanel to hold the chart and set its layout to BorderLayout
        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        chartPanel.add(new XChartPanel<>(chart), BorderLayout.CENTER); // Add the chart to the panel

        // Return the panel containing the chart
        return chartPanel;
    }*/

    /*public static JPanel createChartPanel(List<Stock> stockData) {
        // If stockData is null or empty, return an empty JPanel
        if (stockData == null || stockData.isEmpty()) {
            return new JPanel(); // Return an empty panel if there's no data
        }

        // Extract dates, open, high, low, and close prices from the stock data
        List<Date> xData = stockData.stream().map(Stock::getDateAsDate).collect(Collectors.toList());
        List<Double> openData = stockData.stream().map(Stock::getOpen).collect(Collectors.toList());
        List<Double> highData = stockData.stream().map(Stock::getHigh).collect(Collectors.toList());
        List<Double> lowData = stockData.stream().map(Stock::getLow).collect(Collectors.toList());
        List<Double> closeData = stockData.stream().map(Stock::getClose).collect(Collectors.toList());

        // Create an OHLC chart with specific dimensions and titles for the axes
        OHLCChart chart = new OHLCChartBuilder().width(800).height(400)
                .title(stockData.get(0).getSymbol().toUpperCase()).xAxisTitle("Date")
                .yAxisTitle("Price").build();

        // Customize the chart style
        OHLCStyler styler = chart.getStyler();
        styler.setLegendVisible(false); // Hide the legend
        styler.setChartBackgroundColor(Color.WHITE); // Set chart background color to white
        styler.setPlotBackgroundColor(Color.WHITE); // Set plot background color to white
        styler.setPlotBorderVisible(false); // Hide plot border
        styler.setDatePattern("yyyy-MM-dd"); // Set date format on the x-axis

        // Add OHLC series to the chart
        chart.addSeries("Stock Prices", xData, openData, highData, lowData, closeData)
                .setMarker(SeriesMarkers.NONE);

        // Create a JPanel to hold the chart and set its layout to BorderLayout
        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        chartPanel.add(new XChartPanel<>(chart), BorderLayout.CENTER); // Add the chart to the panel

        // Return the panel containing the chart
        return chartPanel;
    }*/

    public static JPanel createChartPanel(List<Stock> stockData) {
        // Create the main panel with card layout to switch between charts
        JPanel mainPanel = new JPanel(new CardLayout());

        // Create line chart and OHLC chart panels
        JPanel lineChartPanel = createLineChartPanel(stockData);
        JPanel ohlcChartPanel = createOhlcChartPanel(stockData);

        // Add both panels to the main panel
        mainPanel.add(lineChartPanel, "Line Chart");
        mainPanel.add(ohlcChartPanel, "OHLC Chart");

        // Create radio buttons to switch between charts
        JRadioButton lineChartButton = new JRadioButton("Line Chart");
        lineChartButton.setSelected(true);
        JRadioButton ohlcChartButton = new JRadioButton("OHLC Chart");

        // Group radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(lineChartButton);
        group.add(ohlcChartButton);

        // Create a panel for radio buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(lineChartButton);
        buttonPanel.add(ohlcChartButton);

        // Add action listeners to radio buttons to switch charts
        lineChartButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "Line Chart");
        });

        ohlcChartButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "OHLC Chart");
        });

        // Create the container panel and add the button panel and main panel to it
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(buttonPanel, BorderLayout.NORTH);
        containerPanel.add(mainPanel, BorderLayout.CENTER);

        return containerPanel;
    }

    private static JPanel createLineChartPanel(List<Stock> stockData) {
        // Extract dates and close prices from the stock data
        List<Date> xData = stockData.stream().map(Stock::getDateAsDate).collect(Collectors.toList());
        List<Double> yData = stockData.stream().map(Stock::getClose).collect(Collectors.toList());

        // Create a chart with specific dimensions and titles for the axes
        XYChart chart = new XYChartBuilder().width(800).height(400)
                .title(stockData.get(0).getSymbol().toUpperCase()).xAxisTitle("Date")
                .yAxisTitle("Close Price").build();

        // Customize the chart style
        XYStyler styler = chart.getStyler();
        styler.setLegendVisible(false);
        styler.setChartBackgroundColor(Color.WHITE);
        styler.setPlotBackgroundColor(Color.WHITE);
        styler.setPlotBorderVisible(false);
        styler.setDatePattern("yyyy-MM-dd");

        // Add a series to the chart with xData and yData
        XYSeries series = chart.addSeries("Stock Prices", xData, yData);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        series.setMarker(SeriesMarkers.NONE);
        series.setLineWidth(1);
        series.setLineColor(Color.BLUE);

        // Create a JPanel to hold the chart and set its layout to BorderLayout
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.add(new XChartPanel<>(chart), BorderLayout.CENTER);

        return chartPanel;
    }

    private static JPanel createOhlcChartPanel(List<Stock> stockData) {
        // Extract dates, open, high, low, and close prices from the stock data
        List<Date> xData = stockData.stream().map(Stock::getDateAsDate).collect(Collectors.toList());
        List<Double> openData = stockData.stream().map(Stock::getOpen).collect(Collectors.toList());
        List<Double> highData = stockData.stream().map(Stock::getHigh).collect(Collectors.toList());
        List<Double> lowData = stockData.stream().map(Stock::getLow).collect(Collectors.toList());
        List<Double> closeData = stockData.stream().map(Stock::getClose).collect(Collectors.toList());

        // Create an OHLC chart with specific dimensions and titles for the axes
        OHLCChart chart = new OHLCChartBuilder().width(800).height(400)
                .title(stockData.get(0).getSymbol().toUpperCase()).xAxisTitle("Date")
                .yAxisTitle("Price").build();

        // Customize the chart style
        OHLCStyler styler = chart.getStyler();
        styler.setLegendVisible(false);
        styler.setChartBackgroundColor(Color.WHITE);
        styler.setPlotBackgroundColor(Color.WHITE);
        styler.setPlotBorderVisible(false);
        styler.setDatePattern("yyyy-MM-dd");

        // Add OHLC series to the chart
        chart.addSeries("Stock Prices", xData, openData, highData, lowData, closeData)
                .setMarker(SeriesMarkers.NONE);

        // Create a JPanel to hold the chart and set its layout to BorderLayout
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.add(new XChartPanel<>(chart), BorderLayout.CENTER);

        return chartPanel;
    }

}
