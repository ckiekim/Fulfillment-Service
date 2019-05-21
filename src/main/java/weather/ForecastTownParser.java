package weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//import org.json.simple.JSONArray; 
//import org.json.simple.JSONObject; 
//import org.json.simple.JSONValue; 
/* import org.slf4j.Logger; import
 * org.slf4j.LoggerFactory;
 * 
 * public class ForecastTownParser { private static final Logger log =
 * LoggerFactory.getLogger(ForecastTownParser.class); private static final
 * String authKey =
 * "7t1%2BJu7GtCa%2BLEPxtUypI5MoMfYEvnA77nfvT%2FA3snI9YBNqDRmfdsuYAh5kAxsXae1vs%2FX9WdowCCoQHbuJwQ%3D%3D";
 * private static final String reqUrl =
 * "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData";
 * private static final String gridX = "61"; // 경기도 평택시 서탄면 private static final
 * String gridY = "117";
 * 
 * public void getTownForecastFromJSON() { SimpleDateFormat sdf = new
 * SimpleDateFormat("yyyyMMdd"); Calendar currentDate = Calendar.getInstance();
 * String baseDate = sdf.format(currentDate.getTime());
 * 
 * String baseTime = null; sdf = new SimpleDateFormat("HHmm"); int tmpTime =
 * Integer.parseInt(sdf.format(currentDate.getTime())); if (tmpTime < 210)
 * return;
 * 
 * if (tmpTime < 510) baseTime = "0200"; else if (tmpTime < 810) baseTime =
 * "0500"; else if (tmpTime < 1110) baseTime = "0800"; else if (tmpTime < 1410)
 * baseTime = "1100"; else if (tmpTime < 1710) baseTime = "1400"; else if
 * (tmpTime < 2010) baseTime = "1700"; else if (tmpTime < 2310) baseTime =
 * "2000"; else baseTime = "2300"; log.debug("tmpTime = " + tmpTime +
 * ", baseTime = " + baseTime);
 * 
 * WeatherDAO wdao = new WeatherDAO(); //WeatherDTO dto =
 * wdao.weatherGetLastValue(); if (baseDate.equals(dto.getBase_date()) &&
 * baseTime.equals(dto.getBase_time())) return;
 * 
 * String forecastTownApi = reqUrl + "?ServiceKey=" + authKey + "&base_date=" +
 * baseDate + "&base_time=" + baseTime + "&nx=" + gridX + "&ny=" + gridY + //
 * 여기까지는 mandatory "&numOfRows=500&_type=json"; log.debug(forecastTownApi);
 * JSONObject responseObj = (JSONObject)
 * getRemoteJSON(forecastTownApi).get("response");
 * 
 * JSONObject headerObj = (JSONObject) responseObj.get("header");
 * log.debug(headerObj.toString()); String resultCode = (String)
 * headerObj.get("resultCode"); if(headerObj != null &&
 * resultCode.equals("0000")) { JSONObject bodyObj = (JSONObject)
 * responseObj.get("body");
 * 
 * int totCnt = Integer.parseInt(bodyObj.get("totalCount").toString());
 * log.debug("Total Count = " + totCnt); if(totCnt == 0) { return; } JSONArray
 * items = (JSONArray)((JSONObject) bodyObj.get("items")).get("item");
 * 
 * String fcstDate = null; String fcstTime = null; WeatherDTO wdto = new
 * WeatherDTO("20000101", "0000", "-1", "-1", "-1", "-1", "-1", "-1", "-50",
 * "-50", "-50", "-100", "-100", "-1", "-1", "20000101", "0000");
 * 
 * for (int i = 0, j = 0; i < items.size(); i = i + j) { JSONObject itemObj =
 * (JSONObject)items.get(i); fcstDate = itemObj.get("fcstDate").toString();
 * fcstTime = itemObj.get("fcstTime").toString(); initItem(wdto);
 * wdto.setFcst_date(fcstDate); wdto.setFcst_time(fcstTime);
 * wdto.setBase_date(itemObj.get("baseDate").toString());
 * wdto.setBase_time(itemObj.get("baseTime").toString()); fillItem(wdto,
 * itemObj.get("category").toString(), itemObj.get("fcstValue").toString());
 * log.debug("i = " + i + ", j = " + j); for (j = 1; i + j < items.size(); j++)
 * { itemObj = (JSONObject)items.get(i+j); if
 * (fcstDate.equals(itemObj.get("fcstDate").toString()) &&
 * fcstTime.equals(itemObj.get("fcstTime").toString())) { fillItem(wdto,
 * itemObj.get("category").toString(), itemObj.get("fcstValue").toString()); }
 * else { break; } } //dto = wdao.weatherGetValue(fcstDate, fcstTime); if
 * (fcstDate.equals(dto.getFcst_date())) { //wdao.weatherChangeValue(wdto); //
 * 기존 데이터가 있으면 Update } else { //wdao.weatherSetValue(wdto); // 기존 데이터가 없으면
 * Insert } } } }
 * 
 *//**
	 * API 주소를 통해 요청한 JSON형태의 응답결과를 읽어서 JSONObject 객체로 변환
	 *//*
		 * private JSONObject getRemoteJSON(String url) { StringBuffer jsonHtml = new
		 * StringBuffer();
		 * 
		 * try { URL u = new URL(url); InputStream uis = u.openStream();
		 * 
		 * BufferedReader br = new BufferedReader(new InputStreamReader(uis, "UTF-8"));
		 * 
		 * String line = null; while ((line = br.readLine())!= null){
		 * jsonHtml.append(line + "\n"); } br.close(); uis.close(); } catch (Exception
		 * e) { e.printStackTrace(); } JSONObject jsonObj = (JSONObject)
		 * JSONValue.parse(jsonHtml.toString()); //log.debug(jsonObj.toString()); return
		 * jsonObj; }
		 * 
		 * private void fillItem(WeatherDTO dto, String field, String value) { if
		 * (field.equals("POP")) dto.setPop(value); else if (field.equals("PTY"))
		 * dto.setPty(value); else if (field.equals("R06")) dto.setR06(value); else if
		 * (field.equals("REH")) dto.setReh(value); else if (field.equals("S06"))
		 * dto.setS06(value); else if (field.equals("SKY")) dto.setSky(value); else if
		 * (field.equals("T3H")) dto.setT3h(value); else if (field.equals("TMN"))
		 * dto.setTmn(value); else if (field.equals("TMX")) dto.setTmx(value); else if
		 * (field.equals("UUU")) dto.setUuu(value); else if (field.equals("VVV"))
		 * dto.setVvv(value); else if (field.equals("VEC")) dto.setVec(value); else if
		 * (field.equals("WSD")) dto.setWsd(value); } private void initItem(WeatherDTO
		 * dto) { dto.setPop("-1"); dto.setPty("-1"); dto.setR06("-1");
		 * dto.setReh("-1"); dto.setS06("-1"); dto.setSky("-1"); dto.setT3h("-50");
		 * dto.setTmn("-50"); dto.setTmx("-50"); dto.setUuu("-100"); dto.setVvv("-100");
		 * dto.setVec("-1"); dto.setWsd("-1"); } }
		 */