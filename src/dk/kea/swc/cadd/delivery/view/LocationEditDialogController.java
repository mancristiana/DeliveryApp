package dk.kea.swc.cadd.delivery.view;

import dk.kea.swc.cadd.delivery.model.Location;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LocationEditDialogController {

    @FXML private TextField cityNameField;
    @FXML private TextField priceField;

    private Stage 		dialogStage;
    private Location 	location;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
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
        priceField.setText(location.getPrice().toString());
    }


    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            location.setPrice(Double.parseDouble(priceField.getText()));
            
            //TODO edit in the database
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

        if (cityNameField.getText() == null || cityNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n"; 
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