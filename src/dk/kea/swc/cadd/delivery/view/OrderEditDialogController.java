package dk.kea.swc.cadd.delivery.view;

import dk.kea.swc.cadd.delivery.db.LocationDAO;
import dk.kea.swc.cadd.delivery.db.OrderDAO;
import dk.kea.swc.cadd.delivery.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OrderEditDialogController {

	@FXML private ChoiceBox<String> cityNameField;
    @FXML private TextField quantityField;

    private Stage 	dialogStage;
    private Order 	order;
    private LocationDAO locationDAO;
    private OrderDAO orderDAO;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	locationDAO = new LocationDAO();
    	orderDAO = new OrderDAO();
    	cityNameField.setItems(locationDAO.getLocationNames());
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
    public void setOrder(Order order) {
        this.order = order;
        cityNameField.getSelectionModel().select(order.getCityName());
        quantityField.setText(order.getQuantity()+"");
    }


    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	 order.setCityName(cityNameField.getSelectionModel().getSelectedItem().toString());
             order.setQuantity(Double.parseDouble(quantityField.getText()));
             
             orderDAO.updateOrder(order);
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (cityNameField.getSelectionModel().getSelectedItem().toString() == null) {
            errorMessage += "Location must be selected!\n"; 
        }
        if (quantityField.getText() == null || quantityField.getText().length() == 0) {
            errorMessage += "Quantity can't be null!\n"; 
        } else {
            // Try to parse the quantity to double.
            try {
                Double.parseDouble(quantityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid quantity (must be a number)!\n"; 
            }
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}