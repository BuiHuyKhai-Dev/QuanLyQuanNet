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
        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"Tất cả","Sắp xếp theo tên", "Sắp xếp theo địa chỉ"});
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
        supplierTable.setDefaultEditor(Object.class, null);
        supplierTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Chọn 1 hàng
        supplierTable.setRowSelectionAllowed(true); // Cho phép chọn hàng
        supplierTable.setColumnSelectionAllowed(false); // Không cho phép chọn cột
        supplierTable.setCellSelectionEnabled(false);
        supplierTable.setRowSelectionAllowed(true);
        supplierTable.setFocusable(false); // Không cho phép chọn ô

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
        btnSearch.addActionListener(e -> {
            String searchText = searchField.getText();
            if (searchText.isEmpty()) {
                ArrayList<NhaCungCapDTO> resutl = nhaCungCapBUS.getNhaCungCapAll(); // Nếu ô tìm kiếm rỗng, tải lại dữ liệu
                for (NhaCungCapDTO ncc : resutl) {
                    tableModel.addRow(new Object[]{
                        ncc.getMaNhaCungCap(),
                        ncc.getTenNhaCungCap(),
                        ncc.getDiaChi(),
                        ncc.getSoDienThoai(),
                        ncc.getEmail(),
                        ncc.getThoiGianTao()
                    });
                }
            } else {
                // Tìm kiếm và lọc dữ liệu
                tableModel.setRowCount(0);
                ArrayList<NhaCungCapDTO> danhSachNhaCungCap = nhaCungCapBUS.getNhaCungCapAll();
                for (NhaCungCapDTO ncc : danhSachNhaCungCap) {
                    if (ncc.getTenNhaCungCap().toLowerCase().contains(searchText.toLowerCase())) {
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
            }
        });
        sortComboBox.addActionListener(e -> {
            String selectedItem = (String) sortComboBox.getSelectedItem();
            tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng
            ArrayList<NhaCungCapDTO> sortedList;
            if ("Sắp xếp theo tên".equals(selectedItem)) {
                sortedList = nhaCungCapBUS.sortName();
            } else if ("Sắp xếp theo địa chỉ".equals(selectedItem)) {
                sortedList = nhaCungCapBUS.sortAdress();
            } else {
                sortedList = nhaCungCapBUS.getNhaCungCapAll(); // Nếu không chọn sắp xếp, tải lại dữ liệu
            }
            for (NhaCungCapDTO ncc : sortedList) {
                tableModel.addRow(new Object[]{
                    ncc.getMaNhaCungCap(),
                    ncc.getTenNhaCungCap(),
                    ncc.getDiaChi(),
                    ncc.getSoDienThoai(),
                    ncc.getEmail(),
                    ncc.getThoiGianTao()
                });
            }

        });
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

            // Kiểm tra dữ liệu nhập vào
            if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (nhaCungCapBUS.getNhaCungCapById(phone) != null) {
                JOptionPane.showMessageDialog(this, "Nhà cung cấp đã tồn tại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Kiểm tra xem nhà cung cấp đã tồn tại chưa
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 1).equals(name)) {
                    JOptionPane.showMessageDialog(this, "Nhà cung cấp đã tồn tại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // Thêm dữ liệu vào NhaCungCapBUS
            NhaCungCapDTO ncc = new NhaCungCapDTO();
            ncc.setMaNhaCungCap(nhaCungCapBUS.getLastID());
            ncc.setTenNhaCungCap(name);
            ncc.setDiaChi(address);
            ncc.setSoDienThoai(phone);
            ncc.setEmail(email);
            ncc.setThoiGianTao(createdTime);
            if (nhaCungCapBUS.add(ncc)) {
                JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
            dialog.dispose();

            // Thêm dữ liệu vào bảng
            tableModel.addRow(new Object[]{nhaCungCapBUS.getLastID(), name, address, phone, email, createdTime});
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
            if(nhaCungCapBUS.deleteById((Integer) tableModel.getValueAt(selectedRow, 0))) {
                JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
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

            // Cập nhật dữ liệu vào NhaCungCapBUS
            NhaCungCapDTO ncc = new NhaCungCapDTO();
            ncc.setMaNhaCungCap((Integer) tableModel.getValueAt(selectedRow, 0));
            ncc.setTenNhaCungCap(txtName.getText());
            ncc.setDiaChi(txtAddress.getText());
            ncc.setSoDienThoai(txtPhone.getText());
            ncc.setEmail(txtEmail.getText());
            ncc.setThoiGianTao((String) tableModel.getValueAt(selectedRow, 5));
            if (nhaCungCapBUS.updateNhaCungCap_DB(ncc)) {
                JOptionPane.showMessageDialog(this, "Sửa nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Sửa nhà cung cấp thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
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