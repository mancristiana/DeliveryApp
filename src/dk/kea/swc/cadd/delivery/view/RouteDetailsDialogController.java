package dk.kea.swc.cadd.delivery.view;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import dk.kea.swc.cadd.delivery.db.OrderDAO;
import dk.kea.swc.cadd.delivery.model.Order;
import dk.kea.swc.cadd.delivery.model.Route;

public class RouteDetailsDialogController {

	@FXML private DatePicker dateField;
	@FXML private TextField driverField;
	@FXML private TextField contactField;
	@FXML private TextField truckField;
	@FXML private TextField storageField;
	@FXML private CheckBox finishedBox;
	
    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, String> locationColumn;
    @FXML private TableColumn<Order, Double> quantityColumn;

    private Stage 	dialogStage;
    
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
    	ObservableList<Order> orderList = OrderDAO.getOrdersByRoute(route.getRouteID());
    	orderTable.setItems(orderList);
        
    	locationColumn	.setCellValueFactory(cellData -> cellData.getValue().cityNameProperty());
    	quantityColumn	.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
		
    	if(route.getDate() == null)
    		dateField.setValue(LocalDate.now());
    	else dateField.setValue(route.getDate());
    	driverField.setText(route.getDriver().getName());
    	contactField.setText(route.getDriver().getPhone());
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