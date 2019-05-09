package inventory;

public class InventoryDTO {
	private int iid;
	private int iprodId;
	private int ibase;
	private int iinward;
	private int ioutward;
	private int icurrent;
	private String iprodName;
	private int iprice;
	private String iprodPrice;
	
	public int getIid() {
		return iid;
	}
	public void setIid(int iid) {
		this.iid = iid;
	}
	public int getIprodId() {
		return iprodId;
	}
	public void setIprodId(int iprodId) {
		this.iprodId = iprodId;
	}
	public int getIbase() {
		return ibase;
	}
	public void setIbase(int ibase) {
		this.ibase = ibase;
	}
	public int getIinward() {
		return iinward;
	}
	public void setIinward(int iinward) {
		this.iinward = iinward;
	}
	public int getIoutward() {
		return ioutward;
	}
	public void setIoutward(int ioutward) {
		this.ioutward = ioutward;
	}
	public int getIcurrent() {
		return icurrent;
	}
	public void setIcurrent(int icurrent) {
		this.icurrent = icurrent;
	}
	public String getIprodName() {
		return iprodName;
	}
	public void setIprodName(String iprodName) {
		this.iprodName = iprodName;
	}
	public int getIprice() {
		return iprice;
	}
	public void setIprice(int iprice) {
		this.iprice = iprice;
	}
	public String getIprodPrice() {
		return iprodPrice;
	}
	public void setIprodPrice(String iprodPrice) {
		this.iprodPrice = iprodPrice;
	}
	@Override
	public String toString() {
		return "InventoryDTO [iid=" + iid + ", iprodId=" + iprodId + ", ibase=" + ibase + ", iinward=" + iinward
				+ ", ioutward=" + ioutward + ", icurrent=" + icurrent + ", iprodName=" + iprodName + ", iprice="
				+ iprice + ", iprodPrice=" + iprodPrice + "]";
	}
}
