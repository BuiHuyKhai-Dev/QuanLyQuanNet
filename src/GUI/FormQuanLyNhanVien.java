package GUI;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import DTO.NhanVienDTO;
import BUS.NhanVienBUS;
import GUI.InputNhanVien;

public class FormQuanLyNhanVien extends JFrame {
    private JTable tblNhanVien;
    private JTextField txtTimKiemCCCD;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem;

    public FormQuanLyNhanVien() {
        setTitle("Quản Lý Nhân Viên");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadTableData();
    }

    private void initComponents() {
        // Thiết lập màu sắc và font
        Color background = Color.WHITE;
        Color buttonColor = new Color(34, 193, 195);
        Color buttonHoverColor = new Color(253, 182, 35);
        Color textColor = new Color(50, 50, 50);

        getContentPane().setBackground(background);

        // Panel chính sử dụng BorderLayout
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPanel.setBackground(background);
        add(contentPanel);

        // Tạo bảng dữ liệu nhân viên
        String[] columnNames = {
            "Mã NV", "Tên NV", "CCCD", "SĐT", "Username", "Vai trò", "Chức vụ", "Trạng thái"
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tblNhanVien = new JTable(model);
        tblNhanVien.setFont(new Font("Arial", Font.PLAIN, 14));
        tblNhanVien.setRowHeight(30);
        tblNhanVien.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tblNhanVien.getTableHeader().setBackground(buttonColor);
        tblNhanVien.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(tblNhanVien);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel chứa nút và ô tìm kiếm
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
        panelButtons.setBackground(background);
        panelButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Tạo các nút chức năng
        btnThem = createStyledButton("➕ Thêm", buttonColor, buttonHoverColor);
        btnSua = createStyledButton("✏️ Sửa", buttonColor, buttonHoverColor);
        btnXoa = createStyledButton("🗑️ Xóa", new Color(220, 53, 69), buttonHoverColor);
        btnTimKiem = createStyledButton("🔍 Tìm CCCD", buttonColor, buttonHoverColor);

        // Ô tìm kiếm
        txtTimKiemCCCD = new JTextField(20);
        txtTimKiemCCCD.setMaximumSize(new Dimension(200, 35));
        txtTimKiemCCCD.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTimKiemCCCD.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Thêm các thành phần vào panel
        panelButtons.add(btnThem);
        panelButtons.add(Box.createHorizontalStrut(10));
        panelButtons.add(btnSua);
        panelButtons.add(Box.createHorizontalStrut(10));
        panelButtons.add(btnXoa);
        panelButtons.add(Box.createHorizontalStrut(10));
        panelButtons.add(txtTimKiemCCCD);
        panelButtons.add(Box.createHorizontalStrut(10));
        panelButtons.add(btnTimKiem);

        // Thêm panel vào giao diện
        contentPanel.add(panelButtons, BorderLayout.NORTH);

        // Thiết lập sự kiện cho các nút
        btnThem.addActionListener(e -> {
            InputNhanVien form = new InputNhanVien(this);
            form.setVisible(true);
            if (form.isSuccess) loadTableData();
        });

        btnSua.addActionListener(e -> {
            int row = tblNhanVien.getSelectedRow();
            if (row != -1) {
                String maNV = tblNhanVien.getValueAt(row, 0).toString();
                NhanVienDTO nv = NhanVienBUS.timTheoMaNV(maNV);
                InputNhanVien form = new InputNhanVien(this, nv);
                form.setVisible(true);
                if (form.isSuccess) loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa.");
            }
        });

        btnXoa.addActionListener(e -> {
            int row = tblNhanVien.getSelectedRow();
            if (row != -1) {
                String maNV = tblNhanVien.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa nhân viên " + maNV + "?");
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = NhanVienBUS.xoaNhanVien(maNV);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
                        loadTableData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa.");
            }
        });

        btnTimKiem.addActionListener(e -> {
            String cccdTim = txtTimKiemCCCD.getText().trim();
            if (cccdTim.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số CCCD để tìm.");
                return;
            }
            int rowCount = model.getRowCount();
            boolean timThay = false;
            for (int i = 0; i < rowCount; i++) {
                String cccd = model.getValueAt(i, 2).toString();
                if (cccd.equals(cccdTim)) {
                    tblNhanVien.setRowSelectionInterval(i, i);
                    tblNhanVien.scrollRectToVisible(tblNhanVien.getCellRect(i, 0, true));
                    timThay = true;
                    break;
                }
            }
            if (!timThay) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên có CCCD: " + cccdTim);
            }
        });
    }

    // Helper method tạo nút
    private JButton createStyledButton(String text, Color bg, Color hoverBg) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setMaximumSize(new Dimension(150, 40));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBg);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bg);
            }
        });
        return button;
    }

    // Tải dữ liệu vào bảng
    public void loadTableData() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        List<NhanVienDTO> danhSach = NhanVienBUS.getDanhSachNhanVien();
        for (NhanVienDTO nv : danhSach) {
            model.addRow(new Object[]{
                nv.getMaNV(),
                nv.getTenNV(),
                nv.getCccd(),
                nv.getSoDT(),
                nv.getUsername(),
                nv.getRole(),
                nv.getChucVu(),
                nv.getTrangThai() == 1 ? "Hoạt động" : "Vô hiệu"
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormQuanLyNhanVien().setVisible(true));
    }
}