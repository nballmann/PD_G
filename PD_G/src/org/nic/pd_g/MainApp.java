package org.nic.pd_g;

import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application 
{
	private static final String PROXY_IP = "10.140.142.10";
	private static final String PROXY_PORT = "3128";
	
	private Stage primaryStage;
	private BorderPane rootPane;
	private AnchorPane menuPane;
	private AnchorPane gameBoard;
	private AnchorPane typewriterPane;
	private AnchorPane chartPane;
	private Browser theBrowser;
	private GameBoardController gameBoardController;
	private TypewriterController typewriterController;
	private ChartPaneController chartPaneController;
	
	private final ObservableList<Node> centerPaneList = FXCollections.observableArrayList();
	
	private volatile Thread t1;
	private volatile Thread t2;

	@Override
	public void start(Stage primaryStage) 
	{
		theBrowser = new Browser();
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("JavaFX 2.0 Testumgebung");
		
		showRootLayout();
		
		createGameBoard();
		createMenuPane();
		createTypewriter();
		createChartPane();
		
		rootPane.setCenter(theBrowser);
	
		if(menuPane==null)
			System.out.println("MenuPane null");
		
		if(gameBoard==null)
			System.out.println("GameBoard null");
	
		primaryStage.show();
		primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent e) 
			{
				System.exit(0);		
			}
			
		});
		
		t1 = new Thread(typewriterController);	
		t1.setDaemon(true);
		
		t2 = new Thread(new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				
				chartPaneController.createChartTask();
				
				return null;
			}
			
		});
		
		t1.start();
		t2.start();
	}
	
	public AnchorPane getGameBoard()						{ return gameBoard; }
	public AnchorPane getMenuPane()							{ return menuPane; }
	public GameBoardController getGameBoardController()		{ return gameBoardController; }
	public TypewriterController getTypewriterController() 	{ return typewriterController; }
	public ChartPaneController getChartPaneController() 	{ return chartPaneController; }
	public AnchorPane getTypewriterPane()					{ return typewriterPane; }
	public AnchorPane getChartPane()						{ return chartPane; }
	public Thread getThread1()								{ return t1; }
	public Thread getThread2()								{ return t2; }
	
	public void setTypewriterController(final TypewriterController typeController)	{ this.typewriterController = typeController; }
	public void setChartPaneController(final ChartPaneController chartController)	{ this.chartPaneController = chartController; }
	
	private void showRootLayout()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/rootLayout.fxml"));
			rootPane = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootPane);
			primaryStage.setScene(scene);
			
			 // Give the controller access to the main app
		    RootLayoutController controller = loader.getController();
		    controller.setMainApp(this);
		}
		catch(IOException ex)
		{}
	}
	
	private void createMenuPane()
	{
		try
		{	
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/MenuLayout.fxml"));
			menuPane = (AnchorPane) loader.load();
			rootPane.setCenter(menuPane);
			centerPaneList.add(menuPane);
			
			// Give the controller access to the main app
		    MenuLayoutController controller = loader.getController();
		    controller.setMainApp(this);
		}
		catch(IOException ex)
		{ ex.printStackTrace(); }
	}
	
	private void createGameBoard()
	{
		try
		{	
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/GameBoardView.fxml"));
			gameBoard = (AnchorPane) loader.load();
			rootPane.setCenter(gameBoard);
			
			centerPaneList.add(gameBoard);
			
			 // Give the controller access to the main app
		    GameBoardController gameBoardController = loader.getController();
		    gameBoardController.setMainApp(this);
		    
		    gameBoardController.createCanvas();
		}
		catch (IOException ex) { ex.printStackTrace(); }
	}
	
//	public void showPanel(AnchorPane newPane)
//	{
//		if(rootPane.getCenter() != newPane)
//		{
//			rootPane.getChildren().remove(rootPane.getCenter());
//			rootPane.setCenter(newPane);
//		}
//	}
	
	public <T extends Region> void showPanel(T t)
	{
		if(rootPane.getCenter() != t)
		{
			rootPane.getChildren().remove(rootPane.getCenter());
			rootPane.setCenter(t);
		}
	}
	
	private void createTypewriter()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/TypewriterView.fxml"));
			typewriterPane = (AnchorPane) loader.load();
			rootPane.setCenter(typewriterPane);
			
			centerPaneList.add(typewriterPane);
			
			// Give the controller access to the main app
		    typewriterController = loader.getController();
		    typewriterController.setMainApp(this);
			
		} catch(IOException e) {}
		
	}
	
	private void createChartPane()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ChartPaneView.fxml"));
			chartPane = (AnchorPane) loader.load();
			rootPane.setCenter(chartPane);
			
			centerPaneList.add(chartPane);
			
			// Give the controller access to the main app
		    chartPaneController = loader.getController();
		    chartPaneController.setMainApp(this);
		    chartPaneController.showLineCharts();
		    chartPaneController.changeActiveStatus();
			
		} catch(IOException e) {}
		
	}
	
	private AnchorPane newBrowserPane()
	{
		AnchorPane temp = new AnchorPane();

		temp.setPrefSize(800, 600);
		temp.setMaxSize(1920, 1024);
		temp.setMinSize(300, 200);
		temp.setManaged(true);
		
		temp.getChildren().add(new Browser());
		
		return temp;
	}
	
	private class Browser extends Region {
		 
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
	        
	        System.out.println("Browser: " + this.isResizable() + " browser: " + browser.isResizable());
	        
	        //add the web view to the scene
	        getChildren().add(browser);
	        this.setManaged(true);
	 
	    }
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
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
