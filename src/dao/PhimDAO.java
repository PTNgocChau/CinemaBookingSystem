package dao;

import java.sql.*;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.Phim;

public class PhimDAO {

    public ArrayList<Phim> getAllPhim() {
        ArrayList<Phim> ds = new ArrayList<>();
        Connection con = ConnectDB.getConnection();
        try {
            

            String sql = "SELECT * FROM Phim";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Phim p = new Phim(
                    rs.getString("maPhim").trim(),
                    rs.getString("tenPhim"),
                    rs.getInt("thoiLuong"),
                    rs.getString("theLoai"),
                    rs.getString("gioiHanTuoi"),
                    rs.getString("ngonNgu")
                );

                ds.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    public boolean themPhim(Phim p) {
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "INSERT INTO Phim VALUES(?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, p.getMaPhim());
            ps.setString(2, p.getTenPhim());
            ps.setInt(3, p.getThoiLuong());
            ps.setString(4, p.getTheLoai());
            ps.setString(5, p.getGioiHanTuoi());
            ps.setString(6, p.getNgonNgu());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean xoaPhim(String ma) {
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "DELETE FROM Phim WHERE maPhim=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ma);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean suaPhim(Phim p) {
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "UPDATE Phim SET tenPhim=?, thoiLuong=?, theLoai=?, gioiHanTuoi=?, ngonNgu=? WHERE maPhim=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, p.getTenPhim());
            ps.setInt(2, p.getThoiLuong());
            ps.setString(3, p.getTheLoai());
            ps.setString(4, p.getGioiHanTuoi());
            ps.setString(5, p.getNgonNgu());
            ps.setString(6, p.getMaPhim());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Phim timPhim(String ma) {
        try {
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM Phim WHERE maPhim=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ma);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Phim(
                    rs.getString("maPhim").trim(),
                    rs.getString("tenPhim"),
                    rs.getInt("thoiLuong"),
                    rs.getString("theLoai"),
                    rs.getString("gioiHanTuoi"),
                    rs.getString("ngonNgu")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}