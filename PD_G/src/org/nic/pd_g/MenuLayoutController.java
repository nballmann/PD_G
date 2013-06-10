package org.nic.pd_g;

import org.nic.pd_g.util.ControllerInterface;

import javafx.fxml.FXML;

public class MenuLayoutController implements ControllerInterface
{
	private MainApp mainApp;
	private volatile boolean isActive;
	
	public void setMainApp(MainApp mainApp) 
	{
	      this.mainApp = mainApp;
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
		mainApp.getTypewriterController().changeActiveStatus();
	}
	
	@FXML
	private void handleChartPane()
	{
		mainApp.showPanel(mainApp.getChartPane());
	}
	
	@FXML
	private void handleSettings()
	{
		
	}
	
	@FXML
	private void handleExit()
	{
		System.exit(0);
	}

	@Override
	public void changeActiveStatus() {
		isActive = !isActive;
	}
}
