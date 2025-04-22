package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import DTO.NhanVienDTO;
import BUS.NhanVienBUS;
import java.util.regex.Pattern;

public class InputNhanVien extends JDialog {
    protected JTextField txtTenNV, txtMatKhau, txtCCCD, txtSDT, txtUsername;
    protected JComboBox<String> cboNgay, cboThang, cboNam, cboRole, cboChucVu;
    protected JButton btnLuu, btnHuy;
    public boolean isSuccess = false;
    private String maNVDangSua = null;

    public InputNhanVien(Frame parent) {
        super(parent, "Thêm Nhân Viên", true);
        initComponents();
        setLocationRelativeTo(parent);
        setSize(550, 620); // Tăng chiều cao để chứa thêm trường
    }

    public InputNhanVien(Frame parent, NhanVienDTO nv) {
        super(parent, "Sửa Nhân Viên", true);
        initComponents();
        setLocationRelativeTo(parent);
        setSize(550, 620);
        maNVDangSua = nv.getMaNV();
        txtTenNV.setText(nv.getTenNV());
        txtMatKhau.setText(nv.getMatKhau());
        txtCCCD.setText(nv.getCccd());
        txtSDT.setText(nv.getSoDT());
        txtUsername.setText(nv.getUsername());
        cboRole.setSelectedItem(nv.getRole());
        cboChucVu.setSelectedItem(nv.getChucVu());
        String[] dateParts = nv.getNgaySinh().split("-");
        cboNam.setSelectedItem(dateParts[0]);
        cboThang.setSelectedItem(dateParts[1]);
        cboNgay.setSelectedItem(dateParts[2]);
    }

    private void initComponents() {
        Color background = new Color(30, 30, 30);
        Color accent = new Color(255, 165, 0);
        Color textColor = Color.WHITE;

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(background);

        JLabel lblTitle = new JLabel("THÔNG TIN NHÂN VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(accent);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(background);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Thêm các trường mới
        JLabel lblUsername = createStyledLabel("Tên đăng nhập:", textColor);
        txtUsername = createStyledTextField();
        JLabel lblChucVu = createStyledLabel("Chức vụ:", textColor);
        cboChucVu = createStyledComboBox();
        cboChucVu.addItem("Quản lý");
        cboChucVu.addItem("Nhân viên");
        cboChucVu.addItem("Admin");

        JLabel lblRole = createStyledLabel("Vai trò:", textColor);
        cboRole = createStyledComboBox();
        cboRole.addItem("Xuất nhập kho");
        cboRole.addItem("Phục vụ");
        cboRole.addItem("Tiếp tân");
        cboRole.addItem("Kế toán");
        cboRole.addItem("All");



        // Các trường cũ
        JLabel lblTenNV = createStyledLabel("Tên NV:", textColor);
        txtTenNV = createStyledTextField();
        JLabel lblMatKhau = createStyledLabel("Mật Khẩu:", textColor);
        txtMatKhau = createStyledTextField();
        JLabel lblCCCD = createStyledLabel("CCCD:", textColor);
        txtCCCD = createStyledTextField();
        JLabel lblSDT = createStyledLabel("SĐT:", textColor);
        txtSDT = createStyledTextField();
        JLabel lblNgaySinh = createStyledLabel("Ngày Sinh:", textColor);

        cboNgay = createStyledComboBox();
        cboThang = createStyledComboBox();
        cboNam = createStyledComboBox();

        for (int i = 1; i <= 31; i++) cboNgay.addItem(String.format("%02d", i));
        for (int i = 1; i <= 12; i++) cboThang.addItem(String.format("%02d", i));
        for (int i = 1970; i <= java.time.LocalDate.now().getYear(); i++) cboNam.addItem(String.valueOf(i));

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        datePanel.setBackground(background);
        datePanel.add(cboNgay);
        datePanel.add(cboThang);
        datePanel.add(cboNam);

        // Thêm các thành phần vào form
        int row = 0;
        addFormRow(formPanel, gbc, lblUsername, txtUsername, row++);
        addFormRow(formPanel, gbc, lblRole, cboRole, row++);
        addFormRow(formPanel, gbc, lblChucVu, cboChucVu, row++);
        addFormRow(formPanel, gbc, lblTenNV, txtTenNV, row++);
        addFormRow(formPanel, gbc, lblMatKhau, txtMatKhau, row++);
        addFormRow(formPanel, gbc, lblCCCD, txtCCCD, row++);
        addFormRow(formPanel, gbc, lblSDT, txtSDT, row++);
        addFormRow(formPanel, gbc, lblNgaySinh, datePanel, row++);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(background);
        btnLuu = new JButton("✔ Lưu");
        btnHuy = new JButton("✖ Hủy");

        styleButton(btnLuu, accent);
        styleButton(btnHuy, Color.GRAY);

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        mainPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        btnLuu.addActionListener(e -> luuNhanVien());
        btnHuy.addActionListener(e -> dispose());
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, JComponent label, JComponent field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    // Các phương thức helper giữ nguyên từ InputKhachHang
    private JLabel createStyledLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setBackground(new Color(50, 50, 50));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return field;
    }

    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBackground(new Color(50, 50, 50));
        comboBox.setForeground(Color.WHITE);
        comboBox.setUI(new BasicComboBoxUI());
        comboBox.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return comboBox;
    }

    private void styleButton(JButton button, Color bg) {
        button.setBackground(bg);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 35));
    }

    private void luuNhanVien() {
        // Thêm validation cho các trường mới
        String username = txtUsername.getText().trim();
        String role = (String) cboRole.getSelectedItem();
        String chucVu = (String) cboChucVu.getSelectedItem();
        String ten = txtTenNV.getText().trim();
        String mk = txtMatKhau.getText().trim();
        String cccd = txtCCCD.getText().trim();
        String sdt = txtSDT.getText().trim();
        String ngaysinh = cboNam.getSelectedItem() + "-" + cboThang.getSelectedItem() + "-" + cboNgay.getSelectedItem();

        if (username.isEmpty() || ten.isEmpty() || mk.isEmpty() || cccd.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        // Thêm các validation khác tương tự...

        NhanVienDTO nv = new NhanVienDTO(
            maNVDangSua, ten, mk, cccd, sdt,
            ngaysinh, java.time.LocalDate.now().toString(),
            username, role, chucVu, 1
        );

        boolean result = maNVDangSua == null ?
            NhanVienBUS.themNhanVien(nv) :
            NhanVienBUS.suaNhanVien(nv);

        if (result) {
            JOptionPane.showMessageDialog(this, maNVDangSua == null ? "Thêm thành công!" : "Cập nhật thành công!");
            isSuccess = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lưu thất bại!");
        }
    }

    public static void main(String[] args) {
        InputNhanVien form = new InputNhanVien(null);
        form.setVisible(true);
        if (form.isSuccess) {
            System.out.println("NV được thêm thành công!");
        }
    }
}