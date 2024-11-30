package com.evidenziatore.numeriprimi.controller;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

//controller astratto contenente funzionalita' frontend generiche
public abstract class BaseController {

    UnaryOperator<TextFormatter.Change> campoDiTestoNumericoMaggioreDiZero = cambiamento -> {
        if (cambiamento.isContentChange()) {
            String nuovoTesto = cambiamento.getControlNewText();
            if (!nuovoTesto.matches("\\d*") || nuovoTesto.startsWith("0")) return null;
        }
        return cambiamento;
    };

    /**
     * @param campoDiTesto Il TextField in cui devono essere aggiunti i controlli.
     */
    protected void setCampoDiTestoNumericoMaggioreDiZero(TextField campoDiTesto) {
        TextFormatter<String> formattatoreTesto = new TextFormatter<>(campoDiTestoNumericoMaggioreDiZero);
        campoDiTesto.setTextFormatter(formattatoreTesto);
    }

    /**
     * @param tabella La TableView in cui deve essere settata l'altezza.
     */
    protected void setAltezzaTabella(TableView<?> tabella) {
        double altezzaRiga = 25;
        double altezzaHeader = 30;
        int numeroDiRighe = tabella.getItems().size();
        double altezzaTotale = altezzaHeader + (altezzaRiga * numeroDiRighe);
        tabella.setPrefHeight(altezzaTotale);
    }
}