package DAO;

import DAL.DBConnect;
import DTO.NhanVienDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NhanVienDAO {
    public int insert(NhanVienDTO a) {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "INSERT INTO nhanvien(MaNV, HoTen, GioiTinh, NgaySinh, SoDienThoai, Email, DiaChi, LuongCoBan, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, a.getMaNV());
            pst.setString(2, a.getTenNV());
            pst.setInt(3, a.getGioiTinh());
            pst.setString(4, a.getNgaySinh());
            pst.setString(5, a.getSoDT());
            pst.setString(6, a.getEmail());
            pst.setString(7, a.getDiaChi());
            pst.setDouble(8, a.getLuong());
            pst.setTimestamp(9, java.sql.Timestamp.valueOf(a.getThoiGianTao()));
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public int update(NhanVienDTO a) {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "UPDATE nhanvien SET HoTen = ?, GioiTinh = ?, NgaySinh = ?, SoDienThoai = ?, Email = ?, DiaChi = ?, LuongCoBan = ? WHERE MaNV = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, a.getTenNV());
            pst.setInt(2, a.getGioiTinh());
            pst.setString(3, a.getNgaySinh());
            pst.setString(4, a.getSoDT());
            pst.setString(5, a.getEmail());
            pst.setString(6, a.getDiaChi());
            pst.setDouble(7, a.getLuong());
            pst.setString(8, a.getMaNV());
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public int delete(String a) {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "DELETE FROM nhanvien WHERE MaNV = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, a);
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public ArrayList<NhanVienDTO> selectAll() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "SELECT * FROM nhanvien";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("HoTen"));
                nv.setGioiTinh(rs.getInt("GioiTinh"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setSoDT(rs.getString("SoDienThoai"));
                nv.setEmail(rs.getString("Email"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setLuong(rs.getDouble("LuongCoBan"));
                nv.setThoiGianTao(rs.getString("created_at"));
                list.add(nv);
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public NhanVienDTO selectById(String MaNV) {
        NhanVienDTO nv = new NhanVienDTO();
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "SELECT * FROM nhanvien WHERE MaNV = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, MaNV);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("HoTen"));
                nv.setGioiTinh(rs.getInt("GioiTinh"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setSoDT(rs.getString("SoDienThoai"));
                nv.setEmail(rs.getString("Email"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setLuong(rs.getDouble("LuongCoBan"));
                nv.setThoiGianTao(rs.getString("created_at"));
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return nv;
    }

    public int getAutoIncrement() {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'quanlyquannet' AND TABLE_NAME = 'nhanvien'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getInt("AUTO_INCREMENT");
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
}
