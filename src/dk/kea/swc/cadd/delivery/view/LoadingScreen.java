package dk.kea.swc.cadd.delivery.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class LoadingScreen {
	
	private BorderPane 	rootLayout;
	private Pane	loadingScreen;
	
	public LoadingScreen(BorderPane rootLayout){
		this.rootLayout = rootLayout;

		loadingScreen = new Pane();
        ImageView imgView = new ImageView();
        imgView.setId("loading-image");
        loadingScreen.getChildren().add(imgView);
	}
	public void show(){
        rootLayout.setCenter(loadingScreen);
	}
}
