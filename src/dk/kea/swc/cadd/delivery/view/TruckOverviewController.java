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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import dk.kea.swc.cadd.delivery.MainApp;
import dk.kea.swc.cadd.delivery.db.TruckDAO;
import dk.kea.swc.cadd.delivery.model.Truck;
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
		truckIDColumn   .prefWidthProperty().bind(truckTable.widthProperty().subtract(130).multiply(0.40));
		capacityColumn  .prefWidthProperty().bind(truckTable.widthProperty().subtract(130).multiply(0.20));
		speedColumn		.prefWidthProperty().bind(truckTable.widthProperty().subtract(130).multiply(0.20));
		availableColumn .prefWidthProperty().bind(truckTable.widthProperty().subtract(130).multiply(0.20));
		
		// Initialize the table with the three columns
		truckIDColumn	.setCellValueFactory(cellData -> cellData.getValue().truckIDProperty());
		capacityColumn	.setCellValueFactory(cellData -> cellData.getValue().capacityProperty().asObject());
		speedColumn		.setCellValueFactory(cellData -> cellData.getValue().speedProperty().asObject());
		availableColumn	.setCellValueFactory(cellData -> cellData.getValue().availableProperty());

		// Create a cell value factory with buttons for each row in the table
		editColumn	.setCellFactory( truckBooleanTableColumn -> new AddEditCell());
		deleteColumn.setCellFactory( truckBooleanTableColumn -> new AddDeleteCell());
	}
	
	@FXML
	private void handleAdd() {
		showTruckDialog(null);
		//truckTable.getItems().add(?);
	}
	
	/** 
	 * A table cell containing a button for editing a truck. 
	 */
    private class AddEditCell extends TableCell<Truck, Boolean> {
    	final Button button = new Button();
    	HBox wrap = new HBox();
      
      AddEditCell() {
    	  button.setId("edit-button");
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  showTruckDialog(truckTable.getItems().get(getTableRow().getIndex()));
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
	 * A table cell containing a button for editing a truck. 
	 */
    private class AddDeleteCell extends TableCell<Truck, Boolean> {
    	final Button button = new Button();
    	HBox wrap = new HBox();
      
      AddDeleteCell() {
    	  button.setId("delete-button");
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  int selectedIndex = getTableRow().getIndex();
        	  deleteTruck(truckTable.getItems().get(selectedIndex));       	  
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
			MyAlert alert = new MyAlert(
					AlertType.ERROR,
					"Error while deleting truck",
					"The truck was not deleted.",
					errorMessage
					);
			alert.showAndWait();
			if (errorMessage.startsWith("Truck")){
				// Updates the application's driver list with the new changes
				truckTable.getItems().remove(truck);
				}
			
		} else {
			// Shows the confirmation message because the update was successful
			MyAlert alert = new MyAlert(
					AlertType.INFORMATION,
					"Success",
					"Changes were submitted successfully",
					"The truck with ID: "+truck.getTruckID()+" was deleted.");
			alert.showAndWait();
			
			// Updates the application's driver list with the new changes
			truckTable.getItems().remove(truck);
		}
    }

}
