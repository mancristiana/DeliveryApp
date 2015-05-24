package dk.kea.swc.cadd.delivery;

import java.io.IOException;

import dk.kea.swc.cadd.delivery.db.LocationDAO;
import dk.kea.swc.cadd.delivery.view.LocationOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DeliveryApp extends Application {

    private Stage 		primaryStage;
    private BorderPane 	rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DeliveryApp");

        initRootLayout();
        showLocationOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DeliveryApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the location overview inside the root layout.
     */
    public void showLocationOverview() {
        try {
            // Load location overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DeliveryApp.class.getResource("view/LocationOverview.fxml"));
            AnchorPane locationOverview = (AnchorPane) loader.load();

            // Set location overview into the center of root layout.
            rootLayout.setCenter(locationOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}