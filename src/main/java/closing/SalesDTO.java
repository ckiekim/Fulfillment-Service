package closing;

public class SalesDTO {
	private int lid;
	private int linvId;
	private int ldelId;
	private String lmonth;
	private int lrevenue;
	private int lnetSales;
	private int lcomId;
	private String lcomName;
	private int llogisId;
	private String llogisName;
	private String ltotalComma;
	
	public SalesDTO() {
	}
	public SalesDTO(int linvId, int ldelId, String lmonth, int lrevenue) {
		super();
		this.linvId = linvId;
		this.ldelId = ldelId;
		this.lmonth = lmonth;
		this.lrevenue = lrevenue;
	}
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public int getLinvId() {
		return linvId;
	}
	public void setLinvId(int linvId) {
		this.linvId = linvId;
	}
	public int getLdelId() {
		return ldelId;
	}
	public void setLdelId(int ldelId) {
		this.ldelId = ldelId;
	}
	public String getLmonth() {
		return lmonth;
	}
	public void setLmonth(String lmonth) {
		this.lmonth = lmonth;
	}
	public int getLrevenue() {
		return lrevenue;
	}
	public void setLrevenue(int lrevenue) {
		this.lrevenue = lrevenue;
	}
	public int getLnetSales() {
		return lnetSales;
	}
	public void setLnetSales(int lnetSales) {
		this.lnetSales = lnetSales;
	}
	public int getLcomId() {
		return lcomId;
	}
	public void setLcomId(int lcomId) {
		this.lcomId = lcomId;
	}
	public String getLcomName() {
		return lcomName;
	}
	public void setLcomName(String lcomName) {
		this.lcomName = lcomName;
	}
	public int getLlogisId() {
		return llogisId;
	}
	public void setLlogisId(int llogisId) {
		this.llogisId = llogisId;
	}
	public String getLlogisName() {
		return llogisName;
	}
	public void setLlogisName(String llogisName) {
		this.llogisName = llogisName;
	}
	public String getLtotalComma() {
		return ltotalComma;
	}
	public void setLtotalComma(String ltotalComma) {
		this.ltotalComma = ltotalComma;
	}
	@Override
	public String toString() {
		return "SalesDTO [lid=" + lid + ", linvId=" + linvId + ", ldelId=" + ldelId + ", lmonth=" + lmonth + ", lrevenue="
				+ lrevenue + ", lnetSales=" + lnetSales + ", lcomId=" + lcomId + ", lcomName=" + lcomName + ", llogisId="
				+ llogisId + ", llogisName=" + llogisName + ", ltotalComma=" + ltotalComma + "]";
	}
}
