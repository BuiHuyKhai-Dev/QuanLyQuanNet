package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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

        // Đặt chiều cao dòng và header
        table.setRowHeight(30); // Chiều cao các hàng
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25)); // Chiều cao header

        // Căn giữa chữ trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Tô màu nền xen kẽ cho các dòng
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE); // Màu nền xen kẽ
                }
                setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa chữ
                return c;
            }
        });

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