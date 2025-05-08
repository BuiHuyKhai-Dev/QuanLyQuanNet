package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import DTO.KhachHangDTO;
import BUS.KhachHangBUS;
import java.util.regex.Pattern;

public class InputKhachHang extends JDialog {
    protected JTextField txtTenKH, txtMatKhau, txtCCCD, txtSDT;
    protected JComboBox<String> cboNgay, cboThang, cboNam;
    protected JButton btnLuu, btnHuy;
    public boolean isSuccess = false;
    private String maKHDangSua = null;

    public InputKhachHang(Frame parent) {
        super(parent, "Thêm Khách Hàng", true);
        initComponents();
        setLocationRelativeTo(parent);
        setSize(550, 520);
    }

    public InputKhachHang(Frame parent, KhachHangDTO kh) {
        super(parent, "Sửa Khách Hàng", true);
        initComponents();
        setLocationRelativeTo(parent);
        setSize(550, 520);
        maKHDangSua = kh.getMaKH();
        txtTenKH.setText(kh.getTenKH());
        txtMatKhau.setText(kh.getMatKhau());
        txtCCCD.setText(kh.getCccd());
        txtSDT.setText(kh.getSoDT());
        String[] dateParts = kh.getNgaySinh().split("-");
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

        JLabel lblTitle = new JLabel("ĐĂNG KÝ KHÁCH HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(accent);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(background);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTenKH = createStyledLabel("Tên KH:", textColor);
        txtTenKH = createStyledTextField();
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

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblTenKH, gbc);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(txtTenKH, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblMatKhau, gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(txtMatKhau, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lblCCCD, gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(txtCCCD, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(lblSDT, gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(txtSDT, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(lblNgaySinh, gbc);
        gbc.gridx = 1; gbc.gridy = 4; formPanel.add(datePanel, gbc);

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

        btnLuu.addActionListener(e -> luuKhachHang());
        btnHuy.addActionListener(e -> dispose());
    }

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

    private void luuKhachHang() {
        String ten = txtTenKH.getText().trim();
        String mk = txtMatKhau.getText().trim();
        String cccd = txtCCCD.getText().trim();
        String sdt = txtSDT.getText().trim();
        String ngaysinh = cboNam.getSelectedItem() + "-" + cboThang.getSelectedItem() + "-" + cboNgay.getSelectedItem();

        if (ten.isEmpty() || mk.isEmpty() || cccd.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        if (!ten.matches("^[\\u00C0-\\u1EF9a-zA-Z\\s]+$")) {
            JOptionPane.showMessageDialog(this, "Tên không được chứa ký tự đặc biệt!");
            return;
        }

        if (mk.length() < 8) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 8 ký tự!");
            return;
        }

        if (!cccd.matches("^0\\d{11}$")) {
            JOptionPane.showMessageDialog(this, "CCCD phải gồm 12 số và bắt đầu bằng 0!");
            return;
        }

        if (!sdt.matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 10 số!");
            return;
        }

        String maKH = maKHDangSua;

        KhachHangDTO kh = new KhachHangDTO(
            maKH, ten, mk, cccd, sdt,
            ngaysinh,
            java.time.LocalDate.now().toString(),
            0, 0, 0, 1
        );

        boolean result = maKHDangSua == null ?
            KhachHangBUS.themKhachHang(kh) :
            KhachHangBUS.suaKhachHang(kh);

        if (result) {
            JOptionPane.showMessageDialog(this, maKHDangSua == null ? "Thêm thành công!" : "Cập nhật thành công!");
            isSuccess = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lưu thất bại!");
        }
    }

    public static void main(String[] args) {
        InputKhachHang form = new InputKhachHang(null);
        form.setVisible(true);
        if (form.isSuccess) {
            System.out.println("KH được thêm thành công!");
        }
    }
}
