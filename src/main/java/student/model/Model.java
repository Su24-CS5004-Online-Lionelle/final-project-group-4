package student.model;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import student.model.entity.Stock;
import student.model.entity.StockList;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Model {

    String database = "data/records.xml";

    StockList stockList;

    public Model() throws IOException {
        stockList = loadData(database);
    }

    private StockList loadData(String database) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(new File(database), StockList.class);
    }

    public void addStock(String stockToAdd) {

    }

    public void rmStock(String stockToRm) {
    }

    public List<Stock> getRecord(String query, String orderBy, String order) {
        // TODO: Aakash do ordering and filtering here

        return stockList.toList();
    }
}
