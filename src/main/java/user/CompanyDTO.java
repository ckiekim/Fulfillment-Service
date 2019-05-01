package user;

public class CompanyDTO {
	private int cid;
	private String cname;
	private int crole;
	private String ctel;
	private String cfax;
	private String cmail;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public int getCrole() {
		return crole;
	}
	public void setCrole(int crole) {
		this.crole = crole;
	}
	public String getCtel() {
		return ctel;
	}
	public void setCtel(String ctel) {
		this.ctel = ctel;
	}
	public String getCfax() {
		return cfax;
	}
	public void setCfax(String cfax) {
		this.cfax = cfax;
	}
	public String getCmail() {
		return cmail;
	}
	public void setCmail(String cmail) {
		this.cmail = cmail;
	}
	@Override
	public String toString() {
		return "CompanyDTO [cid=" + cid + ", cname=" + cname + ", crole=" + crole + ", ctel=" + ctel + ", cfax=" + cfax
				+ ", cmail=" + cmail + "]";
	}
}
