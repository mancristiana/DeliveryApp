package dk.kea.swc.cadd.delivery.view.orders;

import dk.kea.swc.cadd.delivery.db.LocationDAO;
import dk.kea.swc.cadd.delivery.db.OrderDAO;
import dk.kea.swc.cadd.delivery.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class OrderCreateController {

    @FXML private ChoiceBox<String> cityNameField;
    @FXML private TextField quantityField;
    @FXML private Label cityNameValidationLabel;
    @FXML private Label quantityValidationLabel;
    @FXML private Label greatSuccessLabel;

	private Order	order;
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	order = new Order();
    	cityNameField.setItems(LocationDAO.getLocationNames());
    	quantityField.setText("");
    	cityNameValidationLabel.setVisible(false);
    	quantityValidationLabel.setVisible(false);
    	greatSuccessLabel.setVisible(false);
    	
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
    	 greatSuccessLabel.setVisible(false);
    	if (isInputValid()) {
            order.setCityName(cityNameField.getSelectionModel().getSelectedItem().toString());
            order.setQuantity(Double.parseDouble(quantityField.getText()));
            
            String dbMessage = OrderDAO.createOrder(order);
            if(dbMessage != null) {
            	greatSuccessLabel.setStyle("-fx-color: red");
            	greatSuccessLabel.setText(dbMessage);
            	
            } else {
            	greatSuccessLabel.setStyle("-fx-color: #30ab0a");
            	greatSuccessLabel.setText("New order was successfully placed");
            }
        	cityNameValidationLabel.setVisible(false);
        	quantityValidationLabel.setVisible(false);
            greatSuccessLabel.setVisible(true);
        }
    }

    /**
     * Called when the user clicks clear.
     */
    @FXML
    private void handleClear() {
    	cityNameField.getSelectionModel().select(null);
    	quantityField.setText("");
    	cityNameValidationLabel.setVisible(false);
    	quantityValidationLabel.setVisible(false);
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
    		cityNameValidationLabel.setText("Location is not selected!");
        	cityNameValidationLabel.setVisible(true);
        	isValid = false;
        }
    	
    	if (quantityField.getText() == null) {
    		quantityValidationLabel.setText("Quantity can't be null!");
    		quantityValidationLabel.setVisible(true);
        	isValid = false;
        } else {
        	try {
	            Double.parseDouble(quantityField.getText());
	        } catch (NumberFormatException e) {
	        	quantityValidationLabel.setText("Quantity must be a number!");
	        	quantityValidationLabel.setVisible(true);
	        	isValid = false;
	        }
        	
        	if( Double.parseDouble(quantityField.getText()) > 21) {
        		quantityValidationLabel.setText("Quantity can't be larger than 21 tons!");
	        	quantityValidationLabel.setVisible(true);
	        	isValid = false;
        	}
        }
    	
        return isValid;        	
    }
    
}