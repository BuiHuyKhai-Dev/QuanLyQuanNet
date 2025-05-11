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
public class SuDungMayDTO {
    private int maSuDung;
    private int maNV;
    private int maKhachHang;
    private int maMay;
    private Timestamp thoiGianBatDau;
    private Timestamp thoiGianKetThuc;
    private double tongThoiGian;
    private double chiPhi;
    private int maPhanMem;
    private Timestamp thoiGianTao;

    public SuDungMayDTO() {
    }

    public SuDungMayDTO(int maSuDung, int maNV, int maKhachHang, int maMay, Timestamp thoiGianBatDau, Timestamp thoiGianKetThuc, double tongThoiGian, double chiPhi) {
        this.maSuDung = maSuDung;
        this.maNV = maNV;
        this.maKhachHang = maKhachHang;
        this.maMay = maMay;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.tongThoiGian = tongThoiGian;
        this.chiPhi = chiPhi;
    }

    public int getMaSuDung() {
        return maSuDung;
    }

    public void setMaSuDung(int maSuDung) {
        this.maSuDung = maSuDung;
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
    public int getMaNV() {
        return maNV;
    }

    public void setNV(int maNV) {
        this.maNV= maNV;
    }

    public Timestamp getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(Timestamp thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public Timestamp getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Timestamp thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public double getTongThoiGian() {
        return tongThoiGian;
    }

    public void setTongThoiGian(int tongThoiGian) {
        this.tongThoiGian = tongThoiGian;
    }

    public double getChiPhi() {
        return chiPhi;
    }

    public void setChiPhi(double chiPhi) {
        this.chiPhi = chiPhi;
    }

    public int getMaPhanMem() {
        return maPhanMem;
    }

    public void setMaPhanMem(int maPhanMem) {
        this.maPhanMem = maPhanMem;
    }

    public Timestamp getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Timestamp thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    @Override
    public String toString() {
        return "SuDungMayDTO{" + "maSuDung=" + maSuDung + ", maKhachHang=" + maKhachHang + ", maMay=" + maMay + ", thoiGianBatDau=" + thoiGianBatDau + ", thoiGianKetThuc=" + thoiGianKetThuc + ", tongThoiGian=" + tongThoiGian + ", chiPhi=" + chiPhi + ", maPhanMem=" + maPhanMem + ", thoiGianTao=" + thoiGianTao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.maSuDung;
        hash = 97 * hash + this.maKhachHang;
        hash = 97 * hash + this.maMay;
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
        final SuDungMayDTO other = (SuDungMayDTO) obj;
        if (this.maSuDung != other.maSuDung) {
            return false;
        }
        if (this.maKhachHang != other.maKhachHang) {
            return false;
        }
        return this.maMay == other.maMay;
    }
    
    
}