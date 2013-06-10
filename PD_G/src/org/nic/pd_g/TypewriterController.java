package org.nic.pd_g;

import org.nic.pd_g.util.ControllerInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class TypewriterController implements Runnable, ControllerInterface
{
	private static final int INTERVAL = 250;
	
	private volatile boolean isActive = false;
	private Region theView;
	
	@FXML
	private Label labelOne;
	
	@FXML 
	private Text typeText;
	
	private char[] runningText;
	private final String string = "Mein Lauftext, lorem ipsum etc und so.... Und wieder von vorne....";
	
	private MainApp mainApp;

	@FXML
	private void initialize()
	{
		runningText = string.toCharArray();
		System.out.println(runningText.length);
		System.out.println(runningText);
		runningText = string.toCharArray();
		typeText.setText(string);
		System.out.println(runningText.length);
	}
	
	public TypewriterController()
	{		
	}
	
	public char[] getRunningText()		{ return runningText; }
	public Text getTypeText()			{ return typeText; }
	public boolean getActiveStatus()	{ return isActive; }
	
	@Override
	public void setView(Region view) {
		theView = view;
	}

	@Override
	public Region getView() {
		return theView;
	}
	
//	public Task<Void> getTask()		{ return task; }
	
	public void setMainApp(MainApp mainApp)		{ this.mainApp = mainApp; }
	
	
	public synchronized void changeActiveStatus()
	{
		isActive = !isActive;
		notify();
	}
	
	public void writeType()
	{
		String tempString = "";
		
		for(int i=0; i<runningText.length;i++)
		{
			try
			{
				Thread.sleep((long)(Math.floor(Math.random() * INTERVAL)));
				tempString += runningText[i];
				typeText.setText(tempString);
			}
			catch(InterruptedException e) { e.printStackTrace(); }
			
			System.out.println(tempString);
			 
		}
	}
	
	@Override
	public void run() 
	{
		Thread currentThread = mainApp.getThread1();
		
		while(Thread.currentThread()==currentThread)
		{
			while(isActive)
				writeType();
		
			synchronized(this)
			{
				while(!isActive && Thread.currentThread()==currentThread)
				{
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
}





