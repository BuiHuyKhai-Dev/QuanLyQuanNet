package GUI.QLDA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import BUS.HoaDonThucAnBUS;

public class datdoan extends JPanel {
    private DefaultTableModel orderTableModel;
    private JLabel lblTotalPrice;
    private int totalPrice = 0;

    private JTextField txtMaKH;
    private JTextField txtMaMay;
    private JTextField txtMaNV;
    private HoaDonThucAnBUS hoaDonBUS = new HoaDonThucAnBUS();

    public datdoan() {
        setLayout(new BorderLayout());

        // Panel nhập mã KH, mã Máy, mã Nhân viên
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        inputPanel.add(new JLabel("Mã Khách Hàng:"));
        txtMaKH = new JTextField(10);
        inputPanel.add(txtMaKH);

        inputPanel.add(new JLabel("Mã Máy:"));
        txtMaMay = new JTextField(10);
        inputPanel.add(txtMaMay);

        inputPanel.add(new JLabel("Mã Nhân Viên:"));
        txtMaNV = new JTextField(10);
        inputPanel.add(txtMaNV);

        add(inputPanel, BorderLayout.NORTH);

        // Panel menu món ăn bên trái
        JPanel menuPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thêm món
        addMenuItem(menuPanel, "Cơm gà", "../../img/comga.jpg", 50000);
        addMenuItem(menuPanel, "Gà rán", "../../img/garan.jpg", 60000);
        addMenuItem(menuPanel, "Khoai tây chiên", "../../img/khoaitay.jpg", 30000);
        addMenuItem(menuPanel, "Mì xào", "../../img/mixao.jpg", 40000);
        addMenuItem(menuPanel, "Trà sữa", "../../img/trasua.jpg", 35000);
        addMenuItem(menuPanel, "Bánh mì", "../../img/banhmi.jpg", 20000);
        addMenuItem(menuPanel, "Phở bò", "../../img/phobo.jpg", 45000);
        addMenuItem(menuPanel, "Nước ngọt", "../../img/nuocngot.jpg", 15000);

        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        menuScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        menuScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Panel đơn hàng bên phải
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        orderTableModel = new DefaultTableModel(new Object[]{"Tên món", "Đơn giá", "Số lượng", "Thành tiền"}, 0);
        JTable orderTable = new JTable(orderTableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        orderPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        lblTotalPrice = new JLabel("Thành tiền: 0 VND", SwingConstants.RIGHT);
        lblTotalPrice.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(lblTotalPrice, BorderLayout.CENTER);

        JButton btnCheckout = new JButton("Thanh toán");
        btnCheckout.addActionListener(e -> {
            try {
                int maKH = Integer.parseInt(txtMaKH.getText().trim());
                int maMay = Integer.parseInt(txtMaMay.getText().trim());
                int maNV = Integer.parseInt(txtMaNV.getText().trim());

                Timestamp now = new Timestamp(System.currentTimeMillis());

                boolean success = hoaDonBUS.themHoaDon(maKH, maMay, now, totalPrice, 1, maNV);

                if (success) {
                    JOptionPane.showMessageDialog(this, "✅ Đặt đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    orderTableModel.setRowCount(0);
                    totalPrice = 0;
                    lblTotalPrice.setText("Thành tiền: 0 VND");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Không thể đặt đơn. Kiểm tra mã khách hàng, mã máy, mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Vui lòng nhập đúng định dạng cho mã khách hàng, mã máy và mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        bottomPanel.add(btnCheckout, BorderLayout.EAST);
        orderPanel.add(bottomPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuScrollPane, orderPanel);
        splitPane.setDividerLocation(700);
        splitPane.setResizeWeight(0.7);
        splitPane.setOneTouchExpandable(true);
        add(splitPane, BorderLayout.CENTER);
    }

    private void addMenuItem(JPanel menuPanel, String name, String imagePath, int price) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ImageIcon icon = new ImageIcon(imagePath);
        JLabel lblImage = new JLabel(icon);
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setPreferredSize(new Dimension(100, 100));
        itemPanel.add(lblImage, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JLabel lblName = new JLabel(name, SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(lblName, BorderLayout.CENTER);

        JButton btnOrder = new JButton("Đặt món");
        btnOrder.addActionListener(e -> showOrderDialog(name, price));
        bottomPanel.add(btnOrder, BorderLayout.SOUTH);

        itemPanel.add(bottomPanel, BorderLayout.SOUTH);
        menuPanel.add(itemPanel);
    }

    private void showOrderDialog(String name, int price) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Đặt món", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));

        dialog.add(new JLabel("Tên món:"));
        dialog.add(new JLabel(name));

        dialog.add(new JLabel("Đơn giá:"));
        dialog.add(new JLabel(price + " VND"));

        dialog.add(new JLabel("Số lượng:"));
        JTextField txtQuantity = new JTextField();
        dialog.add(txtQuantity);

        JButton btnConfirm = new JButton("Đặt");
        btnConfirm.addActionListener(e -> {
            try {
                int quantity = Integer.parseInt(txtQuantity.getText());
                if (quantity <= 0) throw new NumberFormatException();

                int total = price * quantity;
                orderTableModel.addRow(new Object[]{name, price, quantity, total});
                totalPrice += total;
                lblTotalPrice.setText("Thành tiền: " + totalPrice + " VND");
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập số lượng hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        dialog.add(btnConfirm);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.add(btnCancel);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
