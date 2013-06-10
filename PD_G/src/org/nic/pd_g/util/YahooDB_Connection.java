package org.nic.pd_g.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class YahooDB_Connection 
{
//	private static final String TAG = "STOCKQUOTE";
	
	private static String yahooURLFirst = "http://query.yahooapis.com/v1/public/" +
			"yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22";
	
	private static String stockSymbol = "GOOG";
	
	private static String yahooURLSecond = "%22)&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
	
	private static final String PROXY_IP = "10.140.142.10";
	private static final String PROXY_PORT = "3128";
	
	public static final String YQL_URL = yahooURLFirst + stockSymbol + yahooURLSecond;
	
	// XML Data to Retrieve
	private static String name = "";
	private static String yearLow = "";
	private static String yearHigh = "";
	private static String daysLow = "";
	private static String daysHigh = "";
	private static String lastTradePriceOnly = "";
	private static String change = "";
	private static String daysRange = "";

	
	
	public static String getName() {
		return name;
	}


	public static String getYearLow() {
		return yearLow;
	}


	public static String getYearHigh() {
		return yearHigh;
	}


	public static String getDaysLow() {
		return daysLow;
	}


	public static String getDaysHigh() {
		return daysHigh;
	}


	public static String getLastTradePriceOnly() {
		return lastTradePriceOnly;
	}


	public static String getChange() {
		return change;
	}


	public static String getDaysRange() {
		return daysRange;
	}

	
	
	public static StockInfo connectTo(String yqlURL)
	{	
		StockInfo theStock = null;
		
		try
		{
			URL url = new URL(yqlURL);
			URLConnection connection;
			
			Properties systemProperties = System.getProperties();
			systemProperties.setProperty("http.proxyHost", PROXY_IP);
			systemProperties.setProperty("http.proxyPort", PROXY_PORT);
			
			connection = url.openConnection();
			
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			
//			System.out.println(httpConnection.usingProxy());
			
			int responceCode = httpConnection.getResponseCode();
			
			if(responceCode == HttpURLConnection.HTTP_OK)
			{
//				System.out.println("Connect OK");
				InputStream in = httpConnection.getInputStream();
				
				 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				 DocumentBuilder db = dbf.newDocumentBuilder();
				 
				 Document dom = db.parse(in);
				 
				 Element docEle = dom.getDocumentElement();
				 
				 NodeList nl = docEle.getElementsByTagName("quote");
				 
				 if (nl != null && nl.getLength() > 0) 
				 {
					 for (int i = 0 ; i < nl.getLength(); i++) 
					 {
						 theStock = getStockInformation(docEle);
						 
//						 daysLow = theStock.getDaysLow();
//						 daysHigh = theStock.getDaysHigh();
						 yearLow = theStock.getYearLow();
						 yearHigh = theStock.getYearHigh();
						 name = theStock.getName();
						 lastTradePriceOnly = theStock.getLastTradePriceOnly();
						 change = theStock.getChange();
						 daysRange = theStock.getDaysRange();
						 
						 System.out.println(name + ": ");
					 }	 
				 }
			}		
		}
		catch (MalformedURLException e) {}	
		catch (IOException e) {}	
		catch (ParserConfigurationException e) {}
		catch (SAXException e) {}
		finally {}

		
		return theStock;
	}
	
	
	 private static StockInfo getStockInformation(Element entry)
	 {	 	         
        String stockName = getTextValue(entry, "Name");
        String stockYearLow = getTextValue(entry, "YearLow");
        String stockYearHigh = getTextValue(entry, "YearHigh");
//        String stockDaysLow = getTextValue(entry, "DaysLow");
//        String stockDaysHigh = getTextValue(entry, "DaysHigh");
        String stocklastTradePriceOnlyTextView = getTextValue(entry, "LastTradePriceOnly");
        String stockChange = getTextValue(entry, "Change");
        String stockDaysRange = getTextValue(entry, "DaysRange");
         
        StockInfo theStock = new StockInfo( //stockDaysLow, stockDaysHigh, 
        		stockYearLow,
            stockYearHigh, stockName, stocklastTradePriceOnlyTextView,
            stockChange, stockDaysRange);
         
        return theStock;
		 	         
	}
	 
	 private static String getTextValue(Element entry, String tagName)
	 {
		 	         
        String tagValueToReturn = null;
        
        NodeList nl = entry.getElementsByTagName(tagName);
         
        if(nl != null && nl.getLength() > 0){
             
            Element element = (Element) nl.item(0);
             
            tagValueToReturn = element.getFirstChild().getNodeValue();
        }
         
        return tagValueToReturn;
         
    }
}
