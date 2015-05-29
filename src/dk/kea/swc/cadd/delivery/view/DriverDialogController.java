package dk.kea.swc.cadd.delivery.view;

import dk.kea.swc.cadd.delivery.model.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DriverDialogController {

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private CheckBox 	availableBox;

    private Stage 		dialogStage;
    private Driver 		driver;

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
     * Sets the driver to be edited in the dialog.
     * 
     * @param driver
     */
    public void setDriver(Driver driver) {
        this.driver = driver;

        nameField.setText(driver.getName());
        phoneField.setText(driver.getPhone());
        emailField.setText(driver.getEmail());
        availableBox.setSelected(driver.getAvailable());
        nameField.setEditable(false);
    }


    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            driver.setPhone(phoneField.getText());
            driver.setEmail(emailField.getText());
            driver.setAvailable(availableBox.isSelected());
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
        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "Invalid email!\n"; 
        }
        if (phoneField.getText() == null || phoneField.getText().length() == 0) {
            errorMessage += "Invalid phone!\n"; 
        } else {
            try {
                Integer.parseInt(phoneField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid phone (must be an integer)!\n"; 
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