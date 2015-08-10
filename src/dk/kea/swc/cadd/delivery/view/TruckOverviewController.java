package dk.kea.swc.cadd.delivery.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import dk.kea.swc.cadd.delivery.MainApp;
import dk.kea.swc.cadd.delivery.db.TruckDAO;
import dk.kea.swc.cadd.delivery.model.Truck;
import dk.kea.swc.cadd.delivery.view.ui.AvailableCell;
import dk.kea.swc.cadd.delivery.view.ui.ButtonCell;
import dk.kea.swc.cadd.delivery.view.ui.MyAlert;

public class TruckOverviewController {
	
	@FXML private TableView<Truck> 	truckTable;
	@FXML private TableColumn<Truck, String> 	truckIDColumn;
	@FXML private TableColumn<Truck, Integer> 	capacityColumn;
	@FXML private TableColumn<Truck, Double> 	speedColumn;
    @FXML private TableColumn<Truck, Boolean> 	availableColumn;
    @FXML private TableColumn<Truck, Boolean> 	editColumn;
	@FXML private TableColumn<Truck, Boolean> 	deleteColumn;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public TruckOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Adds the list of trucks from the database to the table
		truckTable.setItems(TruckDAO.getTrucks());
			
		// Resize the columns (with percentages) when the window is enlarged
		truckIDColumn   .prefWidthProperty().bind(truckTable.widthProperty().subtract(215).multiply(0.40));
		capacityColumn  .prefWidthProperty().bind(truckTable.widthProperty().subtract(215).multiply(0.25));
		speedColumn		.prefWidthProperty().bind(truckTable.widthProperty().subtract(215).multiply(0.35));
		
		// Initialize the table with the three columns
		truckIDColumn	.setCellValueFactory(cellData -> cellData.getValue().truckIDProperty());
		capacityColumn	.setCellValueFactory(cellData -> cellData.getValue().capacityProperty().asObject());
		speedColumn		.setCellValueFactory(cellData -> cellData.getValue().speedProperty().asObject());
		availableColumn	.setCellValueFactory(cellData -> cellData.getValue().availableProperty());

		// Create a cell value factory with buttons for each row in the table
		availableColumn	.setCellFactory(cellData -> new AvailableCell<Truck>());
		editColumn		.setCellFactory(cellData -> new ButtonCell<Truck>("edit-button"){
			@Override
			public void onClick() {
				showTruckDialog(truckTable.getItems().get(getTableRow().getIndex()));
			}});
		deleteColumn	.setCellFactory(cellData -> new ButtonCell<Truck>("delete-button"){
			@Override
			public void onClick(){
	        	int selectedIndex = getTableRow().getIndex();
	        	deleteTruck(truckTable.getItems().get(selectedIndex));
			}});
	}
	
	/** 
	 * The add button's action, which is invoked whenever the add button is clicked.
	 */
	@FXML
	private void handleAdd() {
		showTruckDialog(null);
	}
    
    /**
     * Shows the truck dialog.
     */
    public void showTruckDialog(Truck truck) {
        try {
            // Load the fxml file and create a new stage for the dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TruckDialog.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Truck");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the truck, list object into the controller
            TruckDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTruck(truck);
            controller.setTable(truckTable);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void deleteTruck(Truck truck){
		String errorMessage = "";
        errorMessage = TruckDAO.removeTruck(truck);
  	 
        // Checks if we got sql errors
		if(!errorMessage.isEmpty()){
            // Shows the error message because we got a sql error.
			MyAlert.show(
					AlertType.ERROR,
					"Error while deleting truck",
					"The truck was not deleted.",
					errorMessage
					);
			if (errorMessage.startsWith("Truck")){
				// Updates the application's driver list with the new changes
				truckTable.getItems().remove(truck);
				}
			
		} else {
			// Shows the confirmation message because the update was successful
			MyAlert.show(
					AlertType.INFORMATION,
					"Success",
					"Changes were submitted successfully",
					"The truck with ID: "+truck.getTruckID()+" was deleted.");
			
			// Updates the application's driver list with the new changes
			truckTable.getItems().remove(truck);
		}
    }

}
