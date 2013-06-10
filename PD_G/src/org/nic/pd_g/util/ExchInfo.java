package org.nic.pd_g.util;

public class ExchInfo 
{
	private String symbol;
	
	private String lastTradePriceOnly;
	private String lastTradeDate;
	private String lastTradeTime;
	private String change;
	private String open;
	private String daysHigh;
	private String daysLow;
	private String volume;
	
	
	public ExchInfo(final String symbol,final String lastTradePriceOnly, 
					final String lastTradeDate,final String lastTradeTime,
					final String change,final String open,
					final String daysHigh,final String daysLow,final String volume)
	{
		this.symbol = symbol;
		
		this.lastTradePriceOnly = lastTradePriceOnly;
		this.lastTradeDate = lastTradeDate;
		this.lastTradeTime = lastTradeTime;
		this.change = change;
		this.open = open;
		this.daysHigh = daysHigh;
		this.daysLow = daysLow;
		this.volume = volume;
	}


	public String getSymbol() {
		return symbol;
	}


	public String getLastTradePriceOnly() {
		return lastTradePriceOnly;
	}


	public String getLastTradeDate() {
		return lastTradeDate;
	}


	public String getLastTradeTime() {
		return lastTradeTime;
	}


	public String getChange() {
		return change;
	}


	public String getOpen() {
		return open;
	}


	public String getDaysHigh() {
		return daysHigh;
	}


	public String getDaysLow() {
		return daysLow;
	}


	public String getVolume() {
		return volume;
	}



}
