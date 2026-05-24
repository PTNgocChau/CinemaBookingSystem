package entity;

public class Phong {

	private String maPhong;
	private int sucChua;
	private Ghe ghe;
	
	public String getMaPhong() {
		return maPhong;
	}
	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}
	public int getSucChua() {
		return sucChua;
	}
	public void setSucChua(int sucChua) {
		this.sucChua = sucChua;
	}
	public Ghe getGhe() {
		return ghe;
	}
	public void setGhe(Ghe ghe) {
		this.ghe = ghe;
	}
	
	public Phong(String maPhong, int sucChua, Ghe ghe) {
		super();
		this.maPhong = maPhong;
		this.sucChua = sucChua;
		this.ghe = ghe;
	}
	
	public Phong() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Phong(String maPhong) {
		super();
		this.maPhong = maPhong;
	}
	@Override
	public String toString() {
		return "Phong [maPhong=" + maPhong + ", sucChua=" + sucChua + ", ghe=" + ghe + "]";
	}
	
	
	
	
}
