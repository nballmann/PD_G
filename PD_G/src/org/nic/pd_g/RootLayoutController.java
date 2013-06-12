package org.nic.pd_g;

import javafx.fxml.FXML;


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
		mainApp.getChartPaneController().notify();
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
