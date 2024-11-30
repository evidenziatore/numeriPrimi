package com.evidenziatore.numeriprimi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class BaseController {

    UnaryOperator<TextFormatter.Change> textFieldNumerico = change -> {
        if (change.isContentChange()) {
            String newText = change.getControlNewText();
            if (!newText.matches("\\d*") || newText.startsWith("0")) {
                return null;
            }
        }
        return change;
    };

    protected void setTextFieldNumericoMaggioreDiZero(TextField textField) {
        TextFormatter<String> textFormatter = new TextFormatter<>(textFieldNumerico);
        textField.setTextFormatter(textFormatter);
    }

    protected void setTableSize(TableView tableView) {
        double rowHeight = 25;
        double headerHeight = 30;
        int numberOfRows = tableView.getItems().size();
        double totalHeight = headerHeight + (rowHeight * numberOfRows);
        tableView.setPrefHeight(totalHeight);
    }
}