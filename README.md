# Final Project for CS 5004 - Stock Market Data Viewer Application

## Group Members

- **Jubal Bewick** - [GitHub](https://github.com/Darkknight-86)
- **Jiazuo Zhang** - [GitHub](https://github.com/JZZhang04)
- **Kangning Li** - [GitHub](https://github.com/ShakyVertex)
- **Aakash Sharma** - [GitHub](https://github.com/sharma-aak)

## Application Overview

**Stock Data View** is an application that leverages the Alpha Vantage API to provide daily updated stock data and historical data for the last 100 trading days. Users can query stock information, create custom watchlists of stocks, and visualize individual stock performance through detailed line charts. Additionally, the application includes a calendar section that allows users to select individual days and search through historical data visually.

## Design Documents and Manuals

- [Manual/README](./Manual/README.md)
- [GUI Instructions](./Manual/GUI_Instructions.md)
- [Proposal](./DesignDocuments/Proposal.md)
- [Design](./DesignDocuments/Design.md)
- [Features](./DesignDocuments/Features.md)
- [GUITesting](./DesignDocuments/GUITesting.md)
- [View Javadoc Documentation](./build/docs/javadoc/index.html)

## Instructions to Run the Application

1. **Clone the repository:**

   ```sh
   git clone https://github.com/Su24-CS5004-Online-Lionelle/final-project-group-4.git
   ```

2. Open the project in the IDE of your choice. IntelliJ IDEA or Visual Studio Code is recommended.
3. Ensure all dependencies are installed by running the appropriate build commands for your IDE via the terminal. Use one of the following commands:

   ```sh
   ./gradlew clean build
   ```

   or

   ```sh
   gradlew clean build
   ```

4. Locate the `Main` class in the `src` folder.
5. Run the `Main` class to start the application.
6. The application will open in a new window.
7. For visual instructions on using the GUI, see [GUI Instructions](./Manual/GUI_Instructions.md).

## Generating Javadoc Documentation

To generate the Javadoc documentation for this project, follow these steps:

1. **Open the terminal in VS Code:**

   You can open the terminal by going to `View -> Terminal` or by pressing `Ctrl+` ``.

2. **Navigate to the project root directory:**

   ```sh
   cd /path/to/final-project-group-4
   ```

3. **Run the Javadoc generation command:**

   ```sh
   gradle generateJavadoc
   ```

4. **The generated Javadoc documentation will be located in the `build/docs/javadoc` directory.

5. **To view the Javadoc documentation:**
   - Open the `build/docs/javadoc` directory in your file explorer.
   - Open the `index.html` file in your web browser.

Alternatively, you can view the documentation directly from this README by clicking the link below after completing the previous steps:

- [View Javadoc Documentation](./build/docs/javadoc/index.html)
