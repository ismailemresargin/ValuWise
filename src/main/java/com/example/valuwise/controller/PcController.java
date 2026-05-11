package com.example.valuwise.controller;

import com.example.valuwise.model.Pc;
import com.example.valuwise.util.PcVerileri;
import com.example.valuwise.service.pricing.PcPricingEngine;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PcController {
    @FXML private ComboBox<String> pcCihazTipiCombo, pcMarkaCombo, pcModelAilesiCombo;
    @FXML private ComboBox<String> pcIslemciMarkasiCombo, pcIslemciAilesiCombo, pcIslemciNesliCombo;
    @FXML private ComboBox<String> pcRamKapasitesiCombo, pcRamTipiCombo, pcDepolamaKapasitesiCombo, pcDepolamaTipiCombo;
    @FXML private ComboBox<String> pcEkranKartiCombo, pcEkranBoyutuCombo, pcCihazYasiCombo;
    @FXML private Slider pcBataryaSagligiSlider;
    @FXML private Label pcBataryaSagligiLabel, pcResultLabel, errorLabel;
    @FXML private CheckBox pcKozmetikHasarCheck, pcGarantiCheck;

    @FXML
    public void initialize() {
        // Temel Kategoriler
        pcCihazTipiCombo.getItems().addAll("Seçiniz", "Masaüstü", "Dizüstü");
        pcCihazTipiCombo.getSelectionModel().select(0);

        // Veri Sınıfından Yüklemeler
        pcMarkaCombo.getItems().add("Marka Seçiniz");
        pcMarkaCombo.getItems().addAll(PcVerileri.getMarkalar());
        pcMarkaCombo.getSelectionModel().select(0);

        pcIslemciMarkasiCombo.getItems().add("Üretici Seçiniz");
        pcIslemciMarkasiCombo.getItems().addAll(PcVerileri.getCpuMarkalar());
        pcIslemciMarkasiCombo.getSelectionModel().select(0);

        pcEkranKartiCombo.getItems().addAll(PcVerileri.getEkranKartlari());

        // Diğer Sabitler
        setupConstantCombos();

        // --- Dinamik Dinleyiciler (Genele Yayılmış Mantık) ---

        // Marka -> Model Bağımlılığı
        pcMarkaCombo.setOnAction(e -> {
            String marka = pcMarkaCombo.getValue();
            pcModelAilesiCombo.getItems().clear();
            pcModelAilesiCombo.getItems().add("Model Seçiniz");
            if (marka != null && !marka.equals("Marka Seçiniz")) {
                pcModelAilesiCombo.getItems().addAll(PcVerileri.getModeller(marka));
                pcModelAilesiCombo.setDisable(false);
            } else {
                pcModelAilesiCombo.setDisable(true);
            }
            pcModelAilesiCombo.getSelectionModel().select(0);
        });

        // İşlemci Marka -> Aile VE NESİL Bağımlılığı
        pcIslemciMarkasiCombo.setOnAction(e -> {
            String cpuMarka = pcIslemciMarkasiCombo.getValue();

            // Aile kutusunu sıfırla
            pcIslemciAilesiCombo.getItems().clear();
            pcIslemciAilesiCombo.getItems().add("Aile Seçiniz");

            // Nesil kutusunu sıfırla
            pcIslemciNesliCombo.getItems().clear();
            pcIslemciNesliCombo.getItems().add("Nesil/Seri Seçiniz");

            if (cpuMarka != null && !cpuMarka.equals("Üretici Seçiniz")) {
                // Aileleri Doldur
                pcIslemciAilesiCombo.getItems().addAll(PcVerileri.getCpuAileleri(cpuMarka));
                pcIslemciAilesiCombo.setDisable(false);

                // Nesilleri Doldur (Intel ise Nesil, AMD ise Seri gelecek)
                pcIslemciNesliCombo.getItems().addAll(PcVerileri.getCpuNesilleri(cpuMarka));
                pcIslemciNesliCombo.setDisable(false);

                // Apple seçildiyse nesli otomatik doldur ve kilitle
                if (cpuMarka.equals("Apple")) {
                    pcIslemciNesliCombo.getSelectionModel().select(1); // "Apple Silicon"u seçer
                    pcIslemciNesliCombo.setDisable(true);
                }
            } else {
                pcIslemciAilesiCombo.setDisable(true);
                pcIslemciNesliCombo.setDisable(true);
            }
            pcIslemciAilesiCombo.getSelectionModel().select(0);
            pcIslemciNesliCombo.getSelectionModel().select(0);
        });

        // Masaüstü Seçilirse Batarya/Ekran Kilitleme (Mini PC mantığı burada çözülüyor)
        pcCihazTipiCombo.setOnAction(e -> {
            boolean isDesktop = "Masaüstü".equals(pcCihazTipiCombo.getValue());
            pcBataryaSagligiSlider.setDisable(isDesktop);
            pcEkranBoyutuCombo.setDisable(isDesktop);
            if (isDesktop) {
                pcBataryaSagligiSlider.setValue(100);
                pcEkranBoyutuCombo.getSelectionModel().select("Yok (Kasa)");
            }
        });

        pcBataryaSagligiSlider.valueProperty().addListener((obs, old, newVal) ->
                pcBataryaSagligiLabel.setText(newVal.intValue() + "%"));
    }

    private void setupConstantCombos() {
        // Ara RAM değerleri ve yüksek kapasiteler eklendi (DDR5 ile gelen 24/48 vb.)
        pcRamKapasitesiCombo.getItems().addAll("4", "8", "12", "16", "24", "32", "48", "64", "96", "128", "192", "256");
        pcRamTipiCombo.getItems().addAll("DDR4", "DDR5", "LPDDR5 (Lehimli)");

        // 4TB ve 8TB depolama eklendi
        pcDepolamaKapasitesiCombo.getItems().addAll("128", "256", "512", "1024 (1TB)", "2048 (2TB)", "4096 (4TB)", "8192 (8TB+)");
        pcDepolamaTipiCombo.getItems().addAll("Gen4/Gen5 NVMe SSD", "Standart NVMe SSD", "SATA SSD", "HDD (Mekanik)");

        pcEkranBoyutuCombo.getItems().addAll("Yok (Kasa)", "13.3", "14.0", "15.6", "16.1", "17.3", "18.0");
        pcCihazYasiCombo.getItems().addAll("0", "1", "2", "3", "4", "5+");
    }

    @FXML
    protected void onCalculateButtonClick() {
        errorLabel.setVisible(false);
        pcResultLabel.setStyle("-fx-text-fill: #8e44ad;"); // Varsayılan mor renk

        // Stil sıfırlama (Her hesaplamada çerçeveleri temizle)
        ComboBox[] tumCombolar = {
                pcCihazTipiCombo, pcMarkaCombo, pcModelAilesiCombo, pcIslemciMarkasiCombo,
                pcIslemciAilesiCombo, pcIslemciNesliCombo, pcRamKapasitesiCombo, pcRamTipiCombo,
                pcDepolamaKapasitesiCombo, pcDepolamaTipiCombo, pcEkranKartiCombo, pcEkranBoyutuCombo, pcCihazYasiCombo
        };
        for (ComboBox c : tumCombolar) c.setStyle("");

        try {
            boolean bosKutuVar = false;

            // Zorunlu alan kontrolü (isInvalid yardımcı metodunu kullanarak)
            for (ComboBox c : tumCombolar) {
                if (isInvalid(c)) {
                    c.setStyle("-fx-border-color: #d9a400; -fx-border-width: 2px; -fx-border-radius: 3px;");
                    bosKutuVar = true;
                }
            }

            if (bosKutuVar) {
                throw new IllegalArgumentException("Eksik veri girişi yapıldı!");
            }

            // Veri Dönüştürme (Parse işlemleri)
            int ramKapasite = Integer.parseInt(pcRamKapasitesiCombo.getValue());

            // Depolama: "1024 (1TB)" gibi metinlerden sayısal kısmı al
            String depolamaRaw = pcDepolamaKapasitesiCombo.getValue().split(" ")[0];
            int depolamaKapasite = Integer.parseInt(depolamaRaw);

            // Yaş: "5+" gibi metinlerden sayıyı al
            int cihazYasi = Integer.parseInt(pcCihazYasiCombo.getValue().replace("+", ""));

            // Nesil: "13. Nesil" -> 13 (Apple ise 0)
            int nesil = 0;
            String nesilStr = pcIslemciNesliCombo.getValue();
            if (nesilStr.matches(".*\\d.*")) { // İçinde sayı varsa
                nesil = Integer.parseInt(nesilStr.replaceAll("[^0-9]", ""));
            }

            // Ekran Boyutu: "15.6 inç" -> 15.6
            double ekranBoyutu = 0.0;
            if (!pcEkranBoyutuCombo.isDisable() && !pcEkranBoyutuCombo.getValue().equals("Yok (Kasa)")) {
                ekranBoyutu = Double.parseDouble(pcEkranBoyutuCombo.getValue().split(" ")[0]);
            }

            // Pc Nesnesini Oluştur
            Pc yeniPc = new Pc(
                    "PC-" + System.currentTimeMillis(),
                    pcCihazTipiCombo.getValue(),
                    pcMarkaCombo.getValue(),
                    pcModelAilesiCombo.getValue(),
                    pcIslemciMarkasiCombo.getValue(),
                    pcIslemciAilesiCombo.getValue(),
                    nesil,
                    ramKapasite,
                    pcRamTipiCombo.getValue(),
                    pcEkranKartiCombo.getValue(),
                    pcDepolamaTipiCombo.getValue(),
                    depolamaKapasite,
                    ekranBoyutu,
                    cihazYasi,
                    (int) pcBataryaSagligiSlider.getValue(),
                    pcKozmetikHasarCheck.isSelected(),
                    pcGarantiCheck.isSelected()
            );

            // Algoritmayı Çalıştır
            double sonuc = new PcPricingEngine().tahminiDegerHesapla(yeniPc);
            pcResultLabel.setText(String.format("Tahmini Değer: %,.2f ₺", sonuc));

        } catch (IllegalArgumentException e) {
            pcResultLabel.setText("Hata: Verileri eksiksiz girin!");
            pcResultLabel.setStyle("-fx-text-fill: #d9a400;"); // Sarı uyarı rengi
        } catch (Exception e) {
            pcResultLabel.setText("Hesaplama Hatası!");
            pcResultLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setVisible(true);
            e.printStackTrace();
        }
    }

    @FXML
    protected void onClearButtonClick() {
        // Tüm ComboBox'ları "Seçiniz" durumuna getir
        pcCihazTipiCombo.getSelectionModel().select(0);
        pcCihazYasiCombo.getSelectionModel().select(0);
        pcEkranKartiCombo.getSelectionModel().select(0);
        pcRamKapasitesiCombo.getSelectionModel().select(0);
        pcRamTipiCombo.getSelectionModel().select(0);
        pcDepolamaKapasitesiCombo.getSelectionModel().select(0);
        pcDepolamaTipiCombo.getSelectionModel().select(0);
        pcEkranBoyutuCombo.getSelectionModel().select(0);

        // Bağımlı ComboBox'ları sıfırla ve kilitle
        pcMarkaCombo.getSelectionModel().select(0);
        pcModelAilesiCombo.getItems().clear();
        pcModelAilesiCombo.getItems().add("Model Seçiniz");
        pcModelAilesiCombo.getSelectionModel().select(0);
        pcModelAilesiCombo.setDisable(true);

        pcIslemciMarkasiCombo.getSelectionModel().select(0);
        pcIslemciAilesiCombo.getItems().clear();
        pcIslemciAilesiCombo.getItems().add("Aile Seçiniz");
        pcIslemciAilesiCombo.getSelectionModel().select(0);
        pcIslemciAilesiCombo.setDisable(true);

        pcIslemciNesliCombo.getItems().clear();
        pcIslemciNesliCombo.getItems().add("Nesil/Seri Seçiniz");
        pcIslemciNesliCombo.getSelectionModel().select(0);
        pcIslemciNesliCombo.setDisable(true);

        // Checkbox ve Slider sıfırla
        pcKozmetikHasarCheck.setSelected(false);
        pcGarantiCheck.setSelected(false);
        pcBataryaSagligiSlider.setValue(90);
        pcBataryaSagligiSlider.setDisable(false);
        pcEkranBoyutuCombo.setDisable(false);
        pcBataryaSagligiLabel.setText("90%");

        // Stil ve Yazıları Sıfırla
        ComboBox[] tumCombolar = {
                pcCihazTipiCombo, pcMarkaCombo, pcModelAilesiCombo, pcIslemciMarkasiCombo,
                pcIslemciAilesiCombo, pcIslemciNesliCombo, pcRamKapasitesiCombo, pcRamTipiCombo,
                pcDepolamaKapasitesiCombo, pcDepolamaTipiCombo, pcEkranKartiCombo, pcEkranBoyutuCombo, pcCihazYasiCombo
        };
        for (ComboBox c : tumCombolar) c.setStyle("");

        pcResultLabel.setText("Tahmini Değer: -- ₺");
        pcResultLabel.setStyle("-fx-text-fill: #8e44ad;");
        errorLabel.setVisible(false);

        // Odaklanma
        pcCihazTipiCombo.requestFocus();
    }

    private boolean isInvalid(ComboBox<String> combo) {
        if (combo.isDisable()) return false; // Kilitli kutuları kontrol etme (Örn: Masaüstü seçilince ekran boyutu)
        String v = combo.getValue();
        return v == null || v.contains("Seçiniz") || v.contains("Üretici");
    }
}