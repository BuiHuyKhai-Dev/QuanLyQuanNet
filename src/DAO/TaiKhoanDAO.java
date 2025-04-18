/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBConnect;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Minnie
 */
public class TaiKhoanDAO {
    public static TaiKhoanDAO getInstance(){
        return new TaiKhoanDAO();
    }
    
    public TaiKhoanDTO kiemTraDangNhap(String username, String matkhau){
        TaiKhoanDTO tk = null;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Select * From taikhoan WHERE tendangnhap = ? AND matkhau = ? ";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, matkhau);
            ResultSet rs = (ResultSet) pst.executeQuery();
            if(rs.next()){
                String manv = rs.getString("maNV");
                String tenDangNhap = rs.getString("TenDangNhap");
                String mk = rs.getString("matkhau");   
                String nhomQuyen = rs.getString("nhomQuyen");   
                int trangthai = rs.getInt("trangthai");
                tk = new TaiKhoanDTO(manv, tenDangNhap, mk, nhomQuyen, trangthai);
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return tk;
    }
}
