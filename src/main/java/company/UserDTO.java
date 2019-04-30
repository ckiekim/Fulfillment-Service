package company;

public class UserDTO {
	private String uid;
	private String upass;
	private String uname;
	private int ucomId;
	private String ucomName;
	private int ucomRole;
	
	public UserDTO() { }
	public UserDTO(String uid, String upass, String uname, int ucomId) {
		super();
		this.uid = uid;
		this.upass = upass;
		this.uname = uname;
		this.ucomId = ucomId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUpass() {
		return upass;
	}
	public void setUpass(String upass) {
		this.upass = upass;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public int getUcomId() {
		return ucomId;
	}
	public void setUcomId(int ucomId) {
		this.ucomId = ucomId;
	}
	public String getUcomName() {
		return ucomName;
	}
	public void setUcomName(String ucomName) {
		this.ucomName = ucomName;
	}
	public int getUcomRole() {
		return ucomRole;
	}
	public void setUcomRole(int ucomRole) {
		this.ucomRole = ucomRole;
	}
	
}
