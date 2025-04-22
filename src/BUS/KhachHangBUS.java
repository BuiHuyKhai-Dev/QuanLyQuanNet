package BUS;
import DTO.KhachHangDTO;
import DAO.KhachHangDAO;
import java.util.List;

public class KhachHangBUS {
    public static List<KhachHangDTO> getDanhSachKhachHang() {
        return KhachHangDAO.layDanhSachKhachHang();
    }

   public static boolean themKhachHang(KhachHangDTO kh) {
    return KhachHangDAO.themKhachHang(kh);
}

    public static boolean suaKhachHang(KhachHangDTO kh) {
        return KhachHangDAO.suaKhachHang(kh);
    } 
    
    public static boolean napTien(String maKH, double soTien) {
        return KhachHangDAO.napTien(maKH, soTien);
    }

    public static boolean xoaKhachHang(String maKH) {
        return KhachHangDAO.xoaMemKhachHang(maKH);
    }

    public static KhachHangDTO timTheoCCCD(String cccd) {
        return KhachHangDAO.timTheoCCCD(cccd);
    }
    public static KhachHangDTO timTheoMaKH(String maKH) {
        return KhachHangDAO.timTheoMaKH(maKH);
    }
}
