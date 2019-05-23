package weather;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;

public class WeatherDAO {
	private static final Logger LOG = LoggerFactory.getLogger(WeatherDAO.class);
	Connection conn;
	PreparedStatement pStmt;
	ResultSet rs;

	public WeatherDTO getMinMaxTemp(String fcstDate) {
		WeatherDTO wDto = new WeatherDTO();
		conn = DBManager.getConnection();
		String query = "select max(tmn), max(tmx) from weather where fcstDate like ?;";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, fcstDate);
			rs = pStmt.executeQuery(); 
			while (rs.next()) { 
				wDto.setTmn(rs.getString(1));
				wDto.setTmx(rs.getString(2));
				LOG.debug(wDto.toString());
			}
		} catch (Exception e) {
			LOG.error("Exception occurred!!!");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wDto;
	}
	
	public WeatherDTO weatherGetValue(String fcstDate, String fcstTime) {
		WeatherDTO wDto = new WeatherDTO();
		conn = DBManager.getConnection();
		String query = "select * from weather where fcstDate like ? and fcstTime like ?;";
		
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, fcstDate);
			pStmt.setString(2, fcstTime);
			rs = pStmt.executeQuery(); 
			while (rs.next()) { 
				wDto.setFcstDate(fcstDate);
				wDto.setFcstTime(fcstTime);
				wDto.setBaseDate(rs.getString("baseDate"));
				wDto.setBaseTime(rs.getString("baseTime"));
				wDto.setPop(rs.getString("pop"));
				wDto.setPty(rs.getString("pty"));
				wDto.setR06(rs.getString("r06"));
				wDto.setReh(rs.getString("reh"));
				wDto.setS06(rs.getString("s06"));
				wDto.setSky(rs.getString("sky"));
				wDto.setT3h(rs.getString("t3h"));
				wDto.setTmn(rs.getString("tmn"));
				wDto.setTmx(rs.getString("tmx"));
				wDto.setUuu(rs.getString("uuu"));
				wDto.setVvv(rs.getString("vvv"));
				wDto.setVvv(rs.getString("wav"));
				wDto.setVec(rs.getString("vec"));
				wDto.setWsd(rs.getString("wsd"));
				LOG.debug(wDto.toString());
			}
		} catch (Exception e) {
			LOG.error("Exception occurred!!!");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wDto;
	 }
	 
	public WeatherDTO weatherGetLastValue() {
		WeatherDTO wDto = new WeatherDTO();
		conn = DBManager.getConnection();
		String query = "select baseDate, baseTime from weather order by fcstDate desc limit 1;";
		
		try {
			pStmt = conn.prepareStatement(query);
			rs = pStmt.executeQuery(); 
			while (rs.next()) { 
				wDto.setBaseDate(rs.getString("baseDate"));
				wDto.setBaseTime(rs.getString("baseTime"));
			}
		} catch (Exception e) {
			LOG.error("Exception occurred!!!");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wDto;
	}

	public List<WeatherDTO> weatherGetValueList() {
		List<WeatherDTO> wList = new ArrayList<WeatherDTO>();
		String baseDate = null;
		String baseTime = null;
		conn = DBManager.getConnection();
		
		try {
			String query = "select baseDate, baseTime from weather order by fcstDate desc, fcstTime desc limit 1";
			pStmt = conn.prepareStatement(query);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				baseDate = rs.getString(1);
				baseTime = rs.getString(2);
			}
			
			query = "select * from weather where baseDate like ? and baseTime like ?;";
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, baseDate);
			pStmt.setString(2, baseTime);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				WeatherDTO wDto = new WeatherDTO();
				wDto.setFcstDate(rs.getString("fcstDate"));
				wDto.setFcstTime(rs.getString("fcstTime"));
				wDto.setBaseDate(rs.getString("baseDate"));
				wDto.setBaseTime(rs.getString("baseTime"));
				wDto.setPop(rs.getString("pop"));
				wDto.setPty(rs.getString("pty"));
				wDto.setR06(rs.getString("r06"));
				wDto.setReh(rs.getString("reh"));
				wDto.setS06(rs.getString("s06"));
				wDto.setSky(rs.getString("sky"));
				wDto.setT3h(rs.getString("t3h"));
				wDto.setTmn(rs.getString("tmn"));
				wDto.setTmx(rs.getString("tmx"));
				wDto.setUuu(rs.getString("uuu"));
				wDto.setVvv(rs.getString("vvv"));
				wDto.setVvv(rs.getString("wav"));
				wDto.setVec(rs.getString("vec"));
				wDto.setWsd(rs.getString("wsd"));
				wList.add(wDto);
				LOG.debug(wDto.toString());
			}
		} catch (Exception e) {
			LOG.error("Exception occurred!!!");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wList;
	}
	
	public void weatherSetValue(WeatherDTO wDto) {
		conn = DBManager.getConnection();
    	String query = "insert into weather values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	 	try {
	 		pStmt = conn.prepareStatement(query);
	 		pStmt.setString(1, wDto.getFcstDate());
	 		pStmt.setString(2, wDto.getFcstTime());
	 		pStmt.setString(3, wDto.getBaseDate());
	 		pStmt.setString(4, wDto.getBaseTime());
	 		pStmt.setString(5, wDto.getPop());
	 		pStmt.setString(6, wDto.getPty());
	 		pStmt.setString(7, wDto.getR06());
	 		pStmt.setString(8, wDto.getReh());
	 		pStmt.setString(9, wDto.getS06());
	 		pStmt.setString(10, wDto.getSky());
	 		pStmt.setString(11, wDto.getT3h());
	 		pStmt.setString(12, wDto.getTmn());
	 		pStmt.setString(13, wDto.getTmx());
	 		pStmt.setString(14, wDto.getUuu());
	 		pStmt.setString(15, wDto.getVvv());
	 		pStmt.setString(16, wDto.getWav());
	 		pStmt.setString(17, wDto.getVec());
	 		pStmt.setString(18, wDto.getWsd());
	 		pStmt.executeUpdate();
	 	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void weatherChangeValue(WeatherDTO wDto) {
		conn = DBManager.getConnection();
    	String query = "update weather set baseDate=?, baseTime=?, pop=?, pty=?, r06=?, reh=?, s06=?, sky=?, t3h=?, tmn=?, tmx=?, uuu=?, vvv=?, wav=?, vec=?, wsd=? " + 
    					"where fcstDate = ? and fcstTime = ?;";
	 	try {
	 		pStmt = conn.prepareStatement(query);
	 		pStmt.setString(1, wDto.getBaseDate());
	 		pStmt.setString(2, wDto.getBaseTime());
	 		pStmt.setString(3, wDto.getPop());
	 		pStmt.setString(4, wDto.getPty());
	 		pStmt.setString(5, wDto.getR06());
	 		pStmt.setString(6, wDto.getReh());
	 		pStmt.setString(7, wDto.getS06());
	 		pStmt.setString(8, wDto.getSky());
	 		pStmt.setString(9, wDto.getT3h());
	 		pStmt.setString(10, wDto.getTmn());
	 		pStmt.setString(11, wDto.getTmx());
	 		pStmt.setString(12, wDto.getUuu());
	 		pStmt.setString(13, wDto.getVvv());
	 		pStmt.setString(14, wDto.getWav());
	 		pStmt.setString(15, wDto.getVec());
	 		pStmt.setString(16, wDto.getWsd());
	 		pStmt.setString(17, wDto.getFcstDate());
	 		pStmt.setString(18, wDto.getFcstTime());
	 		pStmt.executeUpdate();
	 	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
