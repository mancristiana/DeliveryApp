package dk.kea.swc.cadd.delivery;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
        showStartingPage();
    }

	/**
     * Initializes and shows the root layout.
     */
    public void initRootLayout() {
        try {
            // Loads the root layout from the fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
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
     * Shows the starting page with the welcome image
     */
    public void showStartingPage() {
    	HBox container = new HBox();
    	container.setId("welcome");
    	container.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showOrderOverview();
			}
		});
    	rootLayout.setCenter(container);
	}
    
    /**
     * Shows the location overview in the center of the root layout.
     */
    public void showLocationOverview() {
		showPage("view/manage/LocationOverview");
    }
    
    /**
     * Shows the storage overview in the center of the root layout.
     */
    public void showStorageOverview() {
    	showPage("view/manage/StorageOverview");
    }
    
    /**
     * Shows the truck overview in the center of the root layout.
     */
    public void showTruckOverview() {
    	showPage("view/manage/TruckOverview");
    }
    
    /**
     * Shows the driver overview in the center of the root layout.
     */
    public void showDriverOverview() {
    	showPage("view/manage/DriverOverview");
    }
    
    /**
     * Shows the create order view in the center of the root layout.
     */
    public void showOrderCreate() {
    	showPage("view/orders/OrderCreate");
    }
    
    /**
     * Shows the order overview in the center of the root layout.
     */
    public void showOrderOverview() {
    	showPage("view/orders/OrderOverview");
    }
    
    /**
     * Shows the order archive view in the center of the root layout.
     */
    public void showOrderArchive() {
    	showPage("view/orders/OrderArchive");
    }
    
    /**
     * Shows the create order view in the center of the root layout.
     */
    public void showRouteCreate() {
    	showPage("view/routes/RouteCreate");
    }
    
    /**
     * Shows the route overview in the center of the root layout.
     */
    public void showRouteOverview() {
    	showPage("view/routes/RouteOverview");
    }
    
    /**
     * Shows the finished route page in the center of the root layout.
     */
    public void showRouteFinished() {
    	showPage("view/routes/RouteFinished");
    }
    
    /**
     * Shows the page with the given resource name in the center of the root layout.
     */
    public static void showPage(String location){
    	try {
            // Loads the page from the fxml file into a Pane object.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(location+".fxml"));
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