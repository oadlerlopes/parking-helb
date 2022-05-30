package br.canno.ph.manager.events;

import br.canno.ph.car.type.Vehicle;
import br.canno.ph.car.type.VehicleType;
import br.canno.ph.discount.DiscountModel;
import br.canno.ph.discount.ticket.TicketsModel;
import br.canno.ph.manager.MainManager;
import br.canno.ph.parking.ParkingManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

public class EventsManager {

    public ParkingManager parkingManager = new ParkingManager();
    public DiscountModel discountModel = new DiscountModel();
    public TicketsModel ticketsModel = new TicketsModel();

    public EventsManager() {
    }

    public void onClickCarSpace(Vehicle vehicle, int id) {

        if (vehicle.getVehicleType() == VehicleType.NONE) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Status");
            alert.setHeaderText("Status");
            alert.setContentText("Free parking space.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Status");
            alert.setHeaderText("The vacancy is occupied. select options");
            alert.setContentText("Status: Occupied\nVehicle type: " + vehicle.getVehicleType().name() + "\nPlate: " + vehicle.getLicensePlate().toUpperCase()
                    + "\nBase price: $" + vehicle.getVehicleType().getPrice() + ".00"
                    + "\nTotal to pay: $" + discountModel.discountByDay(vehicle) + "0"
                    + ((discountModel.checkDiscount(vehicle).isDay() && (discountModel.discountByDay(vehicle) != (float)vehicle.getVehicleType().getPrice())) ? "\nReduction: " + discountModel.checkDiscount(vehicle).name() + " - " + discountModel.checkDiscount(vehicle).getDescription() : "")
                    + "\nDate: " + vehicle.getDate() + "");

            ButtonType releaseCar = new ButtonType("Release car");
            ButtonType changeType = new ButtonType("Changer type");
            ButtonType editPlate = new ButtonType("Edit plate");
            ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(releaseCar, changeType, editPlate, cancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == releaseCar) {
                ticketsModel.prize(vehicle);
                vehicle.setVehicleType(VehicleType.NONE);
                vehicle.setLicensePlate("");
                vehicle.setDate(null);

                for (Map.Entry<Button, Vehicle> entry : MainManager.vehiclesList.entrySet()) {
                    if (entry.getKey().getId().equals("" + id)) {
                        entry.getKey().setText(entry.getKey().getId());
                        entry.getKey().setStyle(vehicle.getVehicleType().getColor());
                        break;
                    }
                }

            } else if (result.get() == changeType) {
                List<String> carType = new ArrayList<>();

                for (VehicleType vehicles : VehicleType.values()) {
                    carType.add(vehicles.name());
                }

                ChoiceDialog<String> dialog = new ChoiceDialog<>("", carType);
                dialog.setTitle("Select vehicle type");
                dialog.setHeaderText("Select vehicle type");
                dialog.setContentText("Choose:");

                Optional<String> resultVehicles = dialog.showAndWait();

                if (result.isPresent()) {
                    for (VehicleType vehicles : VehicleType.values()) {
                        if (resultVehicles.get().equals(vehicles.name())) {
                            vehicle.setVehicleType(vehicles);
                        }
                    }
                    for (Map.Entry<Button, Vehicle> entry : MainManager.vehiclesList.entrySet()) {
                        if (entry.getKey().getId().equals("" + id)) {
                            entry.getKey().setText(entry.getKey().getId() + "\n" + entry.getValue().getLicensePlate());
                            entry.getKey().setStyle(vehicle.getVehicleType().getColor());
                            break;
                        }
                    }
                }
            } else if (result.get() == editPlate) {
                TextInputDialog dialog = new TextInputDialog("Plate");
                dialog.setTitle("Plate of vehicle");
                dialog.setHeaderText("Write the license plate of the vehicle");
                dialog.setContentText("Plate:");

                Optional<String> resultPlate = dialog.showAndWait();
                if (result.isPresent()) {
                    vehicle.setLicensePlate(resultPlate.get());
                    for (Map.Entry<Button, Vehicle> entry : MainManager.vehiclesList.entrySet()) {
                        if (entry.getKey().getId().equals("" + id)) {
                            entry.getKey().setText(entry.getKey().getId() + "\n" + vehicle.getLicensePlate());
                        }
                    }
                }
            }
        }
    }

}
