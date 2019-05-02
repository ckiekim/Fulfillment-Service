package admin;

public class InvoiceDTO {
	private int vid;
	private String vname;
	private String vtel;
	private String vaddr;
	private int vcomId;
	private String vdate;
	private int vtotal;
	private String vcomName;
	
	public InvoiceDTO() {
	}
	public InvoiceDTO(String vname, String vtel, String vaddr, int vcomId, String vdate) {
		this.vname = vname;
		this.vtel = vtel;
		this.vaddr = vaddr;
		this.vcomId = vcomId;
		this.vdate = vdate;
	}
	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	public String getVtel() {
		return vtel;
	}
	public void setVtel(String vtel) {
		this.vtel = vtel;
	}
	public String getVaddr() {
		return vaddr;
	}
	public void setVaddr(String vaddr) {
		this.vaddr = vaddr;
	}
	public int getVcomId() {
		return vcomId;
	}
	public void setVcomId(int vcomId) {
		this.vcomId = vcomId;
	}
	public String getVdate() {
		return vdate;
	}
	public void setVdate(String vdate) {
		this.vdate = vdate;
	}
	public int getVtotal() {
		return vtotal;
	}
	public void setVtotal(int vtotal) {
		this.vtotal = vtotal;
	}
	public String getVcomName() {
		return vcomName;
	}
	public void setVcomName(String vcomName) {
		this.vcomName = vcomName;
	}
	@Override
	public String toString() {
		return "InvoiceDTO [vid=" + vid + ", vname=" + vname + ", vtel=" + vtel + ", vaddr=" + vaddr + ", vcomId="
				+ vcomId + ", vdate=" + vdate + ", vtotal=" + vtotal + ", vcomName=" + vcomName + "]";
	}
}
