package org.nic.pd_g.util;

import java.util.Arrays;

import javafx.scene.chart.XYChart;

public class ChartQueue 
{
	private Object[] queueArray;
	
//	private XYChart.Series<Number,Number>[] queueObject;
	
	private int queueSize;
	
	private int front, rear = 0, numberOfItems = 0;
	
	public ChartQueue(int size)
	{
		queueSize = size;
		
		queueArray = new Object[size];
		
		Arrays.fill(queueArray, null);
	}
	
	public void insert(XYChart.Data<String, Number> input)
	{
		if(numberOfItems + 1 <= queueSize)
		{
			queueArray[rear] = input;
			
			rear++;
			
			numberOfItems++;
			
			System.out.println(input + " was added to the Queue");
		}
		else
		{
			System.out.println("queue is full");
			remove();
			insert(input);
		}
	}
	
	public void remove()
	{
		if(numberOfItems > 0)
		{
			System.out.println("remove " + queueArray[front] + "...");
			
			queueArray[front] = "null";
			
			front ++;
			
			numberOfItems--;
		}
		else
		{
			System.out.println("empty Queue");
		}
		
	}
	
	public void peek()
	{
		System.out.println("The first element is " + queueArray[front]);
	}
	
	
}
