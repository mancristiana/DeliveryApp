package dk.kea.swc.cadd.delivery.view.routes;

import java.io.IOException;

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

public class RouteOverviewController {
	
	@FXML private TableView<Route> routeTable;
	@FXML private TableColumn<Route, Integer> 	routeIDColumn;
	@FXML private TableColumn<Route, String> 	driverColumn;
	@FXML private TableColumn<Route, String> 	truckIDColumn;
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
		routeTable.setItems(RouteDAO.getRoutes(false));
			
		// Resize the columns (with percentages) when the window is enlarged 
		routeIDColumn	.prefWidthProperty().bind(routeTable.widthProperty().subtract(185).multiply(0.10));
		driverColumn	.prefWidthProperty().bind(routeTable.widthProperty().subtract(185).multiply(0.70));
		truckIDColumn	.prefWidthProperty().bind(routeTable.widthProperty().subtract(185).multiply(0.20));

		
		// Initialize the table with the four columns
		routeIDColumn	.setCellValueFactory(cellData -> cellData.getValue().routeIDProperty().asObject());
		driverColumn	.setCellValueFactory(cellData -> cellData.getValue().driverProperty());
		truckIDColumn	.setCellValueFactory(cellData -> cellData.getValue().truckIDProperty());

		// Creates a cell value factory with edit buttons for each row in the table
		detailsColumn	.setCellFactory(driverBooleanTableColumn -> new ButtonCell<Route>("edit-button"){
			@Override
			public void onClick() {
				int selectedIndex = getTableRow().getIndex();
				showDetailsDialog(routeTable.getItems().get(selectedIndex), selectedIndex);
			}});
		
		// Creates a cell value factory with edit buttons for each row in the table
		mapColumn		.setCellFactory(driverBooleanTableColumn -> new ButtonCell<Route>("map-button"){
			@Override
			public void onClick() {
	        	int selectedIndex = getTableRow().getIndex();
	        	Route selectedRoute = routeTable.getItems().get(selectedIndex);
	        	  
	        	new MapOfRoute(selectedRoute);
			}});
		
		// Creates a cell value factory with delete buttons for each row in the table
		deleteColumn	.setCellFactory(driverBooleanTableColumn -> new ButtonCell<Route>("check-button"){
			@Override
			public void onClick(){
	        	int selectedIndex = getTableRow().getIndex();
	        	RouteDAO.finishRoute(routeTable.getItems().get(selectedIndex));
	        	routeTable.getItems().remove(selectedIndex);
			}});
	}
    
    /**
     * Shows the details dialog.
     */
    public void showDetailsDialog(Route route, int selectedIndex) {
        try {
            // Load the fxml file and create a new stage for the dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/routes/RouteDetailsDialog.fxml"));
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
            controller.setRouteTable(routeTable);
            controller.setSelectedIndex(selectedIndex);
            controller.setFinished(false);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
