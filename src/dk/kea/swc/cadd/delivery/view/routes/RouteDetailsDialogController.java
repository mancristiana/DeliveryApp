package dk.kea.swc.cadd.delivery.view.routes;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import dk.kea.swc.cadd.delivery.db.OrderDAO;
import dk.kea.swc.cadd.delivery.db.RouteDAO;
import dk.kea.swc.cadd.delivery.model.Order;
import dk.kea.swc.cadd.delivery.model.Route;
import dk.kea.swc.cadd.delivery.view.ui.MyAlert;

public class RouteDetailsDialogController {

	
	@FXML private DatePicker dateField;
	@FXML private TextField driverField;
	@FXML private TextField contactField;
	@FXML private TextField truckField;
	@FXML private TextField storageField;
	@FXML private TextField totalProfitField;
	@FXML private CheckBox finishedBox;
	
    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, String> locationColumn;
    @FXML private TableColumn<Order, Double> quantityColumn;
	@FXML private TableColumn<Order, Double> priceColumn;
	@FXML private TableColumn<Order, Double> profitColumn;
    
    private Route route;
    private Stage dialogStage;
	private TableView<Route> routeTable;
	private int selectedIndex;
	private boolean isFinished;
	{
		isFinished=false;
	}
    
    
    public void setRouteTable(TableView<Route> table) {
		this.routeTable = table;
	}
    public void setSelectedIndex(int selectedIndex) {
    	this.selectedIndex = selectedIndex;
    }
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setFinished(boolean isFinished) {
    	this.isFinished=isFinished;
    	if(isFinished) {
    		dateField.setEditable(false);
    		dateField.setDisable(true);
    		finishedBox.setDisable(true);
    		totalProfitField.setEditable(true);
    	}
    }

    /**
     * Sets the location to be edited in the dialog.
     * 
     * @param location
     */
    public void setRoute(Route route) {
    	this.route = route;
    	ObservableList<Order> orderList = OrderDAO.getOrdersByRoute(route.getRouteID());
    	orderTable.setItems(orderList);
        
    	locationColumn	.setCellValueFactory(cellData -> cellData.getValue().cityNameProperty());
    	quantityColumn	.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
    	priceColumn		.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
    	profitColumn	.setCellValueFactory(cellData -> cellData.getValue().profitProperty().asObject());
    	
    	
    	if(route.getDate() == null)
    		dateField.setValue(LocalDate.now());
    	else dateField.setValue(route.getDate());
    	driverField.setText(route.getDriver().getName());
    	contactField.setText(route.getDriver().getPhone());
        truckField.setText(route.getTruckID()+"");
    	if(orderList.size() > 0)
    		storageField.setText(orderList.get(0).getStorageName()+"");
    	totalProfitField.setText(route.getTotalProfit()+"");
    	finishedBox.setSelected(route.isFinished());
    }


    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
    	if(isFinished){
    		dialogStage.close();
    	} else {
        	if (isInputValid()) {
        		route.setDate(dateField.getValue());
           	 	route.setFinished(finishedBox.isSelected());
           	 	if(finishedBox.isSelected()) {
           	 		routeTable.getItems().remove(selectedIndex);
           	 		RouteDAO.finishRoute(route);
           	 	}
           	 	RouteDAO.updateRoute(route);
                
                
                dialogStage.close();
           }
    	}
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
    	dialogStage.close();
    }
    
	private boolean isInputValid() {
		if (finishedBox.isSelected() && dateField.getValue().isAfter(LocalDate.now())) {
			MyAlert.show(AlertType.ERROR,
					"Invalid Fields",
					"Please correct invalid fields",
					"Can not finish route with future date. \nUnckeck finished or select past/present date.");
            return false;
        }
		return true;
	}
    
}