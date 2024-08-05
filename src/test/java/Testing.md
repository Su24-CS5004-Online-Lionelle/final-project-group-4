# Stock Data Viewer Application - Testing

## Overview

The Stock Data Viewer Application is designed to provide users with updated stock data and historical data for the last 100 trading days. Users can query stock information, create custom watchlists of stocks, and visualize individual stock performance through detailed line charts. This document outlines the testing strategy and approach used to ensure the application meets its requirements and performs reliably.

## Testing Strategy

Our testing strategy includes unit testing and integration testing. The primary goal is to ensure all components of the application function correctly and integrate seamlessly. We aim to cover the following areas:

1. **API Testing**: Validate the MarketDataAPI class interactions with the Alpha Vantage API.
2. **Model Testing**: Verify the core logic and data management within the Model class.
3. **Controller Testing**: Ensure the Controller class correctly manages interactions between the View and the Model.
4. **View Testing**: Ensure the View class correctly displays data and handles user interactions.

## Testing Tools

We use the following tools for testing:

- **JUnit 5**: For unit testing and integration testing.
- **Mockito**: For mocking dependencies in unit tests.

## API Testing

The MarketDataAPI class is responsible for fetching stock data from the Alpha Vantage API. We need to ensure it handles various scenarios, including valid requests, invalid API keys, and error conditions.

### Test Cases

1. **Valid Stock Data Request**
   - **Input**: Valid stock symbol.
   - **Expected Result**: Successful response with stock data.

2. **Invalid API Key**
   - **Input**: Invalid API key.
   - **Expected Result**: Error message indicating invalid API key.

3. **Empty Stock Symbol**
   - **Input**: Empty stock symbol.
   - **Expected Result**: Error message indicating invalid symbol.

4. **Null Stock Symbol**
   - **Input**: Null stock symbol.
   - **Expected Result**: Error message indicating invalid symbol.

5. **Non-Existent Stock Symbol**
   - **Input**: Non-existent stock symbol.
   - **Expected Result**: Error message indicating invalid symbol.

## Model Testing

Model testing focuses on the core logic and data management within the Model class. The goal is to ensure that data is correctly fetched, processed, and stored.

### Test Cases

1. **Fetch Stock Data**
   - **Input**: Valid stock symbol.
   - **Expected Result**: Correct stock data is fetched and stored.

2. **Fetch Stock Data with Invalid Symbol**
   - **Input**: Invalid stock symbol.
   - **Expected Result**: Error message or exception.

3. **Save Stock List**
   - **Input**: List of stocks.
   - **Expected Result**: Stocks are correctly saved to the database or file system.

4. **Load Stock List**
   - **Input**: None (invoke load method).
   - **Expected Result**: Correct list of stocks is loaded from the database or file system.

## Controller Testing

Controller testing ensures that the Controller class correctly manages interactions between the View and the Model. We need to verify that the Controller processes data and commands accurately.

### Test Cases

1. **Handle Stock Data Request**
   - **Input**: Valid stock symbol.
   - **Expected Result**: Data is fetched from the Model and passed to the View.

2. **Handle Invalid Stock Data Request**
   - **Input**: Invalid stock symbol.
   - **Expected Result**: Error is handled and an error message is passed to the View.

3. **Manage Watchlist**
   - **Input**: Add and remove stocks.
   - **Expected Result**: Watchlist is correctly updated and displayed in the View.

## View Testing

View testing ensures that the View class correctly displays data and handles user interactions. We will mock the Controller interactions to focus on the View logic.

### Test Cases

1. **Display Stock Data**
   - **Input**: List of stock data.
   - **Expected Result**: Stock data is correctly displayed in the UI.

2. **Handle User Input**
   - **Input**: Various user inputs.
   - **Expected Result**: Correct processing of user inputs.

3. **Display Error Messages**
   - **Input**: Error condition.
   - **Expected Result**: Correct error message is displayed to the user.

## Conclusion

in progress