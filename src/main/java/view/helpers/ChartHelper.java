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

public class ChartHelper {

    /**
     * Creates and returns a chart panel with the provided stock data.
     *
     * @param stockData the list of Stock objects to display in the chart
     * @return a JPanel containing the chart
     */
    public static JPanel createChartPanel(List<Stock> stockData) {
        if (stockData == null || stockData.isEmpty()) {
            return new JPanel(); // Return an empty panel if there's no data
        }

        // Extract dates and close prices from the stock data
        List<Date> xData =
                stockData.stream().map(Stock::getDateAsDate).collect(Collectors.toList());
        List<Double> yData = stockData.stream().map(Stock::getClose).collect(Collectors.toList());

        // Create a chart
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

        // Add series to the chart
        XYSeries series = chart.addSeries("Stock Prices", xData, yData);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line); // Set the series to line
        series.setMarker(SeriesMarkers.NONE); // Remove markers from the line
        series.setLineWidth(1); // Adjust line width for better visibility
        series.setLineColor(Color.BLUE); // Set the line color to blue

        // Create a chart panel to display the chart
        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        chartPanel.add(new XChartPanel<>(chart), BorderLayout.CENTER);

        return chartPanel;
    }
}
