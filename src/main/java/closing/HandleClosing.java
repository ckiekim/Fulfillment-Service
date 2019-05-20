package closing;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import admin.*;
import deliver.*;
import purchase.*;

public class HandleClosing {
	private static final Logger LOG = LoggerFactory.getLogger(HandleClosing.class);
	public static final int READY = 0;
	public static final int EXECUTE = 1;

	public ClosingDTO processClosing(int sellers[], int logistics[], int suppliers[], String month, int flag) {
		if (flag == READY) {
			ClosingDAO cDao = new ClosingDAO();
			List<RecordDTO> recList = cDao.getRecordsByCompany(ClosingDAO.ROLE_COMPANY, month);
			for (RecordDTO rDto: recList)
				cDao.deleteRecord(rDto.getRid());
		}
		int incomeTotal = closeSales(sellers, month, flag);
		int logisTotal = closeDeliveries(logistics, month, flag);
		int purchaseTotal = closePurchases(suppliers, month, flag);
		int grossMargin = incomeTotal - logisTotal - purchaseTotal;
		LOG.trace("{}: {}, {}, {}, {}", month, incomeTotal, logisTotal, purchaseTotal, grossMargin);
		
		ClosingDTO cDto = new ClosingDTO(incomeTotal, logisTotal, purchaseTotal, grossMargin);
		cDto.setIncomeComma(String.format("%,d", incomeTotal));
		cDto.setLogisExpenseComma(String.format("%,d", logisTotal));
		cDto.setPurchaseExpenseComma(String.format("%,d", purchaseTotal));
		cDto.setGrossMarginComma(String.format("%,d", grossMargin));
		return cDto;
	}
	
	public ClosingDTO showClosing(String month) {
		ClosingDAO cDao = new ClosingDAO();
		List<RecordDTO> recList = null;
		
		int incomeTotal = 0;
		recList = cDao.getRecordsByCompany(ClosingDAO.ROLE_SELLER, month);
		for (RecordDTO rDto: recList)
			incomeTotal += rDto.getRdata();
		
		int logisTotal = 0;
		recList = cDao.getRecordsByCompany(ClosingDAO.ROLE_LOGISTICS, month);
		for (RecordDTO rDto: recList)
			logisTotal += rDto.getRdata();
		
		int purchaseTotal = 0;
		recList = cDao.getRecordsByCompany(ClosingDAO.ROLE_SUPPLIER, month);
		for (RecordDTO rDto: recList)
			purchaseTotal += rDto.getRdata();
		
		int grossMargin = incomeTotal - logisTotal - purchaseTotal;
		LOG.trace("{}: {}, {}, {}, {}", month, incomeTotal, logisTotal, purchaseTotal, grossMargin);
		
		ClosingDTO cDto = new ClosingDTO(incomeTotal, logisTotal, purchaseTotal, grossMargin);
		cDto.setIncomeComma(String.format("%,d", incomeTotal));
		cDto.setLogisExpenseComma(String.format("%,d", logisTotal));
		cDto.setPurchaseExpenseComma(String.format("%,d", purchaseTotal));
		cDto.setGrossMarginComma(String.format("%,d", grossMargin));
		return cDto;
	}
	
	public int closeSales(int comIds[], String month, int flag) {
		ClosingDAO cDao = new ClosingDAO();
		InvoiceDAO vDao = new InvoiceDAO();
		DeliveryDAO dDao = new DeliveryDAO();
		int salesTotal = 0;
		
		for (int i=0; i<comIds.length; i++) {
			int sum = 0;
			List<InvoiceDTO> vList = vDao.getInvoicesByMonth(comIds[i], month+"-01");
			for (InvoiceDTO vDto: vList) {
				if (flag == EXECUTE) {
					vDto.setVstatus(InvoiceDAO.INVOICE_CLOSED);
					vDao.updateInvoice(vDto);
				}
				int deliveryId = dDao.getDeliveryIdByInvoice(vDto.getVid());
				int revenue = (int)(vDto.getVtotal() * 1.1) + 10000;
				sum += revenue;
				if (flag == EXECUTE) {
					SalesDTO lDto = new SalesDTO(vDto.getVid(), deliveryId, month, revenue);
					cDao.insertSales(lDto);
					LOG.trace(lDto.toString());
				}
			}
			RecordDTO rDto = new RecordDTO(comIds[i], ClosingDAO.ROLE_SELLER, month, sum);
			if (flag == READY)
				cDao.insertRecord(rDto);
			LOG.debug(rDto.toString());
			salesTotal += sum;
		}
		return salesTotal;
	}
	
	public int closeDeliveries(int logisIds[], String month, int flag) {
		ClosingDAO cDao = new ClosingDAO();
		DeliveryDAO dDao = new DeliveryDAO();
		int logisTotal = 0;
		
		for (int i=0; i<logisIds.length; i++) {
			int sum = 0;
			List<DeliveryDTO> dList = dDao.getDeliveryListByMonth(logisIds[i], month+"-01", 0);
			for (DeliveryDTO dDto: dList) {
				if (flag == EXECUTE) {
					dDto.setDstatus(DeliveryDAO.DELIVERY_CLOSED);
					dDao.updateDeliveryStatus(dDto);
				}
				sum += 10000;
			}
			RecordDTO rDto = new RecordDTO(logisIds[i], ClosingDAO.ROLE_LOGISTICS, month, sum);
			if (flag == READY)
				cDao.insertRecord(rDto);
			LOG.debug(rDto.toString());
			logisTotal += sum;
		}
		return logisTotal;
	}
	
	public int closePurchases(int suppIds[], String month, int flag) {
		ClosingDAO cDao = new ClosingDAO();
		PurchaseDAO rDao = new PurchaseDAO();
		int purchaseTotal = 0;
		
		for (int i=0; i<suppIds.length; i++) {
			int sum = 0;
			List<PurchaseDTO> rList = rDao.getSuppliedListByMonth(suppIds[i], month+"-01", 0);
			for (PurchaseDTO rDto: rList) {
				if (flag == EXECUTE) {
					rDto.setRstatus(PurchaseDAO.PURCHASE_CLOSED);
					rDao.updatePurchaseStatus(rDto);
				}
				sum += rDto.getRprice() * rDto.getRquantity();
			}
			RecordDTO rDto = new RecordDTO(suppIds[i], ClosingDAO.ROLE_SUPPLIER, month, sum);
			if (flag == READY)
				cDao.insertRecord(rDto);
			LOG.debug(rDto.toString());
			purchaseTotal += sum;
		}
		return purchaseTotal;
	}
}
