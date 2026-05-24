package entity;

public class Phim {

	private String maPhim;
	private String tenPhim;
	private int thoiLuong;
	private String theLoai;
	private String gioiHanTuoi;
	private String ngonNgu;
	
	public String getMaPhim() {
		return maPhim;
	}

	public void setMaPhim(String maPhim) {
		this.maPhim = maPhim;
	}

	public String getTenPhim() {
		return tenPhim;
	}

	public void setTenPhim(String tenPhim) {
		this.tenPhim = tenPhim;
	}

	public int getThoiLuong() {
		return thoiLuong;
	}

	public void setThoiLuong(int thoiLuong) {
		this.thoiLuong = thoiLuong;
	}

	public String getTheLoai() {
		return theLoai;
	}

	public void setTheLoai(String theLoai) {
		this.theLoai = theLoai;
	}

	public String getGioiHanTuoi() {
		return gioiHanTuoi;
	}

	public void setGioiHanTuoi(String gioiHanTuoi) {
		this.gioiHanTuoi = gioiHanTuoi;
	}

	public String getNgonNgu() {
		return ngonNgu;
	}

	public void setNgonNgu(String ngonNgu) {
		this.ngonNgu = ngonNgu;
	}

	public Phim(String maPhim, String tenPhim, int thoiLuong, String theLoai, String gioiHanTuoi, String ngonNgu) {
		super();
		this.maPhim = maPhim;
		this.tenPhim = tenPhim;
		this.thoiLuong = thoiLuong;
		this.theLoai = theLoai;
		this.gioiHanTuoi = gioiHanTuoi;
		this.ngonNgu = ngonNgu;
	}

	public Phim() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Phim(String maPhim) {
		super();
		this.maPhim = maPhim;
	}

	@Override
	public String toString() {
		return "Phim [maPhim=" + maPhim + ", tenPhim=" + tenPhim + ", theLoai=" + theLoai + ", gioiHanTuoi="
				+ gioiHanTuoi + ", thoiLuong=" + thoiLuong + ", ngonNgu=" + ngonNgu + "]";
	}

	
	
	
	
}