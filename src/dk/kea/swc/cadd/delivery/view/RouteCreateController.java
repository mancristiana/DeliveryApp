package dk.kea.swc.cadd.delivery.view;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import dk.kea.swc.cadd.delivery.MainApp;
import dk.kea.swc.cadd.delivery.db.DriverDAO;
import dk.kea.swc.cadd.delivery.db.OrderDAO;
import dk.kea.swc.cadd.delivery.db.RouteDAO;
import dk.kea.swc.cadd.delivery.db.TruckDAO;
import dk.kea.swc.cadd.delivery.model.Driver;
import dk.kea.swc.cadd.delivery.model.Order;
import dk.kea.swc.cadd.delivery.model.Route;
import dk.kea.swc.cadd.delivery.model.Truck;

public class RouteCreateController {
	
	@FXML private TableView<Order> orderTable;
	@FXML private TableColumn<Order, Boolean> selectColumn;
	@FXML private TableColumn<Order, Integer> orderIDColumn;
	@FXML private TableColumn<Order, String> cityNameColumn;
	@FXML private TableColumn<Order, Double> quantityColumn;

	// Data access object for the database
	private OrderDAO orderDAO;
	private RouteDAO routeDAO;
	private DriverDAO driverDAO;
	private TruckDAO  truckDAO;
	
	private MainApp mainApp;
	private ObservableList<Order> selected;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public RouteCreateController() {
		
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		selected = FXCollections.observableArrayList();
		
		// Give the controller data to fill the table view.
		orderDAO = new OrderDAO();
		orderTable.setItems(orderDAO.getOrders(false));
			
		routeDAO = new RouteDAO();
		driverDAO = new DriverDAO();
		truckDAO = new TruckDAO();
		
		//Resize the columns (with percentages) when the window is enlarged
		orderIDColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.05));
		cityNameColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.35));
		quantityColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.40));
		
		// Initialize the table with the four columns.
		selectColumn	.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		orderIDColumn	.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty().asObject());
		cityNameColumn	.setCellValueFactory(cellData -> cellData.getValue().cityNameProperty());
		quantityColumn	.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

		// Create a cell value factory with buttons for each row in the table.
		selectColumn	.setCellFactory( orderBooleanTableColumn -> new AddSelectCell());
		
	}
	
	/** A table cell containing a button for editing an order. */
    private class AddSelectCell extends TableCell<Order, Boolean> {
      final CheckBox check = new CheckBox();

      AddSelectCell() {
    	  check.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  if(check.isSelected()){
        		  selected.add(orderTable.getItems().get(getTableRow().getIndex()));  
        	  }
        	  else{
        		  selected.remove(orderTable.getItems().get(getTableRow().getIndex()));
        	  }
          }
        });
      }
      
      /** Places an edit button in the row only if the row is not empty. */
      @Override protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
          setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
          setGraphic(check);
        } else {
          setGraphic(null);
        }
      }
      
    }
    
    /**
     * Called when the user clicks create.
     */
    @FXML
    private void handleCreate() {
    	if(selected.size() == 0 ){
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Ops! You didn't select any orders.");

            alert.showAndWait();
    	} else {
    		while(selected.size() != 0){
    			ObservableList<Object> list = routeDAO.getDriverAndTruck();
    			if(list.size()==0){
//    	    		 Show the error .
    	            Alert alert = new Alert(AlertType.ERROR);
    	            alert.initOwner(mainApp.getPrimaryStage());
    	            alert.setTitle("Ops");
    	            alert.setHeaderText("There are no drivers or trucks available");

    	            alert.showAndWait();
    	            return;
    			}
	    		Driver driver = (Driver) list.get(0);
	    		Truck truck = (Truck) list.get(1);
	    		
	    		Route route = routeDAO.createRoute(driver.getDriverId(),truck.getTruckID());
	    		
    			double sum = selected.get(0).getQuantity();
    			
    			selected.get(0).setRouteID(route.getRouteID());
    			orderDAO.updateOrder(selected.get(0));
	    		    			
    			for(int i = 1; i<selected.size();i++){
    				System.out.println("" + (sum+selected.get(i).getQuantity() <= 21.0));
    				if(sum+selected.get(i).getQuantity() < 21.0){
    					sum+=selected.get(i).getQuantity();
    	    			selected.get(i).setRouteID(route.getRouteID());
    	    			orderDAO.updateOrder(selected.get(i));
    				}
    			}
	    		
	    		driver.setAvailable(false);
	    		driverDAO.updateDriver(driver);
	    		truck.setAvailable(false);
	    		truckDAO.updateTruck(truck);
	    		
    			for(int i = 0; i<selected.size();i++){
    				if(selected.get(i).getRouteID() != 0){
    					selected.remove(i);
    					i=-1;
    				}
    			}
    		}
    		
    		mainApp.showRouteOverview();   
    	}
    }
    
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
