/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Minnie
 */
public class NhanVienDTO {
    private int maNV;
    private String hoNV;
    private String tenNV;
    private String username;
    private String matkhau;
    private int role;
    
    public NhanVienDTO(){}

    public NhanVienDTO(int maNV, String hoNV, String tenNV, String username, String matkhau, int role) {
        this.maNV = maNV;
        this.hoNV = hoNV;
        this.tenNV = tenNV;
        this.username = username;
        this.matkhau = matkhau;
        this.role = role;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getHoNV() {
        return hoNV;
    }

    public void setHoNV(String hoNV) {
        this.hoNV = hoNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.maNV;
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
        final NhanVienDTO other = (NhanVienDTO) obj;
        return this.maNV == other.maNV;
    }

    @Override
    public String toString() {
        return "NhanVienDTO{" + "maNV=" + maNV + ", hoNV=" + hoNV + ", tenNV=" + tenNV + ", username=" + username + ", matkhau=" + matkhau + ", role=" + role + '}';
    }
    
    
}
