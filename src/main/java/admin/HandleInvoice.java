package admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import user.UserDAO;

public class HandleInvoice {
	private static final Logger LOG = LoggerFactory.getLogger(HandleInvoice.class);

	public void handleFile() {
		File dir = new File("c:/temp/Invoices");
		File[] invFiles = dir.listFiles();
		Reader reader = null;
		String line = null;
		BufferedReader br = null;
		UserDAO uDao = new UserDAO();
		InvoiceDTO vDto = null;
		List<SoldProductDTO> sList = null;
		
		for (File invFile: invFiles) {
			if (invFile.isDirectory())
				continue;
			String s = invFile.getName().substring(0, 12);
			String date = s.substring(0,4) + "-" + s.substring(4,6) + "-" + s.substring(6,8)
							+ " " + s.substring(8,10) + ":" + s.substring(10) + ":00";
			String mall = invFile.getName().substring(12, 14);
			int mallId = uDao.getCompanyCode(mall);
			LOG.debug("{}, {}", date, mallId);
			try {
				reader = new FileReader(invFile);
				br = new BufferedReader(reader);
				boolean toOrder = false;
				while ((line = br.readLine()) != null) {
					LOG.trace(line);
					String str[] = line.split(",");
					if (str[0].length() > 0) {
						if (toOrder) {
							writeDB(vDto, sList);
						}
						toOrder = true;
						vDto = new InvoiceDTO(str[0], str[1], str[2], mallId, date, selectLogis(str[2]));
						LOG.trace(vDto.toString());
						sList = new ArrayList<SoldProductDTO>();
					}
					SoldProductDTO sDto = new SoldProductDTO(0, Integer.parseInt(str[3]), Integer.parseInt(str[5]));
					LOG.trace(sDto.toString());
					sList.add(sDto);
				}
				writeDB(vDto, sList);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	        try {
	        	invFile.renameTo(new File("c:/temp/Invoices/Backup/" + invFile.getName())); 
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	void writeDB(InvoiceDTO vDto, List<SoldProductDTO> sList) {
		InvoiceDAO vDao = new InvoiceDAO();
		SoldProductDAO sDao = new SoldProductDAO();
		ProductDAO pDao = new ProductDAO();
		
		vDao.insertInvoice(vDto);
		vDto = vDao.getLastInvoice();
		LOG.trace(vDto.toString());
		for (SoldProductDTO sDto: sList) {
			sDto.setSinvId(vDto.getVid());
			sDao.insertSoldProduct(sDto);
			ProductDTO pDto = pDao.getProductById(sDto.getSprodId());
			int criteria = pDto.getPstock() - sDto.getSquantity();
			LOG.trace("{}, {}, {}", criteria, pDto.getPstock(), sDto.getSquantity());
			if (criteria < 0) {
				vDto.setVstatus(1);		// 0-출고대기, 1-출고지연(재고부족), 2-출고실행, 3-출고완료
			} else if (criteria < 10) {
				// 발주 요청 목록에 등록
			}
		}
		int total = 0;
		sList = sDao.getSoldProducts(vDto.getVid());
		for (SoldProductDTO sDto: sList) {
			total += sDto.getSprice() * sDto.getSquantity();
			LOG.trace(sDto.toString());
		}
		vDto.setVtotal(total);
		LOG.trace(vDto.toString());
		vDao.updateInvoice(vDto);
	}
	
	int selectLogis(String addr) {
		int logisId = 0;
		char ad = addr.charAt(0);
		
		if (ad=='서' || ad=='인') 
			logisId = 1002;
		else if (ad=='충' || ad=='세' || ad=='강')
			logisId = 1003;
		else if (ad=='부' || ad=='울')
			logisId = 1004;
		else if (ad=='광' || ad=='전' || ad=='제')
			logisId = 1005;
		else if (ad=='경') {
			if (addr.charAt(1) == '기')
				logisId = 1002;
			else
				logisId = 1004;
		} else if (ad=='대') {
			if (addr.charAt(1) == '전')
				logisId = 1003;
			else
				logisId = 1004;
		}
		return logisId;
	}
}
