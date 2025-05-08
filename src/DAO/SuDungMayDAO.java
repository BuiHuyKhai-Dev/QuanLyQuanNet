/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBConnect;
import DTO.SuDungMayDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author Minnie
 */
public class SuDungMayDAO {
    DBConnect db = new DBConnect();

public ArrayList<SuDungMayDTO> selectAll() {
    ArrayList<SuDungMayDTO> ds = new ArrayList<>();
    try (Connection conn = db.getConnection()){
        String sql = "SELECT MaSuDung, MaKH, MaMay, ThoiGianBatDau, ThoiGianKetThuc, TongThoiGian, ChiPhi from sudungmay";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int maSuDung = rs.getInt("MaSuDung");
            int maKhachHang = rs.getInt("MaKH");
            int maMay = rs.getInt("MaMay");
            Timestamp thoiGianBatDau = rs.getTimestamp("ThoiGianBatDau");
            Timestamp thoiGianKetThuc = rs.getTimestamp("ThoiGianKetThuc");
            double tongThoiGian = rs.getDouble("TongThoiGian");
            double chiPhi = rs.getDouble("ChiPhi");

            SuDungMayDTO suDung = new SuDungMayDTO(
                maSuDung,
                maKhachHang,
                maMay,
                thoiGianBatDau,
                thoiGianKetThuc,
                tongThoiGian,
                chiPhi
            );

            ds.add(suDung);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return ds;
}

    public SuDungMayDTO selectById(int maMay1) {
    SuDungMayDTO ds = null;
    try (Connection conn = db.getConnection()){
        String sql = "SELECT * from sudungmay where trangthai= 1 and mamay= ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, maMay1);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int maSuDung = rs.getInt("MaSuDung");
            int maKhachHang = rs.getInt("MaKH");
            int maMay = rs.getInt("MaMay");
            Timestamp thoiGianBatDau = rs.getTimestamp("ThoiGianBatDau");
            Timestamp thoiGianKetThuc = rs.getTimestamp("ThoiGianKetThuc");
            int tongThoiGian = rs.getInt("TongThoiGian");
            double chiPhi = rs.getDouble("ChiPhi");

            ds = new SuDungMayDTO(
                maSuDung,
                maKhachHang,
                maMay,
                thoiGianBatDau,
                thoiGianKetThuc,
                tongThoiGian,
                chiPhi
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return ds;
}

    public boolean insert(SuDungMayDTO sdm) {
    String sql = "INSERT INTO sudungmay (MaKH, MaMay, ThoiGianBatDau, ThoiGianKetThuc, TongThoiGian, ChiPhi, trangthai) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?)";
     try (Connection conn = db.getConnection()){
        doiTrangThai(sdm.getMaMay());
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, sdm.getMaKhachHang());
        stmt.setInt(2, sdm.getMaMay());
        stmt.setTimestamp(3, sdm.getThoiGianBatDau());

        
        stmt.setNull(4, java.sql.Types.TIMESTAMP);
        

        stmt.setDouble(5, sdm.getTongThoiGian());
        stmt.setDouble(6, sdm.getChiPhi());
        stmt.setDouble(7, 1);

        int rows = stmt.executeUpdate();
        stmt.close();
        conn.close();
        return rows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
    
    
}
    
    public boolean khachHangDangSuDung(int maKH) throws SQLException {
    String sql = "SELECT COUNT(*) FROM SuDungMay WHERE MaKH = ? AND ThoiGianKetThuc IS NULL";
    try (Connection conn = db.getConnection()){
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, maKH);
    ResultSet rs = stmt.executeQuery();

    if (rs.next()) {
        int count = rs.getInt(1);
        return count > 0;  // Nếu có ít nhất 1 bản ghi, nghĩa là đang sử dụng
    }
    }catch(Exception e){
        System.out.println("loi");
        return false;
    }
    return false;
}

    
    public void doiTrangThai(int maMay){
        String sql= "Update sudungmay set trangthai= 0 where trangthai=1 and mamay= ?";
        try (Connection conn = db.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maMay);
            stmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace(); 
        }
    }
    
    public boolean capNhatTrangThaiSuDung(int maMay, double giaThue, Timestamp thoiGianKetThuc) throws SQLException {
    // Truy vấn để lấy MaSuDung của máy đang sử dụng
    try (Connection conn = db.getConnection()){
    String sqlGetMaSuDung = "SELECT MaSuDung, ThoiGianBatDau FROM SuDungMay WHERE MaMay = ? AND ThoiGianKetThuc IS NULL";
    PreparedStatement stmtGetMaSuDung = conn.prepareStatement(sqlGetMaSuDung);
    stmtGetMaSuDung.setInt(1, maMay);
    ResultSet rs = stmtGetMaSuDung.executeQuery();

    if (rs.next()) {
        // Lấy MaSuDung và ThoiGianBatDau của máy
        int maSuDung = rs.getInt("MaSuDung");
        Timestamp thoiGianBatDau = rs.getTimestamp("ThoiGianBatDau");

        // Tính tổng thời gian sử dụng (thời gian kết thúc - thời gian bắt đầu)
        long totalTimeInSeconds = Duration.between(thoiGianBatDau.toLocalDateTime(), thoiGianKetThuc.toLocalDateTime()).getSeconds();
        double totalTimeInHours = totalTimeInSeconds / 3600.0; // Quy đổi về số giờ

        // Tính chi phí (Giá thuê * Tổng thời gian)
        double chiPhi = giaThue * totalTimeInHours;

        // Cập nhật thông tin vào bảng SuDungMay
        String sqlUpdate = "UPDATE SuDungMay SET ThoiGianKetThuc = ?, TongThoiGian = ?, ChiPhi = ? WHERE MaSuDung = ?";
        PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
        stmtUpdate.setTimestamp(1, thoiGianKetThuc);
        stmtUpdate.setDouble(2, totalTimeInHours);
        stmtUpdate.setDouble(3, chiPhi);
        stmtUpdate.setInt(4, maSuDung);

        int rowsAffected = stmtUpdate.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Cập nhật trạng thái thành công.");
        } else {
            System.out.println("Không tìm thấy MaSuDung với ID: " + maSuDung);
        }

        // Đóng kết nối
        stmtGetMaSuDung.close();
        stmtUpdate.close();
        conn.close();
    } else {
        System.out.println("Không tìm thấy máy có mã " + maMay + " đang sử dụng.");
        rs.close();
        conn.close();
        return false;
    }
    }catch (Exception e){
        System.out.println("loi");
        return false;
    }
    return true;
}


}
