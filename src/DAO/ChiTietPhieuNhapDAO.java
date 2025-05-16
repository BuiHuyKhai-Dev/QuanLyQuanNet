/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBConnect;
import DTO.ChiTietPhieuNhapDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Minnie
 */
public class ChiTietPhieuNhapDAO {
    public ArrayList<ChiTietPhieuNhapDTO> selectAll() {
        ArrayList<ChiTietPhieuNhapDTO> list = new ArrayList<>();
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "SELECT * FROM chitietphieunhap";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ChiTietPhieuNhapDTO ct = new ChiTietPhieuNhapDTO();
                ct.setMaPN(rs.getInt("MaPN"));
                ct.setMaThucAn(rs.getInt("MaThucAn"));
                ct.setSoLuong(rs.getInt("SoLuong"));
                ct.setDonGia(rs.getDouble("DonGia"));
                ct.setThanhTien(rs.getDouble("ThanhTien"));
                list.add(ct);
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insert(ChiTietPhieuNhapDTO t) {
        int result = 0;
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "Insert into chitietphieunhap (MaPN,MaThucAn,SoLuong,DonGia,ThanhTien) values"
                    + " ( ? , ? , ? , ? , ? ) ";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getMaPN());
            pst.setInt(2, t.getMaThucAn());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            pst.setDouble(5, t.getThanhTien());
            result = pst.executeUpdate();
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<ChiTietPhieuNhapDTO> selectByMaPN(int maPN) {
        ArrayList<ChiTietPhieuNhapDTO> list = new ArrayList<>();
        try {
            Connection con = (Connection) DBConnect.getConnection();
            String sql = "SELECT * FROM chitietphieunhap WHERE MaPN = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, maPN);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ChiTietPhieuNhapDTO ct = new ChiTietPhieuNhapDTO();
                ct.setMaPN(rs.getInt("MaPN"));
                ct.setMaThucAn(rs.getInt("MaThucAn"));
                ct.setSoLuong(rs.getInt("SoLuong"));
                ct.setDonGia(rs.getDouble("DonGia"));
                ct.setThanhTien(rs.getDouble("ThanhTien"));
                list.add(ct);
            }
            DBConnect.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
