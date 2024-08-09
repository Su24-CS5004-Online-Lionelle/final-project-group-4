### Features List

Below is a comprehensive list of features implemented in the Stock Data Viewer project. These features cover both the minimum requirements and additional/optional features as outlined in the project requirements.

## Minimum Requirements

1. **Graphical User Interface (GUI)**

   - Implemented using Java Swing.

2. **View All Items in the Collection**

   - **Table View**: Initially will display randomized stock data values. It will also be responsible for displaying most recent day stock data in a tabular format that deals with lists.
   - **Charts**: Displays stock data in charts, including line charts and OHLC charts. The charts will sorting capabilities of either 1 week, 1 month or all data available.

3. **Build a List of Items from the Collection**

   - **Stock List Management**:
     - Add Stock: Allows adding a stock from the current search query in the form of the most recent day stock data to a custom list.
     - Remove Stock: Users can remove select stocks from the list.
     - Clear All: Provides an option to clear all stocks from the list that is in table view.

4. **Save Out the List Using a File Format**
   - **Export Stock Data**: Users can export the stock list to XML files to a preset folder destination for ease of use.

## Additional/Optional Features

1. **Load in Lists of Items / Previously Saved Lists and Modify Them**

   - **Import Stock Data**: Users can import stock data from XML files from a preset folder destination for ease of use.
   - **Edit Stock Data**: Users can modify the stock list and have those modifications persist.

2. **Search for Items in the Collection**

   - **Search for Stock Data**: Users can enter a stock symbol and fetch its data.

3. **Sort Items in the Collection**

   - **Sort Stocks**: Users can sort stocks by different criteria such as Open, Close, High, Low, Volume, Date, and Symbol.

4. **Filter Items in the Collection**

   - **Date Selection and Custom Date Picker**: Users can select dates to view stock data for specific days.
   - **Single Stock View**: Updated to display results for a specific date selected using the date picker after a search query has been performed.

5. **Online API Access**

   - **API Integration**: The application integrates with the Alpha Vantage API to retrieve stock data.

6. **Include Images for Items**

   - **Dynamic Background**: The application features a dynamic background with price action visualization.

7. **Persistence of Modifications**

   - **Persistent Modifications**: The modifications to the stock list remain persistent across sessions.

8. **Display Numeric Data in a Graph**

   - **Charts**: Displays stock data in charts, including line charts and OHLC charts.

9. **Goodbye Message**

   - **Thank You Message**: Displays a farewell message to the user when the application is closed, expressing gratitude and best wishes.

10. **JavaDoc Webpage**
    - **Documentation**: Generates and provides access to a JavaDoc webpage for the project's source code.

## Additional Notable Features Implemented

1. **Error Handling**

   - **Invalid Stock Symbol**: Displays an error message if the entered stock symbol is invalid.
   - **API Limit Reached**: Notifies users if the API request limit is reached.
   - **Date Out of Range**: Alerts users if the selected date is outside the valid range.
   - **Data Not Found**: Informs users if no stock data is available for the selected date.
   - **Invalid Date**: Notifies users if the selected date is invalid.

2. **User Prompts**

   - **Action Required Prompts**: Alerts users to perform necessary actions such as entering a stock symbol before using the date picker.

3. **Random Stock Generation**

   - **Test Stock Generation**: Allows users to generate a random stock for testing purposes.

4. **Hint Text Field**

   - **HintTextFieldHelper**: Custom JTextField that displays a hint ("Enter a stock symbol") when the field is empty and not focused, setting the user's cursor to that field/box.

5. **API Key Input**
   - **API Key Input Dialog**: Allows users to input their API key for Alpha Vantage.
