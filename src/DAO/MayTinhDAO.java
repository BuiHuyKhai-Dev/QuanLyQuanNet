package DAO;

import DTO.MayTinhDTO;
import java.sql.*;

import DAL.DBConnect;

public class MayTinhDAO {

    public MayTinhDTO chonMayTinhTheoMa(int maMay) {
        MayTinhDTO may = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = new DBConnect().getConnection(); // Giả sử bạn có class Database để kết nối
            String sql = "SELECT * FROM maytinh WHERE maMay = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maMay);
            rs = stmt.executeQuery();

            if (rs.next()) {
                may = new MayTinhDTO();
                may.setMaMay(rs.getInt("maMay"));
                may.setTenMay(rs.getString("tenMay"));
                may.setTrangThai(rs.getInt("trangThai"));
                // Thêm các thuộc tính khác nếu có
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return may;
    }
}
