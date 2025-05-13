/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;
import DTO.DonHangThucAnDTO;
import DTO.MayTinhDTO;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class DatMayDAL {
   DBConnect db= new DBConnect();
   Connection conn= null;
    public ArrayList<MayTinhDTO> selectAll() {
        ArrayList<MayTinhDTO> ds = new ArrayList<>();
        try (Connection conn = db.getConnection()){
            String sql = "SELECT MaMay, TenMay, TrangThai, ViTri, GiaThue FROM MayTinh";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int maMay = rs.getInt("MaMay");
                String tenMay = rs.getString("TenMay");
                int trangThai = rs.getInt("TrangThai");
                String viTri = rs.getString("ViTri");
                int giaThue = rs.getInt("GiaThue");

                MayTinhDTO may = new MayTinhDTO(maMay, tenMay, trangThai, viTri, giaThue);
                ds.add(may);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }
    

    public double layGiaThue(int maMay) throws SQLException {
        double giaThue = 0;
        try (Connection conn = db.getConnection()){
        // Câu lệnh SQL để truy vấn giá thuê của máy tính
        String sql = "SELECT GiaThue FROM maytinh WHERE MaMay = ?";

        // Tạo PreparedStatement
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, maMay); // Thiết lập MaMay vào tham số câu lệnh SQL

        // Thực thi câu lệnh SQL
        ResultSet rs = stmt.executeQuery();

        // Kiểm tra kết quả trả về từ câu lệnh SQL
        if (rs.next()) {
            giaThue = rs.getDouble("GiaThue"); // Lấy giá thuê từ cột GiaThue
        }

        // Đóng ResultSet và PreparedStatement
        rs.close();
        stmt.close();
        }catch (Exception e){
            System.out.println("loi");
            return -1;
        }

        // Trả về giá thuê
        return giaThue;
    }
    
    public boolean capnhattrangthai(int maMay, int tt){
        String query= "Update maytinh set trangthai= ? where maMay= ?";
        try (Connection conn = db.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, tt);
            stmt.setInt(2, maMay);
            stmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println("loi");
            return false;
        }
        return true;
    }

}
