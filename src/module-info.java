module com.example.valuwise {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.valuwise to javafx.fxml;
    exports com.example.valuwise;
}