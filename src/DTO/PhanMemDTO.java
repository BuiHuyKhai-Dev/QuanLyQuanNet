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
public class PhanMemDTO {
    private int maPhanMem;
    private String tenPhanMem;
    private String loaiPhanMem;
    private String moTa;
    private Timestamp thoiGianTao;

    public PhanMemDTO() {
    }

    public PhanMemDTO(int maPhanMem, String tenPhanMem, String loaiPhanMem, String moTa, Timestamp thoiGianTao) {
        this.maPhanMem = maPhanMem;
        this.tenPhanMem = tenPhanMem;
        this.loaiPhanMem = loaiPhanMem;
        this.moTa = moTa;
        this.thoiGianTao = thoiGianTao;
    }

    public int getMaPhanMem() {
        return maPhanMem;
    }

    public void setMaPhanMem(int maPhanMem) {
        this.maPhanMem = maPhanMem;
    }

    public String getTenPhanMem() {
        return tenPhanMem;
    }

    public void setTenPhanMem(String tenPhanMem) {
        this.tenPhanMem = tenPhanMem;
    }

    public String getLoaiPhanMem() {
        return loaiPhanMem;
    }

    public void setLoaiPhanMem(String loaiPhanMem) {
        this.loaiPhanMem = loaiPhanMem;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Timestamp getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Timestamp thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    @Override
    public String toString() {
        return "PhanMemDTO{" + "maPhanMem=" + maPhanMem + ", tenPhanMem=" + tenPhanMem + ", loaiPhanMem=" + loaiPhanMem + ", moTa=" + moTa + ", thoiGianTao=" + thoiGianTao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.maPhanMem;
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
        final PhanMemDTO other = (PhanMemDTO) obj;
        return this.maPhanMem == other.maPhanMem;
    }
    
    
}
