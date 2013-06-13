package org.nic.pd_g.util;

import javafx.scene.layout.Region;

import org.nic.pd_g.MainApp;


/**
 * Interface for the JavaFX Controller classes
 *  
 * @Method <code>setMainApp</code> gives the controller access to the Main App
 * @Method <code>setView</code> gives the controller access to the (FXML)view
 * @Method <code>getView</code> Getter for the associated (FXML)view
 * @Method <code>changeActiveStatus</code> Status change for the visibility of the view
 * @Method <code>getActiveStatus</code> Getter for the Status(visibility) of the view
 */
public interface ControllerInterface 
{	
	public void setMainApp(MainApp mainApp);
	public void setView(Region view);
	public Region getView();
	public void changeActiveStatus();
	public boolean getActiveStatus();
}
