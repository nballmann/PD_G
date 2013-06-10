package org.nic.pd_g.util;

import javafx.scene.chart.XYChart;

public class TimeValue 
{
	private String time;
	private XYChart.Data<String, Number> data;
	
	public String getTime()	{ return time; }
	public XYChart.Data<String, Number> getData()	{ return data; }

	public TimeValue(String time, XYChart.Data<String, Number> data)
	{
		this.time = time;
		this.data = data;
	}
}
