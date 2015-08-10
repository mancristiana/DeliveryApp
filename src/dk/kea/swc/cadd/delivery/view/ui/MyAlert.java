package dk.kea.swc.cadd.delivery.view.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;

public class MyAlert{

	/** 
	 * Displays an alert of the given type with the given parameters. 
	 */
	public static void show(AlertType type, String title, String headerText, String content) {
		Alert alert = new Alert(type);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(content);
		alert.show();
	}
}
