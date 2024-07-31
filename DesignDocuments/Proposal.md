## Proposal

### Team Members

* Jubal Bewick
* Jiazuo Zhang
* Kangning Li
* Aakash Sharma

----------------

* What are you building?

  * Stock Data View Application

* What are the initial features for the application?

  * GUI
  * Collection/database of stock data
  * User can search for a stock by symbol and add these stocks a to list
  * User can see the current price data and volume of the stock for current day and the last 100 tradding days
  * Data will be saved to in xml file format. We will have semi-persistent data storage and persistent data storage

* What are the *minimum* additional features you plan to implement?

  * Users will be able to sort custom watchlists by stock symbol, prices, and volume
  * User can delete a stock from a list
  * Users can save those custom watchlists with custom names and load them later for viewing or editing
  * Using an API to get the stock data
  * Users can view the 100 trading days of stock data as a line graph

* What are your stretch goals (features beyond the minimum)?

  * Users can view the stock data for a specific day within the last 100 trading days by using a calendar to select desired date that is valid
  * Adding visual background to the GUI
  * Adding a feature to compare two stocks

* Go over your initial design.

  * Special emphasis should be placed on how you plan to break it up

  * MVC, presenter, file management, different input validation, testing, documentation, etc.

* How do you plan to break up the work?

* **API & Data Fetching**: Jubal
* **Data Management**: Kangning
* **GUI**: Jiazuo, Aakash
* **Data Filtering**: Aakash

* **Testing**: All members
* **Documentation**: All members
* **UML Diagrams**: Jubal, Jiazuo

* What is your teams timeline and major check-in points?

  * we will meet formally every week
  * goals to be set by members and communication intra-group as needed for implementation and integration of components
  * check-in points will be set by the team and will be flexible based on the progress of the project and terminal date for project

### Deliverables

 • Project Plan Document:
 • Overview of the project, goals, and team roles.
 • Detailed timeline with milestones and check-in points.
 • Description of the initial and minimum viable features.
 • Stretch goals and how they will enhance the application.
 • Initial design diagram (UML).

# Initial Design - Stock Application

```mermaid
---
Stock Application
---


classDiagram
direction TB
    class Main {
        +main(String[] args)
    }

    class View {
        +printHelp(Exception e)
        +welcome()
        +getInput(String prompt) String
        +askForMoreStocks() boolean
        +goodbye()
        +display(List<Stock> records)
        +displayError(String message)
        -refreshBlock(String codeInput, JTextArea outputLabel)
        -build(JFrame frame)
        -class Slot
    }

    class Model {
        -MarketDataAPI marketDataAPI
        -static Model instance
        +Model(String apiKey)
        +static getInstance(String apiKey) Model
        +static getInstance() Model
        +fetchStockData(String symbol) TimeSeriesResponse
        +fetchStockDataForDate(String symbol, String date) StockUnit
        +fetchStockDataForToday(String symbol) StockUnit
        -getComparator(String orderBy) Comparator<Stock>
    }

    class MarketDataAPI {
        +fetchStockData(String symbol) TimeSeriesResponse
        +fetchStockDataForDate(String symbol, String date) StockUnit
        +fetchStockDataForToday(String symbol) StockUnit
    }

    class Controller {
        -Model model
        -static Controller instance
        -StockList stockList
        +Controller(String apiKey)
        +static getInstance(String apiKey) Controller
        +static getInstance() Controller
        +run()
        +fetchAndDisplayStockDataForDate(String symbol, String date)
        +fetchAndDisplayStockDataForToday(String symbol)
        +fetchAllStock(String symbol) StockList
        -askForMoreStocks()
    }

    class Stock {
        -double open
        -double high
        -double low
        -double close
        -long volume
        -String date
        -String symbol
        +Stock(double open, double high, double low, double close, long volume, String date, String symbol)
        +Stock()
        +getOpen() double
        +setOpen(double open)
        +getHigh() double
        +setHigh(double high)
        +getLow() double
        +setLow(double low)
        +getClose() double
        +setClose(double close)
        +getVolume() long
        +setVolume(long volume)
        +getDate() String
        +setDate(String date)
        +getSymbol() String
        +setSymbol(String symbol)
        +fromTimeSeriesResponse(TimeSeriesResponse response) List<Stock>
        +toJson(List<Stock> stocks) String
        +toString() String
    }

    class StockList {
        -List<Stock> stockList
        -String database
        +StockList()
        +getStockList() List<Stock>
        +setStockList(List<Stock> stockList)
        +addStock(Stock stock)
        +save()
        +loadStockFromXML()
        +toString() String
        +getStockFromSymbol(String symbol) Stock
    }

    class StockFilter{
    }

    Main --> Controller
    Controller --> Model
    Controller --> View
    Controller --> StockList
    Model --> MarketDataAPI
    View --> Stock
    StockList --> Stock
```
