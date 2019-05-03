package deliver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import admin.*;

public class HandleDelivery {
	private static final Logger LOG = LoggerFactory.getLogger(HandleDelivery.class);

	public void processDelivery(String time, int logisId) {
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd", Locale.KOREA);
		String date = formatter.format(new Date());
		if (time.equals("am"))
			date += " 09:00:00";
		else
			date += " 18:00:00";
		
		DeliveryDAO dDao = new DeliveryDAO();
		List<InvoiceDTO> vList = dDao.getInvoicesByLogis(logisId);
		// invoices table에 vstatus field 변경: 0 --> 2
		// deliveries table에 등록
		DeliveryDTO dDto = null;
		LOG.debug(dDto.toString());
	}
}
