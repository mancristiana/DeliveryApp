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
import dk.kea.swc.cadd.delivery.db.RouteDAO;
import dk.kea.swc.cadd.delivery.model.Driver;
import dk.kea.swc.cadd.delivery.model.Route;
import dk.kea.swc.cadd.delivery.util.MapOfRoute;

public class RouteOverviewController {
	
	@FXML private TableView<Route> routeTable;
	@FXML private TableColumn<Route, Integer> 	routeIDColumn;
	@FXML private TableColumn<Route, Integer> 	driverColumn;
	@FXML private TableColumn<Route, Integer> 	truckIDColumn;
	@FXML private TableColumn<Route, Boolean> 	detailsColumn;
	@FXML private TableColumn<Route, Boolean> 	mapColumn;
	@FXML private TableColumn<Route, Boolean> 	deleteColumn;


	// Data access object for the database
	private RouteDAO routeDAO;
	
	private MainApp mainApp; //TODO

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public RouteOverviewController() {
		
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Give the controller data to fill the table view
		routeDAO = new RouteDAO();
		routeTable.setItems(routeDAO.getRoutes(false));
			
		// Resize the columns (with percentages) when the window is enlarged 
		routeIDColumn	.prefWidthProperty().bind(routeTable.widthProperty().multiply(0.05));
		driverColumn	.prefWidthProperty().bind(routeTable.widthProperty().multiply(0.30));
		truckIDColumn	.prefWidthProperty().bind(routeTable.widthProperty().multiply(0.05));
		detailsColumn 	.prefWidthProperty().bind(routeTable.widthProperty().multiply(0.20));
		mapColumn		.prefWidthProperty().bind(routeTable.widthProperty().multiply(0.20));
		deleteColumn 	.prefWidthProperty().bind(routeTable.widthProperty().multiply(0.20));
		
		// Initialize the table with the four columns
		routeIDColumn	.setCellValueFactory(cellData -> cellData.getValue().routeIDProperty().asObject());
		driverColumn	.setCellValueFactory(cellData -> cellData.getValue().driverIDProperty().asObject());
		truckIDColumn	.setCellValueFactory(cellData -> cellData.getValue().truckIDProperty().asObject());
		detailsColumn	.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		mapColumn		.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		deleteColumn	.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		
		// Create a cell value factory with buttons for each row in the table
		detailsColumn	.setCellFactory( routeBooleanTableColumn -> new AddDetailsCell());
		mapColumn		.setCellFactory( routeBooleanTableColumn -> new AddMapCell());
		deleteColumn	.setCellFactory( routeBooleanTableColumn -> new AddDeleteCell());
	}
	
	/** 
	 * A table cell containing a button for showing details of a route. 
	 */
    private class AddDetailsCell extends TableCell<Route, Boolean> {
      final Button button = new Button("View Details");

      AddDetailsCell() {
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	 // showDetailsDialog(routeTable.getItems().get(getTableRow().getIndex()));
          }
        });
      }
      
      /** 
       * Places a button in the row only if the row is not empty. 
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
	 * A table cell containing a button for opening a map showing the route. 
	 */
    private class AddMapCell extends TableCell<Route, Boolean> {
      final Button button = new Button("Show map");

      AddMapCell() {
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  int selectedIndex = getTableRow().getIndex();
        	  Route selectedRoute = routeTable.getItems().get(selectedIndex);
        	  
        	  new MapOfRoute(selectedRoute);
          }
        });
      }
      
      /** 
       * Places a button in the row only if the row is not empty. 
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
	 * A table cell containing a button for opening a map showing the route. 
	 */
    private class AddDeleteCell extends TableCell<Route, Boolean> {
      final Button button = new Button("Delete");

      AddDeleteCell() {
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  int selectedIndex = getTableRow().getIndex();
        	  routeTable.getItems().remove(selectedIndex);
        	  //TO DO DATABASE
          }
        });
      }
      
      /** 
       * Places a button in the row only if the row is not empty. 
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
            dialogStage.setTitle("Edit Driver");
            dialogStage.initModality(Modality.WINDOW_MODAL); //TODO
            dialogStage.initOwner(mainApp.getPrimaryStage());
            dialogStage.setResizable(false);
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the driver object into the controller
            DriverDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDriver(driver);

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
