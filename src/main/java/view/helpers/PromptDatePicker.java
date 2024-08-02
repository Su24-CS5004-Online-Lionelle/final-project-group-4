package view.helpers;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;

public class PromptDatePicker extends JDatePickerImpl {
    private final String promptText;
    private boolean showingPrompt;

    public PromptDatePicker(JDatePanelImpl datePanel, JFormattedTextField.AbstractFormatter formatter, String promptText) {
        super(datePanel, formatter);
        this.promptText = promptText;
        this.showingPrompt = true;
        setPrompt();
        getJFormattedTextField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingPrompt) {
                    getJFormattedTextField().setText("");
                    getJFormattedTextField().setForeground(Color.BLACK);
                    showingPrompt = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getJFormattedTextField().getText().isEmpty()) {
                    setPrompt();
                }
            }
        });
    }

    private void setPrompt() {
        getJFormattedTextField().setForeground(Color.GRAY);
        getJFormattedTextField().setText(promptText);
        showingPrompt = true;
    }

}
