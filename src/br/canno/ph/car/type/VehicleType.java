package br.canno.ph.car.type;

public enum VehicleType {

    NONE(0, "-fx-background-color: #33ff7c;-fx-background-radius: 0;-fx-border-color: #8abc91;"),
    CAMIONETTE(30, "-fx-background-color: #a6dced;-fx-background-radius: 0;-fx-border-color: #6fbbd3;"),
    VOITURE(20, "-fx-background-color: #800000;-fx-background-radius: 0;-fx-border-color: #421f14;"),
    MOTO(10, "-fx-background-color: #472e58;-fx-background-radius: 0;-fx-border-color: #2e1e38;");

    public int price;
    public String color;

    private VehicleType(int price, String color) {
        this.price = price;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public int getPrice() {
        return price;
    }

    public static VehicleType getRandom() {

        VehicleType randomValue = values()[(int) (Math.random() * values().length)];

        do {
            randomValue = values()[(int) (Math.random() * values().length)];
        } while (randomValue == NONE);

        return randomValue;
    }

}
