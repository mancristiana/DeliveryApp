package dk.kea.swc.cadd.delivery;

import java.io.IOException;

import dk.kea.swc.cadd.delivery.model.Location;
import dk.kea.swc.cadd.delivery.view.LocationEditDialogController;
import dk.kea.swc.cadd.delivery.view.LocationOverviewController;
import dk.kea.swc.cadd.delivery.view.OrderOverviewController;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
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
            Node locationOverview = loader.load();

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
     * Shows the location edit dialog.
     */
    public void showLocationEditDialog(Location location) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LocationEditDialog.fxml"));
            BorderPane page = (BorderPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Location");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            
            // Setting the minimum width and height
            dialogStage.setMinWidth(page.getMinWidth()+17.25);
            dialogStage.setMinHeight(page.getMinHeight()+46.25);
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the location into the controller.
            LocationEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setLocation(location);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showOrderOverview() {
    	try {
            // Load order overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderOverview.fxml"));
            Node orderOverview = loader.load();

            // Set order overview into the center of root layout.
            rootLayout.setCenter(orderOverview);
            
            // Give the controller access to the main app.
            OrderOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOrderCreate() {
    	try {
            // Load order overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderCreate.fxml"));
            Node orderCreate = loader.load();

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