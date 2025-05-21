package GUI;

import BUS.KhachHangBUS;
import BUS.TaiKhoanBUS;
import DTO.KhachHangDTO;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class QuanLyKhachHangPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable customerTable;
    private KhachHangBUS khachHangBUS;
    private JComboBox<String> sortComboBox;

    public QuanLyKhachHangPanel() {
        khachHangBUS = new KhachHangBUS();
        setLayout(new BorderLayout());

        // Thanh menu phía trên
        JPanel topPanel = new JPanel(new BorderLayout());

        // Các nút chức năng phía trái
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Thêm");
        JButton btnDelete = new JButton("Xóa");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDetail = new JButton("Chi tiết");
        JButton btnRefresh = new JButton("Làm mới");
        leftPanel.add(btnAdd);
        leftPanel.add(btnDelete);
        leftPanel.add(btnEdit);
        leftPanel.add(btnDetail);
        leftPanel.add(btnRefresh);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sortComboBox = new JComboBox<>(new String[]{"Tất cả","Sắp xếp theo tên", "Sắp xếp theo số dư", "Khách hàng bị xóa"});
        JTextField searchField = new JTextField(15);
        JButton btnSearch = new JButton("Tìm kiếm");
        JLabel lblFromDate = new JLabel("Từ:");
        JDateChooser fromDateChooser = new JDateChooser();
        fromDateChooser.setDateFormatString("yyyy-MM-dd");
        JLabel lblToDate = new JLabel("Đến:");
        JDateChooser toDateChooser = new JDateChooser();
        toDateChooser.setDateFormatString("yyyy-MM-dd");
        JButton btnFilter = new JButton("Lọc");

        rightPanel.add(sortComboBox);
        rightPanel.add(searchField);
        rightPanel.add(btnSearch);
        rightPanel.add(lblFromDate);
        rightPanel.add(fromDateChooser);
        rightPanel.add(lblToDate);
        rightPanel.add(toDateChooser);
        rightPanel.add(btnFilter);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Bảng dữ liệu phía dưới (KHÔNG có cột trạng thái)
        String[] columnNames = {
            "Mã Khách Hàng", "Họ", "Tên", "Số điện thoại", "Email", "Số dư tài khoản", "Mật khẩu", "Thời gian tạo", "Nạp tiền"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho phép cột "Nạp tiền" được chỉnh sửa (bấm nút)
                if (column == 8) {
                    Object value = getValueAt(row, column);
                    return value != null && value.toString().equals("Nạp tiền");
                }
                return false; // Các cột khác không cho phép chỉnh sửa
            }
        };
        customerTable = new JTable(tableModel);

        // Đặt chiều cao dòng và header
        customerTable.setRowHeight(30);
        JTableHeader header = customerTable.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25));

        // 3. Tạo cell renderer và editor cho cột "Nạp tiền"
        customerTable.getColumn("Nạp tiền").setCellRenderer(new ButtonRenderer());
        customerTable.getColumn("Nạp tiền").setCellEditor(new ButtonEditor(new JCheckBox(), this, khachHangBUS));

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
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(scrollPane, BorderLayout.CENTER);

        customerTable.setDefaultEditor(Object.class, null);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Lấy dữ liệu khách hàng từ KhachHangBUS và đổ vào bảng
        loadCustomerData();

        // Xử lý sự kiện các nút chức năng
        btnAdd.addActionListener(e -> showAddDialog());
        btnDelete.addActionListener(e -> showDeleteDialog());
        btnEdit.addActionListener(e -> showEditDialog());
        btnDetail.addActionListener(e -> showDetailDialog());
        btnRefresh.addActionListener(e -> 
            {
                loadCustomerData();
                searchField.setText("");
                fromDateChooser.setDate(null);
                toDateChooser.setDate(null);
            }
        );
        btnSearch.addActionListener(e -> searchCustomer(searchField.getText()));
        sortComboBox.addActionListener(e -> {
            String selectedItem = (String) sortComboBox.getSelectedItem();
            if (selectedItem != null) {
                if (selectedItem.equals("Sắp xếp theo tên")) {
                    tableModel.setRowCount(0);
                    List<KhachHangDTO> danhSachKhachHang = khachHangBUS.sortByName();
                    for (KhachHangDTO kh : danhSachKhachHang) {
                        if (kh.getTrangThai() == 1) {
                            tableModel.addRow(new Object[]{
                                kh.getMaKhachHang(),
                                kh.getHoKhachHang(),
                                kh.getTenKhachHang(),
                                kh.getSoDienThoai(),
                                kh.getEmail(),
                                kh.getSoDuTaiKhoan(),
                                kh.getMatKhau(),
                                kh.getThoiGianTao(),
                                "Nạp tiền"
                            });
                        }
                    }
                } else if (selectedItem.equals("Sắp xếp theo số dư")) {
                    tableModel.setRowCount(0);
                    List<KhachHangDTO> danhSachKhachHang = khachHangBUS.sortByBalance();
                    for (KhachHangDTO kh : danhSachKhachHang) {
                        if (kh.getTrangThai() == 1) {
                            tableModel.addRow(new Object[]{
                                kh.getMaKhachHang(),
                                kh.getHoKhachHang(),
                                kh.getTenKhachHang(),
                                kh.getSoDienThoai(),
                                kh.getEmail(),
                                kh.getSoDuTaiKhoan(),
                                kh.getMatKhau(),
                                kh.getThoiGianTao(),
                                "Nạp tiền"
                            });
                        }
                    }
                } else if (selectedItem.equals("Khách hàng bị xóa")) {
                    tableModel.setRowCount(0);
                    List<KhachHangDTO> danhSachKhachHang = khachHangBUS.getKhachHangAll();
                    for (KhachHangDTO kh : danhSachKhachHang) {
                        if (kh.getTrangThai() == 0) { // Chỉ hiển thị khách hàng đã bị xóa
                            tableModel.addRow(new Object[]{
                                kh.getMaKhachHang(),
                                kh.getHoKhachHang(),
                                kh.getTenKhachHang(),
                                kh.getSoDienThoai(),
                                kh.getEmail(),
                                kh.getSoDuTaiKhoan(),
                                kh.getMatKhau(),
                                kh.getThoiGianTao(),
                                "Không khả dụng"
                            });
                        }
                    }
                    customerTable.getColumn("Nạp tiền").setCellEditor(null);
                }
                else if (selectedItem.equals("Tất cả")) {
                    loadCustomerData();
                    customerTable.getColumn("Nạp tiền").setCellEditor(new ButtonEditor(new JCheckBox(), this, khachHangBUS));
                }
            }
        });
        btnFilter.addActionListener(e -> {
            String fromDate = fromDateChooser.getDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(fromDateChooser.getDate()) : "";
            String toDate = toDateChooser.getDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(toDateChooser.getDate()) : "";

            if (fromDate.isEmpty() || toDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn cả ngày bắt đầu và ngày kết thúc!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ArrayList<KhachHangDTO> filteredList = khachHangBUS.filterByDate(fromDate, toDate);
            tableModel.setRowCount(0);
            for (KhachHangDTO kh : filteredList) {
                if (kh.getTrangThai() == 1) {
                    tableModel.addRow(new Object[]{
                        kh.getMaKhachHang(),
                        kh.getHoKhachHang(),
                        kh.getTenKhachHang(),
                        kh.getSoDienThoai(),
                        kh.getEmail(),
                        kh.getSoDuTaiKhoan(),
                        kh.getMatKhau(),
                        kh.getThoiGianTao(),
                        "Nạp tiền"
                    });
                }
            }
        });
    }

    private void loadCustomerData() {
        KhachHangBUS khachHangBUS2 = new KhachHangBUS();
        tableModel.setRowCount(0);
        List<KhachHangDTO> danhSachKhachHang = khachHangBUS2.getKhachHangAll();
        for (KhachHangDTO kh : danhSachKhachHang) {
            if (kh.getTrangThai() == 1) { // Chỉ hiển thị khách hàng đang hoạt động
                tableModel.addRow(new Object[]{
                    kh.getMaKhachHang(),
                    kh.getHoKhachHang(),
                    kh.getTenKhachHang(),
                    kh.getSoDienThoai(),
                    kh.getEmail(),
                    kh.getSoDuTaiKhoan(),
                    kh.getMatKhau(),
                    kh.getThoiGianTao(),
                    "Nạp tiền"
                });
            }
        }
    customerTable.getColumn("Nạp tiền").setCellEditor(new ButtonEditor(new JCheckBox(), this, khachHangBUS));
    }

    // Các hàm showAddDialog, showEditDialog, showDetailDialog, showDeleteDialog, searchCustomer
    // cần cập nhật lại để phù hợp với các cột mới (KHÔNG có cột trạng thái)

    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm khách hàng", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new GridLayout(7, 2, 10, 10));

        dialog.add(new JLabel("Họ:"));
        JTextField txtHo = new JTextField();
        dialog.add(txtHo);

        dialog.add(new JLabel("Tên:"));
        JTextField txtTen = new JTextField();
        dialog.add(txtTen);

        dialog.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField();
        dialog.add(txtPhone);

        dialog.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField();
        dialog.add(txtEmail);

        dialog.add(new JLabel("Số dư tài khoản:"));
        JTextField txtBalance = new JTextField();
        dialog.add(txtBalance);

        dialog.add(new JLabel("Mật khẩu:"));
        JTextField txtMatKhau = new JTextField();
        dialog.add(txtMatKhau);

        // Không cho nhập trạng thái, mặc định là 1 khi thêm mới

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            String ho = txtHo.getText();
            String ten = txtTen.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String matKhau = txtMatKhau.getText();
            Double balance;
            try {
                balance = Double.parseDouble(txtBalance.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Số dư tài khoản không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!validateInput(ho, ten, phone, email, matKhau, "1", String.valueOf(balance))) {
                return;
            }

            String createdTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            KhachHangDTO newCustomer = new KhachHangDTO(
                khachHangBUS.getLastID(), ho, ten, phone, email, balance, matKhau, 1, createdTime
            );

            if (khachHangBUS.add(newCustomer)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadCustomerData();
                TaiKhoanBUS tkBUS = new TaiKhoanBUS();
                tkBUS.insertKhach(email, matKhau);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        dialog.add(btnConfirm);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.add(btnCancel);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showEditDialog() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa khách hàng", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new GridLayout(8, 2, 10, 10)); // 8 dòng, 2 cột

        dialog.add(new JLabel("Họ:"));
        JTextField txtHo = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
        dialog.add(txtHo);

        dialog.add(new JLabel("Tên:"));
        JTextField txtTen = new JTextField(tableModel.getValueAt(selectedRow, 2).toString());
        dialog.add(txtTen);

        dialog.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField(tableModel.getValueAt(selectedRow, 3).toString());
        dialog.add(txtPhone);

        dialog.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField(tableModel.getValueAt(selectedRow, 4).toString());
        dialog.add(txtEmail);

        dialog.add(new JLabel("Số dư tài khoản:"));
        JTextField txtBalance = new JTextField(tableModel.getValueAt(selectedRow, 5).toString());
        dialog.add(txtBalance);

        dialog.add(new JLabel("Mật khẩu:"));
        JTextField txtMatKhau = new JTextField(tableModel.getValueAt(selectedRow, 6).toString());
        dialog.add(txtMatKhau);

        dialog.add(new JLabel("Trạng thái (1: hoạt động, 0: bị xóa):"));
        int maKhachHang = (int) tableModel.getValueAt(selectedRow, 0);
        KhachHangDTO kh = new KhachHangBUS().getKhachHangById(maKhachHang);
        JTextField txtTrangThai = new JTextField(kh != null ? String.valueOf(kh.getTrangThai()) : "1");
        dialog.add(txtTrangThai);

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            String ho = txtHo.getText();
            String ten = txtTen.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String matKhau = txtMatKhau.getText();
            String trangThai = txtTrangThai.getText();
            Double balance;
            try {
                balance = Double.parseDouble(txtBalance.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Số dư tài khoản không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!validateInput(ho, ten, phone, email, matKhau, trangThai, String.valueOf(balance))) {
                return;
            }

            KhachHangDTO updatedCustomer = new KhachHangDTO(
                maKhachHang, ho, ten, phone, email, balance, matKhau, Integer.parseInt(trangThai), tableModel.getValueAt(selectedRow, 7).toString()
            );

            if (khachHangBUS.updateKhachHang_DB(updatedCustomer)) {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadCustomerData();

                customerTable.getColumn("Nạp tiền").setCellEditor(new ButtonEditor(new JCheckBox(), this, khachHangBUS));
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            sortComboBox.setSelectedItem("Tất cả");
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
        dialog.setSize(500, 500);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Mã KH:", "Họ:", "Tên:", "Số điện thoại:", "Email:", "Số dư tài khoản:", "Mật khẩu:", "Thời gian tạo:"};
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            dialog.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            JTextField txt = new JTextField(tableModel.getValueAt(selectedRow, i).toString());
            txt.setEditable(false);
            dialog.add(txt, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dialog.dispose());
        dialog.add(btnClose, gbc);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showDeleteDialog() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maKhachHang = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (khachHangBUS.deleteKhachHang(maKhachHang)) {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                
                loadCustomerData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchCustomer(String keyword) {
        tableModel.setRowCount(0);
        List<KhachHangDTO> danhSachKhachHang = khachHangBUS.searchName(keyword);
        for (KhachHangDTO kh : danhSachKhachHang) {
            if (kh.getTrangThai() == 1) {
                tableModel.addRow(new Object[]{
                    kh.getMaKhachHang(),
                    kh.getHoKhachHang(),
                    kh.getTenKhachHang(),
                    kh.getSoDienThoai(),
                    kh.getEmail(),
                    kh.getSoDuTaiKhoan(),
                    kh.getMatKhau(),
                    kh.getThoiGianTao(),
                    "Nạp tiền"
                });
            }
        }
    }

    private boolean validateInput(String ho, String ten, String phone, String email, String matKhau, String trangThai, String balance) {
        if (ho == null || ho.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ khách hàng không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (ten == null || ten.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!phone.matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải là số và có độ dài từ 10 đến 11 ký tự!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
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
        if (matKhau == null || matKhau.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int t = Integer.parseInt(trangThai);
            if (t != 0 && t != 1) {
                JOptionPane.showMessageDialog(this, "Trạng thái chỉ nhận giá trị 0 (khóa) hoặc 1 (hoạt động)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Trạng thái phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}

class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value == null ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private int selectedRow;
    private Component parentComponent;
    private KhachHangBUS khachHangBUS;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, Component parentComponent, KhachHangBUS khachHangBUS) {
        super(checkBox);
        this.parentComponent = parentComponent;
        this.khachHangBUS = khachHangBUS;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        selectedRow = row;
        this.table = table; // Lưu lại bảng để sử dụng sau này
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            Object value = tableModel.getValueAt(selectedRow, 8); // cột "Nạp tiền"
            if (value != null && value.toString().equals("Không khả dụng")) {
                // Không làm gì nếu là khách hàng bị xóa
                isPushed = false;
                return label;
            }
            String input = JOptionPane.showInputDialog(parentComponent, "Nhập số tiền cần nạp:", "Nạp tiền", JOptionPane.PLAIN_MESSAGE);

            double soDuHienTai = Double.parseDouble(tableModel.getValueAt(selectedRow, 5).toString());
            int maKhachHang = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());

            if (input != null) {
                try {
                    double soTienNap = Double.parseDouble(input);
                    if (soTienNap <= 0) {
                        JOptionPane.showMessageDialog(parentComponent, "Số tiền phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } else {
                        double soDuMoi = soDuHienTai + soTienNap;
                        if (khachHangBUS.napTien(maKhachHang, soDuMoi)) {
                            tableModel.setValueAt(soDuMoi, selectedRow, 5);
                            JOptionPane.showMessageDialog(parentComponent, "Nạp tiền thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(parentComponent, "Nạp tiền thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(parentComponent, "Số tiền không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}