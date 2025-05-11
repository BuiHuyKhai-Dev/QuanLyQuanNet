package DAO;
import DAL.DBConnect;
import DTO.KhachHangDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhachHangDAO {
    public int insert(KhachHangDTO a) {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "INSERT INTO khachhang(TenKH, SoDienThoai, Email, SoDuTaiKhoan, created_at) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, a.getTenKhachHang());
            pst.setString(2, a.getSoDienThoai());
            pst.setString(3, a.getEmail());
            pst.setDouble(4, a.getSoDuTaiKhoan());
            pst.setTimestamp(5, java.sql.Timestamp.valueOf(a.getThoiGianTao()));
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
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

    public int getAutoIncrement() {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'quanlyquannet' AND TABLE_NAME = 'khachhang'";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getInt("AUTO_INCREMENT");
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    public boolean tonTaiMaKH(int maKH) {
        String sql = "SELECT COUNT(*) FROM khachhang WHERE MaKH = ?";
        try (Connection conn = DBConnect.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maKH);  // Gắn giá trị mã khách hàng vào dấu hỏi trong câu lệnh SQL
            ResultSet rs = stmt.executeQuery();  // Thực thi câu lệnh truy vấn
            
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Nếu có ít nhất 1 dòng, trả về true, tức là mã khách hàng tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Trả về false nếu không tìm thấy mã khách hàng trong cơ sở dữ liệu
    }
    
    public double laySoDuTaiKhoan(int maKH) {
    double soDu = 0.0;

    try (Connection conn =DBConnect.getConnection()){
        String sql = "SELECT SoDuTaiKhoan FROM khachhang WHERE MaKH = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, maKH);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            soDu = rs.getDouble("SoDuTaiKhoan");
        }

        rs.close();
        stmt.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    return soDu;
}
    
    public boolean capNhatSoDu(int maKH, double soDuMoi) {
    boolean thanhCong = false;
    try (Connection conn = DBConnect.getConnection()){
        String sql = "UPDATE khachhang SET SoDuTaiKhoan = ? WHERE MaKH = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, soDuMoi);
        stmt.setInt(2, maKH);

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            thanhCong = true;
        }
        stmt.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    return thanhCong;
}
    public ArrayList<KhachHangDTO> getAllKhachHang() {
        ArrayList<KhachHangDTO> ds = new ArrayList<>();
        String sql = "SELECT * FROM khachhang";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO();
                kh.setMaKhachHang(rs.getInt("MaKH"));
                kh.setTenKhachHang(rs.getString("TenKH"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setEmail(rs.getString("Email"));
                kh.setSoDuTaiKhoan(rs.getDouble("SoDuTaiKhoan"));
                kh.setThoiGianTao(rs.getString("created_at"));
                ds.add(kh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }
    public static KhachHangDTO timTheoMaKH(String maKH) {
        String sql = "SELECT * FROM khachhang WHERE makh = ?";
        KhachHangDTO kh = new KhachHangDTO();
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                    kh.setMaKhachHang(rs.getInt("MaKH"));
                    kh.setTenKhachHang(rs.getString("TenKH"));
                    kh.setSoDienThoai(rs.getString("SoDienThoai"));
                    kh.setEmail(rs.getString("Email"));
                    kh.setSoDuTaiKhoan(rs.getDouble("SoDuTaiKhoan"));
                    kh.setThoiGianTao(rs.getString("created_at"));
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
