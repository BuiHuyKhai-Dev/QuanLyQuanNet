package DTO;
public class KhachHangDTO {
    private int maKhachHang;
    private String hoKhachHang;
    private String tenKhachHang;
    private String soDienThoai;
    private String email;
    private double soDuTaiKhoan;
    private String matKhau;
    private int trangThai;
    private String thoiGianTao;

    public KhachHangDTO(){};

    public KhachHangDTO(int maKhachHang, String hoKhachHang, String tenKhachHang, String soDienThoai, String email, double soDuTaiKhoan, String matKhau, int trangThai, String thoiGianTao) {
        this.maKhachHang = maKhachHang;
        this.hoKhachHang = hoKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.soDuTaiKhoan = soDuTaiKhoan;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
        this.thoiGianTao = thoiGianTao;
    }

    public String getHoKhachHang() {
        return hoKhachHang;
    }

    public void setHoKhachHang(String hoKhachHang) {
        this.hoKhachHang = hoKhachHang;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
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
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSoDuTaiKhoan() {
        return soDuTaiKhoan;
    }
    
    public void setSoDuTaiKhoan(double soDuTaiKhoan){
        this.soDuTaiKhoan = soDuTaiKhoan;
    }

    public String getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(String thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    
}
