package GUI;

import java.awt.*;
import javax.swing.*;

public class QuanLyMayPanel extends JPanel {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel;

    // Lưu trạng thái của từng máy (1: Đang trống, 2: Đang sử dụng, 3: Đang bảo trì)
    private int[] machineStatuses = {1, 1, 1, 1, 2, 3}; // Dữ liệu giả cho 6 máy

    public QuanLyMayPanel() {
        setLayout(new BorderLayout());

        // Thanh điều hướng
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnMay = new JButton("Máy");
        JButton btnLichSuMay = new JButton("Lịch sử máy");

        navBar.add(btnMay);
        navBar.add(btnLichSuMay);
        add(navBar, BorderLayout.NORTH);

        // Main panel với CardLayout
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(createMayPanel(), "Máy");
        mainPanel.add(createLichSuMayPanel(), "Lịch sử máy");
        add(mainPanel, BorderLayout.CENTER);

        // Xử lý sự kiện chuyển đổi giữa các màn hình
        btnMay.addActionListener(e -> cardLayout.show(mainPanel, "Máy"));
        btnLichSuMay.addActionListener(e -> cardLayout.show(mainPanel, "Lịch sử máy"));
    }

    // Tạo màn hình Máy
    private JPanel createMayPanel() {
        JPanel mayPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // Lưới 2x3 với khoảng cách 10px
        mayPanel.setBackground(Color.WHITE);

        for (int i = 0; i < machineStatuses.length; i++) {
            final int machineId = i; // Chỉ số mảng bắt đầu từ 0

            JPanel machinePanel = new JPanel(new BorderLayout());
            machinePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel lblMachine = new JLabel("Máy " + (machineId + 1), SwingConstants.CENTER);
            lblMachine.setFont(new Font("Arial", Font.BOLD, 16));

            // Đặt màu nền theo trạng thái
            updateMachinePanelColor(machinePanel, machineStatuses[machineId]);

            JButton btnEdit = new JButton("Chỉnh sửa");
            btnEdit.addActionListener(e -> showMachineDetailsDialog(machineId, machinePanel));

            machinePanel.add(lblMachine, BorderLayout.CENTER);
            machinePanel.add(btnEdit, BorderLayout.SOUTH);

            mayPanel.add(machinePanel);
        }

        return mayPanel;
    }

    // Hiển thị bảng chi tiết máy
    private void showMachineDetailsDialog(int machineId, JPanel machinePanel) {
    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết Máy " + (machineId + 1), true);
    dialog.setSize(400, 300);
    dialog.setLayout(new GridBagLayout()); // Sử dụng GridBagLayout để căn chỉnh linh hoạt

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
    gbc.gridx = 0;
    gbc.gridy = 0;

    // Thời gian thực
    dialog.add(new JLabel("Thời gian thực:"), gbc);
    gbc.gridx = 1;
    dialog.add(new JLabel("10:30:00"), gbc); // Ví dụ thời gian thực

    // Thời gian đã sử dụng
    gbc.gridx = 0;
    gbc.gridy++;
    dialog.add(new JLabel("Thời gian đã sử dụng:"), gbc);
    gbc.gridx = 1;
    dialog.add(new JLabel("2 giờ 15 phút"), gbc); // Ví dụ thời gian đã sử dụng

    // Trạng thái
    gbc.gridx = 0;
    gbc.gridy++;
    dialog.add(new JLabel("Trạng thái:"), gbc);
    gbc.gridx = 1;
    JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Đang trống", "Đang sử dụng", "Đang bảo trì"});
    statusComboBox.setSelectedIndex(machineStatuses[machineId] - 1); // Hiển thị trạng thái hiện tại
    dialog.add(statusComboBox, gbc);

    // Nút đóng
    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 2; // Kéo dài nút đóng toàn bộ chiều rộng
    JButton btnClose = new JButton("Đóng");
    btnClose.addActionListener(e -> {
        // Cập nhật trạng thái khi nhấn nút đóng
        machineStatuses[machineId] = statusComboBox.getSelectedIndex() + 1;
        updateMachinePanelColor(machinePanel, machineStatuses[machineId]);
        dialog.dispose();
    });
    dialog.add(btnClose, gbc);

    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}

    // Cập nhật màu sắc của ô máy dựa trên trạng thái
    private void updateMachinePanelColor(JPanel machinePanel, int status) {
        if (status == 2) { // Đang sử dụng
            machinePanel.setBackground(Color.YELLOW);
        } else if (status == 1) { // Đang trống
            machinePanel.setBackground(Color.GREEN);
        } else if (status == 3) { // Đang bảo trì
            machinePanel.setBackground(Color.RED);
        }
    }

    // Tạo màn hình Lịch sử máy
    private JPanel createLichSuMayPanel() {
        JPanel lichSuMayPanel = new JPanel(new BorderLayout());

        // Tiêu đề
        JLabel lblTitle = new JLabel("Lịch sử máy", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lichSuMayPanel.add(lblTitle, BorderLayout.NORTH);

        // Bảng dữ liệu
        String[] columnNames = {"Mã sử dụng", "Mã nhân viên", "Mã Khách Hàng", "Thời gian bắt đầu", "Thời gian kết thúc", "Tổng thời gian", "Chi phí"};
        Object[][] data = {
            {"1", "NV01", "KH01", "08:00", "10:00", "2 giờ", "20,000 VND"},
            {"2", "NV02", "KH02", "09:00", "11:30", "2 giờ 30 phút", "25,000 VND"}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        lichSuMayPanel.add(scrollPane, BorderLayout.CENTER);

        return lichSuMayPanel;
    }
}