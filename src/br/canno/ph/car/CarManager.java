package br.canno.ph.car;

import java.util.Random;

public class CarManager {

    public CarManager() {
    }

    public String generatePlate() {
        Random rnd = new Random();

        return "" + ((char) ('a' + rnd.nextInt(26))) + ((char) ('a' + rnd.nextInt(26))) + ((char) ('a' + rnd.nextInt(26))) + ((char) ('a' + rnd.nextInt(26)));
    }

}
