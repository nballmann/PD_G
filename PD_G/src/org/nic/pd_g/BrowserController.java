package org.nic.pd_g;

import org.nic.pd_g.util.ControllerInterface;

public class BrowserController implements ControllerInterface
{
	private MainApp mainApp;
	private volatile boolean isActive = false;

	@Override
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
	}

	@Override
	public void changeActiveStatus() {
		isActive = !isActive;
		
	}
	
}
