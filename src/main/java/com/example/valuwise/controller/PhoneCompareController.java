package com.example.valuwise.controller;

import com.example.valuwise.model.Phone;
import com.example.valuwise.service.pricing.PhonePricingEngine;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import org.controlsfx.control.CheckComboBox;
import java.util.*;

public class PhoneCompareController {

    private static final Map<String, Double> EKRAN_DB = new LinkedHashMap<>();
    static {
        EKRAN_DB.put("iPhone 11", 6.1); EKRAN_DB.put("iPhone 11 Pro", 5.8);
        EKRAN_DB.put("iPhone 12", 6.1); EKRAN_DB.put("iPhone 12 Pro", 6.1); EKRAN_DB.put("iPhone 12 Pro Max", 6.7);
        EKRAN_DB.put("iPhone 13", 6.1); EKRAN_DB.put("iPhone 13 Mini", 5.4); EKRAN_DB.put("iPhone 13 Pro", 6.1); EKRAN_DB.put("iPhone 13 Pro Max", 6.7);
        EKRAN_DB.put("iPhone 14", 6.1); EKRAN_DB.put("iPhone 14 Plus", 6.7); EKRAN_DB.put("iPhone 14 Pro", 6.1); EKRAN_DB.put("iPhone 14 Pro Max", 6.7);
        EKRAN_DB.put("iPhone 15", 6.1); EKRAN_DB.put("iPhone 15 Plus", 6.7); EKRAN_DB.put("iPhone 15 Pro", 6.1); EKRAN_DB.put("iPhone 15 Pro Max", 6.7);
        EKRAN_DB.put("iPhone 16", 6.1); EKRAN_DB.put("iPhone 16 Plus", 6.7); EKRAN_DB.put("iPhone 16 Pro", 6.3); EKRAN_DB.put("iPhone 16 Pro Max", 6.9);
        EKRAN_DB.put("Galaxy A34", 6.6); EKRAN_DB.put("Galaxy A54", 6.4); EKRAN_DB.put("Galaxy A55", 6.6); EKRAN_DB.put("Galaxy A35", 6.6);
        EKRAN_DB.put("Galaxy S22", 6.1); EKRAN_DB.put("Galaxy S22 Plus", 6.6); EKRAN_DB.put("Galaxy S22 Ultra", 6.8);
        EKRAN_DB.put("Galaxy S23", 6.1); EKRAN_DB.put("Galaxy S23 Plus", 6.6); EKRAN_DB.put("Galaxy S23 Ultra", 6.8); EKRAN_DB.put("Galaxy S23 FE", 6.4);
        EKRAN_DB.put("Galaxy S24", 6.2); EKRAN_DB.put("Galaxy S24 Plus", 6.7); EKRAN_DB.put("Galaxy S24 Ultra", 6.8); EKRAN_DB.put("Galaxy S24 FE", 6.7);
        EKRAN_DB.put("Galaxy Z Fold 5", 7.6); EKRAN_DB.put("Galaxy Z Flip 5", 6.7);
        EKRAN_DB.put("Redmi Note 12", 6.67); EKRAN_DB.put("Redmi Note 12 Pro", 6.67);
        EKRAN_DB.put("Redmi Note 13", 6.67); EKRAN_DB.put("Redmi Note 13 Pro", 6.67); EKRAN_DB.put("Redmi Note 13 Pro+", 6.67);
        EKRAN_DB.put("Xiaomi 12", 6.28); EKRAN_DB.put("Xiaomi 12 Pro", 6.73);
        EKRAN_DB.put("Xiaomi 13", 6.36); EKRAN_DB.put("Xiaomi 13 Pro", 6.73); EKRAN_DB.put("Xiaomi 13T", 6.67); EKRAN_DB.put("Xiaomi 13T Pro", 6.67);
        EKRAN_DB.put("Xiaomi 14", 6.36); EKRAN_DB.put("Xiaomi 14 Ultra", 6.73);
        EKRAN_DB.put("Reno 10", 6.7); EKRAN_DB.put("Reno 10 Pro", 6.7); EKRAN_DB.put("Reno 11", 6.7); EKRAN_DB.put("Reno 11 Pro", 6.7);
        EKRAN_DB.put("Find X6 Pro", 6.82); EKRAN_DB.put("Find X7 Pro", 6.82);
        EKRAN_DB.put("OnePlus 11", 6.7); EKRAN_DB.put("OnePlus 12", 6.82); EKRAN_DB.put("OnePlus Nord 3", 6.74); EKRAN_DB.put("OnePlus Nord CE4", 6.67);
        EKRAN_DB.put("Pixel 7", 6.3); EKRAN_DB.put("Pixel 7 Pro", 6.7); EKRAN_DB.put("Pixel 7a", 6.1);
        EKRAN_DB.put("Pixel 8", 6.2); EKRAN_DB.put("Pixel 8 Pro", 6.7); EKRAN_DB.put("Pixel 8a", 6.1);
        EKRAN_DB.put("Pixel 9", 6.3); EKRAN_DB.put("Pixel 9 Pro", 6.3);
        EKRAN_DB.put("Nova 11", 6.7); EKRAN_DB.put("Nova 12", 6.7); EKRAN_DB.put("P50 Pro", 6.6); EKRAN_DB.put("P60 Pro", 6.67);
        EKRAN_DB.put("Mate 50 Pro", 6.74); EKRAN_DB.put("Mate 60 Pro", 6.82);
        EKRAN_DB.put("Moto G84", 6.55); EKRAN_DB.put("Moto G Power", 6.5);
        EKRAN_DB.put("Edge 40", 6.55); EKRAN_DB.put("Edge 40 Pro", 6.67); EKRAN_DB.put("Edge 50 Pro", 6.7); EKRAN_DB.put("Razr 40 Ultra", 6.9);
    }

    private static final Map<String, int[]> KAMERA_DB = new LinkedHashMap<>();
    static {
        KAMERA_DB.put("iPhone 11", new int[]{12, 12}); KAMERA_DB.put("iPhone 11 Pro", new int[]{12, 12});
        KAMERA_DB.put("iPhone 12", new int[]{12, 12}); KAMERA_DB.put("iPhone 12 Pro", new int[]{12, 12}); KAMERA_DB.put("iPhone 12 Pro Max", new int[]{12, 12});
        KAMERA_DB.put("iPhone 13", new int[]{12, 12}); KAMERA_DB.put("iPhone 13 Mini", new int[]{12, 12}); KAMERA_DB.put("iPhone 13 Pro", new int[]{12, 12}); KAMERA_DB.put("iPhone 13 Pro Max", new int[]{12, 12});
        KAMERA_DB.put("iPhone 14", new int[]{12, 12}); KAMERA_DB.put("iPhone 14 Plus", new int[]{12, 12}); KAMERA_DB.put("iPhone 14 Pro", new int[]{48, 12}); KAMERA_DB.put("iPhone 14 Pro Max", new int[]{48, 12});
        KAMERA_DB.put("iPhone 15", new int[]{48, 12}); KAMERA_DB.put("iPhone 15 Plus", new int[]{48, 12}); KAMERA_DB.put("iPhone 15 Pro", new int[]{48, 12}); KAMERA_DB.put("iPhone 15 Pro Max", new int[]{48, 12});
        KAMERA_DB.put("iPhone 16", new int[]{48, 12}); KAMERA_DB.put("iPhone 16 Plus", new int[]{48, 12}); KAMERA_DB.put("iPhone 16 Pro", new int[]{48, 12}); KAMERA_DB.put("iPhone 16 Pro Max", new int[]{48, 12});
        KAMERA_DB.put("Galaxy A34", new int[]{48, 13}); KAMERA_DB.put("Galaxy A54", new int[]{50, 32}); KAMERA_DB.put("Galaxy A55", new int[]{50, 32}); KAMERA_DB.put("Galaxy A35", new int[]{50, 13});
        KAMERA_DB.put("Galaxy S22", new int[]{50, 10}); KAMERA_DB.put("Galaxy S22 Plus", new int[]{50, 10}); KAMERA_DB.put("Galaxy S22 Ultra", new int[]{108, 40});
        KAMERA_DB.put("Galaxy S23", new int[]{50, 12}); KAMERA_DB.put("Galaxy S23 Plus", new int[]{50, 12}); KAMERA_DB.put("Galaxy S23 Ultra", new int[]{200, 12}); KAMERA_DB.put("Galaxy S23 FE", new int[]{50, 10});
        KAMERA_DB.put("Galaxy S24", new int[]{50, 12}); KAMERA_DB.put("Galaxy S24 Plus", new int[]{50, 12}); KAMERA_DB.put("Galaxy S24 Ultra", new int[]{200, 12}); KAMERA_DB.put("Galaxy S24 FE", new int[]{50, 10});
        KAMERA_DB.put("Galaxy Z Fold 5", new int[]{50, 10}); KAMERA_DB.put("Galaxy Z Flip 5", new int[]{12, 10});
        KAMERA_DB.put("Redmi Note 12", new int[]{50, 13}); KAMERA_DB.put("Redmi Note 12 Pro", new int[]{50, 16});
        KAMERA_DB.put("Redmi Note 13", new int[]{108, 16}); KAMERA_DB.put("Redmi Note 13 Pro", new int[]{200, 16}); KAMERA_DB.put("Redmi Note 13 Pro+", new int[]{200, 20});
        KAMERA_DB.put("Xiaomi 12", new int[]{50, 32}); KAMERA_DB.put("Xiaomi 12 Pro", new int[]{50, 32});
        KAMERA_DB.put("Xiaomi 13", new int[]{54, 32}); KAMERA_DB.put("Xiaomi 13 Pro", new int[]{50, 32}); KAMERA_DB.put("Xiaomi 13T", new int[]{50, 20}); KAMERA_DB.put("Xiaomi 13T Pro", new int[]{50, 20});
        KAMERA_DB.put("Xiaomi 14", new int[]{50, 32}); KAMERA_DB.put("Xiaomi 14 Ultra", new int[]{50, 32});
        KAMERA_DB.put("Reno 10", new int[]{64, 32}); KAMERA_DB.put("Reno 10 Pro", new int[]{50, 32}); KAMERA_DB.put("Reno 11", new int[]{50, 32}); KAMERA_DB.put("Reno 11 Pro", new int[]{50, 32});
        KAMERA_DB.put("Find X6 Pro", new int[]{50, 32}); KAMERA_DB.put("Find X7 Pro", new int[]{50, 32});
        KAMERA_DB.put("OnePlus 11", new int[]{50, 16}); KAMERA_DB.put("OnePlus 12", new int[]{50, 32}); KAMERA_DB.put("OnePlus Nord 3", new int[]{50, 16}); KAMERA_DB.put("OnePlus Nord CE4", new int[]{50, 16});
        KAMERA_DB.put("Pixel 7", new int[]{50, 10}); KAMERA_DB.put("Pixel 7 Pro", new int[]{50, 10}); KAMERA_DB.put("Pixel 7a", new int[]{64, 13});
        KAMERA_DB.put("Pixel 8", new int[]{50, 10}); KAMERA_DB.put("Pixel 8 Pro", new int[]{50, 10}); KAMERA_DB.put("Pixel 8a", new int[]{64, 13});
        KAMERA_DB.put("Pixel 9", new int[]{50, 10}); KAMERA_DB.put("Pixel 9 Pro", new int[]{50, 42});
        KAMERA_DB.put("Nova 11", new int[]{50, 60}); KAMERA_DB.put("Nova 12", new int[]{50, 60}); KAMERA_DB.put("P50 Pro", new int[]{50, 13}); KAMERA_DB.put("P60 Pro", new int[]{48, 13});
        KAMERA_DB.put("Mate 50 Pro", new int[]{50, 13}); KAMERA_DB.put("Mate 60 Pro", new int[]{50, 13});
        KAMERA_DB.put("Moto G84", new int[]{50, 16}); KAMERA_DB.put("Moto G Power", new int[]{50, 16});
        KAMERA_DB.put("Edge 40", new int[]{50, 32}); KAMERA_DB.put("Edge 40 Pro", new int[]{50, 60}); KAMERA_DB.put("Edge 50 Pro", new int[]{50, 50}); KAMERA_DB.put("Razr 40 Ultra", new int[]{12, 32});
    }

    private static final Map<String, Map<String, Double>> TELEFON_DB = new LinkedHashMap<>();
    static {
        Map<String, Double> apple = new LinkedHashMap<>();
        apple.put("iPhone 11", 9000.0); apple.put("iPhone 11 Pro", 12000.0);
        apple.put("iPhone 12", 13000.0); apple.put("iPhone 12 Pro", 16000.0); apple.put("iPhone 12 Pro Max", 19000.0);
        apple.put("iPhone 13", 22000.0); apple.put("iPhone 13 Mini", 18000.0); apple.put("iPhone 13 Pro", 29000.0); apple.put("iPhone 13 Pro Max", 33000.0);
        apple.put("iPhone 14", 30000.0); apple.put("iPhone 14 Plus", 33000.0); apple.put("iPhone 14 Pro", 38000.0); apple.put("iPhone 14 Pro Max", 44000.0);
        apple.put("iPhone 15", 36000.0); apple.put("iPhone 15 Plus", 40000.0); apple.put("iPhone 15 Pro", 48000.0); apple.put("iPhone 15 Pro Max", 56000.0);
        apple.put("iPhone 16", 46000.0); apple.put("iPhone 16 Plus", 52000.0); apple.put("iPhone 16 Pro", 60000.0); apple.put("iPhone 16 Pro Max", 70000.0);
        TELEFON_DB.put("Apple", apple);
        Map<String, Double> samsung = new LinkedHashMap<>();
        samsung.put("Galaxy A34", 6500.0); samsung.put("Galaxy A54", 8500.0); samsung.put("Galaxy A55", 10000.0); samsung.put("Galaxy A35", 7500.0);
        samsung.put("Galaxy S22", 14000.0); samsung.put("Galaxy S22 Plus", 17000.0); samsung.put("Galaxy S22 Ultra", 24000.0);
        samsung.put("Galaxy S23", 20000.0); samsung.put("Galaxy S23 Plus", 24000.0); samsung.put("Galaxy S23 Ultra", 36000.0); samsung.put("Galaxy S23 FE", 14000.0);
        samsung.put("Galaxy S24", 24000.0); samsung.put("Galaxy S24 Plus", 30000.0); samsung.put("Galaxy S24 Ultra", 46000.0); samsung.put("Galaxy S24 FE", 17000.0);
        samsung.put("Galaxy Z Fold 5", 60000.0); samsung.put("Galaxy Z Flip 5", 30000.0);
        TELEFON_DB.put("Samsung", samsung);
        Map<String, Double> xiaomi = new LinkedHashMap<>();
        xiaomi.put("Redmi Note 12", 5000.0); xiaomi.put("Redmi Note 12 Pro", 6500.0);
        xiaomi.put("Redmi Note 13", 6500.0); xiaomi.put("Redmi Note 13 Pro", 8500.0); xiaomi.put("Redmi Note 13 Pro+", 10000.0);
        xiaomi.put("Xiaomi 12", 11000.0); xiaomi.put("Xiaomi 12 Pro", 15000.0);
        xiaomi.put("Xiaomi 13", 16000.0); xiaomi.put("Xiaomi 13 Pro", 21000.0); xiaomi.put("Xiaomi 13T", 14000.0); xiaomi.put("Xiaomi 13T Pro", 18000.0);
        xiaomi.put("Xiaomi 14", 24000.0); xiaomi.put("Xiaomi 14 Ultra", 38000.0);
        TELEFON_DB.put("Xiaomi", xiaomi);
        Map<String, Double> oppo = new LinkedHashMap<>();
        oppo.put("Reno 10", 10000.0); oppo.put("Reno 10 Pro", 14000.0); oppo.put("Reno 11", 12000.0); oppo.put("Reno 11 Pro", 16000.0);
        oppo.put("Find X6 Pro", 30000.0); oppo.put("Find X7 Pro", 42000.0);
        TELEFON_DB.put("OPPO", oppo);
        Map<String, Double> oneplus = new LinkedHashMap<>();
        oneplus.put("OnePlus 11", 20000.0); oneplus.put("OnePlus 12", 30000.0); oneplus.put("OnePlus Nord 3", 11000.0); oneplus.put("OnePlus Nord CE4", 9500.0);
        TELEFON_DB.put("OnePlus", oneplus);
        Map<String, Double> google = new LinkedHashMap<>();
        google.put("Pixel 7", 18000.0); google.put("Pixel 7 Pro", 24000.0); google.put("Pixel 7a", 15000.0);
        google.put("Pixel 8", 28000.0); google.put("Pixel 8 Pro", 36000.0); google.put("Pixel 8a", 22000.0);
        google.put("Pixel 9", 36000.0); google.put("Pixel 9 Pro", 46000.0);
        TELEFON_DB.put("Google", google);
        Map<String, Double> huawei = new LinkedHashMap<>();
        huawei.put("Nova 11", 9000.0); huawei.put("Nova 12", 11000.0); huawei.put("P50 Pro", 18000.0); huawei.put("P60 Pro", 26000.0);
        huawei.put("Mate 50 Pro", 22000.0); huawei.put("Mate 60 Pro", 36000.0);
        TELEFON_DB.put("Huawei", huawei);
        Map<String, Double> motorola = new LinkedHashMap<>();
        motorola.put("Moto G84", 6000.0); motorola.put("Moto G Power", 5500.0); motorola.put("Edge 40", 10000.0);
        motorola.put("Edge 40 Pro", 14000.0); motorola.put("Edge 50 Pro", 18000.0); motorola.put("Razr 40 Ultra", 32000.0);
        TELEFON_DB.put("Motorola", motorola);
    }

    private static final String[] HASAR_LISTESI = {
        "Kozmetik Hasar (Çizik/Çatlak)", "Ön Cam Kırık", "Arka Cam Kırık",
        "Ekran Değişimi Gördü", "Batarya Değişimi Gördü", "Kamera Değişimi Gördü",
        "Anakart Tamiri Gördü", "Su Hasarı Var"
    };
    private static final double[] HASAR_KESINTILER = {0.08, 0.15, 0.10, 0.18, 0.05, 0.10, 0.28, 0.38};

    @FXML private ComboBox<String> marka1ComboBox, marka2ComboBox;
    @FXML private ComboBox<String> model1ComboBox, model2ComboBox;
    @FXML private ComboBox<Integer> depolama1ComboBox, depolama2ComboBox;
    @FXML private TextField yas1Field, yas2Field;
    @FXML private Slider pil1Slider, pil2Slider;
    @FXML private Label pil1Label, pil2Label;
    @FXML private Label arkaKamera1Label, arkaKamera2Label;
    @FXML private Label ekran1Label, ekran2Label;
    @FXML private CheckBox garanti1Check, garanti2Check;
    @FXML private ComboBox<String> garantiAy1ComboBox, garantiAy2ComboBox;
    @FXML private CheckBox kutuFatura1Check, kutuFatura2Check;
    @FXML private CheckBox imei1Check, imei2Check;
    @FXML private CheckBox besG1Check, besG2Check;
    @FXML private CheckBox hizliSarj1Check, hizliSarj2Check;
    @FXML private CheckComboBox<String> hasar1ComboBox, hasar2ComboBox;
    @FXML private Label baslik1Label, baslik2Label;
    @FXML private Label sonuc1Label, sonuc2Label;
    @FXML private Label aralik1Label, aralik2Label;
    @FXML private Label farkLabel, kazananLabel;

    @FXML
    public void initialize() {
        kurComboBoxlar(marka1ComboBox, model1ComboBox, depolama1ComboBox, garantiAy1ComboBox,
                       pil1Slider, pil1Label, arkaKamera1Label, ekran1Label, garanti1Check, hasar1ComboBox);
        kurComboBoxlar(marka2ComboBox, model2ComboBox, depolama2ComboBox, garantiAy2ComboBox,
                       pil2Slider, pil2Label, arkaKamera2Label, ekran2Label, garanti2Check, hasar2ComboBox);
    }

    private void kurComboBoxlar(ComboBox<String> markaBox, ComboBox<String> modelBox,
                                 ComboBox<Integer> depolamaBox, ComboBox<String> garantiAyBox,
                                 Slider pilSlider, Label pilLabel,
                                 Label arkaKameraLbl, Label ekranLbl,
                                 CheckBox garantiChk, CheckComboBox<String> hasarBox) {
        markaBox.getItems().add("Marka Seçiniz");
        markaBox.getItems().addAll(TELEFON_DB.keySet());
        markaBox.getSelectionModel().select(0);

        modelBox.getItems().add("Model Seçiniz");
        modelBox.getSelectionModel().select(0);
        modelBox.setDisable(true);

        depolamaBox.getItems().addAll(64, 128, 256, 512, 1024);
        depolamaBox.getSelectionModel().select(1);

        pilSlider.setMin(50); pilSlider.setMax(100); pilSlider.setValue(90);
        pilSlider.setMajorTickUnit(10); pilSlider.setShowTickLabels(true); pilSlider.setShowTickMarks(true);
        pilLabel.setText("90%");
        pilSlider.valueProperty().addListener((obs, o, n) -> pilLabel.setText(n.intValue() + "%"));

        garantiAyBox.getItems().addAll("1-3 ay kaldı", "4-6 ay kaldı", "7-12 ay kaldı", "13-18 ay kaldı", "19-24 ay kaldı", "24+ ay kaldı");
        garantiAyBox.setDisable(true);
        garantiChk.selectedProperty().addListener((obs, o, n) -> {
            garantiAyBox.setDisable(!n);
            if (!n) garantiAyBox.getSelectionModel().clearSelection();
        });

        hasarBox.getItems().addAll(HASAR_LISTESI);

        markaBox.setOnAction(e -> {
            String m = markaBox.getValue();
            if (m != null && !m.equals("Marka Seçiniz")) {
                modelBox.getItems().clear();
                modelBox.getItems().add("Model Seçiniz");
                modelBox.getItems().addAll(TELEFON_DB.get(m).keySet());
                modelBox.getSelectionModel().select(0);
                modelBox.setDisable(false);
                arkaKameraLbl.setText("—"); ekranLbl.setText("—");

                modelBox.setOnAction(me -> {
                    String model = modelBox.getValue();
                    if (model != null && !model.equals("Model Seçiniz")) {
                        if (KAMERA_DB.containsKey(model))
                            arkaKameraLbl.setText(KAMERA_DB.get(model)[0] + " MP");
                        ekranLbl.setText(EKRAN_DB.containsKey(model) ? EKRAN_DB.get(model) + " inç" : "—");
                    } else { arkaKameraLbl.setText("—"); ekranLbl.setText("—"); }
                });
            } else {
                modelBox.getItems().clear(); modelBox.getItems().add("Model Seçiniz");
                modelBox.getSelectionModel().select(0); modelBox.setDisable(true);
                arkaKameraLbl.setText("—"); ekranLbl.setText("—");
            }
        });
    }

    @FXML
    protected void onKarsilastirButtonClick() {
        try {
            double sonuc1 = hesapla(marka1ComboBox, model1ComboBox, depolama1ComboBox, yas1Field,
                    pil1Slider, garanti1Check, garantiAy1ComboBox, kutuFatura1Check, imei1Check,
                    besG1Check, hizliSarj1Check, hasar1ComboBox, arkaKamera1Label);
            double sonuc2 = hesapla(marka2ComboBox, model2ComboBox, depolama2ComboBox, yas2Field,
                    pil2Slider, garanti2Check, garantiAy2ComboBox, kutuFatura2Check, imei2Check,
                    besG2Check, hizliSarj2Check, hasar2ComboBox, arkaKamera2Label);

            String ad1 = marka1ComboBox.getValue() + " " + model1ComboBox.getValue();
            String ad2 = marka2ComboBox.getValue() + " " + model2ComboBox.getValue();

            baslik1Label.setText(ad1);
            baslik2Label.setText(ad2);
            sonuc1Label.setText(String.format("%,.0f ₺", sonuc1));
            sonuc2Label.setText(String.format("%,.0f ₺", sonuc2));

            double alt1 = Math.round(sonuc1 * 0.90 / 500.0) * 500.0;
            double ust1 = Math.round(sonuc1 * 1.10 / 500.0) * 500.0;
            double alt2 = Math.round(sonuc2 * 0.90 / 500.0) * 500.0;
            double ust2 = Math.round(sonuc2 * 1.10 / 500.0) * 500.0;
            aralik1Label.setText(String.format("%,.0f ₺ — %,.0f ₺", alt1, ust1));
            aralik2Label.setText(String.format("%,.0f ₺ — %,.0f ₺", alt2, ust2));

            double fark = Math.abs(sonuc1 - sonuc2);
            farkLabel.setText(String.format("%,.0f ₺ fark", fark));

            if (sonuc1 > sonuc2) {
                kazananLabel.setText("📱 " + ad1 + " daha değerli");
                kazananLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #2980b9;");
            } else if (sonuc2 > sonuc1) {
                kazananLabel.setText("📱 " + ad2 + " daha değerli");
                kazananLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #e67e22;");
            } else {
                kazananLabel.setText("İki telefon eşit değerde");
                kazananLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");
            }

        } catch (NumberFormatException e) {
            kazananLabel.setText("Hata: Cihaz yaşlarını girin!");
            kazananLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            kazananLabel.setText("Hata: Tüm alanları doldurun!");
            kazananLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private double hesapla(ComboBox<String> markaBox, ComboBox<String> modelBox,
                            ComboBox<Integer> depolamaBox, TextField yasField,
                            Slider pilSlider, CheckBox garantiChk, ComboBox<String> garantiAyBox,
                            CheckBox kutuChk, CheckBox imeiChk, CheckBox besGChk, CheckBox hizliChk,
                            CheckComboBox<String> hasarBox, Label arkaKameraLbl) {
        String marka = markaBox.getValue();
        String model = modelBox.getValue();
        int depolama = depolamaBox.getValue();
        int yas = Integer.parseInt(yasField.getText().trim());
        int pil = (int) pilSlider.getValue();

        int kalanGaranti = 0;
        if (garantiChk.isSelected() && garantiAyBox.getValue() != null) {
            String g = garantiAyBox.getValue();
            if      (g.startsWith("1-3"))  kalanGaranti = 2;
            else if (g.startsWith("4-6"))  kalanGaranti = 5;
            else if (g.startsWith("7-12")) kalanGaranti = 10;
            else if (g.startsWith("13-18"))kalanGaranti = 15;
            else if (g.startsWith("19-24"))kalanGaranti = 21;
            else if (g.startsWith("24+"))  kalanGaranti = 27;
        }

        ObservableList<String> secilenHasarlar = hasarBox.getCheckModel().getCheckedItems();
        boolean kozmetik = secilenHasarlar.stream().anyMatch(s -> s.contains("Kozmetik") || s.contains("Cam") || s.contains("Ekran"));
        boolean tamir = secilenHasarlar.stream().anyMatch(s -> s.contains("Değişimi") || s.contains("Tamiri") || s.contains("Su"));
        double hasarKesinti = 0.0;
        for (String h : secilenHasarlar)
            for (int i = 0; i < HASAR_LISTESI.length; i++)
                if (h.equals(HASAR_LISTESI[i])) { hasarKesinti += HASAR_KESINTILER[i]; break; }

        int arkaMP = parseMP(arkaKameraLbl.getText());
        int onMP = 12;
        if (KAMERA_DB.containsKey(model)) onMP = KAMERA_DB.get(model)[1];

        Phone telefon = new Phone(marka, model, depolama, pil, yas,
                garantiChk.isSelected(), kutuChk.isSelected(), kozmetik, tamir,
                imeiChk.isSelected(), EKRAN_DB.getOrDefault(model, 6.1), arkaMP, onMP);

        double tabanFiyat = TELEFON_DB.get(marka).get(model);
        return new PhonePricingEngine().tahminiDegerHesapla(telefon, tabanFiyat, hasarKesinti,
                besGChk.isSelected(), hizliChk.isSelected(), arkaMP, onMP, kalanGaranti);
    }

    private int parseMP(String text) {
        try { return Integer.parseInt(text.replace(" MP", "").trim()); }
        catch (Exception e) { return 12; }
    }

    @FXML
    protected void onSifirlaButtonClick() {
        for (ComboBox<String> cb : List.of(marka1ComboBox, marka2ComboBox)) cb.getSelectionModel().select(0);
        for (ComboBox<String> cb : List.of(model1ComboBox, model2ComboBox)) { cb.getItems().clear(); cb.getItems().add("Model Seçiniz"); cb.getSelectionModel().select(0); cb.setDisable(true); }
        for (ComboBox<Integer> cb : List.of(depolama1ComboBox, depolama2ComboBox)) cb.getSelectionModel().select(1);
        for (TextField tf : List.of(yas1Field, yas2Field)) tf.clear();
        pil1Slider.setValue(90); pil2Slider.setValue(90);
        pil1Label.setText("90%"); pil2Label.setText("90%");
        arkaKamera1Label.setText("—"); arkaKamera2Label.setText("—");
        ekran1Label.setText("—"); ekran2Label.setText("—");
        for (CheckBox cb : List.of(garanti1Check, garanti2Check, kutuFatura1Check, kutuFatura2Check, besG1Check, besG2Check, hizliSarj1Check, hizliSarj2Check)) cb.setSelected(false);
        imei1Check.setSelected(true); imei2Check.setSelected(true);
        garantiAy1ComboBox.getSelectionModel().clearSelection(); garantiAy1ComboBox.setDisable(true);
        garantiAy2ComboBox.getSelectionModel().clearSelection(); garantiAy2ComboBox.setDisable(true);
        hasar1ComboBox.getCheckModel().clearChecks(); hasar2ComboBox.getCheckModel().clearChecks();
        sonuc1Label.setText("—"); sonuc2Label.setText("—");
        aralik1Label.setText("—"); aralik2Label.setText("—");
        farkLabel.setText("—"); kazananLabel.setText("—");
        baslik1Label.setText("Telefon 1"); baslik2Label.setText("Telefon 2");
    }
}
