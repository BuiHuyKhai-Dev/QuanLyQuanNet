/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import DAL.DatMayDAL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import DTO.SuDungMayDTO;
import DTO.MayTinhDTO;
import DAO.MayTinhDAO;
import DAO.SuDungMayDAO;

public class SuDungMayGUI extends JPanel {
    private JPanel panelMay;
    private JTable tableLichSu;
    private JScrollPane scrollPane;
    private int maMayDaChon = -1;

    public SuDungMayGUI() {
        setLayout(new BorderLayout());

        // Panel chứa các máy
        panelMay = new JPanel();
        panelMay.setLayout(new GridLayout(0, 4, 10, 10)); // 4 cột
        panelMay.setBackground(Color.WHITE);
        add(panelMay, BorderLayout.NORTH);

        // Bảng lịch sử đặt máy
        tableLichSu = new JTable();
        scrollPane = new JScrollPane(tableLichSu);
        scrollPane.setVisible(false);
        add(scrollPane, BorderLayout.CENTER); // Chuyển vị trí vào giữa

        // Load danh sách máy
        hienThiDanhSachMay();
    }

    private void hienThiDanhSachMay() {
        DatMayDAL mayDAO = new DatMayDAL();
        ArrayList<MayTinhDTO> danhSachMay = mayDAO.selectAll();

        for (MayTinhDTO may : danhSachMay) {
            JPanel mayPanel = new JPanel();
            mayPanel.setLayout(new BorderLayout());
            mayPanel.setPreferredSize(new Dimension(120, 100));
            mayPanel.setBackground(Color.WHITE);
            mayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel lblTenMay = new JLabel(may.getTenMay(), SwingConstants.CENTER);
            lblTenMay.setFont(new Font("Arial", Font.BOLD, 14));

            JButton btnXemLichSu = new JButton("Xem lịch sử");
            btnXemLichSu.setFont(new Font("Arial", Font.PLAIN, 12));
            btnXemLichSu.addActionListener(e -> {
                maMayDaChon = may.getMaMay();
                hienThiLichSuDatMay();
            });

            mayPanel.add(lblTenMay, BorderLayout.CENTER);
            mayPanel.add(btnXemLichSu, BorderLayout.SOUTH);

            panelMay.add(mayPanel);
        }

        revalidate();
        repaint();
    }

    private void hienThiLichSuDatMay() {
    if (maMayDaChon == -1) return;

    SuDungMayDAO suDungDAO = new SuDungMayDAO();
    ArrayList<SuDungMayDTO> lichSu = suDungDAO.selectAll();

    // Lọc theo máy được chọn
    ArrayList<SuDungMayDTO> lichSuMay = new ArrayList<>();
    for (SuDungMayDTO s : lichSu) {
        if (s.getMaMay() == maMayDaChon) {
            lichSuMay.add(s);
        }
    }

    // Tạo dữ liệu bảng
    String[] columnNames = {"Mã SD", "Mã KH", "Thời gian bắt đầu", "Thời gian kết thúc", "Tổng TG", "Chi phí", "Mã PM"};
    String[][] data = new String[lichSuMay.size()][7];

    for (int i = 0; i < lichSuMay.size(); i++) {
        SuDungMayDTO s = lichSuMay.get(i);
        data[i][0] = String.valueOf(s.getMaSuDung());
        data[i][1] = String.valueOf(s.getMaKhachHang());
        data[i][2] = s.getThoiGianBatDau().toString();
         data[i][3] = (s.getThoiGianKetThuc() == null) ? "Đang sử dụng" : s.getThoiGianKetThuc().toString();
        data[i][4] = String.valueOf(s.getTongThoiGian());
        data[i][5] = String.valueOf(s.getChiPhi());
    }

    JTable table = new JTable(data, columnNames);
    JScrollPane scroll = new JScrollPane(table);

    // Tạo dialog
    JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Lịch sử sử dụng máy " + maMayDaChon, true);
    dialog.setSize(800, 400);
    dialog.setLocationRelativeTo(this);
    dialog.add(scroll);
    dialog.setVisible(true);
}

}
