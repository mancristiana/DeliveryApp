package dk.kea.swc.cadd.delivery.view.orders;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import dk.kea.swc.cadd.delivery.MainApp;
import dk.kea.swc.cadd.delivery.db.OrderDAO;
import dk.kea.swc.cadd.delivery.model.Order;
import dk.kea.swc.cadd.delivery.view.ui.ButtonCell;

public class OrderOverviewController {
	
	@FXML private TableView<Order> orderTable;
	@FXML private TableColumn<Order, Integer> orderIDColumn;
	@FXML private TableColumn<Order, String> cityNameColumn;
	@FXML private TableColumn<Order, Double> quantityColumn;
	@FXML private TableColumn<Order, Boolean> editColumn;
	@FXML private TableColumn<Order, Boolean> deleteColumn;
	

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public OrderOverviewController() {
		
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
		// Give the controller data to fill the table view.
		orderTable.setItems(OrderDAO.getOrders(false));
			
		//Resize the columns (with percentages) when the window is enlarged
		orderIDColumn	.prefWidthProperty().bind(orderTable.widthProperty().subtract(130).multiply(0.20));
		cityNameColumn	.prefWidthProperty().bind(orderTable.widthProperty().subtract(130).multiply(0.35));
		quantityColumn	.prefWidthProperty().bind(orderTable.widthProperty().subtract(130).multiply(0.45));
		
		// Initialize the table with the four columns.
		orderIDColumn	.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty().asObject());
		cityNameColumn	.setCellValueFactory(cellData -> cellData.getValue().cityNameProperty());
		quantityColumn	.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
     
		// Create a cell value factory with buttons for each row in the table.
		editColumn		.setCellFactory(cellData -> new ButtonCell<Order>("edit-button"){
			@Override
			public void onClick() {
				 showOrderEditDialog(orderTable.getItems().get(getTableRow().getIndex()));
			}});
		deleteColumn	.setCellFactory(cellData -> new ButtonCell<Order>("delete-button"){
			@Override
			public void onClick(){
		     	  int selectedIndex = getTableRow().getIndex();
	        	  OrderDAO.removeOrder(orderTable.getItems().get(selectedIndex));
	        	  orderTable.getItems().remove(selectedIndex);
			}});
	}

    public void showOrderEditDialog(Order order) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/orders/OrderEditDialog.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Order");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            
            // Setting the minimum width and height
            dialogStage.setMinWidth(page.getMinWidth()+17.25);
            dialogStage.setMinHeight(page.getMinHeight()+46.25);
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Set the location into the controller.
            OrderEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOrder(order);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
