package user;

public class InitDTO {
	private String uid;
	private String uname;
	private int cid;
	private String cname;
	
	public InitDTO() { }
	public InitDTO(String uid, String uname, int cid, String cname) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.cid = cid;
		this.cname = cname;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
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
	@Override
	public String toString() {
		return "InitDTO [uid=" + uid + ", uname=" + uname + ", cid=" + cid + ", cname=" + cname + "]";
	}
}
