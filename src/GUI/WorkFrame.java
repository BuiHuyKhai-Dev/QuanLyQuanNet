package GUI;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class WorkFrame extends JFrame {
    public CardLayout cardLayout = new CardLayout();
    public JPanel PanelCard = new JPanel(cardLayout);
    private JButton btnTrangChu, btnQuanLyMay, btnDatDoAn, btnQuanLyKhachHang, btnQuanLyNhanVien, btnQuanLyNhaCungCap;
    private JButton nutDangHoatDong; // Nút đang được chọn

    public WorkFrame() {
        this.init();
        this.setVisible(true);
        this.nutDangHoatDong = btnTrangChu; // Ban đầu nút trang chủ được chọn
        this.btnTrangChu.setBackground(new Color(100, 149, 237)); // Màu xanh cho nút đang hoạt động
    }

    public void init() {
        setTitle("Quản lý quán net");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar panel
        JPanel sidebar = new JPanel(new GridBagLayout());
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setBackground(new Color(230, 240, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;

        // Add user info panel
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBackground(new Color(230, 240, 250));
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        JLabel lblUserName = new JLabel("Tên: Admin");
        lblUserName.setFont(new Font("Arial", Font.BOLD, 16));
        lblUserName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUserRole = new JLabel("Vai trò: Admin");
        lblUserRole.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUserRole.setAlignmentX(Component.CENTER_ALIGNMENT);

        userInfoPanel.add(lblUserName);
        userInfoPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Khoảng cách giữa tên và vai trò
        userInfoPanel.add(lblUserRole);
        userInfoPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Khoảng cách dưới cùng

        gbc.gridy = 0;
        sidebar.add(userInfoPanel, gbc);

        // Add buttons to sidebar
        btnTrangChu = createMenuButton("Trang chủ");
        btnQuanLyMay = createMenuButton("Quản lý máy");
        btnDatDoAn = createMenuButton("Đặt đồ ăn");
        btnQuanLyKhachHang = createMenuButton("Quản lý khách hàng");
        btnQuanLyNhanVien = createMenuButton("Quản lý nhân viên");
        btnQuanLyNhaCungCap = createMenuButton("Quản lý nhà cung cấp");

        gbc.gridy = 1;
        sidebar.add(btnTrangChu, gbc);

        gbc.gridy = 2;
        sidebar.add(btnQuanLyMay, gbc);

        gbc.gridy = 3;
        sidebar.add(btnDatDoAn, gbc);

        gbc.gridy = 4;
        sidebar.add(btnQuanLyKhachHang, gbc);

        gbc.gridy = 5;
        sidebar.add(btnQuanLyNhanVien, gbc);

        gbc.gridy = 6;
        sidebar.add(btnQuanLyNhaCungCap, gbc);

        // Add a "glue" component to push buttons to the top
        gbc.gridy = 7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        sidebar.add(new JPanel(), gbc);

        // Add panels to PanelCard
        PanelCard.add(new TrangChuPanel(), "Trang chủ");
        PanelCard.add(new QuanLyMayPanel(), "Quản lý máy");
        PanelCard.add(new DatDoAnPanel(), "Đặt đồ ăn");
        PanelCard.add(new QuanLyKhachHangPanel(), "Quản lý khách hàng");
        PanelCard.add(new QuanLyNhanVienPanel(), "Quản lý nhân viên");
        PanelCard.add(new QuanLyNhaCungCapPanel(), "Quản lý nhà cung cấp");

        // Add sidebar and PanelCard to main panel
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(PanelCard, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);

        // Add action listeners
        ActionListener action = e -> {
            JButton source = (JButton) e.getSource();

            // Đổi màu nút được chọn
            if (nutDangHoatDong != null) {
                nutDangHoatDong.setBackground(new Color(240, 240, 240)); // Màu mặc định
            }
            source.setBackground(new Color(100, 149, 237)); // Màu xanh
            nutDangHoatDong = source;

            // Chuyển đổi panel
            if (source == btnTrangChu) {
                cardLayout.show(PanelCard, "Trang chủ");
            } else if (source == btnQuanLyMay) {
                cardLayout.show(PanelCard, "Quản lý máy");
            } else if (source == btnDatDoAn) {
                cardLayout.show(PanelCard, "Đặt đồ ăn");
            } else if (source == btnQuanLyKhachHang) {
                cardLayout.show(PanelCard, "Quản lý khách hàng");
            } else if (source == btnQuanLyNhanVien) {
                cardLayout.show(PanelCard, "Quản lý nhân viên");
            } else if (source == btnQuanLyNhaCungCap) {
                cardLayout.show(PanelCard, "Quản lý nhà cung cấp");
            }
        };

        btnTrangChu.addActionListener(action);
        btnQuanLyMay.addActionListener(action);
        btnDatDoAn.addActionListener(action);
        btnQuanLyKhachHang.addActionListener(action);
        btnQuanLyNhanVien.addActionListener(action);
        btnQuanLyNhaCungCap.addActionListener(action);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(220, 40));
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(new Color(240, 240, 240));
        button.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 0));
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button != nutDangHoatDong) {
                    button.setBackground(new Color(211, 211, 211));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button != nutDangHoatDong) {
                    button.setBackground(new Color(240, 240, 240));
                }
            }
        });

        return button;
    }
}