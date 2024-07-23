## Proposal

The proposal does not have to be a formal presentation. Though if that helps the team keep their thoughts organized, that is fine. Every team member should be present to talk about the proposal. The proposal should include the following:

* What are you building?

  * Stock Application

* What are the initial features for the application?

  * GUI
  * User can search for a stock by symbol
  * User can see the current price data and volume of the stock for current day
  * User can add a stock to a list (via xml storage)

* What are the *minimum* additional features you plan to implement?

  * User can sort a created stock lists via different criteria (symbol, price, volume, etc)
  * User can delete a stock from a list
  * Using an API to get the stock data

* What are your stretch goals (features beyond the minimum)?

  * Adding graphics to the GUI for the stock symbols (company icons etc)
  * Adding a graph of the stock price over time
  * User can see the financials of the stock (stretch goal - this would be a separate API call)

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

Most of the information you are presented needs to be documented as a deliverable. This could be a powerpoint, a word document, or even a markdown file in your repository (use the DesignDocuments directory). The key is that it is a living document that can be updated as you learn more about your project.

You should also have an **Initial** UML diagram to present. It will still be pretty sparse, and that is fine. However, it should be enough where team members have an idea of which part they are expanding on and working on.

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

### Grading

The grading for this assignment will be based on the following criteria:

#### Group Grade

* 4 points - Proposal is well thought out and covers all the major points. Documentation is clear.
* 3 points - Proposal is missing some key points, but is well thought out. Documentation exists, but not clear.
* 2 points - Proposal is missing some key points and missing documentation.
* 1 point - Proposal is missing key points, documentation is missing or not clear, there isn't a plan going into the proposal.

#### Individual Grade

* 2 points - Student is present and contributes to the proposal, their camera is on (unless they have a good reason for it not to be), and they are able to answer questions about the proposal.
* 1 point - Student attends the meeting.
