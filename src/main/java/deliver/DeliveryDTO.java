package deliver;

public class DeliveryDTO {
	private int did;
	private int dcomId;
	private int dinvId;
	private String ddate;
	private int dstatus;
	private String dname;
	private String daddr;
	private String dcomName;
	
	public DeliveryDTO() {
	}
	public DeliveryDTO(int dcomId, int dinvId, String ddate, int dstatus) {
		super();
		this.dcomId = dcomId;
		this.dinvId = dinvId;
		this.ddate = ddate;
		this.dstatus = dstatus;
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public int getDcomId() {
		return dcomId;
	}
	public void setDcomId(int dcomId) {
		this.dcomId = dcomId;
	}
	public int getDinvId() {
		return dinvId;
	}
	public void setDinvId(int dinvId) {
		this.dinvId = dinvId;
	}
	public String getDdate() {
		return ddate;
	}
	public void setDdate(String ddate) {
		this.ddate = ddate;
	}
	public int getDstatus() {
		return dstatus;
	}
	public void setDstatus(int dstatus) {
		this.dstatus = dstatus;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getDaddr() {
		return daddr;
	}
	public void setDaddr(String daddr) {
		this.daddr = daddr;
	}
	public String getDcomName() {
		return dcomName;
	}
	public void setDcomName(String dcomName) {
		this.dcomName = dcomName;
	}
	@Override
	public String toString() {
		return "DeliveryDTO [did=" + did + ", dcomId=" + dcomId + ", dinvId=" + dinvId + ", ddate=" + ddate
				+ ", dstatus=" + dstatus + ", dname=" + dname + ", daddr=" + daddr + ", dcomName=" + dcomName + "]";
	}
}
