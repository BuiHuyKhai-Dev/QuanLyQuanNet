package DTO;

public class NhanVienDTO {
    private String maNV;
    private String tenNV;
    private String matKhau;
    private String cccd;
    private String soDT;
    private String ngaySinh;
    private String ngayDangKy;
    private String username;
    private String role;
    private String chucVu;
    private int trangThai;

    // Constructor mặc định
    public NhanVienDTO() {}

    // Constructor đầy đủ tham số
    public NhanVienDTO(String maNV, String tenNV, String matKhau, String cccd, 
                      String soDT, String ngaySinh, String ngayDangKy, 
                      String username, String role, String chucVu, int trangThai) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.matKhau = matKhau;
        this.cccd = cccd;
        this.soDT = soDT;
        this.ngaySinh = ngaySinh;
        this.ngayDangKy = ngayDangKy;
        this.username = username;
        this.role = role;
        this.chucVu = chucVu;
        this.trangThai = trangThai;
    }

    // Getter & Setter
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public String getSoDT() { return soDT; }
    public void setSoDT(String soDT) { this.soDT = soDT; }

    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getNgayDangKy() { return ngayDangKy; }
    public void setNgayDangKy(String ngayDangKy) { this.ngayDangKy = ngayDangKy; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }

    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
}
