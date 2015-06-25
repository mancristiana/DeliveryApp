package dk.kea.swc.cadd.delivery.view.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

/** 
 * A table cell containing a button.
 */
public abstract class ButtonCell<T> extends TableCell<T, Boolean> {
	
	private Button button;
	private HBox wrap;
	
	/** 
	 * Creates a centered button with the given css id.
	 */
	public ButtonCell(String cssId) {
		this.button = new Button();
		this.wrap 	= new HBox();
		
		// Styles the button accordingly
		button.setId(cssId);
				
		// Adds the click handler
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent actionEvent) {
				onButtonClicked();
			}
		});
		
		// Wraps the button in the middle of a HBox
		wrap.setAlignment(Pos.CENTER);
		wrap.getChildren().add(button);
	}
	
	/** 
	 * Places the button in the row only if the row is not empty. 
	 */
	@Override 
	protected void updateItem(Boolean item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty) {
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			setGraphic(wrap);
		} else {
			setGraphic(null);
		}
	}
	
	/** 
	 * The button's action, which is invoked whenever the button is clicked.
	 */
	public abstract void onButtonClicked();
}