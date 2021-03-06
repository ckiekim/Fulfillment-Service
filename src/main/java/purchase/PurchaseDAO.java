package purchase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import admin.*;
import util.DBManager;

public class PurchaseDAO {
	private static final Logger LOG = LoggerFactory.getLogger(PurchaseDAO.class);
	public static final int PURCHASE_READY = 0;
	public static final int PURCHASE_SUPPLIED = 1;
	public static final int PURCHASE_CONFIRMED = 2;
	public static final int PURCHASE_CLOSED = 3;
	Connection conn;
	PreparedStatement pStmt;
	ResultSet rs;
	
	public int getCountMonthly(int rcomId, String date) {
		conn = DBManager.getConnection();
		String query = null;
		if (rcomId == 0)	// 모든 레코드 선택, 관리자 모드
			query = "select count(*) from purchases where rdate between " +
					"date(?) and last_day(?)";
		else
			query = "select count(*) from purchases where rdate between " +
					"date(?) and last_day(?) and rcomId=?";
		int result = 0;
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, date);
			pStmt.setString(2, date);
			if (rcomId > 0)
				pStmt.setInt(3, rcomId);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
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
		return result;
	}
	
	public List<PurchaseDTO> getSuppliedListByMonth(int supplierId, String date, int page) {
		conn = DBManager.getConnection();
		String query = null;
		if (supplierId == 0)
			query = "select r.rid, r.rinvId, r.rprodId, p.pname, r.rquantity, r.rdate, r.rstatus, p.pstock, c.cname, p.pprice " +
					"from purchases as r inner join products as p on r.rprodId=p.pid " +
					"inner join companies as c on r.rcomId=c.cid where r.rstatus>=? and " +
					"r.rdate between date(?) and last_day(?) order by r.rcomId, r.rid limit ?, 10;";
		else
			if (page == 0)
				query = "select r.rid, r.rinvId, r.rprodId, p.pname, r.rquantity, r.rdate, r.rstatus, p.pstock, c.cname, p.pprice " +
						"from purchases as r inner join products as p on r.rprodId=p.pid " +
						"inner join companies as c on r.rcomId=c.cid where r.rstatus>=? and " +
						"r.rdate between date(?) and last_day(?) and r.rcomId=? order by r.rid;";
			else
				query = "select r.rid, r.rinvId, r.rprodId, p.pname, r.rquantity, r.rdate, r.rstatus, p.pstock, c.cname, p.pprice " +
						"from purchases as r inner join products as p on r.rprodId=p.pid " +
						"inner join companies as c on r.rcomId=c.cid where r.rstatus>=? and " +
						"r.rdate between date(?) and last_day(?) and r.rcomId=? order by r.rid limit ?, 10;";
		int offset = (page - 1) * 10;
		List<PurchaseDTO> rList = new ArrayList<PurchaseDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, PURCHASE_CONFIRMED);
			pStmt.setString(2, date);
			pStmt.setString(3, date);
			if (supplierId == 0) {
				pStmt.setInt(4, offset);
			} else {
				pStmt.setInt(4, supplierId);
				if (page > 0)
					pStmt.setInt(5, offset);
			}
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				PurchaseDTO rDto = new PurchaseDTO();
				rDto.setRid(rs.getInt(1));
				rDto.setRinvId(rs.getInt(2));
				rDto.setRprodId(rs.getInt(3));
				rDto.setRprodName(rs.getString(4));
				rDto.setRquantity(rs.getInt(5));
				if (rs.getString(6) != null)
					rDto.setRdate(rs.getString(6).substring(0, 16));
				rDto.setRstatus(rs.getInt(7));
				rDto.setRpstock(rs.getInt(8));
				rDto.setRcomName(rs.getString(9));
				rDto.setRprice(rs.getInt(10));
				switch(rs.getInt(7)) {
					case PURCHASE_READY:
						rDto.setRstatusName("대기"); break;
					case PURCHASE_SUPPLIED:
						rDto.setRstatusName("실행"); break;
					case PURCHASE_CONFIRMED:
						rDto.setRstatusName("확정"); break;
					case PURCHASE_CLOSED:
						rDto.setRstatusName("정산"); break;
					default:
				}
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
	
	public List<PurchaseDTO> getSuppliedListByDate(String date) {
		conn = DBManager.getConnection();
		String query = "select r.rid, r.rinvId, r.rprodId, p.pname, r.rquantity, r.rdate, r.rstatus, p.pstock, c.cname, p.pprice " +
						"from purchases as r inner join products as p on r.rprodId=p.pid " +
						"inner join companies as c on r.rcomId=c.cid " +
						"where r.rstatus>=? and r.rdate like ? order by r.rcomId, r.rid;";
		List<PurchaseDTO> rList = new ArrayList<PurchaseDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, PURCHASE_CONFIRMED);
			pStmt.setString(2, date);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				PurchaseDTO rDto = new PurchaseDTO();
				rDto.setRid(rs.getInt(1));
				rDto.setRinvId(rs.getInt(2));
				rDto.setRprodId(rs.getInt(3));
				rDto.setRprodName(rs.getString(4));
				rDto.setRquantity(rs.getInt(5));
				if (rs.getString(6) != null)
					rDto.setRdate(rs.getString(6).substring(0, 16));
				rDto.setRstatus(rs.getInt(7));
				rDto.setRpstock(rs.getInt(8));
				rDto.setRcomName(rs.getString(9));
				rDto.setRprice(rs.getInt(10));
				switch(rs.getInt(7)) {
					case PURCHASE_READY:
						rDto.setRstatusName("대기"); break;
					case PURCHASE_SUPPLIED:
						rDto.setRstatusName("실행"); break;
					case PURCHASE_CONFIRMED:
						rDto.setRstatusName("확정"); break;
					case PURCHASE_CLOSED:
						rDto.setRstatusName("정산"); break;
					default:
				}
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
	
	public List<PurchaseDTO> getPurchaseListByStatus(int status) {
		conn = DBManager.getConnection();
		String query = "select r.rid, r.rinvId, r.rprodId, p.pname, r.rquantity, r.rdate, r.rstatus, p.pstock, c.cname, p.pprice " +
						"from purchases as r inner join products as p on r.rprodId=p.pid " +
						"inner join companies as c on r.rcomId=c.cid " +
						"where r.rstatus=? order by r.rcomId, r.rid;";
		List<PurchaseDTO> rList = new ArrayList<PurchaseDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, status);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				PurchaseDTO rDto = new PurchaseDTO();
				rDto.setRid(rs.getInt(1));
				rDto.setRinvId(rs.getInt(2));
				rDto.setRprodId(rs.getInt(3));
				rDto.setRprodName(rs.getString(4));
				rDto.setRquantity(rs.getInt(5));
				if (rs.getString(6) != null)
					rDto.setRdate(rs.getString(6).substring(0, 16));
				rDto.setRstatus(rs.getInt(7));
				rDto.setRpstock(rs.getInt(8));
				rDto.setRcomName(rs.getString(9));
				rDto.setRprice(rs.getInt(10));
				switch(rs.getInt(7)) {
					case PURCHASE_READY:
						rDto.setRstatusName("대기"); break;
					case PURCHASE_SUPPLIED:
						rDto.setRstatusName("실행"); break;
					case PURCHASE_CONFIRMED:
						rDto.setRstatusName("확정"); break;
					case PURCHASE_CLOSED:
						rDto.setRstatusName("정산"); break;
					default:
				}
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
	
	public List<PurchaseDTO> getSuppliedListBySupplierAndDate(int supplierId, String date) {
		conn = DBManager.getConnection();
		String query = "select r.rid, r.rinvId, r.rprodId, p.pname, r.rquantity, r.rdate, r.rstatus, p.pstock, p.pprice " +
						"from purchases as r inner join products as p on r.rprodId=p.pid " +
						"where r.rcomId=? and r.rstatus!=? and r.rdate like ?;";
		List<PurchaseDTO> rList = new ArrayList<PurchaseDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, supplierId);
			pStmt.setInt(2, PURCHASE_READY); 
			pStmt.setString(3, date);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				PurchaseDTO rDto = new PurchaseDTO();
				rDto.setRid(rs.getInt(1));
				rDto.setRinvId(rs.getInt(2));
				rDto.setRprodId(rs.getInt(3));
				rDto.setRprodName(rs.getString(4));
				rDto.setRquantity(rs.getInt(5));
				if (rs.getString(6) != null)
					rDto.setRdate(rs.getString(6).substring(0, 16));
				rDto.setRstatus(rs.getInt(7));
				rDto.setRpstock(rs.getInt(8));
				rDto.setRprice(rs.getInt(9));
				switch(rs.getInt(7)) {
					case PURCHASE_READY:
						rDto.setRstatusName("대기"); break;
					case PURCHASE_SUPPLIED:
						rDto.setRstatusName("실행"); break;
					case PURCHASE_CONFIRMED:
						rDto.setRstatusName("확정"); break;
					case PURCHASE_CLOSED:
						rDto.setRstatusName("정산"); break;
					default:
				}
				rList.add(rDto);
				LOG.debug(rDto.toString());
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
	
	public List<PurchaseDTO> getPurchaseListBySupplier(int supplierId) {
		conn = DBManager.getConnection();
		String query = "select r.rid, r.rinvId, r.rprodId, p.pname, r.rquantity, r.rdate, r.rstatus, p.pstock " +
						"from purchases as r inner join products as p on r.rprodId=p.pid " +
						"where r.rcomId=? and r.rstatus=?;";
		List<PurchaseDTO> rList = new ArrayList<PurchaseDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, supplierId);
			pStmt.setInt(2, PURCHASE_READY);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				PurchaseDTO rDto = new PurchaseDTO();
				rDto.setRid(rs.getInt(1));
				rDto.setRinvId(rs.getInt(2));
				rDto.setRprodId(rs.getInt(3));
				rDto.setRprodName(rs.getString(4));
				rDto.setRquantity(rs.getInt(5));
				if (rs.getString(6) != null)
					rDto.setRdate(rs.getString(6).substring(0, 16));
				rDto.setRstatus(rs.getInt(7));
				rDto.setRpstock(rs.getInt(8));
				switch(rs.getInt(7)) {
					case PURCHASE_READY:
						rDto.setRstatusName("대기"); break;
					case PURCHASE_SUPPLIED:
						rDto.setRstatusName("실행"); break;
					case PURCHASE_CONFIRMED:
						rDto.setRstatusName("확정"); break;
					case PURCHASE_CLOSED:
						rDto.setRstatusName("정산"); break;
					default:
				}
				rList.add(rDto);
				LOG.debug(rDto.toString());
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
	
	public int isPurchasing(int productId) {
		conn = DBManager.getConnection();
		String query = "select rid from purchases where rprodId=? and rstatus=?;";
		int result = 0;
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, productId);
			pStmt.setInt(2, PURCHASE_READY);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
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
		return result;
	}
	
 	public void insertPurchases(PurchaseDTO rDto) {
		conn = DBManager.getConnection();
    	String query = "insert into purchases(rcomId, rinvId, rprodId, rquantity) values (?, ?, ?, ?);";
    	try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, rDto.getRcomId());
			pStmt.setInt(2, rDto.getRinvId());
			pStmt.setInt(3, rDto.getRprodId());
			pStmt.setInt(4, rDto.getRquantity());
			pStmt.executeUpdate();
			LOG.trace(rDto.toString());
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
	
	public void updatePurchaseStatus(PurchaseDTO rDto) {
		conn = DBManager.getConnection();
    	String query = "update purchases set rdate=?, rstatus=? where rid=?;";
    	try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, rDto.getRdate());
			pStmt.setInt(2, rDto.getRstatus());
			pStmt.setInt(3, rDto.getRid());
			pStmt.executeUpdate();
			LOG.trace(rDto.toString());
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
