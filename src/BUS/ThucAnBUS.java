/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.ThucAnDAO;
import DTO.ThucAnDTO;
import java.util.ArrayList;

/**
 *
 * @author Minnie
 */
public class ThucAnBUS {
    private ThucAnDAO taDAO = new ThucAnDAO();
    private ArrayList<ThucAnDTO> list = new ArrayList<>();
    public ThucAnBUS() {
        list = taDAO.getAll();
    }
    
    public ArrayList<ThucAnDTO> getAllThucAn() {
        if (list == null) {
            list = taDAO.getAll();
        }
        return list;
    }

    public String getTenThucAn(int maThucAn) {
        for (ThucAnDTO ta : list) {
            if (ta.getMaThucAn() == maThucAn) {
                return ta.getTenThucAn();
            }
        }
        return null;
    }

    public int getSoLuong(int maThucAn) {
        for (ThucAnDTO ta : list) {
            if (ta.getMaThucAn() == maThucAn) {
                return ta.getSoLuong();
            }
        }
        return 0;
    }

    public double getDonGia(int maThucAn) {
        for (ThucAnDTO ta : list) {
            if (ta.getMaThucAn() == maThucAn) {
                return ta.getDonGia();
            }
        }
        return 0;
    }

    public int getMaTheoTenMon(String tenThucAn) {
        for (ThucAnDTO ta : list) {
            if (ta.getTenThucAn().equals(tenThucAn)) {
                return ta.getMaThucAn();
            }
        }
        return 0;
    }
}
