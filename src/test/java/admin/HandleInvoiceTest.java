package admin;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HandleInvoiceTest {
	private HandleInvoice hi = new HandleInvoice();
	
	@Test
	public void testSelectLogis() {
		String addr = "대전";
		assertEquals(1003, hi.selectLogis(addr));
		addr = "경남";
		assertEquals(1004, hi.selectLogis(addr));
	}
}
