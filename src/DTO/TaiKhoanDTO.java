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
    private String maNV;
    private String tenDangNhap;
    private String matKhau;
    private String nhomQuyen;
    private int trangThai;

    // Constructor không tham số
    public TaiKhoanDTO() {
    }

    // Constructor đầy đủ tham số
    public TaiKhoanDTO(String maNV, String tenDangNhap, String matKhau, String nhomQuyen, int trangThai) {
        this.maNV = maNV;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.nhomQuyen = nhomQuyen;
        this.trangThai = trangThai;
    }

    // Getter và Setter
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

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

    public String getNhomQuyen() {
        return nhomQuyen;
    }

    public void setNhomQuyen(String nhomQuyen) {
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
                "maNV='" + maNV + '\'' +
                ", tenDangNhap='" + tenDangNhap + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", nhomQuyen='" + nhomQuyen + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
