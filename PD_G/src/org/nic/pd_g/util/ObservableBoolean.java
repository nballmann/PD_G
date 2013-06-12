package org.nic.pd_g.util;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;

public class ObservableBoolean implements ObservableBooleanValue
{
	private boolean value;
	
	public ObservableBoolean(boolean initialValue)
	{
		value = initialValue;
	}

	@Override
	public void addListener(ChangeListener<? super Boolean> listener) {
//		this.addListener(listener);
		
	}

	@Override
	public Boolean getValue() {
		
		return Boolean.valueOf(value);
	}

	@Override
	public void removeListener(ChangeListener<? super Boolean> listener) {
//		this.removeListener(listener);
		
	}

	@Override
	public void addListener(InvalidationListener listener) {

		
	}

	@Override
	public void removeListener(InvalidationListener listener) {
//		this.removeListener(listener);
		
	}

	@Override
	public boolean get() {
		
		return value;
	}
	
	public void set(boolean value) {
		this.value = value;
	}

}
