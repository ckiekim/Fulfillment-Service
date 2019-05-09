package inventory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import admin.*;

public class HandleInventory {
	private static final Logger LOG = LoggerFactory.getLogger(HandleInventory.class);

	public List<InventoryDTO> getMonthlyInventories(String month) {
		month = month.substring(0, 4) + month.substring(5);
		List<InventoryDTO> iList = readCSV(month);
		ProductDAO pDao = new ProductDAO();
		for (InventoryDTO iDto: iList) {
			ProductDTO pDto = pDao.getProductById(iDto.getIprodId());
			iDto.setIprodName(pDto.getPname());
			iDto.setIprice(pDto.getPprice());
			iDto.setIprodPrice(String.format("%,d", pDto.getPprice()));
			LOG.trace(iDto.toString());
		}
		return iList;
	}
	
	public void writeCSV(String month, List<InventoryDTO> iList) {
		Writer writer = null;
		BufferedWriter bw = null;
		try {
			writer = new FileWriter("c:/Temp/Inventories/" + month + ".csv");
			bw = new BufferedWriter(writer);
			for (InventoryDTO iDto: iList) {
				String line = iDto.getIid() + "," + iDto.getIprodId() + "," + iDto.getIbase() + "," + 
							iDto.getIinward() + "," + iDto.getIoutward() + "," + iDto.getIcurrent() + "\r\n";
				bw.write(line);
				LOG.debug(line);
			}
			bw.flush();
			bw.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<InventoryDTO> readCSV(String month) {
		List<InventoryDTO> iList = new ArrayList<InventoryDTO>();
		Reader reader = null;
		BufferedReader br = null;
		String line = null;
		try {
			reader = new FileReader("c:/Temp/Inventories/" + month + ".csv");
			br = new BufferedReader(reader);
			while ((line = br.readLine()) != null) {
				LOG.trace(line);
				InventoryDTO iDto = new InventoryDTO();
				String str[] = line.split(",");
				iDto.setIid(Integer.parseInt(str[0]));
				iDto.setIprodId(Integer.parseInt(str[1]));
				iDto.setIbase(Integer.parseInt(str[2]));
				iDto.setIinward(Integer.parseInt(str[3]));
				iDto.setIoutward(Integer.parseInt(str[4]));
				iDto.setIcurrent(Integer.parseInt(str[5]));
				iList.add(iDto);
			}
			br.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iList;
	}
}
