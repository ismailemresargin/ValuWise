package com.example.valuwise;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {
    @FXML private TextField ilanBasligiField, houseLocationField, houseAreaField, houseRoomsField, houseBathroomsField, houseAgeField;
    @FXML private CheckBox checkDenizManzarasi, checkOtopark, checkAsansor, checkDeprem;
    @FXML private Label houseResultLabel;

    @FXML
    protected void onCalculateButtonClick() {
        try {
            House yeniEv = new House(
                    "ID-" + System.currentTimeMillis(),
                    ilanBasligiField.getText(), houseLocationField.getText(),
                    Double.parseDouble(houseAreaField.getText()),
                    Integer.parseInt(houseRoomsField.getText().replaceAll("[^0-9]", "")),
                    Integer.parseInt(houseBathroomsField.getText()),
                    Integer.parseInt(houseAgeField.getText()),
                    1, checkAsansor.isSelected(), checkOtopark.isSelected(),
                    checkDenizManzarasi.isSelected(), checkDeprem.isSelected()
            );

            double sonuc = new HousePricingEngine().tahminiDegerHesapla(yeniEv);
            houseResultLabel.setText(String.format("Tahmini Değer: %,.2f ₺", sonuc));
        } catch (Exception e) {
            houseResultLabel.setText("Hata: Verileri kontrol edin!");
        }
    }
}