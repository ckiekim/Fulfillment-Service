package purchase;

public class PurchaseDTO {
	private int rid;
	private int rcomId;
	private int rinvId;
	private int rprodId;
	private int rquantity;
	private String rdate;
	private int rstatus;
	private String rprodName;
	private int rprice;
	private int rpstock;
	private String rcomName;
	private String rstatusName;
	
	public PurchaseDTO() {
	}
	public PurchaseDTO(int rcomId, int rinvId, int rprodId, int rquantity) {
		this.rcomId = rcomId;
		this.rinvId = rinvId;
		this.rprodId = rprodId;
		this.rquantity = rquantity;
	}
	public PurchaseDTO(int rcomId, int rinvId, int rprodId, int rquantity, String rdate) {
		this.rcomId = rcomId;
		this.rinvId = rinvId;
		this.rprodId = rprodId;
		this.rquantity = rquantity;
		this.rdate = rdate;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getRcomId() {
		return rcomId;
	}
	public void setRcomId(int rcomId) {
		this.rcomId = rcomId;
	}
	public int getRinvId() {
		return rinvId;
	}
	public void setRinvId(int rinvId) {
		this.rinvId = rinvId;
	}
	public int getRprodId() {
		return rprodId;
	}
	public void setRprodId(int rprodId) {
		this.rprodId = rprodId;
	}
	public int getRquantity() {
		return rquantity;
	}
	public void setRquantity(int rquantity) {
		this.rquantity = rquantity;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public int getRstatus() {
		return rstatus;
	}
	public void setRstatus(int rstatus) {
		this.rstatus = rstatus;
	}
	public String getRprodName() {
		return rprodName;
	}
	public void setRprodName(String rprodName) {
		this.rprodName = rprodName;
	}
	public int getRprice() {
		return rprice;
	}
	public void setRprice(int rprice) {
		this.rprice = rprice;
	}
	public int getRpstock() {
		return rpstock;
	}
	public void setRpstock(int rpstock) {
		this.rpstock = rpstock;
	}
	public String getRcomName() {
		return rcomName;
	}
	public void setRcomName(String rcomName) {
		this.rcomName = rcomName;
	}
	public String getRstatusName() {
		return rstatusName;
	}
	public void setRstatusName(String rstatusName) {
		this.rstatusName = rstatusName;
	}
	@Override
	public String toString() {
		return "PurchaseDTO [rid=" + rid + ", rcomId=" + rcomId + ", rinvId=" + rinvId + ", rprodId=" + rprodId
				+ ", rquantity=" + rquantity + ", rdate=" + rdate + ", rstatus=" + rstatus + ", rprodName=" + rprodName
				+ ", rprice=" + rprice + ", rpstock=" + rpstock + ", rcomName=" + rcomName + ", rstatusName="
				+ rstatusName + "]";
	}
}
