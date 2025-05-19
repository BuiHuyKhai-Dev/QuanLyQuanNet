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
            String sql = "INSERT INTO nhanvien(MaNV, HoNV, TenNV, GioiTinh, NgaySinh, SoDienThoai, Email, DiaChi, LuongCoBan, TrangThai, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, a.getMaNV());
            pst.setString(2, a.getHoNV());
            pst.setString(3, a.getTenNV());
            pst.setInt(4, a.getGioiTinh());
            pst.setString(5, a.getNgaySinh());
            pst.setString(6, a.getSoDT());
            pst.setString(7, a.getEmail());
            pst.setString(8, a.getDiaChi());
            pst.setDouble(9, a.getLuong());
            pst.setInt(10, a.getTrangThai());
            pst.setTimestamp(11, java.sql.Timestamp.valueOf(a.getThoiGianTao()));
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
            String sql = "UPDATE nhanvien SET HoNV = ?, TenNV = ?, GioiTinh = ?, NgaySinh = ?, SoDienThoai = ?, Email = ?, DiaChi = ?, LuongCoBan = ?, TrangThai = ? WHERE MaNV = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, a.getHoNV());
            pst.setString(2, a.getTenNV());
            pst.setInt(3, a.getGioiTinh());
            pst.setString(4, a.getNgaySinh());
            pst.setString(5, a.getSoDT());
            pst.setString(6, a.getEmail());
            pst.setString(7, a.getDiaChi());
            pst.setDouble(8, a.getLuong());
            pst.setInt(9, a.getTrangThai());
            pst.setString(10, a.getMaNV());
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
                nv.setHoNV(rs.getString("HoNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setGioiTinh(rs.getInt("GioiTinh"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setSoDT(rs.getString("SoDienThoai"));
                nv.setEmail(rs.getString("Email"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setLuong(rs.getDouble("LuongCoBan"));
                nv.setTrangThai(rs.getInt("TrangThai"));
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
                nv.setHoNV(rs.getString("HoNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setGioiTinh(rs.getInt("GioiTinh"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setSoDT(rs.getString("SoDienThoai"));
                nv.setEmail(rs.getString("Email"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setLuong(rs.getDouble("LuongCoBan"));
                nv.setTrangThai(rs.getInt("TrangThai"));
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
