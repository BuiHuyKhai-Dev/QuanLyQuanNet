package GUI.QLDA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import BUS.HoaDonThucAnBUS;
import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import BUS.ThucAnBUS;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

public class datdoan extends JPanel {
    private DefaultTableModel orderTableModel;
    private JLabel lblTotalPrice;
    private int totalPrice = 0;

    JButton btnChonNV = new JButton("Chọn NV");
    private JTextField txtMaNV= new JTextField();
    private JTextField txtMaMay= new JTextField();
    private JTextField txtMaKH= new JTextField();
    JButton btnChonKH = new JButton("Chọn KH");
    private HoaDonThucAnBUS hoaDonBUS = new HoaDonThucAnBUS();

    public datdoan() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        inputPanel.add(new JLabel("Mã Khách Hàng:"));
        
        JPanel temp1= new JPanel();
        temp1.add(txtMaKH);
        temp1.add(btnChonKH);
        btnChonKH.addActionListener(e -> showChonKhachHangDialog());
        inputPanel.add(temp1);
        txtMaKH.setPreferredSize(new Dimension(100, 25));
        
        inputPanel.add(new JLabel("Mã Máy:"));
        txtMaMay = new JTextField(10);
        inputPanel.add(txtMaMay);

        inputPanel.add(new JLabel("Mã Nhân Viên:"));
        
        JPanel temp2= new JPanel();
        temp2.add(txtMaNV);
        temp2.add(btnChonNV);
        btnChonNV.addActionListener(e -> showChonNhanVienDialog());
        inputPanel.add(temp2);
        txtMaNV.setPreferredSize(new Dimension(100, 25));

        add(inputPanel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        BUS.ThucAnBUS thucAnBUS = new BUS.ThucAnBUS();
        ArrayList<DTO.ThucAnDTO> danhSachThucAn = thucAnBUS.getAllThucAn();

        for (DTO.ThucAnDTO ta : danhSachThucAn) {
            String ten = ta.getTenThucAn();
            // String hinhAnh = ta.getHinhAnh(); // Đảm bảo ảnh là đường dẫn hợp lệ ví dụ: "../../img/comga.jpg"
            double gia = ta.getDonGia();
            addMenuItem(menuPanel, ten, "../../img/comga.jpg", gia);
        }

        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        menuScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        menuScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        orderTableModel = new DefaultTableModel(new Object[]{"Tên món", "Đơn giá", "Số lượng", "Thành tiền", "Hủy"}, 0);
        JTable orderTable = new JTable(orderTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // chỉ cột "Hủy" cho phép tương tác
            }
        };

        // Gán renderer và editor cho cột "Hủy"
        orderTable.getColumn("Hủy").setCellRenderer(new ButtonRenderer());
        orderTable.getColumn("Hủy").setCellEditor(new ButtonEditor(new JCheckBox(), orderTable));

        JScrollPane scrollPane = new JScrollPane(orderTable);
        orderPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        lblTotalPrice = new JLabel("Thành tiền: 0 VND", SwingConstants.RIGHT);
        lblTotalPrice.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(lblTotalPrice, BorderLayout.CENTER);

        JButton btnCheckout = new JButton("Thanh toán");
        btnCheckout.addActionListener(e -> {
            try {
                int maKH = Integer.parseInt(txtMaKH.getText());
                int maMay = Integer.parseInt(txtMaMay.getText().trim());
                int maNV = Integer.parseInt(txtMaNV.getText().trim());

                Timestamp now = new Timestamp(System.currentTimeMillis());

                boolean success = hoaDonBUS.themHoaDon(maKH, maMay, now, totalPrice, 1, maNV);

                if (success) {
                    // Giả sử bạn có BUS để thêm chi tiết:
                    HoaDonThucAnBUS ctBUS = new HoaDonThucAnBUS();
                    int maHD = hoaDonBUS.layMaDonHangCuoi(); // Phải có hàm này

                    for (int i = 0; i < orderTableModel.getRowCount(); i++) {
                        String tenMon = (String) orderTableModel.getValueAt(i, 0);
                        int donGia = (int) orderTableModel.getValueAt(i, 1);
                        int soLuong = (int) orderTableModel.getValueAt(i, 2);
                        // int thanhTien = (int) orderTableModel.getValueAt(i, 3);

                        // Giả sử bạn có thể lấy mã món từ tên món (nếu không thì cần sửa lại)
                        int maMon = new ThucAnBUS().getMaTheoTenMon(tenMon); // Phải có hàm này nếu cần
                        System.out.println("Mã món: " + maMon);
                        DTO.ChiTietDonHangDTO ct = new DTO.ChiTietDonHangDTO();
                        ct.setMaDH(maHD);
                        ct.setMaThucAn(maMon);
                        ct.setSoLuong(soLuong);
                        ct.setDonGia(donGia);
                        // ct.se

                        ctBUS.themChiTietDonHang(ct);
                    }

                    JOptionPane.showMessageDialog(this, "✅ Đặt đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    orderTableModel.setRowCount(0);
                    totalPrice = 0;
                    lblTotalPrice.setText("Thành tiền: 0 VND");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Không thể đặt đơn. Kiểm tra thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Vui lòng nhập đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    private void showChonKhachHangDialog() {
    KhachHangBUS khBUS = new KhachHangBUS();
    ArrayList<KhachHangDTO> ds = khBUS.getKhachHangAll();

    JDialog dialog = new JDialog((JFrame)null, "Chọn Khách Hàng", true);
    dialog.setSize(600, 300);
    dialog.setLayout(new BorderLayout());

    String[] columnNames = {"Mã KH", "Tên KH", "SĐT", "Email"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    for (KhachHangDTO kh : ds) {
        model.addRow(new Object[]{kh.getMaKhachHang(), kh.getTenKhachHang(), kh.getSoDienThoai(), kh.getEmail()});
    }

    JTable table = new JTable(model);
    dialog.add(new JScrollPane(table), BorderLayout.CENTER);

    JButton btnChon = new JButton("Chọn");
    btnChon.addActionListener(e -> {
        int row = table.getSelectedRow();
        if (row != -1) {
            int maKH = (int) table.getValueAt(row, 0);
            txtMaKH.setText(String.valueOf(maKH));
            dialog.dispose();
        }
    });
    dialog.add(btnChon, BorderLayout.SOUTH);

    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}


    private void showChonNhanVienDialog() {
    NhanVienBUS nvBUS = new NhanVienBUS();
    ArrayList<NhanVienDTO> ds = nvBUS.getNhanVienAll();

    JDialog dialog = new JDialog((JFrame)null, "Chọn Nhân Viên", true);
    dialog.setSize(600, 300);
    dialog.setLayout(new BorderLayout());

    String[] columnNames = {"Mã NV", "Tên NV", "SĐT", "Email"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    for (NhanVienDTO nv : ds) {
        model.addRow(new Object[]{nv.getMaNV(), nv.getTenNV(), nv.getSoDT(), nv.getEmail()});
    }

    JTable table = new JTable(model);
    dialog.add(new JScrollPane(table), BorderLayout.CENTER);

    JButton btnChon = new JButton("Chọn");
    btnChon.addActionListener(e -> {
        int row = table.getSelectedRow();
        if (row != -1) {
            String maNV = (String) table.getValueAt(row, 0);
            txtMaNV.setText(maNV);
            dialog.dispose();
        }
    });
    dialog.add(btnChon, BorderLayout.SOUTH);

    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}


    private void addMenuItem(JPanel menuPanel, String name, String imagePath, double price) {
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

    private void showOrderDialog(String name, double price) {
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

                double total = price * quantity;
                orderTableModel.addRow(new Object[]{name, price, quantity, total, "Hủy"});
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

    // Renderer cho nút trong bảng
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Hủy");
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Editor xử lý khi ấn nút "Hủy"
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;
            button = new JButton("Hủy");
            button.addActionListener(e -> {
                int row = table.getEditingRow();
                double thanhTien = (double) table.getValueAt(row, 3);
                totalPrice -= thanhTien;
                lblTotalPrice.setText("Thành tiền: " + totalPrice + " VND");
                ((DefaultTableModel) table.getModel()).removeRow(row);
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Hủy";
        }
    }
}
