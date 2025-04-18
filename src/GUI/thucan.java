package GUI;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
//import java.beans.Statement;
import java.math.BigDecimal;
import java.net.URL;
//import java.security.Timestamp;

public class thucan extends JPanel {

    private DefaultTableModel cartModel;
    private JTable cartTable;
    private JLabel totalLabel;

    public thucan() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ========== DANH SÁCH MÓN ĂN ===========
        JPanel foodPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        foodPanel.setBorder(BorderFactory.createTitledBorder("Chọn món ăn"));

        String[] foods = {"Mì xào", "Cơm gà", "Trà sữa", "Khoai tây", "Gà rán"};
        int[] prices = {30000, 40000, 25000, 20000, 35000};
        String[] imgNames = {
            "../img/mixao.jpg",
            "../img/comga.jpg",
            "../img/trasua.jpg",
            "../img/khoaitay.jpg",
            "../img/garan.jpg"
        };

        // ========== BẢNG GIỎ HÀNG ===========
        cartModel = new DefaultTableModel(new Object[]{"Tên món", "Số lượng", "Đơn giá"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 1 ? Integer.class : Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1; // chỉ cột số lượng chỉnh được
            }
        };

        cartTable = new JTable(cartModel);
        cartTable.setRowHeight(30);
        cartTable.getColumnModel().getColumn(1).setCellEditor(new SpinnerEditor(cartTable));

        for (int i = 0; i < foods.length; i++) {
            ImageIcon icon;
            URL imgURL = getClass().getResource(imgNames[i]);
            if (imgURL != null) {
                icon = new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH));
            } else {
                icon = new ImageIcon(); // ảnh trống nếu không tìm thấy
            }
            foodPanel.add(createFoodBox(foods[i], prices[i], icon));
        }

        // ========== PANEL BÊN PHẢI ==========
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Món đã chọn"));
        JScrollPane scrollPane = new JScrollPane(cartTable);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // ========== NÚT VÀ TỔNG TIỀN ==========
        JButton datDonButton = new JButton("Đặt đơn");
                datDonButton.addActionListener(e -> datDonHang());

        totalLabel = new JLabel("Tổng tiền: 0 đ");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(datDonButton, BorderLayout.EAST);
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        this.add(foodPanel, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
        cartModel.addTableModelListener(e -> updateTotalLabel());
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    // TẠO MỖI MÓN ĂN
    private JPanel createFoodBox(String name, int price, ImageIcon icon) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel nameLabel = new JLabel(name + " - " + price + "đ", JLabel.CENTER);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel imageLabel = new JLabel(icon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addButton = new JButton("Thêm");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(e -> {
            cartModel.addRow(new Object[]{name, 1, price});
            updateTotalLabel();
        });

        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(imageLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(addButton);

        return panel;
    }

    // CẬP NHẬT TỔNG TIỀN
    private void updateTotalLabel() {
        int total = 0;
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            int qty = (int) cartModel.getValueAt(i, 1);
            int price = (int) cartModel.getValueAt(i, 2);
            total += qty * price;
        }
        totalLabel.setText("Tổng tiền: " + total + " đ");
    }
    
    private void datDonHang() {
    if (cartModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một món ăn.");
        return;
    }

    try (Connection conn = DAL.DBConnect.getConnection()) {
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối database.");
            return;
        }

        conn.setAutoCommit(false); // bắt đầu transaction

        int maKH = 1;   // 👈 Tạm thời mã khách hardcode
        int maMay = 2;  // 👈 Tạm thời mã máy cũng hardcode, bạn có thể lấy từ máy đang chọn
        Timestamp now = new Timestamp(System.currentTimeMillis());
        BigDecimal tongTien = BigDecimal.ZERO;

        // === 1. Insert vào DonHangThucAn
        String insertDon = "INSERT INTO DonHangThucAn (MaKH, MaMay, NgayDat, TongTien, TrangThai, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement donStmt = conn.prepareStatement(insertDon, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            int qty = (int) cartModel.getValueAt(i, 1);
            int price = (int) cartModel.getValueAt(i, 2);
            tongTien = tongTien.add(BigDecimal.valueOf(qty * price));
        }

        donStmt.setInt(1, maKH);
        donStmt.setInt(2, maMay);
        donStmt.setTimestamp(3, now);
        donStmt.setBigDecimal(4, tongTien);
        donStmt.setString(5, "DangCho");
        donStmt.setTimestamp(6, now);
        donStmt.executeUpdate();

        ResultSet rs = donStmt.getGeneratedKeys();
        int maDH = -1;
        if (rs.next()) {
            maDH = rs.getInt(1);
        } else {
            conn.rollback();
            JOptionPane.showMessageDialog(this, "Không thể tạo đơn hàng.");
            return;
        }

        // === 2. Thêm chi tiết & cập nhật kho
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            String tenMon = (String) cartModel.getValueAt(i, 0);
            int qty = (int) cartModel.getValueAt(i, 1);

            // Tìm mã thức ăn từ tên
            PreparedStatement findStmt = conn.prepareStatement("SELECT MaThucAn FROM ThucAn WHERE TenThucAn = ?");
            findStmt.setString(1, tenMon);
            ResultSet rsFind = findStmt.executeQuery();

            int maTA = -1;
            if (rsFind.next()) {
                maTA = rsFind.getInt("MaThucAn");
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Không tìm thấy món: " + tenMon);
                return;
            }

            // Trừ kho
            PreparedStatement updateKho = conn.prepareStatement("UPDATE KhoThucAn SET SoLuong = SoLuong - ? WHERE MaThucAn = ?");
            updateKho.setInt(1, qty);
            updateKho.setInt(2, maTA);
            int updated = updateKho.executeUpdate();

            if (updated == 0) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Không đủ hàng trong kho cho món: " + tenMon);
                return;
            }

            // TODO: Chưa có bảng chi tiết đơn hàng, nếu cần thêm bảng `ChiTietDonThucAn` thì sẽ insert ở đây
        }

        conn.commit();
        JOptionPane.showMessageDialog(this, "Đặt món thành công!");

        // Xoá giỏ hàng sau khi đặt
        cartModel.setRowCount(0);
        updateTotalLabel();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi đặt món: " + ex.getMessage());
    }
}


    // EDITOR SPINNER CHO CỘT SỐ LƯỢNG
    class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
    private final JSpinner spinner;
    private final JTable table;

    public SpinnerEditor(JTable table) {
        this.table = table;
        spinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1)); // min = 0, max = 100
        spinner.addChangeListener(e -> {
    int row = table.getEditingRow(); // Lấy dòng hiện tại đang chỉnh sửa
    int value = (int) spinner.getValue(); // Lấy giá trị của spinner
    System.out.println("Giá trị mới của spinner: " + value); 

    if (row >= 0 && row < table.getRowCount()) {
        System.out.println("Dòng hợp lệ: " + row); 

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        if (value == 0) {
            System.out.println("Đang xóa món ăn tại dòng: " + row);
            fireEditingStopped(); // Cập nhật model trước khi xóa
            model.removeRow(row);

            if (table.getRowCount() > 0) {
                updateTotalLabel(); // Nếu còn dòng, cập nhật tổng tiền
            } else {
                totalLabel.setText("Tổng tiền: 0 đ");
            }
        } else {
            fireEditingStopped(); // ✅ Cập nhật model với số lượng mới
            updateTotalLabel();   // ✅ Rồi mới cập nhật tổng tiền
        }
    } else {
        System.out.println("Dòng không hợp lệ hoặc bảng trống.");
    }
});


    }

    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (value instanceof Integer) {
            spinner.setValue(value);
        } else {
            spinner.setValue(1); // Default value if value is not an integer
        }
        return spinner;
    }
}


}
