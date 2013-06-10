package org.nic.pd_g.model;

import java.util.Properties;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Browser extends Region {
	
	private static final String PROXY_IP = "10.140.142.10";
	private static final String PROXY_PORT = "3128";
	 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
       
    public Browser() {
    	
    	Properties systemProperties = System.getProperties();
		systemProperties.setProperty("http.proxyHost", PROXY_IP);
		systemProperties.setProperty("http.proxyPort", PROXY_PORT);
		
        //apply the styles
        getStyleClass().add("browser");
        
        // load the web page
        webEngine.load("http://www.youtube.de");
          
        //add the web view to the scene
        getChildren().add(browser);
        this.setManaged(true); 
    }
    
    
    @SuppressWarnings("unused")
	private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
 
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override protected double computePrefWidth(double height) {
        return 800;
    }
 
    @Override protected double computePrefHeight(double width) {
        return 600;
    }
}
