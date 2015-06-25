package dk.kea.swc.cadd.delivery.view;

import java.io.IOException;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import dk.kea.swc.cadd.delivery.MainApp;
import dk.kea.swc.cadd.delivery.db.StorageDAO;
import dk.kea.swc.cadd.delivery.model.Storage;


public class StorageOverviewController {
	
	@FXML private TableView<Storage> 				storageTable;
	@FXML private TableColumn<Storage, String> 		cityColumn;
	@FXML private TableColumn<Storage, Integer> 	quantityColumn;
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
		editColumn		.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
				
		// Create a cell value factory with buttons for each row in the table 
		editColumn	.setCellFactory( storageBooleanTableColumn -> new AddEditCell());
	}
	
	/** 
	 * A table cell containing a button for editing 
	 */
    private class AddEditCell extends TableCell<Storage, Boolean> {
        final Button button = new Button();
        HBox wrap = new HBox();
      
      AddEditCell() {
    	  button.setId("edit-button");
    	  button.setOnAction(new EventHandler<ActionEvent>() {
    		  @Override public void handle(ActionEvent actionEvent) {
    			  showEditStorageDialog(storageTable.getItems().get(getTableRow().getIndex()));
    		  }
    	  });
    	  wrap.setAlignment(Pos.CENTER);
    	  wrap.getChildren().add(button);
      }
      
      /** 
       * Places an edit button in the row only if the row is not empty. 
       */
      @Override protected void updateItem(Boolean item, boolean empty) {
    	  super.updateItem(item, empty);
    	  if (!empty) {
    		  setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    		  setGraphic(wrap);
    	  } else {
    		  setGraphic(null);
    	  }
      }
    }
      
            
    /**
     * Shows the storage dialog.
     */
    public void showEditStorageDialog(Storage storage) {
    	try {
            // Load the fxml file and create a new stage for the dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/StorageEditDialog.fxml"));
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
 

	