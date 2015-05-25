package dk.kea.swc.cadd.delivery.view;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import dk.kea.swc.cadd.delivery.MainApp;
import dk.kea.swc.cadd.delivery.db.LocationDAO;
import dk.kea.swc.cadd.delivery.model.Location;

public class LocationOverviewController {
	
	@FXML
	private TableView<Location> locationTable;
	@FXML
	private TableColumn<Location, String> cityNameColumn;
	@FXML
	private TableColumn<Location, Double> priceColumn;
	@FXML
	private TableColumn<Location, Boolean> editColumn;
	@FXML
	private TableColumn<Location, Boolean> deleteColumn;

	// Data access object for the database
	private LocationDAO locationDAO;
	
	private MainApp mainApp;

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
		// Give the controller data to fill the table view.
		locationDAO = new LocationDAO();
		locationTable.setItems(locationDAO.getLocations());
			
		//Resize the columns (with percentages) when the window is enlarged
		cityNameColumn	.prefWidthProperty().bind(locationTable.widthProperty().multiply(0.396));
		priceColumn		.prefWidthProperty().bind(locationTable.widthProperty().multiply(0.30));
		editColumn		.prefWidthProperty().bind(locationTable.widthProperty().multiply(0.15));
		deleteColumn	.prefWidthProperty().bind(locationTable.widthProperty().multiply(0.15));
		
		// Initialize the table with the four columns.
		cityNameColumn	.setCellValueFactory(cellData -> cellData.getValue().cityNameProperty());
		priceColumn		.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
		editColumn		.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		deleteColumn	.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
	        
		// Create a cell value factory with buttons for each row in the table.
		editColumn	.setCellFactory( locationBooleanTableColumn -> new AddEditCell());
		deleteColumn.setCellFactory( locationBooleanTableColumn -> new AddDeleteCell());    
	}
	
	/** A table cell containing a button for editing a location. */
    private class AddEditCell extends TableCell<Location, Boolean> {
      final Button button = new Button("Edit");

      AddEditCell() {
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  mainApp.showLocationEditDialog(locationTable.getItems().get(getTableRow().getIndex()));
          }
        });
      }
      
      /** Places an edit button in the row only if the row is not empty. */
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
    
    /** A table cell containing a button for editing a location. */
    private class AddDeleteCell extends TableCell<Location, Boolean> {
      final Button button = new Button("Delete");

      AddDeleteCell() {
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  locationTable.getItems().remove(getTableRow().getIndex());
        	  //TODO delete from db
          }
        });
      }
      
      /** Places an edit button in the row only if the row is not empty. */
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
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
