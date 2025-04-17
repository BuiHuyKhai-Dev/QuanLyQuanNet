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
public class KhoMayTinhDTO {
    private int maKho;
    private int maMay;
    private int soLuong;
    private Timestamp thoiGianTao;
    
    public KhoMayTinhDTO(){}

    public KhoMayTinhDTO(int maKho, int maMay, int soLuong, Timestamp thoiGianTao) {
        this.maKho = maKho;
        this.maMay = maMay;
        this.soLuong = soLuong;
        this.thoiGianTao = thoiGianTao;
    }

    public int getMaKho() {
        return maKho;
    }

    public void setMaKho(int maKho) {
        this.maKho = maKho;
    }

    public int getMaMay() {
        return maMay;
    }

    public void setMaMay(int maMay) {
        this.maMay = maMay;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Timestamp getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Timestamp thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    @Override
    public String toString() {
        return "KhoMayTinhDTO{" + "maKho=" + maKho + ", maMay=" + maMay + ", soLuong=" + soLuong + ", thoiGianTao=" + thoiGianTao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.maKho;
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
        final KhoMayTinhDTO other = (KhoMayTinhDTO) obj;
        return this.maKho == other.maKho;
    }
    
    
}
