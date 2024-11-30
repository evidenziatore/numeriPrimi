package com.evidenziatore.numeriprimi.controller;

import com.evidenziatore.numeriprimi.task.GeneraTabellaPrimiTask;
import com.evidenziatore.numeriprimi.entita.RigaTabellaNumeriPrimi;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NumeriPrimiController extends BaseController implements Initializable {

    @FXML
    private TextField textFieldNumero;

    @FXML
    private Button buttonCalcola;

    @FXML
    private Button buttonAnnulla;

    @FXML
    private Button buttonContinua;

    @FXML
    private VBox vBoxTabella;

    @FXML
    private TableView<RigaTabellaNumeriPrimi> tableNumeriPrimi;

    @FXML
    private TableColumn<RigaTabellaNumeriPrimi, String> columnNumero;

    @FXML
    private TableColumn<RigaTabellaNumeriPrimi, String> columnDivisorePrimo;

    @FXML
    private TableColumn<RigaTabellaNumeriPrimi, String> columnPotenzaDivisore;

    @FXML
    private TableColumn<RigaTabellaNumeriPrimi, String> columnRisultato;

    @FXML
    private TableColumn<RigaTabellaNumeriPrimi, String> columnTempiDiCalcolo;

    @FXML
    private Label labelRisultatoFattorizzazione;

    @FXML
    private Label labelFattori;

    @FXML
    private VBox vBoxProgressione;

    @FXML
    private ProgressBar progressBarBarraProgressione;

    @FXML
    private Label labelPercentuale;

    private String numeroCercato;

    GeneraTabellaPrimiTask taskGeneraTabellaPrimi;

    List<RigaTabellaNumeriPrimi> listaRigaTabellaNumeriPrimi = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCampoDiTestoNumericoMaggioreDiZero(textFieldNumero);
        textFieldNumero.textProperty().addListener((observable, vecchioValore, nuovoValore) -> buttonCalcola.setDisable(nuovoValore == null || nuovoValore.isEmpty() || nuovoValore.equals(numeroCercato) || vBoxProgressione.isVisible() || "1".equals(nuovoValore)));
        Platform.runLater(() ->
                buttonCalcola.getScene().setOnKeyPressed(evento -> {
                    if (evento.getCode() == KeyCode.ENTER && !buttonCalcola.isDisable()) {
                        buttonCalcola.fire();
                        evento.consume();
                    }
                })
        );
    }

    @FXML
    protected void actionCalcola() {
        listaRigaTabellaNumeriPrimi.clear();
        calcola();
    }

    @FXML
    protected void actionAnnulla() {
        taskGeneraTabellaPrimi.setDaStoppare(true);
    }

    @FXML
    protected void actionContinua() {
        calcola();
    }

    private void calcola() {
        numeroCercato = textFieldNumero.getText();
        buttonCalcola.setDisable(true);
        vBoxTabella.setVisible(false);
        labelRisultatoFattorizzazione.setVisible(false);
        labelFattori.setVisible(false);
        vBoxProgressione.setVisible(true);
        vBoxProgressione.setManaged(true);
        textFieldNumero.setDisable(true);
        taskGeneraTabellaPrimi = new GeneraTabellaPrimiTask(new BigInteger(numeroCercato), listaRigaTabellaNumeriPrimi);
        progressBarBarraProgressione.progressProperty().bind(taskGeneraTabellaPrimi.progressProperty());
        taskGeneraTabellaPrimi.setOnSucceeded(evento -> triggeraEventoScomposizioneCompletata());
        Thread thread = new Thread(taskGeneraTabellaPrimi);
        thread.setDaemon(true);
        thread.start();
    }

    private void triggeraEventoScomposizioneCompletata() {
        List<RigaTabellaNumeriPrimi> listaNumeriPrimi = taskGeneraTabellaPrimi.getValue();
        tableNumeriPrimi.setItems(FXCollections.observableArrayList(listaNumeriPrimi));
        setAltezzaTabella(tableNumeriPrimi);
        columnNumero.setCellValueFactory(valoreCella -> new SimpleStringProperty(valoreCella.getValue().getNumero()));
        columnDivisorePrimo.setCellValueFactory(valoreCella -> new SimpleStringProperty(valoreCella.getValue().getDivisorePrimo()));
        columnPotenzaDivisore.setCellValueFactory(valoreCella -> new SimpleStringProperty(valoreCella.getValue().getPotenzaDivisore()));
        columnRisultato.setCellValueFactory(valoreCella -> new SimpleStringProperty(valoreCella.getValue().getRisultato()));
        columnTempiDiCalcolo.setCellValueFactory(valoreCella -> new SimpleStringProperty(valoreCella.getValue().getTempiDiCalcolo().toString() + "ms"));
        vBoxTabella.setVisible(true);
        if (listaNumeriPrimi.stream().anyMatch(rigaTabellaNumeriPrimi -> "1".equals(rigaTabellaNumeriPrimi.getRisultato()))) {
            labelRisultatoFattorizzazione.setText("Risultato scomposizione " + numeroCercato + (tableNumeriPrimi.getItems().size() == 1 && "1".equals(tableNumeriPrimi.getItems().getFirst().getPotenzaDivisore()) ? " (Primo)" : " (Non Primo)") + " in " + getTempoTotale() + ":");
            buttonContinua.setVisible(false);
        } else {
            labelRisultatoFattorizzazione.setText("Risultato parziale scomposizione " + numeroCercato + (!tableNumeriPrimi.getItems().isEmpty() ? (" in " + getTempoTotale()) : "") + ":");
            buttonContinua.setVisible(true);
        }
        labelRisultatoFattorizzazione.setVisible(true);
        if (!tableNumeriPrimi.getItems().isEmpty())
            labelFattori.setText(getFattori());
        labelFattori.setVisible(true);
        vBoxProgressione.setVisible(false);
        vBoxProgressione.setManaged(false);
        textFieldNumero.setDisable(false);
    }

    private String getTempoTotale() {
        Long tempoTotale = 0L;
        for (RigaTabellaNumeriPrimi rigaTabellaNumeriPrimi : tableNumeriPrimi.getItems())
            tempoTotale += rigaTabellaNumeriPrimi.getTempiDiCalcolo();
        return tempoTotale + "ms";
    }

    private String getFattori() {
        StringBuilder elencoFattori = new StringBuilder();
        for (RigaTabellaNumeriPrimi rigaTabellaNumeriPrimi : tableNumeriPrimi.getItems())
            elencoFattori.append(rigaTabellaNumeriPrimi.getDivisorePrimo()).append(new BigInteger(rigaTabellaNumeriPrimi.getPotenzaDivisore()).compareTo(BigInteger.ONE) > 0 ? ("^" + rigaTabellaNumeriPrimi.getPotenzaDivisore()) : "").append(" * ");
        return elencoFattori.substring(0, elencoFattori.toString().length() - 3);
    }
}