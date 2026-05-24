package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import connectDB.ConnectDB;

public class ThongKeDAO {

    public Map<String, Object> getTongQuan(Date tuNgay, Date denNgay) {
        Map<String, Object> result = new LinkedHashMap<>();

        String sqlVe = """
            SELECT
                ISNULL(SUM(giaVe), 0) AS doanhThu,
                COUNT(maVe)           AS soVe
            FROM Ve
            WHERE trangThai = 'DA_DAT'
              AND ngayDat BETWEEN ? AND ?
            """;

        String sqlSuat = """
            SELECT COUNT(maSuat) AS soSuatChieu
            FROM SuatChieu
            WHERE ngayChieu BETWEEN ? AND ?
            """;

        String sqlTyLe = """
            SELECT
                CAST(
                    ISNULL(
                        SUM(CAST(g.trangThai AS FLOAT)) * 100.0
                        / NULLIF(COUNT(g.maGhe), 0)
                    , 0)
                AS DECIMAL(5,2)) AS tyLe
            FROM Ghe g
            JOIN SuatChieu sc ON g.maSuat = sc.maSuat
            WHERE sc.ngayChieu BETWEEN ? AND ?
            """;

        try (Connection con = ConnectDB.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(sqlVe)) {
                ps.setDate(1, tuNgay);
                ps.setDate(2, denNgay);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        result.put("doanhThu", rs.getDouble("doanhThu"));
                        result.put("soVe", rs.getInt("soVe"));
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(sqlSuat)) {
                ps.setDate(1, tuNgay);
                ps.setDate(2, denNgay);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        result.put("soSuatChieu", rs.getInt("soSuatChieu"));
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(sqlTyLe)) {
                ps.setDate(1, tuNgay);
                ps.setDate(2, denNgay);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        result.put("tyLeLapDay", rs.getDouble("tyLe"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result.putIfAbsent("doanhThu", 0.0);
        result.putIfAbsent("soVe", 0);
        result.putIfAbsent("soSuatChieu", 0);
        result.putIfAbsent("tyLeLapDay", 0.0);

        return result;
    }

    public List<Object[]> getDoanhThuTheoNgay(Date tuNgay, Date denNgay) {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT
                ngayDat,
                SUM(giaVe)  AS doanhThu,
                COUNT(maVe) AS soVe
            FROM Ve
            WHERE trangThai = 'DA_DAT'
              AND ngayDat BETWEEN ? AND ?
            GROUP BY ngayDat
            ORDER BY ngayDat
            """;
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, tuNgay);
            ps.setDate(2, denNgay);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[] {
                        rs.getDate("ngayDat"),
                        rs.getDouble("doanhThu"),
                        rs.getInt("soVe")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getTopPhim(Date tuNgay, Date denNgay) {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT
                p.tenPhim,
                p.theLoai,
                COUNT(v.maVe)           AS soVe,
                ISNULL(SUM(v.giaVe), 0) AS doanhThu,
                CAST(
                    COUNT(v.maVe) * 100.0
                    / NULLIF(
                        (SELECT COUNT(g2.maGhe)
                         FROM Ghe g2
                         JOIN SuatChieu sc2 ON g2.maSuat = sc2.maSuat
                         WHERE sc2.maPhim = p.maPhim
                           AND sc2.ngayChieu BETWEEN ? AND ?)
                    , 0)
                AS DECIMAL(5,2)) AS tyLe
            FROM Ve v
            JOIN SuatChieu sc ON v.maSuat = sc.maSuat
            JOIN Phim p ON sc.maPhim = p.maPhim
            WHERE v.trangThai = 'DA_DAT'
              AND v.ngayDat BETWEEN ? AND ?
            GROUP BY p.maPhim, p.tenPhim, p.theLoai
            ORDER BY doanhThu DESC
            """;
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, tuNgay);
            ps.setDate(2, denNgay);
            ps.setDate(3, tuNgay);
            ps.setDate(4, denNgay);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[] {
                        rs.getString("tenPhim"),
                        rs.getString("theLoai"),
                        rs.getInt("soVe"),
                        rs.getDouble("doanhThu"),
                        rs.getDouble("tyLe")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getHieuSuatNhanVien(Date tuNgay, Date denNgay) {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT
                nv.hoTen,
                nv.chucVu,
                COUNT(v.maVe) AS soVe,
                ISNULL(SUM(v.giaVe), 0) AS doanhThu
            FROM Ve v
            JOIN NhanVien nv ON v.maNhanVien = nv.maNhanVien
            WHERE v.trangThai = 'DA_DAT'
              AND v.ngayDat BETWEEN ? AND ?
            GROUP BY nv.maNhanVien, nv.hoTen, nv.chucVu
            ORDER BY doanhThu DESC
            """;
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, tuNgay);
            ps.setDate(2, denNgay);
            try (ResultSet rs = ps.executeQuery()) {
                int rank = 1;
                while (rs.next()) {
                    list.add(new Object[] {
                        rank++,
                        rs.getString("hoTen"),
                        rs.getString("chucVu"),
                        rs.getInt("soVe"),
                        rs.getDouble("doanhThu")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getTyLeLapDay(Date tuNgay, Date denNgay) {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT
                sc.maSuat,
                p.tenPhim,
                sc.ngayChieu,
                COUNT(g.maGhe) AS tongGhe,
                SUM(CAST(g.trangThai AS INT)) AS gheDaDat,
                CAST(
                    SUM(CAST(g.trangThai AS FLOAT)) * 100.0
                    / NULLIF(COUNT(g.maGhe), 0)
                AS DECIMAL(5,2)) AS tyLe
            FROM SuatChieu sc
            JOIN Phim p ON sc.maPhim = p.maPhim
            JOIN Ghe g ON g.maSuat = sc.maSuat
            WHERE sc.ngayChieu BETWEEN ? AND ?
            GROUP BY sc.maSuat, p.tenPhim, sc.ngayChieu
            ORDER BY tyLe DESC, sc.ngayChieu
            """;
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, tuNgay);
            ps.setDate(2, denNgay);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[] {
                        rs.getString("maSuat"),
                        rs.getString("tenPhim"),
                        rs.getDate("ngayChieu"),
                        rs.getInt("tongGhe"),
                        rs.getInt("gheDaDat"),
                        rs.getDouble("tyLe")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}