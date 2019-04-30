package company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.DBManager;

public class CompanyDAO {
	private static final Logger LOG = LoggerFactory.getLogger(CompanyDAO.class);
	public static final int ID_PASSWORD_MATCH = 1;
	public static final int ID_DOES_NOT_EXIST = 2;
	public static final int PASSWORD_IS_WRONG = 3;
	public static final int DATABASE_ERROR = -1;
	public static final int ROLE_COMPANY = 1;
	public static final int ROLE_LOGISTICS = 2;
	public static final int ROLE_SUPPLIER = 3;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public void registerUser(UserDTO uDto) {
		conn = DBManager.getConnection();
    	String query = "insert into users values (?, ?, ?, ?);";
    	PreparedStatement pStmt = null;
    	try {
    		String hashedPassword = BCrypt.hashpw(uDto.getUpass(), BCrypt.gensalt());
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, uDto.getUid());
			pStmt.setString(2, hashedPassword);
			pStmt.setString(3, uDto.getUname());
			pStmt.setInt(4, uDto.getUcomId());
			
			pStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pStmt != null && !pStmt.isClosed()) 
					pStmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<CompanyDTO> getAllCompanies() {
		conn = DBManager.getConnection();
		String query = "select * from company;";
		PreparedStatement pStmt = null;
		List<CompanyDTO> cList = new ArrayList<CompanyDTO>();
    	try {
			pStmt = conn.prepareStatement(query);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				CompanyDTO cDto = new CompanyDTO();
				cDto.setCid(rs.getInt(1));
				cDto.setCname(rs.getString(2));
				cDto.setCrole(rs.getInt(3));
				cDto.setCtel(rs.getString(4));
				cDto.setCfax(rs.getString(5));
				cDto.setCmail(rs.getString(6));
				cList.add(cDto);
				LOG.debug(cDto.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pStmt != null && !pStmt.isClosed()) 
					pStmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cList;
	}
}
