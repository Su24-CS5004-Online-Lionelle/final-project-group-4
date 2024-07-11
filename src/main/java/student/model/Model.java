package student.model;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import student.model.entity.Stock;
import student.model.entity.StockList;
import student.model.util.FinanceAPI;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Model {

    String database = "data/records.xml";

    StockList stockList;

    File file;

    public Model() throws IOException {
        stockList = loadData(database);
        file = new File(database);
    }

    private StockList loadData(String database) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(new File(database), StockList.class);
    }

    public void addStock(String stockToAdd) throws IOException {
        // check if the stock already exists
        for (Stock stock : stockList.toList()) {
            if (stockToAdd.equals(stock.getCode())) {
                return;
            }
        }

        Stock stock = FinanceAPI.getStock(stockToAdd);
        stockList.addStock(stock);
        save();
    }

    private void save() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(file, stockList);
    }

    public void rmStock(String stockToRm) {
    }

    public List<Stock> getRecord(String query, String orderBy, String order) {
        // TODO: Aakash do ordering and filtering here

        return stockList.toList();
    }
}
