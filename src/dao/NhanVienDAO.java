package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.NhanVien;

public class NhanVienDAO {

    // ================== LẤY TOÀN BỘ ==================
    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> ds = new ArrayList<NhanVien>();
        Connection con = ConnectDB.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM NhanVien";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String ma = rs.getString("maNhanVien");
                String ten = rs.getString("hoTen");
                String sdt = rs.getString("soDienThoai");
                boolean gt = rs.getBoolean("gioiTinh");
                String email = rs.getString("email");
                String chucVu = rs.getString("chucVu");
                String diaChi = rs.getString("diaChi");
                String trangThai = rs.getString("trangThai");

                NhanVien nv = new NhanVien(
                        ma, ten, sdt, gt,
                        email, chucVu, diaChi, trangThai
                );

                ds.add(nv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ds;
    }

    // ================== THÊM ==================
    public boolean themNhanVien(NhanVien nv) {
        PreparedStatement ps = null;
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "INSERT INTO NhanVien(maNhanVien, hoTen, soDienThoai, gioiTinh, email, chucVu, diaChi, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);

            ps.setString(1, nv.getMaNhanVien());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getSoDienThoai());
            ps.setBoolean(4, nv.isGioiTinh());
            ps.setString(5, nv.getEmail());
            ps.setString(6, nv.getChucVu());
            ps.setString(7, nv.getDiaChi());
            ps.setString(8, nv.getTrangThai());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // ================== XÓA ==================
    public boolean xoaNhanVien(String maNV) {
        PreparedStatement ps = null;
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "DELETE FROM NhanVien WHERE maNhanVien=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, maNV);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // ================== SỬA ==================
    public boolean suaNhanVien(NhanVien nv) {
        PreparedStatement ps = null;
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "UPDATE NhanVien SET hoTen=?, soDienThoai=?, gioiTinh=?, email=?, chucVu=?, diaChi=?, trangThai=? WHERE maNhanVien=?";
            ps = con.prepareStatement(sql);

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getSoDienThoai());
            ps.setBoolean(3, nv.isGioiTinh());
            ps.setString(4, nv.getEmail());
            ps.setString(5, nv.getChucVu());
            ps.setString(6, nv.getDiaChi());
            ps.setString(7, nv.getTrangThai());
            ps.setString(8, nv.getMaNhanVien());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // ================== TÌM THEO MÃ ==================
    public NhanVien timNhanVien(String maNV) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM NhanVien WHERE maNhanVien=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, maNV);

            rs = ps.executeQuery();

            if (rs.next()) {
                return new NhanVien(
                        rs.getString("maNhanVien"),
                        rs.getString("hoTen"),
                        rs.getString("soDienThoai"),
                        rs.getBoolean("gioiTinh"),
                        rs.getString("email"),
                        rs.getString("chucVu"),
                        rs.getString("diaChi"),
                        rs.getString("trangThai")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // ================== CHECK TRÙNG ==================
    public boolean existsMaNV(String ma) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT 1 FROM NhanVien WHERE maNhanVien=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, ma);

            rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}