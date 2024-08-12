# Stock Data Viewer Manual

Welcome to the Stock Data Viewer! This guide will help you navigate the application, use its features, and understand its components.

## Overview

The Stock Data Viewer is an application designed to help users fetch and view stock data. The application provides a user-friendly interface to search for stock data by symbol, visualize it in charts, and perform various operations such as adding, removing, importing, exporting, and sorting stock records.

## Main Interface

### Components

<img src="../libs/images/GUIManual/manual_components.png" alt="Application Components" width="750">

1. **Stock Symbol Input**

   - **Text Field:** Located near the top of the window, this text field allows you to enter a stock symbol to search for. The field has a hint text, "Enter a stock symbol," to guide you.

2. **Buttons**

   - **Search:** Next to the text field, this button allows you to search for stock data based on the symbol entered.
   - **Add:** Allows you to add a stock record to the list.
   - **Delete:** Removes a selected stock record from the list.
   - **Clear:** Clears the text area and table, removing any displayed records.
   - **Import:** Imports stock data from a file into the application.
   - **Export:** Exports the current stock data to a file.
   - **Rand:** Generates a random mock stock record.
   - **Help:** Displays a help message with instructions on how to use the application.
   - **API Key:** Opens a dialog to enter or update your API key.

3. **Sorting Options**

   - **Sort By Menu:** Allows you to sort the displayed stock records by various fields such as Symbol, Date, Open, High, Low, Close, or Volume.

4. **Date Picker**

   - **Calendar Icon:** Next to the stock symbol input field, you'll see a calendar icon. Clicking this icon opens a date picker.
   - **Date Picker:** The date picker allows you to select a specific date to retrieve stock data for that day. Once the calendar is opened, it remains visible even if you move your mouse away, ensuring you can easily select a date. The calendar will only close if you click outside the calendar and the icon. After selecting a date, the search will consider this specific date for fetching stock data. The selected date will be cleared when clicking the "x" icon in the calendar.

5. **Text Area**

   - **Text Area:** A large text area where messages are displayed. It is scrollable to allow viewing of all content.

6. **Tables**

   - **Search Result Table:** A table displaying the searched record of single stock data. The table provides an overview of various stock data fields: Symbol, Date, Open, High, Low, Close, and Volume.
   - **Stock Data Table:** A table displaying multiple records of stock data. The table is scrollable and provides an overview of various stock data fields: Symbol, Date, Open, High, Low, Close, and Volume.

7. **Chart Panel**

   - **Stock Data Visualization:** A panel for displaying visual charts of the stock data. The chart will dynamically update based on the data being viewed.

### Using the Application

1. **Starting the Application**

   Launch the application. The main interface will appear, displaying the components. By default, some random data will be generated in the Stock Data Table, and a placeholder picture will be shown in the Chart Panel.

   <img src="../libs/images/GUIManual/manual_starting.png" alt="Application Launch" width="750">

2. **Searching for Stock Data**

   Enter the stock symbol into the text field. Click the Search button.

   <img src="../libs/images/GUIManual/manual_searching_1.png" alt="Searching" width="750">

   The stock data will appear in the text area, displaying the date, symbol, open, high, low, close, and volume for the specified stock.

   <img src="../libs/images/GUIManual/manual_searching_2.png" alt="Searching_2" width="750">

   You can click the calendar icon to open the date picker, then select a specific date, and search data for this date.

   <img src="../libs/images/GUIManual/manual_searching_3.png" alt="Searching_3" width="750">
   <img src="../libs/images/GUIManual/manual_searching_4.png" alt="Searching_4" width="750">

   You can also clear the record of the changed date in the Search Result Table by clicking the "x" icon on the calendar.

   <img src="../libs/images/GUIManual/manual_searching_5.png" alt="Searching_5" width="750">
   <img src="../libs/images/GUIManual/manual_searching_6.png" alt="Searching_6" width="750">

3. **Visualizing Stock Data**

   After fetching stock data, the chart panel will display a visual representation of the data. This helps in analyzing trends.

   <img src="../libs/images/GUIManual/manual_visualizing_1.png" alt="Visualizing_1" width="750">

   By switching between "Line Chart" and "OHLC Chart," you can view graph types of line charts and OHLC charts.

   <img src="../libs/images/GUIManual/manual_visualizing_2.png" alt="Visualizing_2" width="750">

   You can also see the graph of data within 1 week, 1 month, or all recent 100 records.

   <img src="../libs/images/GUIManual/manual_visualizing_3.png" alt="Visualizing_3" width="750">
   <img src="../libs/images/GUIManual/manual_visualizing_4.png" alt="Visualizing_4" width="750">

4. **Adding a Stock Record**

   After searching for stock data, click the Add button to add the displayed record to the Stock Data Table.

   <img src="../libs/images/GUIManual/manual_adding.png" alt="Add_To_List" width="750">

5. **Deleting a Stock Record**

   Select the stock record from the table.

   <img src="../libs/images/GUIManual/manual_deleting_1.png" alt="Deleting_1" width="750">

   Click the Delete button to remove it from the list.

   <img src="../libs/images/GUIManual/manual_deleting_2.png" alt="Deleting_2" width="750">

6. **Sorting Stock Data**

   Choose a sorting option from the Sort By Menu.

   <img src="../libs/images/GUIManual/manual_sorting_1.png" alt="Sorting_1" width="750">

   The records in the table will be sorted in descending order based on the selected field.

   <img src="../libs/images/GUIManual/manual_sorting_2.png" alt="Sorting_2" width="750">

7. **Clearing Data**

   Click the Clear button to remove all displayed data from the text area and the table.

   <img src="../libs/images/GUIManual/manual_clearing.png" alt="Clear_List_Pane" width="750">

8. **Searching Different Stock Symbols**

   By entering another stock symbol and clicking the Search button, you can get another result. You can do all previous operations on this new result.

   <img src="../libs/images/GUIManual/manual_another.png" alt="Additional_Search" width="750">

9. **Importing and Exporting Data**

   **Import:**

   Click the Import button to load stock data from a file if you already have data to import.

   <img src="../libs/images/GUIManual/manual_import_1.png" alt="Import_1" width="750">

   By default, the folder containing the data is the "customlists" folder. XML-formatted data is required for import. Choose the data you would like to import, and click "Open."

   <img src="../libs/images/GUIManual/manual_import_2.png" alt="Import_2" width="750">

   This will populate the Stock Data Table with data from the selected file.

   <img src="../libs/images/GUIManual/manual_import_3.png" alt="Import_3" width="750">

   **Export:**

   Click the Export button to save the current stock data to a file.

   <img src="../libs/images/GUIManual/manual_export_1.png" alt="Export_1" width="750">

   After clicking the Export button, a window will pop up to let you save the data to the "customlists" folder by default as an XML file.

   <img src="../libs/images/GUIManual/manual_export_2.png" alt="Export_2" width="750">

   Then click "Save" to export the data.

10. **Viewing Help**

    Click the Help button to view the help message, which provides instructions on how to use the application.

    <img src="../libs/images/GUIManual/manual_help.png" alt="Help_Display" width="750">

11. **Updating API Key**

    Click the API Key button to open a dialog where you can enter or update your API key.

    <img src="../libs/images/GUIManual/manual_api.png" alt="API" width="750">

12. **Closing the Application**

    When you are done using the application, you can close it by clicking the close button on the window. A farewell message will be displayed before the application exits.

    <img src="../libs/images/GUIManual/manual_closing.png" alt="Exit_Application" width="750">
