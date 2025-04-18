/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import java.util.ArrayList;

/**
 *
 * @author Minnie
 */
public class NhanVienBUS {
        private final ArrayList<NhanVienDTO> listNV;
        private final NhanVienDAO nvDAO;
    
        public NhanVienBUS(){
            this.nvDAO = NhanVienDAO.getInstance();
            this.listNV = nvDAO.selectAll();
        }
    
        public NhanVienDTO dangNhap(String username, String matkhau){
            return nvDAO.kiemTraDangNhap(username, matkhau);
        }
}
