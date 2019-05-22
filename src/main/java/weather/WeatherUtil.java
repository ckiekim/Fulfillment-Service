package weather;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherUtil {
	private static final Logger LOG = LoggerFactory.getLogger(WeatherUtil.class);

	public boolean checkWeather() {
		String baseDate = getCurrentDate();
		String baseTime = getCurrentTime(); 
		if (baseTime == null)
			return false;
		
		WeatherDAO wDao = new WeatherDAO(); 
		WeatherDTO wDto = wDao.weatherGetLastValue(); 
		if (baseDate.equals(wDto.getBaseDate()) && baseTime.equals(wDto.getBaseTime())) 
			return false;
		
		return true;
	}
	
	public void changeWeather() {
		ForecastTownParser parser = new ForecastTownParser();
		parser.getTownForecastFromJSON();
	}
	
	public String getWeatherInfo() {
		String fcstTime = getForecastTime();
		String fcstDate = null;
		if (fcstTime.equals("2400")) {
			fcstDate = getForecastDate(1);
			fcstTime = "0000";
		} else
			fcstDate = getForecastDate(0);
		LOG.debug("fcstTime = {}, fcstDate = {}", fcstTime, fcstDate);
		
		WeatherDAO wDao = new WeatherDAO();
		WeatherDTO wDto = wDao.weatherGetValue(fcstDate, fcstTime);
		StringBuilder info = new StringBuilder();
		if (wDto.getPty().equals("0")) {
			switch(wDto.getSky()) {
				case "1":	info.append("<img src=\"../img/sunny.png\" width=\"20\" height=\"20\"> "); break;
				case "2":	info.append("<img src=\"../img/cloudy.png\" width=\"20\" height=\"20\"> "); break;
				case "3":	info.append("<img src=\"../img/cloudy.png\" width=\"20\" height=\"20\"> "); break;
				case "4":	info.append("<img src=\"../img/cloud.png\" width=\"20\" height=\"20\"> "); break;
				default:	info.append("없음 ");
			}
			info.append(wDto.getTmn() + "&deg;, " + wDto.getTmx() +"&deg;C");
		} else if (wDto.getPty().equals("1")) {
			info.append("<img src=\"../img/rain.png\" width=\"20\" height=\"20\"> " + wDto.getPop() + "%");
		} else if (wDto.getPty().equals("2")) {
			info.append("<img src=\"../img/sleet.png\" width=\"20\" height=\"20\"> " + wDto.getPop() + "%");
		} else if (wDto.getPty().equals("3")) {
			info.append("<img src=\"../img/snow.png\" width=\"20\" height=\"20\"> " + wDto.getPop() + "%");
		} else {
			info.append("없음");
		}
		return info.toString();
	}
	
	public String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime());
	}
	
	public String getForecastDate(int i) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, i);
		return sdf.format(cal.getTime());
	}
	
	public String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Calendar currentDate = Calendar.getInstance();
		int tmpTime = Integer.parseInt(sdf.format(currentDate.getTime())); 
		if (tmpTime < 210) return null;
		if (tmpTime < 510) return "0200"; 
		if (tmpTime < 810) return "0500"; 
		if (tmpTime < 1110) return "0800";
		if (tmpTime < 1410) return "1100";
		if (tmpTime < 1710) return "1400";
		if (tmpTime < 2010) return "1700";
		if (tmpTime < 2310) return "2000";
		return "2300";
	}
	
	public String getForecastTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Calendar currentDate = Calendar.getInstance();
		int tmpTime = Integer.parseInt(sdf.format(currentDate.getTime())); 
		if (tmpTime == 0) return "0000";
		if (tmpTime <= 300) return "0300"; 
		if (tmpTime <= 600) return "0600"; 
		if (tmpTime <= 900) return "0900";
		if (tmpTime <= 1200) return "1200";
		if (tmpTime <= 1500) return "1500";
		if (tmpTime <= 1800) return "1800";
		if (tmpTime <= 2100) return "2100";
		return "2400";
	}
}
