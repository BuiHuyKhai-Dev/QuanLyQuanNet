/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import java.util.ArrayList;

/**
 *
 * @author Minnie
 */
public class TaiKhoanBUS {
//        private final ArrayList<TaiKhoanDTO> listTK;
        private final TaiKhoanDAO tkDAO;
    
        public TaiKhoanBUS(){
            this.tkDAO = TaiKhoanDAO.getInstance();
//            this.tkDAO= tkDAO.selectAll();
        }

        public ArrayList<TaiKhoanDTO> getAll() {
            return tkDAO.selectAll();
        }
    
        public TaiKhoanDTO dangNhap(String username, String matkhau){
            return tkDAO.kiemTraDangNhap(username, matkhau);
        }
}
