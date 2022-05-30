package br.canno.ph;

import br.canno.ph.discount.ticket.TicketsModel;
import br.canno.ph.manager.MainManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ParkingHelb extends Application {

    public MainManager mainManager = new MainManager();
    public TicketsModel ticketsModel = new TicketsModel();

    @Override
    public void start(Stage primaryStage) throws InterruptedException, FileNotFoundException, IOException {

        Scene scene = new Scene(mainManager.openApplication(), 500, 500);

        primaryStage.setTitle("Parking System!");
        primaryStage.setScene(scene);
        primaryStage.show();

        mainManager.loadApplication();
        ticketsModel.initialProceed();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
