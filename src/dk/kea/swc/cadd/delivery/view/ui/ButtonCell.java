package dk.kea.swc.cadd.delivery.view.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

/** 
 * A table cell containing a Button.
 */
public abstract class ButtonCell<T> extends TableCell<T, Boolean> {
	
	private Button 	button;
	private HBox 	container;
	
	/** 
	 * Creates a centered Button with the given css id.
	 */
	public ButtonCell(String cssId) {
		this.button = new Button();
		this.container 	= new HBox();
		
		// Styles the Button accordingly
		button.setId(cssId);
				
		// Adds the click handler
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent actionEvent) {
				onClick();
			}
		});
		
		// Wraps the Button in the middle of a HBox
		container.setAlignment(Pos.CENTER);
		container.getChildren().add(button);
	}
	

	/*
	 * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Cell.html#updateItem-T-boolean-
	 * 
	 * The updateItem method should not be called by developers, but it is the best method for developers to override to allow for them to customise the visuals of the cell. 
	 * To clarify, developers should never call this method in their code (they should leave it up to the UI control, such as the ListView control) to call this method. 
	 * However, the purpose of having the updateItem method is so that developers, when specifying custom cell factories (again, like the ListView cell factory), 
	 * the updateItem method can be overridden to allow for complete customisation of the cell.
	 * 
	 * It is very important that subclasses of Cell override the updateItem method properly, 
	 * as failure to do so will lead to issues such as blank cells or cells with unexpected 
	 * content appearing within them. Here is an example of how to properly override the updateItem method:
	 * 
	 * 		protected void updateItem(T item, boolean empty) {
	 *     		super.updateItem(item, empty);
	 *
	 *     		if (empty || item == null) {
	 *     			setText(null);
	 *      		setGraphic(null);
	 *    		} else {
 	 *     			setText(item.toString());
	 *    		}
	 * 		}
	 */
	
	/** 
	 * Places the Button in the row only if the row is not empty. 
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
	 * The Button's action, which is invoked whenever the Button is clicked.
	 */
	public abstract void onClick();
}