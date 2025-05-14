package GUI;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import com.toedter.calendar.JDateChooser; // Import thư viện JCalendar

public class QuanLyNhanVienPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable employeeTable;
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();


    public QuanLyNhanVienPanel() {
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
        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"Tất cả","Sắp xếp theo tên", "Sắp xếp theo lương"});
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
        String[] columnNames = {"Mã nhân viên", "Tên nhân viên", "Giới tính", "Ngày sinh", "Số điện thoại", "Email", "Địa chỉ", "Lương cơ bản", "Thời gian tạo"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);

        // Đặt chiều cao dòng và header
        employeeTable.setRowHeight(30); // Chiều cao các hàng
        JTableHeader header = employeeTable.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25)); // Chiều cao header
        // Chỉ cho phép chọn dòng, không cho phép chọn ô
        employeeTable.setCellSelectionEnabled(false);
        employeeTable.setRowSelectionAllowed(true);
        employeeTable.setColumnSelectionAllowed(false); // Không cho phép chọn cột
        employeeTable.setFocusable(false); // Không cho phép ô được chọn có tiêu điểm

        // Căn giữa chữ trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        employeeTable.setDefaultRenderer(Object.class, centerRenderer);

        // Tô màu nền xen kẽ cho các dòng
        employeeTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        // Chặn chỉnh sửa ô
        employeeTable.setDefaultEditor(Object.class, null);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Chọn 1 hàng
        employeeTable.setRowSelectionAllowed(true); // Cho phép chọn hàng
        employeeTable.setColumnSelectionAllowed(false); // Không cho phép chọn cột


        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);
        // Tải dữ liệu nhân viên
        loadEmployeeData();
        

        // Xử lý sự kiện các nút chức năng
        btnAdd.addActionListener(e -> showAddDialog());
        btnDelete.addActionListener(e -> showDeleteDialog());
        btnEdit.addActionListener(e -> showEditDialog());
        btnDetail.addActionListener(e -> showDetailDialog());
        btnSearch.addActionListener(e -> {
            String searchText = searchField.getText();
            if (searchText.isEmpty()) {
                ArrayList<NhanVienDTO> result = nhanVienBUS.sortByID();
                tableModel.setRowCount(0); // Xóa dữ liệu cũ
                for (NhanVienDTO nv : result) {
                    String gioiTing;
                    if (nv.getGioiTinh() == 1) {
                        gioiTing = "Nam";
                    } else {
                        gioiTing = "Nữ";
                    }
                    tableModel.addRow(new Object[]{
                        nv.getMaNV(),
                        nv.getTenNV(),
                        gioiTing,
                        nv.getNgaySinh(),
                        nv.getSoDT(),
                        nv.getEmail(),
                        nv.getDiaChi(),
                        nv.getLuong(),
                        nv.getThoiGianTao()
                    });
                }
            } else {
                ArrayList<NhanVienDTO> searchResults = nhanVienBUS.searchName(searchText);
                tableModel.setRowCount(0); // Xóa dữ liệu cũ
                for (NhanVienDTO nv : searchResults) {
                    String gioiTing;
                    if (nv.getGioiTinh() == 1) {
                        gioiTing = "Nam";
                    } else {
                        gioiTing = "Nữ";
                    }
                    tableModel.addRow(new Object[]{
                        nv.getMaNV(),
                        nv.getTenNV(),
                        gioiTing,
                        nv.getNgaySinh(),
                        nv.getSoDT(),
                        nv.getEmail(),
                        nv.getDiaChi(),
                        nv.getLuong(),
                        nv.getThoiGianTao()
                    });
                }
            }
        });
        sortComboBox.addActionListener(e -> {
            String selectedItem = (String) sortComboBox.getSelectedItem();
            if (selectedItem.equals("Sắp xếp theo tên")) {
                // Sắp xếp theo tên
                tableModel.setRowCount(0);
                ArrayList<NhanVienDTO> sortedList = nhanVienBUS.sortName();
                for (NhanVienDTO nv : sortedList) {
                    String gioiTing;
                    if (nv.getGioiTinh() == 1) {
                        gioiTing = "Nam";
                    } else {
                        gioiTing = "Nữ";
                    }
                    tableModel.addRow(new Object[]{
                        nv.getMaNV(),
                        nv.getTenNV(),
                        gioiTing,
                        nv.getNgaySinh(),
                        nv.getSoDT(),
                        nv.getEmail(),
                        nv.getDiaChi(),
                        nv.getLuong(),
                        nv.getThoiGianTao()
                    });
                }
            } else if (selectedItem.equals("Sắp xếp theo lương")) {
                // Sắp xếp theo lương
                tableModel.setRowCount(0);
                ArrayList<NhanVienDTO> sortedList = nhanVienBUS.sortSalary();
                for (NhanVienDTO nv : sortedList) {
                    String gioiTing;
                    if (nv.getGioiTinh() == 1) {
                        gioiTing = "Nam";
                    } else {
                        gioiTing = "Nữ";
                    }
                    tableModel.addRow(new Object[]{
                        nv.getMaNV(),
                        nv.getTenNV(),
                        gioiTing,
                        nv.getNgaySinh(),
                        nv.getSoDT(),
                        nv.getEmail(),
                        nv.getDiaChi(),
                        nv.getLuong(),
                        nv.getThoiGianTao()
                    });
                }
            }
        // Nếu chọn "Tất cả", tải lại dữ liệu
            else if (selectedItem.equals("Tất cả")) {
                ArrayList<NhanVienDTO> allList = nhanVienBUS.sortByID();
                tableModel.setRowCount(0);
                for (NhanVienDTO nv : allList) {
                    String gioiTing;
                    if (nv.getGioiTinh() == 1) {
                        gioiTing = "Nam";
                    } else {
                        gioiTing = "Nữ";
                    }
                    tableModel.addRow(new Object[]{
                        nv.getMaNV(),
                        nv.getTenNV(),
                        gioiTing,
                        nv.getNgaySinh(),
                        nv.getSoDT(),
                        nv.getEmail(),
                        nv.getDiaChi(),
                        nv.getLuong(),
                        nv.getThoiGianTao()
                    });
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
            ArrayList<NhanVienDTO> filteredList = nhanVienBUS.filterByDate(fromDate, toDate);
            tableModel.setRowCount(0); // Xóa dữ liệu cũ
            for (NhanVienDTO nv : filteredList) {
                String gioiTinh = nv.getGioiTinh() == 1 ? "Nam" : "Nữ";
                tableModel.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getTenNV(),
                    gioiTinh,
                    nv.getNgaySinh(),
                    nv.getSoDT(),
                    nv.getEmail(),
                    nv.getDiaChi(),
                    nv.getLuong(),
                    nv.getThoiGianTao()
                });
            }
        });
    }

    private void loadEmployeeData() {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Lấy danh sách nhân viên từ NhanVienBUS
        List<NhanVienDTO> danhSachNhanVien = nhanVienBUS.getNhanVienAll();

        // Đổ dữ liệu vào bảng
        for (NhanVienDTO nv : danhSachNhanVien) {
            String gioiTing;
            if (nv.getGioiTinh() == 1) {
                gioiTing = "Nam";
            } else {
                gioiTing = "Nữ";
            }
            tableModel.addRow(new Object[]{
                nv.getMaNV(),
                nv.getTenNV(),
                gioiTing,
                nv.getNgaySinh(),
                nv.getSoDT(),
                nv.getEmail(),
                nv.getDiaChi(),
                nv.getLuong(),
                nv.getThoiGianTao()
            });
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm nhân viên", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new GridLayout(9, 2, 9, 9));

        dialog.add(new JLabel("Tên:"));
        JTextField txtFirstName = new JTextField();
        dialog.add(txtFirstName);

        dialog.add(new JLabel("Giới tính:"));
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Nam", "Nữ"});
        dialog.add(genderComboBox);

        dialog.add(new JLabel("Ngày sinh:"));
        JTextField txtBirthDate = new JTextField();
        dialog.add(txtBirthDate);

        dialog.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField();
        dialog.add(txtPhone);

        dialog.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField();
        dialog.add(txtEmail);

        dialog.add(new JLabel("Địa chỉ:"));
        JTextField txtAddress = new JTextField();
        dialog.add(txtAddress);

        dialog.add(new JLabel("Lương cơ bản:"));
        JTextField txtSalary = new JTextField();
        dialog.add(txtSalary);

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            String firstName = txtFirstName.getText();
            String gender = (String) genderComboBox.getSelectedItem();
            String birthDate = txtBirthDate.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String address = txtAddress.getText();
            String salary = txtSalary.getText();

            // Lấy thời gian hiện tại
            String createdTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Thêm dữ liệu vào cơ sở dữ liệu
            String maNV = String.valueOf(nhanVienBUS.getLastID());
            NhanVienDTO nv = new NhanVienDTO();
            nv.setMaNV(maNV);
            nv.setTenNV(firstName);
            nv.setGioiTinh(gender.equals("Nam") ? 1 : 2);
            nv.setNgaySinh(birthDate);
            nv.setSoDT(phone);
            nv.setEmail(email);
            nv.setDiaChi(address);
            nv.setLuong(Double.parseDouble(salary));
            nv.setThoiGianTao(createdTime);
            if (nhanVienBUS.add(nv)) {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }

            dialog.dispose();

             // Thêm dữ liệu vào bảng
            tableModel.addRow(new Object[]{nhanVienBUS.getLastID(), firstName, gender, birthDate, phone, email, address, salary, createdTime});
        });
        dialog.add(btnConfirm);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.add(btnCancel);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        

    }

    private void showDeleteDialog() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // tableModel.removeRow(selectedRow);
            int maKhachHang = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            NhanVienBUS nhanVienBUS = new NhanVienBUS();
            if (nhanVienBUS.deleteById(String.valueOf(maKhachHang))) {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditDialog() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa nhân viên", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new BorderLayout(10, 10)); // Sử dụng BorderLayout

        // Panel chứa các trường nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10)); // 8 dòng dữ liệu
        inputPanel.add(new JLabel("Tên:"));
        JTextField txtFirstName = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
        inputPanel.add(txtFirstName);

        inputPanel.add(new JLabel("Giới tính:"));
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Nam", "Nữ"});
        genderComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
        inputPanel.add(genderComboBox);

        inputPanel.add(new JLabel("Ngày sinh:"));
        JTextField txtBirthDate = new JTextField(tableModel.getValueAt(selectedRow, 3).toString());
        inputPanel.add(txtBirthDate);

        inputPanel.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField(tableModel.getValueAt(selectedRow, 4).toString());
        inputPanel.add(txtPhone);

        inputPanel.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField(tableModel.getValueAt(selectedRow, 5).toString());
        inputPanel.add(txtEmail);

        inputPanel.add(new JLabel("Địa chỉ:"));
        JTextField txtAddress = new JTextField(tableModel.getValueAt(selectedRow, 6).toString());
        inputPanel.add(txtAddress);

        inputPanel.add(new JLabel("Lương cơ bản:"));
        JTextField txtSalary = new JTextField(tableModel.getValueAt(selectedRow, 7).toString());
        inputPanel.add(txtSalary);

        dialog.add(inputPanel, BorderLayout.CENTER);

        // Panel chứa nút "Xác nhận" và "Đóng"
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // 1 dòng, 2 cột
        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            tableModel.setValueAt(txtFirstName.getText(), selectedRow, 1);
            tableModel.setValueAt(genderComboBox.getSelectedItem(), selectedRow, 2);
            tableModel.setValueAt(txtBirthDate.getText(), selectedRow, 3);
            tableModel.setValueAt(txtPhone.getText(), selectedRow, 4);
            tableModel.setValueAt(txtEmail.getText(), selectedRow, 5);
            tableModel.setValueAt(txtAddress.getText(), selectedRow, 6);
            tableModel.setValueAt(txtSalary.getText(), selectedRow, 7);

            // Cập nhật dữ liệu vào cơ sở dữ liệu
            NhanVienDTO nv = new NhanVienDTO();
            nv.setMaNV(tableModel.getValueAt(selectedRow, 0).toString());
            nv.setTenNV(txtFirstName.getText());
            nv.setGioiTinh(genderComboBox.getSelectedItem().toString().equals("Nam") ? 1 : 2);
            nv.setNgaySinh(txtBirthDate.getText());
            nv.setSoDT(txtPhone.getText());
            nv.setEmail(txtEmail.getText());
            nv.setDiaChi(txtAddress.getText());
            nv.setLuong(Double.parseDouble(txtSalary.getText()));
            nv.setThoiGianTao(tableModel.getValueAt(selectedRow, 8).toString());
            if (nhanVienBUS.updateNhanVien_DB(nv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
            dialog.dispose();
        });
        buttonPanel.add(btnConfirm);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dialog.dispose());
        buttonPanel.add(btnClose);

        dialog.add(buttonPanel, BorderLayout.SOUTH); // Đặt panel nút ở phía dưới

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showDetailDialog() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết nhân viên", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new GridLayout(9, 2, 9, 9));

        dialog.add(new JLabel("Tên:"));
        JTextField txtFirstName = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
        txtFirstName.setEditable(false);
        dialog.add(txtFirstName);

        dialog.add(new JLabel("Giới tính:"));
        JTextField txtGender = new JTextField(tableModel.getValueAt(selectedRow, 2).toString());
        txtGender.setEditable(false);
        dialog.add(txtGender);

        dialog.add(new JLabel("Ngày sinh:"));
        JTextField txtBirthDate = new JTextField(tableModel.getValueAt(selectedRow, 3).toString());
        txtBirthDate.setEditable(false);
        dialog.add(txtBirthDate);

        dialog.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField(tableModel.getValueAt(selectedRow, 4).toString());
        txtPhone.setEditable(false);
        dialog.add(txtPhone);

        dialog.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField(tableModel.getValueAt(selectedRow, 5).toString());
        txtEmail.setEditable(false);
        dialog.add(txtEmail);

        dialog.add(new JLabel("Địa chỉ:"));
        JTextField txtAddress = new JTextField(tableModel.getValueAt(selectedRow, 6).toString());
        txtAddress.setEditable(false);
        dialog.add(txtAddress);

        dialog.add(new JLabel("Lương cơ bản:"));
        JTextField txtSalary = new JTextField(tableModel.getValueAt(selectedRow, 7).toString());
        txtSalary.setEditable(false);
        dialog.add(txtSalary);

        dialog.add(new JLabel("Thời gian tạo:"));
        JTextField txtCreatedTime = new JTextField(tableModel.getValueAt(selectedRow, 8).toString());
        txtCreatedTime.setEditable(false);
        dialog.add(txtCreatedTime);

        // Nút đóng chiếm toàn bộ chiều ngang
        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnClose);
        dialog.add(buttonPanel);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}