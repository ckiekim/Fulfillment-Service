package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandleDate {
	private static final Logger LOG = LoggerFactory.getLogger(HandleDate.class);

	public String getToday() {
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd", Locale.KOREA);
		String date = formatter.format(new Date());
		return date;
	}
	
	public String getLastMonth() {
		Calendar cal = Calendar.getInstance( );
		cal.add(cal.MONTH, -1);
		String month = cal.get(cal.YEAR) + "-" + String.format("%02d", cal.get(cal.MONTH) + 1);
		return month;
	}
	
	public String getNumericTime(String amPm) {
		String timeStr = null;
		if (amPm.equals("am"))
			timeStr = " 09:00:00";
		if (amPm.equals("pm"))
			timeStr = " 18:00:00";
		return timeStr;
	}
	
	public String getDateAndTime() {
		SimpleDateFormat formatter = new SimpleDateFormat ("MMddhhmmss", Locale.KOREA);
		String dateAndTime = formatter.format(new Date());
		return dateAndTime;
	}
}
