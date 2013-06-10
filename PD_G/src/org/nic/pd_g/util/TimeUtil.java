package org.nic.pd_g.util;

import java.text.SimpleDateFormat;
import java.util.*;

public final class TimeUtil 
{
	private static final String TIMEFORMAT = "HH:mm:ss";
	
	
	public static String getFormattedTime()
	{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(TIMEFORMAT);
			
		return df.format(date);
	}
}
