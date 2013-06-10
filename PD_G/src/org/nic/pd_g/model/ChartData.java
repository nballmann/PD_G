package org.nic.pd_g.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.nic.pd_g.util.ChartQueue;

import javafx.scene.chart.XYChart;


public class ChartData 
{
	public static final String DATE_FORMAT_TIME_NOW = "HH:mm:ss";
	public static final String DATE_FORMAT_DATE_NOW = "dd:MM:yyyy";
	
	
	private static final int CHARTVALUESSIZE = 12;
	
	private ChartQueue chartValues;
	
	
	private ChartData()
	{
		chartValues = new ChartQueue(CHARTVALUESSIZE);
		
		for(int i=0;i<12;i++)
			chartValues.insert(generateNewRandomChartData());
		
	}
	
	public static String now(String dateFormat) 
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		
		return df.format(cal.getTime());
	}
	
	public static XYChart.Data<String, Number> generateNewRandomChartData()
	{
		
		return new XYChart.Data<String, Number>(now(DATE_FORMAT_TIME_NOW), 
												(int)(Math.random() * 45));		
	}
	
	public static XYChart.Data<String, Number> generateNewChartData(final double stockPrice)
	{
		return new XYChart.Data<String, Number>(now(DATE_FORMAT_TIME_NOW),
												stockPrice);
	}
	
	
	
}
