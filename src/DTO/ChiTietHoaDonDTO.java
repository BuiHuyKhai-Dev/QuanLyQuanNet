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
public class ChiTietHoaDonDTO {
    private int maHoaDon;
    private int maHang;
    private int maSoLuong;
    private double donGia;
    private double thanhTien;
    private Timestamp thoiGianTao;

    public ChiTietHoaDonDTO() {
    }

    public ChiTietHoaDonDTO(int maHoaDon, int maHang, int maSoLuong, double donGia, double thanhTien, Timestamp thoiGianTao) {
        this.maHoaDon = maHoaDon;
        this.maHang = maHang;
        this.maSoLuong = maSoLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
        this.thoiGianTao = thoiGianTao;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaHang() {
        return maHang;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }

    public int getMaSoLuong() {
        return maSoLuong;
    }

    public void setMaSoLuong(int maSoLuong) {
        this.maSoLuong = maSoLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public Timestamp getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Timestamp thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDonDTO{" + "maHoaDon=" + maHoaDon + ", maHang=" + maHang + ", maSoLuong=" + maSoLuong + ", donGia=" + donGia + ", thanhTien=" + thanhTien + ", thoiGianTao=" + thoiGianTao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.maHoaDon;
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
        final ChiTietHoaDonDTO other = (ChiTietHoaDonDTO) obj;
        return this.maHoaDon == other.maHoaDon;
    }

    
}
