package GUI;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class QuanLyKhachHangPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable customerTable;
    private KhachHangBUS khachHangBUS;

    public QuanLyKhachHangPanel() {
        khachHangBUS = new KhachHangBUS(); // Khởi tạo KhachHangBUS
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
        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"Tất cả","Sắp xếp theo tên", "Sắp xếp theo số dư"});
        JTextField searchField = new JTextField(15);
        JButton btnSearch = new JButton("Tìm kiếm");
        rightPanel.add(sortComboBox);
        rightPanel.add(searchField);
        rightPanel.add(btnSearch);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Bảng dữ liệu phía dưới
        String[] columnNames = {"Mã Khách Hàng", "Tên Khách Hàng", "Số điện thoại", "Email", "Số dư tài khoản", "Thời gian tạo"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(tableModel);

        // Đặt chiều cao dòng và header
        customerTable.setRowHeight(30);
        JTableHeader header = customerTable.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25));

        // Căn giữa chữ trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        customerTable.setDefaultRenderer(Object.class, centerRenderer);

        // Tô màu nền xen kẽ cho các dòng
        customerTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(scrollPane, BorderLayout.CENTER);

        customerTable.setDefaultEditor(Object.class, null);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Chọn 1 hàng
        customerTable.setRowSelectionAllowed(true); // Cho phép chọn hàng
        customerTable.setColumnSelectionAllowed(false); // Không cho phép chọn cột
        customerTable.setCellSelectionEnabled(false);
        customerTable.setRowSelectionAllowed(true);
        customerTable.setFocusable(false); // Không cho phép chọn ô

        // Lấy dữ liệu khách hàng từ KhachHangBUS và đổ vào bảng
        loadCustomerData();

        // Xử lý sự kiện các nút chức năng
        btnAdd.addActionListener(e -> showAddDialog());
        btnDelete.addActionListener(e -> showDeleteDialog());
        btnEdit.addActionListener(e -> showEditDialog());
        btnDetail.addActionListener(e -> showDetailDialog());
        btnSearch.addActionListener(e -> searchCustomer(searchField.getText()));
        sortComboBox.addActionListener(e -> {
            String selectedItem = (String) sortComboBox.getSelectedItem();
            if (selectedItem != null) {
                if (selectedItem.equals("Sắp xếp theo tên")) {
                    // Sắp xếp theo tên
                    tableModel.setRowCount(0);
                    List<KhachHangDTO> danhSachKhachHang = khachHangBUS.sortByName();
                    for (KhachHangDTO kh : danhSachKhachHang) {
                        tableModel.addRow(new Object[]{
                            kh.getMaKhachHang(),
                            kh.getTenKhachHang(),
                            kh.getSoDienThoai(),
                            kh.getEmail(),
                            kh.getSoDuTaiKhoan(),
                            kh.getThoiGianTao()
                        });
                    }
                } else if (selectedItem.equals("Sắp xếp theo số dư")) {
                    // Sắp xếp theo số dư
                    tableModel.setRowCount(0);
                    List<KhachHangDTO> danhSachKhachHang = khachHangBUS.sortByBalance();
                    for (KhachHangDTO kh : danhSachKhachHang) {
                        tableModel.addRow(new Object[]{
                            kh.getMaKhachHang(),
                            kh.getTenKhachHang(),
                            kh.getSoDienThoai(),
                            kh.getEmail(),
                            kh.getSoDuTaiKhoan(),
                            kh.getThoiGianTao()
                        });
                    }
                }   else if (selectedItem.equals("Tất cả")) {
                    // Hiển thị tất cả khách hàng
                    tableModel.setRowCount(0);
                    List<KhachHangDTO> danhSachKhachHang = khachHangBUS.sortByID();
                    for (KhachHangDTO kh : danhSachKhachHang) {
                        tableModel.addRow(new Object[]{
                            kh.getMaKhachHang(),
                            kh.getTenKhachHang(),
                            kh.getSoDienThoai(),
                            kh.getEmail(),
                            kh.getSoDuTaiKhoan(),
                            kh.getThoiGianTao()
                        });
                    }
                }
            }
        });
    }

    private void showDeleteDialog() {
    int selectedRow = customerTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        int maKhachHang = (int) tableModel.getValueAt(selectedRow, 0);
        // Xóa khách hàng trong danh sách
        if(khachHangBUS.deleteById(maKhachHang)) {
            JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        // Cập nhật lại bảng
        loadCustomerData();
    }
    }

    private void showEditDialog() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa khách hàng", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        dialog.add(new JLabel("Tên Khách Hàng:"));
        JTextField txtName = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
        dialog.add(txtName);

        dialog.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField(tableModel.getValueAt(selectedRow, 2).toString());
        dialog.add(txtPhone);

        dialog.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField(tableModel.getValueAt(selectedRow, 3).toString());
        dialog.add(txtEmail);

        dialog.add(new JLabel("Số dư tài khoản:"));
        JTextField txtBalance = new JTextField(tableModel.getValueAt(selectedRow, 4).toString());
        dialog.add(txtBalance);

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            int maKhachHang = (int) tableModel.getValueAt(selectedRow, 0);
            String name = txtName.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            double balance = Double.parseDouble(txtBalance.getText());

            // Kiểm tra dữ liệu đầu vào
            if (!validateInput(name, phone, email, String.valueOf(balance))) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Nếu dữ liệu không hợp lệ, không thực hiện cập nhật
            }

            // Cập nhật khách hàng trong danh sách
            KhachHangDTO updatedCustomer = new KhachHangDTO(maKhachHang, name, phone, email, balance, null);

            if(khachHangBUS.updateKhachHang_DB(updatedCustomer)) {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            // Cập nhật lại bảng
            tableModel.setValueAt(name, selectedRow, 1); // Cập nhật tên
            tableModel.setValueAt(phone, selectedRow, 2); // Cập nhật số điện thoại
            tableModel.setValueAt(email, selectedRow, 3); // Cập nhật email
            tableModel.setValueAt(balance, selectedRow, 4);
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
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết khách hàng", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tên khách hàng
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Tên Khách Hàng:"), gbc);

        gbc.gridx = 1;
        JTextField txtName = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
        txtName.setEditable(false);
        dialog.add(txtName, gbc);

        // Số điện thoại
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Số điện thoại:"), gbc);

        gbc.gridx = 1;
        JTextField txtPhone = new JTextField(tableModel.getValueAt(selectedRow, 2).toString());
        txtPhone.setEditable(false);
        dialog.add(txtPhone, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        JTextField txtEmail = new JTextField(tableModel.getValueAt(selectedRow, 3).toString());
        txtEmail.setEditable(false);
        dialog.add(txtEmail, gbc);

        // Số dư tài khoản
        gbc.gridx = 0;
        gbc.gridy = 3;
        dialog.add(new JLabel("Số dư tài khoản:"), gbc);

        gbc.gridx = 1;
        JTextField txtBalance = new JTextField(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
        txtBalance.setEditable(false);
        dialog.add(txtBalance, gbc);

        // Thời gian tạo
        gbc.gridx = 0;
        gbc.gridy = 4;
        dialog.add(new JLabel("Thời gian tạo:"), gbc);

        gbc.gridx = 1;
        JTextField txtCreatedTime = new JTextField(tableModel.getValueAt(selectedRow, 5).toString());
        txtCreatedTime.setEditable(false);
        dialog.add(txtCreatedTime, gbc);

        // Nút đóng
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dialog.dispose());
        dialog.add(btnClose, gbc);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void loadCustomerData() {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Lấy danh sách khách hàng từ KhachHangBUS
        List<KhachHangDTO> danhSachKhachHang = khachHangBUS.getKhachHangAll();

        // Đổ dữ liệu vào bảng
        for (KhachHangDTO kh : danhSachKhachHang) {
            tableModel.addRow(new Object[]{
                kh.getMaKhachHang(),
                kh.getTenKhachHang(),
                kh.getSoDienThoai(),
                kh.getEmail(),
                kh.getSoDuTaiKhoan(),
                kh.getThoiGianTao()
            });
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm khách hàng", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        dialog.add(new JLabel("Tên Khách Hàng:"));
        JTextField txtName = new JTextField();
        dialog.add(txtName);

        dialog.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField();
        dialog.add(txtPhone);

        dialog.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField();
        dialog.add(txtEmail);

        dialog.add(new JLabel("Số dư tài khoản:"));
        JTextField txtBalance = new JTextField();
        dialog.add(txtBalance);

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            String name = txtName.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            Double balance = Double.parseDouble(txtBalance.getText());

            // Kiểm tra dữ liệu đầu vào
            if (!validateInput(name, phone, email, String.valueOf(balance))) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Nếu dữ liệu không hợp lệ, không thực hiện thêm
            }

            // Lấy thời gian hiện tại
            String createdTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Thêm khách hàng mới vào danh sách
            KhachHangDTO newCustomer = new KhachHangDTO(
                khachHangBUS.getLastID(), name, phone, email, balance, createdTime
            );

            if(khachHangBUS.add(newCustomer)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            // Cập nhật lại bảng
            loadCustomerData();
            dialog.dispose();
        });
        dialog.add(btnConfirm);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.add(btnCancel);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void searchCustomer(String keyword) {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Lấy danh sách khách hàng theo từ khóa
        List<KhachHangDTO> danhSachKhachHang = khachHangBUS.searchName(keyword);

        // Đổ dữ liệu vào bảng
        for (KhachHangDTO kh : danhSachKhachHang) {
            tableModel.addRow(new Object[]{
                kh.getMaKhachHang(),
                kh.getTenKhachHang(),
                kh.getSoDienThoai(),
                kh.getEmail(),
                kh.getSoDuTaiKhoan(),
                kh.getThoiGianTao()
            });
        }
    }

    private boolean validateInput(String name, String phone, String email, String balance) {
        // Kiểm tra tên không được để trống
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra số điện thoại (chỉ chứa số và độ dài hợp lệ)
        if (!phone.matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải là số và có độ dài từ 10 đến 11 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra email (định dạng hợp lệ)
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra số dư tài khoản (phải là số dương)
        try {
            double balanceValue = Double.parseDouble(balance);
            if (balanceValue < 0) {
                JOptionPane.showMessageDialog(this, "Số dư tài khoản phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số dư tài khoản phải là một số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Nếu tất cả kiểm tra đều đúng
        return true;
    }
}