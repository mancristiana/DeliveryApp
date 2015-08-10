package dk.kea.swc.cadd.delivery.view.ui;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

/** 
 * A table cell containing an image with the available/unavailable icon.
 */
public class AvailableCell<T> extends TableCell<T, Boolean> {
	
	private HBox container;
	
	/** 
	 * Creates a centered image with the available/unavailable icon.
	 */
	public AvailableCell() {
		this.container = new HBox();
	}
	
	/** 
	 * Places the image in the row only if the row is not empty. 
	 */
	@Override protected void updateItem(Boolean item, boolean empty) {
		super.updateItem(item, empty);
	    if (!empty) {
	      setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	      setGraphic(container);
	      
	      if(item) 	container.setId("available-button");
	      else 		container.setId("unavailable-button");
	      
	    } else {
	      setGraphic(null);
	    }
	}
}