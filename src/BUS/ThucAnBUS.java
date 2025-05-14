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
    public String getTenThucAn(int maThucAn) {
        ArrayList<ThucAnDTO> list = new ThucAnDAO().getAll();
        for (ThucAnDTO ta : list) {
            if (ta.getMaThucAn() == maThucAn) {
                return ta.getTenThucAn();
            }
        }
        return null;
    }
}
