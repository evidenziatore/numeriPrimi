package com.evidenziatore.numeriprimi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class BaseController {

    UnaryOperator<TextFormatter.Change> textFieldNumerico = change -> {
        if (change.isContentChange()) {
            if (!change.getControlNewText().matches("\\d*")) {
                return null;
            }
        }
        return change;
    };

    protected void setTextFieldNumerico(TextField textField) {
        TextFormatter<String> textFormatter = new TextFormatter<>(textFieldNumerico);
        textField.setTextFormatter(textFormatter);
    }
}