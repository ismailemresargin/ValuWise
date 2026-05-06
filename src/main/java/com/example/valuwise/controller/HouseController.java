package com.example.valuwise.controller;

import com.example.valuwise.model.House;
import com.example.valuwise.util.SehirVerileri;
import com.example.valuwise.service.pricing.HousePricingEngine;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HouseController {
    @FXML private TextField ilanBasligiField, houseAreaField, houseRoomsField, houseBathroomsField, houseAgeField;
    @FXML private CheckBox checkDenizManzarasi, checkOtopark, checkAsansor, checkDeprem;
    @FXML private Label houseResultLabel;

    @FXML private ComboBox<String> cityComboBox;
    @FXML private ComboBox<String> districtComboBox;
    @FXML
    public void initialize() {
        //sehir hanesu
        cityComboBox.getItems().clear();
        cityComboBox.getItems().add("İl Seçiniz");
        cityComboBox.getItems().addAll(SehirVerileri.getIller());
        cityComboBox.getSelectionModel().select(0); // İlk açılışta "İl Seçiniz" seçili gelsin

        // ilce hanesi
        districtComboBox.getItems().clear();
        districtComboBox.getItems().add("İlçe Seçiniz");
        districtComboBox.getSelectionModel().select(0);
        districtComboBox.setDisable(true);

        // sehir secildiginde
        cityComboBox.setOnAction(event -> {
            String secilenSehir = cityComboBox.getValue();
            // eger "İl Seçiniz" disinda gercek bir sehir secildiyse:
            if (secilenSehir != null && !secilenSehir.equals("İl Seçiniz")) {
                districtComboBox.getItems().clear();

                //ilce yaziniz i tekrar ekle
                districtComboBox.getItems().add("İlçe Seçiniz");

                // yeni ile gore ilceleri yukle
                districtComboBox.getItems().addAll(SehirVerileri.getIlceler(secilenSehir));

                // yazının varsayılan olarak secili gelmesini sağla
                districtComboBox.getSelectionModel().select(0);

                districtComboBox.setDisable(false);
            } else {
                // eger tekrar "İl Seçiniz"e dönülürse ilceyi kilitle
                districtComboBox.getItems().clear();
                districtComboBox.getItems().add("İlçe Seçiniz");
                districtComboBox.getSelectionModel().select(0);
                districtComboBox.setDisable(true);
            }
        });
    }
    @FXML
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

    //formu sifirla butonu
    @FXML
    protected void onClearButtonClick() {
        // tum metin kutularini temizliyoruz
        ilanBasligiField.clear();
        houseAreaField.clear();
        houseRoomsField.clear();
        houseBathroomsField.clear();
        houseAgeField.clear();

        // tum tikleri kaldiriyoruz
        checkDenizManzarasi.setSelected(false);
        checkOtopark.setSelected(false);
        checkAsansor.setSelected(false);
        checkDeprem.setSelected(false);

        // sehir ve ilce secimini sifirla
        cityComboBox.getSelectionModel().select(0);

        districtComboBox.getItems().clear();
        districtComboBox.getItems().add("İlçe Seçiniz");
        districtComboBox.getSelectionModel().select(0);
        districtComboBox.setDisable(true);

        // alttaki yaziyi eski haline getiriyoruz
        houseResultLabel.setText("Tahmini Değer: -- ₺");

        // imleci en bastaki kutuya yonlendiriyoruz
        ilanBasligiField.requestFocus();
    }
}
