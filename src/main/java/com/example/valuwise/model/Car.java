package com.example.valuwise.model;

public class Car extends Asset {
    private String marka;
    private String model;
    private int modelYili;
    private String yakitTipi;
    private int kilometre;
    private String vitesTipi;
    private double tramerKaydi;
    private int degisenParcaSayisi;

    public Car(String id, String marka, String model, int modelYili,
               String yakitTipi, int kilometre, String vitesTipi,
               double tramerKaydi, int degisenParcaSayisi) {
        super(id);
        this.marka = marka;
        this.model = model;
        this.modelYili = modelYili;
        this.yakitTipi = yakitTipi;
        this.kilometre = kilometre;
        this.vitesTipi = vitesTipi;
        this.tramerKaydi = tramerKaydi;
        this.degisenParcaSayisi = degisenParcaSayisi;
    }

    public String getMarka() { return marka; }
    public String getModel() { return model; }
    public int getModelYili() { return modelYili; }
    public String getYakitTipi() { return yakitTipi; }
    public int getKilometre() { return kilometre; }
    public String getVitesTipi() { return vitesTipi; }
    public double getTramerKaydi() { return tramerKaydi; }
    public int getDegisenParcaSayisi() { return degisenParcaSayisi; }

    @Override
    public void tahminiDegerHesapla() {
        // Hesaplama sorumluluğu CarPricingEngine'de.
    }
}
