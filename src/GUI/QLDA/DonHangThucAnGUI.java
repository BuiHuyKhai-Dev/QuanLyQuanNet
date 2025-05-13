package GUI.QLDA;

import BUS.HoaDonThucAnBUS;
import DTO.DonHangThucAnDTO;
import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class DonHangThucAnGUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private HoaDonThucAnBUS bus = new HoaDonThucAnBUS();

    public DonHangThucAnGUI() {
        setLayout(new BorderLayout());

        String[] columns = {"Mã Đơn", "Mã KH", "Mã Máy", "Ngày Đặt", "Tổng Tiền", "Trạng Thái", "Mã NV"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Chỉ cho sửa Trạng Thái
            }
        };

        table = new JTable(model);
        String[] statusOptions = {"Đã đặt", "Đã xác nhận", "Đã giao", "Đã hủy"};
        TableColumn statusColumn = table.getColumnModel().getColumn(5);
        statusColumn.setCellEditor(new DefaultCellEditor(new JComboBox<>(statusOptions)));

        table.setRowHeight(30); // Chiều cao các hàng
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 25)); // Chiều cao header
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
        table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean stopCellEditing() {
                fireEditingStopped();
                return super.stopCellEditing();
            }
        });
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30); // Chiều cao các hàng
        // Không cho chọn ô 
        table.setCellSelectionEnabled(false);
        table.setColumnSelectionAllowed(false);
        table.setFocusable(false);


        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Nút chức năng
        JPanel buttonPanel = new JPanel();
        JButton btnCapNhat = new JButton("Cập nhật");
        JButton btnXoa = new JButton("Xóa");
        JButton btnTimKiem = new JButton("Tìm kiếm");

        btnCapNhat.addActionListener(e -> capNhatTrangThai());
        btnXoa.addActionListener(e -> xoaHoaDon());
        btnTimKiem.addActionListener(e -> timKiem());

        buttonPanel.add(btnCapNhat);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnTimKiem);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load dữ liệu ban đầu
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        ArrayList<DonHangThucAnDTO> list = bus.selectAll();
        for (DonHangThucAnDTO hd : list) {
            model.addRow(new Object[]{
                hd.getMaDonHang(),
                hd.getMaKhachHang(),
                hd.getMaMay(),
                hd.getNgayDat(),
                hd.getTongTien(),
                convertTrangThaiToString(hd.getTrangThai()),
                hd.getMaNV()
            });
        }
    }

    private void capNhatTrangThai() {
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            int maDonHang = (int) model.getValueAt(i, 0);
            String trangThaiStr = (String) model.getValueAt(i, 5);
            int trangThai = convertTrangThaiToInt(trangThaiStr);
            bus.capNhatDonHang(maDonHang, null, null, null, null, trangThai, null);
        }
        JOptionPane.showMessageDialog(this, "✅ Cập nhật trạng thái thành công!");
    }

    private void xoaHoaDon() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int maDon = (int) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa đơn hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bus.xoaHoaDon(maDon);
                model.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Vui lòng chọn một dòng để xóa.");
        }
    }

    private void timKiem() {
        JPanel panel = new JPanel(new GridLayout(7, 2));

        JTextField tfMaDon = new JTextField();
        JTextField tfMaKH = new JTextField();
        JTextField tfMaMay = new JTextField();
        JTextField tfNgayDat = new JTextField(); // yyyy-mm-dd hh:mm:ss
        JTextField tfTongTien = new JTextField();
        JTextField tfTrangThai = new JTextField(); // Nhập số: 1–4
        JTextField tfMaNV = new JTextField();

        panel.add(new JLabel("Mã Đơn:"));
        panel.add(tfMaDon);
        panel.add(new JLabel("Mã KH:"));
        panel.add(tfMaKH);
        panel.add(new JLabel("Mã Máy:"));
        panel.add(tfMaMay);
        panel.add(new JLabel("Ngày Đặt (yyyy-mm-dd hh:mm:ss):"));
        panel.add(tfNgayDat);
        panel.add(new JLabel("Tổng Tiền:"));
        panel.add(tfTongTien);
        panel.add(new JLabel("Trạng Thái (1-4):"));
        panel.add(tfTrangThai);
        panel.add(new JLabel("Mã NV:"));
        panel.add(tfMaNV);

        int result = JOptionPane.showConfirmDialog(this, panel, "🔍 Tìm kiếm hóa đơn", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Parse dữ liệu
            Integer maDon = parseInteger(tfMaDon.getText());
            Integer maKH = parseInteger(tfMaKH.getText());
            Integer maMay = parseInteger(tfMaMay.getText());
            Timestamp ngayDat = parseTimestamp(tfNgayDat.getText());
            Double tongTien = parseDouble(tfTongTien.getText());
            Integer trangThai = parseInteger(tfTrangThai.getText());
            Integer maNV = parseInteger(tfMaNV.getText());

            ArrayList<DonHangThucAnDTO> results = bus.timKiemDonHang(maDon, maKH, maMay, ngayDat, tongTien, trangThai, maNV);
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Không tìm thấy hóa đơn nào!");
            } else {
                model.setRowCount(0);
                for (DonHangThucAnDTO hd : results) {
                    model.addRow(new Object[]{
                        hd.getMaDonHang(),
                        hd.getMaKhachHang(),
                        hd.getMaMay(),
                        hd.getNgayDat(),
                        hd.getTongTien(),
                        convertTrangThaiToString(hd.getTrangThai()),
                        hd.getMaNV()
                    });
                }
            }
        }
    }

    private String convertTrangThaiToString(int trangThai) {
        return switch (trangThai) {
            case 1 -> "Đã đặt";
            case 2 -> "Đã xác nhận";
            case 3 -> "Đã giao";
            case 4 -> "Đã hủy";
            default -> "Không xác định";
        };
    }

    private int convertTrangThaiToInt(String trangThaiStr) {
        return switch (trangThaiStr) {
            case "Đã đặt" -> 1;
            case "Đã xác nhận" -> 2;
            case "Đã giao" -> 3;
            case "Đã hủy" -> 4;
            default -> 0;
        };
    }

    private Integer parseInteger(String text) {
        try {
            return text.trim().isEmpty() ? null : Integer.parseInt(text.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private Double parseDouble(String text) {
        try {
            return text.trim().isEmpty() ? null : Double.parseDouble(text.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private Timestamp parseTimestamp(String text) {
        try {
            return text.trim().isEmpty() ? null : Timestamp.valueOf(text.trim());
        } catch (Exception e) {
            return null;
        }
    }
}
