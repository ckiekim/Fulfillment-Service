package util;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.junit.Test;

public class DBManagerTest {
	@Test
	public void testGetConnection() {
		Connection conn = null;
		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/ezen");
			conn = ds.getConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(conn);
	}
}
