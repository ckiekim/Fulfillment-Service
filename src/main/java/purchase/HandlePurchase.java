package purchase;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import admin.*;
import util.HandleDate;

public class HandlePurchase {
	private static final Logger LOG = LoggerFactory.getLogger(HandlePurchase.class);

	public void processPurchase(int supplierId) {
		PurchaseDAO rDao = new PurchaseDAO();
		InvoiceDAO vDao = new InvoiceDAO();
		
		HandleDate hDate = new HandleDate();
		String date = hDate.getToday();
		
		List<PurchaseDTO> rList = rDao.getPurchaseListBySupplier(supplierId);
		for (PurchaseDTO rDto: rList) {
			InvoiceDTO vDto = vDao.getInvoiceById(rDto.getRinvId());
			String orderedDate = vDto.getVdate().substring(0, 10);
			if (orderedDate.equals(date)) 
				continue;
			// purchases 테이블 rdate, rstatus 필드 수정
			
			// products 테이블 pstock 필드 수정
			
			// invoices 테이블에서 지연 상태인 레코드 처리
		}
	}
}
