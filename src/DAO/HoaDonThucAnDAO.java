package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import DAL.DBConnect;
import DTO.ChiTietDonHangDTO;
import DTO.DonHangThucAnDTO;

public class HoaDonThucAnDAO {
    DBConnect db = new DBConnect();

    // ✅ Sửa: KHÔNG đóng conn trước khi executeUpdate
    public boolean themDonHang(int maKhachHang, int maMay,
                               Timestamp ngayDat, double tongTien,
                               int trangThai, int maNV) {
        String sql = "INSERT INTO donhangthucan (maKH, maMay, ngayDat, tongTien, trangThai, maNV) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maKhachHang);
            ps.setInt(2, maMay);
            ps.setTimestamp(3, ngayDat);
            ps.setDouble(4, tongTien);
            ps.setInt(5, trangThai);
            ps.setInt(6, maNV);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<DonHangThucAnDTO> layTatCaHoaDon() {
        ArrayList<DonHangThucAnDTO> dsHoaDon = new ArrayList<>();
        String query = "SELECT * FROM donhangthucan";

        try (Connection conn = DBConnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                DonHangThucAnDTO hd = new DonHangThucAnDTO();
                hd.setMaDonHang(rs.getInt("maDH"));
                hd.setMaKhachHang(rs.getInt("maKH"));
                hd.setMaMay(rs.getInt("maMay"));
                hd.setNgayDat(rs.getTimestamp("ngaydat"));
                hd.setTongTien(rs.getDouble("tongTien"));
                hd.setTrangThai(rs.getInt("trangThai"));
                hd.setMaNV(rs.getInt("maNV"));

                dsHoaDon.add(hd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dsHoaDon;
    }

    public ArrayList<DonHangThucAnDTO> timKiemDonHang(Integer maDonHang, Integer maKhachHang,
                                                      Integer maMay, Timestamp ngayDat,
                                                      Double tongTien, Integer trangThai,
                                                      Integer maNV) {
        ArrayList<DonHangThucAnDTO> ds = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM DonHangThucAn WHERE 1=1 ");
        ArrayList<Object> params = new ArrayList<>();

        if (maDonHang != null) {
            sql.append("AND maDonHang = ? ");
            params.add(maDonHang);
        }
        if (maKhachHang != null) {
            sql.append("AND maKhachHang = ? ");
            params.add(maKhachHang);
        }
        if (maMay != null) {
            sql.append("AND maMay = ? ");
            params.add(maMay);
        }
        if (ngayDat != null) {
            sql.append("AND ngayDat = ? ");
            params.add(ngayDat);
        }
        if (tongTien != null) {
            sql.append("AND tongTien = ? ");
            params.add(tongTien);
        }
        if (trangThai != null) {
            sql.append("AND trangThai = ? ");
            params.add(trangThai);
        }
        if (maNV != null) {
            sql.append("AND maNV = ? ");
            params.add(maNV);
        }

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DonHangThucAnDTO don = new DonHangThucAnDTO();
                don.setMaDonHang(rs.getInt("maDonHang"));
                don.setMaKhachHang(rs.getInt("maKhachHang"));
                don.setMaMay(rs.getInt("maMay"));
                don.setNgayDat(rs.getTimestamp("ngayDat"));
                don.setTongTien(rs.getDouble("tongTien"));
                don.setTrangThai(rs.getInt("trangThai"));
                don.setMaNV(rs.getInt("maNV"));
                ds.add(don);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    public boolean capNhatDonHang(int maDonHang, Integer maKhachHang, Integer maMay,
                                   Timestamp ngayDat, Double tongTien,
                                   Integer trangThai, Integer maNV) {
        StringBuilder sql = new StringBuilder("UPDATE DonHangThucAn SET ");
        ArrayList<Object> values = new ArrayList<>();

        if (maKhachHang != null) {
            sql.append("maKhachHang = ?, ");
            values.add(maKhachHang);
        }
        if (maMay != null) {
            sql.append("maMay = ?, ");
            values.add(maMay);
        }
        if (ngayDat != null) {
            sql.append("ngayDat = ?, ");
            values.add(ngayDat);
        }
        if (tongTien != null) {
            sql.append("tongTien = ?, ");
            values.add(tongTien);
        }
        if (trangThai != null) {
            sql.append("trangThai = ?, ");
            values.add(trangThai);
        }
        if (maNV != null) {
            sql.append("maNV = ?, ");
            values.add(maNV);
        }

        if (values.isEmpty()) return false;

        sql.setLength(sql.length() - 2); // xóa dấu ", "
        sql.append(" WHERE maDonHang = ?");
        values.add(maDonHang);

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < values.size(); i++) {
                stmt.setObject(i + 1, values.get(i));
            }

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaHoaDon(int maDonHang) {
        String query = "DELETE FROM donhangthucan WHERE maDonHang = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, maDonHang);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<ChiTietDonHangDTO> timChiTietDonHang(int maDonHang) {
    ArrayList<ChiTietDonHangDTO> danhSachChiTiet = new ArrayList<>();
    String query = "SELECT * FROM chitietdonthucan WHERE maDH = ?";

    try (Connection conn = db.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, maDonHang);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            ChiTietDonHangDTO chiTiet = new ChiTietDonHangDTO();
            chiTiet.setMaDH(rs.getInt("maDH"));
            chiTiet.setMaThucAn(rs.getInt("maThucAn"));
            chiTiet.setSoLuong(rs.getInt("soLuong"));
            chiTiet.setDonGia(rs.getDouble("donGia"));

            danhSachChiTiet.add(chiTiet);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return danhSachChiTiet;
}

    public boolean themChiTietDonHang(ChiTietDonHangDTO ct) {
        try {
            Connection conn = db.getConnection(); // Kết nối CSDL
            String sql = "INSERT INTO chitietdonthucan (maDH, maThucAn, soLuong, donGia) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ct.getMaDH());
            ps.setInt(2, ct.getMaThucAn());
            ps.setInt(3, ct.getSoLuong());
            ps.setDouble(4, ct.getDonGia());

            int rowsInserted = ps.executeUpdate();
            conn.close();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
      public int getLastInsertedId() {
        int id = -1;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = db.getConnection(); // Hàm mở kết nối
            String sql = "SELECT MAX(maDH) AS lastId FROM donhangthucan";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("lastId");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        return id;
    }
    

}
