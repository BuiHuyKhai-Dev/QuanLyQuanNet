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
public class KhachHangDTO {
    private int maKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String Email;
    private String matKhau;
    private double soDuTaiKhoan;
    private Timestamp thoigianTao;

    public KhachHangDTO(){}
    
    public KhachHangDTO(int maKhachHang, String tenKhachHang, String soDienThoai, String Email, String matKhau, double soDuTaiKhoan, Timestamp thoigianTao) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.Email = Email;
        this.matKhau = matKhau;
        this.soDuTaiKhoan = soDuTaiKhoan;
        this.thoigianTao = thoigianTao;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public double getSoDuTaiKhoan() {
        return soDuTaiKhoan;
    }

    public void setSoDuTaiKhoan(double soDuTaiKhoan) {
        this.soDuTaiKhoan = soDuTaiKhoan;
    }

    public Timestamp getThoigianTao() {
        return thoigianTao;
    }

    public void setThoigianTao(Timestamp thoigianTao) {
        this.thoigianTao = thoigianTao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.maKhachHang;
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
        final KhachHangDTO other = (KhachHangDTO) obj;
        return this.maKhachHang == other.maKhachHang;
    }

    @Override
    public String toString() {
        return "KhachHangDTO{" + "maKhachHang=" + maKhachHang + ", tenKhachHang=" + tenKhachHang + ", soDienThoai=" + soDienThoai + ", Email=" + Email + ", matKhau=" + matKhau + ", soDuTaiKhoan=" + soDuTaiKhoan + ", thoigianTao=" + thoigianTao + '}';
    }
}
