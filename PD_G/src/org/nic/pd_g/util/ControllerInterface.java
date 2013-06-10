package org.nic.pd_g.util;

import javafx.scene.layout.Region;

import org.nic.pd_g.MainApp;

public interface ControllerInterface 
{	
	public void setMainApp(MainApp mainApp);
	public void setView(Region view);
	public Region getView();
	public void changeActiveStatus();
	public boolean getActiveStatus();
}
