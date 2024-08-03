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

public class TableHelper {
    private static String lastSelected;

    public static void updateTableModel(DefaultTableModel tableModel) {
        // Update table, clear existing rows
        tableModel.setRowCount(0);

        List<Stock> stockData = Controller.getInstance().getStockList().getStockList();

        // update large panel
        for (Stock stock : stockData) {
            Object[] rowData = {stock.getSymbol(), stock.getDate(), stock.getOpen(),
                    stock.getHigh(), stock.getLow(), stock.getClose(), stock.getVolume()};

            tableModel.addRow(rowData);
        }
    }

    public static void updateTableModel(int delay, DefaultTableModel tableModel) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable updateTask = () -> {
            tableModel.setRowCount(0);

            List<Stock> stockData = Controller.getInstance().getStockList().getStockList();

            for (Stock stock : stockData) {
                Object[] rowData = {stock.getSymbol(), stock.getDate(), stock.getOpen(),
                        stock.getHigh(), stock.getLow(), stock.getClose(), stock.getVolume()};

                tableModel.addRow(rowData);
            }
        };

        executor.schedule(updateTask, delay, TimeUnit.SECONDS);
    }

    public static void updateModelSingle(Stock mostRecentStock, DefaultTableModel tableSingle) {
        tableSingle.setRowCount(0);

        // Add the most recent stock data to tableSingle
        if (mostRecentStock != null) {
            Object[] recentRowData = {mostRecentStock.getSymbol(), mostRecentStock.getDate(),
                    mostRecentStock.getOpen(), mostRecentStock.getHigh(), mostRecentStock.getLow(),
                    mostRecentStock.getClose(), mostRecentStock.getVolume()};
            tableSingle.addRow(recentRowData);
        }
    }

    public static void clearLastHighLight(JTable table) {
        if (lastSelected != null) {
            table.getColumn(lastSelected).setCellRenderer(table.getDefaultRenderer(Object.class));
        }
    }

    public static void highLightSelected(String selectedOption, JTable table) {
        if (lastSelected != null) {
            table.getColumn(lastSelected).setCellRenderer(table.getDefaultRenderer(Object.class));
        }
        lastSelected = selectedOption;

        table.getColumn(selectedOption).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(Color.YELLOW); // set column color to yellow
                }
                return c;
            }
        });
    }
}
