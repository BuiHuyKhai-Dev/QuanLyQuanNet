package DAO;
import DAL.DBConnect;
import DTO.KhachHangDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhachHangDAO {
    public int insert(KhachHangDTO a){
        int result=0;
        try{
             Connection con= (Connection) DBConnect.getConnection();
             String sql= "Insert into khachhang(MaKH,TenKH,SoDienThoai,Email,SoDuTaiKhoan,created_at)  values ( ? , ? , ? ,  ? ,  ? ,  ?)";
             PreparedStatement pst=(PreparedStatement) con.prepareStatement(sql);
             pst.setInt(1, a.getMaKhachHang());
             pst.setString(2, a.getTenKhachHang());
             pst.setString(4, a.getSoDienThoai());
             pst.setDouble(5, a.getSoDuTaiKhoan());
             java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
             java.util.Date parsedDate = sdf.parse(a.getThoiGianTao());
             pst.setDate(6, new java.sql.Date(parsedDate.getTime()));
             pst.setTimestamp(6, java.sql.Timestamp.valueOf(a.getThoiGianTao()));
             result=pst.executeUpdate();
             DBConnect.closeConnection(con);
        }catch(Exception e){
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }        
        return result;
    }
    
    public int update(KhachHangDTO a){
        int result=0;
        try{
             Connection con= (Connection) DBConnect.getConnection();
             String sql= "Update khachhang set TenKH = ? , SoDienThoai = ? , Email = ? , SoDuTaiKhoan = ? where MaKH = ?";
             PreparedStatement pst=(PreparedStatement) con.prepareStatement(sql);
             pst.setString(1, a.getTenKhachHang());
             pst.setString(2, a.getSoDienThoai());
             pst.setString(3, a.getEmail());
             pst.setDouble(4, a.getSoDuTaiKhoan());
             pst.setInt(5, a.getMaKhachHang());
             result=pst.executeUpdate();
             DBConnect.closeConnection(con);
        }catch(Exception e){
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    } 
    
    public int delete(String a){
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Delete From khachhang Where MaKH = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(a));
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    };
    
    public ArrayList<KhachHangDTO> selectAll(){
        ArrayList<KhachHangDTO> list = new ArrayList<>();
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Select * From khachhang";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO();
                kh.setMaKhachHang(rs.getInt("MaKH"));
                kh.setTenKhachHang(rs.getString("TenKH"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setEmail(rs.getString("Email"));
                kh.setSoDuTaiKhoan(rs.getDouble("SoDuTaiKhoan"));
                kh.setThoiGianTao(rs.getString("created_at"));
                list.add(kh);
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    };
    
    public KhachHangDTO selectById(String MaKH){
        KhachHangDTO kh = new KhachHangDTO();
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Select * From khachhang Where MaKH = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(MaKH));
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                kh.setMaKhachHang(rs.getInt("MaKH"));
                kh.setTenKhachHang(rs.getString("TenKH"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setEmail(rs.getString("Email"));
                kh.setSoDuTaiKhoan(rs.getDouble("SoDuTaiKhoan"));
                kh.setThoiGianTao(rs.getString("created_at"));
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return kh;
    }
}
