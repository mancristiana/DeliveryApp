package dk.kea.swc.cadd.delivery.view;

import dk.kea.swc.cadd.delivery.MainApp;
import dk.kea.swc.cadd.delivery.db.LocationDAO;
import dk.kea.swc.cadd.delivery.model.Location;
import dk.kea.swc.cadd.delivery.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OrderCreateController {

    @FXML private ChoiceBox<String> cityNameField;
    @FXML private TextField quantityField;

	private MainApp	mainApp;
	private Order	order;
	private LocationDAO locationDAO;
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	locationDAO = new LocationDAO();
    	cityNameField.setItems(locationDAO.getLocationNames());
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            order.setCityName(cityNameField.getSelectionModel().toString());
            order.setQuantity(Double.parseDouble(quantityField.getText()));
            
            //TODO edit in the database
        }
    }

    /**
     * Called when the user clicks clear.
     */
    @FXML
    private void handleClear() {

    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
//        String errorMessage = "";
//
//        if (cityNameField.getText() == null || cityNameField.getText().length() == 0) {
//            errorMessage += "No valid first name!\n"; 
//        }
//        if (priceField.getText() == null || priceField.getText().length() == 0) {
//            errorMessage += "No valid last name!\n"; 
//        } else {
//            // try to parse the postal code into an int.
//            try {
//                Double.parseDouble(priceField.getText());
//            } catch (NumberFormatException e) {
//                errorMessage += "No valid postal code (must be an integer)!\n"; 
//            }
//        }
//
//
//        if (errorMessage.length() == 0) {
//            return true;
//        } else {
//            // Show the error message.
//            Alert alert = new Alert(AlertType.ERROR);
//            alert.initOwner(dialogStage);
//            alert.setTitle("Invalid Fields");
//            alert.setHeaderText("Please correct invalid fields");
//            alert.setContentText(errorMessage);
//
//            alert.showAndWait();
//
//            return false;
//        }
    	return true;
    }
    
    public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}