/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.KhoMayTinhDAO;
import DTO.KhoMayTinhDTO;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;

/**
 *
 * @author Minnie
 */
public class KhoMayTinhBUS {
    private KhoMayTinhDAO khoMayTinhDAO = new KhoMayTinhDAO();
    private ArrayList<KhoMayTinhDTO> khoMayTinhList = new KhoMayTinhDAO().sellectAll();

    public KhoMayTinhBUS() {
        khoMayTinhList = khoMayTinhDAO.sellectAll();
    }

    public ArrayList<KhoMayTinhDTO> getKhoMayTinhList() {
        return khoMayTinhList;
    }

    public ArrayList<KhoMayTinhDTO> getKhoMayTinhByMaNCC(int maNCC) {
        ArrayList<KhoMayTinhDTO> result = new ArrayList<>();
        for (KhoMayTinhDTO kho : khoMayTinhList) {
            if (kho.getMaNCC() == maNCC) {
                result.add(kho);
            }
        }
        return result;
    }

    public String getTenMayByMa(int ma) {
        NhaCungCapDTO ncc = new NhaCungCapBUS().getNhaCungCapById(ma);
        if (ncc != null) {
            return ncc.getTenNhaCungCap();
        }
        return null;
    }
}
