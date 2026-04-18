package com.example.valuwise.model;

public class Phone extends Asset {
    private String marka;
    private String model;
    private int depolamaKapasitesi;
    private int pilSagligi;
    private int cihazYasiAy;
    private boolean garantisiVarMi;
    private boolean kutuFaturaVarMi;
    private boolean kozmetikHasarVarMi;
    private boolean tamirGorduMu;
    private boolean imeiKaydiVarMi;
    private double ekranBoyutuInc;
    private int arkaKameraMP;
    private int onKameraMP;

    // Constructor: Asset'ten gelen ID eklendi
    public Phone(String id, String marka, String model, int depolamaKapasitesi, int pilSagligi, int cihazYasiAy,
                 boolean garantisiVarMi, boolean kutuFaturaVarMi,
                 boolean kozmetikHasarVarMi, boolean tamirGorduMu, boolean imeiKaydiVarMi,
                 double ekranBoyutuInc, int arkaKameraMP, int onKameraMP) {
        super(id);
        this.marka = marka;
        this.model = model;
        this.depolamaKapasitesi = depolamaKapasitesi;
        this.pilSagligi = pilSagligi;
        this.cihazYasiAy = cihazYasiAy;
        this.garantisiVarMi = garantisiVarMi;
        this.kutuFaturaVarMi = kutuFaturaVarMi;
        this.kozmetikHasarVarMi = kozmetikHasarVarMi;
        this.tamirGorduMu = tamirGorduMu;
        this.imeiKaydiVarMi = imeiKaydiVarMi;
        this.ekranBoyutuInc = ekranBoyutuInc;
        this.arkaKameraMP = arkaKameraMP;
        this.onKameraMP = onKameraMP;
    }

    // --- Getter ve Setter Metotları ---
    public String getMarka() { return marka; }
    public String getModel() { return model; }
    public int getDepolamaKapasitesi() { return depolamaKapasitesi; }
    public int getPilSagligi() { return pilSagligi; }
    public int getCihazYasiAy() { return cihazYasiAy; }
    public boolean isGarantisiVarMi() { return garantisiVarMi; }
    public boolean isKutuFaturaVarMi() { return kutuFaturaVarMi; }
    public boolean isKozmetikHasarVarMi() { return kozmetikHasarVarMi; }
    public boolean isTamirGorduMu() { return tamirGorduMu; }
    public boolean isImeiKaydiVarMi() { return imeiKaydiVarMi; }

    @Override
    public void tahminiDegerHesapla() {
        // Hesaplama sorumluluğu PhonePricingEngine'de.
    }
}