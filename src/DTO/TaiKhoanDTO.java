/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Minnie
 */
public class TaiKhoanDTO {
    private String tenDangNhap;
    private String matKhau;
    private int nhomQuyen;
    private int trangThai;

    // Constructor không tham số
    public TaiKhoanDTO() {
    }

    // Constructor đầy đủ tham số
    public TaiKhoanDTO(String tenDangNhap, String matKhau, int nhomQuyen, int trangThai) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.nhomQuyen = nhomQuyen;
        this.trangThai = trangThai;
    }

    // Getter và Settet

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getMaNhomQuyen() {
        return nhomQuyen;
    }

    public void setMaNhomQuyen(int nhomQuyen) {
        this.nhomQuyen = nhomQuyen;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    // toString
    @Override
    public String toString() {
        return "TaiKhoanDTO{" +
                ", tenDangNhap='" + tenDangNhap + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", nhomQuyen='" + nhomQuyen + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
