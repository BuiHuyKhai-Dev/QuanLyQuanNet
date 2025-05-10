package BUS;
import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class KhachHangBUS {
    public final KhachHangDAO khDAO = new KhachHangDAO();
    private ArrayList<KhachHangDTO> listkh = null;

    public KhachHangBUS() {
        listkh = khDAO.selectAll();
    }

    public ArrayList<KhachHangDTO> getKhachHangAll() {
        if (listkh == null)
            listkh = khDAO.selectAll();
        return listkh;
    }

    public Boolean add(KhachHangDTO a) {
        boolean result = khDAO.insert(a) != 0;
        if (result) {
            listkh.add(a);
        }
        return result;
    }

    public KhachHangDTO getKhachHangById(int ma) {
        for (KhachHangDTO kh : listkh) {
            if (kh.getMaKhachHang() == ma) {
                return kh;
            }
        }
        return null;
    }

    public Boolean updateKhachHang_DB(KhachHangDTO a) {
        return khDAO.update(a) != 0;
    }

    public Boolean deleteById(int ma) {
        KhachHangDTO toDelete = getKhachHangById(ma);
        boolean result = khDAO.delete(ma + "") != 0;
        if (result && toDelete != null) {
            listkh.remove(toDelete);
        }
        return result;
    }

    public ArrayList<KhachHangDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        for (KhachHangDTO kh : listkh) {
            if (Integer.toString(kh.getMaKhachHang()).toLowerCase().contains(text)) {
                result.add(kh);
            }
        }
        return result;
    }

    public ArrayList<KhachHangDTO> searchName(String text) {
        text = text.toLowerCase();
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        for (KhachHangDTO kh : listkh) {
            if (kh.getTenKhachHang().toLowerCase().contains(text)) {
                result.add(kh);
            }
        }
        return result;
    }

    public ArrayList<KhachHangDTO> searchEmail(String text) {
        text = text.toLowerCase();
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        for (KhachHangDTO kh : listkh) {
            if (kh.getEmail().toLowerCase().contains(text)) {
                result.add(kh);
            }
        }
        return result;
    }

    public ArrayList<KhachHangDTO> searchSDTKh(String text) {
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        for (KhachHangDTO kh : listkh) {
            if (kh.getSoDienThoai().contains(text)) {
                result.add(kh);
            }
        }
        return result;
    }

    public KhachHangDTO getKHBySDT(String sdt) {
        for (KhachHangDTO kh : listkh) {
            if (kh.getSoDienThoai().equals(sdt)) {
                return kh;
            }
        }
        return null;
    }

    public ArrayList<String> getAllSDT() {
        ArrayList<String> result = new ArrayList<>();
        for (KhachHangDTO kh : listkh) {
            result.add(kh.getSoDienThoai());
        }
        return result;
    }

    public static boolean checkemail(String email) {
        return email.contains("@gmail.com");
    }

    public static boolean checksdt(String sdt) {
        return sdt.length() == 10 && sdt.charAt(0) == '0';
    }

    public static Date checkngaysinh(String ngaysinh) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        try {
            return (Date) formatter.parse(ngaysinh);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
