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
    
    public boolean checkLogin(String sdt, String matkhau) {
        for (KhachHangDTO kh : listkh) {
            if (kh.getSoDienThoai().equals(sdt) && kh.getMatKhau().equals(matkhau)) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteKhachHang(int ma) {
        for (KhachHangDTO kh : listkh) {
            if (kh.getMaKhachHang() == ma) {
            kh.setTrangThai(0); // cập nhật trạng thái về 0
            boolean result = khDAO.update(kh) != 0;
            return result;
            }
        }
        return false;
    }

    public Boolean add(KhachHangDTO a) {
        boolean result = khDAO.insert(a) != 0;
        if (result) {
            listkh.add(a);
        }
        return result;
    }

    public boolean napTien(int ma, double soTien) {
        for (KhachHangDTO kh : listkh) {
            if (kh.getMaKhachHang() == ma) {
                kh.setSoDuTaiKhoan(soTien);
                return khDAO.update(kh) != 0;
            }
        }
        return false;
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

    public int getLastID() {
        int max = 0;
        for (KhachHangDTO kh : listkh) {
            if (kh.getMaKhachHang() > max) {
                max = kh.getMaKhachHang();
            }
        }
        return max + 1;
    }

    public ArrayList<KhachHangDTO> sortByName() {
        listkh.sort((kh1, kh2) -> {
            String name1 = kh1.getTenKhachHang();
            String name2 = kh2.getTenKhachHang();
            return name1.compareTo(name2);
        });
        return listkh;
    }

    public ArrayList<KhachHangDTO> sortByBalance() {
        listkh.sort((kh1, kh2) -> {
            double balance1 = kh1.getSoDuTaiKhoan();
            double balance2 = kh2.getSoDuTaiKhoan();
            return Double.compare(balance1, balance2);
        });
        return listkh;
    }

    public ArrayList<KhachHangDTO> sortByID() {
        listkh.sort((kh1, kh2) -> {
            int id1 = kh1.getMaKhachHang();
            int id2 = kh2.getMaKhachHang();
            return Integer.compare(id1, id2);
        });
        return listkh;
    }
    
    public ArrayList<KhachHangDTO> filterByDate(String fromDate, String toDate) {
        ArrayList<KhachHangDTO> filteredList = new ArrayList<>();
        for (KhachHangDTO kh : khDAO.selectAll()) {
            String ngayTao = kh.getThoiGianTao();
            if (ngayTao.compareTo(fromDate) >= 0 && ngayTao.compareTo(toDate) <= 0) {
                filteredList.add(kh);
            }
        }
        return filteredList;
    }
}
