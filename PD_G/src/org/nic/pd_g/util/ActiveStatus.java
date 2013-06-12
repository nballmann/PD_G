package org.nic.pd_g.util;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ActiveStatus 
{
	private BooleanProperty activeStatus = new SimpleBooleanProperty(false);
	
	public final boolean getActiveStatus()	{ return activeStatus.get(); }
	
	public final void setActiveStatus(final boolean value)	{ activeStatus.set(value); }
	
	public BooleanProperty activeStatusProperty()	{ return activeStatus; }
}
