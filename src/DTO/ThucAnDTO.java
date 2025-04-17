/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Minnie
 */
public class ThucAnDTO {
    private int maThucAn;
    private String tenThucAn;
    private String donVi;
    private double donGia;
    private Date hanSuDung;
    private Timestamp thoiGianTao;

    public ThucAnDTO() {
    }

    public ThucAnDTO(int maThucAn, String tenThucAn, String donVi, double donGia, Date hanSuDung, Timestamp thoiGianTao) {
        this.maThucAn = maThucAn;
        this.tenThucAn = tenThucAn;
        this.donVi = donVi;
        this.donGia = donGia;
        this.hanSuDung = hanSuDung;
        this.thoiGianTao = thoiGianTao;
    }

    public int getMaThucAn() {
        return maThucAn;
    }

    public void setMaThucAn(int maThucAn) {
        this.maThucAn = maThucAn;
    }

    public String getTenThucAn() {
        return tenThucAn;
    }

    public void setTenThucAn(String tenThucAn) {
        this.tenThucAn = tenThucAn;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(Date hanSuDung) {
        this.hanSuDung = hanSuDung;
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
        hash = 97 * hash + this.maThucAn;
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
        final ThucAnDTO other = (ThucAnDTO) obj;
        return this.maThucAn == other.maThucAn;
    }

    @Override
    public String toString() {
        return "ThucAnDTO{" + "maThucAn=" + maThucAn + ", tenThucAn=" + tenThucAn + ", donVi=" + donVi + ", donGia=" + donGia + ", hanSuDung=" + hanSuDung + ", thoiGianTao=" + thoiGianTao + '}';
    }
    
    
}
