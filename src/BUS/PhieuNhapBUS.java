/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import java.util.ArrayList;


/**
 *
 * @author Minnie
 */
public class PhieuNhapBUS {
    private PhieuNhapDAO phieuNhapDAO = PhieuNhapDAO.getInstance();
    private ArrayList<PhieuNhapDTO> listPhieuNhap = PhieuNhapDAO.getInstance().selectAll();

    public ArrayList<PhieuNhapDTO> getAll() {
        return listPhieuNhap;
    }

    public int getLastID() {
        ArrayList<PhieuNhapDTO> list = phieuNhapDAO.selectAll();
        if (list == null || list.isEmpty()) {
            return 0;
        }
        return list.get(list.size() - 1).getMaPN();
    }
}
