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
import dk.kea.swc.cadd.delivery.db.LocationDAO;
import dk.kea.swc.cadd.delivery.model.Location;

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
		editColumn			.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));

		// Create a cell value factory with buttons for each row in the table
		editColumn	.setCellFactory( locationBooleanTableColumn -> new AddEditCell());
	}
	
	/** 
	 * A table cell containing a button for editing a location. 
	 */
    private class AddEditCell extends TableCell<Location, Boolean> {
    	final Button button = new Button();
    	HBox wrap = new HBox();
    	
	    AddEditCell() {
	    	button.setId("edit-button");
	    	button.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override public void handle(ActionEvent actionEvent) {
	        		showLocationEditDialog(locationTable.getItems().get(getTableRow().getIndex()));
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
     * Shows the location edit dialog.
     */
    public void showLocationEditDialog(Location location) {
        try {
            // Load the fxml file and create a new stage for the dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LocationEditDialog.fxml"));
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
