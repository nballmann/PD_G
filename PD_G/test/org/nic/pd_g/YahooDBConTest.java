package org.nic.pd_g;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.nic.pd_g.util.StockInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class YahooDBConTest {

	private static String yahooURLFirst = "http://query.yahooapis.com/v1/public/" +
			"yql?q=select%20LastTradePriceOnly%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22";
	
	private static String stockSymbol = "GOOG";
	
	private static String yahooURLSecond = "%22)&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
	
	public static void main(String[] args) 
	{
		connectTo();
	}
	
	private static void connectTo()
	{
	String yqlURL = yahooURLFirst + stockSymbol + yahooURLSecond;
	
	System.out.println(yqlURL);
		
		String lastTradePriceOnly = "test";
		
		try
		{
			URL url = new URL(yqlURL);
			
			URLConnection connection;
			
			System.out.println("vor open");
			connection = url.openConnection();
			System.out.println("nach open");
			
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			
			System.out.println("vor responce");
			int responceCode = httpConnection.getResponseCode();
			System.out.println("nach responce");
			
			System.out.println(responceCode);
			
			if(responceCode == HttpURLConnection.HTTP_OK)
			{
				System.out.println("Connect OK");
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
						 StockInfo theStock = getStockInformation(docEle);
						 
					
						 lastTradePriceOnly = theStock.getLastTradePriceOnly();
					
						 
						 System.out.println(lastTradePriceOnly);
					 }	 
				 }
			}		
		}
		catch (MalformedURLException e) {}	
		catch (IOException e) {}	
		catch (ParserConfigurationException e) {}
		catch (SAXException e) {}
		finally {}

	}
	
	private static  StockInfo getStockInformation(Element entry)
	 {	 	         
       String stockName = getTextValue(entry, "Name");
       String stockYearLow = getTextValue(entry, "YearLow");
       String stockYearHigh = getTextValue(entry, "YearHigh");
       String stockDaysLow = getTextValue(entry, "DaysLow");
       String stockDaysHigh = getTextValue(entry, "DaysHigh");
       String stocklastTradePriceOnlyTextView = getTextValue(entry, "LastTradePriceOnly");
       String stockChange = getTextValue(entry, "Change");
       String stockDaysRange = getTextValue(entry, "DaysRange");
        
       StockInfo theStock = new StockInfo( //stockDaysLow, stockDaysHigh,
    		   stockYearLow,
           stockYearHigh, stockName, stocklastTradePriceOnlyTextView,
           stockChange, stockDaysRange);
        
       return theStock;
		 	         
	}
	 
	 private static  String getTextValue(Element entry, String tagName)
	 {
		 	         
       String tagValueToReturn = null;
       
       NodeList nl = entry.getElementsByTagName(tagName);
        
       if(nl != null && nl.getLength() > 0)
       {     
           Element element = (Element) nl.item(0);
            
           tagValueToReturn = element.getFirstChild().getNodeValue();
       }
        
       return tagValueToReturn;
        
   }

}
