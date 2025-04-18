/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Timestamp;
/**
 *
 * @author Minnie
 */
/*
Trạng thái:
0 = Chưa hoàn thành
1 = Hoàn thành
2 = Đã hủy
*/

public class DonHangThucAnDTO {
    private int maDonHang;
    private int maKhachHang;
    private int maMay;
    private Timestamp ngayDat;
    private double tongTien;
    private int trangThai;
    private Timestamp thoiGianTao;
    
    public DonHangThucAnDTO(){}

    public DonHangThucAnDTO(int maDonHang, int maKhachHang, int maMay, Timestamp ngayDat, double tongTien, int trangThai, Timestamp thoiGianTao) {
        this.maDonHang = maDonHang;
        this.maKhachHang = maKhachHang;
        this.maMay = maMay;
        this.ngayDat = ngayDat;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.thoiGianTao = thoiGianTao;
    }

    public int getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public int getMaMay() {
        return maMay;
    }

    public void setMaMay(int maMay) {
        this.maMay = maMay;
    }

    public Timestamp getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Timestamp ngayDat) {
        this.ngayDat = ngayDat;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public Timestamp getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Timestamp thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.maDonHang;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DonHangThucAnDTO other = (DonHangThucAnDTO) obj;
        return this.maDonHang == other.maDonHang;
    }

    @Override
    public String toString() {
        return "DonHangThucAnDTO{" + "maDonHang=" + maDonHang + ", maKhachHang=" + maKhachHang + ", maMay=" + maMay + ", ngayDat=" + ngayDat + ", tongTien=" + tongTien + ", trangThai=" + trangThai + ", thoiGianTao=" + thoiGianTao + '}';
    }
    
    
}
