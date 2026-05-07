package com.example.valuwise.controller;

import com.example.valuwise.model.House;
import com.example.valuwise.util.SehirVerileri;
import com.example.valuwise.service.pricing.HousePricingEngine;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HouseController {
    @FXML private TextField houseAreaField, houseRoomsField, houseBathroomsField, houseAgeField, houseFloorField;
    @FXML private ComboBox<String> konutTipiComboBox;
    @FXML private CheckBox checkDenizManzarasi, checkOtopark, checkAsansor, checkDeprem;
    @FXML private RadioButton radioKrediVar, radioKrediYok, radioBalkonVar, radioBalkonYok;
    @FXML private Label houseResultLabel;

    @FXML private ComboBox<String> cityComboBox;
    @FXML private ComboBox<String> districtComboBox;
    @FXML private ComboBox<String> houseHeatingComboBox;

    @FXML
    public void initialize() {
        konutTipiComboBox.getItems().addAll("Seçiniz", "Daire", "Rezidans", "Müstakil Ev", "Villa", "Köy Evi", "Yazlık", "Yalı / Yalı Dairesi", "Prefabrik Ev", "Gecekondu");
        konutTipiComboBox.getSelectionModel().select(0);
        konutTipiComboBox.setOnAction(event -> {
            String secilenTip = konutTipiComboBox.getValue();

            // kat kavrami olmayan ev tipleri
            boolean katKilitlensinMi = secilenTip != null && (
                    secilenTip.equals("Müstakil Ev") ||
                            secilenTip.equals("Villa") ||
                            secilenTip.equals("Köy Evi") ||
                            secilenTip.equals("Yazlık") ||
                            secilenTip.equals("Prefabrik Ev") ||
                            secilenTip.equals("Gecekondu") ||
                            secilenTip.equals("Yalı / Yalı Dairesi")
            );

            if (katKilitlensinMi) {
                houseFloorField.setText("0"); // villa vb ise kat 0 olsun
                houseFloorField.setDisable(true); // kutuyu kilitle
                houseFloorField.setStyle("-fx-opacity: 0.6; -fx-background-color: #e0e0e0;");
            } else {
                houseFloorField.setDisable(false); // daire ise ac
                houseFloorField.setStyle("");
                if (houseFloorField.getText().equals("0")) {
                    houseFloorField.clear(); // eger icinde 0 kaldiysa temizle
                }
            }
        });
        // ---------------------------------------------------------

        houseHeatingComboBox.getItems().addAll("Seçiniz", "Kombi (Doğalgaz)", "Merkezi", "Soba", "Klima", "Elektrikli Isıtıcı");
        houseHeatingComboBox.getSelectionModel().select(0);

        // sehir hanesi
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

                // ilce yaziniz i tekrar ekle
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
            if (konutTipiComboBox.getValue() == null || konutTipiComboBox.getValue().equals("Seçiniz") ||
                    cityComboBox.getValue() == null || cityComboBox.getValue().equals("İl Seçiniz") ||
                    districtComboBox.getValue() == null || districtComboBox.getValue().equals("İlçe Seçiniz") ||
                    houseHeatingComboBox.getValue() == null || houseHeatingComboBox.getValue().equals("Seçiniz") ||
                    (!radioKrediVar.isSelected() && !radioKrediYok.isSelected()) ||
                    (!radioBalkonVar.isSelected() && !radioBalkonYok.isSelected())) {
                //bunlar eksikse hata
                throw new IllegalArgumentException("Eksik veri girişi yapıldı!");
            }

            // "1" olan sabit kat değerini houseFloorField ile değiştirdim.
            // Yeni parametreleri constructor'a ekledim.
            House yeniEv = new House(
                    "ID-" + System.currentTimeMillis(),
                    konutTipiComboBox.getValue(), // <-- İLAN BAŞLIĞI YERİNE ARTIK KONUT TİPİNİ YOLLUYORUZ
                    cityComboBox.getValue(),
                    districtComboBox.getValue(),
                    Double.parseDouble(houseAreaField.getText()),
                    Integer.parseInt(houseRoomsField.getText().replaceAll("[^0-9]", "")),
                    Integer.parseInt(houseBathroomsField.getText()),
                    Integer.parseInt(houseAgeField.getText()),
                    Integer.parseInt(houseFloorField.getText()),
                    checkAsansor.isSelected(),
                    checkOtopark.isSelected(),
                    checkDenizManzarasi.isSelected(),
                    checkDeprem.isSelected(),
                    radioBalkonVar.isSelected(),
                    radioKrediVar.isSelected(),
                    houseHeatingComboBox.getValue()
            );

            double sonuc = new HousePricingEngine().tahminiDegerHesapla(yeniEv);
            houseResultLabel.setText(String.format("Tahmini Değer: %,.2f ₺", sonuc));
        } catch (Exception e) {
            houseResultLabel.setText("Hata: Verileri eksiksiz girin!");
        }
    }

    //formu sifirla butonu
    @FXML
    protected void onClearButtonClick() {
        // tum metin kutularini temizliyoruz
        konutTipiComboBox.getSelectionModel().select(0);
        houseAreaField.clear();
        houseRoomsField.clear();
        houseBathroomsField.clear();
        houseAgeField.clear();
        houseFloorField.clear(); // kat alanı

        // tum tikleri kaldiriyoruz
        checkDenizManzarasi.setSelected(false);
        checkOtopark.setSelected(false);
        checkAsansor.setSelected(false);
        checkDeprem.setSelected(false);

        //eklendi
        radioKrediVar.setSelected(false);
        radioKrediYok.setSelected(false);
        radioBalkonVar.setSelected(false);
        radioBalkonYok.setSelected(false); //balkon ve radyo butonlarini bosaltir

        // sehir ve ilce secimini sifirla
        cityComboBox.getSelectionModel().select(0);
        houseHeatingComboBox.getSelectionModel().select(0);

        districtComboBox.getItems().clear();
        districtComboBox.getItems().add("İlçe Seçiniz");
        districtComboBox.getSelectionModel().select(0);
        districtComboBox.setDisable(true);

        houseFloorField.setDisable(false); // kilidi ac
        houseFloorField.setStyle(""); // rengi duzelt

        // alttaki yaziyi eski haline getiriyoruz
        houseResultLabel.setText("Tahmini Değer: -- ₺");

        // imleci en bastaki kutuya yonlendiriyoruz
        konutTipiComboBox.requestFocus();
    }
}
