package org.nic.pd_g;

import org.nic.pd_g.model.ChartData;
import org.nic.pd_g.util.ActiveStatus;
import org.nic.pd_g.util.ControllerInterface;
import org.nic.pd_g.util.TimeUtil;
import org.nic.pd_g.util.YQL_Exch_Connection;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class ChartPaneController implements ControllerInterface
{
	private MainApp mainApp;
	
	private volatile ActiveStatus activeStatus;
	private Region theView;
	
	private ObservableList<LineChart<String,Number>> lineChartList;
	private ObservableList<XYChart.Series<String,Number>> chartSeriesList;
	
	private static int listCount = 0;

	public void setMainApp(MainApp mainApp)	{ this.mainApp = mainApp; }

	@Override
	public void setView(Region view) {
		theView = view;
	}

	@Override
	public Region getView() {
		return theView;
	}
	
	public boolean getActiveStatus()	{ return activeStatus.getActiveStatus(); }
	
	public synchronized void changeActiveStatus()
	{
		if(getActiveStatus())
		{ 
			activeStatus.setActiveStatus(false);
		}
		else
		{
			activeStatus.setActiveStatus(true);
		}
	}
	
	@FXML
	private void initialize()
	{
		activeStatus = new ActiveStatus();
		System.out.println(activeStatus.getActiveStatus());
		
		lineChartList = FXCollections.observableArrayList();
		chartSeriesList = FXCollections.observableArrayList();
		addNewChart(YQL_Exch_Connection.DAX_SYMBOL);
		addNewChart(YQL_Exch_Connection.MDAX_SYMBOL);
		
	}
	
	public void addChangeListener(ChangeListener<? super Boolean> myListener)
	{
		activeStatus.activeStatusProperty().addListener(myListener);
	}
	
	public void addNewChart(String ...symbol)
	{
		NumberAxis yAxis = new NumberAxis();
		CategoryAxis timeAxis = new CategoryAxis();
		
		yAxis.setAnimated(true);
		timeAxis.setAnimated(false);
		
//		yAxis.setLabel("Stock Value");
		yAxis.setAnimated(true);
		yAxis.setTickLabelsVisible(true);
		yAxis.setForceZeroInRange(false);
		yAxis.setTickLabelFill(Color.WHITE);
		
//		timeAxis.setLabel("Zeit");
		timeAxis.setTickLabelsVisible(true);
		timeAxis.setTickLabelFill(Color.WHITE);
		
		LineChart<String,Number> lineChart = new LineChart<String, Number>(timeAxis,yAxis);
		
		lineChart.setPrefWidth(720);
		lineChart.setPrefHeight(290);
		
		lineChart.setTranslateY(listCount * lineChart.getPrefHeight());
		
		
		lineChart.setTitleSide(Side.TOP);
		lineChart.setLegendVisible(false);
		lineChart.setAnimated(true);
		
		String chartTitle = "Aktienkurs: ";
		for(String sym : symbol)
		{
			XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();
			series.setName(sym);
        
			lineChart.getData().add(series);
			chartSeriesList.add(series);
			
			chartTitle += sym + " ";
		}
        
		lineChart.setTitle(chartTitle);
		
        lineChartList.add(lineChart);
        
        listCount++;
	}
	
	public void showLineCharts()
	{
		for(LineChart<String,Number> lineChart : lineChartList)
		{
			mainApp.getChartPane().getChildren().add(lineChart);
		}
				
	}
	
	public ChartPaneController()
	{
		
	}
	
	public void animateGraph()
	{
		for(final XYChart.Series<String,Number> series : chartSeriesList)
		{
			final double stockPrice = Double.parseDouble((
					YQL_Exch_Connection.connectTo(
							YQL_Exch_Connection.getYQLUrl(series.getName()))).getLastTradePriceOnly());
		
			if(series.getData().size() >= 12)
				series.getData().remove(0);
			
			Platform.runLater(new Runnable() {
	
				@Override
				public void run() {
					
					try
					{
						series.getData().add(
								ChartData.generateNewChartData(stockPrice));
						
						System.out.println(series.getName() + " (" + TimeUtil.getFormattedTime() + " : " + stockPrice + ")");
					}
					catch(NullPointerException e)
					{
						System.out.println("Keine Preisdaten vorliegend");
					}
				}	
			});
		}
	}

	public void createChartTask()
	{
		final Thread currentThread = mainApp.getThread2();
		
		while(currentThread == Thread.currentThread())
		{
			System.out.println("Chart Thread");
			while(getActiveStatus())
			{
				System.out.println("Chart Thread animating");
				animateGraph();
				
				try
				{
					Thread.sleep(9000);
				}
				catch(InterruptedException e) { break; }
			}
			
			synchronized(this)
			{
				while(!getActiveStatus() && currentThread == Thread.currentThread())
				{
					try
					{
						System.out.println("Chart Thread waiting");
						wait();
					}
					catch(InterruptedException e) { break; }
				}
			}
		}	
	}

}
