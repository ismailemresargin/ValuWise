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

    public Phone(String marka, String model, int depolamaKapasitesi, int pilSagligi, int cihazYasiAy,
                 boolean garantisiVarMi, boolean kutuFaturaVarMi,
                 boolean kozmetikHasarVarMi, boolean tamirGorduMu, boolean imeiKaydiVarMi,
                 double ekranBoyutuInc, int arkaKameraMP, int onKameraMP) {
        super("ID-" + System.currentTimeMillis());
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

    @Override
    public void tahminiDegerHesapla() {
        // Hesaplama sorumluluğu PhonePricingEngine'de.
    }

    public boolean isImeiKaydiVarMi() { return imeiKaydiVarMi; }
    public void setImeiKaydiVarMi(boolean imeiKaydiVarMi) { this.imeiKaydiVarMi = imeiKaydiVarMi; }
    public String getMarka() { return marka; }
    public void setMarka(String marka) { this.marka = marka; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getDepolamaKapasitesi() { return depolamaKapasitesi; }
    public void setDepolamaKapasitesi(int depolamaKapasitesi) { this.depolamaKapasitesi = depolamaKapasitesi; }
    public int getPilSagligi() { return pilSagligi; }
    public void setPilSagligi(int pilSagligi) { this.pilSagligi = pilSagligi; }
    public int getCihazYasiAy() { return cihazYasiAy; }
    public void setCihazYasiAy(int cihazYasiAy) { this.cihazYasiAy = cihazYasiAy; }
    public boolean isGarantisiVarMi() { return garantisiVarMi; }
    public void setGarantisiVarMi(boolean garantisiVarMi) { this.garantisiVarMi = garantisiVarMi; }
    public boolean isKutuFaturaVarMi() { return kutuFaturaVarMi; }
    public void setKutuFaturaVarMi(boolean kutuFaturaVarMi) { this.kutuFaturaVarMi = kutuFaturaVarMi; }
    public boolean isKozmetikHasarVarMi() { return kozmetikHasarVarMi; }
    public void setKozmetikHasarVarMi(boolean kozmetikHasarVarMi) { this.kozmetikHasarVarMi = kozmetikHasarVarMi; }
    public boolean isTamirGorduMu() { return tamirGorduMu; }
    public void setTamirGorduMu(boolean tamirGorduMu) { this.tamirGorduMu = tamirGorduMu; }
    public double getEkranBoyutuInc() { return ekranBoyutuInc; }
    public void setEkranBoyutuInc(double ekranBoyutuInc) { this.ekranBoyutuInc = ekranBoyutuInc; }
    public int getArkaKameraMP() { return arkaKameraMP; }
    public void setArkaKameraMP(int arkaKameraMP) { this.arkaKameraMP = arkaKameraMP; }
    public int getOnKameraMP() { return onKameraMP; }
    public void setOnKameraMP(int onKameraMP) { this.onKameraMP = onKameraMP; }
}