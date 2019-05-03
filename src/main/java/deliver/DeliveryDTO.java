package deliver;

public class DeliveryDTO {
	private int did;
	private int dcomId;
	private int dinvId;
	private String ddate;
	
	public DeliveryDTO() {
	}
	public DeliveryDTO(int dcomId, int dinvId, String ddate) {
		super();
		this.dcomId = dcomId;
		this.dinvId = dinvId;
		this.ddate = ddate;
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
	@Override
	public String toString() {
		return "DeliveryDTO [did=" + did + ", dcomId=" + dcomId + ", dinvId=" + dinvId + ", ddate=" + ddate + "]";
	}
}
