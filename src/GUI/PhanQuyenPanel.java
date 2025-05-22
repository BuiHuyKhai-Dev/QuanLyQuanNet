package GUI;

import DAO.PhanQuyenDAO;
import DTO.PhanQuyenDTO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PhanQuyenPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private PhanQuyenDAO pqDAO = new PhanQuyenDAO();

    public PhanQuyenPanel() {
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

        topPanel.add(leftPanel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        // Bảng hiển thị dữ liệu
        String[] columns = {"Mã nhóm quyền", "Tên nhóm quyền"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 25));
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        table.setDefaultEditor(Object.class, null); // Không cho chỉnh sửa ô
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Chọn một dòng
        //Lấy dữ liệu từ database
        pqDAO.selectAll().forEach(pq -> {
            tableModel.addRow(new Object[]{pq.getIdNhomQuyen(), pq.getTenNhomQuyen()});
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Xử lý sự kiện các nút
        btnAdd.addActionListener(e -> showAddDialog());
        btnDelete.addActionListener(e -> deleteSelectedRow());
        btnEdit.addActionListener(e -> showEditDialog());
        btnDetail.addActionListener(e -> showDetailDialog());
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm nhóm quyền", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new BorderLayout(10, 10));

        // Panel chứa thông tin cơ bản
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        infoPanel.add(new JLabel("Mã nhóm quyền:"));
        JTextField txtId = new JTextField(String.valueOf(pqDAO.getLastID() + 1)); // Tự động gán mã nhóm quyền
        txtId.setEditable(false);
        infoPanel.add(txtId);

        infoPanel.add(new JLabel("Tên nhóm quyền:"));
        JTextField txtName = new JTextField();
        infoPanel.add(txtName);

        dialog.add(infoPanel, BorderLayout.NORTH);

        // Panel chứa checkbox các danh mục chức năng
        JPanel checkboxPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JCheckBox cbQuanLyMay = new JCheckBox("Quản lý máy");
        JCheckBox cbQuanLyKhachHang = new JCheckBox("Quản lý khách hàng");
        JCheckBox cbQuanLyNhanVien = new JCheckBox("Quản lý nhân viên");
        JCheckBox cbQuanLyNhaCungCap = new JCheckBox("Quản lý nhà cung cấp");
        JCheckBox cbQuanLyNhapHang = new JCheckBox("Quản lý nhập hàng");
        JCheckBox cbQuanLyKho = new JCheckBox("Quản lý kho");
        JCheckBox cbThongKe = new JCheckBox("Thống kê");
        JCheckBox cbPhanQuyen = new JCheckBox("Phân quyền");

        checkboxPanel.add(cbQuanLyMay);
        checkboxPanel.add(cbQuanLyKhachHang);
        checkboxPanel.add(cbQuanLyNhanVien);
        checkboxPanel.add(cbQuanLyNhaCungCap);
        checkboxPanel.add(cbQuanLyNhapHang);
        checkboxPanel.add(cbQuanLyKho);
        checkboxPanel.add(cbThongKe);
        checkboxPanel.add(cbPhanQuyen);

        dialog.add(checkboxPanel, BorderLayout.CENTER);

        // Panel chứa nút xác nhận và hủy
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            String id = txtId.getText();
            String name = txtName.getText();

            // Lấy danh sách các chức năng được chọn
            ArrayList<String> selectedPermissions = new ArrayList<>();
            if (cbQuanLyMay.isSelected()) selectedPermissions.add("Quản lý máy");
            if (cbQuanLyKhachHang.isSelected()) selectedPermissions.add("Quản lý khách hàng");
            if (cbQuanLyNhanVien.isSelected()) selectedPermissions.add("Quản lý nhân viên");
            if (cbQuanLyNhaCungCap.isSelected()) selectedPermissions.add("Quản lý nhà cung cấp");
            if (cbQuanLyNhapHang.isSelected()) selectedPermissions.add("Quản lý nhập hàng");
            if (cbQuanLyKho.isSelected()) selectedPermissions.add("Quản lý kho");
            if (cbThongKe.isSelected()) selectedPermissions.add("Thống kê");
            if (cbPhanQuyen.isSelected()) selectedPermissions.add("Phân quyền");

            // Thêm dữ liệu vào bảng
            tableModel.addRow(new Object[]{id, name});

            // Ghi log hoặc xử lý lưu danh sách quyền vào cơ sở dữ liệu
            System.out.println("Mã nhóm quyền: " + id);
            System.out.println("Tên nhóm quyền: " + name);
            System.out.println("Danh mục chức năng: " + selectedPermissions);

            pqDAO.insert(new PhanQuyenDTO(Integer.parseInt(id), name));
            JOptionPane.showMessageDialog(this, "Thêm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            dialog.dispose();
        });
        buttonPanel.add(btnConfirm);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dialog.dispose());
        buttonPanel.add(btnCancel);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa dòng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return; // Nếu không xác nhận thì thoát
        }
        else{
            if(pqDAO.delete(id) != 0){
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, "Xóa không thành công!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Cập nhật lại danh sách quyền trong cơ sở dữ liệu nếu cần
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa nhóm quyền", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(3, 2, 10, 10));

        dialog.add(new JLabel("Mã nhóm quyền:"));
        JTextField txtId = new JTextField(tableModel.getValueAt(selectedRow, 0).toString());
        txtId.setEditable(false); // Không cho phép sửa mã nhóm quyền
        dialog.add(txtId);

        dialog.add(new JLabel("Tên nhóm quyền:"));
        JTextField txtName = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
        dialog.add(txtName);

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            //Confirm 
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa dòng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return; // Nếu không xác nhận thì thoát
            }
            else{
            tableModel.setValueAt(txtName.getText(), selectedRow, 1);
            dialog.dispose();
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            pqDAO.update(new PhanQuyenDTO(id, name));
            JOptionPane.showMessageDialog(this, "Sửa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết nhóm quyền", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(2, 2, 10, 10));

        // Mỗi dòng chỉ có 1 label và 1 textfield
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Mã nhóm quyền:"));
        JTextField txtId = new JTextField(tableModel.getValueAt(selectedRow, 0).toString());
        txtId.setEditable(false);
        panel.add(txtId);

        panel.add(new JLabel("Tên nhóm quyền:"));
        JTextField txtName = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
        txtName.setEditable(false);
        panel.add(txtName);

        dialog.add(panel);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnClose);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}