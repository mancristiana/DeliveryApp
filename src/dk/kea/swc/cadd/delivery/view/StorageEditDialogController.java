package dk.kea.swc.cadd.delivery.view;

import dk.kea.swc.cadd.delivery.db.StorageDAO;
import dk.kea.swc.cadd.delivery.model.Storage;
import dk.kea.swc.cadd.delivery.view.ui.MyAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StorageEditDialogController {

	@FXML private TextField cityNameField;
    @FXML private TextField quantityField;
    
    private Stage 		dialogStage;
    private Storage 	storage;
    
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
     * Sets the storage to be edited in the dialog.
     * 
     * @param storage
     */
    public void setStorage(Storage storage) {
        this.storage = storage;
        
        cityNameField.setText(storage.getCityName());
        cityNameField.setEditable(false);
        quantityField.setText(storage.getAvailableQuantity().toString());

    }


    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            storage.setCityName(cityNameField.getText());
            storage.setAvailableQuantity(Double.parseDouble(quantityField.getText()));
            StorageDAO.updateStorage(storage);
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
            errorMessage += "Invalid City Name!\n"; 
        } else {
            try {
            	Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid quantity number (must be an integer)!\n"; 
            }
        }
     
    if (errorMessage.length() == 0) {
        return true;
    } else {
        // Show the error message.
        MyAlert alert = new MyAlert(
        		AlertType.ERROR,
        		"Invalid fields",
        		errorMessage,
        		"Please correct invalid fields");
        

        alert.showAndWait();
        return false;
    }
}
}


