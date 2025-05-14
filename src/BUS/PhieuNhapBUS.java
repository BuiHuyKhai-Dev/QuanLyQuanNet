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

}
