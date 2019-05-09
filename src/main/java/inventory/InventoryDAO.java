package inventory;

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

public class InventoryDAO {
	private static final Logger LOG = LoggerFactory.getLogger(InventoryDAO.class);
	Connection conn;
	PreparedStatement pStmt;
	ResultSet rs;
	
	public void updateInventory(InventoryDTO iDto) {
		conn = DBManager.getConnection();
		String query = "update inventories set ibase=?, iinward=?, ioutward=?, icurrent=? where iid=?;";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, iDto.getIbase());
			pStmt.setInt(2, iDto.getIinward());
			pStmt.setInt(3, iDto.getIoutward());
			pStmt.setInt(4, iDto.getIcurrent());
			pStmt.setInt(5, iDto.getIid());
			pStmt.executeUpdate();
			LOG.debug(iDto.toString());
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

	public InventoryDTO getInventoryByProduct(int productId) {
		conn = DBManager.getConnection();
		String query = "select * from inventories where iprodId=?;";
		InventoryDTO iDto = new InventoryDTO();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, productId);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				iDto.setIid(rs.getInt(1));
				iDto.setIprodId(rs.getInt(2));
				iDto.setIbase(rs.getInt(3));
				iDto.setIinward(rs.getInt(4));
				iDto.setIoutward(rs.getInt(5));
				iDto.setIcurrent(rs.getInt(6));
			}
			LOG.debug(iDto.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return iDto;
	}
	
	public List<InventoryDTO> getInventoriesByPage(int page) {
		conn = DBManager.getConnection();
		int offset = 0;
		String query = null;
		if (page == 0) {
			query = "select i.iid, i.iprodId, i.ibase, i.iinward, i.ioutward, i.icurrent, p.pname, p.pprice " + 
					"from inventories as i inner join products p on i.iprodId=p.pid;";
		} else {
			query = "select i.iid, i.iprodId, i.ibase, i.iinward, i.ioutward, i.icurrent, p.pname, p.pprice " + 
					"from inventories as i inner join products p on i.iprodId=p.pid limit ?, 10;";
			offset = (page - 1) * 10;
		}
		List<InventoryDTO> iList = new ArrayList<InventoryDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			if (page > 0)
				pStmt.setInt(1, offset);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				InventoryDTO iDto = new InventoryDTO();
				iDto.setIid(rs.getInt(1));
				iDto.setIprodId(rs.getInt(2));
				iDto.setIbase(rs.getInt(3));
				iDto.setIinward(rs.getInt(4));
				iDto.setIoutward(rs.getInt(5));
				iDto.setIcurrent(rs.getInt(6));
				iDto.setIprodName(rs.getString(7));
				iDto.setIprice(rs.getInt(8));
				iDto.setIprodPrice(String.format("%,d", rs.getInt(8)));
				iList.add(iDto);
				LOG.trace(iDto.toString());
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
		return iList;
	}
	
	public int getCount() {
		conn = DBManager.getConnection();
		String query = "select count(*) from inventories;";
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
}
