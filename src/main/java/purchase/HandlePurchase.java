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
		ProductDAO pDao = new ProductDAO();
		SoldProductDAO sDao = new SoldProductDAO();
		
		HandleDate hDate = new HandleDate();
		String date = hDate.getToday();
		List<PurchaseDTO> rList = rDao.getPurchaseListBySupplier(supplierId);
		for (PurchaseDTO rDto: rList) {
			// 전일 주문한 것만 처리
			InvoiceDTO vDto = vDao.getInvoiceById(rDto.getRinvId());
			String orderedDate = vDto.getVdate().substring(0, 10);
			if (orderedDate.equals(date)) 
				continue;
			// purchases 테이블 rdate, rstatus 필드 수정
			rDto.setRdate(date + " 10:00:00");
			rDto.setRstatus(PurchaseDAO.PURCHASE_SUPPLIED);
			rDao.updatePurchaseStatus(rDto);
			// products 테이블 pstock 필드 수정
			ProductDTO pDto = pDao.getProductById(rDto.getRprodId());
			pDto.setPstock(pDto.getPstock() + rDto.getRpstock());
			pDao.updateStock(pDto);
		}
		// invoices 테이블에서 지연 상태인 레코드를 Ready 상태로 변경
		List<InvoiceDTO> vList = vDao.getInvoicesByStatus(InvoiceDAO.INVOICE_DELAYED);	// 지연 상태인 송장을 가져옴
		for (InvoiceDTO vDto: vList) {
			LOG.debug(vDto.toString());
			boolean toUpdate = true;
			List<SoldProductDTO> sList = sDao.getSoldProducts(vDto.getVid());
			for (SoldProductDTO sDto: sList) {
				ProductDTO pDto = pDao.getProductById(sDto.getSprodId());
				if (sDto.getSquantity() > pDto.getPstock())
					toUpdate = false;
			}
			if (toUpdate) {
				vDto.setVstatus(InvoiceDAO.INVOICE_DELAY_READY);
				vDao.updateInvoice(vDto);
			}
		}
	}
}
