package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Phim;
import entity.Phong;
import entity.SuatChieu;

public class SuatChieuDAO {

    // ===================== LẤY TOÀN BỘ =====================
	public List<SuatChieu> getAllSuatChieu() {

	    List<SuatChieu> ds = new ArrayList<>();

	    String sql = """
	        SELECT sc.maSuat, sc.ngayChieu, sc.thoiGian,

	               p.maPhim, p.tenPhim, p.thoiLuong, p.theLoai,
	               p.gioiHanTuoi, p.ngonNgu,

	               ph.maPhong, ph.sucChua

	        FROM SuatChieu sc
	        JOIN Phim p  ON sc.maPhim = p.maPhim
	        JOIN Phong ph ON sc.maPhong = ph.maPhong
	        ORDER BY sc.thoiGian
	    """;
	    Connection con = ConnectDB.getConnection();
	    try {
	        
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            // ===== PHIM =====
	            Phim phim = new Phim(
	                    rs.getString("maPhim"),
	                    rs.getString("tenPhim"),
	                    rs.getInt("thoiLuong"),
	                    rs.getString("theLoai"),
	                    rs.getString("gioiHanTuoi"),
	                    rs.getString("ngonNgu")
	            );

	            // ===== PHÒNG =====
	            Phong phong = new Phong(
	                    rs.getString("maPhong"),
	                    rs.getInt("sucChua"),
	                    null
	            );

	            // ===== SUẤT CHIẾU =====
	            SuatChieu sc = new SuatChieu(
	                    rs.getString("maSuat"),
	                    phim,
	                    rs.getDate("ngayChieu").toLocalDate(),
	                    phong,
	                    rs.getTimestamp("thoiGian").toLocalDateTime()
	            );

	            ds.add(sc);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return ds;
	}

    // ===================== THÊM =====================
    public boolean themSuatChieu(SuatChieu sc) {
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "INSERT INTO SuatChieu VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, sc.getMaSuat());
            ps.setString(2, sc.getPhim().getMaPhim());
            ps.setDate(3, Date.valueOf(sc.getNgayChieu()));
            ps.setString(4, sc.getPhong().getMaPhong());
            ps.setTimestamp(5, Timestamp.valueOf(sc.getThoiGian()));

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===================== XÓA =====================
    public boolean xoaSuatChieu(String maSuat) {
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "DELETE FROM SuatChieu WHERE maSuat = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSuat);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===================== SỬA =====================
    public boolean suaSuatChieu(SuatChieu sc) {
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "UPDATE SuatChieu "
                    + "SET maPhim=?, ngayChieu=?, maPhong=?, thoiGian=? "
                    + "WHERE maSuat=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, sc.getPhim().getMaPhim());
            ps.setDate(2, Date.valueOf(sc.getNgayChieu()));
            ps.setString(3, sc.getPhong().getMaPhong());
            ps.setTimestamp(4, Timestamp.valueOf(sc.getThoiGian()));
            ps.setString(5, sc.getMaSuat());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===================== TÌM THEO MÃ =====================
    public SuatChieu timSuatChieu(String maSuat) {
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM SuatChieu WHERE maSuat=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSuat);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Phim phim = new Phim(rs.getString("maPhim").trim());
                Phong phong = new Phong(rs.getString("maPhong").trim());

                return new SuatChieu(
                        rs.getString("maSuat").trim(),
                        phim,
                        rs.getDate("ngayChieu").toLocalDate(),
                        phong,
                        rs.getTimestamp("thoiGian").toLocalDateTime()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}