package view.helpers;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Helper class to manage dialog boxes, ensuring each message is displayed only once.
 */
public class DialogHelper {

    /**
     * Enum representing different dialog states.
     */
    public enum DialogState {
        /** Indicates an action is required. */
        ACTION_REQUIRED,

        /** Indicates the selected date is out of range. */
        DATE_OUT_OF_RANGE,

        /** Indicates the requested data was not found. */
        DATA_NOT_FOUND,

        /** Indicates the selected date is invalid. */
        INVALID_DATE,

        /** Indicates the input provided is invalid. */
        INVALID_INPUT,

        /** Indicates the API limit has been reached. */
        API_LIMIT_REACHED,

        /** Represents any other state not covered by the above constants. */
        OTHER
    }

    /**
     * Set to track displayed messages to prevent duplicate dialogs.
     */
    private static final Set<String> displayedMessages = new HashSet<>();

    /**
     * Shows a single dialog box with the specified message, title, and state. Ensures that each
     * message is displayed only once.
     *
     * @param message the message to display
     * @param title the title of the dialog box
     * @param state the state of the dialog
     */
    public static void showSingleDialog(String message, String title, DialogState state) {
        String key = state + ": " + title + ": " + message;
        if (!displayedMessages.contains(key)) {
            displayedMessages.add(key);
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
                // Remove the message from the set after displaying
                displayedMessages.remove(key);
            });
        }
    }
}
