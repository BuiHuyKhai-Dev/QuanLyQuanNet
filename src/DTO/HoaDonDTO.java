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
Phương thức thanh toán
1 = Tiền mặt
2 = Chuyển khoản
*/
public class HoaDonDTO {
    private int maHoaDon;
    private int maKhachHang;
    private int loaiHoaDon;
    private int reflID;
    Timestamp ngayLap;
    private double tongTien;
    private int phuongThucThanhToan;
    private Timestamp thoiGianTao;
    
    public HoaDonDTO(){}

    public HoaDonDTO(int maHoaDon, int maKhachHang, int loaiHoaDon, int reflID, Timestamp ngayLap, double tongTien, int phuongThucThanhToan, Timestamp thoiGianTao) {
        this.maHoaDon = maHoaDon;
        this.maKhachHang = maKhachHang;
        this.loaiHoaDon = loaiHoaDon;
        this.reflID = reflID;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.thoiGianTao = thoiGianTao;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public int getLoaiHoaDon() {
        return loaiHoaDon;
    }

    public void setLoaiHoaDon(int loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }

    public int getReflID() {
        return reflID;
    }

    public void setReflID(int reflID) {
        this.reflID = reflID;
    }

    public Timestamp getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Timestamp ngayLap) {
        this.ngayLap = ngayLap;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(int phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
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
        hash = 11 * hash + this.maHoaDon;
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
        final HoaDonDTO other = (HoaDonDTO) obj;
        return this.maHoaDon == other.maHoaDon;
    }

    @Override
    public String toString() {
        return "HoaDonDTO{" + "maHoaDon=" + maHoaDon + ", maKhachHang=" + maKhachHang + ", loaiHoaDon=" + loaiHoaDon + ", reflID=" + reflID + ", ngayLap=" + ngayLap + ", tongTien=" + tongTien + ", phuongThucThanhToan=" + phuongThucThanhToan + ", thoiGianTao=" + thoiGianTao + '}';
    }
    
    
}
