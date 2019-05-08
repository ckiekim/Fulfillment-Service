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

public class InvoiceDAO {
	private static final Logger LOG = LoggerFactory.getLogger(InvoiceDAO.class);
	public static final int INVOICE_READY = 0;
	public static final int INVOICE_DELAYED = 1;
	public static final int INVOICE_RELEASED = 2;
	public static final int INVOICE_CONFIRMED = 3;
	public static final int INVOICE_DELAY_READY = 4;
	Connection conn;
	PreparedStatement pStmt;
	ResultSet rs;
	
	public List<InvoiceDTO> getInvoicesByStatus(int status) {
		conn = DBManager.getConnection();
		String query = "select * from invoices where vstatus=?;";
		List<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, status);
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
	
	public List<InvoiceDTO> getInvoicesByPage(int page) {
		conn = DBManager.getConnection();
		int offset = 0;
		String query = null;
		if (page == 0) {	// page가 0이면 모든 데이터를 보냄
			query = "select v.vid, v.vname, v.vtel, v.vaddr, v.vcomId, c.cname, v.vdate, v.vtotal, v.vstatus, v.vlogisId, l.cname " +
					"from invoices as v inner join companies as c on v.vcomId=c.cid " + 
					"inner join companies as l on v.vlogisId=l.cid order by v.vid desc;";
		} else {			// page가 0이 아니면 해당 페이지 데이터만 보냄
			query = "select v.vid, v.vname, v.vtel, v.vaddr, v.vcomId, c.cname, v.vdate, v.vtotal, v.vstatus, v.vlogisId, l.cname " +
					"from invoices as v inner join companies as c on v.vcomId=c.cid " + 
					"inner join companies as l on v.vlogisId=l.cid order by v.vid desc limit ?, 10;";
			offset = (page - 1) * 10;
		}
		List<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
    	try {
			pStmt = conn.prepareStatement(query);
			if (page > 0)
				pStmt.setInt(1, offset);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				InvoiceDTO vDto = new InvoiceDTO();
				vDto.setVid(rs.getInt(1));
				vDto.setVname(rs.getString(2));
				vDto.setVtel(rs.getString(3));
				vDto.setVaddr(rs.getString(4));
				vDto.setVcomId(rs.getInt(5));
				vDto.setVcomName(rs.getString(6));
				vDto.setVdate(rs.getString(7).substring(0, 16));
				vDto.setVtotal(rs.getInt(8));
				vDto.setVstatus(rs.getInt(9));
				vDto.setVlogisId(rs.getInt(10));
				vDto.setVlogisName(rs.getString(11));
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
	
	public void updateInvoice(InvoiceDTO vDto) {
		conn = DBManager.getConnection();
		String query = "update invoices set vtotal=?, vstatus=? where vid=?;";
		LOG.trace("{}, {}, {}", vDto.getVtotal(), vDto.getVstatus(), vDto.getVid());
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, vDto.getVtotal());
			pStmt.setInt(2, vDto.getVstatus());
			pStmt.setInt(3, vDto.getVid());
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

	public int getCount() {
		conn = DBManager.getConnection();
		String query = "select count(*) from invoices;";
		int result = 0;
		try {
			pStmt = conn.prepareStatement(query);
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
	
	public InvoiceDTO getInvoiceById(int vid) {
		conn = DBManager.getConnection();
		String query = "select v.vid, v.vname, v.vtel, v.vaddr, v.vcomId, c.cname, v.vdate, v.vtotal, v.vstatus, v.vlogisId, l.cname " +
				"from invoices as v inner join companies as c on v.vcomId=c.cid " + 
				"inner join companies as l on v.vlogisId=l.cid where vid=?;";
		InvoiceDTO vDto = new InvoiceDTO();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, vid);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				vDto.setVid(rs.getInt(1));
				vDto.setVname(rs.getString(2));
				vDto.setVtel(rs.getString(3));
				vDto.setVaddr(rs.getString(4));
				vDto.setVcomId(rs.getInt(5));
				vDto.setVcomName(rs.getString(6));
				vDto.setVdate(rs.getString(7).substring(0, 16));
				vDto.setVtotal(rs.getInt(8));
				vDto.setVstatus(rs.getInt(9));
				vDto.setVlogisId(rs.getInt(10));
				vDto.setVlogisName(rs.getString(11));
				LOG.debug(vDto.toString());
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
		return vDto;
	}
	
	public InvoiceDTO getLastInvoice() {
		conn = DBManager.getConnection();
		String query = "select * from invoices order by vid desc limit 1;";
		InvoiceDTO vDto = new InvoiceDTO();
		try {
			pStmt = conn.prepareStatement(query);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				vDto.setVid(rs.getInt(1));
				vDto.setVname(rs.getString(2));
				vDto.setVtel(rs.getString(3));
				vDto.setVaddr(rs.getString(4));
				vDto.setVcomId(rs.getInt(5));
				vDto.setVdate(rs.getString(6).substring(0, 16));
				LOG.debug(vDto.toString());
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
		return vDto;
	}
	
	public void insertInvoice(InvoiceDTO vDto) {
		conn = DBManager.getConnection();
    	String query = "insert into invoices(vname, vtel, vaddr, vcomId, vdate, vlogisId) values (?, ?, ?, ?, ?, ?);";
    	try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, vDto.getVname());
			pStmt.setString(2, vDto.getVtel());
			pStmt.setString(3, vDto.getVaddr());
			pStmt.setInt(4, vDto.getVcomId());
			pStmt.setString(5, vDto.getVdate());
			pStmt.setInt(6, vDto.getVlogisId());
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
