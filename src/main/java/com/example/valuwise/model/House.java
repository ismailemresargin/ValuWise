package com.example.valuwise.model;

public class House extends Asset {
    private double metrekare;
    private int odaSayisi;
    private int banyoSayisi;
    private int binaYasi;
    private int bulunduguKat;
    private boolean asansorVarMi;
    private boolean otoparkVarMi;
    private boolean denizManzarasiVarMi; //fiyati   etkiler
    private boolean depremeDayanikliMi;

    // Constructor: id parametresini ekledik ve super(id) ile üst sınıfa gönderdik
    public House(String id, double metrekare, int odaSayisi, int banyoSayisi, int binaYasi, int bulunduguKat,
              boolean asansorVarMi, boolean otoparkVarMi,
              boolean denizManzarasiVarMi, boolean depremeDayanikliMi) {
        super(id);
        this.metrekare = metrekare;
        this.odaSayisi = odaSayisi;
        this.banyoSayisi = banyoSayisi;
        this.binaYasi = binaYasi;
        this.bulunduguKat = bulunduguKat;
        this.asansorVarMi = asansorVarMi;
        this.otoparkVarMi = otoparkVarMi;
        this.denizManzarasiVarMi = denizManzarasiVarMi;
        this.depremeDayanikliMi = depremeDayanikliMi;
    }

    public double getMetrekare() {
        return metrekare;
    }

    public void setMetrekare(double metrekare) {
        this.metrekare = metrekare;
    }

    public int getOdaSayisi() {
        return odaSayisi;
    }

    public void setOdaSayisi(int odaSayisi) {
        this.odaSayisi = odaSayisi;
    }

    public int getBanyoSayisi() {
        return banyoSayisi;
    }

    public void setBanyoSayisi(int banyoSayisi) {
        this.banyoSayisi = banyoSayisi;
    }

    public int getBinaYasi() {
        return binaYasi;
    }

    public void setBinaYasi(int binaYasi) {
        this.binaYasi = binaYasi;
    }

    public int getBulunduguKat() {
        return bulunduguKat;
    }

    public void setBulunduguKat(int bulunduguKat) {
        this.bulunduguKat = bulunduguKat;
    }

    public boolean isAsansorVarMi() {
        return asansorVarMi;
    }

    public void setAsansorVarMi(boolean asansorVarMi) {
        this.asansorVarMi = asansorVarMi;
    }

    public boolean isOtoparkVarMi() {
        return otoparkVarMi;
    }

    public void setOtoparkVarMi(boolean otoparkVarMi) {
        this.otoparkVarMi = otoparkVarMi;
    }

    public boolean isDenizManzarasiVarMi() {
        return denizManzarasiVarMi;
    }

    public void setDenizManzarasiVarMi(boolean denizManzarasiVarMi) {
        this.denizManzarasiVarMi = denizManzarasiVarMi;
    }

    public boolean isDepremeDayanikliMi() {
        return depremeDayanikliMi;
    }

    public void setDepremeDayanikliMi(boolean depremeDayanikliMi) {
        this.depremeDayanikliMi = depremeDayanikliMi;
    }

    //Gerçek hesaplama, servis katmanında (HousePricingEngine) yapılacağı için burayı boş bırakıyoruz.
    @Override
    public void tahminiDegerHesapla() {
        // Hesaplama sorumluluğu artık HousePricingEngine'de.
    }

}
