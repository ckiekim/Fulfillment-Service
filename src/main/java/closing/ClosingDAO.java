package closing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import purchase.PurchaseDTO;
import util.DBManager;

public class ClosingDAO {
	private static final Logger LOG = LoggerFactory.getLogger(ClosingDAO.class);
	public static final int ROLE_COMPANY = 1;
	public static final int ROLE_LOGISTICS = 2;
	public static final int ROLE_SUPPLIER = 3;
	public static final int ROLE_SELLER = 4;
	Connection conn;
	PreparedStatement pStmt;
	ResultSet rs;

	public List<RecordDTO> getRecordsByCompany(int role, String month) {
		conn = DBManager.getConnection();
		String query = "select r.rid, r.rcomId, r.rrole, r.rmonth, r.rdata, c.cname " +
						"from records as r inner join companies as c on r.rcomId=c.cid " +
						"where r.rrole=? and r.rmonth like ?;";
		List<RecordDTO> rList = new ArrayList<RecordDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, role);
			pStmt.setString(2, month);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				RecordDTO rDto = new RecordDTO();
				rDto.setRid(rs.getInt(1));
				rDto.setRcomId(rs.getInt(2));
				rDto.setRrole(rs.getInt(3));
				rDto.setRmonth(rs.getString(4));
				rDto.setRdata(rs.getInt(5));
				rDto.setRcomName(rs.getString(6));
				switch(rs.getInt(3)) {
					case ROLE_LOGISTICS:
						rDto.setRroleName("배송"); break;
					case ROLE_SUPPLIER:
						rDto.setRroleName("공급"); break;
					case ROLE_SELLER:
						rDto.setRroleName("판매"); break;
					default:
				}
				rDto.setRdataComma(String.format("%,d", rs.getInt(5)));
				rList.add(rDto);
				LOG.trace(rDto.toString());
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
		return rList;
	}
	
	public void insertRecord(RecordDTO rDto) {
		conn = DBManager.getConnection();
		String query = "insert into records(rcomId, rrole, rmonth, rdata) values (?, ?, ?, ?);";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, rDto.getRcomId());
			pStmt.setInt(2, rDto.getRrole());
			pStmt.setString(3, rDto.getRmonth());
			pStmt.setInt(4, rDto.getRdata());
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
	
	public void insertSales(SalesDTO lDto) {
		conn = DBManager.getConnection();
		String query = "insert into sales(linvId, ldelId, lmonth, lrevenue) values (?, ?, ?, ?);";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, lDto.getLinvId());
			pStmt.setInt(2, lDto.getLdelId());
			pStmt.setString(3, lDto.getLmonth());
			pStmt.setInt(4, lDto.getLrevenue());
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
