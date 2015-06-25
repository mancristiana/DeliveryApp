package dk.kea.swc.cadd.delivery.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class LoadingScreen {
	
	private static BorderPane rootLayout;
	private static HBox	loadingScreen;
	private static Node currentPage;
	static SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SSS");
	
	public static void initLoadingScreen(BorderPane pane){
		rootLayout = pane;
		loadingScreen = new HBox();
		
	    Image image = new Image("http://goo.gl/YsuGV");
	    ImageView iv = new ImageView(image);
	    
        loadingScreen.setAlignment(Pos.CENTER);
        loadingScreen.getChildren().add(iv);
	}
	
	public static void show(){
		System.out.println("show"+sdf.format(new Date()));
		currentPage =  rootLayout.getLeft();
        rootLayout.setLeft(loadingScreen);
	}
	
	public static void hide(){
		System.out.println("hide"+sdf.format(new Date()));
        rootLayout.setLeft(currentPage);
	}
}
