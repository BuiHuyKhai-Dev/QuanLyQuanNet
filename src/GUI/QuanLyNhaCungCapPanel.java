package GUI;

import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class QuanLyNhaCungCapPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable supplierTable;
    private NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();

    public QuanLyNhaCungCapPanel() {
        setLayout(new BorderLayout());

        // Thanh menu phía trên
        JPanel topPanel = new JPanel(new BorderLayout());

        // Các nút chức năng phía trái
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Thêm");
        JButton btnDelete = new JButton("Xóa");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDetail = new JButton("Chi tiết");
        leftPanel.add(btnAdd);
        leftPanel.add(btnDelete);
        leftPanel.add(btnEdit);
        leftPanel.add(btnDetail);

        // ComboBox sắp xếp và ô tìm kiếm phía phải
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"Sắp xếp theo tên", "Sắp xếp theo thời gian tạo"});
        JTextField searchField = new JTextField(15);
        JButton btnSearch = new JButton("Tìm kiếm");
        rightPanel.add(sortComboBox);
        rightPanel.add(searchField);
        rightPanel.add(btnSearch);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Bảng dữ liệu phía dưới
        String[] columnNames = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Số điện thoại", "Email", "Thời gian tạo"};
        tableModel = new DefaultTableModel(columnNames, 0);
        supplierTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(supplierTable);
        add(scrollPane, BorderLayout.CENTER);
        loadSupplierData(); // Tải dữ liệu nhà cung cấp vào bảng

        // Đặt chiều cao dòng và header
        supplierTable.setRowHeight(30); // Chiều cao các hàng
        JTableHeader header = supplierTable.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25)); // Chiều cao header

        // Căn giữa chữ trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        supplierTable.setDefaultRenderer(Object.class, centerRenderer);

        // Tô màu nền xen kẽ cho các dòng
        supplierTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE); // Màu nền xen kẽ
                }
                setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa chữ
                return c;
            }
        });

        // Xử lý sự kiện các nút chức năng
        btnAdd.addActionListener(e -> showAddDialog());
        btnDelete.addActionListener(e -> showDeleteDialog());
        btnEdit.addActionListener(e -> showEditDialog());
        btnDetail.addActionListener(e -> showDetailDialog());
    }

    private void loadSupplierData() {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Lấy danh sách nhà cung cấp từ NhaCungCapBUS
        ArrayList<NhaCungCapDTO> danhSachNhaCungCap = nhaCungCapBUS.getNhaCungCapAll();

        // Đổ dữ liệu vào bảng
        for (NhaCungCapDTO ncc : danhSachNhaCungCap) {
            tableModel.addRow(new Object[]{
                ncc.getMaNhaCungCap(),
                ncc.getTenNhaCungCap(),
                ncc.getDiaChi(),
                ncc.getSoDienThoai(),
                ncc.getEmail(),
                ncc.getThoiGianTao()
            });
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm nhà cung cấp", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        dialog.add(new JLabel("Tên nhà cung cấp:"));
        JTextField txtName = new JTextField();
        dialog.add(txtName);

        dialog.add(new JLabel("Địa chỉ:"));
        JTextField txtAddress = new JTextField();
        dialog.add(txtAddress);

        dialog.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField();
        dialog.add(txtPhone);

        dialog.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField();
        dialog.add(txtEmail);

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            String name = txtName.getText();
            String address = txtAddress.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();

            // Lấy thời gian hiện tại
            String createdTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Thêm dữ liệu vào bảng
            tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, name, address, phone, email, createdTime});
            dialog.dispose();
        });
        dialog.add(btnConfirm);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.add(btnCancel);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showDeleteDialog() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhà cung cấp này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
        }
    }

    private void showEditDialog() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa nhà cung cấp", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        dialog.add(new JLabel("Tên nhà cung cấp:"));
        JTextField txtName = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        dialog.add(txtName);

        dialog.add(new JLabel("Địa chỉ:"));
        JTextField txtAddress = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        dialog.add(txtAddress);

        dialog.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField((String) tableModel.getValueAt(selectedRow, 3));
        dialog.add(txtPhone);

        dialog.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField((String) tableModel.getValueAt(selectedRow, 4));
        dialog.add(txtEmail);

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            tableModel.setValueAt(txtName.getText(), selectedRow, 1);
            tableModel.setValueAt(txtAddress.getText(), selectedRow, 2);
            tableModel.setValueAt(txtPhone.getText(), selectedRow, 3);
            tableModel.setValueAt(txtEmail.getText(), selectedRow, 4);
            dialog.dispose();
        });
        dialog.add(btnConfirm);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.add(btnCancel);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showDetailDialog() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết nhà cung cấp", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        dialog.add(new JLabel("Tên nhà cung cấp:"));
        JTextField txtName = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        txtName.setEditable(false);
        dialog.add(txtName);

        dialog.add(new JLabel("Địa chỉ:"));
        JTextField txtAddress = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        txtAddress.setEditable(false);
        dialog.add(txtAddress);

        dialog.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField((String) tableModel.getValueAt(selectedRow, 3));
        txtPhone.setEditable(false);
        dialog.add(txtPhone);

        dialog.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField((String) tableModel.getValueAt(selectedRow, 4));
        txtEmail.setEditable(false);
        dialog.add(txtEmail);

        dialog.add(new JLabel("Thời gian tạo:"));
        JTextField txtCreatedTime = new JTextField((String) tableModel.getValueAt(selectedRow, 5));
        txtCreatedTime.setEditable(false);
        dialog.add(txtCreatedTime);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dialog.dispose());
        dialog.add(btnClose);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}