package org.nic.pd_g.model;

import javafx.scene.image.WritableImage;

public class ImageModel 
{
	private WritableImage image;
	
	private int width;
	private int height;
	
	
	public ImageModel(int width, int height)
	{
		image = new WritableImage(width, height);
	}
	

	
}
