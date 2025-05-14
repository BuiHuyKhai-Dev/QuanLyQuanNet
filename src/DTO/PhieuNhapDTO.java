/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Minnie
 */
public class PhieuNhapDTO {
    private int maPN;
    private int maNCC;
    private int maNV;
    private double tongTien;
    private String thoiGianTao;
    
    public PhieuNhapDTO(int maPN, int maNCC, int maNV, double tongTien, String thoiGianTao) {
        this.maPN = maPN;
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.tongTien = tongTien;
        this.thoiGianTao = thoiGianTao;
    }

    public PhieuNhapDTO() {
    }

    public int getMaPN() {
        return maPN;
    }

    public void setMaPN(int maPN) {
        this.maPN = maPN;
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(String thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    
}
