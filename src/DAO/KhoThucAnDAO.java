package DAO;
import DAL.DBConnect;
import DTO.KhoThucAnDTO;
import java.sql.*;
import java.util.*;

public class KhoThucAnDAO {

    public KhoThucAnDAO() {

    }
    
    
    public ArrayList<KhoThucAnDTO> getAllKhoThucAn() {
        ArrayList<KhoThucAnDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM khothucan";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                KhoThucAnDTO kta = new KhoThucAnDTO();
                kta.setMaKho(rs.getInt("MaKho"));
                kta.setmaNCC(rs.getInt("MaNCC"));
                kta.setMaThucAn(rs.getInt("MaThucAn"));
                kta.setSoLuong(rs.getInt("SoLuong"));
                list.add(kta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(KhoThucAnDTO kta) {
        String sql = "INSERT INTO khothucan(MaKho, MaThucAn, SoLuong) VALUES (?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, kta.getMaKho());
            ps.setInt(2, kta.getMaThucAn());
            ps.setInt(3, kta.getSoLuong());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(KhoThucAnDTO kta) {
        String sql = "UPDATE khothucan SET SoLuong=? WHERE MaKho=? AND MaThucAn=?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, kta.getSoLuong());
            ps.setInt(2, kta.getMaKho());
            ps.setInt(3, kta.getMaThucAn());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int maKho, int maThucAn) {
        String sql = "DELETE FROM khothucan WHERE MaKho=? AND MaThucAn=?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKho);
            ps.setInt(2, maThucAn);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean truSoLuong(int maThucAn, int soLuong) {
        String sql = "UPDATE khothucan SET SoLuong = SoLuong - ? WHERE MaThucAn = ? AND SoLuong >= ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuong);  // The quantity to subtract
            ps.setInt(2, maThucAn); // The food item to update
            ps.setInt(3, soLuong);  // Ensure the quantity doesn't go negative
    
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Return true if the update is successful
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Return false if there's an error
    }
    
}