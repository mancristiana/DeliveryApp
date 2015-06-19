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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import dk.kea.swc.cadd.delivery.MainApp;
import dk.kea.swc.cadd.delivery.db.OrderDAO;
import dk.kea.swc.cadd.delivery.model.Order;

public class OrderOverviewController {
	
	@FXML private TableView<Order> orderTable;
	@FXML private TableColumn<Order, Integer> orderIDColumn;
	@FXML private TableColumn<Order, String> cityNameColumn;
	@FXML private TableColumn<Order, Double> quantityColumn;
	@FXML private TableColumn<Order, Boolean> editColumn;
	@FXML private TableColumn<Order, Boolean> deleteColumn;

	// Data access object for the database
	private OrderDAO orderDAO;
	
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
		orderTable.setItems(orderDAO.getOrders(false));
			
		//Resize the columns (with percentages) when the window is enlarged
		orderIDColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.05));
		cityNameColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.35));
		quantityColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.40));
		editColumn		.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.10));
		deleteColumn	.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.10));
		
		// Initialize the table with the four columns.
		orderIDColumn	.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty().asObject());
		cityNameColumn	.setCellValueFactory(cellData -> cellData.getValue().cityNameProperty());
		quantityColumn	.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
		editColumn		.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
		deleteColumn	.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
	         
		// Create a cell value factory with buttons for each row in the table.
		editColumn	.setCellFactory( orderBooleanTableColumn -> new AddEditCell());
		deleteColumn.setCellFactory( orderBooleanTableColumn -> new AddDeleteCell());    
	}
	
	/** A table cell containing a button for editing an order. */
    private class AddEditCell extends TableCell<Order, Boolean> {
      final Button button = new Button("");
      Image image = new Image(getClass().getResourceAsStream("images/edit.png"));
      
      AddEditCell() {
    	  button.setGraphic(new ImageView(image));
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  showOrderEditDialog(orderTable.getItems().get(getTableRow().getIndex()));
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
      final Button button = new Button("");
      Image image = new Image(getClass().getResourceAsStream("images/delete.png"));
      
      AddDeleteCell() {
    	  button.setGraphic(new ImageView(image));
    	  button.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent actionEvent) {
        	  int selectedIndex = getTableRow().getIndex();
        	  orderDAO.removeOrder(orderTable.getItems().get(selectedIndex));
        	  orderTable.getItems().remove(selectedIndex);
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
    
    public void showOrderEditDialog(Order order) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OrderEditDialog.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Order");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            
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
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
