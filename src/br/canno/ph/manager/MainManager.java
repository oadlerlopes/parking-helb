package br.canno.ph.manager;

import br.canno.ph.car.type.Vehicle;
import br.canno.ph.car.type.VehicleType;
import br.canno.ph.discount.ticket.TicketsModel;
import br.canno.ph.manager.events.EventsManager;
import br.canno.ph.manager.menu.MenuUtils;
import br.canno.ph.parking.ParkingManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class MainManager {

    public List screenList;
    public int maxParked = ParkingManager.maxParked;
    public int preLoad = 100;

    public static LinkedHashMap<Button, Vehicle> vehiclesList = new LinkedHashMap ();

    public MenuUtils menuUtils = new MenuUtils();
    public EventsManager eventsManager = new EventsManager();
    public TicketsModel ticketsModel = new TicketsModel();

    public MainManager() {
    }

    public VBox openApplication() {
        HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(20, 20, 20, 20));
        hbox.setAlignment(Pos.TOP_CENTER);

        HBox hbox2 = new HBox();
        hbox2.setAlignment(Pos.CENTER);
        hbox2.setSpacing(5);
        hbox2.setPadding(new Insets(20, 20, 20, 20));

        HBox hbox3 = new HBox();
        hbox3.setAlignment(Pos.BOTTOM_CENTER);
        hbox3.setSpacing(5);
        hbox3.setPadding(new Insets(20, 20, 20, 20));

        HBox hbox4 = new HBox();
        hbox4.setAlignment(Pos.BASELINE_CENTER);
        hbox4.setSpacing(5);
        hbox4.setPadding(new Insets(20, 20, 20, 20));

        for (int i = 0; i < maxParked; i++) {

            Button btn = new Button();
            btn.setId("" + i);

            VehicleType vehicleType = VehicleType.NONE;
            btn.setText(btn.getId());
            btn.setStyle(VehicleType.NONE.getColor());

            btn.textAlignmentProperty().set(TextAlignment.CENTER);
            btn.setPrefSize(100, 150);
            btn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    final Node source = (Node) event.getSource();
                    String id = source.getId();

                    for (Map.Entry<Button, Vehicle> entry : vehiclesList.entrySet()) {
                        if (entry.getKey().getId().equals(id)) {
                            eventsManager.onClickCarSpace(entry.getValue(), Integer.valueOf(id));
                        }
                    }

                }
            });

            if (i < 5) {
                hbox.getChildren().add(btn);
            } else if (i >= 5 && i < 10) {
                hbox2.getChildren().add(btn);
            } else if (i >= 10 && i < 15) {
                hbox3.getChildren().add(btn);
            } else if (i >= 15) {
                hbox4.getChildren().add(btn);
            }

            Vehicle vehicleID = new Vehicle(i, vehicleType, "", new Date(System.currentTimeMillis()));

            vehiclesList.put(btn, vehicleID);
        }

        VBox vbox = new VBox(menuUtils.getMenu(), hbox, hbox2, hbox3, hbox4);

        return vbox;
    }

    public void loadApplication() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("data.txt"));

        ArrayList<Integer> seconds = new ArrayList<>();
        ArrayList<String> model = new ArrayList<>();
        ArrayList<String> plate = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(",");
            seconds.add(Integer.valueOf(split[0]));
            model.add(split[1]);
            plate.add(split[2]);
        }

        Thread thread;
        thread = new Thread() {
            @Override
            public void run() {
                int i = 0;

                for (Map.Entry<Button, Vehicle> entry : vehiclesList.entrySet()) {
                    if (entry.getValue().getVehicleType().equals(VehicleType.NONE)) {
                        entry.getValue().setDate(new Date(System.currentTimeMillis()));
                        entry.getValue().setLicensePlate(plate.get(i));

                        for (VehicleType vehicles : VehicleType.values()) {
                            if (model.get(i).equalsIgnoreCase(vehicles.name().toLowerCase().toString())) {
                                Platform.runLater(() -> entry.getKey().setText(entry.getKey().getId() + "\n" + entry.getValue().getLicensePlate()));
                                Platform.runLater(() -> entry.getKey().setStyle(vehicles.getColor()));
                                entry.getValue().setVehicleType(vehicles);
                            }
                        }

                        Platform.runLater(() -> entry.getKey().setStyle(entry.getValue().getVehicleType().getColor()));

                        i++;

                        try {
                            new Thread().sleep(seconds.get(i) * 1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MainManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        };

        thread.setDaemon(true);
        thread.start();

    }

    public List getScreenList() {
        return screenList;
    }

}
