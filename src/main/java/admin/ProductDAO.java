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

public class ProductDAO {
	private static final Logger LOG = LoggerFactory.getLogger(ProductDAO.class);
	Connection conn;
	PreparedStatement pStmt;
	ResultSet rs;
	
	public List<ProductDTO> getProductsByCategory(String category) {
		conn = DBManager.getConnection();
		String query = "select * from products where pcategory like ?;";
		List<ProductDTO> pList = new ArrayList<ProductDTO>();
    	try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, category);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				ProductDTO pDto = new ProductDTO();
				pDto.setPid(rs.getInt(1));
				pDto.setPname(rs.getString(2));
				pDto.setPprice(rs.getInt(3));
				pDto.setPstock(rs.getInt(4));
				pDto.setPimage(rs.getString(5));
				pDto.setPcategory(rs.getString(6));
				pList.add(pDto);
				LOG.trace(pDto.toString());
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
		return pList;
	}
	
	public ProductDTO getProductById(int productId) {
		conn = DBManager.getConnection();
		String query = "select * from products where pid=?;";
		ProductDTO pDto = new ProductDTO();
    	try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, productId);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				pDto.setPid(rs.getInt(1));
				pDto.setPname(rs.getString(2));
				pDto.setPprice(rs.getInt(3));
				pDto.setPstock(rs.getInt(4));
				pDto.setPimage(rs.getString(5));
				pDto.setPcategory(rs.getString(6));
				LOG.trace(pDto.toString());
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
		return pDto;
	}
	
	public void updateStock(ProductDTO pDto) {
		conn = DBManager.getConnection();
		String query = "update products set pstock=? where pid=?;";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, pDto.getPstock());
			pStmt.setInt(2, pDto.getPid());
			pStmt.executeUpdate();
			LOG.debug(pDto.toString());
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
