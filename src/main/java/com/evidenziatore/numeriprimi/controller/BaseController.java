package com.evidenziatore.numeriprimi.controller;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

//controller contenente funzionalita' generiche che puo' essere esteso per non duplicarle
public class BaseController {

    UnaryOperator<TextFormatter.Change> campoDiTestoNumericoMaggioreDiZero = cambiamento -> {
        if (cambiamento.isContentChange()) {
            String nuovoTesto = cambiamento.getControlNewText();
            if (!nuovoTesto.matches("\\d*") || nuovoTesto.startsWith("0")) return null;
        }
        return cambiamento;
    };

    protected void setCampoDiTestoNumericoMaggioreDiZero(TextField campoDiTesto) {
        TextFormatter<String> formattatoreTesto = new TextFormatter<>(campoDiTestoNumericoMaggioreDiZero);
        campoDiTesto.setTextFormatter(formattatoreTesto);
    }

    protected void setAltezzaTabella(TableView tabella) {
        double altezzaRiga = 25;
        double altezzaHeader = 30;
        int numeroDiRighe = tabella.getItems().size();
        double altezzaTotale = altezzaHeader + (altezzaRiga * numeroDiRighe);
        tabella.setPrefHeight(altezzaTotale);
    }
}