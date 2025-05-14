/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBConnect;
import DTO.KhoMayTinhDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Minnie
 */
public class KhoMayTinhDAO {
    public ArrayList <KhoMayTinhDTO> sellectAll() {
        ArrayList <KhoMayTinhDTO> list = new ArrayList<>();
        try{
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Select * From khomaytinh";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                KhoMayTinhDTO kmt = new KhoMayTinhDTO();
                kmt.setMaKho(rs.getInt("MaKho"));
                kmt.setMaNCC(rs.getInt("MaNCC"));
                kmt.setMaMay(rs.getInt("MaMay"));
                kmt.setSoLuong(rs.getInt("SoLuong"));
                kmt.setThoiGianTao(rs.getString("created_at"));
                list.add(kmt);
            }
            DBConnect.closeConnection(con);
        }
        catch(Exception e){
            Logger.getLogger(KhoMayTinhDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }
}
