package dk.kea.swc.cadd.delivery.view;

import java.io.IOException;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import dk.kea.swc.cadd.delivery.MainApp;
import dk.kea.swc.cadd.delivery.db.TruckDAO;
import dk.kea.swc.cadd.delivery.model.Truck;

public class TruckOverviewController {
	
	@FXML private TableView<Truck> 			truckTable;
	@FXML private TableColumn<Truck, Integer> 	capacityColumn;
	@FXML private TableColumn<Truck, Double> 	speedColumn;
    @FXML private TableColumn<Truck, Boolean> 	availableColumn;
    @FXML private TableColumn<Truck, Boolean> editColumn;
	@FXML private TableColumn<Truck, Boolean> deleteColumn;



	// Data access object for the database
	private TruckDAO truckDAO;
	
	private MainApp mainApp; //TODO

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
		// Give the controller data to fill the table view
		truckDAO = new TruckDAO();
		truckTable.setItems(truckDAO.getTrucks());
			
		// Resize the columns (with percentages) when the window is enlarged //TODO COPYRIGHT
		capacityColumn  .prefWidthProperty().bind(truckTable.widthProperty().multiply(0.196));
		speedColumn		.prefWidthProperty().bind(truckTable.widthProperty().multiply(0.17));
		availableColumn .prefWidthProperty().bind(truckTable.widthProperty().multiply(0.15));
		editColumn		.prefWidthProperty().bind(truckTable.widthProperty().multiply(0.10));
		deleteColumn 	.prefWidthProperty().bind(truckTable.widthProperty().multiply(0.13));
		
		// Initialize the table with the three columns
		capacityColumn	.setCellValueFactory(cellData -> cellData.getValue().capacityProperty().asObject());
		speedColumn		.setCellValueFactory(cellData -> cellData.getValue().speedProperty().asObject());
		availableColumn	.setCellValueFactory(cellData -> cellData.getValue().availableProperty());
		editColumn		.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		deleteColumn	.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		
		// Create a cell value factory with buttons for each row in the table
		editColumn	.setCellFactory( truckBooleanTableColumn -> new AddEditCell());
		deleteColumn.setCellFactory( truckBooleanTableColumn -> new AddDeleteCell());
	}
	
	/** 
	 * A table cell containing a button for editing a truck. 
	 */
    private class AddEditCell extends TableCell<Truck, Boolean> {
      final Button button = new Button("Edit");

      AddEditCell() {
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  showTruckDialog(truckTable.getItems().get(getTableRow().getIndex()));
        	  //TODO
          }
        });
      }
      
      /** 
       * Places an edit button in the row only if the row is not empty. 
       */
      @Override protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
          setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
          setGraphic(button);
        } else {
          setGraphic(null);
        }
      }
      
    }
   
    /** 
	 * A table cell containing a button for editing a truck. 
	 */
    private class AddDeleteCell extends TableCell<Truck, Boolean> {
      final Button button = new Button("Delete");

      AddDeleteCell() {
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  int selectedIndex = getTableRow().getIndex();
        	  truckTable.getItems().remove(selectedIndex);
        	  //TODO
          }
        });
      }
      
      /** 
       * Places an edit button in the row only if the row is not empty. 
       */
      @Override protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
          setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
          setGraphic(button);
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
            dialogStage.initModality(Modality.WINDOW_MODAL); //TODO
            dialogStage.initOwner(mainApp.getPrimaryStage());
            dialogStage.setResizable(false);
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the truck object into the controller
            TruckDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTruck(truck);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
