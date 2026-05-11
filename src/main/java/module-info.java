module com.example.valuwise {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.valuwise to javafx.fxml;
    exports com.example.valuwise;
    requires com.fasterxml.jackson.databind;
    requires org.apache.pdfbox;
    opens com.example.valuwise.controller to javafx.fxml;
}