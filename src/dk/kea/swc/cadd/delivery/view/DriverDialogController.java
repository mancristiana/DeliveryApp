package dk.kea.swc.cadd.delivery.view;

import dk.kea.swc.cadd.delivery.db.DriverDAO;
import dk.kea.swc.cadd.delivery.model.Driver;
import dk.kea.swc.cadd.delivery.view.ui.MyAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DriverDialogController {

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private CheckBox 	availableBox;
    
	private TableView<Driver> driverTable;
    private boolean	isNew;
    private Stage	dialogStage;
    private Driver	driver;

    /**
     * Sets the values of the fields with the data from the driver.
     *
     * @param driver - or null for empty fields
     */
    public void setDriver(Driver driver) {
    	// Checks if the user clicked new or edit
    	this.isNew = driver==null;
    	
    	// If the user clicked new, we create a new Driver, otherwise we work with the selected driver
    	this.driver = isNew ? new Driver() : driver; // you've got to love how ternary expressions make programmer's life easier :D
    	
    	if(!isNew){
    		nameField.setText(driver.getName());
	        phoneField.setText(driver.getPhone());
	        emailField.setText(driver.getEmail());
	        availableBox.setSelected(driver.getAvailable());
	        
	        nameField.setEditable(false);
    	}
    }
    
	public void setDriverTable(TableView<Driver> table) {
		this.driverTable = table;
	}

	public void setDialogStage(Stage stage) {
		this.dialogStage = stage;
	}
	
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        
        // Validates the email
        if (emailField.getText().isEmpty()) {
            errorMessage += "Email is required.\n"; 
        }
        
        // Validates the phone
        if (phoneField.getText().isEmpty()) {
            errorMessage += "Phone is required.\n"; 
        } else {
            try {
                Integer.parseInt(phoneField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid phone format (must be an integer).\n"; 
            }
        }
        
        // Checks if we found any errors
        if (errorMessage.length() != 0) {
            // Shows the error message because the input is not valid.
        	MyAlert.show(
					AlertType.ERROR,
					"Invalid input",
					errorMessage,
					"Please correct invalid fields and try again.");
            
            // Returns false because the input is not valid
            return false;
        } else {
        	// Returns true because the input is valid
        	return true;
        }
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
	        
            String errorMessage = "";
            
            // Checks if the driver was created or edited
            if(isNew) {
            	errorMessage = DriverDAO.createDriver(driver);
        	} else {
        		errorMessage = DriverDAO.updateDriver(driver);
            }
            
            // Checks if we got sql errors
    		if(!errorMessage.isEmpty()){
                // Shows the error message because we got a sql error.
    			MyAlert.show(
    					AlertType.ERROR,
    					"Error while submitting changes",
    					errorMessage,
    					"The driver was not created/updated.");
    		} else {
    			// Shows the confirmation message because the update was successful
    			MyAlert.show(
    					AlertType.INFORMATION,
    					"Success",
    					"Changes were submitted successfully",
    					"The driver with ID: "+driver.getDriverId()+" was created/updated.");
    			
    			// Updates the application's driver list with the new data from the database 
    			driverTable.setItems(DriverDAO.getDrivers());
    			
        		// Closes the add/edit dialog
        		dialogStage.close();
    		}
        }
    }

    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
	private void handleCancel() {
    	// Closes the add/edit dialog
        dialogStage.close();
    }
}