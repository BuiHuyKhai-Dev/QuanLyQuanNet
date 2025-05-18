package DAO;

import DAL.DBConnect;
import DTO.ThucAnDTO;
import java.sql.*;
import java.util.ArrayList;

public class ThucAnDAO {

    public ThucAnDAO() {
    }

    public ArrayList<ThucAnDTO> getAll() {
        ArrayList<ThucAnDTO> list = new ArrayList<>();
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "SELECT * FROM ThucAn";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThucAnDTO ta = new ThucAnDTO();
                ta.setMaThucAn(rs.getInt("MaThucAn"));
                ta.setTenThucAn(rs.getString("TenThucAn"));
                ta.setSoLuong(rs.getInt("SoLuong"));
                ta.setDonGia(rs.getInt("DonGia"));
                ta.setThoiGianTao(rs.getTimestamp("created_at").toLocalDateTime().toString());
                list.add(ta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insert(ThucAnDTO ta) {
        int result = 0;
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "INSERT INTO ThucAn (MaThucAn, TenThucAn, SoLuong, DonGia, created_at) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ta.getMaThucAn());
            ps.setString(2, ta.getTenThucAn());
            ps.setInt(3, ta.getSoLuong());
            ps.setDouble(4, ta.getDonGia());
            ps.setTimestamp(5, Timestamp.valueOf(ta.getThoiGianTao()));
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int update(ThucAnDTO ta) {
        int result = 0;
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "UPDATE ThucAn SET TenThucAn = ?, SoLuong = ?, DonGia = ? WHERE MaThucAn = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ta.getTenThucAn());
            ps.setInt(2, ta.getSoLuong());
            ps.setDouble(3, ta.getDonGia());
            ps.setInt(4, ta.getMaThucAn());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int delete(int maThucAn) {
        int result = 0;
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "DELETE FROM ThucAn WHERE MaThucAn = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maThucAn);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public int updateSoLuong(int maThucAn, int soLuong) {
        int result = 0;
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "UPDATE ThucAn SET SoLuong = ? WHERE MaThucAn = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, soLuong);
            ps.setInt(2, maThucAn);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
        public int giamSoLuong(int maThucAn, int soLuongGiam) {
    int result = 0;
    try {
        Connection conn = DBConnect.getConnection();
        String sql = "UPDATE ThucAn SET SoLuong = SoLuong - ? WHERE MaThucAn = ? AND SoLuong >= ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, soLuongGiam);
        ps.setInt(2, maThucAn);
        ps.setInt(3, soLuongGiam);
        result = ps.executeUpdate(); // Trả về số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu thất bại)
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return result;
}

}