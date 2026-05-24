package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connectDB.ConnectDB;
import entity.TaiKhoan;

public class TaiKhoanDAO {

    public TaiKhoanDAO() {
    }

    // CHECK LOGIN
    public String checkLogin(String tenDangNhap, String password) {

        String sql = """
            SELECT vaiTro
            FROM TaiKhoan
            WHERE tenDangNhap = ?
            AND [password] = ?
        """;

        try {
            Connection con = ConnectDB.getConnection();

            if (con == null) {
                System.out.println("NULL connection");
                return null;
            }

            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, tenDangNhap.trim());
                stmt.setString(2, password.trim());

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return rs.getString("vaiTro").trim();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // TÌM THEO MÃ
    public TaiKhoan timTheoMa(String maTK) {

        String sql = "SELECT * FROM TaiKhoan WHERE maTK = ?";

        try {

            Connection con = ConnectDB.getConnection();

            if (con == null) return null;

            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, maTK);

                try (ResultSet rs = stmt.executeQuery()) {

                    if (rs.next()) {

                        return new TaiKhoan(
                                rs.getString("maTK"),
                                rs.getString("tenDangNhap"),
                                rs.getString("password"),
                                rs.getString("vaiTro")
                        );
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // THÊM TÀI KHOẢN
    public boolean themTaiKhoan(TaiKhoan tk) {

        String sql = """
            INSERT INTO TaiKhoan(maTK, tenDangNhap, password, vaiTro)
            VALUES (?, ?, ?, ?)
        """;

        try {

            Connection con = ConnectDB.getConnection();

            if (con == null) return false;

            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, tk.getMaTK());
                stmt.setString(2, tk.getTenDangNhap());
                stmt.setString(3, tk.getPassword());
                stmt.setString(4, tk.getVaiTro());

                return stmt.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ĐỔI MẬT KHẨU
    public boolean doiMatKhau(String maTK, String matKhauMoi) {

        String sql = """
            UPDATE TaiKhoan
            SET password = ?
            WHERE maTK = ?
        """;

        try {

            Connection con = ConnectDB.getConnection();

            if (con == null) return false;

            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, matKhauMoi);
                stmt.setString(2, maTK);

                return stmt.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}