package dk.kea.swc.cadd.delivery.view;

import java.io.IOException;

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
import dk.kea.swc.cadd.delivery.db.DriverDAO;
import dk.kea.swc.cadd.delivery.model.Driver;
import dk.kea.swc.cadd.delivery.view.ui.ButtonCell;

public class DriverOverviewController {
	
	@FXML private TableView<Driver> 			driverTable;
	@FXML private TableColumn<Driver, String> 	nameColumn;
	@FXML private TableColumn<Driver, String> 	phoneColumn;
	@FXML private TableColumn<Driver, String> 	emailColumn;
	@FXML private TableColumn<Driver, Boolean> 	availableColumn;
	@FXML private TableColumn<Driver, Boolean> 	editColumn;
	@FXML private TableColumn<Driver, Boolean> 	deleteColumn;

	// Data access object for the database
	private DriverDAO driverDAO;
	
	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public DriverOverviewController() {
		
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
//		// Adds the list of drivers from the database to the table
//		driverTable.setItems(driverDAO.getDrivers());
			
		// Resize the columns (with percentages) when the window is enlarged
		nameColumn		.prefWidthProperty().bind(driverTable.widthProperty().subtract(130).multiply(0.30));
		phoneColumn		.prefWidthProperty().bind(driverTable.widthProperty().subtract(130).multiply(0.17));
		emailColumn		.prefWidthProperty().bind(driverTable.widthProperty().subtract(130).multiply(0.33));
		availableColumn .prefWidthProperty().bind(driverTable.widthProperty().subtract(130).multiply(0.20));
		
		// Sets the values of the columns
		nameColumn		.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		phoneColumn		.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		emailColumn		.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		availableColumn	.setCellValueFactory(cellData -> cellData.getValue().availableProperty());
		
		// The value of the columns with buttons are booleans that check if the row is empty or now
		editColumn		.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		deleteColumn	.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		
		// Creates a cell value factory with edit buttons for each row in the table
		editColumn		.setCellFactory(driverBooleanTableColumn -> new ButtonCell<Driver>("edit-button"){
			@Override
			public void onButtonClicked() {
	        	showDriverDialog(driverTable.getItems().get(getTableRow().getIndex()));
	        	//TODO
			}});
		
		// Creates a cell value factory with delete buttons for each row in the table
		deleteColumn	.setCellFactory(driverBooleanTableColumn -> new ButtonCell<Driver>("delete-button"){
			@Override
			public void onButtonClicked(){
				int selectedIndex = getTableRow().getIndex();
				System.out.println(driverDAO.removeDriver(driverTable.getItems().get(selectedIndex)));
				driverTable.getItems().remove(selectedIndex);
			}
		});
	}

    /**
     * Shows the driver dialog.
     */
    public void showDriverDialog(Driver driver) {
        try {
            // Load the fxml file and create a new stage for the dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DriverDialog.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            if(driver!=null){
            	dialogStage.setTitle("Edit driver");
            }
            else {
            	dialogStage.setTitle("Add driver");
            }
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the driver object into the controller
            DriverDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDriver(driver);
            if(driver==null){
            	controller.setDriverList(driverTable.getItems());
            }
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshTable(){
    	LoadingScreen.show();
    	driverDAO = new DriverDAO();
    	// Adds the list of drivers from the database to the table
    	driverTable.setItems(driverDAO.getDrivers());
    	LoadingScreen.hide();
    }
    @FXML
    private void handleAdd(){
    	refreshTable();
//    	showDriverDialog(null);
  	  //TODO
    }

}
