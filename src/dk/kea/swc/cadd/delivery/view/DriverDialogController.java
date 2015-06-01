package dk.kea.swc.cadd.delivery.view;

import dk.kea.swc.cadd.delivery.db.DriverDAO;
import dk.kea.swc.cadd.delivery.model.Driver;
import javafx.collections.ObservableList;
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

    private boolean 	isNew;
    private Stage 		dialogStage;
    private Driver 		driver;
    private ObservableList<Driver> driverList;
    private DriverDAO driverDAO;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	driverDAO = new DriverDAO();
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
    	if(driver !=null){
	        this.driver = driver;
	        nameField.setText(driver.getName());
	        phoneField.setText(driver.getPhone());
	        emailField.setText(driver.getEmail());
	        availableBox.setSelected(driver.getAvailable());
	        nameField.setEditable(false); 
	        isNew = false;
    	}
    	else{
    		this.driver = new Driver();
    		isNew = true;
    	}
    }

    /**
     * Sets the driver to be edited in the dialog.
     * 
     * @param driverList
     */
	public void setDriverList(ObservableList<Driver> driverList) {
		this.driverList = driverList;
	}
    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	driver.setName(nameField.getText());
    		driver.setPhone(phoneField.getText());
            driver.setEmail(emailField.getText());
            driver.setAvailable(availableBox.isSelected());
	         
            if(isNew) { 
            	System.out.println(driverDAO.createDriver(driver));
            	driverList.add(driver);
	           
	           
        	} else {
        		System.out.println(driverDAO.updateDriver(driver));
	           
            }
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