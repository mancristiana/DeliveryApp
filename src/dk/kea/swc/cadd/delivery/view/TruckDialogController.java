package dk.kea.swc.cadd.delivery.view;

import dk.kea.swc.cadd.delivery.model.Truck;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TruckDialogController {
	
	@FXML private TextField capacityField;
	@FXML private TextField speedField;
	@FXML private CheckBox availableBox;
	
	private Truck             truck;
	private Stage 		dialogStage;

	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	private void initialize(){
		
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
     * Sets the truck to be edited in the dialog.
     * 
     * @param truck
     */
	public void setTruck(Truck truck){
		this.truck= truck;
		
		capacityField.setText(truck.getCapacity().toString());
		speedField.setText(truck.getSpeed().toString());
	    availableBox.setSelected(truck.getAvailable());
	}
	/**
    * Called when the user clicks ok.
    */
	
	 @FXML
	    private void handleOk() {
	        if (isInputValid()) {
	            truck.setCapacity(Integer.parseInt(capacityField.getText()));
	            truck.setSpeed(Double.parseDouble(speedField.getText()));
	            truck.setAvailable(availableBox.isSelected());
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
	        if (capacityField.getText() == null || capacityField.getText().length() == 0) {
	            errorMessage += "Invalid capacity!\n"; 
	        }
	        if (speedField.getText() == null || speedField.getText().length() == 0) {
	            errorMessage += "Invalid speed!\n"; 
	        } else {
	            try {
	                Integer.parseInt(capacityField.getText());
	            } catch (NumberFormatException e) {
	                errorMessage += "Invalid capacity (must be an integer)!\n"; 
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

