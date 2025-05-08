/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author ADMIN
 */
import DAO.SuDungMayDAO;
import DTO.SuDungMayDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ChinhSuaSDM extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JButton btnSua, btnXoa, btnTimKiem;
    private SuDungMayDAO suDungMayDAO = new SuDungMayDAO(); // DAO

    public ChinhSuaSDM() {
        setLayout(new BorderLayout());

        // ==== Cột bảng ====
        String[] columnNames = {
            "Mã SD", "Mã KH", "Mã Máy", 
            "Thời gian bắt đầu", "Thời gian kết thúc", 
            "Tổng TG (giờ)", "Chi phí (VNĐ)"
        };

        // ==== Khởi tạo model ====
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ==== Load dữ liệu từ DAO ====
        loadData();

        // ==== Các nút chức năng ====
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTimKiem = new JButton("Tìm kiếm");

        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnTimKiem);

        add(panelButtons, BorderLayout.SOUTH);

        // ==== Gắn sự kiện ====
        btnSua.addActionListener(e -> suaDong());
        btnXoa.addActionListener(e -> xoaDong());
        btnTimKiem.addActionListener(e -> timKiem());
    }

    private void loadData() {
        model.setRowCount(0); // Xóa dòng cũ
        ArrayList<SuDungMayDTO> list = suDungMayDAO.selectAll();
        for (SuDungMayDTO s : list) {
            model.addRow(new Object[] {
                s.getMaSuDung(),
                s.getMaKhachHang(),
                s.getMaMay(),
                s.getThoiGianBatDau(),
                s.getThoiGianKetThuc(),
                s.getTongThoiGian(),
                String.format("%,.2f", s.getChiPhi())
            });
        }
    }

    private void suaDong() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int maSD = (int) model.getValueAt(row, 0);
            JOptionPane.showMessageDialog(this, "Sửa dòng có mã SD: " + maSD);
            // Gọi form sửa ở đây nếu muốn
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa.");
        }
    }

    private void xoaDong() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa?", "Xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                model.removeRow(row);
                // Gọi DAO để xóa trong database nếu cần
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa.");
        }
    }

    private void timKiem() {
        String keyword = JOptionPane.showInputDialog(this, "Nhập mã KH cần tìm:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            for (int i = 0; i < model.getRowCount(); i++) {
                String maKH = model.getValueAt(i, 1).toString();
                if (maKH.equalsIgnoreCase(keyword)) {
                    table.setRowSelectionInterval(i, i);
                    table.scrollRectToVisible(table.getCellRect(i, 0, true));
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã KH: " + keyword);
        }
    }
}

