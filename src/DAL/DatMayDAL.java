/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;
import DTO.MayTinhDTO;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class DatMayDAL {
   DBConnect db= new DBConnect();
   Connection conn= db.getConnection();
    public ArrayList<MayTinhDTO> layDanhSachMay() {
        ArrayList<MayTinhDTO> ds = new ArrayList<>();
        try {
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
}
