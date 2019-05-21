package weather;

public class WeatherDTO {
	private String fcst_date;
	private String fcst_time;
	private String pop;
	private String pty;
	private String r06;
	private String reh;
	private String s06;
	private String sky;
	private String t3h;
	private String tmn;
	private String tmx;
	private String uuu;
	private String vvv;
	private String vec;
	private String wsd;
	private String base_date;
	private String base_time;
	
	public WeatherDTO() {
	}
	public WeatherDTO(String fcst_date, String fcst_time, String pop, String pty, String r06, String reh, String s06,
			String sky, String t3h, String tmn, String tmx, String uuu, String vvv, String vec, String wsd,
			String base_date, String base_time) {
		super();
		this.fcst_date = fcst_date;
		this.fcst_time = fcst_time;
		this.pop = pop;
		this.pty = pty;
		this.r06 = r06;
		this.reh = reh;
		this.s06 = s06;
		this.sky = sky;
		this.t3h = t3h;
		this.tmn = tmn;
		this.tmx = tmx;
		this.uuu = uuu;
		this.vvv = vvv;
		this.vec = vec;
		this.wsd = wsd;
		this.base_date = base_date;
		this.base_time = base_time;
	}

	public String getFcst_date() {
		return fcst_date;
	}
	public void setFcst_date(String fcst_date) {
		this.fcst_date = fcst_date;
	}
	public String getFcst_time() {
		return fcst_time;
	}
	public void setFcst_time(String fcst_time) {
		this.fcst_time = fcst_time;
	}
	public String getPop() {
		return pop;
	}
	public void setPop(String pop) {
		this.pop = pop;
	}
	public String getPty() {
		return pty;
	}
	public void setPty(String pty) {
		this.pty = pty;
	}
	public String getR06() {
		return r06;
	}
	public void setR06(String r06) {
		this.r06 = r06;
	}
	public String getReh() {
		return reh;
	}
	public void setReh(String reh) {
		this.reh = reh;
	}
	public String getS06() {
		return s06;
	}
	public void setS06(String s06) {
		this.s06 = s06;
	}
	public String getSky() {
		return sky;
	}
	public void setSky(String sky) {
		this.sky = sky;
	}
	public String getT3h() {
		return t3h;
	}
	public void setT3h(String t3h) {
		this.t3h = t3h;
	}
	public String getTmn() {
		return tmn;
	}
	public void setTmn(String tmn) {
		this.tmn = tmn;
	}
	public String getTmx() {
		return tmx;
	}
	public void setTmx(String tmx) {
		this.tmx = tmx;
	}
	public String getUuu() {
		return uuu;
	}
	public void setUuu(String uuu) {
		this.uuu = uuu;
	}
	public String getVvv() {
		return vvv;
	}
	public void setVvv(String vvv) {
		this.vvv = vvv;
	}
	public String getVec() {
		return vec;
	}
	public void setVec(String vec) {
		this.vec = vec;
	}
	public String getWsd() {
		return wsd;
	}
	public void setWsd(String wsd) {
		this.wsd = wsd;
	}
	public String getBase_date() {
		return base_date;
	}
	public void setBase_date(String base_date) {
		this.base_date = base_date;
	}
	public String getBase_time() {
		return base_time;
	}
	public void setBase_time(String base_time) {
		this.base_time = base_time;
	}
	@Override
	public String toString() {
		return "WeatherDTO [fcst_date=" + fcst_date + ", fcst_time=" + fcst_time + ", pop=" + pop + ", pty=" + pty
				+ ", r06=" + r06 + ", reh=" + reh + ", s06=" + s06 + ", sky=" + sky + ", t3h=" + t3h + ", tmn=" + tmn
				+ ", tmx=" + tmx + ", uuu=" + uuu + ", vvv=" + vvv + ", vec=" + vec + ", wsd=" + wsd + ", base_date="
				+ base_date + ", base_time=" + base_time + "]";
	}
}
