package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ThongKePanel extends JPanel {
    private DefaultTableModel tableModel;

    public ThongKePanel() {
        setLayout(new BorderLayout());

        // Tiêu đề
        JLabel lblTitle = new JLabel("Thống kê", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitle, BorderLayout.NORTH);

        // Bảng thống kê
        String[] columnNames = {"Loại thống kê", "Số lượng", "Tổng tiền"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Thêm dữ liệu mẫu
        addSampleData();
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"Khách hàng", 120, "0 VND"});
        tableModel.addRow(new Object[]{"Nhân viên", 15, "0 VND"});
        tableModel.addRow(new Object[]{"Nhà cung cấp", 8, "0 VND"});
        tableModel.addRow(new Object[]{"Doanh thu tháng", "-", "50,000,000 VND"});
    }
}