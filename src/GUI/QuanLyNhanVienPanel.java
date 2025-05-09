package GUI;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QuanLyNhanVienPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable employeeTable;

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
        String[] columnNames = {"Mã nhân viên", "Họ", "Tên", "Giới tính", "Ngày sinh", "Số điện thoại", "Email", "Địa chỉ", "Lương cơ bản", "Thời gian tạo"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Xử lý sự kiện các nút chức năng
        btnAdd.addActionListener(e -> showAddDialog());
        btnDelete.addActionListener(e -> showDeleteDialog());
        btnEdit.addActionListener(e -> showEditDialog());
        btnDetail.addActionListener(e -> showDetailDialog());
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm nhân viên", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new GridLayout(10, 2, 10, 10));

        dialog.add(new JLabel("Họ:"));
        JTextField txtLastName = new JTextField();
        dialog.add(txtLastName);

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
            String lastName = txtLastName.getText();
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
            tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, lastName, firstName, gender, birthDate, phone, email, address, salary, createdTime});
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
        dialog.setLayout(new GridLayout(10, 2, 10, 10));

        dialog.add(new JLabel("Họ:"));
        JTextField txtLastName = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        dialog.add(txtLastName);

        dialog.add(new JLabel("Tên:"));
        JTextField txtFirstName = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        dialog.add(txtFirstName);

        dialog.add(new JLabel("Giới tính:"));
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Nam", "Nữ"});
        genderComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 3));
        dialog.add(genderComboBox);

        dialog.add(new JLabel("Ngày sinh:"));
        JTextField txtBirthDate = new JTextField((String) tableModel.getValueAt(selectedRow, 4));
        dialog.add(txtBirthDate);

        dialog.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField((String) tableModel.getValueAt(selectedRow, 5));
        dialog.add(txtPhone);

        dialog.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField((String) tableModel.getValueAt(selectedRow, 6));
        dialog.add(txtEmail);

        dialog.add(new JLabel("Địa chỉ:"));
        JTextField txtAddress = new JTextField((String) tableModel.getValueAt(selectedRow, 7));
        dialog.add(txtAddress);

        dialog.add(new JLabel("Lương cơ bản:"));
        JTextField txtSalary = new JTextField((String) tableModel.getValueAt(selectedRow, 8));
        dialog.add(txtSalary);

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            tableModel.setValueAt(txtLastName.getText(), selectedRow, 1);
            tableModel.setValueAt(txtFirstName.getText(), selectedRow, 2);
            tableModel.setValueAt(genderComboBox.getSelectedItem(), selectedRow, 3);
            tableModel.setValueAt(txtBirthDate.getText(), selectedRow, 4);
            tableModel.setValueAt(txtPhone.getText(), selectedRow, 5);
            tableModel.setValueAt(txtEmail.getText(), selectedRow, 6);
            tableModel.setValueAt(txtAddress.getText(), selectedRow, 7);
            tableModel.setValueAt(txtSalary.getText(), selectedRow, 8);
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
        dialog.setLayout(new GridLayout(10, 2, 10, 10));

        dialog.add(new JLabel("Họ:"));
        JTextField txtLastName = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        txtLastName.setEditable(false);
        dialog.add(txtLastName);

        dialog.add(new JLabel("Tên:"));
        JTextField txtFirstName = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        txtFirstName.setEditable(false);
        dialog.add(txtFirstName);

        dialog.add(new JLabel("Giới tính:"));
        JTextField txtGender = new JTextField((String) tableModel.getValueAt(selectedRow, 3));
        txtGender.setEditable(false);
        dialog.add(txtGender);

        dialog.add(new JLabel("Ngày sinh:"));
        JTextField txtBirthDate = new JTextField((String) tableModel.getValueAt(selectedRow, 4));
        txtBirthDate.setEditable(false);
        dialog.add(txtBirthDate);

        dialog.add(new JLabel("Số điện thoại:"));
        JTextField txtPhone = new JTextField((String) tableModel.getValueAt(selectedRow, 5));
        txtPhone.setEditable(false);
        dialog.add(txtPhone);

        dialog.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField((String) tableModel.getValueAt(selectedRow, 6));
        txtEmail.setEditable(false);
        dialog.add(txtEmail);

        dialog.add(new JLabel("Địa chỉ:"));
        JTextField txtAddress = new JTextField((String) tableModel.getValueAt(selectedRow, 7));
        txtAddress.setEditable(false);
        dialog.add(txtAddress);

        dialog.add(new JLabel("Lương cơ bản:"));
        JTextField txtSalary = new JTextField((String) tableModel.getValueAt(selectedRow, 8));
        txtSalary.setEditable(false);
        dialog.add(txtSalary);

        dialog.add(new JLabel("Thời gian tạo:"));
        JTextField txtCreatedTime = new JTextField((String) tableModel.getValueAt(selectedRow, 9));
        txtCreatedTime.setEditable(false);
        dialog.add(txtCreatedTime);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dialog.dispose());
        dialog.add(btnClose);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}