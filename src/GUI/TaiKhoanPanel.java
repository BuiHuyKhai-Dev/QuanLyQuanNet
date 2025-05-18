package GUI;

import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import com.toedter.calendar.JDateChooser;

public class TaiKhoanPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable accountTable;
    private TaiKhoanBUS taiKhoanBUS;

    public TaiKhoanPanel() {
        taiKhoanBUS = new TaiKhoanBUS();
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
        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"Tất cả", "Sắp xếp theo tên đăng nhập", "Sắp xếp theo trạng thái"});
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
        String[] columnNames = {"Tên đăng nhập", "Mật khẩu", "Mã nhóm quyền", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        accountTable = new JTable(tableModel);

        // Đặt chiều cao dòng và header
        accountTable.setRowHeight(30);
        JTableHeader header = accountTable.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25));

        // Căn giữa chữ trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        accountTable.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scrollPane = new JScrollPane(accountTable);
        add(scrollPane, BorderLayout.CENTER);

        accountTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Load dữ liệu tài khoản
        loadAccountData();

        // Xử lý sự kiện các nút chức năng
        btnAdd.addActionListener(e -> showAddDialog());
        btnDelete.addActionListener(e -> showDeleteDialog());
        btnEdit.addActionListener(e -> showEditDialog());
        btnDetail.addActionListener(e -> showDetailDialog());
        btnSearch.addActionListener(e -> searchAccount(searchField.getText()));
        sortComboBox.addActionListener(e -> {
            String selectedItem = (String) sortComboBox.getSelectedItem();
            if (selectedItem != null) {
                if (selectedItem.equals("Sắp xếp theo tên đăng nhập")) {
                    tableModel.setRowCount(0);
                    List<TaiKhoanDTO> list = taiKhoanBUS.sortByUsername();
                    for (TaiKhoanDTO tk : list) {
                        tableModel.addRow(new Object[]{
                            tk.getTenDangNhap(),
                            tk.getMatKhau(),
                            tk.getMaNhomQuyen(),
                            tk.getTrangThai()
                        });
                    }
                } else if (selectedItem.equals("Sắp xếp theo trạng thái")) {
                    tableModel.setRowCount(0);
                    List<TaiKhoanDTO> list = taiKhoanBUS.sortByStatus();
                    for (TaiKhoanDTO tk : list) {
                        tableModel.addRow(new Object[]{
                            tk.getTenDangNhap(),
                            tk.getMatKhau(),
                            tk.getMaNhomQuyen(),
                            tk.getTrangThai()
                        });
                    }
                } else if (selectedItem.equals("Tất cả")) {
                    loadAccountData();
                }
            }
        });
    }

    private void loadAccountData() {
        tableModel.setRowCount(0);
        List<TaiKhoanDTO> list = taiKhoanBUS.getAll();
        for (TaiKhoanDTO tk : list) {
            tableModel.addRow(new Object[]{
                tk.getTenDangNhap(),
                tk.getMatKhau(),
                tk.getMaNhomQuyen(),
                tk.getTrangThai()
            });
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm tài khoản", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        dialog.add(new JLabel("Tên đăng nhập:"));
        JTextField txtUsername = new JTextField();
        dialog.add(txtUsername);

        dialog.add(new JLabel("Mật khẩu:"));
        JTextField txtPassword = new JTextField();
        dialog.add(txtPassword);

        dialog.add(new JLabel("Mã nhóm quyền:"));
        JTextField txtRole = new JTextField();
        dialog.add(txtRole);

        dialog.add(new JLabel("Trạng thái:"));
        JTextField txtStatus = new JTextField();
        dialog.add(txtStatus);

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            int role = Integer.parseInt(txtRole.getText());
            int status = Integer.parseInt(txtStatus.getText());

            if (username.isEmpty() || password.isEmpty() || txtStatus.getText().isEmpty() || txtRole.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TaiKhoanDTO newAccount = new TaiKhoanDTO(username, password, role, status);
            if (taiKhoanBUS.add(newAccount)) {
                JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadAccountData();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        dialog.add(btnConfirm);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.add(btnCancel);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showDeleteDialog() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String username = tableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tài khoản này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (taiKhoanBUS.delete(username)) {
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadAccountData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditDialog() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String username = tableModel.getValueAt(selectedRow, 0).toString();
        String password = tableModel.getValueAt(selectedRow, 1).toString();
        String role = tableModel.getValueAt(selectedRow, 2).toString();
        String status = tableModel.getValueAt(selectedRow, 3).toString();

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa tài khoản", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        dialog.add(new JLabel("Tên đăng nhập:"));
        JTextField txtUsername = new JTextField(username);
        txtUsername.setEditable(false);
        dialog.add(txtUsername);

        dialog.add(new JLabel("Mật khẩu:"));
        JTextField txtPassword = new JTextField(password);
        dialog.add(txtPassword);

        dialog.add(new JLabel("Mã nhóm quyền:"));
        JTextField txtRole = new JTextField(role);
        dialog.add(txtRole);

        dialog.add(new JLabel("Trạng thái:"));
        JTextField txtStatus = new JTextField(status);
        dialog.add(txtStatus);

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            String newPassword = txtPassword.getText();
            int newRole = Integer.parseInt(txtRole.getText());
            int newStatus = Integer.parseInt(txtStatus.getText());

            if (newPassword.isEmpty() || txtRole.getText().isEmpty() || txtStatus.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TaiKhoanDTO updatedAccount = new TaiKhoanDTO(username, newPassword, newRole, newStatus);
            if (taiKhoanBUS.update(updatedAccount)) {
                JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadAccountData();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        dialog.add(btnConfirm);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.add(btnCancel);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showDetailDialog() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String username = tableModel.getValueAt(selectedRow, 0).toString();
        String password = tableModel.getValueAt(selectedRow, 1).toString();
        String role = tableModel.getValueAt(selectedRow, 2).toString();
        String status = tableModel.getValueAt(selectedRow, 3).toString();

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết tài khoản", true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        dialog.add(new JLabel("Tên đăng nhập:"));
        JTextField txtUsername = new JTextField(username);
        txtUsername.setEditable(false);
        dialog.add(txtUsername);

        dialog.add(new JLabel("Mật khẩu:"));
        JTextField txtPassword = new JTextField(password);
        txtPassword.setEditable(false);
        dialog.add(txtPassword);

        dialog.add(new JLabel("Mã nhóm quyền:"));
        JTextField txtRole = new JTextField(role);
        txtRole.setEditable(false);
        dialog.add(txtRole);

        dialog.add(new JLabel("Trạng thái:"));
        JTextField txtStatus = new JTextField(status);
        txtStatus.setEditable(false);
        dialog.add(txtStatus);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dialog.dispose());
        dialog.add(btnClose);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void searchAccount(String keyword) {
        tableModel.setRowCount(0);
        List<TaiKhoanDTO> list = taiKhoanBUS.searchByUsername(keyword);
        for (TaiKhoanDTO tk : list) {
            tableModel.addRow(new Object[]{
                tk.getTenDangNhap(),
                tk.getMatKhau(),
                tk.getMaNhomQuyen(),
                tk.getTrangThai()
            });
        }
    }
}