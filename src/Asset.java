public abstract class Asset {
    private String id;
    private double tahminiDeger;

    public Asset(String id) {
        this.id = id;
    }

    public String getId() { return id; }
    public double getTahminiDeger() { return tahminiDeger; }
    public void setTahminiDeger(double tahminiDeger) { this.tahminiDeger = tahminiDeger; }

    public abstract void tahminiDegerHesapla();
}