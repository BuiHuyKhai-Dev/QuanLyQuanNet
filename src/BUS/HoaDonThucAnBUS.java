package BUS;

import java.sql.Timestamp;
import java.util.ArrayList;

import DAL.*;
import DAO.HoaDonThucAnDAO;
import DAO.KhachHangDAO;
import DAO.MayTinhDAO;
import DAO.NhanVienDAO;
import DTO.ChiTietHoaDonDTO;
import DTO.DonHangThucAnDTO;

public class HoaDonThucAnBUS {
    private HoaDonThucAnDAO dhDAL= new HoaDonThucAnDAO();
    private KhachHangDAO khDAL= new KhachHangDAO();
    private MayTinhDAO mayDAL= new MayTinhDAO();
    private NhanVienDAO nvDAL= new NhanVienDAO();

    public boolean themHoaDon(int maKhachHang, int maMay, Timestamp ngayDat, double tongTien, int trangThai, int maNV) {
        if (!khDAL.tonTaiMaKH(maKhachHang)) {
            System.out.println("❌ Mã khách hàng không tồn tại!");
            return false;
        }
        if (nvDAL.selectById(String.valueOf(maNV)) == null) {
            System.out.println("❌ Mã nhân viên không tồn tại!");
            return false;
        }

        if (mayDAL.chonMayTinhTheoMa(maMay)== null) {
            System.out.println("❌ Mã máy không tồn tại!");
            return false;
        }

        return dhDAL.themDonHang(maKhachHang, maMay, ngayDat, tongTien, trangThai, maNV);
    }

    public ArrayList<DonHangThucAnDTO> selectAll() {
        return dhDAL.layTatCaHoaDon();
    }

    public boolean capNhatDonHang(int maDonHang, Integer maKhachHang, Integer maMay,
                                  Timestamp ngayDat, Double tongTien,
                                  Integer trangThai, Integer maNV) {
        return dhDAL.capNhatDonHang(maDonHang, maKhachHang, maMay, ngayDat, tongTien, trangThai, maNV);
    }

    // Gọi hàm tìm kiếm từ DAL
    public ArrayList<DonHangThucAnDTO> timKiemDonHang(Integer maDonHang, Integer maKhachHang,
                                                      Integer maMay, Timestamp ngayDat,
                                                      Double tongTien, Integer trangThai,
                                                      Integer maNV) {
        return dhDAL.timKiemDonHang(maDonHang, maKhachHang, maMay, ngayDat, tongTien, trangThai, maNV);
    }
    public boolean xoaHoaDon(int maDonHang) {
        return dhDAL.xoaHoaDon(maDonHang);
    }
}
