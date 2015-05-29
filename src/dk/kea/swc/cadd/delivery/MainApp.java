package dk.kea.swc.cadd.delivery;

import java.io.IOException;

import dk.kea.swc.cadd.delivery.view.DriverOverviewController;
import dk.kea.swc.cadd.delivery.view.LocationOverviewController;
import dk.kea.swc.cadd.delivery.view.OrderOverviewController;
import dk.kea.swc.cadd.delivery.view.RouteOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage 		primaryStage;
    private static BorderPane 	rootLayout;

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
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Setting the minimum width and height
            primaryStage.setMinWidth(rootLayout.getMinWidth()+17.25);
            primaryStage.setMinHeight(rootLayout.getMinHeight()+46.25);
            
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
            loader.setLocation(MainApp.class.getResource("view/LocationOverview.fxml"));
            Pane locationOverview = loader.load();

            // Set location overview into the center of root layout.
            rootLayout.setCenter(locationOverview);
            
            // Give the controller access to the main app.
            LocationOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the storage overview inside the root layout.
     */
    public void showStorageOverview() {
    	//TODO
    }
    
    /**
     * Shows the truck overview inside the root layout.
     */
    public void showTruckOverview() {
    	//TODO
    }
    
    /**
     * Shows the driver overview inside the root layout.
     */
    public void showDriverOverview() {
        try {
            // Load storage overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DriverOverview.fxml"));
            Pane driverOverview = loader.load();

            // Set driver overview into the center of root layout.
            rootLayout.setCenter(driverOverview);
            
            // Give the controller access to the main app.
            DriverOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the create order view inside the root layout.
     */
    public void showOrderCreate() {
    	try {
            // Load order overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderCreate.fxml"));
            Pane orderCreate = loader.load();

            // Set order overview into the center of root layout.
            rootLayout.setCenter(orderCreate);
            
            // Give the controller access to the main app.
//            OrderCreateController controller = loader.getController();
//            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the order overview inside the root layout.
     */
    public void showOrderOverview() {
    	try {
            // Load order overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderOverview.fxml"));
            Pane orderOverview = loader.load();

            // Set order overview into the center of root layout.
            rootLayout.setCenter(orderOverview);
            
            // Give the controller access to the main app.
            OrderOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the order archive view inside the root layout.
     */
    public void showOrderArchive() {
    	try {
            // Load order overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderArchive.fxml"));
            Pane orderArchive = loader.load();

            // Set order overview into the center of root layout.
            rootLayout.setCenter(orderArchive);
            
//            // Give the controller access to the main app.
//            OrderArchiveController controller = loader.getController();
//            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the order overview inside the root layout.
     */
    public void showRouteOverview() {
    	try {
            // Load order overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RouteOverview.fxml"));
            Pane routeOverview = loader.load();

            // Set order overview into the center of root layout.
            rootLayout.setCenter(routeOverview);
            
            // Give the controller access to the main app.
            RouteOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}