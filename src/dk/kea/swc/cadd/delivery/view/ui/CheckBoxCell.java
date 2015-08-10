package dk.kea.swc.cadd.delivery.view.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

/** 
 * A table cell containing a CheckBox.
 */
public abstract class CheckBoxCell<T> extends TableCell<T, Boolean> {
	
    private CheckBox 	checkbox;
	private HBox 		container;
	
	/** 
	 * Creates a centered CheckBox.
	 */
	public CheckBoxCell() {
		this.container 	= new HBox();
		this.checkbox = new CheckBox();
				
		// Adds the click handler
		checkbox.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent actionEvent) {
				onClick(checkbox.isSelected());
			}
		});
		
		// Wraps the CheckBox in the middle of a HBox
		container.setAlignment(Pos.CENTER);
		container.getChildren().add(checkbox);
	}
	
	/** 
	 * Places the CheckBox in the row only if the row is not empty. 
	 */
	@Override 
	protected void updateItem(Boolean item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty) {
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			setGraphic(container);
		} else {
			setGraphic(null);
		}
	}
	
	/** 
	 * The CheckBox's action, which is invoked whenever the CheckBox is clicked.
	 */
	public abstract void onClick(boolean selected);
}