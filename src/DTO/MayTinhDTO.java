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

/*
TrangThai:
0 = Trống
1 = Đang sử dụng
2 = Bảo trì
*/
public class MayTinhDTO {
    private int maMay;
    private String tenMay;
    private int trangThai;
    private String viTri;
    private int giaThue;
    private Timestamp thoiGianTao;

    public MayTinhDTO() {
    }

    public MayTinhDTO(int maMay, String tenMay, int trangThai, String viTri, int giaThue) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.trangThai = trangThai;
        this.viTri = viTri;
        this.giaThue= giaThue;
    }

    public int getMaMay() {
        return maMay;
    }

    public void setMaMay(int maMay) {
        this.maMay = maMay;
    }

    public String getTenMay() {
        return tenMay;
    }

    public void setTenMay(String tenMay) {
        this.tenMay = tenMay;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public Timestamp getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Timestamp thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }
    
     public int getGiaThue() { return giaThue; }

    @Override
    public String toString() {
        return "MayTinhDTO{" + "maMay=" + maMay + ", tenMay=" + tenMay + ", trangThai=" + trangThai + ", viTri=" + viTri + ", thoiGianTao=" + thoiGianTao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.maMay;
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
        final MayTinhDTO other = (MayTinhDTO) obj;
        return this.maMay == other.maMay;
    }
    
    
}
