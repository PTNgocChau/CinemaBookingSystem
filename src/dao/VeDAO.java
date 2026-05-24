package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Ghe;
import entity.Phim;
import entity.Phong;
import entity.SuatChieu;
import entity.Ve;

public class VeDAO {

    // =========================
    // LẤY TẤT CẢ VÉ
    // JOIN 1 lần duy nhất, không query lồng bên trong vòng lặp
    // =========================
    public List<Ve> getAllVe() {

        List<Ve> ds = new ArrayList<>();

        String sql = """
            SELECT
                v.maVe,
                v.giaVe,
                v.ngayDat,
                v.trangThai,
                v.maGhe,
                v.maSuat,
                p.tenPhim
            FROM Ve v
            JOIN SuatChieu sc ON v.maSuat  = sc.maSuat
            JOIN Phim p       ON sc.maPhim = p.maPhim
            ORDER BY v.ngayDat DESC
        """;
        Connection con = ConnectDB.getConnection();
        try (
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                // Phim — chỉ cần tenPhim để hiển thị
                Phim phim = new Phim();
                phim.setTenPhim(rs.getString("tenPhim"));

                // SuatChieu
                SuatChieu sc = new SuatChieu();
                sc.setMaSuat(rs.getString("maSuat"));
                sc.setPhim(phim);   // ← gắn phim vào để không bị NullPointerException

                // Ghe
                Ghe ghe = new Ghe(rs.getString("maGhe"));

                // Ve
                Ve ve = new Ve();
                ve.setMaVe(rs.getString("maVe"));
                ve.setGiaVe(rs.getDouble("giaVe"));
                ve.setNgayDat(rs.getDate("ngayDat").toLocalDate());
                ve.setTrangThai(rs.getString("trangThai"));
                ve.setGhe(ghe);
                ve.setSuatChieu(sc);

                ds.add(ve);
            }

            System.out.println("So ve load = " + ds.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    // =========================
    // THÊM VÉ
    // =========================
    public boolean themVe(Ve ve) {

        String sql = """
            INSERT INTO Ve(maVe, giaVe, ngayDat, trangThai, maNhanVien, maGhe, maSuat)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ve.getMaVe());
            ps.setDouble(2, ve.getGiaVe());
            ps.setDate(3, java.sql.Date.valueOf(ve.getNgayDat()));
            ps.setString(4, ve.getTrangThai());
            ps.setString(5, ve.getNhanVien().getMaNhanVien());
            ps.setString(6, ve.getGhe().getMaGhe());
            ps.setString(7, ve.getSuatChieu().getMaSuat());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // XÓA VÉ + TRẢ GHẾ VỀ TRỐNG
    // Dùng transaction để đảm bảo toàn vẹn dữ liệu
    // =========================
    public boolean xoaVe(String maVe) {

        Connection con = null;

        try {
            con = ConnectDB.getConnection();
            con.setAutoCommit(false);

            // Bước 1: Lấy maGhe và maSuat của vé cần xóa
            String maGhe  = null;
            String maSuat = null;

            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT maGhe, maSuat FROM Ve WHERE maVe = ?")) {

                ps.setString(1, maVe);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        maGhe  = rs.getString("maGhe");
                        maSuat = rs.getString("maSuat");
                    }
                }
            }

            if (maGhe == null) {
                con.rollback();
                return false;
            }

            // Bước 2: Trả ghế về trống
            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE Ghe SET trangThai = 0 WHERE maGhe = ? AND maSuat = ?")) {

                ps.setString(1, maGhe);
                ps.setString(2, maSuat);
                ps.executeUpdate();
            }

            // Bước 3: Xóa vé
            try (PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM Ve WHERE maVe = ?")) {

                ps.setString(1, maVe);
                ps.executeUpdate();
            }

            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } finally {
            try {
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

    // =========================
    // LẤY VÉ THEO MÃ (dùng cho In Vé)
    // JOIN đầy đủ Phim + Phong + Ghe để FormInVe không bị NullPointerException
    // =========================
    public Ve getVeByMa(String maVe) {

        String sql = """
                SELECT
                    v.maVe, v.giaVe, v.ngayDat, v.trangThai,
                    v.maGhe,
                    sc.maSuat, sc.thoiGian,
                    p.maPhim, p.tenPhim, p.theLoai, p.thoiLuong,
                    ph.maPhong
                FROM Ve v
                JOIN SuatChieu sc ON v.maSuat  = sc.maSuat
                JOIN Phim p       ON sc.maPhim = p.maPhim
                JOIN Phong ph     ON sc.maPhong = ph.maPhong
                JOIN Ghe g        ON v.maGhe   = g.maGhe
                                 AND v.maSuat  = g.maSuat
                WHERE v.maVe = ?
                """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maVe);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Phim phim = new Phim();
                    phim.setMaPhim(rs.getString("maPhim"));
                    phim.setTenPhim(rs.getString("tenPhim"));
                    phim.setTheLoai(rs.getString("theLoai"));
                    phim.setThoiLuong(rs.getInt("thoiLuong"));

                    Phong phong = new Phong();
                    phong.setMaPhong(rs.getString("maPhong"));

                    SuatChieu sc = new SuatChieu();
                    sc.setMaSuat(rs.getString("maSuat"));
                    sc.setThoiGian(rs.getTimestamp("thoiGian").toLocalDateTime());
                    sc.setPhim(phim);
                    sc.setPhong(phong);

                    Ve ve = new Ve();
                    ve.setMaVe(rs.getString("maVe"));
                    ve.setGiaVe(rs.getDouble("giaVe"));
                    ve.setNgayDat(rs.getDate("ngayDat").toLocalDate());
                    ve.setTrangThai(rs.getString("trangThai"));
                    ve.setSuatChieu(sc);
                    ve.setGhe(new Ghe(rs.getString("maGhe")));

                    return ve;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // =========================
    // LỌC VÉ THEO SUẤT
    // =========================
    public List<Ve> getBySuat(String maSuat) {

        List<Ve> ds = new ArrayList<>();

        String sql = "SELECT * FROM Ve WHERE maSuat = ?";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maSuat);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ve ve = new Ve();
                    ve.setMaVe(rs.getString("maVe"));
                    ve.setGiaVe(rs.getDouble("giaVe"));
                    ve.setNgayDat(rs.getDate("ngayDat").toLocalDate());
                    ve.setTrangThai(rs.getString("trangThai"));
                    ve.setGhe(new Ghe(rs.getString("maGhe")));
                    ve.setSuatChieu(new SuatChieu(maSuat));
                    ds.add(ve);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    // =========================
    // TẠO MÃ VÉ MỚI TỰ ĐỘNG
    // =========================
    public String taoMaVeMoi() {

        String sql = "SELECT TOP 1 maVe FROM Ve ORDER BY maVe DESC";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String maCu = rs.getString("maVe");          // VD: "VE007"
                int so = Integer.parseInt(maCu.substring(2)) + 1;
                return String.format("VE%03d", so);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "VE001";
    }
    public List<String> getDanhSachGheDaDatTheoSuat(String maSuat) {
        List<String> ds = new ArrayList<>();

        String sql = """
            SELECT maGhe
            FROM Ve
            WHERE maSuat = ? AND trangThai = N'DA_DAT'
        """;

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maSuat);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add(rs.getString("maGhe").trim());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }
}