package DTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import DAO.ThucAnDAO;

public class ThucAnDTO {
    private int maThucAn;
    private String tenThucAn;
    private int soLuong;
    private double donGia;
    private String thoiGianTao;
    
    public ThucAnDTO() {}

    public ThucAnDTO(int maThucAn, String tenThucAn, int soLuong, double donGia, String thoiGianTao) {
        this.maThucAn = maThucAn;
        this.tenThucAn = tenThucAn;
        this.soLuong = soLuong;
        this.donGia = donGia;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(String thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    
}
