package view;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.*;

import view.helpers.CustomButton;
import view.helpers.DynamicBackgroundCanvas;
import view.helpers.Messages;
import controller.Controller;
import view.helpers.PromptDatePicker;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * TestView class provides automated GUI testing for the View component of the application. It
 * verifies the visibility and presence of various GUI components such as buttons, labels, and
 * panels. The tests are executed in a specific order to ensure proper initialization and consistent
 * results.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestView {

    private FrameFixture window;
    private Controller mockController;

    /**
     * Sets up the testing environment before each test. This includes ensuring the tests run on the
     * Event Dispatch Thread (EDT), initializing the mock controller, and creating the View instance
     * to be tested.
     */
    @BeforeEach
    public void setUp() {
        // Ensure that any GUI operations are properly handled on the EDT
        FailOnThreadViolationRepaintManager.install();

        // Create a mock of the Controller class
        mockController = mock(Controller.class);
        assertNotNull(mockController, "Mock controller should not be null");

        // Create the View instance on the EDT for thread safety
        View view = GuiActionRunner.execute(() -> new View(mockController));
        JFrame frame = view.frame;
        assertNotNull(frame, "Frame should not be null");

        // Initialize the FrameFixture with the JFrame for AssertJ-Swing testing
        window = new FrameFixture(frame);
        window.show(); // Display the frame for testing

        // Verify that the FrameFixture window is not null
        assertNotNull(window, "Window should not be null");
    }

    /**
     * Cleans up the testing environment after each test by closing the FrameFixture window to
     * ensure no lingering GUI elements.
     */
    @AfterEach
    public void tearDown() {
        if (window != null) {
            // Safely close and clean up the window
            SwingUtilities.invokeLater(() -> window.cleanUp());
        }
    }

    /**
     * Verifies the minimal setup for the test environment, ensuring that the mock controller and
     * FrameFixture window are initialized correctly.
     */
    @Test
    @Order(1)
    public void testMinimalSetup() {
        assertNotNull(mockController, "Mock controller should not be null");
        assertNotNull(window, "Window should not be null");
    }

    /**
     * Tests whether the welcome message is correctly displayed in the JTextArea component of the
     * View.
     */
    @Test
    @Order(2)
    public void testWelcomeMessageDisplayed() {
        // Find the JTextArea by matching its client property name and visibility
        JTextArea textArea =
                window.robot().finder().find(new GenericTypeMatcher<JTextArea>(JTextArea.class) {
                    @Override
                    protected boolean isMatching(JTextArea component) {
                        // Ensure the component is showing and matches the expected properties
                        return "textArea".equals(component.getClientProperty("name"))
                                && component.isShowing();
                    }
                });

        // Assert that the welcome message is displayed correctly
        assertNotNull(textArea, "The JTextArea should not be null");
        assertEquals(Messages.WELCOME_MESSAGE.getMessage(), textArea.getText());
    }

    /**
     * Helper method to test the visibility of various GUI components. This method uses Awaitility
     * to wait for the component to appear and verifies that it is visible and not null.
     *
     * @param <T> The type of the GUI component.
     * @param componentName The name assigned to the component for identification.
     * @param componentClass The class of the component being tested.
     */
    private <T extends JComponent> void testComponentVisibility(String componentName,
            Class<T> componentClass) {
        // Wait up to 5 seconds for the component to appear and verify visibility
        Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            T component = window.robot().finder().find(new GenericTypeMatcher<T>(componentClass) {
                @Override
                protected boolean isMatching(T c) {
                    // Check if the component's name and visibility match
                    return componentName.equals(c.getClientProperty("name")) && c.isShowing();
                }
            });

            // Assert that the component is not null and is visible
            assertThat(component).isNotNull();
        });
    }

    /**
     * Tests the visibility of the search button in the GUI.
     */
    @Test
    @Order(3)
    public void testSearchButtonVisibility() {
        testComponentVisibility("searchButton", CustomButton.class);
    }

    /**
     * Tests the visibility of the add button in the GUI.
     */
    @Test
    @Order(4)
    public void testAddButtonVisibility() {
        testComponentVisibility("addButton", CustomButton.class);
    }

    /**
     * Tests the visibility of the remove button in the GUI.
     */
    @Test
    @Order(5)
    public void testRemoveButtonVisibility() {
        testComponentVisibility("removeButton", CustomButton.class);
    }

    /**
     * Tests the visibility of the clear button in the GUI.
     */
    @Test
    @Order(6)
    public void testClearButtonVisibility() {
        testComponentVisibility("clearButton", CustomButton.class);
    }

    /**
     * Tests the visibility of the import button in the GUI.
     */
    @Test
    @Order(7)
    public void testImportButtonVisibility() {
        testComponentVisibility("importButton", CustomButton.class);
    }

    /**
     * Tests the visibility of the export button in the GUI.
     */
    @Test
    @Order(8)
    public void testExportButtonVisibility() {
        testComponentVisibility("exportButton", CustomButton.class);
    }

    /**
     * Tests the visibility of the push button in the GUI.
     */
    @Test
    @Order(9)
    public void testPushButtonVisibility() {
        testComponentVisibility("pushButton", CustomButton.class);
    }

    /**
     * Tests the visibility of the help button in the GUI.
     */
    @Test
    @Order(10)
    public void testHelpButtonVisibility() {
        testComponentVisibility("helpButton", CustomButton.class);
    }

    /**
     * Tests the visibility of the API dialog button in the GUI.
     */
    @Test
    @Order(11)
    public void testApiDialogButtonVisibility() {
        testComponentVisibility("apiDialogButton", CustomButton.class);
    }

    /**
     * Tests the visibility of the logo label in the GUI.
     */
    @Test
    @Order(12)
    public void testLogoLabelVisibility() {
        testComponentVisibility("logoLabel", JLabel.class);
    }

    /**
     * Tests the visibility of the sort by combo box in the GUI.
     */
    @Test
    @Order(13)
    public void testSortByComboBoxVisibility() {
        testComponentVisibility("sortByComboBox", JComboBox.class);
    }

    /**
     * Tests the visibility of the scroll pane that holds the table in the GUI.
     */
    @Test
    @Order(14)
    public void testScrollPaneVisibility() {
        testComponentVisibility("scrollPane", JScrollPane.class);
    }

    /**
     * Tests the visibility of the table scroll pane in the main view of the GUI.
     */
    @Test
    @Order(15)
    public void testTableScrollPaneVisibility() {
        testComponentVisibility("tableScrollPane", JScrollPane.class);
    }

    /**
     * Tests the visibility of the single table scroll pane in the GUI.
     */
    @Test
    @Order(16)
    public void testSingleTableScrollPaneVisibility() {
        testComponentVisibility("tableScrollPaneSingle", JScrollPane.class);
    }

    /**
     * Tests the visibility of the chart panel in the GUI.
     */
    @Test
    @Order(17)
    public void testChartPanelVisibility() {
        testComponentVisibility("chartPanel", JPanel.class);
    }

    /**
     * Tests the visibility of the background panel in the GUI.
     */
    @Test
    @Order(18)
    public void testBackgroundPanelVisibility() {
        testComponentVisibility("backgroundPanel", JPanel.class);
    }

    /**
     * Tests the visibility of the date picker component in the GUI.
     */
    @Test
    @Order(19)
    public void testDatePickerVisibility() {
        testComponentVisibility("datePicker", PromptDatePicker.class);
    }

    /**
     * Tests the visibility of the dynamic background canvas in the GUI.
     */
    @Test
    @Order(20)
    public void testDynamicBackgroundCanvasVisibility() {
        testComponentVisibility("dynamicBackgroundCanvas", DynamicBackgroundCanvas.class);
    }
}
