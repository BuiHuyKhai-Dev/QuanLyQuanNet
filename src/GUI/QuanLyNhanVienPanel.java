package GUI;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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
        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"Sắp xếp theo tên", "Sắp xếp theo lương"});
        JTextField searchField = new JTextField(15);
        JButton btnSearch = new JButton("Tìm kiếm");
        rightPanel.add(sortComboBox);
        rightPanel.add(searchField);
        rightPanel.add(btnSearch);

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

            // Thêm dữ liệu vào bảng
            tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, firstName, gender, birthDate, phone, email, address, salary, createdTime});
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
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
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
        dialog.setLayout(new GridLayout(9, 2, 9, 9));

        dialog.add(new JLabel("Tên:"));
        JTextField txtFirstName = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
        dialog.add(txtFirstName);

        dialog.add(new JLabel("Giới tính:"));
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Nam", "Nữ"});
        genderComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 2).toString());
        dialog.add(genderComboBox);

        dialog.add(new JLabel("Ngày sinh:"));
        JTextField txtBirthDate = new JTextField(tableModel.getValueAt(selectedRow, 3).toString());
        dialog.add(txtBirthDate);

        dialog.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField(tableModel.getValueAt(selectedRow, 4).toString());
        dialog.add(txtPhone);

        dialog.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField(tableModel.getValueAt(selectedRow, 5).toString());
        dialog.add(txtEmail);

        dialog.add(new JLabel("Địa chỉ:"));
        JTextField txtAddress = new JTextField(tableModel.getValueAt(selectedRow, 6).toString());
        dialog.add(txtAddress);

        dialog.add(new JLabel("Lương cơ bản:"));
        JTextField txtSalary = new JTextField(tableModel.getValueAt(selectedRow, 7).toString());
        dialog.add(txtSalary);

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            tableModel.setValueAt(txtFirstName.getText(), selectedRow, 1);
            tableModel.setValueAt(genderComboBox.getSelectedItem(), selectedRow, 2);
            tableModel.setValueAt(txtBirthDate.getText(), selectedRow, 3);
            tableModel.setValueAt(txtPhone.getText(), selectedRow, 4);
            tableModel.setValueAt(txtEmail.getText(), selectedRow, 5);
            tableModel.setValueAt(txtAddress.getText(), selectedRow, 6);
            tableModel.setValueAt(txtSalary.getText(), selectedRow, 7);
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