package DAO;

import DTO.ThucAnDTO;
import DAL.DBConnect;

import java.sql.*;
import java.util.ArrayList;

public class ThucAnDAO {

    public static ArrayList<ThucAnDTO> getAll() {
        ArrayList<ThucAnDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM ThucAn";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ThucAnDTO ta = new ThucAnDTO();
                ta.setMaThucAn(rs.getInt("MaThucAn"));
                ta.setTenThucAn(rs.getString("TenThucAn"));
                ta.setDonGia(rs.getBigDecimal("DonGia")); // Sử dụng BigDecimal thay vì double
                list.add(ta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(ThucAnDTO obj) {
        String sql = "INSERT INTO ThucAn (TenThucAn, DonVi, DonGia, HanSuDung) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, obj.getTenThucAn());
            stmt.setString(2, obj.getDonVi());
            stmt.setBigDecimal(3, obj.getDonGia());
            stmt.setDate(4, new java.sql.Date(obj.getHanSuDung().getTime()));
    
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    

    public boolean update(ThucAnDTO obj) {
        String sql = "UPDATE ThucAn SET TenThucAn = ?, DonVi = ?, DonGia = ?, HanSuDung = ? WHERE MaThucAn = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, obj.getTenThucAn());
            stmt.setString(2, obj.getDonVi());
            stmt.setBigDecimal(3, obj.getDonGia());
            stmt.setDate(4, obj.getHanSuDung());
            stmt.setInt(5, obj.getMaThucAn());
    
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    

    public boolean delete(int id) {
        String deleteKhoThucAnSql = "DELETE FROM KhoThucAn WHERE MaThucAn = ?";
        String deleteThucAnSql = "DELETE FROM ThucAn WHERE MaThucAn = ?";
        
        // Đảm bảo kết nối được khai báo và sử dụng đúng
        try (Connection conn = DBConnect.getConnection()) {
            // Bắt đầu giao dịch
            conn.setAutoCommit(false);
    
            // Xóa các bản ghi trong bảng KhoThucAn
            try (PreparedStatement stmtKho = conn.prepareStatement(deleteKhoThucAnSql)) {
                stmtKho.setInt(1, id);
                stmtKho.executeUpdate();
            }
    
            // Xóa bản ghi trong bảng ThucAn
            try (PreparedStatement stmtThucAn = conn.prepareStatement(deleteThucAnSql)) {
                stmtThucAn.setInt(1, id);
                int rowsAffected = stmtThucAn.executeUpdate();
                
                // Nếu xóa thành công, commit giao dịch
                if (rowsAffected > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();  // Nếu không có dòng nào bị xóa, rollback giao dịch
                    return false;
                }
            }
        } catch (Exception e) {
        return false;
    }    
}
    public static ThucAnDTO getById(int id) {
        String sql = "SELECT * FROM ThucAn WHERE MaThucAn = ?";
        try (Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ThucAnDTO obj = new ThucAnDTO(); // hoặc dùng constructor có tham số nếu bạn có
                    obj.setMaThucAn(rs.getInt("MaThucAn"));
                    obj.setTenThucAn(rs.getString("TenThucAn"));
                    obj.setDonVi(rs.getString("DonVi"));
                    obj.setDonGia(rs.getBigDecimal("DonGia"));
                    obj.setHanSuDung(rs.getDate("HanSuDung"));
                    return obj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}