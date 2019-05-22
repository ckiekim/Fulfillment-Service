package weather;

public class WeatherDTO {
	private String fcstDate;	// 예보 일자
	private String fcstTime;	// 예보 시각
	private String baseDate;	// 발표 일자
	private String baseTime;	// 발표 시각
	private String pop;		// 강수 확률
	private String pty;		// 강수 형태
	private String r06;		// 6시간 강수량
	private String reh;		// 습도
	private String s06;		// 6시간 신적설
	private String sky;		// 하늘 상태
	private String t3h;		// 3시간 기온
	private String tmn;		// 아침 최저기온
	private String tmx;		// 낮 최고기온
	private String uuu;		// 풍속(동서성분)
	private String vvv;		// 풍속(남북성분)
	private String wav;		// 파고
	private String vec;		// 풍향
	private String wsd;		// 풍속
	
	public WeatherDTO() {
	}
	public WeatherDTO(String fcstDate, String fcstTime, String baseDate, String baseTime, String pop, String pty,
			String r06, String reh, String s06, String sky, String t3h, String tmn, String tmx, String uuu, String vvv,
			String wav, String vec, String wsd) {
		this.fcstDate = fcstDate;
		this.fcstTime = fcstTime;
		this.baseDate = baseDate;
		this.baseTime = baseTime;
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
		this.wav = wav;
		this.vec = vec;
		this.wsd = wsd;
	}
	@Override
	public String toString() {
		return "WeatherDTO [fcstDate=" + fcstDate + ", fcstTime=" + fcstTime + ", baseDate=" + baseDate + ", baseTime="
				+ baseTime + ", pop=" + pop + ", pty=" + pty + ", r06=" + r06 + ", reh=" + reh + ", s06=" + s06
				+ ", sky=" + sky + ", t3h=" + t3h + ", tmn=" + tmn + ", tmx=" + tmx + ", uuu=" + uuu + ", vvv=" + vvv
				+ ", wav=" + wav + ", vec=" + vec + ", wsd=" + wsd + "]";
	}
	public String getFcstDate() {
		return fcstDate;
	}
	public void setFcstDate(String fcstDate) {
		this.fcstDate = fcstDate;
	}
	public String getFcstTime() {
		return fcstTime;
	}
	public void setFcstTime(String fcstTime) {
		this.fcstTime = fcstTime;
	}
	public String getBaseDate() {
		return baseDate;
	}
	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}
	public String getBaseTime() {
		return baseTime;
	}
	public void setBaseTime(String baseTime) {
		this.baseTime = baseTime;
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
	public String getWav() {
		return wav;
	}
	public void setWav(String wav) {
		this.wav = wav;
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
}
