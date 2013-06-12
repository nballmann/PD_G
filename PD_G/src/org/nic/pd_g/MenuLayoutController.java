package org.nic.pd_g;

import org.nic.pd_g.util.ControllerInterface;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

public class MenuLayoutController implements ControllerInterface
{
	private MainApp mainApp;
	private Region theView;
	private volatile boolean isActive = false;
	
	public void setMainApp(MainApp mainApp) 
	{
	      this.mainApp = mainApp;
	}
	
	public boolean getActiveStatus()	{ return isActive; }
	
	@Override
	public void setView(Region view) {
		theView = view;
	}

	@Override
	public Region getView() {
		return theView;
	}

	@FXML
	private void handleNewGame()
	{
		mainApp.showPanel(mainApp.getGameBoard());
	}
	
	@FXML
	private void handleTypewriter()
	{
		mainApp.showPanel(mainApp.getTypewriterPane());
	}
	
	@FXML
	private void handleChartPane()
	{
		mainApp.showPanel(mainApp.getChartPane());
	}
	
	@FXML
	private void handleSettings()
	{
		mainApp.showPanel(mainApp.getBrowser());
	}
	
	@FXML
	private void handleExit()
	{
		System.exit(0);
	}

	@Override
	public synchronized void changeActiveStatus() {
		isActive = !isActive;
		notify();
	}
}
