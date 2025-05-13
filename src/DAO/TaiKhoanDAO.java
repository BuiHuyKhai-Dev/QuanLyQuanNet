/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBConnect;
import DTO.TaiKhoanDTO;
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
                String tenDangNhap = rs.getString("tendangnhap");
                String mk = rs.getString("matkhau");   
                int nhomQuyen = rs.getInt("manhomquyen");   
                int trangthai = rs.getInt("trangthai");
                tk = new TaiKhoanDTO(tenDangNhap, mk, nhomQuyen, trangthai);
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return tk;
    }

    public ArrayList<TaiKhoanDTO> selectAll() {
        ArrayList<TaiKhoanDTO> list = new ArrayList<>();
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "SELECT * FROM taikhoan";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                String tenDangNhap = rs.getString("tendangnhap");
                String mk = rs.getString("matkhau");
                int nhomQuyen = rs.getInt("manhomquyen");
                int trangthai = rs.getInt("trangthai");
                TaiKhoanDTO tk = new TaiKhoanDTO(tenDangNhap, mk, nhomQuyen, trangthai);
                list.add(tk);
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }
}
