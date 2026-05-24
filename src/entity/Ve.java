package entity;

import java.time.LocalDate;

public class Ve {
	private String maVe;
	private double giaVe;
	private LocalDate ngayDat;
	private String trangThai;

	private NhanVien nhanVien;
	private Ghe ghe;
	private SuatChieu suatChieu;
	
	public static final double GIA_VIP = 70000;
    public static final double GIA_THUONG = 50000;
	
	public String getMaVe() {
		return maVe;
	}

	public void setMaVe(String maVe) {
		this.maVe = maVe;
	}

	public double getGiaVe() {
		return giaVe;
	}

	public void setGiaVe(double giaVe) {
		this.giaVe = giaVe;
	}

	public LocalDate getNgayDat() {
		return ngayDat;
	}

	public void setNgayDat(LocalDate ngayDat) {
		this.ngayDat = ngayDat;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public Ghe getGhe() {
		return ghe;
	}

	public void setGhe(Ghe ghe) {
		this.ghe = ghe;
		this.giaVe = tinhGiaVe();
	}

	public SuatChieu getSuatChieu() {
		return suatChieu;
	}

	public void setSuatChieu(SuatChieu suatChieu) {
		this.suatChieu = suatChieu;
	}

	public Ve(String maVe, LocalDate ngayDat, String trangThai,
	          NhanVien nhanVien, Ghe ghe, SuatChieu suatChieu) {

	    this.maVe = maVe;
	    this.ngayDat = ngayDat;
	    this.trangThai = trangThai;
	    this.nhanVien = nhanVien;
	    this.ghe = ghe;
	    this.suatChieu = suatChieu;
	    this.giaVe = tinhGiaVe();
	}
	
	public Ve() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Ve(String maVe) {
		super();
		this.maVe = maVe;
	}

	public double tinhGiaVe() {
        if (ghe == null) return 0;

        if (ghe.isLoaiGhe()) {
            return GIA_VIP;       
        } else {
            return GIA_THUONG;  
        }
    }

	@Override
	public String toString() {
	    return "VeXemPhim [maVe=" + maVe 
	            + ", giaVe=" + giaVe 
	            + ", ngayDat=" + ngayDat 
	            + ", trangThai=" + trangThai 
	            + ", nhanVien=" + (nhanVien != null ? nhanVien.getMaNhanVien() : "null")
	            + ", ghe=" + (ghe != null ? ghe.getMaGhe() : "null")
	            + " (" + (ghe != null ? (ghe.isLoaiGhe() ? "VIP" : "Thuong") : "null") + ")"
	            + ", suatChieu=" + (suatChieu != null ? suatChieu.getMaSuat() : "null")
	            + "]";
	}
}
