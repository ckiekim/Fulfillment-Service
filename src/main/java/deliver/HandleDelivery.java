package deliver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import admin.*;
import inventory.*;
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
	
	// 확정을 클릭하면 상태를 DELIVERY_CONFIRMED로 바꾸고 재고 수량을 변경함
	public void confirmDelivery(List<DeliveryDTO> dList) {
		DeliveryDAO dDao = new DeliveryDAO();
		SoldProductDAO sDao = new SoldProductDAO();
		InventoryDAO iDao = new InventoryDAO();
		InvoiceDAO vDao = new InvoiceDAO();
		
		for (DeliveryDTO dDto: dList) {
			dDto.setDstatus(DeliveryDAO.DELIVERY_CONFIRMED);
			dDao.updateDeliveryStatus(dDto);
			// Invoice 상태도 INVOICE_CONFIRMED로 수정
			InvoiceDTO vDto = vDao.getInvoiceById(dDto.getDinvId());
			vDto.setVstatus(InvoiceDAO.INVOICE_CONFIRMED);
			vDao.updateInvoice(vDto);
			// 재고 수량을 변경함
			List<SoldProductDTO> sList = sDao.getSoldProducts(dDto.getDinvId());
			for (SoldProductDTO sDto: sList) {
				InventoryDTO iDto = iDao.getInventoryByProduct(sDto.getSprodId());
				iDto.setIoutward(iDto.getIoutward() + sDto.getSquantity());
				iDto.setIcurrent(iDto.getIcurrent() - sDto.getSquantity());
				iDao.updateInventory(iDto);
			}
		}
	}
}
