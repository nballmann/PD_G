package org.nic.pd_g;

import javafx.scene.layout.Region;

import org.nic.pd_g.util.ControllerInterface;

public class BrowserController implements ControllerInterface
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
	public synchronized void changeActiveStatus() {
		isActive = !isActive;	
		notify();
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
	
}
