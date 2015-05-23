package dk.kea.swc.cadd.delivery.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import dk.kea.swc.cadd.delivery.DeliveryApp;
import dk.kea.swc.cadd.delivery.model.Location;

public class LocationOverviewController {
	
	@FXML
	private TableView<Location> locationTable;
	 
	public void setData(ObservableList<Location> locationList) {
		// TODO Auto-generated method stub
		
	}

}
