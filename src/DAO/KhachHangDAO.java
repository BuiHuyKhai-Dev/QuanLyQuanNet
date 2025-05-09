package DAO;
import DAL.DBConnect;
import DTO.KhachHangDTO;
import java.sql.*;
import java.util.*;

public class KhachHangDAO {
    public static List<KhachHangDTO> layDanhSachKhachHang() {
        List<KhachHangDTO> ds = new ArrayList<>();
        try (Connection conn = DBConnect.getConnection();
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
        try (Connection conn = DBConnect.getConnection();
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
        try (Connection conn = DBConnect.getConnection();
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
        try (Connection conn = DBConnect.getConnection();
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
        try (Connection conn = DBConnect.getConnection();
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
        try (Connection conn = DBConnect.getConnection();
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
        try (Connection conn = DBConnect.getConnection();
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
    public boolean tonTaiMaKH(int maKH) {
        String sql = "SELECT COUNT(*) FROM khachhang WHERE MaKH = ?";
        try (Connection conn = DBConnect.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maKH);  // Gắn giá trị mã khách hàng vào dấu hỏi trong câu lệnh SQL
            ResultSet rs = stmt.executeQuery();  // Thực thi câu lệnh truy vấn
            
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Nếu có ít nhất 1 dòng, trả về true, tức là mã khách hàng tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Trả về false nếu không tìm thấy mã khách hàng trong cơ sở dữ liệu
    }
    
    public double laySoDuTaiKhoan(int maKH) {
    double soDu = 0.0;

    try (Connection conn =DBConnect.getConnection()){
        String sql = "SELECT SoDu FROM khachhang WHERE MaKH = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, maKH);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            soDu = rs.getDouble("SoDu");
        }

        rs.close();
        stmt.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    return soDu;
}
    
    public boolean capNhatSoDu(int maKH, double soDuMoi) {
    boolean thanhCong = false;
    try (Connection conn = DBConnect.getConnection()){
        String sql = "UPDATE khachhang SET SoDu = ? WHERE MaKH = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, soDuMoi);
        stmt.setInt(2, maKH);

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            thanhCong = true;
        }
        stmt.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    return thanhCong;
}
    public ArrayList<KhachHangDTO> getAllKhachHang() {
        ArrayList<KhachHangDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM khachhang";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }
}
