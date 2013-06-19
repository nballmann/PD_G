package org.nic.pd_g;

import java.io.IOException;
import org.nic.pd_g.model.Browser;
import org.nic.pd_g.util.ControllerInterface;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


/**
 * The JavaFX 2.x main class controlling the scene graph
 * 
 * This Application is for testing purposes of the Java FX 2.x 
 * capabilities only
 * <ul>
 * <li>Actual implementations:
 * <li>GameBoard: 	Drawing board for testing of Canvas capabilities
 * <li>Typewriter: 	simple thread implementation for Text and Label
 * <li>ChartPane: 	complex implementation of the JavaFX Chart-System 
 * <li>				using YQL Queries and DOM W3C for parsing database
 * <li>				entries of real-time stock-data
 * <li>Browser:		HTML viewer based on WebKit-Engine
 * </ul>
 * @author NBallmann
 * @version %I%, %G%
 * @since 0.2
 */
public class MainApp extends Application 
{
	
	private Stage primaryStage;
	
	// Austauschbare Panes im Scene graph der App
	private BorderPane rootPane;
	private AnchorPane menuPane;
	private AnchorPane gameBoard;
	private AnchorPane typewriterPane;
	private AnchorPane chartPane;
	private AnchorPane fadeMenu;
	private Browser theBrowser;
	
	// Zugehörige Controller
	private MenuLayoutController menuLayoutController;
	private GameBoardController gameBoardController;
	private TypewriterController typewriterController;
	private ChartPaneController chartPaneController;
	private BrowserController browserController;
	private FadeMenuController fadeMenuController;
	
	private FadeTransition ft;
	
//	private final ObservableList<Node> centerPaneList = FXCollections.observableArrayList();
	
	// ObservableList für die ControllerObjekte -> erlaubt generische Implementierung einer Methode zur Sichtbarkeitssteuerung
	private final ObservableList<ControllerInterface> controllerList = FXCollections.observableArrayList();
	
	private volatile Thread t1;
	private volatile Thread t2;

	public AnchorPane getFadeMenu()							{ return fadeMenu; }
	public AnchorPane getGameBoard()						{ return gameBoard; }
	public AnchorPane getMenuPane()							{ return menuPane; }
	public AnchorPane getTypewriterPane()					{ return typewriterPane; }
	public AnchorPane getChartPane()						{ return chartPane; }
	public Browser getBrowser()								{ return theBrowser; }
	public GameBoardController getGameBoardController()		{ return gameBoardController; }
	public TypewriterController getTypewriterController() 	{ return typewriterController; }
	public ChartPaneController getChartPaneController() 	{ return chartPaneController; }
	
	public FadeTransition getFadeTransition()				{ return ft; }
	
	// Thread für den TypewriterController
	public Thread getThread1()								{ return t1; }
	
	// Thread für die Animation des ChartsPanes
	public Thread getThread2()								{ return t2; }
	
	@Override
	public void start(Stage primaryStage) 
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("JavaFX 2.0 Testumgebung");
		
		showRootLayout();
		
		createMenuPane();
		
		createGameBoard();
		createFadeMenu();
		rootPane.setCenter(menuPane);
		rootPane.setBottom(fadeMenu);
		menuLayoutController.changeActiveStatus();
		
		createTypewriter();
		
		createChartPane();
		
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
			
//			centerPaneList.add(menuPane);
			
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
			
//			centerPaneList.add(gameBoard);
			
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
			
//			centerPaneList.add(typewriterPane);
			
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
			
//			centerPaneList.add(chartPane);
			
			// Give the controller access to the main app
		    chartPaneController = loader.getController();
		    chartPaneController.setMainApp(this);
		    chartPaneController.showLineCharts();
		    chartPaneController.setView(chartPane);
		    
		    controllerList.add(chartPaneController);
			
		} 
		catch(IOException e) { e.printStackTrace(); }
		
	}
	
	private void createFadeMenu()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/FadeMenuView.fxml"));
			fadeMenu = (AnchorPane) loader.load();
				
			fadeMenuController = loader.getController();
			fadeMenuController.setMainApp(this);
			fadeMenuController.setView(fadeMenu);
			
			ft = new FadeTransition(Duration.millis(6000), fadeMenu);
			ft.setFromValue(1.f);
			ft.setToValue(.0f);
			ft.setCycleCount(Timeline.INDEFINITE);
			ft.setAutoReverse(true);
			
			
			controllerList.add(fadeMenuController);
			
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	
	private Browser createBrowser()
	{
		Browser temp = new Browser();
		
//		centerPaneList.add(temp);
		
		browserController = new BrowserController();
		browserController.setView(temp);
		controllerList.add(browserController);
		
		return temp;
	}
	
	
	/**
	 * Generic method for swapping the center node  of
	 * the RootPane(BorderPane)
	 * @param t	valid Node extending Region
	 * <p>
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
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
