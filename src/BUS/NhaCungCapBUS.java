/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;

/**
 *
 * @author Minnie
 */
public class NhaCungCapBUS {
    public final NhaCungCapDAO nccDAO = new NhaCungCapDAO();
    private ArrayList<NhaCungCapDTO> listncc = null;

    public NhaCungCapBUS() {
        listncc = nccDAO.selectAll();
    }

    public ArrayList<NhaCungCapDTO> getNhaCungCapAll() {
        if (listncc == null)
            listncc = nccDAO.selectAll();
        return listncc;
    }

    public Boolean add(NhaCungCapDTO a) {
        boolean result = nccDAO.insert(a) != 0;
        if (result) {
            listncc.add(a);
        }
        return result;
    }

    public NhaCungCapDTO getNhaCungCapById(String ma) {
        for (NhaCungCapDTO ncc : listncc) {
            if (ncc.getMaNhaCungCap() == Integer.parseInt(ma)) {
                return ncc;
            }
        }
        return null;
    }

    public Boolean updateNhaCungCap_DB(NhaCungCapDTO a) {
        return nccDAO.update(a) != 0;
    }

    public Boolean deleteById(int ma) {
        NhaCungCapDTO toDelete = getNhaCungCapById(String.valueOf(ma));
        boolean result = nccDAO.delete(ma) != 0;
        if (result && toDelete != null) {
            listncc.remove(toDelete);
        }
        return result;
    }

    public int getLastID() {
        if (listncc == null) {
            listncc = nccDAO.selectAll();
        }
        int maxId = 0;
        for (NhaCungCapDTO ncc : listncc) {
            if (ncc.getMaNhaCungCap() > maxId) {
                maxId = ncc.getMaNhaCungCap();
            }
        }
        return maxId +1 ;
    }

    public ArrayList<NhaCungCapDTO> searchName(String name) {
        String text = name.toLowerCase();
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        for (NhaCungCapDTO ncc : listncc) {
            if (ncc.getTenNhaCungCap().toLowerCase().contains(text)) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<NhaCungCapDTO> searchPhone(String phone) {
        String text = phone.toLowerCase();
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        for (NhaCungCapDTO ncc : listncc) {
            if (ncc.getSoDienThoai().toLowerCase().contains(text)) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<NhaCungCapDTO> sortName() {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>(listncc);
        result.sort((NhaCungCapDTO o1, NhaCungCapDTO o2) -> o1.getTenNhaCungCap().compareTo(o2.getTenNhaCungCap()));
        return result;
    }

    public ArrayList<NhaCungCapDTO> sortAdress() {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>(listncc);
        result.sort((NhaCungCapDTO o1, NhaCungCapDTO o2) -> o1.getDiaChi().compareTo(o2.getDiaChi()));
        return result;
    }
    
    public ArrayList<NhaCungCapDTO> filterByDate(String fromDate, String toDate) {
        ArrayList<NhaCungCapDTO> filteredList = new ArrayList<>();
        for (NhaCungCapDTO nv : nccDAO.selectAll()) {
            String ngayTao = nv.getThoiGianTao();
            if (ngayTao.compareTo(fromDate) >= 0 && ngayTao.compareTo(toDate) <= 0) {
                filteredList.add(nv);
            }
        }
        return filteredList;
    }
}
