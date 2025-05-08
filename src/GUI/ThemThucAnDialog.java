package GUI;
import javax.swing.*;

import DAO.ThucAnDAO;
import DTO.ThucAnDTO;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.*;
import java.util.Date;

public class ThemThucAnDialog extends JDialog {
    private JTextField txtTen, txtDonVi, txtDonGia;
    private JFormattedTextField txtHanSuDung;
    private JButton btnLuu, btnHuy;

    public ThemThucAnDialog(JFrame parent) {
        super(parent, "Thêm Dịch Vụ Mới", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        txtTen = new JTextField();
        txtDonVi = new JTextField();
        txtDonGia = new JTextField();

        // Hạn sử dụng (date input)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txtHanSuDung = new JFormattedTextField(sdf);
        txtHanSuDung.setValue(new Date());

        inputPanel.add(new JLabel("Tên dịch vụ:"));
        inputPanel.add(txtTen);
        inputPanel.add(new JLabel("Đơn vị:"));
        inputPanel.add(txtDonVi);
        inputPanel.add(new JLabel("Đơn giá:"));
        inputPanel.add(txtDonGia);
        inputPanel.add(new JLabel("Hạn sử dụng:"));
        inputPanel.add(txtHanSuDung);

        add(inputPanel, BorderLayout.CENTER);

        // Nút
        JPanel buttonPanel = new JPanel();
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);
        add(buttonPanel, BorderLayout.SOUTH);

        // Sự kiện
        btnLuu.addActionListener(e -> {
            String ten = txtTen.getText().trim();
            String donVi = txtDonVi.getText().trim();
            String giaStr = txtDonGia.getText().trim();
            String hanStr = txtHanSuDung.getText().trim();

            // Kiểm tra các trường dữ liệu có rỗng không
            if (ten.isEmpty() || donVi.isEmpty() || giaStr.isEmpty() || hanStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            try {
                // Kiểm tra giá trị của đơn giá
                double gia = Double.parseDouble(giaStr);

                // Kiểm tra hạn sử dụng
                java.sql.Date hanSuDung = java.sql.Date.valueOf(hanStr);
                ThucAnDTO newDichVu = new ThucAnDTO();
                newDichVu.setTenThucAn(ten);
                newDichVu.setDonVi(donVi);
                newDichVu.setDonGia(BigDecimal.valueOf(gia));
                newDichVu.setHanSuDung(hanSuDung);

                // Ví dụ gọi một phương thức insert vào cơ sở dữ liệu
                ThucAnDAO thucAnDAO = new ThucAnDAO();
                boolean success = thucAnDAO.insert(newDichVu);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm dịch vụ.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải là một số hợp lệ.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Hạn sử dụng không hợp lệ.");
            }
        });

        btnHuy.addActionListener(e -> dispose());
    }

}
