package dk.kea.swc.cadd.delivery.view.ui;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class MyAlert extends Alert{

	public MyAlert(AlertType type, String title, String headerText, String content) {
		super(type);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setTitle(title);
		this.setHeaderText(headerText);
		this.setContentText(content);
	}
}
