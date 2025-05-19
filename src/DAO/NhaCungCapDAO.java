/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBConnect;
import DTO.NhaCungCapDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Minnie
 */
public class NhaCungCapDAO {
    public int insert(NhaCungCapDTO a) {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "INSERT INTO nhacungcap(MaNCC, TenNCC, DiaChi, SoDienThoai, Email, TrangThai, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, a.getMaNhaCungCap());
            pst.setString(2, a.getTenNhaCungCap());
            pst.setString(3, a.getDiaChi());
            pst.setString(4, a.getSoDienThoai());
            pst.setString(5, a.getEmail());
            pst.setInt(6, a.getTrangThai());
            pst.setTimestamp(7, java.sql.Timestamp.valueOf(a.getThoiGianTao()));
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public int update(NhaCungCapDTO a) {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "UPDATE nhacungcap SET TenNCC = ?, DiaChi = ?, SoDienThoai = ?, Email = ?, TrangThai = ? WHERE MaNCC = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, a.getTenNhaCungCap());
            pst.setString(2, a.getDiaChi());
            pst.setString(3, a.getSoDienThoai());
            pst.setString(4, a.getEmail());
            pst.setInt(5, a.getTrangThai());
            pst.setInt(6, a.getMaNhaCungCap());
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public int delete(int a) {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "DELETE FROM nhacungcap WHERE MaNCC = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, a);
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public NhaCungCapDTO selectByID(int a) {
        NhaCungCapDTO ncc = null;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "SELECT * FROM nhacungcap WHERE MaNCC = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, a);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                ncc = new NhaCungCapDTO();
                ncc.setMaNhaCungCap(rs.getInt("MaNCC"));
                ncc.setTenNhaCungCap(rs.getString("TenNCC"));
                ncc.setDiaChi(rs.getString("DiaChi"));
                ncc.setSoDienThoai(rs.getString("SoDienThoai"));
                ncc.setEmail(rs.getString("Email"));
                ncc.setTrangThai(rs.getInt("TrangThai"));
                ncc.setThoiGianTao(rs.getTimestamp("created_at").toLocalDateTime().toString());
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return ncc;
    }

    public ArrayList<NhaCungCapDTO> selectAll() {
        ArrayList<NhaCungCapDTO> list = new ArrayList<>();
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "SELECT * FROM nhacungcap";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                NhaCungCapDTO ncc = new NhaCungCapDTO();
                ncc.setMaNhaCungCap(rs.getInt("MaNCC"));
                ncc.setTenNhaCungCap(rs.getString("TenNCC"));
                ncc.setDiaChi(rs.getString("DiaChi"));
                ncc.setSoDienThoai(rs.getString("SoDienThoai"));
                ncc.setEmail(rs.getString("Email"));
                ncc.setTrangThai(rs.getInt("TrangThai"));
                ncc.setThoiGianTao(rs.getTimestamp("created_at").toLocalDateTime().toString());
                list.add(ncc);
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }
}
