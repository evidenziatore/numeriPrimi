package com.evidenziatore.numeriprimi.controller;

import com.evidenziatore.numeriprimi.task.TaskGeneraTabellaPrimi;
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

public class ControllerNumeriPrimi extends BaseController implements Initializable {

    @FXML
    private TextField textFieldNumero;
    @FXML
    private Button buttonCalcola;

    @FXML
    private VBox vBoxProgressione;
    @FXML
    private ProgressBar progressBarBarraProgressione;

    @FXML
    private Label labelRisultatoFattorizzazione;
    @FXML
    private Label labelFattori;

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
    private Button buttonContinua;

    private String numeroCercato;
    TaskGeneraTabellaPrimi taskGeneraTabellaPrimi;
    List<RigaTabellaNumeriPrimi> listaRigaTabellaNumeriPrimi = new ArrayList<>();

    /**
     * @param url               I'URL del file .fxml che corrisponde a questo controller.
     * @param caricatoreRisorse Il ResourceBundle che carica le risorse.
     */
    @Override
    public void initialize(URL url, ResourceBundle caricatoreRisorse) {
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
        generaTabellaECalcolaVisibilitaComponenti();
    }

    @FXML
    protected void actionAnnulla() {
        taskGeneraTabellaPrimi.setDaStoppare(true);
    }

    @FXML
    protected void actionContinua() {
        generaTabellaECalcolaVisibilitaComponenti();
    }

    private void generaTabellaECalcolaVisibilitaComponenti() {
        numeroCercato = textFieldNumero.getText();
        buttonCalcola.setDisable(true);
        vBoxTabella.setVisible(false);
        labelRisultatoFattorizzazione.setVisible(false);
        labelFattori.setVisible(false);
        vBoxProgressione.setVisible(true);
        vBoxProgressione.setManaged(true);
        textFieldNumero.setDisable(true);
        taskGeneraTabellaPrimi = new TaskGeneraTabellaPrimi(new BigInteger(numeroCercato), listaRigaTabellaNumeriPrimi);
        progressBarBarraProgressione.progressProperty().bind(taskGeneraTabellaPrimi.progressProperty());
        taskGeneraTabellaPrimi.setOnSucceeded(evento -> triggheraEventoDopoCompletamentoScomposizione());
        Thread thread = new Thread(taskGeneraTabellaPrimi);
        thread.setDaemon(true);
        thread.start();
    }

    private void triggheraEventoDopoCompletamentoScomposizione() {
        List<RigaTabellaNumeriPrimi> listaNumeriPrimi = taskGeneraTabellaPrimi.getValue();
        tableNumeriPrimi.setItems(FXCollections.observableArrayList(listaNumeriPrimi));
        setAltezzaTabella(tableNumeriPrimi);
        columnNumero.setCellValueFactory(valoreCella -> new SimpleStringProperty(valoreCella.getValue().numero()));
        columnDivisorePrimo.setCellValueFactory(valoreCella -> new SimpleStringProperty(valoreCella.getValue().divisorePrimo()));
        columnPotenzaDivisore.setCellValueFactory(valoreCella -> new SimpleStringProperty(valoreCella.getValue().potenzaDivisore()));
        columnRisultato.setCellValueFactory(valoreCella -> new SimpleStringProperty(valoreCella.getValue().risultato()));
        columnTempiDiCalcolo.setCellValueFactory(valoreCella -> new SimpleStringProperty(valoreCella.getValue().tempiDiCalcolo().toString() + "ms"));
        vBoxTabella.setVisible(true);
        if (listaNumeriPrimi.stream().anyMatch(rigaTabellaNumeriPrimi -> "1".equals(rigaTabellaNumeriPrimi.risultato()))) {
            labelRisultatoFattorizzazione.setText("Risultato scomposizione " + numeroCercato + (tableNumeriPrimi.getItems().size() == 1 && "1".equals(tableNumeriPrimi.getItems().getFirst().potenzaDivisore()) ? " (Primo)" : " (Non Primo)") + " in " + getTempoTotale() + ":");
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

    /**
     * @return Il tempo totale impiegato per scomporre in fattori primi il numero inserito nel TextField dall'utente.
     */
    private String getTempoTotale() {
        Long tempoTotale = 0L;
        for (RigaTabellaNumeriPrimi rigaTabellaNumeriPrimi : tableNumeriPrimi.getItems())
            tempoTotale += rigaTabellaNumeriPrimi.tempiDiCalcolo();
        return tempoTotale + "ms";
    }

    /**
     * @return L'elenco dei fattori primi completi di esponente del numero inserito nel TextField dall'utente.
     */
    private String getFattori() {
        StringBuilder elencoFattori = new StringBuilder();
        for (RigaTabellaNumeriPrimi rigaTabellaNumeriPrimi : tableNumeriPrimi.getItems())
            elencoFattori.append(rigaTabellaNumeriPrimi.divisorePrimo()).append(new BigInteger(rigaTabellaNumeriPrimi.potenzaDivisore()).compareTo(BigInteger.ONE) > 0 ? ("^" + rigaTabellaNumeriPrimi.potenzaDivisore()) : "").append(" * ");
        return elencoFattori.substring(0, elencoFattori.toString().length() - 3);
    }
}