/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Minnie
 */
public class PhanQuyenDTO {
    private int idNhomQuyen;
    private String tenNhomQuyen;

    public PhanQuyenDTO() {
    }

    public PhanQuyenDTO(int idNhomQuyen, String tenNhomQuyen) {
        this.idNhomQuyen = idNhomQuyen;
        this.tenNhomQuyen = tenNhomQuyen;
    }

    public int getIdNhomQuyen() {
        return idNhomQuyen;
    }

    public void setIdNhomQuyen(int idNhomQuyen) {
        this.idNhomQuyen = idNhomQuyen;
    }

    public String getTenNhomQuyen() {
        return tenNhomQuyen;
    }

    public void setTenNhomQuyen(String tenNhomQuyen) {
        this.tenNhomQuyen = tenNhomQuyen;
    }
    
    
}
