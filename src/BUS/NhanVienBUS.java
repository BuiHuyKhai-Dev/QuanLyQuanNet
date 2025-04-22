package BUS;

import DTO.NhanVienDTO;
import DAO.NhanVienDAO;
import java.util.List;

public class NhanVienBUS {
    private static NhanVienDAO dao = new NhanVienDAO();

    // Lấy danh sách nhân viên
    public static List<NhanVienDTO> getDanhSachNhanVien() {
        return dao.getDanhSachNhanVien();
    }
    
    public static NhanVienDTO timTheoMaNV(String maNV) {
        return dao.timTheoMaNV(maNV);
    }
    // Thêm nhân viên với validation
    public static boolean themNhanVien(NhanVienDTO nv) {
        if (!validateCCCD(nv.getCccd())) return false;
        if (!validateSDT(nv.getSoDT())) return false;
        if (!validateUsername(nv.getUsername())) return false;
        if (!validateRole(nv.getRole())) return false;
        if (!validateChucVu(nv.getChucVu())) return false;

        
        return dao.themNhanVien(nv);
    }

    // Sửa nhân viên với validation
    public static boolean suaNhanVien(NhanVienDTO nv) {
        if (!validateCCCD(nv.getCccd())) return false;
        if (!validateSDT(nv.getSoDT())) return false;
        if (!validateRole(nv.getRole())) return false;
        if (!validateChucVu(nv.getChucVu())) return false;

        
        return dao.suaNhanVien(nv);
    }

    // Xóa nhân viên
    public static boolean xoaNhanVien(String maNV) {
        return dao.xoaNhanVien(maNV);
    }

    // Các phương thức validate
    private static boolean validateCCCD(String cccd) {
        return cccd.matches("^0\\d{11}$");
    }

    private static boolean validateSDT(String sdt) {
        return sdt.matches("^\\d{10}$");
    }

    private static boolean validateUsername(String username) {
        return getDanhSachNhanVien().stream()
               .noneMatch(nv -> nv.getUsername().equalsIgnoreCase(username));
    }

    private static boolean validateRole(String role) {
        return role.equals("Xuất nhập kho") || role.equals("Phục vụ") || role.equals("Tiếp tân")|| role.equals("Kế toán")|| role.equals("All");
    }
        private static boolean validateChucVu(String chucvu) {
        return chucvu.equals("Quản lý") || chucvu.equals("Nhân viên") || chucvu.equals("Admin");
    }
}
