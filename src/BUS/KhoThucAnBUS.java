/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.KhoThucAnDAO;
import DTO.KhoThucAnDTO;
import java.util.ArrayList;

/**
 *
 * @author Minnie
 */
public class KhoThucAnBUS {
    private KhoThucAnDAO kmtDAO = new KhoThucAnDAO();
    private ArrayList<KhoThucAnDTO> list = new KhoThucAnDAO().getAllKhoThucAn();
    private ThucAnBUS thucAnBUS = new ThucAnBUS();
    private NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    
    public String getTenThucAn(int maThucAn) {
        for (KhoThucAnDTO kta : list) {
            if (kta.getMaThucAn() == maThucAn) {
                return thucAnBUS.getTenThucAn(kta.getMaThucAn());
            }
        }
        return null;
    }

    public String getTenNCC(int maNCC) {
        for (KhoThucAnDTO kta : list) {
            if (kta.getmaNCC() == maNCC) {
                return nccBUS.getTenNCC(kta.getmaNCC());
            }
        }
        return null;
    }

    public double getDonGia(int maThucAn) {
        for (KhoThucAnDTO kta : list) {
            if (kta.getMaThucAn() == maThucAn) {
                return thucAnBUS.getDonGia(kta.getMaThucAn());
            }
        }
        return 0;
    }
}
