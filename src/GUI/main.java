/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.NhanVienBUS;
import BUS.TaiKhoanBUS;
import DTO.NhanVienDTO;

/**
 *
 * @author Minnie
 */
public class main {
    public static void main(String[] args) {
        // Khởi tạo giao diện chính
        DangNhap dn = new DangNhap();
        dn.setVisible(true);
//       TaiKhoanBUS taiKhoanBus = new TaiKhoanBUS();
//        KhachHangBUS khachHangBus = new KhachHangBUS();
//        for(KhachHangDTO khachHang : khachHangBus.getKhachHangAll()){
//            taiKhoanBus.insertKhach(khachHang.getEmail(), khachHang.getMatKhau());
//        }
//        NhanVienBUS nhanVienBus = new NhanVienBUS();
//        for(NhanVienDTO nhanVien : nhanVienBus.getNhanVienAll()){
//            taiKhoanBus.insertNhanVien(nhanVien.getEmail(), "1");
//        }


        
        // // Giả sử bạn có một lớp TàiKhoảnBus để xử lý logic liên quan đến tài khoản
        // TaiKhoanBUS taiKhoanBus = new TaiKhoanBUS();
        // ArrayList<TaiKhoanDTO> danhSachTaiKhoan = taiKhoanBus.getAll();

        // // In ra danh sách tài khoản
        // for (TaiKhoanDTO taiKhoan : danhSachTaiKhoan) {
        //     System.out.println("Tài khoản: " + taiKhoan.getTenDangNhap() + ", Mật khẩu: " + taiKhoan.getMatKhau());
        //     System.out.println("Nhóm quyền: " + taiKhoan.getNhomQuyen() + ", Trạng thái: " + taiKhoan.getTrangThai());
        // }
    }
}
