package deliver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import admin.*;
import util.*;

public class HandleDelivery {
	private static final Logger LOG = LoggerFactory.getLogger(HandleDelivery.class);

	public void processDelivery(String time, int logisId) {
		HandleDate hDate = new HandleDate();
		String date = hDate.getToday();
		date += hDate.getNumericTime(time);
		
		DeliveryDAO dDao = new DeliveryDAO();
		List<InvoiceDTO> vList = dDao.getInvoicesByLogis(logisId);
		for (InvoiceDTO vDto: vList) {
			if (vDto.getVstatus() == InvoiceDAO.INVOICE_DELAYED || vDto.getVstatus() == InvoiceDAO.INVOICE_DELAY_READY)
				continue;
			// 주문 시각이 출고처리 시각보다 같거나 늦으면 처리하지 않음
			if (vDto.getVdate().compareTo(date) >= 0) {
				LOG.debug("{}, {}, {}", vDto.getVdate(), date, vDto.getVdate().compareTo(date));
				continue;
			}
			// invoices table에 vstatus field 변경: READY --> RELEASED
			vDto.setVstatus(InvoiceDAO.INVOICE_RELEASED);
			dDao.updateInvoiceStatus(vDto);
			// deliveries table에 등록
			DeliveryDTO dDto = new DeliveryDTO(logisId, vDto.getVid(), date, DeliveryDAO.DELIVERY_EXECUTED);
			dDao.insertDelivery(dDto);
			LOG.trace(dDto.toString());
		}
	}
}
