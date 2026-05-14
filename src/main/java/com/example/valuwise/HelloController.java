package com.example.valuwise;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class HelloController {

    @FXML private TabPane mainTabPane;
    @FXML private Tab karsilastirTab;
    @FXML private Label welcomeText;

    public static HelloController instance;

    @FXML
    public void initialize() {
        instance = this;
    }

    public void karsilastirTabineGit() {
        karsilastirTab.setDisable(false);
        mainTabPane.getSelectionModel().select(karsilastirTab);
    }

    @FXML
    protected void onHelloButtonClick() {
        if (welcomeText != null)
            welcomeText.setText("Welcome to JavaFX Application!");
    }
}
