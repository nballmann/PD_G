package org.nic.pd_g;

import org.nic.pd_g.util.ControllerInterface;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class GameBoardController implements ControllerInterface
{
	private MainApp mainApp;
	private volatile boolean isActive = false;;
	
	private Region theView;
	private Canvas canvas;
	private GraphicsContext gc;
	
	public void setMainApp(MainApp mainApp)		{ this.mainApp = mainApp; }
	
	@Override
	public void changeActiveStatus() {
		isActive = !isActive;
	}
	
	public boolean getActiveStatus()	{ return isActive; }
	
	public GraphicsContext getGraphicsContext()	{ return gc; }
	
	@Override
	public void setView(Region view) {
		this.theView = view;	
	}

	@Override
	public Region getView() {		
		return theView;
	}

	public GameBoardController()
	{	
		
	}
	
	public Canvas createCanvas()
	{
		canvas = new Canvas(800, 577);
		
		System.out.println(canvas.getWidth() + ":" + canvas.getHeight());
		gc = canvas.getGraphicsContext2D();
		
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() 
				{
					@Override
					public void handle(MouseEvent e) 
					{
						if(e.getButton()==MouseButton.PRIMARY)
						{
							drawRandomRect((int)e.getX(), (int)e.getY());
						}
						else if(e.getButton()==MouseButton.SECONDARY)
						{
							clearCanvas();
						}	
					}
				});
		
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>()
				{
					@Override
					public void handle(MouseEvent event) 
					{
						if(event.getButton()==MouseButton.PRIMARY)
						{
							gc.clearRect(event.getX() - 2, event.getY() - 2, 6, 6);
						}
					}
				});
		
		if(mainApp.getGameBoard().getChildren() != null)
		{
			mainApp.getGameBoard().getChildren().removeAll(mainApp.getGameBoard().getChildren());	
		}
		
		mainApp.getGameBoard().getChildren().add(canvas);
		
		return canvas;
	}
	
	public void drawRandomRect(int xPos, int yPos)
	{
		gc.setFill(Color.rgb((int)(Math.random() * 254), 
				(int)(Math.random() * 254), 
				(int)(Math.random() * 254)));
		gc.setStroke(Color.rgb((int)(Math.random() * 254), 
				(int)(Math.random() * 254), 
				(int)(Math.random() * 254)));
		gc.setLineWidth(Math.random() * 6);
		
		gc.fillRect(xPos, yPos, 
					Math.random() * canvas.getWidth()/2, 
					Math.random() * canvas.getHeight()/2);	
		
	}
	
	private void clearCanvas()
	{
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}



	
	
	
	
}
