/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBConnect;
import DTO.PhanQuyenDTO;
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
public class PhanQuyenDAO {
    public int insert(PhanQuyenDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Insert into nhomquyen (idnhomquyen,tennhomquyen) values"
                    + " ( ? , ? ) ";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getIdNhomQuyen());
            pst.setString(2, t.getTenNhomQuyen());
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(PhanQuyenDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public int update(PhanQuyenDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Update nhomquyen set tennhomquyen = ? Where idnhomquyen = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t.getTenNhomQuyen());
            pst.setInt(2, t.getIdNhomQuyen());
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(PhanQuyenDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public int delete(int id) {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Delete from nhomquyen Where idnhomquyen = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, id);
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(PhanQuyenDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public ArrayList<PhanQuyenDTO> selectAll() {
        ArrayList<PhanQuyenDTO> list = new ArrayList<>();
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Select * from nhomquyen";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                PhanQuyenDTO pq = new PhanQuyenDTO();
                pq.setIdNhomQuyen(rs.getInt("idnhomquyen"));
                pq.setTenNhomQuyen(rs.getString("tennhomquyen"));
                list.add(pq);
            }
        } catch (Exception e) {
            Logger.getLogger(PhanQuyenDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    public int getLastID() {
        int lastID = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Select max(idnhomquyen) as lastID from nhomquyen";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                lastID = rs.getInt("lastID");
            }
        } catch (Exception e) {
            Logger.getLogger(PhanQuyenDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return lastID;
    }
}
