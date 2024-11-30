module com.evidenziatore.numeriprimi {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.evidenziatore.numeriprimi to javafx.fxml;
    exports com.evidenziatore.numeriprimi;
    exports com.evidenziatore.numeriprimi.controller;
    opens com.evidenziatore.numeriprimi.controller to javafx.fxml;
    exports com.evidenziatore.numeriprimi.task;
    opens com.evidenziatore.numeriprimi.task to javafx.fxml;
    exports com.evidenziatore.numeriprimi.entita;
    opens com.evidenziatore.numeriprimi.entita to javafx.fxml;
}