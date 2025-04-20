package DTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import DAO.ThucAnDAO;

public class ThucAnDTO {
    private int maThucAn;
    private String tenThucAn;
    private String donVi;
    private BigDecimal donGia;
    private Date hanSuDung;
    private Timestamp createdAt;
    public ThucAnDTO() {}

    public ThucAnDTO(int maThucAn, String tenThucAn, String donVi, BigDecimal donGia, Date hanSuDung) {
        this.maThucAn = maThucAn;
        this.tenThucAn = tenThucAn;
        this.donVi = donVi;
        this.donGia = donGia;
        this.hanSuDung = hanSuDung;
    }

    // Getter và Setter
    public static ArrayList<ThucAnDTO> getAll(){
        return ThucAnDAO.getAll();
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

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(Date hanSuDung) {
        this.hanSuDung = hanSuDung;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    @Override
    public String toString() {
        return this.tenThucAn; 
    }
}
