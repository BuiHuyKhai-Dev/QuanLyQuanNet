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

    public Boolean deleteById(String ma) {
        NhaCungCapDTO toDelete = getNhaCungCapById(ma);
        boolean result = nccDAO.delete(Integer.parseInt(ma)) != 0;
        if (result && toDelete != null) {
            listncc.remove(toDelete);
        }
        return result;
    }
}
