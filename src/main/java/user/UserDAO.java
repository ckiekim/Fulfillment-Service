package user;

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

public class UserDAO {
	private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);
	public static final int ID_PASSWORD_MATCH = 1;
	public static final int ID_DOES_NOT_EXIST = 2;
	public static final int PASSWORD_IS_WRONG = 3;
	public static final int DATABASE_ERROR = -1;
	public static final int ROLE_COMPANY = 1;
	public static final int ROLE_LOGISTICS = 2;
	public static final int ROLE_SUPPLIER = 3;
	Connection conn;
	PreparedStatement pStmt;
	ResultSet rs;
	
	public void registerUser(UserDTO uDto) {
		conn = DBManager.getConnection();
    	String query = "insert into users values (?, ?, ?, ?);";
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
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getCompanyCode(String name) {
		int companyId = 0;
		conn = DBManager.getConnection();
		String query = "select cid from companies where cname like ? and crole=4;";
    	try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, name+"%");
			rs = pStmt.executeQuery();
			while (rs.next()) {
				companyId = rs.getInt(1);
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
		return companyId;
	}
	
	public List<CompanyDTO> getAllCompanies() {
		conn = DBManager.getConnection();
		String query = "select * from companies where crole < 4;";
		List<CompanyDTO> cList = new ArrayList<CompanyDTO>();
    	try {
			pStmt = conn.prepareStatement(query);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				CompanyDTO cDto = new CompanyDTO();
				cDto.setCid(rs.getInt(1));
				cDto.setCname(rs.getString(2));
				cDto.setCrole(rs.getInt(3));
				cDto.setCtel(rs.getString(4));
				cDto.setCfax(rs.getString(5));
				cDto.setCmail(rs.getString(6));
				cList.add(cDto);
				LOG.trace(cDto.toString());
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
		return cList;
	}
	
	public List<UserDTO> getAllUsers() {
		conn = DBManager.getConnection();
		String query = "select u.uid, u.uname, u.ucomId, c.cname, c.crole " + 
				"from users as u inner join companies as c on u.ucomId = c.cid;";
		List<UserDTO> userList = new ArrayList<UserDTO>();
		try {
			pStmt = conn.prepareStatement(query);
			rs = pStmt.executeQuery();
			while (rs.next()) {	
				UserDTO uDto = new UserDTO();
				uDto.setUid(rs.getString(1));
				uDto.setUname(rs.getString(2));
				uDto.setUcomId(rs.getInt(3));
				uDto.setUcomName(rs.getString(4));
				uDto.setUcomRole(rs.getInt(5));
				userList.add(uDto);
				LOG.trace(uDto.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return userList;
	}
	
	public UserDTO getUserInfo(String uid) {
		LOG.trace("uid = {}", uid);
		conn = DBManager.getConnection();
		String query = "select u.uname, u.ucomId, c.cname, c.crole from users as u " + 
				"inner join companies as c on u.ucomId = c.cid where uid=?;";
		UserDTO uDto = new UserDTO();
		uDto.setUid(uid);
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, uid);
			rs = pStmt.executeQuery();
			while (rs.next()) {	
				uDto.setUname(rs.getString(1));
				uDto.setUcomId(rs.getInt(2));
				uDto.setUcomName(rs.getString(3));
				uDto.setUcomRole(rs.getInt(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		LOG.debug(uDto.toString());
		return uDto;
	}
	
	public int verifyIdPassword(String uid, String upass) {
		LOG.trace("uid, upass = {}, {}", uid, upass);
		conn = DBManager.getConnection();
		String query = "select upass from users where uid=?;";
		String hashedPassword = "";
		try {
			pStmt = conn.prepareStatement(query);
			pStmt.setString(1, uid);
			rs = pStmt.executeQuery();
			while (rs.next()) {	
				hashedPassword = rs.getString(1);
				if (BCrypt.checkpw(upass, hashedPassword))
					return ID_PASSWORD_MATCH;
				else
					return PASSWORD_IS_WRONG;
			}
			return ID_DOES_NOT_EXIST;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return DATABASE_ERROR;
	}
}
