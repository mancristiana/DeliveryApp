package dk.kea.swc.cadd.delivery.view;

import dk.kea.swc.cadd.delivery.db.LocationDAO;
import dk.kea.swc.cadd.delivery.db.StorageDAO;
import dk.kea.swc.cadd.delivery.model.Location;
import dk.kea.swc.cadd.delivery.view.ui.MyAlert;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LocationEditDialogController {

    @FXML private TextField cityNameField;
    @FXML private ChoiceBox<String> storageNameField;
    @FXML private TextField priceField;

    private Stage 		dialogStage;
    private Location 	location;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	storageNameField.setItems(StorageDAO.getStorageNames());
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
    public void setLocation(Location location) {
        this.location = location;

        cityNameField.setText(location.getCityName());
        cityNameField.setEditable(false);
        storageNameField.getSelectionModel().select(location.getStorageName());
        priceField.setText(location.getPrice().toString());
    }


    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            location.setPrice(Double.parseDouble(priceField.getText()));
            location.setStorageName(storageNameField.getSelectionModel().getSelectedItem().toString());
            System.out.println(LocationDAO.updateLocation(location));
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

        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "Invalid price!\n"; 
        } else {
            try {
                Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid price (must be an integer)!\n"; 
            }
        }


        if (errorMessage.length() != 0) {
        	// Shows the error message because the input is not valid.
            MyAlert alert = new MyAlert(
            		AlertType.ERROR,
            		"Invalid input",
            		errorMessage,
            		"Please correct invalid fields and try again.");
            alert.showAndWait();
            //Return false because the input is not valid
            return false;
        }else {
        	//Return true because the input was valid
        	return true;
        }
    }
}