package admin;

public class SoldProductDTO {
	private int sinvId;
	private int sprodId;
	private int squantity;
	private String sprodName;
	private int sprice;
	
	public SoldProductDTO() {
	}
	public SoldProductDTO(int sinvId, int sprodId, int squantity) {
		this.sinvId = sinvId;
		this.sprodId = sprodId;
		this.squantity = squantity;
	}
	public int getSinvId() {
		return sinvId;
	}
	public void setSinvId(int sinvId) {
		this.sinvId = sinvId;
	}
	public int getSprodId() {
		return sprodId;
	}
	public void setSprodId(int sprodId) {
		this.sprodId = sprodId;
	}
	public int getSquantity() {
		return squantity;
	}
	public void setSquantity(int squantity) {
		this.squantity = squantity;
	}
	public String getSprodName() {
		return sprodName;
	}
	public void setSprodName(String sprodName) {
		this.sprodName = sprodName;
	}
	public int getSprice() {
		return sprice;
	}
	public void setSprice(int sprice) {
		this.sprice = sprice;
	}
	@Override
	public String toString() {
		return "SoldProductDTO [sinvId=" + sinvId + ", sprodId=" + sprodId + ", squantity=" + squantity + "]";
	}
}
