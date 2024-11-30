package com.evidenziatore.numeriprimi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NumeriPrimiApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader caricatoreFXML = new FXMLLoader(NumeriPrimiApplication.class.getResource("numeriPrimiView.fxml"));
        Scene scena = new Scene(caricatoreFXML.load(), 800, 600);
        scena.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        stage.setScene(scena);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image.png"))));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}