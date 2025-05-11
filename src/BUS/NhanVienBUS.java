package BUS;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import java.util.List;

public class NhanVienBUS {
    public final NhanVienDAO nvDAO = new NhanVienDAO();
    private List<NhanVienDTO> listnv = null;

    public NhanVienBUS() {
        listnv = nvDAO.selectAll();
    }

    public List<NhanVienDTO> getNhanVienAll() {
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
        boolean result = nvDAO.delete(ma) != 0;
        if (result && toDelete != null) {
            listnv.remove(toDelete);
        }
        return result;
    }

    public List<NhanVienDTO> search(String text) {
        text = text.toLowerCase();
        List<NhanVienDTO> result = new java.util.ArrayList<>();
        for (NhanVienDTO nv : listnv) {
            if (nv.getMaNV().toLowerCase().contains(text)) {
                result.add(nv);
            }
        }
        return result;
    }

    public List<NhanVienDTO> getListnv() {
        return listnv;
    }

    public void setListnv(List<NhanVienDTO> listnv) {
        this.listnv = listnv;
    }

}
