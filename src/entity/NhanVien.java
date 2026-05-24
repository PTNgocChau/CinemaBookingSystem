package entity;

public class NhanVien {

	private String maNhanVien;
	private String hoTen;
	private String soDienThoai;
	private boolean gioiTinh;
	private String email;
	private String chucVu;
	private String diaChi;
	private String trangThai;
	
	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public NhanVien(String maNhanVien, String hoTen, String soDienThoai, boolean gioiTinh, String email, String chucVu,
			String diaChi, String trangThai) {
		super();
		this.maNhanVien = maNhanVien;
		this.hoTen = hoTen;
		this.soDienThoai = soDienThoai;
		this.gioiTinh = gioiTinh;
		this.email = email;
		this.chucVu = chucVu;
		this.diaChi = diaChi;
		this.trangThai = trangThai;
	}

	public NhanVien() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NhanVien(String maNhanVien) {
		super();
		this.maNhanVien = maNhanVien;
	}

	@Override
	public String toString() {
		return "NhanVien [maNhanVien=" + maNhanVien + ", hoTen=" + hoTen + ", chucVu=" + chucVu + ", soDienThoai="
				+ soDienThoai + ", gioiTinh=" + gioiTinh + ", email=" + email + ", diaChi=" + diaChi + ", trangThai="
				+ trangThai + "]";
	}
	
	
}