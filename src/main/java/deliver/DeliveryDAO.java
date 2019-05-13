package deliver;

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

public class DeliveryDAO {
	private static final Logger LOG = LoggerFactory.getLogger(DeliveryDAO.class);
	public static final int DELIVERY_READY = 0;
	public static final int DELIVERY_EXECUTED = 1;
	public static final int DELIVERY_CONFIRMED = 2;
	public static final int DELIVERY_CLOSED = 3;
	Connection conn;
	PreparedStatement pStmt;
	ResultSet rs;

	public void insertDelivery(DeliveryDTO dDto) {
		conn = DBManager.getConnection();
    	String query = "insert into deliveries(dcomId, dinvId, ddate, dstatus) values (?, ?, ?, ?);";
    	try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, dDto.getDcomId());
			pStmt.setInt(2, dDto.getDinvId());
			pStmt.setString(3, dDto.getDdate());
			pStmt.setInt(4, dDto.getDstatus());
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
	
	public void updateInvoiceStatus(InvoiceDTO vDto) {
		conn = DBManager.getConnection();
		String query = "update invoices set vstatus=? where vid=?;";
		LOG.trace("{}, {}, {}", vDto.getVstatus(), vDto.getVid());
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, vDto.getVstatus());
			pStmt.setInt(2, vDto.getVid());
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
	
	public void updateDeliveryStatus(DeliveryDTO dDto) {
		conn = DBManager.getConnection();
		String query = "update deliveries set dstatus=? where did=?;";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, dDto.getDstatus());
			pStmt.setInt(2, dDto.getDid());
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
	
	public int getDeliveryIdByInvoice(int invoiceId) {
		conn = DBManager.getConnection();
		String query = "select did from deliveries where dinvId=?;";
		int result = 0;
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, invoiceId);
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
	
	public int getCountMonthly(int dcomId, String date) {
		conn = DBManager.getConnection();
		String query = null;
		if (dcomId == 0)	// 모든 레코드 선택, 관리자 모드
			query = "select count(*) from deliveries where ddate between " +
					"date(?) and last_day(?)";
		else
			query = "select count(*) from deliveries where ddate between " +
					"date(?) and last_day(?) and dcomId=?";
		int result = 0;
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, date);
			pStmt.setString(2, date);
			if (dcomId > 0)
				pStmt.setInt(3, dcomId);
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
	
	public List<DeliveryDTO> getDeliveryListByMonth(int dcomId, String date, int page) {
		conn = DBManager.getConnection();
		String query = null;
		if (dcomId == 0)	// 모든 레코드 선택, 관리자 모드
			query = "select d.did, d.dcomId, d.dinvId, d.ddate, d.dstatus, v.vname, v.vaddr, c.cname " + 
					"from deliveries as d inner join invoices as v on d.dinvId=v.vid " + 
					"inner join companies as c on d.dcomId=c.cid where d.dstatus=? and " + 
					"d.ddate between date(?) and last_day(?) order by d.dcomId, d.ddate, d.did limit ?, 10;";
		else
			if (page == 0)
				query = "select d.did, d.dcomId, d.dinvId, d.ddate, d.dstatus, v.vname, v.vaddr, c.cname " + 
						"from deliveries as d inner join invoices as v on d.dinvId=v.vid " + 
						"inner join companies as c on d.dcomId=c.cid where d.dstatus=? and " + 
						"d.ddate between date(?) and last_day(?) and d.dcomId=? order by d.ddate, d.did;";
			else
				query = "select d.did, d.dcomId, d.dinvId, d.ddate, d.dstatus, v.vname, v.vaddr, c.cname " + 
						"from deliveries as d inner join invoices as v on d.dinvId=v.vid " + 
						"inner join companies as c on d.dcomId=c.cid where d.dstatus=? and " + 
						"d.ddate between date(?) and last_day(?) and d.dcomId=? order by d.ddate, d.did limit ?, 10;";
		int offset = (page - 1) * 10;
		List<DeliveryDTO> dList = new ArrayList<DeliveryDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, DELIVERY_CONFIRMED);
			pStmt.setString(2, date);
			pStmt.setString(3, date);
			if (dcomId == 0) {
				pStmt.setInt(4, offset);
			} else {
				pStmt.setInt(4, dcomId);
				if (page > 0)
					pStmt.setInt(5, offset);
			}
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				DeliveryDTO dDto = new DeliveryDTO();
				dDto.setDid(rs.getInt(1));
				dDto.setDcomId(rs.getInt(2));
				dDto.setDinvId(rs.getInt(3));
				dDto.setDdate(rs.getString(4).substring(0, 16));
				dDto.setDstatus(rs.getInt(5));
				dDto.setDname(rs.getString(6));
				dDto.setDaddr(rs.getString(7));
				dDto.setDcomName(rs.getString(8));
				switch(rs.getInt(5)) {
					case DELIVERY_READY:
						dDto.setDstatusName("대기"); break;
					case DELIVERY_EXECUTED:
						dDto.setDstatusName("실행"); break;
					case DELIVERY_CONFIRMED:
						dDto.setDstatusName("확정"); break;
					default:
				}
				dList.add(dDto);
				LOG.trace(dDto.toString());
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
		return dList;
	}
	
	public List<DeliveryDTO> getDeliveryListByDate(String date) {
		conn = DBManager.getConnection();
		String query = "select d.did, d.dcomId, d.dinvId, d.ddate, d.dstatus, v.vname, v.vaddr, c.cname " + 
				"from deliveries as d inner join invoices as v on d.dinvId=v.vid " + 
				"inner join companies as c on d.dcomId=c.cid " + 
				"where d.dstatus=? and d.ddate like ? order by d.dcomId, d.ddate, d.did;";
		List<DeliveryDTO> dList = new ArrayList<DeliveryDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, DELIVERY_CONFIRMED);
			pStmt.setString(2, date);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				DeliveryDTO dDto = new DeliveryDTO();
				dDto.setDid(rs.getInt(1));
				dDto.setDcomId(rs.getInt(2));
				dDto.setDinvId(rs.getInt(3));
				dDto.setDdate(rs.getString(4).substring(0, 16));
				dDto.setDstatus(rs.getInt(5));
				dDto.setDname(rs.getString(6));
				dDto.setDaddr(rs.getString(7));
				dDto.setDcomName(rs.getString(8));
				switch(rs.getInt(5)) {
					case DELIVERY_READY:
						dDto.setDstatusName("대기"); break;
					case DELIVERY_EXECUTED:
						dDto.setDstatusName("실행"); break;
					case DELIVERY_CONFIRMED:
						dDto.setDstatusName("확정"); break;
					default:
				}
				dList.add(dDto);
				LOG.trace(dDto.toString());
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
		return dList;
	}
	
	public List<DeliveryDTO> getDeliveryListByStatus(int status) {
		conn = DBManager.getConnection();
		String query = "select d.did, d.dcomId, d.dinvId, d.ddate, d.dstatus, v.vname, v.vaddr, c.cname " + 
				"from deliveries as d inner join invoices as v on d.dinvId=v.vid " + 
				"inner join companies as c on d.dcomId=c.cid " + 
				"where d.dstatus=? order by d.did desc;";
		List<DeliveryDTO> dList = new ArrayList<DeliveryDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, status);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				DeliveryDTO dDto = new DeliveryDTO();
				dDto.setDid(rs.getInt(1));
				dDto.setDcomId(rs.getInt(2));
				dDto.setDinvId(rs.getInt(3));
				dDto.setDdate(rs.getString(4).substring(0, 16));
				dDto.setDstatus(rs.getInt(5));
				dDto.setDname(rs.getString(6));
				dDto.setDaddr(rs.getString(7));
				dDto.setDcomName(rs.getString(8));
				switch(rs.getInt(5)) {
					case DELIVERY_READY:
						dDto.setDstatusName("대기"); break;
					case DELIVERY_EXECUTED:
						dDto.setDstatusName("실행"); break;
					case DELIVERY_CONFIRMED:
						dDto.setDstatusName("확정"); break;
					default:
				}
				dList.add(dDto);
				LOG.trace(dDto.toString());
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
		return dList;
	}
	
	public List<DeliveryDTO> getDeliveryReleasedList(int dcomId, String date) {
		conn = DBManager.getConnection();
		String query = "select d.did, d.dcomId, d.dinvId, d.ddate, d.dstatus, v.vname, v.vaddr " + 
				"from deliveries as d inner join invoices as v on d.dinvId=v.vid " + 
				"where d.dcomId=? and d.ddate like ? order by d.did desc;";
		List<DeliveryDTO> dList = new ArrayList<DeliveryDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, dcomId);
			pStmt.setString(2, date);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				DeliveryDTO dDto = new DeliveryDTO();
				dDto.setDid(rs.getInt(1));
				dDto.setDcomId(rs.getInt(2));
				dDto.setDinvId(rs.getInt(3));
				dDto.setDdate(rs.getString(4).substring(0, 16));
				dDto.setDstatus(rs.getInt(5));
				dDto.setDname(rs.getString(6));
				dDto.setDaddr(rs.getString(7));
				switch(rs.getInt(5)) {
					case DELIVERY_READY:
						dDto.setDstatusName("대기"); break;
					case DELIVERY_EXECUTED:
						dDto.setDstatusName("실행"); break;
					case DELIVERY_CONFIRMED:
						dDto.setDstatusName("확정"); break;
					default:
				}
				dList.add(dDto);
				LOG.trace(dDto.toString());
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
		return dList;
	}
	
	public List<InvoiceDTO> getInvoicesByLogis(int logisId) {
		conn = DBManager.getConnection();
		String query = "select * from invoices where vlogisId=? and (vstatus=? or vstatus=? or vstatus=?);";
		List<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, logisId);
			pStmt.setInt(2, InvoiceDAO.INVOICE_READY);
			pStmt.setInt(3, InvoiceDAO.INVOICE_DELAYED);
			pStmt.setInt(4, InvoiceDAO.INVOICE_DELAY_READY);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				InvoiceDTO vDto = new InvoiceDTO();
				vDto.setVid(rs.getInt(1));
				vDto.setVname(rs.getString(2));
				vDto.setVtel(rs.getString(3));
				vDto.setVaddr(rs.getString(4));
				vDto.setVcomId(rs.getInt(5));
				vDto.setVdate(rs.getString(6).substring(0, 16));
				vDto.setVtotal(rs.getInt(7));
				vDto.setVstatus(rs.getInt(8));
				vDto.setVlogisId(rs.getInt(9));
				switch(rs.getInt(8)) {
					case InvoiceDAO.INVOICE_READY:
						vDto.setVstatusName("대기"); break;
					case InvoiceDAO.INVOICE_DELAYED:
						vDto.setVstatusName("지연"); break;
					case InvoiceDAO.INVOICE_RELEASED:
						vDto.setVstatusName("실행"); break;
					case InvoiceDAO.INVOICE_CONFIRMED:
						vDto.setVstatusName("확정"); break;
					case InvoiceDAO.INVOICE_DELAY_READY:
						vDto.setVstatusName("우선대기"); break;
					default:
				}
				vList.add(vDto);
				LOG.trace(vDto.toString());
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
		return vList;
	}
}
