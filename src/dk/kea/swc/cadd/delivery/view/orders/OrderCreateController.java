package dk.kea.swc.cadd.delivery.view.orders;

import dk.kea.swc.cadd.delivery.db.LocationDAO;
import dk.kea.swc.cadd.delivery.db.OrderDAO;
import dk.kea.swc.cadd.delivery.model.Order;
import dk.kea.swc.cadd.delivery.view.ui.MyAlert;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class OrderCreateController {

    @FXML private ChoiceBox<String> cityNameField;
    @FXML private TextField quantityField;
    @FXML private Label confirmationLabel;
    private int orderCount;

	private Order	order;
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	orderCount = 0;
    	order = new Order();
    	cityNameField.setItems(LocationDAO.getLocationNames());
    	quantityField.setText("");
    	confirmationLabel.setVisible(false);
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
    	if (isInputValid()) {
            order.setCityName(cityNameField.getSelectionModel().getSelectedItem().toString());
            order.setQuantity(Double.parseDouble(quantityField.getText()));
            
            String dbMessage = OrderDAO.createOrder(order);
            if(dbMessage.isEmpty()) {
            	orderCount++;
            	confirmationLabel.setTextFill(Color.GREEN);
            	if(orderCount==1) 	confirmationLabel.setText("1 order was successfully placed");
            	else 				confirmationLabel.setText(orderCount+" orders were successfully placed");
            } else {
            	confirmationLabel.setTextFill(Color.RED);
            	confirmationLabel.setText(dbMessage);	
            }
            confirmationLabel.setVisible(true);
        }
    }

    /**
     * Called when the user clicks clear.
     */
    @FXML
    private void handleClear() {
    	cityNameField.getSelectionModel().select(null);
    	quantityField.setText("");
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    @FXML
    private boolean isInputValid() {
    	boolean isValid = true;
        
    	if (cityNameField.getSelectionModel().getSelectedItem() == null) {
        	MyAlert.show(
					AlertType.ERROR,
					"Invalid Fields",
					"Please correct invalid fields",
					"City is not selected");
        	isValid = false;
        }
    	
    	if (quantityField.getText().isEmpty()) {
        	MyAlert.show(
					AlertType.ERROR,
					"Invalid Fields",
					"Please correct invalid fields",
					"Quantity can't be null");
        	isValid = false;
        } else {
        	try {
	            Double.parseDouble(quantityField.getText());
	        } catch (NumberFormatException e) {
	        	MyAlert.show(
						AlertType.ERROR,
						"Invalid Fields",
						"Please correct invalid fields",
						"Quantity must be a number");
	        	isValid = false;
	        }
        	
        	if( Double.parseDouble(quantityField.getText()) > 21) {
	        	MyAlert.show(
						AlertType.ERROR,
						"Invalid Fields",
						"Please correct invalid fields",
						"Quantity can't be greater than 21 tons");
	        	isValid = false;
        	}
        }
    	
        return isValid;        	
    }
}