package dk.kea.swc.cadd.delivery.view;

import dk.kea.swc.cadd.delivery.db.OrderDAO;
import dk.kea.swc.cadd.delivery.model.Order;
import dk.kea.swc.cadd.delivery.model.Route;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RouteDetailsDialogController {

	@FXML private TextField dateField;
	@FXML private TextField driverField;
	@FXML private TextField truckField;
	@FXML private TextField storageField;
	@FXML private CheckBox finishedBox;
	
    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, String> locationColumn;
    @FXML private TableColumn<Order, Double> quantityColumn;

    private Stage 	dialogStage;
    private OrderDAO orderDAO;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	orderDAO = new OrderDAO();
    	
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the location to be edited in the dialog.
     * 
     * @param location
     */
    public void setRoute(Route route) {
    	ObservableList<Order> orderList = orderDAO.getOrdersByRoute(route.getRouteID());
    	orderTable.setItems(orderList);
        
    	locationColumn	.setCellValueFactory(cellData -> cellData.getValue().cityNameProperty());
    	quantityColumn	.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
		
    	driverField.setText(route.getDriverID()+"");
        truckField.setText(route.getTruckID()+"");
    	if(orderList.size() > 0)
    		storageField.setText(orderList.get(0).getStorageName()+"");
    	finishedBox.setSelected(route.isFinished());
    }


    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
    	dialogStage.close();
    }
    
}