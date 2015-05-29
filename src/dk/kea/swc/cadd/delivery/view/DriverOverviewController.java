package dk.kea.swc.cadd.delivery.view;

import java.io.IOException;
import java.net.URL;

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
import dk.kea.swc.cadd.delivery.db.DriverDAO;
import dk.kea.swc.cadd.delivery.model.Driver;

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
	
	private MainApp mainApp; //TODO

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
		// Give the controller data to fill the table view
		driverDAO = new DriverDAO();
		driverTable.setItems(driverDAO.getDrivers());
			
		// Resize the columns (with percentages) when the window is enlarged //TODO COPYRIGHT
		nameColumn		.prefWidthProperty().bind(driverTable.widthProperty().multiply(0.196));
		phoneColumn		.prefWidthProperty().bind(driverTable.widthProperty().multiply(0.17));
		emailColumn		.prefWidthProperty().bind(driverTable.widthProperty().multiply(0.25));
		availableColumn .prefWidthProperty().bind(driverTable.widthProperty().multiply(0.15));
		editColumn		.prefWidthProperty().bind(driverTable.widthProperty().multiply(0.10));
		deleteColumn 	.prefWidthProperty().bind(driverTable.widthProperty().multiply(0.13));
		
		// Initialize the table with the four columns
		nameColumn		.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		phoneColumn		.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		emailColumn		.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		availableColumn	.setCellValueFactory(cellData -> cellData.getValue().availableProperty());
		editColumn		.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		deleteColumn	.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		
		// Create a cell value factory with buttons for each row in the table
		editColumn	.setCellFactory( driverBooleanTableColumn -> new AddEditCell());
		deleteColumn.setCellFactory( driverBooleanTableColumn -> new AddDeleteCell());
	}
	
	/** 
	 * A table cell containing a button for editing a driver. 
	 */
    private class AddEditCell extends TableCell<Driver, Boolean> {
      final Button button = new Button("Edit");

      AddEditCell() {
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  showDriverDialog(driverTable.getItems().get(getTableRow().getIndex()));
        	  //TODO
          }
        });
      }
      
      /** 
       * Places an edit button in the row only if the row is not empty. 
       */
      @Override 
      protected void updateItem(Boolean item, boolean empty) {
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
	 * A table cell containing a button for editing a driver. 
	 */
    private class AddDeleteCell extends TableCell<Driver, Boolean> {
      final Button button = new Button("Delete");

      AddDeleteCell() {
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  int selectedIndex = getTableRow().getIndex();
        	  driverTable.getItems().remove(selectedIndex);
        	  //TODO
          }
        });
      }
      
      /** 
       * Places an edit button in the row only if the row is not empty. 
       */
      @Override 
      protected void updateItem(Boolean item, boolean empty) {
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
            dialogStage.initModality(Modality.WINDOW_MODAL); //TODO
            dialogStage.initOwner(mainApp.getPrimaryStage());
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

    @FXML
    private void handleAdd(){
    	showDriverDialog(null);
  	  //TODO
    }
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
