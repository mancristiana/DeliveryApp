package dk.kea.swc.cadd.delivery.view;

import java.io.IOException;
import java.util.Date;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import dk.kea.swc.cadd.delivery.MainApp;
import dk.kea.swc.cadd.delivery.db.RouteDAO;
import dk.kea.swc.cadd.delivery.model.Route;
import dk.kea.swc.cadd.delivery.util.MapOfRoute;
import dk.kea.swc.cadd.delivery.view.ui.ButtonCell;

public class RouteFinishedController {
	
	@FXML private TableView<Route> routeTable;
	@FXML private TableColumn<Route, Integer> 	routeIDColumn;
	@FXML private TableColumn<Route, String>	dateColumn;
	@FXML private TableColumn<Route, String> 	driverColumn;
	@FXML private TableColumn<Route, Integer> 	truckIDColumn;
	@FXML private TableColumn<Route, Boolean> 	detailsColumn;
	@FXML private TableColumn<Route, Boolean> 	mapColumn;
	@FXML private TableColumn<Route, Boolean> 	deleteColumn;


	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Give the controller data to fill the table view
		routeTable.setItems(RouteDAO.getRoutes(true));
			
		// Resize the columns (with percentages) when the window is enlarged 
		routeIDColumn	.prefWidthProperty().bind(routeTable.widthProperty().subtract(185).multiply(0.10));
		dateColumn		.prefWidthProperty().bind(routeTable.widthProperty().subtract(185).multiply(0.20));
		driverColumn	.prefWidthProperty().bind(routeTable.widthProperty().subtract(185).multiply(0.60));
		truckIDColumn	.prefWidthProperty().bind(routeTable.widthProperty().subtract(185).multiply(0.10));

		
		// Initialize the table with the four columns
		routeIDColumn	.setCellValueFactory(cellData -> cellData.getValue().routeIDProperty().asObject());
		dateColumn		.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		driverColumn	.setCellValueFactory(cellData -> cellData.getValue().driverProperty());
		truckIDColumn	.setCellValueFactory(cellData -> cellData.getValue().truckIDProperty().asObject());
		detailsColumn	.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		mapColumn		.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		deleteColumn	.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		
		// Creates a cell value factory with edit buttons for each row in the table
		detailsColumn	.setCellFactory(driverBooleanTableColumn -> new ButtonCell<Route>("edit-button"){
			@Override
			public void onButtonClicked() {
				showDetailsDialog(routeTable.getItems().get(getTableRow().getIndex()));
			}});
		
		// Creates a cell value factory with edit buttons for each row in the table
		mapColumn		.setCellFactory(driverBooleanTableColumn -> new ButtonCell<Route>("map-button"){
			@Override
			public void onButtonClicked() {
	        	int selectedIndex = getTableRow().getIndex();
	        	Route selectedRoute = routeTable.getItems().get(selectedIndex);
	        	  
	        	new MapOfRoute(selectedRoute);
			}});
		
		// Creates a cell value factory with delete buttons for each row in the table
		deleteColumn	.setCellFactory(driverBooleanTableColumn -> new ButtonCell<Route>("delete-button"){
			@Override
			public void onButtonClicked(){
	        	int selectedIndex = getTableRow().getIndex();
	         	 
	        	RouteDAO.deleteRoute(routeTable.getItems().get(selectedIndex));
	        	routeTable.getItems().remove(selectedIndex);
			}});
	}
    
    /**
     * Shows the details dialog.
     */
    public void showDetailsDialog(Route route) {
        try {
            // Load the fxml file and create a new stage for the dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RouteDetailsDialog.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Details");
            dialogStage.initModality(Modality.APPLICATION_MODAL); 
            dialogStage.setResizable(false);
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the driver object into the controller
            RouteDetailsDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRoute(route);
            controller.setFinished(true);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
