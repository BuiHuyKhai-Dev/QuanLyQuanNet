/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author ADMIN
 */
import DAL.DBConnect;
import java.sql.*;

public class KhachHangDAO {
    DBConnect db = new DBConnect();
    // Hàm kiểm tra mã khách hàng có tồn tại không
    public boolean tonTaiMaKH(int maKH) {
        String sql = "SELECT COUNT(*) FROM khachhang WHERE MaKH = ?";
        try (Connection conn = db.getConnection()){
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

    try (Connection conn = db.getConnection()){
        String sql = "SELECT SoDuTaiKhoan FROM khachhang WHERE MaKH = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, maKH);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            soDu = rs.getDouble("SoDuTaiKhoan");
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
    try (Connection conn = db.getConnection()){
        String sql = "UPDATE khachhang SET SoDuTaiKhoan = ? WHERE MaKH = ?";
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


}
