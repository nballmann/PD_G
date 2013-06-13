package org.nic.pd_g;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

import org.nic.pd_g.util.ControllerInterface;

public class WritableImageController implements ControllerInterface
{
	private MainApp mainApp;
	private volatile boolean isActive= false;
	private Region theView;
	
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
	public synchronized void changeActiveStatus() {
		isActive = !isActive;
		notify();
	}

	@Override
	public boolean getActiveStatus() {
		return isActive;
	}
	
	@FXML
	private void initialize()
	{
		
	}
	
	

}
