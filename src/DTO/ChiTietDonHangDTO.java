/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

public class ChiTietDonHangDTO {
    private String maDH;
    private String maThucAn;
    private int soLuong;
    private double donGia;

    // Constructor không tham số
    public ChiTietDonHangDTO() {
    }

    // Constructor đầy đủ tham số
    public ChiTietDonHangDTO(String maDH, String maThucAn, int soLuong, double donGia) {
        this.maDH = maDH;
        this.maThucAn = maThucAn;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    // Getter và Setter
    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getMaThucAn() {
        return maThucAn;
    }

    public void setMaThucAn(String maThucAn) {
        this.maThucAn = maThucAn;
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

    // toString để in thông tin đối tượng
    @Override
    public String toString() {
        return "ChiTietDonHangDTO{" +
                "maDH='" + maDH + '\'' +
                ", maThucAn='" + maThucAn + '\'' +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                '}';
    }
}
