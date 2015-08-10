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
import dk.kea.swc.cadd.delivery.db.StorageDAO;
import dk.kea.swc.cadd.delivery.model.Storage;
import dk.kea.swc.cadd.delivery.view.ui.ButtonCell;


public class StorageOverviewController {
	
	@FXML private TableView<Storage> 				storageTable;
	@FXML private TableColumn<Storage, String> 		cityColumn;
	@FXML private TableColumn<Storage, Double> 	quantityColumn;
	@FXML private TableColumn<Storage, Boolean> 	editColumn;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public StorageOverviewController() {
		
	}
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Give the controller data to fill the table view
		storageTable.setItems(StorageDAO.getStorages());
		
	    // Resize the columns (with percentages) when the window is enlarged //TODO COPYRIGHT		
		cityColumn		.prefWidthProperty().bind(storageTable.widthProperty().subtract(75).multiply(0.60));
		quantityColumn	.prefWidthProperty().bind(storageTable.widthProperty().subtract(75).multiply(0.40));
				
		// Initialize the table with the four columns
		cityColumn		.setCellValueFactory(cellData -> cellData.getValue().cityNameProperty());
		quantityColumn	.setCellValueFactory(cellData -> cellData.getValue().availableQuantityProperty().asObject());
			
		// Create a cell value factory with buttons for each row in the table 
		editColumn		.setCellFactory(cellData -> new ButtonCell<Storage>("edit-button"){
			@Override
			public void onClick() {
				showEditStorageDialog(storageTable.getItems().get(getTableRow().getIndex()));
			}});
	}
            
    /**
     * Shows the storage dialog.
     */
    public void showEditStorageDialog(Storage storage) {
    	try {
            // Load the fxml file and create a new stage for the dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/manage/StorageEditDialog.fxml"));
            Pane page = loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Storage");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the storage object into the controller
            StorageEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setStorage(storage);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 

	