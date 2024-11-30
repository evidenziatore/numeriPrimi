package com.evidenziatore.numeriprimi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NumeriPrimiApplication extends Application {

    /**
     * @param maschera Lo Stage che contiene la scena.
     * @throws IOException Se il file FXML non può essere caricato correttamente.
     */
    @Override
    public void start(Stage maschera) throws IOException {
        FXMLLoader caricatoreFXML = new FXMLLoader(NumeriPrimiApplication.class.getResource("numeriPrimiView.fxml"));
        maschera.setScene(generaScena(caricatoreFXML));
        maschera.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image.png"))));
        maschera.show();
    }

    /**
     * @param caricatoreFXML Il FXMLLoader che carica il file FXML per la scena.
     * @return La scena generata a partire dal file FXML caricato.
     * @throws IOException Se il file FXML non può essere caricato correttamente.
     */
    private Scene generaScena(FXMLLoader caricatoreFXML) throws IOException {
        double lunghezzaSchermo = Screen.getPrimary().getVisualBounds().getWidth();
        double altezzaSchermo = Screen.getPrimary().getVisualBounds().getHeight();
        Scene scena = new Scene(caricatoreFXML.load(), lunghezzaSchermo / 3, altezzaSchermo / 3);
        scena.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        return scena;
    }

    public static void main(String[] args) {
        launch();
    }
}