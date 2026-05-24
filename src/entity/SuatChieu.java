package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class SuatChieu {

	private String maSuat;
	private Phim phim;
	private LocalDate ngayChieu;
	private Phong phong;
	private LocalDateTime thoiGian;
	
	public String getMaSuat() {
		return maSuat;
	}
	public void setMaSuat(String maSuat) {
		this.maSuat = maSuat;
	}
	public Phim getPhim() {
		return phim;
	}
	public void setPhim(Phim phim) {
		this.phim = phim;
	}
	public LocalDate getNgayChieu() {
		return ngayChieu;
	}
	public void setNgayChieu(LocalDate ngayChieu) {
		this.ngayChieu = ngayChieu;
	}
	public Phong getPhong() {
		return phong;
	}
	public void setPhong(Phong phong) {
		this.phong = phong;
	}
	public LocalDateTime getThoiGian() {
		return thoiGian;
	}
	public void setThoiGian(LocalDateTime thoiGian) {
		this.thoiGian = thoiGian;
	}
	public SuatChieu(String maSuat, Phim phim, LocalDate ngayChieu, Phong phong, LocalDateTime thoiGian) {
		super();
		this.maSuat = maSuat;
		this.phim = phim;
		this.ngayChieu = ngayChieu;
		this.phong = phong;
		this.thoiGian = thoiGian;
	}
	
	public SuatChieu(String maSuat) {
		super();
		this.maSuat = maSuat;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maSuat);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuatChieu other = (SuatChieu) obj;
		return Objects.equals(maSuat, other.maSuat);
	}
	
	@Override
	public String toString() {
		return "SuatChieu [maSuat=" + maSuat + ", thoiGian=" + thoiGian + ", ngayChieu=" + ngayChieu + ", phim=" + phim
				+ ", phong=" + phong + "]";
	}
	public SuatChieu() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}