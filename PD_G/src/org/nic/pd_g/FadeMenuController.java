package org.nic.pd_g;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableDoubleValue;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import org.nic.pd_g.util.ControllerInterface;

public class FadeMenuController implements ControllerInterface
{
	@SuppressWarnings("unused")
	private MainApp mainApp;
	
	private Region theView;
	private Timeline timeline = new Timeline();
	private WritableDoubleValue yPos;
	
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
		yPos = new WritableDoubleValue() {
			
			Double val = .0d;
			
			@Override
			public void setValue(Number value) {
				val = value.doubleValue();
			}
			
			@Override
			public Number getValue() {
				return (Number)val;
			}
			
			@Override
			public void set(double value) {
				val = value;
			}
			
			@Override
			public double get() {
				return val;
			}
		};
		
		yPos.set(0);
		
		timeline.setCycleCount(1);
		timeline.setAutoReverse(true);
		
		final KeyValue kv = new KeyValue(yPos,  100);
		final KeyFrame kf = new KeyFrame(Duration.seconds(1.5), kv);
		
		timeline.getKeyFrames().add(kf);
	}
	
	@FXML
	private void handleButton1()
	{
		timeline.play();
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
