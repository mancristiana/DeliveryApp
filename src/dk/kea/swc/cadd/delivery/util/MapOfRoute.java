package dk.kea.swc.cadd.delivery.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import dk.kea.swc.cadd.delivery.db.OrderDAO;
import dk.kea.swc.cadd.delivery.model.Order;
import dk.kea.swc.cadd.delivery.model.Route;

public class MapOfRoute {
	private String url = "https://www.google.dk/maps/dir/";	
	
	public MapOfRoute(Route route) {
		List<Order> orderList = OrderDAO.getOrdersByRoute(route.getRouteID());
		Iterator<Order> itr = orderList.iterator();
		if(itr.hasNext()) {
			Order o = itr.next();
			url += o.getStorageName().replaceAll(" ", "+") + "/";
			url += o.getCityName().replaceAll(" ", "+") + "/";
		}
		while(itr.hasNext()) {
			url += itr.next().getCityName().replaceAll(" ", "+") + "/";// + ",+Romania/";
		}
		url += "?hl=ro";
	        
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (IOException ex) {
        	System.out.println("IOException" + ex.getMessage());
        } catch (URISyntaxException ex) {
        	System.out.println("URISyntaxException" + ex.getMessage());
            
        }
	}
}
