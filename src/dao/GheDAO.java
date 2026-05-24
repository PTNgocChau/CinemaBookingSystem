package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Ghe;
import entity.SuatChieu;

public class GheDAO {

    // ===============================
    // LẤY DANH SÁCH GHẾ THEO SUẤT CHIẾU
    // ===============================
	public List<Ghe> getAllGhe(String maSuatChieu) {

	    List<Ghe> ds = new ArrayList<>();

	    String sql = "SELECT * FROM Ghe WHERE maSuat = ? ORDER BY maGhe";
	    Connection con = ConnectDB.getConnection();
	    try (
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, maSuatChieu);

	        try (ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                Ghe ghe = new Ghe(
	                    rs.getString("maGhe"),
	                    rs.getBoolean("loaiGhe"),
	                    rs.getInt("trangThai"),
	                    new SuatChieu(maSuatChieu)
	                );
	                ds.add(ghe);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return ds;
	}

    // ===============================
    // KIỂM TRA GHẾ CÒN TRỐNG ?
    // true = còn trống
    // false = đã đặt
    // ===============================
    public boolean isGheTrong(String maGhe, String maSuatChieu) {

        String sql = """
                SELECT trangThai
                FROM Ghe
                WHERE maGhe = ? AND maSuat = ?
                """;

        try (
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, maGhe);
            ps.setString(2, maSuatChieu);

            try{ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                boolean trangThai = rs.getBoolean("trangThai");

                // false = 0 = trống
                // true  = 1 = đã đặt
                return !trangThai;
            }
            }catch(Exception e) {
            	e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // overload dùng object
    public boolean isGheTrong(String maGhe, SuatChieu suat) {
        return isGheTrong(maGhe, suat.getMaSuat());
    }

    // ===============================
    // CẬP NHẬT TRẠNG THÁI 1 GHẾ
    // true = đã đặt
    // false = trống
    // ===============================
    public boolean capNhatTrangThai(String maGhe, String maSuatChieu, boolean trangThai) {

        String sql = """
                UPDATE Ghe
                SET trangThai = ?
                WHERE maGhe = ? AND maSuat = ?
                """;

        try (
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setBoolean(1, trangThai);
            ps.setString(2, maGhe);
            ps.setString(3, maSuatChieu);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===============================
    // ĐẶT NHIỀU GHẾ
    // ===============================
    public boolean datNhieuGhe(List<String> dsGhe, String maSuatChieu) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = """
                UPDATE Ghe
                SET trangThai = 1
                WHERE maGhe = ? AND maSuat = ?
                """;

        try {
            con = ConnectDB.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);

            for (String maGhe : dsGhe) {
                ps.setString(1, maGhe);
                ps.setString(2, maSuatChieu);
                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // ===============================
    // TRẢ GHẾ / HỦY GHẾ
    // ===============================
    public boolean traGhe(List<String> dsGhe, String maSuatChieu) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = """
                UPDATE Ghe
                SET trangThai = 0
                WHERE maGhe = ? AND maSuat = ?
                """;

        try {
            con = ConnectDB.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);

            for (String maGhe : dsGhe) {
                ps.setString(1, maGhe);
                ps.setString(2, maSuatChieu);
                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // ===============================
    // RESET TOÀN BỘ GHẾ VỀ TRỐNG
    // ===============================
    public boolean resetGhe(String maSuatChieu) {

        String sql = """
                UPDATE Ghe
                SET trangThai = 0
                WHERE maSuat = ?
                """;

        try (
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, maSuatChieu);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===============================
    // TẠO 100 GHẾ CHO SUẤT CHIẾU
    // A1 -> J10
    // VIP: D,E,F,G,H
    // ===============================
    public boolean taoGheChoSuat(String maSuatChieu) {

        String sql = """
            INSERT INTO Ghe(maGhe, loaiGhe, trangThai, maSuat)
            VALUES (?, ?, 0, ?)
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);

            for (int row = 0; row < 10; row++) {

                char rowChar = (char) ('A' + row);
                boolean vip = (row >= 3 && row <= 7);

                for (int col = 1; col <= 10; col++) {

                    String maGhe = rowChar + String.valueOf(col);

                    ps.setString(1, maGhe);
                    ps.setBoolean(2, vip);
                    ps.setString(3, maSuatChieu);

                    ps.addBatch();
                }
            }

            ps.executeBatch();
            con.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===============================
    // ĐẾM GHẾ ĐÃ ĐẶT
    // ===============================
    public int demSoGheDaDat(String maSuatChieu) {

        String sql = """
                SELECT COUNT(*) AS tong
                FROM Ghe
                WHERE maSuat = ? AND trangThai = 1
                """;

        try (
            Connection con = ConnectDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, maSuatChieu);

            try{ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("tong");
            }
            }catch(Exception e) {
            	e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}