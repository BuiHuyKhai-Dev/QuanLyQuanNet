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
import com.toedter.calendar.JDateChooser; // Import thư viện JCalendar


public class QuanLyNhaCungCapPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable supplierTable;
    private NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();
    private JComboBox<String> sortComboBox; // Moved to class-level for access in all methods

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

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sortComboBox = new JComboBox<>(new String[]{"Tất cả","Sắp xếp theo tên", "Sắp xếp theo địa chỉ","Nhà cung cấp đã xóa"});
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
                ArrayList<NhaCungCapDTO> resutl = new NhaCungCapBUS().getNhaCungCapAll(); // Nếu ô tìm kiếm rỗng, tải lại dữ liệu
                for (NhaCungCapDTO ncc : resutl) {
                    if (ncc.getTrangThai() == 1) { // Chỉ hiển thị nhà cung cấp còn hoạt động
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
            } else {
                // Tìm kiếm và lọc dữ liệu
                tableModel.setRowCount(0);
                ArrayList<NhaCungCapDTO> danhSachNhaCungCap = new NhaCungCapBUS().getNhaCungCapAll();
                for (NhaCungCapDTO ncc : danhSachNhaCungCap) {
                    if (ncc.getTenNhaCungCap().toLowerCase().contains(searchText.toLowerCase())) {
                        if (ncc.getTrangThai() == 1) { // Chỉ hiển thị nhà cung cấp còn hoạt động
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
            }
        });
        sortComboBox.addActionListener(e -> {
            String selectedItem = (String) sortComboBox.getSelectedItem();
            tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng
            ArrayList<NhaCungCapDTO> sortedList;
            if ("Sắp xếp theo tên".equals(selectedItem)) {
                sortedList = new NhaCungCapBUS().sortName();
            } else if ("Sắp xếp theo địa chỉ".equals(selectedItem)) {
                sortedList = new NhaCungCapBUS().sortAdress();
            } else {
                sortedList = new NhaCungCapBUS().getNhaCungCapAll(); // Nếu không chọn sắp xếp, tải lại dữ liệu
            }
            for (NhaCungCapDTO ncc : sortedList) {
                if (ncc.getTrangThai() == 1) { // Chỉ hiển thị nhà cung cấp còn hoạt động
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

            if ("Nhà cung cấp đã xóa".equals(selectedItem)) {
                tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng
                ArrayList<NhaCungCapDTO> deletedList = new NhaCungCapBUS().getNhaCungCapAll();
                for (NhaCungCapDTO ncc : deletedList) {
                    if (ncc.getTrangThai() == 0) { // Chỉ hiển thị nhà cung cấp đã xóa
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
        btnFilter.addActionListener(e -> {
            String fromDate = fromDateChooser.getDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(fromDateChooser.getDate()) : "";
            String toDate = toDateChooser.getDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(toDateChooser.getDate()) : "";

            if (fromDate.isEmpty() || toDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn cả ngày bắt đầu và ngày kết thúc!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lọc dữ liệu theo khoảng thời gian
            ArrayList<NhaCungCapDTO> filteredList = new NhaCungCapBUS().filterByDate(fromDate, toDate);
            tableModel.setRowCount(0); // Xóa dữ liệu cũ
            for (NhaCungCapDTO nv : filteredList) {
                if (nv.getTrangThai() == 1) { // Chỉ hiển thị nhà cung cấp còn hoạt động
                    tableModel.addRow(new Object[]{
                        nv.getMaNhaCungCap(),
                        nv.getTenNhaCungCap(),
                        nv.getDiaChi(),
                        nv.getSoDienThoai(),
                        nv.getEmail(),
                        nv.getThoiGianTao()
                    });
                }
            }
        });
    }

    private void loadSupplierData() {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Lấy danh sách nhà cung cấp từ NhaCungCapBUS
        ArrayList<NhaCungCapDTO> danhSachNhaCungCap = new NhaCungCapBUS().getNhaCungCapAll();

        // Đổ dữ liệu vào bảng
        for (NhaCungCapDTO ncc : danhSachNhaCungCap) {
            if (ncc.getTrangThai() == 1) { // Chỉ hiển thị nhà cung cấp còn hoạt động
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
            ncc.setTrangThai(1); // Trạng thái nhà cung cấp là 1 (còn hoạt động)
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
        dialog.setLayout(new GridLayout(6, 2, 10, 10));

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

        dialog.add(new JLabel("Trạng thái (1: hoạt động, 0: đã xóa):"));
        // Lấy trạng thái từ BUS để luôn đúng nhất
        String maNCC = tableModel.getValueAt(selectedRow, 0).toString();
        NhaCungCapDTO nccDTO = new NhaCungCapBUS().getNhaCungCapById(Integer.parseInt(maNCC));
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"1", "0"});
        statusComboBox.setSelectedItem(nccDTO != null ? String.valueOf(nccDTO.getTrangThai()) : "1");
        dialog.add(statusComboBox);

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
            ncc.setTrangThai(statusComboBox.getSelectedIndex() == 0 ? 1 : 0); // Cập nhật trạng thái
            if (nhaCungCapBUS.updateNhaCungCap_DB(ncc)) {
                JOptionPane.showMessageDialog(this, "Sửa nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Sửa nhà cung cấp thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
            dialog.dispose();
            // Cập nhật lại dữ liệu trong bảng
            loadSupplierData();
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
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết nhà cung cấp", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout(10, 10)); // Sử dụng BorderLayout

        // Panel chứa thông tin chi tiết
        JPanel detailPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        detailPanel.add(new JLabel("Tên nhà cung cấp:"));
        JTextField txtName = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        txtName.setEditable(false);
        detailPanel.add(txtName);

        detailPanel.add(new JLabel("Địa chỉ:"));
        JTextField txtAddress = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        txtAddress.setEditable(false);
        detailPanel.add(txtAddress);

        detailPanel.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField((String) tableModel.getValueAt(selectedRow, 3));
        txtPhone.setEditable(false);
        detailPanel.add(txtPhone);

        detailPanel.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField((String) tableModel.getValueAt(selectedRow, 4));
        txtEmail.setEditable(false);
        detailPanel.add(txtEmail);

        detailPanel.add(new JLabel("Thời gian tạo:"));
        JTextField txtCreatedTime = new JTextField((String) tableModel.getValueAt(selectedRow, 5));
        txtCreatedTime.setEditable(false);
        detailPanel.add(txtCreatedTime);

        dialog.add(detailPanel, BorderLayout.CENTER);

        // Nút đóng
        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(btnClose, BorderLayout.CENTER); // Nút chiếm toàn bộ chiều ngang
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}