package dk.kea.swc.cadd.delivery.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

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
import dk.kea.swc.cadd.delivery.db.StorageDAO;
import dk.kea.swc.cadd.delivery.db.TruckDAO;
import dk.kea.swc.cadd.delivery.model.Driver;
import dk.kea.swc.cadd.delivery.model.Order;
import dk.kea.swc.cadd.delivery.model.Route;
import dk.kea.swc.cadd.delivery.model.Storage;
import dk.kea.swc.cadd.delivery.model.Truck;
import dk.kea.swc.cadd.delivery.view.ui.MyAlert;

public class RouteCreateController {
	
	@FXML private TableView<Order> orderTable;
	@FXML private TableColumn<Order, Boolean> selectColumn;
	@FXML private TableColumn<Order, Integer> orderIDColumn;
	@FXML private TableColumn<Order, String> cityNameColumn;
	@FXML private TableColumn<Order, Double> quantityColumn;
	@FXML private TableColumn<Order, Double> priceColumn;
	@FXML private TableColumn<Order, Double> profitColumn;
	
	private ObservableList<Order> selectedItems;
	private Map<String, Double> storageMap = new HashMap<>(); 
	private ObservableList<Storage> storageList = StorageDAO.getStorages(); 

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
		orderTable.setItems(OrderDAO.getOrders(false));
		
		//Resize the columns (with percentages) when the window is enlarged
		orderIDColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.05));
		cityNameColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.35));
		quantityColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.20));
		priceColumn		.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.20));
		profitColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.20));
		
		// Initialize the table with the four columns.
		selectColumn	.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		orderIDColumn	.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty().asObject());
		cityNameColumn	.setCellValueFactory(cellData -> cellData.getValue().cityNameProperty());
		quantityColumn	.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
		priceColumn		.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
		profitColumn	.setCellValueFactory(cellData -> cellData.getValue().profitProperty().asObject());

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
      
      /*
       * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Cell.html#updateItem-T-boolean-
       * The updateItem method should not be called by developers, but it is the best method for developers to override to allow for them to customise the visuals of the cell. 
       * To clarify, developers should never call this method in their code (they should leave it up to the UI control, such as the ListView control) to call this method. 
       * However, the purpose of having the updateItem method is so that developers, when specifying custom cell factories (again, like the ListView cell factory), 
       * the updateItem method can be overridden to allow for complete customisation of the cell.
       * 
       * It is very important that subclasses of Cell override the updateItem method properly, 
       * as failure to do so will lead to issues such as blank cells or cells with unexpected 
       * content appearing within them. Here is an example of how to properly override the updateItem method:
       
        protected void updateItem(T item, boolean empty) {
        		super.updateItem(item, empty);

    	      if (empty || item == null) {
    	          setText(null);
    	          setGraphic(null);
    	      } else {
    	         setText(item.toString());
    	      }
     	}
       */
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
    		// Create a map with storage info
    		for (Storage s : storageList) {
    			 storageMap.put(s.getCityName(), s.getAvailableQuantity());
    		}
    		
    		// Start creating as many routes as possible
    		while(selectedItems.size() != 0){
    			// Get one available driver and truck
    			ObservableList<Object> driverTruck = RouteDAO.getDriverAndTruck(); 
    			
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
	    
	    		Route route = RouteDAO.createRoute(driver, truck);
	    		
	    		// Set availability to false so the same driver and truck won't be selected again for the next route
	    		driver.setAvailable(false);
	    		DriverDAO.updateDriver(driver);
	    		truck.setAvailable(false);
	    		TruckDAO.updateTruck(truck);
	    		
	    		// Start adding orders to the route
	    		
	    		double totalQuantity = 0; //selectedItems.get(0).getQuantity(); //get quantity of the first selected item
    			String storage = null;
	    		
    			selectedItems.get(0).setRouteID(route.getRouteID()); 	
    			OrderDAO.updateOrder(selectedItems.get(0));			 	

    			// Try to add as many selected items to the route as possible
    			for(int i = 0; i < selectedItems.size(); i++){
    				Order order = selectedItems.get(i);
    				
    				// Check if order fits and if it has the same storage as the route, skip order otherwise
    				if(isValidOrder(order, truck, totalQuantity, storage)){
    					totalQuantity += order.getQuantity();
    					if (storage == null) storage = order.getStorageName();
    					order.setRouteID(route.getRouteID());	//add it to the route by setting route id of the order
    					OrderDAO.updateOrder(order);				//make the changes in the DB
    				}
    			}
	    		
    			Set<String> cityNames = new HashSet<>();
    			
    			// Remove added orders the from the selectedItems  list
    			for(int i = 0; i < selectedItems.size(); i++){
    				if(selectedItems.get(i).getRouteID() != 0){
    					cityNames.add(selectedItems.get(i).getCityName()); // Put city names in a set (for display)
    					selectedItems.remove(i);
    					i=-1; //TODO WTF is happening here??
    				}
    			}
    			
    			// Show success message for a route
    			String details = "Route ID " + route.getRouteID() 	+ "\n" +
    							 "Driver " 	 + driver.getName()		+ "\n" +
    							 "Truck  " 	 + truck.getTruckID() + " (capacity " + truck.getCapacity() + ")\n" +
    							 "Locations " 	 + cityNames.toString().replaceAll("[^a-zA-Z ,]", "");
    			showAlert(
    					AlertType.INFORMATION,
    					"Success!",
    					"New route was successfully created",
    					details); //TODO Alert cleanup
    			
    		}
    		MainApp.showPage("RouteOverview");   
    	}
    }
    
    private boolean isValidOrder(Order order, Truck truck, double totalQuantity, String storage) {
    	boolean result = true;
    	result &= totalQuantity + order.getQuantity() <= truck.getCapacity() ; // Check if order fits in the truck
    	result &= (order.getStorageName().equals(storage) || storage == null); // Check that all orders have the same storage (if this is the first order, then storage is still null)
    	result &=  storageMap.get(order.getStorageName()) >= totalQuantity + order.getQuantity(); // Check if there is enough quantity in storage for the order
    	return result;
    }
    private void showAlert(AlertType type, String title, String header, String content) {
    	MyAlert.show(type,title,header,content);
    }
}
