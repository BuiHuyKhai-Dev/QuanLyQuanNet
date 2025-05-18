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

        public boolean add(TaiKhoanDTO a) {
            boolean result = tkDAO.insert(a) != 0;
            return result;
        }

        public boolean update(TaiKhoanDTO a) {
            return tkDAO.update(a) != 0;
        }

        public boolean delete(String a) {
            boolean result = tkDAO.delete(a) != 0;
            return result;
        }

        public ArrayList<TaiKhoanDTO> sortByUsername() {
            ArrayList<TaiKhoanDTO> list = tkDAO.selectAll();
            list.sort((TaiKhoanDTO o1, TaiKhoanDTO o2) -> o1.getTenDangNhap().compareTo(o2.getTenDangNhap()));
            return list;
        }

        public ArrayList<TaiKhoanDTO> sortByStatus() {
            ArrayList<TaiKhoanDTO> list = tkDAO.selectAll();
            list.sort((TaiKhoanDTO o1, TaiKhoanDTO o2) -> Integer.compare(o1.getTrangThai(), o2.getTrangThai()));
            return list;
        }

        public ArrayList<TaiKhoanDTO> searchByUsername(String username) {
            ArrayList<TaiKhoanDTO> list = tkDAO.selectAll();
            ArrayList<TaiKhoanDTO> result = new ArrayList<>();
            for (TaiKhoanDTO tk : list) {
                if (tk.getTenDangNhap().toLowerCase().contains(username.toLowerCase())) {
                    result.add(tk);
                }
            }
            return result;
        }
        
}
