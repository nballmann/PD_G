package org.nic.pd_g;

import javafx.fxml.FXML;

/**
 * Root Layout Controller of the Main Application<br>
 * Handles all Menu entries of the main Menu bar
 */
public class RootLayoutController 
{
	private MainApp mainApp;
	
	
	
	public void setMainApp(MainApp mainApp) 
	{
	      this.mainApp = mainApp;
	}
	
	@FXML
	private void handleExit()
	{
		System.exit(0);
	}
	
	@FXML
	private void handleAbout()
	{
//		mainApp.getTypewriterController().changeActiveStatus();
		mainApp.getChartPaneController().changeActiveStatus();
	}
	
	@FXML
	private void handleMenu()
	{	
		mainApp.showPanel(mainApp.getMenuPane());
	}
	
	@FXML
	private void handleBoard()
	{
		mainApp.showPanel(mainApp.getGameBoard());
	}
	
	@FXML
	private void handleNewData()
	{
		
	}

}
