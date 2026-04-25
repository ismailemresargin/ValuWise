package com.example.valuwise;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {
    @FXML private TextField ilanBasligiField, houseAreaField, houseRoomsField, houseBathroomsField, houseAgeField;
    @FXML private CheckBox checkDenizManzarasi, checkOtopark, checkAsansor, checkDeprem;
    @FXML private Label houseResultLabel;

    @FXML private ComboBox<String> cityComboBox;
    @FXML private ComboBox<String> districtComboBox;
    @FXML
    public void initialize() {
        cityComboBox.getItems().addAll(SehirVerileri.getIller());
        districtComboBox.setDisable(true);

        cityComboBox.setOnAction(event -> {
            String secilenSehir = cityComboBox.getValue();
            if (secilenSehir != null) {
                districtComboBox.getItems().clear();

                //ilce yaziniz i tekrar ekle
                districtComboBox.getItems().add("İlçe Seçiniz");

                // yeni ile gore ilceleri yukle
                districtComboBox.getItems().addAll(SehirVerileri.getIlceler(secilenSehir));

                // yazının varsayılan olarak secili gelmesini sağla
                districtComboBox.getSelectionModel().select(0);

                districtComboBox.setDisable(false);
            }
        });
    }
    protected void onCalculateButtonClick() {
        try {
            House yeniEv = new House(
                    "ID-" + System.currentTimeMillis(),
                    ilanBasligiField.getText(), cityComboBox.getValue(),
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