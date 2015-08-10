package dk.kea.swc.cadd.delivery.view.manage;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import dk.kea.swc.cadd.delivery.db.TruckDAO;
import dk.kea.swc.cadd.delivery.model.Truck;
import dk.kea.swc.cadd.delivery.view.ui.MyAlert;

public class TruckDialogController {
	
	@FXML private TextField truckIDField;
	@FXML private TextField capacityField;
	@FXML private TextField speedField;
	@FXML private CheckBox 	availableBox;
	
	private Truck   truck;
	private Stage 	dialogStage;
	private boolean isNew;
	private TableView<Truck> truckTable;
	
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
		
		if (truck == null) {
			this.truck = new Truck();
			isNew = true;
			//no need for these, as dialog is constructed again everytime
//			capacityField.setText("");
//			speedField.setText("");
//		    availableBox.setSelected(false);
//		   
//		    capacityField.setEditable(true);
//		    speedField.setEditable(true);
		} else {
			this.truck = truck;
			isNew = false;
			truckIDField.setText(truck.getTruckID()); 
			capacityField.setText(truck.getCapacity().toString());
			speedField.setText(truck.getSpeed().toString());
		    availableBox.setSelected(truck.getAvailable());
		    
		    truckIDField.setEditable(false);
		    capacityField.setEditable(false);
		    speedField.setEditable(false);
	    
		}
	}
	
	public void setTable(TableView<Truck> truckTable) {
		this.truckTable = truckTable;
	}
	/**
    * Called when the user clicks ok.
    */
	
	 @FXML
	 private void handleOk() {
		 if (isInputValid()) {
        	truck.setTruckID(truckIDField.getText());
            truck.setCapacity(Integer.parseInt(capacityField.getText()));
            truck.setSpeed(Double.parseDouble(speedField.getText()));
            truck.setAvailable(availableBox.isSelected());
            
            String errorMessage = "";
            
            // Checks if the truck was created or edited
            if(isNew) {
            	errorMessage = TruckDAO.createTruck(truck);
            } else {
            	errorMessage = TruckDAO.updateTruck(truck);
            }
            
            // Checks if we got sql errors
    		if(!errorMessage.isEmpty()){
                // Shows the error message because we got a sql error.
    			MyAlert.show(
    					AlertType.ERROR,
    					"Error while submitting changes",
    					errorMessage,
    					"The truck was not created/updated.");
    		} else {
    			// Shows the confirmation message because the update was successful
    			MyAlert.show(
    					AlertType.INFORMATION,
    					"Success",
    					"Changes were submitted successfully",
    					"The truck with ID: "+truck.getTruckID()+" was created/updated.");
    			
    			// Updates the application's driver list with the new data from the database 
        		truckTable.getItems().add(truck);	
    			    			
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
	        dialogStage.close();
	    }
	
	 /**
     * Validates the user input in the text fields.
     * @return true if the input is valid
     */
	 private boolean isInputValid() {
	        String errorMessage = "";
	        if (!truckIDField.getText().matches("[A-Z ][A-Z] [0-9][0-9] [A-Z][A-Z][A-Z]")) {
	            errorMessage += "Invalid truck registration number!\nFormat should be [XX nn XXX] or [ X nn XXX] \nX = Uppercase Letter (A-Z) \nn = digit(0-9) \n\n"; 
	        }
	        if (capacityField.getText().isEmpty()) {
	            errorMessage += "Invalid capacity!\n"; 
	        } else {
	            try {
	                Integer.parseInt(capacityField.getText());
	            } catch (NumberFormatException e) {
	                errorMessage += "Invalid capacity (must be an integer)!\n"; 
	            }
	        }
	        if (speedField.getText().isEmpty()) {
	            errorMessage += "Invalid speed!\n"; 
	        } else {
	            try {
	                Integer.parseInt(capacityField.getText());
	            } catch (NumberFormatException e) {
	                errorMessage += "Invalid capacity (must be an integer)!\n"; 
	            }
	        }

	        if (errorMessage.isEmpty()) {
	            return true;
	        } else {
	            // Show the error message.
	        	MyAlert.show(
    					AlertType.ERROR,
    					"Invalid Fields",
    					"Please correct invalid fields",
    					errorMessage);
	            
	            return false;
	        }
	    }
	}

