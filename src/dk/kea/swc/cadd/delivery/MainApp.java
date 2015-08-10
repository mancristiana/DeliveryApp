package dk.kea.swc.cadd.delivery;

import java.io.IOException;
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
        showOrderOverview();
    }

    /**
     * Initializes and shows the root layout.
     */
    public void initRootLayout() {
        try {
            // Loads the root layout from the fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Sets the minimum width and height
            primaryStage.setMinWidth(rootLayout.getMinWidth()+17.25);
            primaryStage.setMinHeight(rootLayout.getMinHeight()+46.25);
            
            // Shows the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the location overview in the center of the root layout.
     */
    public void showLocationOverview() {
		showPage("LocationOverview");
    }
    
    /**
     * Shows the storage overview in the center of the root layout.
     */
    public void showStorageOverview() {
    	showPage("StorageOverview");
    }
    
    /**
     * Shows the truck overview in the center of the root layout.
     */
    public void showTruckOverview() {
    	showPage("TruckOverview");
    }
    
    /**
     * Shows the driver overview in the center of the root layout.
     */
    public void showDriverOverview() {
    	showPage("DriverOverview");
    }
    
    /**
     * Shows the create order view in the center of the root layout.
     */
    public void showOrderCreate() {
    	showPage("OrderCreate");
    }
    
    /**
     * Shows the order overview in the center of the root layout.
     */
    public void showOrderOverview() {
    	showPage("OrderOverview");
    }
    
    /**
     * Shows the order archive view in the center of the root layout.
     */
    public void showOrderArchive() {
    	showPage("OrderArchive");
    }
    
    /**
     * Shows the create order view in the center of the root layout.
     */
    public void showRouteCreate() {
    	showPage("RouteCreate");
    }
    
    /**
     * Shows the route overview in the center of the root layout.
     */
    public void showRouteOverview() {
    	showPage("RouteOverview");
    }
    
    /**
     * Shows the finished route page in the center of the root layout.
     */
    public void showRouteFinished() {
    	showPage("RouteFinished");
    }
    
    /**
     * Shows the page with the given resource name in the center of the root layout.
     */
    public static void showPage(String name){
    	try {
            // Loads the page from the fxml file into a Pane object.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/"+name+".fxml"));
            Pane page = loader.load();

            // Sets the page in the center of root layout.
            rootLayout.setCenter(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}