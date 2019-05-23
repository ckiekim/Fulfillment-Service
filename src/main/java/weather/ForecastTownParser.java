package weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.JSONValue; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;

public class ForecastTownParser { 
	private static final Logger LOG = LoggerFactory.getLogger(ForecastTownParser.class); 
	private static final String authKey = "7t1%2BJu7GtCa%2BLEPxtUypI5MoMfYEvnA77nfvT%2FA3snI9YBNqDRmfdsuYAh5kAxsXae1vs%2FX9WdowCCoQHbuJwQ%3D%3D";
	private static final String reqUrl = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData";
	private static final String gridX = "67"; 	// 대전시 서구 월평1동 
	private static final String gridY = "100";
 
	public void getTownForecastFromJSON() { 
		WeatherUtil util = new WeatherUtil();
		if (!util.checkWeather())
			return;
		String baseDate = util.getCurrentDate();
		String baseTime = util.getCurrentTime(); 
		
		String forecastTownApi = reqUrl + "?ServiceKey=" + authKey + "&base_date=" +
				baseDate + "&base_time=" + baseTime + "&nx=" + gridX + "&ny=" + gridY + // 여기까지는 mandatory
				"&numOfRows=500&_type=json";
		LOG.debug(forecastTownApi);
		JSONObject responseObj = (JSONObject)getRemoteJSON(forecastTownApi).get("response");

		JSONObject headerObj = (JSONObject) responseObj.get("header");
		LOG.debug(headerObj.toString());
		String resultCode = (String)headerObj.get("resultCode");
		if(headerObj != null && resultCode.equals("0000")) {
			JSONObject bodyObj = (JSONObject)responseObj.get("body");
			int totCnt = Integer.parseInt(bodyObj.get("totalCount").toString());
			LOG.debug("Total Count = " + totCnt);
			if(totCnt == 0)
				return;
			
			JSONArray items = (JSONArray)((JSONObject) bodyObj.get("items")).get("item");
			String fcstDate = null;
			String fcstTime = null;
			
			WeatherDTO wDto = new WeatherDTO();
			WeatherDTO lwDto = new WeatherDTO();
			WeatherDAO wDao = new WeatherDAO();
			for (int i = 0, k = 0; i < items.size(); i = i + k) {
				JSONObject itemObj = (JSONObject)items.get(i);
				fcstDate = itemObj.get("fcstDate").toString();
				fcstTime = itemObj.get("fcstTime").toString();
				initItem(wDto);
				wDto.setFcstDate(fcstDate);
				wDto.setFcstTime(fcstTime);
				wDto.setBaseDate(itemObj.get("baseDate").toString());
				wDto.setBaseTime(itemObj.get("baseTime").toString());
				fillItem(wDto, itemObj.get("category").toString(), itemObj.get("fcstValue").toString());
				LOG.debug("i = " + i + ", k = " + k);
				for (k = 1; i + k < items.size(); k++) {
					itemObj = (JSONObject)items.get(i + k);
					if (fcstDate.equals(itemObj.get("fcstDate").toString()) &&
						fcstTime.equals(itemObj.get("fcstTime").toString())) {
						fillItem(wDto, itemObj.get("category").toString(), itemObj.get("fcstValue").toString());
					} else {
						break;
					}
				}
				lwDto = wDao.weatherGetValue(fcstDate, fcstTime);
				if (fcstDate.equals(lwDto.getFcstDate())) {
					// Missing Data이면 이전 데이터로 채워줌
					/*if (wDto.getPop().equals("-1"))	wDto.setPop(lwDto.getPop());
					if (wDto.getPty().equals("-1"))	wDto.setPty(lwDto.getPty());
					if (wDto.getR06().equals("-1"))	wDto.setR06(lwDto.getR06());
					if (wDto.getReh().equals("-1"))	wDto.setReh(lwDto.getReh());
					if (wDto.getS06().equals("-1"))	wDto.setS06(lwDto.getS06());
					if (wDto.getSky().equals("-1"))	wDto.setSky(lwDto.getSky());
					if (wDto.getT3h().equals("-50")) wDto.setT3h(lwDto.getT3h());
					if (wDto.getTmn().equals("-50")) wDto.setTmn(lwDto.getTmn());
					if (wDto.getTmx().equals("-50")) wDto.setTmx(lwDto.getTmx());
					if (wDto.getUuu().equals("-100")) wDto.setUuu(lwDto.getUuu());
					if (wDto.getVvv().equals("-100")) wDto.setVvv(lwDto.getVvv());
					if (wDto.getWav().equals("-1"))	wDto.setWav(lwDto.getWav());
					if (wDto.getVec().equals("-1"))	wDto.setVec(lwDto.getVec());
					if (wDto.getWsd().equals("-1"))	wDto.setWsd(lwDto.getWsd());*/
					wDao.weatherChangeValue(wDto); 	// 기존 데이터가 있으면 Update
				} else {
					wDao.weatherSetValue(wDto); 	// 기존 데이터가 없으면 Insert
				}
			}
		}
	}

	// API 주소를 통해 요청한 JSON형태의 응답결과를 읽어서 JSONObject 객체로 변환
	private JSONObject getRemoteJSON(String url) {
		StringBuffer jsonHtml = new StringBuffer();
       
		try {            
			URL u = new URL(url);            
			InputStream uis = u.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(uis, "UTF-8"));
           
			String line = null;
			while ((line = br.readLine())!= null){
				jsonHtml.append(line + "\n");
			}
			br.close();
			uis.close();        
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = (JSONObject) JSONValue.parse(jsonHtml.toString());        
		LOG.debug(jsonObj.toString());
		return jsonObj;
	}

	private void fillItem(WeatherDTO wDto, String field, String value) {
		switch(field) {
			case "POP": wDto.setPop(value); break;
			case "PTY": wDto.setPty(value); break;
			case "R06": wDto.setR06(value); break;
			case "REH": wDto.setReh(value); break;
			case "S06": wDto.setS06(value); break;
			case "SKY": wDto.setSky(value); break;
			case "T3H": wDto.setT3h(value); break;
			case "TMN": wDto.setTmn(value); break;
			case "TMX": wDto.setTmx(value); break;
			case "UUU": wDto.setUuu(value); break;
			case "VVV": wDto.setVvv(value); break;
			case "WAV": wDto.setWav(value); break;
			case "VEC": wDto.setVec(value); break;
			case "WSD": wDto.setWsd(value); break;
			default:
		}
	}
	
	private void initItem(WeatherDTO wDto) {
		wDto.setPop("-1");
		wDto.setPty("-1");
		wDto.setR06("-1");
		wDto.setReh("-1");
		wDto.setS06("-1");
		wDto.setSky("-1");
		wDto.setT3h("-50");
		wDto.setTmn("-50");
		wDto.setTmx("-50");
		wDto.setUuu("-100");
		wDto.setVvv("-100");
		wDto.setWav("-1");
		wDto.setVec("-1");
		wDto.setWsd("-1");
	}
}