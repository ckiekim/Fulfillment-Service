package user;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class UserDAOTest {
	private UserDAO uDao = null;
	
	@Before
	public void testBefore() {
		uDao = new UserDAO();
	}
	
	@Test
	public void testGetCompanyCode() {
		int companyCode = uDao.getCompanyCode("이젠");
		assertEquals(1012, companyCode);
	}
}
