package view.helpers;

import model.DataMgmt.Stock;
import org.knowm.xchart.*;
import org.knowm.xchart.style.OHLCStyler;
import org.knowm.xchart.style.XYStyler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        // Group radio buttons for chart type selection
        ButtonGroup chartGroup = new ButtonGroup();
        chartGroup.add(lineChartButton);
        chartGroup.add(ohlcChartButton);

        // Create radio buttons for data selection
        JRadioButton oneWeekButton = new JRadioButton("1 Week");
        JRadioButton oneMonthButton = new JRadioButton("1 Month");
        JRadioButton allDataButton = new JRadioButton("All Data");
        allDataButton.setSelected(true); // Default to show all data

        // Group radio buttons for data range selection
        ButtonGroup dataGroup = new ButtonGroup();
        dataGroup.add(oneWeekButton);
        dataGroup.add(oneMonthButton);
        dataGroup.add(allDataButton);

        // Create a panel for chart type radio buttons
        JPanel chartControlPanel = new JPanel();
        chartControlPanel.add(lineChartButton);
        chartControlPanel.add(ohlcChartButton);

        // Create a panel for data range radio buttons
        JPanel dataControlPanel = new JPanel();
        dataControlPanel.add(oneWeekButton);
        dataControlPanel.add(oneMonthButton);
        dataControlPanel.add(allDataButton);

        // Add action listeners to radio buttons to switch charts
        lineChartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, "Line Chart");
            }
        });

        ohlcChartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, "OHLC Chart");
            }
        });

        // Add action listeners to radio buttons to filter data
        ActionListener filterAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Stock> filteredData = filterData(stockData, oneWeekButton.isSelected(), oneMonthButton.isSelected(), allDataButton.isSelected());
                updateChartPanel(mainPanel, filteredData, lineChartButton.isSelected());
            }
        };

        oneWeekButton.addActionListener(filterAction);
        oneMonthButton.addActionListener(filterAction);
        allDataButton.addActionListener(filterAction);

        // Create the container panel and add the control panels and main panel to it
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(chartControlPanel, BorderLayout.NORTH);
        containerPanel.add(dataControlPanel, BorderLayout.SOUTH);
        containerPanel.add(mainPanel, BorderLayout.CENTER);

        return containerPanel;
    }

    /**
     * Creates and returns a line chart panel with the provided stock data.
     *
     * @param stockData the list of Stock objects to display in the chart
     * @return a JPanel containing the line chart
     */
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

    /**
     * Creates and returns an OHLC chart panel with the provided stock data.
     *
     * @param stockData the list of Stock objects to display in the chart
     * @return a JPanel containing the OHLC chart
     */
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

    /**
     * Filters the stock data based on the selected time range.
     *
     * @param stockData the list of Stock objects to filter
     * @param oneWeek   whether to filter the data for the last week
     * @param oneMonth  whether to filter the data for the last month
     * @param allData   whether to show all data
     * @return the filtered list of Stock objects
     */
    private static List<Stock> filterData(List<Stock> stockData, boolean oneWeek, boolean oneMonth, boolean allData) {
        if (allData) {
            return stockData;
        }

        long currentTime = System.currentTimeMillis();
        long oneWeekMillis = 7L * 24 * 60 * 60 * 1000;
        long oneMonthMillis = 30L * 24 * 60 * 60 * 1000;

        return stockData.stream().filter(stock -> {
            long stockTime = stock.getDateAsDate().getTime();
            if (oneWeek && stockTime >= currentTime - oneWeekMillis) {
                return true;
            } else return oneMonth && stockTime >= currentTime - oneMonthMillis;
        }).collect(Collectors.toList());
    }

    /**
     * Updates the chart panel with the filtered data.
     *
     * @param mainPanel         the main panel containing the charts
     * @param filteredData      the filtered list of Stock objects
     * @param isLineChartSelected whether the line chart is currently selected
     */
    private static void updateChartPanel(JPanel mainPanel, List<Stock> filteredData, boolean isLineChartSelected) {
        JPanel lineChartPanel = createLineChartPanel(filteredData);
        JPanel ohlcChartPanel = createOhlcChartPanel(filteredData);

        mainPanel.removeAll();
        mainPanel.add(lineChartPanel, "Line Chart");
        mainPanel.add(ohlcChartPanel, "OHLC Chart");

        mainPanel.revalidate();
        mainPanel.repaint();

        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        if (isLineChartSelected) {
            cl.show(mainPanel, "Line Chart");
        } else {
            cl.show(mainPanel, "OHLC Chart");
        }
    }
}
