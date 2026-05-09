package com.example.valuwise.controller;

import com.example.valuwise.model.House;
import com.example.valuwise.util.SehirVerileri;
import com.example.valuwise.service.pricing.HousePricingEngine;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HouseController {
    @FXML private TextField houseAreaField, houseFloorField;
    @FXML private ComboBox<String> houseBathroomsComboBox;
    @FXML private ComboBox<String> houseAgeComboBox;
    @FXML private ComboBox<String> houseRoomsComboBox;
    @FXML private ComboBox<String> konutTipiComboBox;
    @FXML private CheckBox checkDenizManzarasi, checkOtopark, checkAsansor, checkDeprem;
    @FXML private RadioButton radioKrediVar, radioKrediYok, radioBalkonVar, radioBalkonYok;
    @FXML private Label houseResultLabel;
    @FXML private Label errorLabel;

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
                houseFloorField.setStyle(""); // once bir temizle
                if (houseFloorField.getText().equals("0")) {
                    houseFloorField.clear(); // eger icinde 0 kaldiysa temizle
                }

                // kutu bossa, bu kutuyu da otomatik olarak sari yap
                if (houseResultLabel.getStyle().contains("#d9a400") && houseFloorField.getText().trim().isEmpty()) {
                    houseFloorField.setStyle("-fx-border-color: #d9a400; -fx-border-width: 2px; -fx-border-radius: 3px;");
                }
            }
        });
        // ---------------------------------------------------------

        // banyo secenekleri
        houseBathroomsComboBox.getItems().addAll("Seçiniz", "Yok", "1", "2", "3", "4", "5", "6", "6 Üzeri");
        houseBathroomsComboBox.getSelectionModel().select(0);

        // bina yasi secenekleri
        houseAgeComboBox.getItems().addAll("Seçiniz", "0 (Oturuma Hazır)", "0 (Yapım Aşamasında)", "1-5 arası", "6-10 arası", "11-20 arası", "21-30 arası", "31-50 arası", "51 ve üzeri");
        houseAgeComboBox.getSelectionModel().select(0);

        houseRoomsComboBox.getItems().addAll("Seçiniz", "1+0", "1+1", "2+0", "2+1", "3+1", "3+2", "4+1", "4+2", "5+1", "5+2", "6+1", "6+2");
        houseRoomsComboBox.getSelectionModel().select(0);

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
        errorLabel.setVisible(false); // her hesaplama basinda kirmizi hatayi gizle
        houseResultLabel.setStyle(""); // hesaplama basladiginda rengi maviye dondurur

        houseAreaField.setStyle(""); // onceki kirmizi cizgileri temizle
        if (!houseFloorField.isDisable()) { // eger kat kutusu kilitli degilse
            houseFloorField.setStyle(""); // onun da kirmizi cizgisini temizle
        }
        //kutulari varsaiylan rengine dondurur
        konutTipiComboBox.setStyle("");
        cityComboBox.setStyle("");
        districtComboBox.setStyle("");
        houseHeatingComboBox.setStyle("");
        houseRoomsComboBox.setStyle("");
        houseBathroomsComboBox.setStyle("");
        houseAgeComboBox.setStyle("");
        radioKrediVar.setStyle("");
        radioKrediYok.setStyle("");
        radioBalkonVar.setStyle("");
        radioBalkonYok.setStyle("");

        try {
            //eksik veri girilen kutu varsa gosterir
            boolean bosKutuVar = false; // eksik kutulari tespit etmek icin degisken

            if (konutTipiComboBox.getValue() == null || konutTipiComboBox.getValue().equals("Seçiniz")) {
                konutTipiComboBox.setStyle("-fx-border-color: #d9a400; -fx-border-width: 2px; -fx-border-radius: 3px;"); // bos ise kirmizi yap
                bosKutuVar = true; // eksik isaretle
            }
            if (cityComboBox.getValue() == null || cityComboBox.getValue().equals("İl Seçiniz")) {
                cityComboBox.setStyle("-fx-border-color: #d9a400; -fx-border-width: 2px; -fx-border-radius: 3px;"); // bos ise kirmizi yap
                bosKutuVar = true; // eksik isaretle
            }
            if (districtComboBox.getValue() == null || districtComboBox.getValue().equals("İlçe Seçiniz")) {
                districtComboBox.setStyle("-fx-border-color: #d9a400; -fx-border-width: 2px; -fx-border-radius: 3px;"); // bos ise kirmizi yap
                bosKutuVar = true; // eksik isaretle
            }
            if (houseHeatingComboBox.getValue() == null || houseHeatingComboBox.getValue().equals("Seçiniz")) {
                houseHeatingComboBox.setStyle("-fx-border-color: #d9a400; -fx-border-width: 2px; -fx-border-radius: 3px;"); // bos ise kirmizi yap
                bosKutuVar = true; // eksik isaretle
            }
            if (houseRoomsComboBox.getValue() == null || houseRoomsComboBox.getValue().equals("Seçiniz")) {
                houseRoomsComboBox.setStyle("-fx-border-color: #d9a400; -fx-border-width: 2px; -fx-border-radius: 3px;"); // bos ise kirmizi yap
                bosKutuVar = true; // eksik isaretle
            }
            if (houseBathroomsComboBox.getValue() == null || houseBathroomsComboBox.getValue().equals("Seçiniz")) {
                houseBathroomsComboBox.setStyle("-fx-border-color: #d9a400; -fx-border-width: 2px; -fx-border-radius: 3px;"); // bos ise kirmizi yap
                bosKutuVar = true; // eksik isaretle
            }
            if (houseAgeComboBox.getValue() == null || houseAgeComboBox.getValue().equals("Seçiniz")) {
                houseAgeComboBox.setStyle("-fx-border-color: #d9a400; -fx-border-width: 2px; -fx-border-radius: 3px;"); // bos ise kirmizi yap
                bosKutuVar = true; // eksik isaretle
            }
            if (houseAreaField.getText().trim().isEmpty()) {
                houseAreaField.setStyle("-fx-border-color: #d9a400; -fx-border-width: 2px; -fx-border-radius: 3px;"); // bos ise kirmizi yap
                bosKutuVar = true; // eksik isaretle
            }
            if (!houseFloorField.isDisable() && houseFloorField.getText().trim().isEmpty()) {
                houseFloorField.setStyle("-fx-border-color: #d9a400; -fx-border-width: 2px; -fx-border-radius: 3px;"); // bos ise kirmizi yap
                bosKutuVar = true; // eksik isaretle
            }
            if (!radioKrediVar.isSelected() && !radioKrediYok.isSelected()) {
                radioKrediVar.setStyle("-fx-outer-border: #d9a400; -fx-inner-border: #d9a400;"); // sadece dis cemberi sari yapar
                radioKrediYok.setStyle("-fx-outer-border: #d9a400; -fx-inner-border: #d9a400;");
                bosKutuVar = true; // eksik isaretle
            }
            if (!radioBalkonVar.isSelected() && !radioBalkonYok.isSelected()) {
                radioBalkonVar.setStyle("-fx-outer-border: #d9a400; -fx-inner-border: #d9a400;"); // bu da digeri ile ayni sekilde
                radioBalkonYok.setStyle("-fx-outer-border: #d9a400; -fx-inner-border: #d9a400;");
                bosKutuVar = true; // eksik isaretle
            }

            if (bosKutuVar) { // eksik kutu bulunduysa
                throw new IllegalArgumentException("Eksik veri girişi yapıldı!"); // isleyisi durdur ve asagiya yolla
            }

            boolean harfHatasiVar = false; // harf girilip girilmedigini takip eden degisken

            try { // metrekare kutusunda harf var mi diye test et
                Double.parseDouble(houseAreaField.getText().trim());
            } catch (NumberFormatException ex) {
                houseAreaField.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 3px;"); // harf varsa kutuyu kirmizi yap
                harfHatasiVar = true; // hatayi isaretle
            }

            if (!houseFloorField.isDisable()) { // kat kutusu aciksa
                try { // kat kutusunda harf var mi diye test et
                    Integer.parseInt(houseFloorField.getText().trim());
                } catch (NumberFormatException ex) {
                    houseFloorField.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 3px;"); // harf varsa kutuyu kirmizi yap
                    harfHatasiVar = true; // hatayi isaretle
                }
            }

            if (harfHatasiVar) { // kutulardan birinde hata bulunduysa
                throw new NumberFormatException(); // islemi durdurup asagidaki kirmizi yaziya yolla
            }

            // oda sayisi cevirici
            String odaSecimi = houseRoomsComboBox.getValue();
            int hesaplananOdaSayisi = 0;
            if (odaSecimi != null && !odaSecimi.equals("Seçiniz")) {
                String[] parcalar = odaSecimi.split("\\+");
                hesaplananOdaSayisi = Integer.parseInt(parcalar[0]) + Integer.parseInt(parcalar[1]);
            }

            // banyo sayisi cevirici ("yok" ise 0, "6 uzeri" ise 7 yap)
            String banyoSecimi = houseBathroomsComboBox.getValue();
            int hesaplananBanyo = 0;
            if (banyoSecimi.equals("Yok")) hesaplananBanyo = 0;
            else if (banyoSecimi.equals("6 Üzeri")) hesaplananBanyo = 7;
            else hesaplananBanyo = Integer.parseInt(banyoSecimi);

            // bina yasi cevirici (araliklari matematiksel ortalamalara donustur)
            String yasSecimi = houseAgeComboBox.getValue();
            int hesaplananYas = 0;
            if (yasSecimi.contains("0")) hesaplananYas = 0;
            else if (yasSecimi.equals("1-5 arası")) hesaplananYas = 3;
            else if (yasSecimi.equals("6-10 arası")) hesaplananYas = 8;
            else if (yasSecimi.equals("11-20 arası")) hesaplananYas = 15;
            else if (yasSecimi.equals("21-30 arası")) hesaplananYas = 25;
            else if (yasSecimi.equals("31-50 arası")) hesaplananYas = 41;
            else if (yasSecimi.equals("51 ve üzeri")) hesaplananYas = 55;

            House yeniEv = new House(
                    "ID-" + System.currentTimeMillis(),
                    konutTipiComboBox.getValue(),
                    cityComboBox.getValue(),
                    districtComboBox.getValue(),
                    Double.parseDouble(houseAreaField.getText().trim()),
                    hesaplananOdaSayisi,
                    hesaplananBanyo, // cevirilen banyo sayisi
                    hesaplananYas,   // cevirilen bina yasi
                    Integer.parseInt(houseFloorField.getText().trim()),
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

        } catch (NumberFormatException e) {
            houseResultLabel.setText("Tahmini Değer: -- ₺");
            errorLabel.setText("Sayısal alanlara sadece sayı giriniz!"); // hatanin tam sebebini ekrana yazdir
            errorLabel.setVisible(true);
        } catch (Exception e) {
            houseResultLabel.setText("Hata: Verileri eksiksiz girin!");
            houseResultLabel.setStyle("-fx-text-fill: #d9a400;");
            errorLabel.setVisible(false);
        }
    }

    //formu sifirla butonu
    @FXML
    protected void onClearButtonClick() {
        // tum metin kutularini temizliyoruz
        konutTipiComboBox.getSelectionModel().select(0);
        houseAreaField.clear();
        houseRoomsComboBox.getSelectionModel().select(0);
        houseFloorField.clear(); // kat alanı

        houseBathroomsComboBox.getSelectionModel().select(0);
        houseAgeComboBox.getSelectionModel().select(0);

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

        // formu sifirlayinca kirmizi hatayi da gizle
        errorLabel.setVisible(false);

        // kutulardaki sari hata cizgilerini temizle
        konutTipiComboBox.setStyle("");
        cityComboBox.setStyle("");
        districtComboBox.setStyle("");
        houseAreaField.setStyle("");
        houseHeatingComboBox.setStyle("");
        houseRoomsComboBox.setStyle("");
        houseBathroomsComboBox.setStyle("");
        houseAgeComboBox.setStyle("");
        radioKrediVar.setStyle("");
        radioKrediYok.setStyle("");
        radioBalkonVar.setStyle("");
        radioBalkonYok.setStyle("");
        houseResultLabel.setStyle("");

        // imleci en bastaki kutuya yonlendiriyoruz
        konutTipiComboBox.requestFocus();
    }
}
