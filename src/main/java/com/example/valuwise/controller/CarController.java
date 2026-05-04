package com.example.valuwise.controller;

import com.example.valuwise.model.Car;
import com.example.valuwise.service.pricing.CarPricingEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CarController {

    @FXML private TextField carBrandField;
    @FXML private TextField carModelField;
    @FXML private TextField carYearField;
    @FXML private TextField fuelTypeField;
    @FXML private TextField kmField;
    @FXML private TextField transmissionField;
    @FXML private TextField tramerField;
    @FXML private TextField damageStatusField;
    @FXML private Label carResultLabel;

    @FXML
    protected void onCalculateButtonClick() {
        try {
            Car arac = new Car(
                    "ID-" + System.currentTimeMillis(),
                    carBrandField.getText(),
                    carModelField.getText(),
                    Integer.parseInt(carYearField.getText().trim()),
                    fuelTypeField.getText().trim(),
                    Integer.parseInt(kmField.getText().trim().replaceAll("[^0-9]", "")),
                    transmissionField.getText().trim(),
                    tramerField.getText().trim().isEmpty() ? 0 : Double.parseDouble(tramerField.getText().trim().replaceAll("[^0-9.]", "")),
                    damageStatusField.getText().trim().isEmpty() ? 0 : Integer.parseInt(damageStatusField.getText().trim().replaceAll("[^0-9]", ""))
            );

            double sonuc = new CarPricingEngine().tahminiDegerHesapla(arac);
            carResultLabel.setText(String.format("Tahmini Değer: %,.0f ₺", sonuc));
        } catch (NumberFormatException e) {
            carResultLabel.setText("Hata: Sayisal alanlari kontrol edin!");
        } catch (Exception e) {
            carResultLabel.setText("Hata: Verileri kontrol edin!");
        }
    }

    @FXML
    protected void onClearButtonClick() {
        carBrandField.clear();
        carModelField.clear();
        carYearField.clear();
        fuelTypeField.clear();
        kmField.clear();
        transmissionField.clear();
        tramerField.clear();
        damageStatusField.clear();
        carResultLabel.setText("Tahmini Deger: -- ₺");
    }
}
