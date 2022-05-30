package br.canno.ph.parking;

import br.canno.ph.car.CarManager;
import br.canno.ph.car.type.Vehicle;
import br.canno.ph.car.type.VehicleType;
import br.canno.ph.manager.MainManager;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class ParkingManager {

    public static int maxParked = 20;
    public static int parkedVehicles = 0;

    public CarManager carManager = new CarManager();

    public ParkingManager() {
    }

    public void onCarPark() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Attention!");
        alert.setHeaderText("A car has pulled into the parking lot!");
        alert.setContentText("A car has now parked. The charge has already been made immediately and you can check it by clicking on the occupied space.");

        alert.showAndWait();

        for (Map.Entry<Button, Vehicle> entry : MainManager.vehiclesList.entrySet()) {
            if (entry.getValue().getVehicleType() == VehicleType.NONE) {
                Vehicle vehicle = entry.getValue();

                vehicle.setLicensePlate(carManager.generatePlate());
                vehicle.setVehicleType(VehicleType.getRandom());
                vehicle.setDate(new Date(ThreadLocalRandom.current().nextInt() * 1000L));

                entry.getKey().setText(entry.getKey().getId() + "\n" + carManager.generatePlate());
                entry.getKey().setStyle(vehicle.getVehicleType().getColor());

                break;
            }
        }

    }

}
