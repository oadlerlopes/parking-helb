package br.canno.ph.manager.menu;

import br.canno.ph.parking.ParkingManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class MenuUtils {

    public ParkingManager parkingManager = new ParkingManager();

    public MenuUtils() {
    }

    public BorderPane getMenu() {
        MenuBar menuBar = new MenuBar();
        Menu administrationMenu = new Menu("Administration");
        MenuItem carParking = new MenuItem("Random Car Park");

        EventHandler<ActionEvent> actionEvent = eventOnMenu();

        administrationMenu.getItems().addAll(carParking);
        menuBar.getMenus().addAll(administrationMenu);

        carParking.setOnAction(actionEvent);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);

        return borderPane;
    }

    private EventHandler<ActionEvent> eventOnMenu() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                MenuItem menuItem = (MenuItem) event.getSource();
                String side = menuItem.getText();
                if ("Random Car Park".equalsIgnoreCase(side)) {
                    parkingManager.onCarPark();
                }
            }
        };
    }
}
