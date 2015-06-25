package dk.kea.swc.cadd.delivery.view;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import dk.kea.swc.cadd.delivery.view.ui.MyAlert;

public class RouteCreateController {
	
	@FXML private TableView<Order> orderTable;
	@FXML private TableColumn<Order, Boolean> selectColumn;
	@FXML private TableColumn<Order, Integer> orderIDColumn;
	@FXML private TableColumn<Order, String> cityNameColumn;
	@FXML private TableColumn<Order, Double> quantityColumn;

	// Data access object for the database
	private OrderDAO orderDAO;
	private RouteDAO routeDAO;
	private TruckDAO  truckDAO;
	
	private MainApp mainApp;
	private ObservableList<Order> selectedItems;

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
		selectedItems = FXCollections.observableArrayList();
		
		// Give the controller data to fill the table view.
		orderDAO = new OrderDAO();
		orderTable.setItems(orderDAO.getOrders(false));
			
		routeDAO = new RouteDAO();
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
        		  selectedItems.add(orderTable.getItems().get(getTableRow().getIndex()));  
        	  }
        	  else{
        		  selectedItems.remove(orderTable.getItems().get(getTableRow().getIndex()));
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
    	//If no orders/items selected, show an alert
    	if(selectedItems.size() == 0 ){ 
    		showAlert(
    				AlertType.ERROR,
    				"Invalid Fields",
    				"Please correct invalid fields",
    				"Ops! You didn't select any orders.");
    	} 
    	//If there are selected orders/items
    	else {
    		// Start creating as many routes as possible
    		while(selectedItems.size() != 0){
    			// Get one available driver and truck
    			ObservableList<Object> driverTruck = routeDAO.getDriverAndTruck(); 
    			
    			// If there aren't available ones, show error message and return
    			if(driverTruck.size() == 0){
    				showAlert(
    						AlertType.ERROR, 
    						"Ops",
    						"",
    						"There are no drivers or trucks available");
    	            return;
    			}
    			
    			// Create a new route using the available driver and truck 
	    		Driver driver 	= (Driver) driverTruck.get(0);
	    		Truck truck 	= (Truck)  driverTruck.get(1);
	    
	    		Route route = routeDAO.createRoute(driver.getDriverId(), truck.getTruckID());
	    		
	    		// Set availability to false so the same driver and truck won't be selected again for the next route
	    		driver.setAvailable(false);
	    		DriverDAO.updateDriver(driver);
	    		truck.setAvailable(false);
	    		truckDAO.updateTruck(truck);
	    		
	    		// Start adding orders to the route
	    		
	    		double totalQuantity = 0; //selectedItems.get(0).getQuantity(); //get quantity of the first selected item
    			String storage = null;
	    		
    			selectedItems.get(0).setRouteID(route.getRouteID()); 	
    			orderDAO.updateOrder(selectedItems.get(0));			 	

    			// Try to add as many selected items to the route as possible
    			for(int i = 0; i < selectedItems.size(); i++){
    				Order order = selectedItems.get(i);
    				
    				// Check if order fits and if it has the same storage as the route, skip order otherwise
    				if(totalQuantity + order.getQuantity() <= truck.getCapacity() && (order.getStorageName().equals(storage) || storage == null)){
    					totalQuantity += order.getQuantity();
    					if (storage == null) storage = order.getStorageName();
    					order.setRouteID(route.getRouteID());	//add it to the route by setting route id of the order
    	    			orderDAO.updateOrder(order);				//make the changes in the DB
    				}
    			}
	    		
    			// Remove added orders the from the selectedItems  list
    			for(int i = 0; i < selectedItems.size(); i++){
    				if(selectedItems.get(i).getRouteID() != 0){
    					selectedItems.remove(i);
    					i=-1;
    				}
    			}
    			
    			// Show success message for a route
    			String details = "Route ID " + route.getRouteID() 	+ "\n" +
    							 "Driver " 	 + driver.getName()		+ "\n" +
    							 "Truck  " 	 + truck.getTruckID() + " (capacity " + truck.getCapacity() + ")\n";
    			showAlert(
    					AlertType.INFORMATION,
    					"Success!",
    					"New route was successfully created",
    					details);
    			
    		}
    		
    		mainApp.showRouteOverview();   
    	}
    }
    
    private void showAlert(AlertType type, String title, String header, String content) {
    	MyAlert alert = new MyAlert(type,title,header,content);
        alert.showAndWait();
    }
    
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
