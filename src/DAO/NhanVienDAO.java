/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.NhanVienDTO;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import DAL.DBConnect;

/**
 *
 * @author Minnie
 */
public class NhanVienDAO {
    public static NhanVienDAO getInstance(){
        return new NhanVienDAO();
    }
    
    public ArrayList<NhanVienDTO> selectAll(){
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String sql = "Select * From nhanvien";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int manv = rs.getInt("maNV");
                String hoNV = rs.getString("hoNV");
                String tenNV = rs.getString("tenNV");
                String username = rs.getString("username");
                String matkhau = rs.getString("matkhau");
                int role = rs.getInt("role");
                NhanVienDTO nv = new NhanVienDTO(manv, hoNV, tenNV, username, matkhau, role);
                result.add(nv);
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
                        Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    public NhanVienDTO kiemTraDangNhap(String username, String matkhau){
        NhanVienDTO nv = null;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Select * From nhanvien WHERE username = ? AND matkhau = ? ";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, matkhau);
            ResultSet rs = (ResultSet) pst.executeQuery();
            if(rs.next()){
                int manv = rs.getInt("maNV");
                String hoNV = rs.getString("hoNV");
                String tenNV = rs.getString("tenNV");
                String usernamee = rs.getString("username");
                String matkhauu = rs.getString("matkhau");
                int role = rs.getInt("role");
                nv = new NhanVienDTO(manv, hoNV, tenNV, usernamee, matkhauu, role);
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return nv;
    }
}
