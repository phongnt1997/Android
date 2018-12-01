package vn.phongandfriends.motorwashing.model;

public class Combo {
    private String combo;
    private String price;
    private Double basePrice;

    public Combo() {
    }

    public Combo(String combo, String price) {
        this.combo = combo;
        this.price = price;
    }

    public String getCombo() {
        return combo;
    }

    public void setCombo(String combo) {
        this.combo = combo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public Combo setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
        return this;
    }
}