package deliver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import admin.InvoiceDTO;
import admin.ProductDTO;
import util.DBManager;

public class DeliveryDAO {
	private static final Logger LOG = LoggerFactory.getLogger(DeliveryDAO.class);
	Connection conn;
	PreparedStatement pStmt;
	ResultSet rs;

	List<DeliveryDTO> getDeliveryWaitList(int companyId) {
		conn = DBManager.getConnection();
		String query = "select * from deliveries where dcomId=?;";
		List<DeliveryDTO> dList = new ArrayList<DeliveryDTO>();
		
		return dList;
	}
	
	List<InvoiceDTO> getInvoicesByLogis(int logisId) {
		conn = DBManager.getConnection();
		String query = "select * from invoices where vlogisId=? and vstatus<2;";
		List<InvoiceDTO> vList = new ArrayList<InvoiceDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, logisId);
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
}
