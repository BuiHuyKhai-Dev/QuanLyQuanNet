package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import DAL.DBConnect;
import DTO.KhachHangDTO;
import DAO.KhachHangDAO;
import BUS.KhachHangBUS;
import GUI.FormNapTien;
import GUI.InputKhachHang;

public class FormQuanLyKhachHang extends JFrame {
    private JTable tblKhachHang;
    private JTextField txtTimKiemCCCD;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnNapTien;

    public FormQuanLyKhachHang() {
        setTitle("Quản Lý Khách Hàng");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadTableData();
    }

private void initComponents() {
    // Sửa tiêu đề cho đúng encoding
    setTitle("Quản Lý Khách Hàng"); 

    // Định nghĩa màu sắc và font
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

    // Tạo bảng với model
    String[] columnNames = {
        "Mã KH", "Tên KH", "Mật Khẩu", "CCCD", "SĐT", 
        "Ngày Sinh", "Ngày Đăng Ký", "Số Giờ", "Tiền Nạp", "Số Dư"
    };
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    tblKhachHang = new JTable(model);
    // Style cho bảng
    tblKhachHang.setFont(new Font("Arial", Font.PLAIN, 14));
    tblKhachHang.setRowHeight(30);
    tblKhachHang.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
    tblKhachHang.getTableHeader().setBackground(buttonColor);
    tblKhachHang.getTableHeader().setForeground(Color.WHITE);
    JScrollPane scrollPane = new JScrollPane(tblKhachHang);
    contentPanel.add(scrollPane, BorderLayout.CENTER);

    // Panel chứa nút và ô tìm kiếm (sử dụng BoxLayout)
    JPanel panelButtons = new JPanel();
    panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
    panelButtons.setBackground(background);
    panelButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

    // Tạo các nút với kích thước cố định
    btnThem = createStyledButton("➕ Thêm", buttonColor, buttonHoverColor);
    btnSua = createStyledButton("✏️ Sửa", buttonColor, buttonHoverColor);
    btnXoa = createStyledButton("🗑️ Xóa", new Color(220, 53, 69), buttonHoverColor);
    btnTimKiem = createStyledButton("🔍 Tìm CCCD", buttonColor, buttonHoverColor);
    btnNapTien = createStyledButton("💵 Nạp Tiền", buttonColor, buttonHoverColor);

    // Ô tìm kiếm
    txtTimKiemCCCD = new JTextField(20);
    txtTimKiemCCCD.setMaximumSize(new Dimension(200, 35));
    txtTimKiemCCCD.setFont(new Font("Arial", Font.PLAIN, 14));
    txtTimKiemCCCD.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(5, 5, 5, 5)
    ));

    // Thêm các thành phần vào panelButtons
    panelButtons.add(btnThem);
    panelButtons.add(Box.createHorizontalStrut(10));
    panelButtons.add(btnSua);
    panelButtons.add(Box.createHorizontalStrut(10));
    panelButtons.add(btnXoa);
    panelButtons.add(Box.createHorizontalStrut(10));
    panelButtons.add(txtTimKiemCCCD);
    panelButtons.add(Box.createHorizontalStrut(10));
    panelButtons.add(btnTimKiem);
    panelButtons.add(Box.createHorizontalStrut(10));
    panelButtons.add(btnNapTien);

    // Thêm panelButtons vào phía Bắc của contentPanel
    contentPanel.add(panelButtons, BorderLayout.NORTH);

      // Các hành động cho nút bấm
        btnThem.addActionListener(e -> {
            InputKhachHang form = new InputKhachHang(this);
            form.setVisible(true);
            if (form.isSuccess) loadTableData();
        });

        btnSua.addActionListener(e -> {
            int row = tblKhachHang.getSelectedRow();
            if (row != -1) {
                String maKH = tblKhachHang.getValueAt(row, 0).toString();
                KhachHangDTO kh = KhachHangBUS.timTheoMaKH(maKH);
                InputKhachHang form = new InputKhachHang(this, kh);
                form.setVisible(true);
                if (form.isSuccess) loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa.");
            }
        });

        btnXoa.addActionListener(e -> {
            int row = tblKhachHang.getSelectedRow();
            if (row != -1) {
                String maKH = tblKhachHang.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa khách hàng " + maKH + "?");
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = KhachHangBUS.xoaKhachHang(maKH);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
                        loadTableData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa.");
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
                String cccd = model.getValueAt(i, 3).toString();
                if (cccd.equals(cccdTim)) {
                    tblKhachHang.setRowSelectionInterval(i, i);
                    tblKhachHang.scrollRectToVisible(tblKhachHang.getCellRect(i, 0, true));
                    timThay = true;
                    break;
                }
            }
            if (!timThay) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng có CCCD: " + cccdTim);
            }
        });

        btnNapTien.addActionListener(e -> {
            int row = tblKhachHang.getSelectedRow();
            if (row != -1) {
                String maKH = tblKhachHang.getValueAt(row, 0).toString();
                FormNapTien form = new FormNapTien(this, maKH);
                form.setVisible(true);
                if (form.isSuccess) loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để nạp tiền.");
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
    button.setMaximumSize(new Dimension(150, 40)); // Kích thước cố định
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
    public void loadTableData() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        List<KhachHangDTO> danhSach = KhachHangBUS.getDanhSachKhachHang();
        for (KhachHangDTO kh : danhSach) {
            model.addRow(new Object[]{
                kh.getMaKH(), kh.getTenKH(), kh.getMatKhau(), kh.getCccd(), kh.getSoDT(),
                kh.getNgaySinh(), kh.getNgayDangKy(), kh.getSoGio(), kh.getSoTienNaptong(), kh.getSoDu()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormQuanLyKhachHang().setVisible(true));
    }
}
 