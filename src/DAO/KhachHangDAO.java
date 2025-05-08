package DAO;
import DAL.Database;
import DTO.KhachHangDTO;
import java.sql.*;
import java.util.*;

public class KhachHangDAO {
    public static List<KhachHangDTO> layDanhSachKhachHang() {
        List<KhachHangDTO> ds = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM khachhang WHERE trangthai = 1")) {

            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO(
                        rs.getString("makh"),
                        rs.getString("tenkh"),
                        rs.getString("matkhau"),
                        rs.getString("cccd"),
                        rs.getString("sodienthoai"),
                        rs.getString("ngaysinh"),
                        rs.getString("ngaydangky"),
                        rs.getInt("sogio"),
                        rs.getDouble("sotiennaptong"),
                        rs.getDouble("sodu"),
                        rs.getInt("trangthai")
                );
                ds.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    public static boolean themKhachHang(KhachHangDTO kh) {
        String sql = "INSERT INTO khachhang (makh, tenkh, matkhau, cccd, sodienthoai, ngaysinh, ngaydangky, sogio, sotiennaptong, sodu, trangthai) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getTenKH());
            ps.setString(3, kh.getMatKhau());
            ps.setString(4, kh.getCccd());
            ps.setString(5, kh.getSoDT());
            ps.setString(6, kh.getNgaySinh());
            ps.setString(7, kh.getNgayDangKy());
            ps.setInt(8, kh.getSoGio());
            ps.setDouble(9, kh.getSoTienNaptong());
            ps.setDouble(10, kh.getSoDu());
            ps.setInt(11, kh.getTrangThai());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean suaKhachHang(KhachHangDTO kh) {
        String sql = "UPDATE khachhang SET tenkh=?, matkhau=?, cccd=?, sodienthoai=?, ngaysinh=? WHERE makh=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getMatKhau());
            ps.setString(3, kh.getCccd());
            ps.setString(4, kh.getSoDT());
            ps.setString(5, kh.getNgaySinh());
            ps.setString(6, kh.getMaKH());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean napTien(String maKH, double soTien) {
        String sql = "UPDATE khachhang SET sotiennaptong = sotiennaptong + ?, sodu = sodu + ? WHERE makh = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, soTien);
            ps.setDouble(2, soTien);
            ps.setString(3, maKH);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean xoaMemKhachHang(String maKH) {
        String sql = "UPDATE khachhang SET trangthai = 0 WHERE makh = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static KhachHangDTO timTheoCCCD(String cccd) {
        String sql = "SELECT * FROM khachhang WHERE cccd = ? AND trangthai = 1";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cccd);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new KhachHangDTO(
                        rs.getString("makh"),
                        rs.getString("tenkh"),
                        rs.getString("matkhau"),
                        rs.getString("cccd"),
                        rs.getString("sodienthoai"),
                        rs.getString("ngaysinh"),
                        rs.getString("ngaydangky"),
                        rs.getInt("sogio"),
                        rs.getDouble("sotiennaptong"),
                        rs.getDouble("sodu"),
                        rs.getInt("trangthai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static KhachHangDTO timTheoMaKH(String maKH) {
        String sql = "SELECT * FROM khachhang WHERE makh = ? AND trangthai = 1";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new KhachHangDTO(
                    rs.getString("makh"),
                    rs.getString("tenkh"),
                    rs.getString("matkhau"),
                    rs.getString("cccd"),
                    rs.getString("sodienthoai"),
                    rs.getString("ngaysinh"),
                    rs.getString("ngaydangky"),
                    rs.getInt("sogio"),
                    rs.getDouble("sotiennaptong"),
                    rs.getDouble("sodu"),
                    rs.getInt("trangthai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
