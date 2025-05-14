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
    private int maNCC;
    private int maMay;
    private int soLuong;
    private String thoiGianTao;
    
    public KhoMayTinhDTO(){}

    public KhoMayTinhDTO(int maKho, int maNCC,int maMay, int soLuong, String thoiGianTao) {
        this.maKho = maKho;
        this.maNCC = maNCC;
        this.maMay = maMay;
        this.soLuong = soLuong;
        this.thoiGianTao = thoiGianTao;
    }
    
    public int getMaNCC() {
        return maNCC;
    }
    
    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
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

    public String getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(String thoiGianTao) {
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
