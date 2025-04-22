package DTO;
public class KhachHangDTO {
    private String maKH, tenKH, matKhau, cccd, soDT, ngaySinh, ngayDangKy;
    private int soGio;
    private double soTienNaptong, soDu;
    private int trangThai;

    public KhachHangDTO() {}

    public KhachHangDTO(String maKH, String tenKH, String matKhau, String cccd, String soDT,
                        String ngaySinh, String ngayDangKy, int soGio, double soTienNaptong,
                        double soDu, int trangThai) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.matKhau = matKhau;
        this.cccd = cccd;
        this.soDT = soDT;
        this.ngaySinh = ngaySinh;
        this.ngayDangKy = ngayDangKy;
        this.soGio = soGio;
        this.soTienNaptong = soTienNaptong;
        this.soDu = soDu;
        this.trangThai = trangThai;
    }

    // Getter & Setter
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }

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

    public int getSoGio() { return soGio; }
    public void setSoGio(int soGio) { this.soGio = soGio; }

    public double getSoTienNaptong() { return soTienNaptong; }
    public void setSoTienNaptong(double soTienNaptong) { this.soTienNaptong = soTienNaptong; }

    public double getSoDu() { return soDu; }
    public void setSoDu(double soDu) { this.soDu = soDu; }

    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
}
