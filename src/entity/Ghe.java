package entity;

public class Ghe {

	private String maGhe;
	private boolean loaiGhe;
	private int trangThai;
	private SuatChieu suat;
	
	public boolean isLoaiGhe() {
		return loaiGhe;
	}
	public void setLoaiGhe(boolean hangGhe) {
		this.loaiGhe = hangGhe;
	}
	public int isTrangThai() {
		return trangThai;
	}
	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}
	public String getMaGhe() {
		return maGhe;
	}
	public void setMaGhe(String maGhe) {
		this.maGhe = maGhe;
	}
	public SuatChieu getSuat() {
		return suat;
	}
	public void setSuat(SuatChieu suat) {
		this.suat = suat;
	}
	
	public Ghe(String maGhe, boolean hangGhe, int trangThai, SuatChieu suat) {
		super();
		this.maGhe = maGhe;
		this.loaiGhe = hangGhe;
		this.trangThai = trangThai;
		this.suat = suat;
	}
	
	public int getTrangThai() {
		return trangThai;
	}
	public Ghe() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Ghe(String maGhe) {
		super();
		this.maGhe = maGhe;
	}
	@Override
	public String toString() {
		return "Ghe [maGhe=" + maGhe + ", hangGhe=" + loaiGhe + ", trangThai=" + trangThai + ", suat=" + suat + "]";
	}
	
	
}
