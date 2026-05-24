package entity;

public class TaiKhoan {
	private String maTK;
	private String tenDangNhap;
	private String password;
	private String vaiTro;
	
	public String getMaTK() {
		return maTK;
	}
	public void setMaTK(String maTK) {
		this.maTK = maTK;
	}
	public String getTenDangNhap() {
		return tenDangNhap;
	}
	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVaiTro() {
		return vaiTro;
	}
	public void setVaiTro(String vaiTro) {
		this.vaiTro = vaiTro;
	}
	
	public TaiKhoan(String maTK, String tenDangNhap, String password, String vaiTro) {
		super();
		this.maTK = maTK;
		this.tenDangNhap = tenDangNhap;
		this.password = password;
		this.vaiTro = vaiTro;
	}
	
	public TaiKhoan() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TaiKhoan(String maTK) {
		super();
		this.maTK = maTK;
	}
	@Override
	public String toString() {
		return "Admin [maTK = " + maTK + ", tên đăng nhập = " + tenDangNhap + ", password = " + password + ", vai trò = " + vaiTro + "]";
	}
}
