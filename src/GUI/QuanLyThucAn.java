package GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DTO.ThucAnDTO;
import DAL.DBConnect;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.math.BigDecimal;
import javax.swing.SwingUtilities;

public class QuanLyThucAn extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTimKiem;

    public QuanLyThucAn() {
        setLayout(new BorderLayout());



        // Bảng dữ liệu
        tableModel = new DefaultTableModel(new String[]{"Mã DV", "Tên DV", "Đơn vị", "Đơn giá", "Hạn sử dụng"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Panel tìm kiếm + nút
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        txtTimKiem = new JTextField(20);
        JButton btnTim = createIconButton("Tìm", "src/icon/search.png", 18, 18, "Tìm dịch vụ theo tên");
        btnTim.setPreferredSize(new Dimension(90, 30)); // hoặc 100 tùy bạn muốn dài bao nhiêu
        btnTim.setToolTipText("Tìm dịch vụ theo tên");
        searchPanel.add(new JLabel("Tìm tên:"));
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnTim);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        JButton btnThem = createIconButton("Thêm", "src/icon/add.png", 18, 18, "Thêm dịch vụ mới");
        JButton btnSua = createIconButton("Sửa", "src/icon/edit.png", 18, 18, "Sửa thông tin dịch vụ");
        JButton btnXoa = createIconButton("Xóa", "src/icon/delete.png", 18, 18, "Xóa dịch vụ");
        JButton btnLamMoi = createIconButton("Làm mới", "src/icon/refresh.png", 18, 18, "Làm mới bảng");



        Dimension buttonSize = new Dimension(110, 30);
        btnThem.setPreferredSize(buttonSize);
        btnSua.setPreferredSize(buttonSize);
        btnXoa.setPreferredSize(buttonSize);
        btnLamMoi.setPreferredSize(buttonSize);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLamMoi);

        bottomPanel.add(searchPanel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        // Gọi dữ liệu mẫu (test)
        loadData();

        // Sự kiện nút
        btnThem.addActionListener(e -> {
            ThemThucAnDialog dialog = new ThemThucAnDialog((JFrame) SwingUtilities.getWindowAncestor(this));
            dialog.setVisible(true);
            loadData();
        });
        btnLamMoi.addActionListener(e -> loadData());
        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dịch vụ để sửa.");
                return;
            }

            int ma = (int) table.getValueAt(row, 0);
            String ten = (String) table.getValueAt(row, 1);
            String donVi = (String) table.getValueAt(row, 2);
            double donGia = (double) table.getValueAt(row, 3);
            String hanSuDung = table.getValueAt(row, 4).toString();

            SuaThucAnDialog dialog = new SuaThucAnDialog((JFrame) SwingUtilities.getWindowAncestor(this), ma, ten, donVi, donGia, hanSuDung);
            dialog.setVisible(true);

            loadData();
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dịch vụ để xóa.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa dịch vụ này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            int maThucAn = (int) table.getValueAt(row, 0);

            // Gọi DAO để xóa
            DAO.ThucAnDAO thucAnDAO = new DAO.ThucAnDAO();
            boolean deleted = thucAnDAO.delete(maThucAn);

            if (deleted) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        });

        btnTim.addActionListener(e -> {
            String keyword = txtTimKiem.getText().trim();

            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm.");
                return;
            }

            search(keyword);
        });
    }
    private JButton createIconButton(String text, String iconPath, int width, int height, String tooltip) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(text, new ImageIcon(scaledImage));
        button.setToolTipText(tooltip);
        return button;
    }
    private void search(String keyword) {
        keyword = keyword.toLowerCase();
        tableModel.setRowCount(0);

        boolean found = false;
        for (ThucAnDTO thucAn : thucAnList) {
            if (thucAn.getTenThucAn().toLowerCase().contains(keyword)) {
                tableModel.addRow(new Object[]{
                        thucAn.getMaThucAn(),
                        thucAn.getTenThucAn(),
                        thucAn.getDonVi(),
                        thucAn.getDonGia(),
                        thucAn.getHanSuDung()
                });
                found = true;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dịch vụ nào!");
        }
    }

    private ArrayList<ThucAnDTO> thucAnList = new ArrayList<>();

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            thucAnList.clear();

            Connection conn = DBConnect.getConnection();
            String sql = "SELECT MaThucAn, TenThucAn, DonVi, DonGia, HanSuDung FROM ThucAn";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int ma = rs.getInt("MaThucAn");
                String ten = rs.getString("TenThucAn");
                String donVi = rs.getString("DonVi");
                double donGia = rs.getDouble("DonGia");
                java.sql.Date hanSuDung = rs.getDate("HanSuDung");

                ThucAnDTO thucAn = new ThucAnDTO();
                thucAn.setMaThucAn(ma);
                thucAn.setTenThucAn(ten);
                thucAn.setDonVi(donVi);
                thucAn.setDonGia(BigDecimal.valueOf(donGia));
                thucAn.setHanSuDung(hanSuDung);
                thucAnList.add(thucAn);

                tableModel.addRow(new Object[]{ma, ten, donVi, donGia, hanSuDung});
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi load dữ liệu dịch vụ: " + ex.getMessage());
        }
    }
}
