module com.example.valuwise {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.fasterxml.jackson.databind;
    opens com.example.valuwise to javafx.fxml;
    exports com.example.valuwise;
}
