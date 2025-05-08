package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import BUS.KhachHangBUS;

public class FormNapTien extends JDialog {
    private JTextField txtSoTien;
    private JLabel lblMaKH;
    private JButton btnXacNhan, btnHuy;
    private String maKH;
    public boolean isSuccess = false;

    public FormNapTien(Frame parent, String maKH) {
        super(parent, "Nạp Tiền Cho Khách Hàng", true);
        this.maKH = maKH;
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        Color background = new Color(30, 30, 30);
        Color accent = new Color(255, 165, 0);
        Color textColor = Color.WHITE;

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(background);

        JLabel lblTitle = new JLabel("NẠP TIỀN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(accent);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(background);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel lblMaKHLabel = createStyledLabel("Mã Khách Hàng:", textColor);
        lblMaKH = createStyledLabel(maKH, accent);
        JLabel lblSoTien = createStyledLabel("Số Tiền Nạp:", textColor);
        txtSoTien = createStyledTextField();
        txtSoTien.setPreferredSize(new Dimension(200, 30));

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(lblMaKHLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(lblMaKH, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(lblSoTien, gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(txtSoTien, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(background);
        btnXacNhan = new JButton("✔ Xác Nhận");
        btnHuy = new JButton("✖ Hủy");
        styleButton(btnXacNhan, accent);
        styleButton(btnHuy, Color.GRAY);

        buttonPanel.add(btnXacNhan);
        buttonPanel.add(btnHuy);

        mainPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        btnXacNhan.addActionListener(e -> {
            try {
                double soTien = Double.parseDouble(txtSoTien.getText());
                if (soTien <= 0) {
                    JOptionPane.showMessageDialog(this, "Số tiền phải lớn hơn 0!");
                    return;
                }

                boolean success = KhachHangBUS.napTien(maKH, soTien);
                if (success) {
                    isSuccess = true;
                    JOptionPane.showMessageDialog(this, "Nạp tiền thành công!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Nạp tiền thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền hợp lệ!");
            }
        });

        btnHuy.addActionListener(e -> dispose());
    }

    private JLabel createStyledLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBackground(new Color(50, 50, 50));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return field;
    }

    private void styleButton(JButton button, Color bg) {
        button.setBackground(bg);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 35));
    }
}    
