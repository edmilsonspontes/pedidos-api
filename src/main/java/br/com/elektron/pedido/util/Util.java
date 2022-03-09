package br.com.elektron.pedido.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String DATE_HOUR = "hh:mm:ss";
	public static final String DATE_HOUR_FORMAT = "dd/MM/yyyy hh:mm:ss";
	
	public static SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
	public static SimpleDateFormat dh = new SimpleDateFormat(DATE_HOUR);
	public static SimpleDateFormat dhf = new SimpleDateFormat(DATE_HOUR_FORMAT);
	
	
	public static String getDateHour(Date pDate) {
		return dhf.format(pDate);
	}
	
	public static String getDate(Date pDate) {
		return df.format(pDate);
	}

	public static String getHour(Date pDate) {
		return dh.format(pDate);
	}
}
