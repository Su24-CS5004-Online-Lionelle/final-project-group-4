package view.helpers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import controller.Controller;
import model.DataMgmt.Stock;

/**
 * Helper class for updating and managing JTable components.
 */
public class TableHelper {

    /** The last selected column name for highlighting purposes. */
    private static String lastSelected;

    /**
     * Updates the table model with the current stock list.
     *
     * @param tableModel the table model to be updated
     */
    public static void updateTableModel(DefaultTableModel tableModel) {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Get stock data from the controller
        List<Stock> stockData = Controller.getInstance().getStockList().getStockList();

        // Add stock data to the table model
        for (Stock stock : stockData) {
            Object[] rowData = {stock.getSymbol(), stock.getDate(), stock.getOpen(),
                    stock.getHigh(), stock.getLow(), stock.getClose(), stock.getVolume()};
            tableModel.addRow(rowData);
        }
    }

    /**
     * Updates the table model with a delay.
     *
     * @param delay the delay in seconds before updating the table model
     * @param tableModel the table model to be updated
     */
    public static void updateTableModel(int delay, DefaultTableModel tableModel) {
        // Create a scheduled executor service for the delayed task
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // Define the task to update the table model
        Runnable updateTask = () -> {
            // Clear existing rows
            tableModel.setRowCount(0);

            // Add default 12 data to our database
            Controller.getInstance().getStockList().generateDefault();

            // Get stock data from the controller
            List<Stock> stockData = Controller.getInstance().getStockList().getStockList();

            // Add stock data to the table model
            for (Stock stock : stockData) {
                Object[] rowData = {stock.getSymbol(), stock.getDate(), stock.getOpen(),
                        stock.getHigh(), stock.getLow(), stock.getClose(), stock.getVolume()};
                tableModel.addRow(rowData);
            }
        };

        // Schedule the task with the specified delay
        executor.schedule(updateTask, delay, TimeUnit.SECONDS);
    }

    /**
     * Updates the table model with the most recent stock data.
     *
     * @param mostRecentStock the most recent stock data to be displayed
     * @param tableSingle the table model to be updated
     */
    public static void updateModelSingle(Stock mostRecentStock, DefaultTableModel tableSingle) {
        // Clear existing rows
        tableSingle.setRowCount(0);

        // Check if the most recent stock data is valid
        if (mostRecentStock != null && mostRecentStock.getSymbol() != null
                && !mostRecentStock.getSymbol().isEmpty()) {
            // Add the most recent stock data to the table model
            Object[] recentRowData = {mostRecentStock.getSymbol(), mostRecentStock.getDate(),
                    mostRecentStock.getOpen(), mostRecentStock.getHigh(), mostRecentStock.getLow(),
                    mostRecentStock.getClose(), mostRecentStock.getVolume()};
            tableSingle.addRow(recentRowData);
        } else {
            // Clear all columns by adding an empty row
            tableSingle.addRow(new Object[] {"", "", "", "", "", "", ""});
        }
    }

    /**
     * Clears the highlight from the last selected column.
     *
     * @param table the table to clear the highlight from
     */
    public static void clearLastHighLight(JTable table) {
        if (lastSelected != null) {
            // Reset the cell renderer for the last selected column
            table.getColumn(lastSelected).setCellRenderer(table.getDefaultRenderer(Object.class));
        }
    }

    /**
     * Highlights the selected column.
     *
     * @param selectedOption the name of the column to be highlighted
     * @param table the table containing the column
     */
    public static void highLightSelected(String selectedOption, JTable table) {
        // Clear the highlight from the last selected column
        if (lastSelected != null) {
            table.getColumn(lastSelected).setCellRenderer(table.getDefaultRenderer(Object.class));
        }
        lastSelected = selectedOption;

        // Set the cell renderer for the selected column to highlight it
        table.getColumn(selectedOption).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(Color.LIGHT_GRAY); // Set column color to light Gray
                }
                return c;
            }
        });
    }

    /**
     * Sets the first column to have a gray background by default.
     *
     * @param table the JTable whose first column will be set to gray
     */
    public static void setFirstColumnGray(JTable table) {
        lastSelected = "Symbol";

        // Set the cell renderer for the default Column to highlight it
        table.getColumn(lastSelected).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(Color.LIGHT_GRAY); // Set column color to light Gray
                }
                return c;
            }
        });
    }
}
