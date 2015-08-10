package dk.kea.swc.cadd.delivery.view.manage;

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
import dk.kea.swc.cadd.delivery.db.LocationDAO;
import dk.kea.swc.cadd.delivery.model.Location;
import dk.kea.swc.cadd.delivery.view.ui.ButtonCell;

public class LocationOverviewController {
	
	@FXML private TableView<Location> 				locationTable;
	@FXML private TableColumn<Location, String> 	cityNameColumn;
	@FXML private TableColumn<Location, String> 	storageNameColumn;
	@FXML private TableColumn<Location, Double> 	priceColumn;
	@FXML private TableColumn<Location, Boolean> 	editColumn;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public LocationOverviewController() {
		
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Give the controller data to fill the table view
		locationTable.setItems(LocationDAO.getLocations());
			
		// Resize the columns (with percentages) when the window is enlarged //TODO COPYRIGHT
		cityNameColumn		.prefWidthProperty().bind(locationTable.widthProperty().subtract(75).multiply(0.34));
		storageNameColumn	.prefWidthProperty().bind(locationTable.widthProperty().subtract(75).multiply(0.33));
		priceColumn			.prefWidthProperty().bind(locationTable.widthProperty().subtract(75).multiply(0.33));
		
		// Initialize the table with the four columns
		cityNameColumn		.setCellValueFactory(cellData -> cellData.getValue().cityNameProperty());
		storageNameColumn	.setCellValueFactory(cellData -> cellData.getValue().storageNameProperty());
		priceColumn			.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

		// Create a cell value factory with buttons for each row in the table
		editColumn		.setCellFactory(cellData -> new ButtonCell<Location>("edit-button"){
			@Override
			public void onClick() {
				showLocationEditDialog(locationTable.getItems().get(getTableRow().getIndex()));
			}});
	}
    
    /**
     * Shows the location edit dialog.
     */
    public void showLocationEditDialog(Location location) {
        try {
            // Load the fxml file and create a new stage for the dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/manage/LocationEditDialog.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Location");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the location object into the controller
            LocationEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setLocation(location);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
