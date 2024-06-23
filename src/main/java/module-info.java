module com.evidenziatore.numeriprimi {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.evidenziatore.numeriprimi to javafx.fxml;
    exports com.evidenziatore.numeriprimi;
}