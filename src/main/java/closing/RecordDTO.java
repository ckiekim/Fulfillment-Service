package closing;

public class RecordDTO {
	private int rid;
	private int rcomId;
	private int rrole;
	private String rmonth;
	private int rdata;
	private String rcomName;
	private String rroleName;
	public String getRroleName() {
		return rroleName;
	}
	public void setRroleName(String rroleName) {
		this.rroleName = rroleName;
	}
	private String rdataComma;
	
	public RecordDTO() {
	}
	public RecordDTO(int rcomId, int rrole, String rmonth, int rdata) {
		super();
		this.rcomId = rcomId;
		this.rrole = rrole;
		this.rmonth = rmonth;
		this.rdata = rdata;
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
	public int getRrole() {
		return rrole;
	}
	public void setRrole(int rrole) {
		this.rrole = rrole;
	}
	public String getRmonth() {
		return rmonth;
	}
	public void setRmonth(String rmonth) {
		this.rmonth = rmonth;
	}
	public int getRdata() {
		return rdata;
	}
	public void setRdata(int rdata) {
		this.rdata = rdata;
	}
	public String getRcomName() {
		return rcomName;
	}
	public void setRcomName(String rcomName) {
		this.rcomName = rcomName;
	}
	public String getRdataComma() {
		return rdataComma;
	}
	public void setRdataComma(String rdataComma) {
		this.rdataComma = rdataComma;
	}
	@Override
	public String toString() {
		return "RecordDTO [rid=" + rid + ", rcomId=" + rcomId + ", rrole=" + rrole + ", rmonth=" + rmonth + ", rdata="
				+ rdata + ", rcomName=" + rcomName + ", rroleName=" + rroleName + ", rdataComma=" + rdataComma + "]";
	}
}
