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
import dk.kea.swc.cadd.delivery.db.OrderDAO;
import dk.kea.swc.cadd.delivery.model.Order;

public class OrderOverviewController {
	
	@FXML
	private TableView<Order> orderTable;
	@FXML
	private TableColumn<Order, Integer> orderIDColumn;
	@FXML
	private TableColumn<Order, Integer> locationIDColumn;
	@FXML
	private TableColumn<Order, Double> quantityColumn;
	@FXML
	private TableColumn<Order, Boolean> editColumn;
	@FXML
	private TableColumn<Order, Boolean> deleteColumn;

	// Data access object for the database
	private OrderDAO orderDAO;
	private LocationDAO locationDAO;
	
	private MainApp mainApp;

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
		orderDAO = new OrderDAO();
		orderTable.setItems(orderDAO.getOrders());
		
		locationDAO = new LocationDAO();
			
		//Resize the columns (with percentages) when the window is enlarged
		orderIDColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.05));
		locationIDColumn.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.35));
		quantityColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.20));
		editColumn		.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.20));
		deleteColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.20));
		
		// Initialize the table with the four columns.
		orderIDColumn	.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty().asObject());
		locationIDColumn.setCellValueFactory(cellData -> cellData.getValue().locationIDProperty().asObject());
		quantityColumn	.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
		editColumn		.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		deleteColumn	.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
	         
		// Create a cell value factory with buttons for each row in the table.
		editColumn	.setCellFactory( orderBooleanTableColumn -> new AddEditCell());
		deleteColumn.setCellFactory( orderBooleanTableColumn -> new AddDeleteCell());    
	}
	
	/** A table cell containing a button for editing an order. */
    private class AddEditCell extends TableCell<Order, Boolean> {
      final Button button = new Button("Edit");

      AddEditCell() {
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	//  mainApp.showLocationEditDialog(orderTable.getItems().get(getTableRow().getIndex()));
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
    
    /** A table cell containing a button for editing an order. */
    private class AddDeleteCell extends TableCell<Order, Boolean> {
      final Button button = new Button("Delete");

      AddDeleteCell() {
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  orderTable.getItems().remove(getTableRow().getIndex());
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
