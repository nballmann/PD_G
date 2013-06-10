package org.nic.pd_g;

import java.io.IOException;
import org.nic.pd_g.model.Browser;
import org.nic.pd_g.util.ControllerInterface;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MainApp extends Application 
{
//	private static final String PROXY_IP = "10.140.142.10";
//	private static final String PROXY_PORT = "3128";
	
	private Stage primaryStage;
	private BorderPane rootPane;
	private AnchorPane menuPane;
	private AnchorPane gameBoard;
	private AnchorPane typewriterPane;
	private AnchorPane chartPane;
	private Browser theBrowser;
	private MenuLayoutController menuLayoutController;
	private GameBoardController gameBoardController;
	private TypewriterController typewriterController;
	private ChartPaneController chartPaneController;
	private BrowserController browserController;
	
	private final ObservableList<Node> centerPaneList = FXCollections.observableArrayList();
	private final ObservableList<ControllerInterface> controllerList = FXCollections.observableArrayList();
	
	private volatile Thread t1;
	private volatile Thread t2;
	
	public AnchorPane getGameBoard()						{ return gameBoard; }
	public AnchorPane getMenuPane()							{ return menuPane; }
	public AnchorPane getTypewriterPane()					{ return typewriterPane; }
	public AnchorPane getChartPane()						{ return chartPane; }
	public Browser getBrowser()								{ return theBrowser; }
	public GameBoardController getGameBoardController()		{ return gameBoardController; }
	public TypewriterController getTypewriterController() 	{ return typewriterController; }
	public ChartPaneController getChartPaneController() 	{ return chartPaneController; }
	
	public Thread getThread1()								{ return t1; }
	public Thread getThread2()								{ return t2; }
	
	@Override
	public void start(Stage primaryStage) 
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("JavaFX 2.0 Testumgebung");
		
		showRootLayout();
		
		createMenuPane();
		createGameBoard();
		rootPane.setCenter(menuPane);
		menuLayoutController.changeActiveStatus();
		
		createChartPane();
		createTypewriter();
		
		theBrowser = createBrowser();
	
		if(menuPane==null)
			System.out.println("MenuPane null");
		
		if(gameBoard==null)
			System.out.println("GameBoard null");
	
		primaryStage.show();
		primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {

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
			protected Void call() throws Exception
			{
				chartPaneController.addChangeListener(new BooleanChangeListener());
				chartPaneController.createChartTask();
				
				return null;
			}
			
		});
		
		t1.start();
		t2.start();
	}
	
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
			
			centerPaneList.add(menuPane);
			
			// Give the controller access to the main app
		    menuLayoutController = loader.getController();
		    menuLayoutController.setMainApp(this);
		    menuLayoutController.setView(menuPane);
		    
		    controllerList.add(menuLayoutController);
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
			
			centerPaneList.add(gameBoard);
			
			 // Give the controller access to the main app
		    GameBoardController gameBoardController = loader.getController();
		    gameBoardController.setMainApp(this);
		    
		    gameBoardController.createCanvas();
		    gameBoardController.setView(gameBoard);
		    controllerList.add(gameBoardController);
		}
		catch (IOException ex) { ex.printStackTrace(); }
	}

	
	
	private void createTypewriter()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/TypewriterView.fxml"));
			typewriterPane = (AnchorPane) loader.load();
			
			centerPaneList.add(typewriterPane);
			
			// Give the controller access to the main app
		    typewriterController = loader.getController();
		    typewriterController.setMainApp(this);
		    typewriterController.setView(typewriterPane);
		    
		    controllerList.add(typewriterController);
			
		} catch(IOException e) {}
		
	}
	
	private void createChartPane()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ChartPaneView.fxml"));
			chartPane = (AnchorPane) loader.load();
			
			centerPaneList.add(chartPane);
			
			// Give the controller access to the main app
		    chartPaneController = loader.getController();
		    chartPaneController.setMainApp(this);
		    chartPaneController.showLineCharts();
		    chartPaneController.setView(chartPane);
		    
		    controllerList.add(chartPaneController);
			
		} catch(IOException e) {}
		
	}
	
	private Browser createBrowser()
	{
		Browser temp = new Browser();

		temp.setPrefSize(800, 600);
		temp.setMaxSize(1920, 1024);
		temp.setMinSize(300, 200);
		temp.setManaged(true);
		
		centerPaneList.add(temp);
		
		browserController = new BrowserController();
		browserController.setView(temp);
		controllerList.add(browserController);
		
		return temp;
	}
	
	/*
	 * @param t = valid Node extending Region
	 * 
	 */
	public <T extends Region> void showPanel(final T t)
	{
		if(rootPane.getCenter() != t)
		{
			Node tempNode = rootPane.getCenter();
			
			rootPane.getChildren().remove(rootPane.getCenter());
			rootPane.setCenter(t);
			
			for(ControllerInterface controller : controllerList)
			{
				if(controller.getView() == t || controller.getView() == tempNode)
				{
					controller.changeActiveStatus();
					System.out.println(controller + " Jetzt:" + controller.getActiveStatus());
				}	
			}
			
		}
	}
	
	private class BooleanChangeListener implements ChangeListener<Boolean>
	{
		@Override
		public void changed(ObservableValue<? extends Boolean> o,
				Boolean oldVal, Boolean newVal) 
		{
			System.out.println("Active Status changed");
			notify();
		}
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
