package com.evidenziatore.numeriprimi;

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
import java.util.ResourceBundle;

public class NumeriPrimiController extends BaseController implements Initializable {

    @FXML
    private TextField numeroInput;

    @FXML
    private Button calcola;

    @FXML
    private TableView<NumeriPrimiObject> tabella;

    @FXML
    private TableColumn<NumeriPrimiObject, String> numero;

    @FXML
    private TableColumn<NumeriPrimiObject, String> divisorePrimo;

    @FXML
    private TableColumn<NumeriPrimiObject, String> potenzaDivisore;

    @FXML
    private TableColumn<NumeriPrimiObject, String> risultato;

    @FXML
    private TableColumn<NumeriPrimiObject, String> tempiDiCalcolo;

    @FXML
    private Label risultatoFattorizzazione;

    @FXML
    private Label fattori;

    @FXML
    private VBox progress;

    @FXML
    private ProgressBar barra;

    @FXML
    private Label percentuale;

    private String numeroCercato;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTextFieldNumerico(numeroInput);
        numeroInput.textProperty().addListener((observable, oldValue, newValue) -> {
            calcola.setDisable(newValue == null || newValue.isEmpty() || newValue.equals(numeroCercato) || progress.isVisible());
        });
        Platform.runLater(() -> {
            calcola.getScene().setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER && !calcola.isDisable()) {
                    calcola.fire();
                    event.consume();
                }
            });
        });
    }

    @FXML
    protected void actionCalcola() {
        numeroCercato = numeroInput.getText();
        calcola.setDisable(true);
        tabella.setVisible(false);
        risultatoFattorizzazione.setVisible(false);
        fattori.setVisible(false);
        progress.setVisible(true);
        numeroInput.setDisable(true);
        GeneraTabellaPrimiTask task = new GeneraTabellaPrimiTask(new BigInteger(numeroCercato));
        barra.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(event -> {
            tabella.setItems(FXCollections.observableArrayList(task.getValue()));
            setTableSize(tabella);
            numero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumero()));
            divisorePrimo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDivisorePrimo()));
            potenzaDivisore.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPotenzaDivisore()));
            risultato.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRisultato()));
            tempiDiCalcolo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTempiDiCalcolo().toString() + "ms"));
            tabella.setVisible(true);
            risultatoFattorizzazione.setText("Risultato scomposizione " + numeroCercato + (tabella.getItems().size() > 1 ? " (Non Primo)" : " (Primo)") + " in " + getTempoTotale() + ":");
            risultatoFattorizzazione.setVisible(true);
            fattori.setText(getFattori());
            fattori.setVisible(true);
            progress.setVisible(false);
            numeroInput.setDisable(false);
        });
        new Thread(task).start();
    }

    private String getTempoTotale() {
        Long tempoTotale = 0L;
        for (NumeriPrimiObject e : tabella.getItems()) {
            tempoTotale += e.getTempiDiCalcolo();
        }
        return tempoTotale + "ms";
    }

    private String getFattori() {
        StringBuilder elencoFattori = new StringBuilder();
        for (NumeriPrimiObject e : tabella.getItems()) {
            elencoFattori.append(e.getDivisorePrimo()).append(new BigInteger(e.getPotenzaDivisore()).compareTo(BigInteger.ONE) > 0 ? ("^" + e.getPotenzaDivisore()) : "").append(" * ");
        }
        return elencoFattori.substring(0, elencoFattori.toString().length() - 3);
    }
}