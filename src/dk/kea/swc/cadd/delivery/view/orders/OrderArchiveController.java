package dk.kea.swc.cadd.delivery.view.orders;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import dk.kea.swc.cadd.delivery.db.OrderDAO;
import dk.kea.swc.cadd.delivery.model.Order;
import dk.kea.swc.cadd.delivery.view.ui.ButtonCell;

public class OrderArchiveController {
	
	@FXML private TableView<Order> orderTable;
	@FXML private TableColumn<Order, Integer> orderIDColumn;
	@FXML private TableColumn<Order, Integer> routeIDColumn;
	@FXML private TableColumn<Order, String> cityNameColumn;
	@FXML private TableColumn<Order, Double> quantityColumn;
	@FXML private TableColumn<Order, Boolean> deleteColumn;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public OrderArchiveController() {
		
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Give the controller data to fill the table view.
		orderTable.setItems(OrderDAO.getOrders(true));
			
		//Resize the columns (with percentages) when the window is enlarged
		orderIDColumn	.prefWidthProperty().bind(orderTable.widthProperty().subtract(75).multiply(0.15));
		routeIDColumn	.prefWidthProperty().bind(orderTable.widthProperty().subtract(75).multiply(0.15));
		cityNameColumn	.prefWidthProperty().bind(orderTable.widthProperty().subtract(75).multiply(0.30));
		quantityColumn	.prefWidthProperty().bind(orderTable.widthProperty().subtract(75).multiply(0.40));
		
		// Initialize the table with the four columns.
		orderIDColumn	.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty().asObject());
		routeIDColumn	.setCellValueFactory(cellData -> cellData.getValue().routeIDProperty().asObject());
		cityNameColumn	.setCellValueFactory(cellData -> cellData.getValue().cityNameProperty());
		quantityColumn	.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
		
		// Create a cell value factory with buttons for each row in the table.
		deleteColumn	.setCellFactory(cellData -> new ButtonCell<Order>("delete-button"){
			@Override
			public void onClick(){
	        	  int selectedIndex = getTableRow().getIndex();
	        	  OrderDAO.removeOrder(orderTable.getItems().get(selectedIndex));
	        	  orderTable.getItems().remove(selectedIndex);
			}});
	}
}