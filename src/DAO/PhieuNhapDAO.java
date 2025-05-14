/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBConnect;
import DTO.PhieuNhapDTO;
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
public class PhieuNhapDAO{
    public static PhieuNhapDAO getInstance(){
        return new PhieuNhapDAO();
    }
    
    public int insert(PhieuNhapDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Insert into phieunhap (MaPN,MaNCC,MaNV,TongTien,created_at) values"
                    + " ( ? , ? , ? , ? , ? ) ";
            PreparedStatement pst=(PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getMaPN());
            pst.setInt(2, t.getMaNCC());
            pst.setInt(3, t.getMaNV());
            pst.setDouble(4, t.getTongTien());
            pst.setString(5, t.getThoiGianTao());
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public int update(PhieuNhapDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Update phieunhap set MaNCC = ? , MaNV = ? , TongTien = ? , created_at = ? Where MaPN = ?";
            PreparedStatement pst=(PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getMaNCC());
            pst.setInt(2, t.getMaNV());
            pst.setDouble(3, t.getTongTien());
            pst.setString(4, t.getThoiGianTao());
            pst.setInt(5, t.getMaPN());
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public int delete(String t) {
        int result=0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Delete from phieunhap Where MaPN = ?";
            PreparedStatement pst=(PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return  result;
    }

    public ArrayList<PhieuNhapDTO> selectAll() {
        ArrayList<PhieuNhapDTO> result = new ArrayList<>();
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Select * From phieunhap";
            PreparedStatement pst=(PreparedStatement) con.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                int mapn = rs.getInt("MaPN");
                int manv = rs.getInt("MaNV");
                int mancc = rs.getInt("MaNCC");
                double tongtien = rs.getDouble("TongTien");
                String thoigiantao = rs.getString("created_at");
                PhieuNhapDTO pn = new PhieuNhapDTO(mapn, mancc, manv, tongtien, thoigiantao);
                result.add(pn);
            }
            DBConnect.closeConnection(con);

        } catch (Exception e) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public PhieuNhapDTO selectById(String t) {
        PhieuNhapDTO result = new PhieuNhapDTO();
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Select * From phieunhap Where MaPN = ?";
            PreparedStatement pst=(PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                int mapn = rs.getInt("MaPN");
                int manv = rs.getInt("MaNV");
                int mancc = rs.getInt("MaNCC");
                double tongtien = rs.getDouble("TongTien");
                String thoigiantao = rs.getString("created_at");
                result = new PhieuNhapDTO(mapn, mancc, manv, tongtien, thoigiantao);
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
}
