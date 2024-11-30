package com.evidenziatore.numeriprimi;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
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
import java.util.concurrent.atomic.AtomicBoolean;

public class NumeriPrimiController extends BaseController implements Initializable {

    @FXML
    private TextField numeroInput;

    @FXML
    private Button calcola;

    @FXML
    private Button annulla;

    @FXML
    private Button continua;

    @FXML
    private VBox tableBox;

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

    GeneraTabellaPrimiTask task;

    List<NumeriPrimiObject> numeriPrimiObjectList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTextFieldNumericoMaggioreDiZero(numeroInput);
        numeroInput.textProperty().addListener((observable, oldValue, newValue) -> {
            calcola.setDisable(newValue == null || newValue.isEmpty() || newValue.equals(numeroCercato) || progress.isVisible() || "1".equals(newValue));
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
        numeriPrimiObjectList.clear();
        calcola();
    }

    @FXML
    protected void actionAnnulla() {
        task.setDaCancellare(true);
    }

    @FXML
    protected void actionContinua() {
        calcola();
    }

    private void calcola() {
        numeroCercato = numeroInput.getText();
        calcola.setDisable(true);
        tableBox.setVisible(false);
        risultatoFattorizzazione.setVisible(false);
        fattori.setVisible(false);
        progress.setVisible(true);
        progress.setManaged(true);
        numeroInput.setDisable(true);
        task = new GeneraTabellaPrimiTask(new BigInteger(numeroCercato), numeriPrimiObjectList);
        barra.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(event -> onEventTriggered());
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void onEventTriggered() {
        List<NumeriPrimiObject> taskValue = task.getValue();
        tabella.setItems(FXCollections.observableArrayList(taskValue));
        setTableSize(tabella);
        numero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumero()));
        divisorePrimo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDivisorePrimo()));
        potenzaDivisore.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPotenzaDivisore()));
        risultato.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRisultato()));
        tempiDiCalcolo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTempiDiCalcolo().toString() + "ms"));
        tableBox.setVisible(true);
        if (taskValue.stream().anyMatch(obj -> "1".equals(obj.getRisultato()))) {
            risultatoFattorizzazione.setText("Risultato scomposizione " + numeroCercato + (tabella.getItems().size() == 1 && "1".equals(tabella.getItems().getFirst().getPotenzaDivisore()) ? " (Primo)" : " (Non Primo)") + " in " + getTempoTotale() + ":");
        } else {
            risultatoFattorizzazione.setText("Risultato parziale scomposizione " + numeroCercato + (!tabella.getItems().isEmpty() ? (" in " + getTempoTotale()) : "") + ":");
        }
        risultatoFattorizzazione.setVisible(true);
        if (!tabella.getItems().isEmpty())
            fattori.setText(getFattori());
        fattori.setVisible(true);
        progress.setVisible(false);
        progress.setManaged(false);
        numeroInput.setDisable(false);
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