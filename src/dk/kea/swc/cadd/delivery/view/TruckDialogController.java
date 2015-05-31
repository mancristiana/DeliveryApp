package dk.kea.swc.cadd.delivery.view;

import dk.kea.swc.cadd.delivery.db.TruckDAO;
import dk.kea.swc.cadd.delivery.model.Truck;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TruckDialogController {
	
	@FXML private TextField capacityField;
	@FXML private TextField speedField;
	@FXML private CheckBox 	availableBox;
	
	private Truck   truck;
	private Stage 	dialogStage;
	private boolean isNew;
	private TableView<Truck> truckTable;
	private TruckDAO truckDAO;
	
	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	private void initialize(){
		truckDAO = new TruckDAO();
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
			capacityField.setText(truck.getCapacity().toString());
			speedField.setText(truck.getSpeed().toString());
		    availableBox.setSelected(truck.getAvailable());
		    
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
	            truck.setCapacity(Integer.parseInt(capacityField.getText()));
	            truck.setSpeed(Double.parseDouble(speedField.getText()));
	            truck.setAvailable(availableBox.isSelected());
	            
	            if(isNew) {
	            	System.out.println(truckDAO.createTruck(truck));
	            	truckTable.getItems().add(truck);
	            } else 
	            	System.out.println(truckDAO.updateTruck(truck));
	            
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

