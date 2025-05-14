package DTO;
public class KhoThucAnDTO {
    private int maKho;
    private int maNCC;
    private int maThucAn;
    private String tenThucAn;
    private int soLuong;

    public KhoThucAnDTO() {}

    public KhoThucAnDTO(int maKho, int maNCC, int maThucAn, String tenThucAn, int soLuong) {
        this.maKho = maKho;
        this.maNCC = maNCC;
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

    public int getmaNCC() {
        return maNCC;
    }

    public void setmaNCC(int maNCC) {
        this.maNCC = maNCC;
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

