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
public class KhoThucAn {
    private int maKho;
    private int maThucAn;
    private int soLuong;
    private Timestamp thoiGianTao;

    public KhoThucAn() {
    }

    public KhoThucAn(int maKho, int maThucAn, int soLuong, Timestamp thoiGianTao) {
        this.maKho = maKho;
        this.maThucAn = maThucAn;
        this.soLuong = soLuong;
        this.thoiGianTao = thoiGianTao;
    }

    public int getMaKho() {
        return maKho;
    }

    public void setMaKho(int maKho) {
        this.maKho = maKho;
    }

    public int getMaThucAn() {
        return maThucAn;
    }

    public void setMaThucAn(int maThucAn) {
        this.maThucAn = maThucAn;
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
        return "KhoThucAn{" + "maKho=" + maKho + ", maThucAn=" + maThucAn + ", soLuong=" + soLuong + ", thoiGianTao=" + thoiGianTao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.maKho;
        hash = 89 * hash + this.maThucAn;
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
        final KhoThucAn other = (KhoThucAn) obj;
        if (this.maKho != other.maKho) {
            return false;
        }
        return this.maThucAn == other.maThucAn;
    }
    
    
}
