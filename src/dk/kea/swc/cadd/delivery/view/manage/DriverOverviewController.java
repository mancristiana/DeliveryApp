package dk.kea.swc.cadd.delivery.view.manage;

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
import dk.kea.swc.cadd.delivery.db.DriverDAO;
import dk.kea.swc.cadd.delivery.model.Driver;
import dk.kea.swc.cadd.delivery.view.ui.AvailableCell;
import dk.kea.swc.cadd.delivery.view.ui.ButtonCell;
import dk.kea.swc.cadd.delivery.view.ui.MyAlert;

public class DriverOverviewController {
	
	@FXML private TableView<Driver> 			driverTable;
	@FXML private TableColumn<Driver, String> 	nameColumn;
	@FXML private TableColumn<Driver, String> 	phoneColumn;
	@FXML private TableColumn<Driver, String> 	emailColumn;
	@FXML private TableColumn<Driver, Boolean> 	availableColumn;
	@FXML private TableColumn<Driver, Boolean> 	editColumn;
	@FXML private TableColumn<Driver, Boolean> 	deleteColumn;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
    	// Adds the list of drivers from the database to the table
    	driverTable.setItems(DriverDAO.getDrivers());
    	
		// Resizes the columns (with percentages) when the window is enlarged
		nameColumn		.prefWidthProperty().bind(driverTable.widthProperty().subtract(215).multiply(0.40));
		phoneColumn		.prefWidthProperty().bind(driverTable.widthProperty().subtract(215).multiply(0.20));
		emailColumn		.prefWidthProperty().bind(driverTable.widthProperty().subtract(215).multiply(0.40));
		
		// Sets the values of the columns
		nameColumn		.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		phoneColumn		.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		emailColumn		.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		availableColumn	.setCellValueFactory(cellData -> cellData.getValue().availableProperty());
		
		availableColumn	.setCellFactory(cellData -> new AvailableCell<Driver>());
		// Creates a cell value factory with edit buttons for each row in the table
		editColumn	.setCellFactory(cellData -> new ButtonCell<Driver>("edit-button"){
			@Override
			public void onClick() {
	        	showDriverDialog(driverTable.getItems().get(getTableRow().getIndex()));
			}});
		
		// Creates a cell value factory with delete buttons for each row in the table
		deleteColumn.setCellFactory(cellData -> new ButtonCell<Driver>("delete-button"){
			@Override
			public void onClick(){
				int selectedIndex = getTableRow().getIndex();
				deleteDriver(driverTable.getItems().get(selectedIndex));
			}});
	}

    /**
     * Shows the driver dialog.
     */
    public void showDriverDialog(Driver driver) {
        try {
            // Loads the fxml file and creates a new stage for the dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/manage/DriverDialog.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(driver==null ? "Add driver" : "Edit driver");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
        
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Gives the controller access to the driver and the driver list
            DriverDialogController controller = loader.getController();
            controller.setDriver(driver);
            controller.setDriverTable(driverTable);
            controller.setDialogStage(dialogStage);
            
            // Shows the dialog and waits for the user
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd(){
    	showDriverDialog(null);
    }
    
    private void deleteDriver(Driver driver){
		String errorMessage = "";
		
        errorMessage = DriverDAO.removeDriver(driver);
        
        // Checks if we got sql errors
		if(!errorMessage.isEmpty()){
            // Shows the error message because we got a sql error.
			MyAlert.show(
					AlertType.ERROR,
					"Error while deleting driver",
					"The driver was not deleted.",
					errorMessage
					);
			if (errorMessage.startsWith("Driver"))
				// Updates the application's driver list with the new changes
				driverTable.getItems().remove(driver);
		} else {
			// Shows the confirmation message because the update was successful
			MyAlert.show(
					AlertType.INFORMATION,
					"Success",
					"Changes were submitted successfully",
					"The driver with ID: "+driver.getDriverId()+" was deleted.");
			
			// Updates the application's driver list with the new changes
			driverTable.getItems().remove(driver);
		}
    }
}
