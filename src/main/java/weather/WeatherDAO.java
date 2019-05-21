package weather;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherDAO {
	private static final Logger LOG = LoggerFactory.getLogger(WeatherDAO.class);
	Connection conn;
	PreparedStatement pStmt;
	ResultSet rs;
	
	/*
	 * Servlet code
	 * ForecastTownParser parser = new ForecastTownParser();
	 * parser.getTownForecastFromJSON();
	 * response.sendRedirect("../view/forecastView.jsp");
	 */
	
	
	/*
	 * public WeatherDTO weatherGetValue(String fcstDate, String fcstTime) {
	 * WeatherDTO dto = new WeatherDTO(); Connection conn = null; PreparedStatement
	 * pstmt = null; ResultSet rs = null;
	 * 
	 * try { conn = DB.dbConn(); String sql =
	 * "select * from weather_table where fcst_date = " + fcstDate +
	 * " and fcst_time = " + fcstTime; pstmt = conn.prepareStatement(sql); rs =
	 * pstmt.executeQuery(); if (rs.next()) { dto.setFcst_date(fcstDate);
	 * dto.setFcst_time(fcstTime); dto.setPop(rs.getString("pop"));
	 * dto.setPty(rs.getString("pty")); dto.setR06(rs.getString("r06"));
	 * dto.setReh(rs.getString("reh")); dto.setS06(rs.getString("s06"));
	 * dto.setSky(rs.getString("sky")); dto.setT3h(rs.getString("t3h"));
	 * dto.setTmn(rs.getString("tmn")); dto.setTmx(rs.getString("tmx"));
	 * dto.setUuu(rs.getString("uuu")); dto.setVvv(rs.getString("vvv"));
	 * dto.setVec(rs.getString("vec")); dto.setWsd(rs.getString("wsd"));
	 * dto.setBase_date(rs.getString("base_date"));
	 * dto.setBase_time(rs.getString("base_time")); log.debug(dto.toString()); }
	 * else { dto.setFcst_date(""); } } catch (Exception e) {
	 * log.error("Exception occurred."); e.printStackTrace(); } finally { try { if
	 * (rs != null) rs.close(); } catch (Exception e2) { e2.printStackTrace(); } try
	 * { if (pstmt != null) pstmt.close(); } catch (Exception e2) {
	 * e2.printStackTrace(); } try { if (conn != null) conn.close(); } catch
	 * (Exception e2) { e2.printStackTrace(); } } return dto; }
	 * 
	 * public WeatherDTO weatherGetLastValue() { WeatherDTO dto = new WeatherDTO();
	 * Connection conn = null; PreparedStatement pstmt = null; ResultSet rs = null;
	 * 
	 * try { conn = DB.dbConn(); String sql =
	 * "select base_date, base_time from weather_table order by fcst_date desc limit 1"
	 * ; pstmt = conn.prepareStatement(sql); rs = pstmt.executeQuery(); if
	 * (rs.next()) { dto.setBase_date(rs.getString("base_date"));
	 * dto.setBase_time(rs.getString("base_time")); } else { dto.setBase_date(""); }
	 * } catch (Exception e) { log.error("Exception occurred.");
	 * e.printStackTrace(); } finally { try { if (rs != null) rs.close(); } catch
	 * (Exception e2) { e2.printStackTrace(); } try { if (pstmt != null)
	 * pstmt.close(); } catch (Exception e2) { e2.printStackTrace(); } try { if
	 * (conn != null) conn.close(); } catch (Exception e2) { e2.printStackTrace(); }
	 * } return dto; }
	 * 
	 * public ArrayList<WeatherDTO> weatherGetValueList() { ArrayList<WeatherDTO> al
	 * = new ArrayList<WeatherDTO>(); Connection conn = null; PreparedStatement
	 * pstmt = null; ResultSet rs = null; String baseDate = null; String baseTime =
	 * null;
	 * 
	 * try { conn = DB.dbConn(); String sql =
	 * "select base_date, base_time from weather_table order by fcst_date desc, fcst_time desc limit 1"
	 * ; pstmt = conn.prepareStatement(sql); rs = pstmt.executeQuery(); if
	 * (rs.next()) { baseDate = rs.getString("base_date"); baseTime =
	 * rs.getString("base_time"); }
	 * 
	 * sql = "select * from weather_table where base_date=? and base_time=?"; pstmt
	 * = conn.prepareStatement(sql); pstmt.setString(1, baseDate);
	 * pstmt.setString(2, baseTime); rs = pstmt.executeQuery(); while(rs.next()) {
	 * WeatherDTO dto = new WeatherDTO();
	 * dto.setFcst_date(rs.getString("fcst_date"));
	 * dto.setFcst_time(rs.getString("fcst_time")); dto.setPop(rs.getString("pop"));
	 * dto.setPty(rs.getString("pty")); dto.setR06(rs.getString("r06"));
	 * dto.setReh(rs.getString("reh")); dto.setS06(rs.getString("s06"));
	 * dto.setSky(rs.getString("sky")); dto.setT3h(rs.getString("t3h"));
	 * dto.setTmn(rs.getString("tmn")); dto.setTmx(rs.getString("tmx"));
	 * dto.setUuu(rs.getString("uuu")); dto.setVvv(rs.getString("vvv"));
	 * dto.setVec(rs.getString("vec")); dto.setWsd(rs.getString("wsd"));
	 * dto.setBase_date(rs.getString("base_date"));
	 * dto.setBase_time(rs.getString("base_time")); //log.debug(dto.toString());
	 * al.add(dto); } log.debug("baseDate = " + baseDate + ", baseTime = " +
	 * baseTime); } catch (Exception e) { log.error("Exception occurred.");
	 * e.printStackTrace(); } finally { try { if (rs != null) rs.close(); } catch
	 * (Exception e2) { e2.printStackTrace(); } try { if (pstmt != null)
	 * pstmt.close(); } catch (Exception e2) { e2.printStackTrace(); } try { if
	 * (conn != null) conn.close(); } catch (Exception e2) { e2.printStackTrace(); }
	 * } return al; }
	 * 
	 * public void weatherSetValue(WeatherDTO dto) { Connection conn = null;
	 * PreparedStatement pstmt = null; try { conn = DB.dbConn(); String sql =
	 * "insert into weather_table (fcst_date, fcst_time, pop, pty, r06, reh, s06, sky, t3h, tmn, tmx, uuu, vvv, vec, wsd, base_date, base_time) "
	 * + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; log.debug(sql); pstmt =
	 * conn.prepareStatement(sql); pstmt.setString(1, dto.getFcst_date());
	 * pstmt.setString(2, dto.getFcst_time()); pstmt.setString(3, dto.getPop());
	 * pstmt.setString(4, dto.getPty()); pstmt.setString(5, dto.getR06());
	 * pstmt.setString(6, dto.getReh()); pstmt.setString(7, dto.getS06());
	 * pstmt.setString(8, dto.getSky()); pstmt.setString(9, dto.getT3h());
	 * pstmt.setString(10, dto.getTmn()); pstmt.setString(11, dto.getTmx());
	 * pstmt.setString(12, dto.getUuu()); pstmt.setString(13, dto.getVvv());
	 * pstmt.setString(14, dto.getVec()); pstmt.setString(15, dto.getWsd());
	 * pstmt.setString(16, dto.getBase_date()); pstmt.setString(17,
	 * dto.getBase_time()); pstmt.executeUpdate(); } catch (Exception e) {
	 * log.error("Exception occurred."); e.printStackTrace(); } finally { try { if
	 * (pstmt != null) pstmt.close(); } catch (Exception e2) { e2.printStackTrace();
	 * } try { if (conn != null) conn.close(); } catch (Exception e2) {
	 * e2.printStackTrace(); } } }
	 * 
	 * public void weatherChangeValue(WeatherDTO dto) { Connection conn = null;
	 * PreparedStatement pstmt = null; try { conn = DB.dbConn(); String sql =
	 * "update weather_table set pop=?, pty=?, r06=?, reh=?, s06=?, sky=?, t3h=?, tmn=?, tmx=?, uuu=?, vvv=?, vec=?, wsd=?, base_date=?, base_time=? "
	 * + "where fcst_date = ? and fcst_time = ?"; log.debug(sql); pstmt =
	 * conn.prepareStatement(sql); pstmt.setString(1, dto.getPop());
	 * pstmt.setString(2, dto.getPty()); pstmt.setString(3, dto.getR06());
	 * pstmt.setString(4, dto.getReh()); pstmt.setString(5, dto.getS06());
	 * pstmt.setString(6, dto.getSky()); pstmt.setString(7, dto.getT3h());
	 * pstmt.setString(8, dto.getTmn()); pstmt.setString(9, dto.getTmx());
	 * pstmt.setString(10, dto.getUuu()); pstmt.setString(11, dto.getVvv());
	 * pstmt.setString(12, dto.getVec()); pstmt.setString(13, dto.getWsd());
	 * pstmt.setString(14, dto.getBase_date()); pstmt.setString(15,
	 * dto.getBase_time()); pstmt.setString(16, dto.getFcst_date());
	 * pstmt.setString(17, dto.getFcst_time()); pstmt.executeUpdate(); } catch
	 * (Exception e) { log.error("Exception occurred."); e.printStackTrace(); }
	 * finally { try { if (pstmt != null) pstmt.close(); } catch (Exception e2) {
	 * e2.printStackTrace(); } try { if (conn != null) conn.close(); } catch
	 * (Exception e2) { e2.printStackTrace(); } } }
	 */
}
