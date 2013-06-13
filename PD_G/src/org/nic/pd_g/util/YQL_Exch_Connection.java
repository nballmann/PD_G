package org.nic.pd_g.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * 
 */
public class YQL_Exch_Connection 
{
	private static final String YAHOO_URL_FIRST = "http://query.yahooapis.com/v1/public/" +
			"yql?q=select%20*%20from%20yahoo.finance.quoteslist%20where%20symbol%3D'%5E";
			
	private static final String YAHOO_URL_SECOND = "'&diagnostics=true&env=store%3A%2F%2" +
													"Fdatatables.org%2Falltableswithkeys";
	
	public static final String DAX_SYMBOL = "GDAXI";
	public static final String GSPC_SYMBOL = "GSPC";
	public static final String SDAX_SYMBOL = "SDAXI";
	public static final String MDAX_SYMBOL = "MDAXI";
	public static final String IBEX_SYMBOL = "IBEX";
	public static final String NIKKEI225_SYMBOL = "N255";
	public static final String TECDAX_SYMBOL = "TECDAX";
	public static final String HDAX_SYMBOL = "GDAXHI";
	public static final String GEX_SYMBOL = "GEXI";
	public static final String SMI_SYMBOL = "SMI";
	public static final String JKSE_SYMBOL = "JKSE";
	public static final String STI_SYMBOL = "STI";
	public static final String TSEC_SYMBOL = "TWII";
	public static final String KOSPI_SYMBOL = "KS11";
	public static final String NZX50_SYMBOL = "NZ50";
	
//	private static final String PROXY_IP = "10.140.142.10";
//	private static final String PROXY_PORT = "3128";
	
	public static final String DAX_YQL_URL = YAHOO_URL_FIRST + DAX_SYMBOL + YAHOO_URL_SECOND;
	public static final String GSPC_YQL_URL = YAHOO_URL_FIRST + GSPC_SYMBOL + YAHOO_URL_SECOND;
	
	// XML Data to Retrieve
	private static String lastTradePriceOnly = "";
	private static String lastTradeDate = "";
	private static String lastTradeTime = "";
	private static String change = "";
	private static String open = "";
	private static String daysHigh = "";
	private static String daysLow = "";
	private static String volume = "";
	
	
	// --------------------------------------------------------------------------------------
	public static String getLastTradePriceOnly() {
		return lastTradePriceOnly;
	}

	public static String getLastTradeDate() {
		return lastTradeDate;
	}

	public static String getLastTradeTime() {
		return lastTradeTime;
	}

	public static String getChange() {
		return change;
	}

	public static String getOpen() {
		return open;
	}

	public static String getDaysHigh() {
		return daysHigh;
	}

	public static String getDaysLow() {
		return daysLow;
	}

	public static String getVolume() {
		return volume;
	}
	// --------------------------------------------------------------------------------------
	
	public static String getYQLUrl(final String symbol)
	{
		return YAHOO_URL_FIRST + symbol + YAHOO_URL_SECOND;
	}
	
	/**
	 * <p>
	 * Connects to the Yahoo yql-table: <code>yahoo.finance.quoteslist</code> and gets information from the 
	 * stock symbol passed as the parameter.
	 * <p>
	 * The input stream is passed as a DOM and parsed to String via "quote" tag name.
	 * 
	 * @param yqlURL	constant market stock symbol
	 * @return all query information as an ExchInfo object
	 */
	public static ExchInfo connectTo(final String yqlURL)
	{	
		ExchInfo theExch = null;
		
		try
		{
			final URL url = new URL(yqlURL);
			URLConnection connection;
			
//			Properties systemProperties = System.getProperties();
//			systemProperties.setProperty("http.proxyHost", PROXY_IP);
//			systemProperties.setProperty("http.proxyPort", PROXY_PORT);
			
			connection = url.openConnection();
			
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			
//			System.out.println(httpConnection.usingProxy());
			
			final int responceCode = httpConnection.getResponseCode();
			
			System.out.println("Sending Query... " + "Responce Code: " + responceCode);
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
						 theExch = getStockInformation(docEle);
						 
						 daysLow = theExch.getDaysLow();
						 daysHigh = theExch.getDaysHigh();
						 lastTradeDate = theExch.getLastTradeDate();
						 lastTradeTime = theExch.getLastTradeTime();
						 volume = theExch.getVolume();
						 lastTradePriceOnly = theExch.getLastTradePriceOnly();
						 change = theExch.getChange();
						 open = theExch.getOpen();
					 }	 
				 }
			}
			else if(responceCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT)
			{
				System.out.println("Client Timeout");
			}
			else if(responceCode == HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
			{
				System.out.println("Gateway Timeout");
			}
			else if(responceCode == HttpURLConnection.HTTP_UNAVAILABLE)
			{
				System.out.println("Service Unavailable");
			}
			else
			{
				System.out.println("Responce Code: " + responceCode);
			}
		}
		catch (MalformedURLException e) {}	
		catch (IOException e) {}	
		catch (ParserConfigurationException e) {}
		catch (SAXException e) {}
		finally {}

		return theExch;
	}
	
	 private static ExchInfo getStockInformation(final Element entry)
	 {	 	
		lastTradePriceOnly = getTextValue(entry, "LastTradePriceOnly");
        lastTradeDate = getTextValue(entry, "LastTradeDate");
        lastTradeTime = getTextValue(entry, "LastTradeTime");
        change = getTextValue(entry, "Change");
        open = getTextValue(entry, "Open");
        daysHigh = getTextValue(entry, "DaysHigh");
        daysLow = getTextValue(entry, "DaysLow");
        volume = getTextValue(entry, "Volume");
     
        ExchInfo theExch = new ExchInfo(DAX_SYMBOL, 
        		lastTradePriceOnly, lastTradeDate, lastTradeTime, 
        		change, open, daysHigh, daysLow, volume);
         
        return theExch;		 	         
	}
	 
	 private static String getTextValue(final Element entry,final String tagName)
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
