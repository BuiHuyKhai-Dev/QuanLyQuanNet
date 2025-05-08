package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SuaThucAnDialog extends JDialog {
    private JTextField txtTen, txtDonVi, txtDonGia;
    private JFormattedTextField txtHanSuDung;
    private JButton btnLuu, btnHuy;

    private int maThucAn;  // ID của dịch vụ đang chỉnh sửa

    public SuaThucAnDialog(JFrame parent, int maThucAn, String ten, String donVi, double donGia, String hanSuDung) {
        super(parent, "Sửa Dịch Vụ", true);
        this.maThucAn = maThucAn;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        txtTen = new JTextField(ten);
        txtDonVi = new JTextField(donVi);
        txtDonGia = new JTextField(String.valueOf(donGia));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txtHanSuDung = new JFormattedTextField(sdf);
        txtHanSuDung.setText(hanSuDung);

        inputPanel.add(new JLabel("Tên dịch vụ:"));
        inputPanel.add(txtTen);
        inputPanel.add(new JLabel("Đơn vị:"));
        inputPanel.add(txtDonVi);
        inputPanel.add(new JLabel("Đơn giá:"));
        inputPanel.add(txtDonGia);
        inputPanel.add(new JLabel("Hạn sử dụng:"));
        inputPanel.add(txtHanSuDung);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);
        add(buttonPanel, BorderLayout.SOUTH);

        btnLuu.addActionListener(e -> {
            String tenMoi = txtTen.getText().trim();
            String donViMoi = txtDonVi.getText().trim();
            String giaStr = txtDonGia.getText().trim();
            String hanStr = txtHanSuDung.getText().trim();
        
            if (tenMoi.isEmpty() || donViMoi.isEmpty() || giaStr.isEmpty() || hanStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }
        
            try {
                double gia = Double.parseDouble(giaStr);
                java.sql.Date hanSuDungMoi = java.sql.Date.valueOf(hanStr);
        
                // Gọi DAO update
                DTO.ThucAnDTO obj = new DTO.ThucAnDTO();
                obj.setMaThucAn(maThucAn);
                obj.setTenThucAn(tenMoi);
                obj.setDonVi(donViMoi);
                obj.setDonGia(java.math.BigDecimal.valueOf(gia));
                obj.setHanSuDung(hanSuDungMoi);
        
                DAO.ThucAnDAO dao = new DAO.ThucAnDAO();
                boolean result = dao.update(obj);
        
                if (result) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
        
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi dữ liệu: " + ex.getMessage());
            }
        });        
        btnHuy.addActionListener(e -> dispose());
    }
}
