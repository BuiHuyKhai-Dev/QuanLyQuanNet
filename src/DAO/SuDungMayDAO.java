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
Connection conn = db.getConnection();

public ArrayList<SuDungMayDTO> selectAll() {
    ArrayList<SuDungMayDTO> ds = new ArrayList<>();

    try {
        String sql = "SELECT MaSuDung, MaKH, MaMay, ThoiGianBatDau, ThoiGianKetThuc, TongThoiGian, ChiPhi from sudungmay";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int maSuDung = rs.getInt("MaSuDung");
            int maKhachHang = rs.getInt("MaKH");
            int maMay = rs.getInt("MaMay");
            Timestamp thoiGianBatDau = rs.getTimestamp("ThoiGianBatDau");
            Timestamp thoiGianKetThuc = rs.getTimestamp("ThoiGianKetThuc");
            int tongThoiGian = rs.getInt("TongThoiGian");
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

        rs.close();
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return ds;
}

    public boolean insert(SuDungMayDTO sdm) {
    String sql = "INSERT INTO sudungmay (MaKH, MaMay, ThoiGianBatDau, ThoiGianKetThuc, TongThoiGian, ChiPhi) "
               + "VALUES (?, ?, ?, ?, ?, ?)";

    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, sdm.getMaKhachHang());
        stmt.setInt(2, sdm.getMaMay());
        stmt.setTimestamp(3, sdm.getThoiGianBatDau());

        
        stmt.setNull(4, java.sql.Types.TIMESTAMP);
        

        stmt.setInt(5, sdm.getTongThoiGian());
        stmt.setDouble(6, sdm.getChiPhi());

        int rows = stmt.executeUpdate();
        stmt.close();
        return rows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    public boolean capNhatTrangThaiSuDung(int maMay, double giaThue, Timestamp thoiGianKetThuc) throws SQLException {
    // Truy vấn để lấy MaSuDung của máy đang sử dụng
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
        long totalTimeInHours = totalTimeInSeconds / 3600; // Quy đổi về số giờ

        // Tính chi phí (Giá thuê * Tổng thời gian)
        double chiPhi = giaThue * totalTimeInHours;

        // Cập nhật thông tin vào bảng SuDungMay
        String sqlUpdate = "UPDATE SuDungMay SET ThoiGianKetThuc = ?, TongThoiGian = ?, ChiPhi = ? WHERE MaSuDung = ?";
        PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
        stmtUpdate.setTimestamp(1, thoiGianKetThuc);
        stmtUpdate.setLong(2, totalTimeInHours);
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
    } else {
        System.out.println("Không tìm thấy máy có mã " + maMay + " đang sử dụng.");
        rs.close();
        return false;
    }
    return true;
}


}
