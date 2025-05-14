package GUI;

import BUS.NhaCungCapBUS;
import BUS.NhanVienBUS;
import BUS.PhieuNhapBUS;
import DTO.PhieuNhapDTO;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;

public class PhieuNhapPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();

    public PhieuNhapPanel() {
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

        // ComboBox sắp xếp, ô tìm kiếm và lọc thời gian phía phải
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"Tất cả", "Sắp xếp theo tổng tiền", "Sắp xếp theo ngày nhập"});
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

        // Bảng hiển thị dữ liệu
        String[] columns = {"Mã Phiếu Nhập", "Nhà Cung Cấp", "Nhân viên nhập", "Tổng tiền", "Ngày nhập"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 25));
        //Lấy dữ liệu từ cơ sở dữ liệu
        PhieuNhapBUS  phieuNhapBUS = new PhieuNhapBUS();
        ArrayList<PhieuNhapDTO> listPhieuNhap = phieuNhapBUS.getAll();
        for (PhieuNhapDTO phieuNhap : listPhieuNhap) {
            tableModel.addRow(new Object[]{
                    phieuNhap.getMaPN(),
                    nhaCungCapBUS.getTenNCC(phieuNhap.getMaNCC()),
                    nhanVienBUS.getTenNV(phieuNhap.getMaNV()),
                    phieuNhap.getTongTien(),
                    phieuNhap.getThoiGianTao()
            });
        }
        table.setModel(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null); // Không cho phép chỉnh sửa trực tiếp trên bảng
        table.setFillsViewportHeight(true);
        // Căn chỉnh chữ ở giữa ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Xử lý sự kiện các nút
        btnAdd.addActionListener(e -> showAddDialog());
        btnDelete.addActionListener(e -> deleteSelectedRow());
        btnEdit.addActionListener(e -> showEditDialog());
        btnDetail.addActionListener(e -> showDetailDialog());
        btnSearch.addActionListener(e -> searchByKeyword(searchField.getText()));
        btnFilter.addActionListener(e -> filterByDate(fromDateChooser, toDateChooser));
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm phiếu nhập", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        dialog.add(new JLabel("Nhà cung cấp:"));
        JTextField txtSupplier = new JTextField();
        dialog.add(txtSupplier);

        dialog.add(new JLabel("Nhân viên nhập:"));
        JTextField txtEmployee = new JTextField();
        dialog.add(txtEmployee);

        dialog.add(new JLabel("Tổng tiền:"));
        JTextField txtTotal = new JTextField();
        dialog.add(txtTotal);

        dialog.add(new JLabel("Ngày nhập:"));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dialog.add(dateChooser);

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            String supplier = txtSupplier.getText();
            String employee = txtEmployee.getText();
            String total = txtTotal.getText();
            String date = new SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate());

            // Thêm dữ liệu vào bảng
            tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, supplier, employee, total, date});
            dialog.dispose();
        });
        dialog.add(btnConfirm);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.add(btnCancel);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.removeRow(selectedRow);
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa phiếu nhập", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        dialog.add(new JLabel("Nhà cung cấp:"));
        JTextField txtSupplier = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        dialog.add(txtSupplier);

        dialog.add(new JLabel("Nhân viên nhập:"));
        JTextField txtEmployee = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        dialog.add(txtEmployee);

        dialog.add(new JLabel("Tổng tiền:"));
        JTextField txtTotal = new JTextField((String) tableModel.getValueAt(selectedRow, 3));
        dialog.add(txtTotal);

        dialog.add(new JLabel("Ngày nhập:"));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dialog.add(dateChooser);

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            tableModel.setValueAt(txtSupplier.getText(), selectedRow, 1);
            tableModel.setValueAt(txtEmployee.getText(), selectedRow, 2);
            tableModel.setValueAt(txtTotal.getText(), selectedRow, 3);
            tableModel.setValueAt(new SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate()), selectedRow, 4);
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
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết phiếu nhập", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        dialog.add(new JLabel("Mã Phiếu Nhập:"));
        JTextField txtId = new JTextField(String.valueOf(tableModel.getValueAt(selectedRow, 0)));
        txtId.setEditable(false);
        dialog.add(txtId);

        dialog.add(new JLabel("Nhà cung cấp:"));
        JTextField txtSupplier = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        txtSupplier.setEditable(false);
        dialog.add(txtSupplier);

        dialog.add(new JLabel("Nhân viên nhập:"));
        JTextField txtEmployee = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        txtEmployee.setEditable(false);
        dialog.add(txtEmployee);

        dialog.add(new JLabel("Tổng tiền:"));
        JTextField txtTotal = new JTextField((String) tableModel.getValueAt(selectedRow, 3));
        txtTotal.setEditable(false);
        dialog.add(txtTotal);

        dialog.add(new JLabel("Ngày nhập:"));
        JTextField txtDate = new JTextField((String) tableModel.getValueAt(selectedRow, 4));
        txtDate.setEditable(false);
        dialog.add(txtDate);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dialog.dispose());
        dialog.add(btnClose);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void searchByKeyword(String keyword) {
        // Logic tìm kiếm theo từ khóa
        JOptionPane.showMessageDialog(this, "Tìm kiếm với từ khóa: " + keyword, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void filterByDate(JDateChooser fromDateChooser, JDateChooser toDateChooser) {
        String fromDate = fromDateChooser.getDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(fromDateChooser.getDate()) : "";
        String toDate = toDateChooser.getDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(toDateChooser.getDate()) : "";

        if (fromDate.isEmpty() || toDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn cả ngày bắt đầu và ngày kết thúc!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Logic lọc theo thời gian
        JOptionPane.showMessageDialog(this, "Lọc từ ngày: " + fromDate + " đến ngày: " + toDate, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}