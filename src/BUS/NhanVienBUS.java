package BUS;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import java.util.ArrayList;

public class NhanVienBUS {
    public final NhanVienDAO nvDAO = new NhanVienDAO();
    private ArrayList<NhanVienDTO> listnv = nvDAO.selectAll();

    public NhanVienBUS() {
        listnv = nvDAO.selectAll();
    }

    public ArrayList<String> getAllTenNV() {
        ArrayList<String> listTenNV = new ArrayList<>();
        for (NhanVienDTO nv : listnv) {
            listTenNV.add(nv.getTenNV());
        }
        return listTenNV;
    }

    public String getMaNV(String tenNV) {
        for (NhanVienDTO nv : listnv) {
            if (nv.getTenNV().equals(tenNV)) {
                return nv.getMaNV();
            }
        }
        return null;
    }

    public ArrayList<NhanVienDTO> getNhanVienAll() {
        if (listnv == null)
            listnv = nvDAO.selectAll();
        return listnv;
    }

    public Boolean add(NhanVienDTO a) {
        boolean result = nvDAO.insert(a) != 0;
        if (result) {
            listnv.add(a);
        }
        return result;
    }

    public NhanVienDTO getNhanVienById(String ma) {
        for (NhanVienDTO nv : listnv) {
            if (nv.getMaNV().equals(ma)) {
                return nv;
            }
        }
        return null;
    }

    public Boolean updateNhanVien_DB(NhanVienDTO a) {
        return nvDAO.update(a) != 0;
    }

    public Boolean deleteById(String ma) {
        NhanVienDTO toDelete = getNhanVienById(ma);
        if (toDelete != null) {
            toDelete.setTrangThai(0); // cập nhật trạng thái về 0
            boolean result = nvDAO.update(toDelete) != 0;
            return result;
        }
        return false;
    }

    public String getEmailByID(String ma) {
        for (NhanVienDTO nv : listnv) {
            if (nv.getMaNV().equals(ma)) {
                return nv.getEmail();
            }
        }
        return null;
    }

    public ArrayList<NhanVienDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<NhanVienDTO> result = new java.util.ArrayList<>();
        for (NhanVienDTO nv : listnv) {
            if (nv.getMaNV().toLowerCase().contains(text)) {
                result.add(nv);
            }
        }
        return result;
    }

    public ArrayList<NhanVienDTO> getListnv() {
        return listnv;
    }

    public void setListnv(ArrayList<NhanVienDTO> listnv) {
        this.listnv = listnv;
    }

    public int getLastID() {
        if (listnv == null || listnv.isEmpty()) {
            return 0;
        }
        int maxId = Integer.parseInt(listnv.get(0).getMaNV());
        for (NhanVienDTO nv : listnv) {
            int id = Integer.parseInt(nv.getMaNV());
            if (id > maxId) {
                maxId = id;
            }
        }
        return maxId + 1;
    }

    public ArrayList<NhanVienDTO> searchName(String text) {
        text = text.toLowerCase();
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        for (NhanVienDTO nv : listnv) {
            if (nv.getTenNV().toLowerCase().contains(text)) {
                result.add(nv);
            }
        }
        return result;
    }

    public ArrayList<NhanVienDTO> searchSDT(String text) {
        text = text.toLowerCase();
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        for (NhanVienDTO nv : listnv) {
            if (nv.getSoDT().toLowerCase().contains(text)) {
                result.add(nv);
            }
        }
        return result;
    }

    public ArrayList<NhanVienDTO> sortName() {
        ArrayList<NhanVienDTO> sortedList = new ArrayList<>(listnv);
        sortedList.sort((o1, o2) -> o1.getTenNV().compareToIgnoreCase(o2.getTenNV()));
        return sortedList;
    }

    public ArrayList<NhanVienDTO> sortSalary() {
       ArrayList<NhanVienDTO> sortedList = new ArrayList<>(listnv);
        sortedList.sort((o1, o2) -> {
            double salary1 = o1.getLuong();
            double salary2 = o2.getLuong();
            return Double.compare(salary1, salary2);
        });
        return sortedList;
    }

    public ArrayList<NhanVienDTO> sortByID() {
        ArrayList<NhanVienDTO> sortedList = new ArrayList<>(listnv);
        sortedList.sort((o1, o2) -> {
            int id1 = Integer.parseInt(o1.getMaNV());
            int id2 = Integer.parseInt(o2.getMaNV());
            return Integer.compare(id1, id2);
        });
        return sortedList;
    }

    public ArrayList<NhanVienDTO> filterByDate(String fromDate, String toDate) {
        ArrayList<NhanVienDTO> filteredList = new ArrayList<>();
        for (NhanVienDTO nv : nvDAO.selectAll()) {
            String ngayTao = nv.getThoiGianTao();
            if (ngayTao.compareTo(fromDate) >= 0 && ngayTao.compareTo(toDate) <= 0) {
                filteredList.add(nv);
            }
        }
        return filteredList;
    }

    public String getTenNV(int ma){
        for (NhanVienDTO nv : listnv) {
            if (nv.getMaNV().equals(String.valueOf(ma))) {
                return nv.getTenNV();
            }
        }
        return null;
    }
}
