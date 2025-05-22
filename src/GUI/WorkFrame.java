package GUI;

import DTO.KhachHangDTO;
import DTO.TaiKhoanDTO;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import DAO.KhachHangDAO;

public class WorkFrame extends JFrame {
    public CardLayout cardLayout = new CardLayout();
    public JPanel PanelCard = new JPanel(cardLayout);
    private JButton btnTrangChu, btnQuanLyMay, btnDatDoAn, btnQuanLyKhachHang, btnQuanLyNhanVien, btnQuanLyNhaCungCap, btnThongKe;
    private JButton btnPhieuNhap, btnQuanLyKho, btnPhanQuyen, btnDangXuat, btnTaiKhoan; // Nút mới
    private JButton nutDangHoatDong; // Nút đang được chọn
    private int maKH;
    private int quyen;

    public WorkFrame() {
        this.init();
        this.setVisible(true);
        this.nutDangHoatDong = btnTrangChu; // Ban đầu nút trang chủ được chọn
        this.btnTrangChu.setBackground(new Color(100, 149, 237)); // Màu xanh cho nút đang hoạt động
    }

    public WorkFrame(TaiKhoanDTO tk) {
        if (tk.getTrangThai() == 0) {
            JOptionPane.showMessageDialog(this, "Tài khoản đã bị khóa. Vui lòng liên hệ quản trị viên.");
            System.exit(0);
        } else {
            
            quyen= tk.getMaNhomQuyen();
            switch (tk.getMaNhomQuyen()) {
                case 1 -> {
                    this.init();
                    this.setVisible(true);
                    this.nutDangHoatDong = btnTrangChu; // Ban đầu nút trang chủ được chọn
                    this.btnTrangChu.setBackground(new Color(100, 149, 237)); // Màu xanh cho nút đang hoạt động
                }
                case 2 -> {
                    this.init();
                    btnQuanLyNhaCungCap.setVisible(false);
                    btnQuanLyNhanVien.setVisible(false);
                    btnQuanLyKho.setVisible(false); // Ẩn nút Quản lý kho
                    btnPhieuNhap.setVisible(false); // Ẩn nút Phiếu nhập
                    btnPhanQuyen.setVisible(false);
                    btnTaiKhoan.setVisible(false); // Ẩn nút Tài khoản
                    this.setVisible(true);
                    this.nutDangHoatDong = btnTrangChu; // Ban đầu nút trang chủ được chọn
                    this.btnTrangChu.setBackground(new Color(100, 149, 237)); // Màu xanh cho nút đang hoạt động
                }
                case 3 -> {
                    this.init();
                    btnQuanLyKhachHang.setVisible(false);
                    btnQuanLyNhanVien.setVisible(false);
                    btnPhanQuyen.setVisible(false);
                    btnQuanLyMay.setVisible(false);
                    btnDatDoAn.setVisible(false);
                    btnTaiKhoan.setVisible(false); // Ẩn nút Tài khoản
                    this.setVisible(true);
                    this.nutDangHoatDong = btnTrangChu; // Ban đầu nút trang chủ được chọn
                    this.btnTrangChu.setBackground(new Color(100, 149, 237)); // Màu xanh cho nút đang hoạt động
                }
                case 4,5 -> {
                    KhachHangDAO khDAO = new KhachHangDAO();
                    KhachHangDTO khach = khDAO.timTheoEmail(tk.getTenDangNhap());
                    maKH = khach.getMaKhachHang();
                    this.init();
                    btnQuanLyMay.setVisible(false);
                    btnQuanLyKhachHang.setVisible(false);
                    btnQuanLyNhanVien.setVisible(false);
                    btnQuanLyNhaCungCap.setVisible(false);
                    btnThongKe.setVisible(false);
                    btnPhieuNhap.setVisible(false); // Ẩn nút Phiếu nhập
                    btnQuanLyKho.setVisible(false); // Ẩn nút Quản lý kho
                    btnPhanQuyen.setVisible(false);
                    btnTaiKhoan.setVisible(false); // Ẩn nút Tài khoản


                    this.setVisible(true);
                    this.nutDangHoatDong = btnTrangChu; // Ban đầu nút đặt đồ ăn được chọn
                    this.btnDatDoAn.setBackground(new Color(100, 149, 237)); // Màu xanh cho nút đang hoạt động
                }
                default -> {
                    JOptionPane.showMessageDialog(this, "Tài khoản không có quyền truy cập.");
                    System.exit(0);
                }
            }
        }
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
        btnPhieuNhap = createMenuButton("Phiếu nhập"); // Nút mới
        btnQuanLyKho = createMenuButton("Quản lý kho"); // Nút mới
        btnThongKe = createMenuButton("Thống kê");
        btnPhanQuyen = createMenuButton("Phân quyền"); // Nút mới
        btnTaiKhoan = createMenuButton("Tài khoản"); // Nút mới
        btnDangXuat = createMenuButton("Đăng xuất"); // Nút đăng xuất

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
        
        gbc.gridy = 7;
        sidebar.add(btnQuanLyKho, gbc); // Thêm nút Quản lý kho

        gbc.gridy = 8;
        sidebar.add(btnPhieuNhap, gbc); // Thêm nút Phiếu nhập

        gbc.gridy = 9;
        sidebar.add(btnThongKe, gbc);

        gbc.gridy = 10;
        sidebar.add(btnPhanQuyen, gbc); // Thêm nút Phân quyền

        gbc.gridy = 11;
        sidebar.add(btnTaiKhoan, gbc); // Nút tài khoản

        gbc.gridy = 12;
        sidebar.add(btnDangXuat, gbc); // Nút đăng xuất

        // Add a "glue" component to push buttons to the top
        gbc.gridy = 11;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        sidebar.add(new JPanel(), gbc);

        // Add panels to PanelCard
        PanelCard.add(new TrangChuPanel(), "Trang chủ");
        PanelCard.add(new QuanLyMayPanel(), "Quản lý máy");
        PanelCard.add(new DatDoAnPanel(quyen, maKH), "Đặt đồ ăn");
        PanelCard.add(new QuanLyKhachHangPanel(), "Quản lý khách hàng");
        PanelCard.add(new QuanLyNhanVienPanel(), "Quản lý nhân viên");
        PanelCard.add(new QuanLyNhaCungCapPanel(), "Quản lý nhà cung cấp");
        PanelCard.add(new ThongKePanel(), "Thống kê");
        PanelCard.add(new PhieuNhapPanel(), "Phiếu nhập"); // Panel mới
        PanelCard.add(new QuanLyKhoPanel(), "Quản lý kho"); // Panel mới
        PanelCard.add(new PhanQuyenPanel(), "Phân quyền"); // Panel mới
        PanelCard.add(new TaiKhoanPanel(), "Tài khoản"); // Panel mới

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
            } else if (source == btnThongKe) {
                cardLayout.show(PanelCard, "Thống kê");
            } else if (source == btnPhieuNhap) {
                cardLayout.show(PanelCard, "Phiếu nhập");
            } else if (source == btnQuanLyKho) {
                cardLayout.show(PanelCard, "Quản lý kho");
            } else if (source == btnPhanQuyen) {
                cardLayout.show(PanelCard, "Phân quyền");
            }   else if (source == btnTaiKhoan) {
                cardLayout.show(PanelCard, "Tài khoản");
            }
        };

        btnTrangChu.addActionListener(action);
        btnQuanLyMay.addActionListener(action);
        btnDatDoAn.addActionListener(action);
        btnQuanLyKhachHang.addActionListener(action);
        btnQuanLyNhanVien.addActionListener(action);
        btnQuanLyNhaCungCap.addActionListener(action);
        btnThongKe.addActionListener(action);
        btnPhieuNhap.addActionListener(action); // Thêm action listener cho nút Phiếu nhập
        btnQuanLyKho.addActionListener(action); // Thêm action listener cho nút Quản lý kho
        btnPhanQuyen.addActionListener(action); // Thêm action listener cho nút Phân quyền
        btnTaiKhoan.addActionListener(action); // Thêm action listener cho nút Tài khoản
        btnDangXuat.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                this.dispose();
                new DangNhap().setVisible(true);
            }
        });
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