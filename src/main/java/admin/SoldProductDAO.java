package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;

public class SoldProductDAO {
	private static final Logger LOG = LoggerFactory.getLogger(SoldProductDAO.class);
	Connection conn;
	PreparedStatement pStmt;
	ResultSet rs;
	
	public List<SoldProductDTO> getSoldProducts(int invoiceId) {
		conn = DBManager.getConnection();
		String query = "select s.sinvId, s.sprodId, p.pname, p.pprice, s.squantity " 
						+ "from soldProducts as s inner join products as p on s.sprodId=p.pid where s.sinvId=?;";
		List<SoldProductDTO> sList = new ArrayList<SoldProductDTO>();
    	try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, invoiceId);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				SoldProductDTO sDto = new SoldProductDTO();
				sDto.setSinvId(rs.getInt(1));
				sDto.setSprodId(rs.getInt(2));
				sDto.setSprodName(rs.getString(3));
				sDto.setSprice(rs.getInt(4));
				sDto.setSquantity(rs.getInt(5));
				sList.add(sDto);
				LOG.trace(sDto.toString() + sDto.getSprodName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sList;
	}
	
	public void insertSoldProduct(SoldProductDTO sDto) {
		conn = DBManager.getConnection();
    	String query = "insert into soldProducts values (?, ?, ?);";
    	try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, sDto.getSinvId());
			pStmt.setInt(2, sDto.getSprodId());
			pStmt.setInt(3, sDto.getSquantity());
			
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
