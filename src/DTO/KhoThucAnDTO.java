package DTO;
public class KhoThucAnDTO {
    private int maKho;
    private String tenKho;
    private int maThucAn;
    private String tenThucAn;
    private int soLuong;

    public KhoThucAnDTO() {}

    public KhoThucAnDTO(int maKho, String tenKho, int maThucAn, String tenThucAn, int soLuong) {
        this.maKho = maKho;
        this.tenKho = tenKho;
        this.maThucAn = maThucAn;
        this.tenThucAn = tenThucAn;
        this.soLuong = soLuong;
    }

    public int getMaKho() {
        return maKho;
    }

    public void setMaKho(int maKho) {
        this.maKho = maKho;
    }

    public String getTenKho() {
        return tenKho;
    }

    public void setTenKho(String tenKho) {
        this.tenKho = tenKho;
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
}

