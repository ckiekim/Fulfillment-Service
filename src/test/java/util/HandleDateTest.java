package util;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandleDateTest {
	private static final Logger LOG = LoggerFactory.getLogger(HandleDateTest.class);
	private HandleDate hd = null;
	
	@Before
	public void testBefore() {
		hd = new HandleDate();
		LOG.debug("클래스 생성");
	}
	
	@Test
	public void testGetToday() {
		String today = hd.getToday();
		assertEquals("2019-05-17", today);
		LOG.debug("메쏘드 테스트");
	}
	
	@Test
	public void testGetLastMonth() {
		String month = hd.getLastMonth();
		assertEquals("2019-04", month);
		LOG.debug("메쏘드 테스트");
	}
	
	@Test
	public void testGetNumericTime() {
		assertEquals(" 09:00:00", hd.getNumericTime("am"));
		assertEquals(" 18:00:00", hd.getNumericTime("pm"));
		LOG.debug("메쏘드 테스트");
	}
	
	@After
	public void testAfter() {
		LOG.debug("테스트 후처리");
	}
}
