package DAO;

import DTO.NhanVienDTO;
import DAL.DBConnect;
import DAL.DBConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// import DTO.

public class NhanVienDAO {
    private static final String TABLE_NAME = "nhanvien";

    public List<NhanVienDTO> getDanhSachNhanVien() {
        List<NhanVienDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO(
                    rs.getString("manv"),
                    rs.getString("tennv"),
                    rs.getString("matkhau"),
                    rs.getString("cccd"),
                    rs.getString("sodt"),
                    rs.getString("ngaysinh"),
                    rs.getString("ngaydangky"),
                    rs.getString("username"),
                    rs.getString("role"),
                    rs.getString("chucvu"),
                    rs.getInt("trangthai")
                );
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean themNhanVien(NhanVienDTO nv) {
        String sql = "INSERT INTO nhanvien (manv, tennv, matkhau, cccd, sodt, ngaysinh, ngaydangky, username, role, chucvu, trangthai) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nv.getMaNV());
            pstmt.setString(2, nv.getTenNV());
            pstmt.setString(3, nv.getMatKhau());
            pstmt.setString(4, nv.getCccd());
            pstmt.setString(5, nv.getSoDT());
            pstmt.setString(6, nv.getNgaySinh());
            pstmt.setString(7, nv.getNgayDangKy());
            pstmt.setString(8, nv.getUsername());
            pstmt.setString(9, nv.getRole());
            pstmt.setString(10, nv.getChucVu());
            pstmt.setInt(11, nv.getTrangThai());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean suaNhanVien(NhanVienDTO nv) {
        String sql = "UPDATE nhanvien SET tennv=?, matkhau=?, cccd=?, sodt=?, ngaysinh=?, username=?, role=?, chucvu=?, trangthai=? WHERE manv=?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nv.getTenNV());
            pstmt.setString(2, nv.getMatKhau());
            pstmt.setString(3, nv.getCccd());
            pstmt.setString(4, nv.getSoDT());
            pstmt.setString(5, nv.getNgaySinh());
            pstmt.setString(6, nv.getUsername());
            pstmt.setString(7, nv.getRole());
            pstmt.setString(8, nv.getChucVu());
            pstmt.setInt(9, nv.getTrangThai());
            pstmt.setString(10, nv.getMaNV());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public NhanVienDTO timTheoMaNV(String maNV) {
        String sql = "SELECT * FROM nhanvien WHERE manv = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maNV);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new NhanVienDTO(
                    rs.getString("manv"),
                    rs.getString("tennv"),
                    rs.getString("matkhau"),
                    rs.getString("cccd"),
                    rs.getString("sodt"),
                    rs.getString("ngaysinh"),
                    rs.getString("ngaydangky"),
                    rs.getString("username"),
                    rs.getString("role"),
                    rs.getString("chucvu"),
                    rs.getInt("trangthai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        public static boolean xoaNhanVien(String maNV) {
        String sql = "UPDATE nhanvien SET trangthai = 0 WHERE manv = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
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
                NhanVienDTO nv = new NhanVienDTO(
                    rs.getString("manv"),
                    rs.getString("tennv"),
                    rs.getString("matkhau"),
                    rs.getString("cccd"),
                    rs.getString("sodt"),
                    rs.getString("ngaysinh"),
                    rs.getString("ngaydangky"),
                    rs.getString("username"),
                    rs.getString("role"),
                    rs.getString("chucvu"),
                    rs.getInt("trangthai")
                );
                result.add(nv);
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
                        // Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println("Lỗi: " + e.getMessage());
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
                nv = new NhanVienDTO(
                    rs.getString("manv"),
                    rs.getString("tennv"),
                    rs.getString("matkhau"),
                    rs.getString("cccd"),
                    rs.getString("sodt"),
                    rs.getString("ngaysinh"),
                    rs.getString("ngaydangky"),
                    rs.getString("username"),
                    rs.getString("role"),
                    rs.getString("chucvu"),
                    rs.getInt("trangthai")
                );
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return nv;
    }

}
