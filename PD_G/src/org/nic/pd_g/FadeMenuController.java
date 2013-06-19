package org.nic.pd_g;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import org.nic.pd_g.util.ControllerInterface;

public class FadeMenuController implements ControllerInterface
{
	@SuppressWarnings("unused")
	private MainApp mainApp;
	
	private Region theView;
	
	private volatile boolean isActive = false;
	
	@Override
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void setView(Region view) {
		theView = view;
	}

	@Override
	public Region getView() {
		return theView;
	}

	@Override
	public void changeActiveStatus() {
		isActive = !isActive;
		
	}

	@Override
	public boolean getActiveStatus() {
		return isActive;
	}
	
	@FXML
	private void initialize()
	{
	
	}

	
	@FXML
	private void handleButton1()
	{
		mainApp.getFadeTransition().play();
	}
	
	@FXML
	private void handleButton2()
	{
		
	}
	
	@FXML
	private void handleButton3()
	{
		
	}

}
