package dk.kea.swc.cadd.delivery;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import dk.kea.swc.cadd.delivery.view.LoadingScreen;
import dk.kea.swc.cadd.delivery.view.RouteCreateController;
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
        LoadingScreen.initLoadingScreen(rootLayout);
        showOrderOverview();
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
     * Shows the storage overview inside the root layout.
     */
    public void showStorageOverview() {
    	showPage("StorageOverview");
    }
    
    /**
     * Shows the truck overview inside the root layout.
     */
    public void showTruckOverview() {
    	showPage("TruckOverview");
    }
    
    /**
     * Shows the driver overview inside the root layout.
     */
    public void showDriverOverview() {
    	showPage("DriverOverview");
    }
    
    /**
     * Shows the create order view inside the root layout.
     */
    public void showOrderCreate() {
    	showPage("OrderCreate");
    }
    
    /**
     * Shows the order overview inside the root layout.
     */
    public void showOrderOverview() {
    	showPage("OrderOverview");
    }
    
    /**
     * Shows the order archive view inside the root layout.
     */
    public void showOrderArchive() {
    	showPage("OrderArchive");
    }
    
    /**
     * Shows the create order view inside the root layout.
     */
    public void showRouteCreate() {
    	try {
            // Load order overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RouteCreate.fxml"));
            Pane routeCreate = loader.load();

            // Set order overview into the center of root layout.
            rootLayout.setCenter(routeCreate);
            
            // Give the controller access to the main app.
            RouteCreateController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the order overview inside the root layout.
     */
    public void showRouteOverview() {
    	showPage("RouteOverview");
    }

    /**
     * Shows the location overview inside the root layout.
     */
    public void showLocationOverview() {
		showPage("LocationOverview");
    }
    
    public void showPage(String name){
    	try {
            // Load the page into a Pane object.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/"+name+".fxml"));
            Pane page = loader.load();

            // Set the page in the center of root layout.
            rootLayout.setCenter(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	/**
     * Returns the main stage.
     * @return <code>primaryStage</code>
     */
	public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}