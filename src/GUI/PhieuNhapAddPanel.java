package GUI;

import BUS.NhaCungCapBUS;
import BUS.NhanVienBUS;
import BUS.PhieuNhapBUS;
import BUS.ThucAnBUS;
import DAO.PhieuNhapDAO;
import DTO.NhaCungCapDTO;
import DTO.NhanVienDTO;
import DTO.ThucAnDTO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PhieuNhapAddPanel extends JPanel {
    private JButton btnAddToList, btnRemoveFromList, btnEditInList, btnConfirm;
    private JTable tableThucAn, tableDanhSachNhap;
    private JTextField txtMaThucAn, txtTenThucAn, txtDonGia, txtSoLuong, txtMaPhieuNhap;
    private JComboBox<String> cbNhaCungCap, cbNhanVien;
    private JLabel lblTongTien;

    public PhieuNhapAddPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 5, 8)); // giảm khoảng cách giữa các dòng
        infoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            "Thông tin sản phẩm",
            0, 0, infoPanel.getFont().deriveFont(Font.BOLD, 14), Color.BLUE
        ));
        Font infoFont = new Font("Arial", Font.PLAIN, 14); // giảm size font

        JLabel lblMaTA = new JLabel("Mã thức ăn:");
        lblMaTA.setFont(infoFont);
        txtMaThucAn = new JTextField();
        txtMaThucAn.setEditable(false);
        txtMaThucAn.setFont(infoFont);
        infoPanel.add(lblMaTA);
        infoPanel.add(txtMaThucAn);

        JLabel lblTenTA = new JLabel("Tên thức ăn:");
        lblTenTA.setFont(infoFont);
        txtTenThucAn = new JTextField();
        txtTenThucAn.setEditable(false);
        txtTenThucAn.setFont(infoFont);
        infoPanel.add(lblTenTA);
        infoPanel.add(txtTenThucAn);

        JLabel lblDonGia = new JLabel("Đơn giá:");
        lblDonGia.setFont(infoFont);
        txtDonGia = new JTextField();
        txtDonGia.setEditable(false);
        txtDonGia.setFont(infoFont);
        infoPanel.add(lblDonGia);
        infoPanel.add(txtDonGia);

        JLabel lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setFont(infoFont);
        txtSoLuong = new JTextField();
        txtSoLuong.setFont(infoFont);
        infoPanel.add(lblSoLuong);
        infoPanel.add(txtSoLuong);

        // Bảng danh sách thức ăn
        String[] colThucAn = {"Mã", "Tên", "Đơn giá", "Tồn kho"};
        tableThucAn = new JTable(new DefaultTableModel(colThucAn, 0));
        JScrollPane spThucAn = new JScrollPane(tableThucAn);
        spThucAn.setBorder(BorderFactory.createTitledBorder("Danh sách thức ăn"));
        // Nhập dữ liệu vào bảng thức ăn
        tableThucAn.setRowHeight(30); // tăng chiều cao dòng
        tableThucAn.getColumnModel().getColumn(0).setPreferredWidth(50); // cột mã
        tableThucAn.getColumnModel().getColumn(1).setPreferredWidth(150); // cột tên
        tableThucAn.getColumnModel().getColumn(2).setPreferredWidth(100); // cột đơn giá
        tableThucAn.getColumnModel().getColumn(3).setPreferredWidth(100); // cột tồn kho
        tableThucAn.setFont(infoFont);
        // Lấy dữ liệu từ ThucAnBUS để đổ vào bảng thức ăn
        ThucAnBUS thucAnBUS = new ThucAnBUS();
        ArrayList<ThucAnDTO> danhSachThucAn = thucAnBUS.getAllThucAn();
        DefaultTableModel model = (DefaultTableModel) tableThucAn.getModel();
        for (ThucAnDTO ta : danhSachThucAn) {
            model.addRow(new Object[]{
            ta.getMaThucAn(),
            ta.getTenThucAn(),
            ta.getDonGia(),
            ta.getSoLuong()
            });
        }


        // Bảng danh sách nhập
        String[] colNhap = {"Mã", "Tên", "Đơn giá", "Số lượng", "Thành tiền"};
        tableDanhSachNhap = new JTable(new DefaultTableModel(colNhap, 0));
        JScrollPane spNhap = new JScrollPane(tableDanhSachNhap);
        spNhap.setBorder(BorderFactory.createTitledBorder("Danh sách nhập"));

        // Các nút thao tác
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAddToList = new JButton("Thêm vào danh sách nhập");
        btnRemoveFromList = new JButton("Bỏ khỏi danh sách nhập");
        btnEditInList = new JButton("Sửa trong danh sách nhập");
        btnPanel.add(btnAddToList);
        btnPanel.add(btnRemoveFromList);
        btnPanel.add(btnEditInList);

        // Trái: infoPanel ở trên, bảng thức ăn ở dưới
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.add(infoPanel, BorderLayout.NORTH);
        leftPanel.add(spThucAn, BorderLayout.CENTER);

        // Thêm panel chứa nút làm mới dưới bảng danh sách thức ăn
        JPanel panelRefreshThucAn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        JButton btnRefreshThucAn = new JButton("Làm mới");
        panelRefreshThucAn.add(btnRefreshThucAn);
        leftPanel.add(panelRefreshThucAn, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.add(spNhap, BorderLayout.CENTER);
        rightPanel.add(btnPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(leftPanel, BorderLayout.WEST);
        centerPanel.add(rightPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Panel bên phải: thông tin phiếu nhập
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu nhập"));

        // Mã phiếu nhập (ở trên, kích thước bằng combobox)
        JLabel lblMaPhieuNhap = new JLabel("Mã phiếu nhập:");
        lblMaPhieuNhap.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(lblMaPhieuNhap);

        txtMaPhieuNhap = new JTextField();
        txtMaPhieuNhap.setEditable(false);
        txtMaPhieuNhap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // cao 30, rộng tự động
        txtMaPhieuNhap.setPreferredSize(new Dimension(180, 30)); // giống combobox mặc định
        txtMaPhieuNhap.setText( new PhieuNhapBUS().getLastID() + 1 + ""); // lấy mã phiếu nhập mới nhất
        sidePanel.add(txtMaPhieuNhap);

        sidePanel.add(Box.createVerticalStrut(10));

        // Nhà cung cấp
        JLabel lblNCC = new JLabel("Nhà cung cấp:");
        lblNCC.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(lblNCC);

        cbNhaCungCap = new JComboBox<>();
        for (NhaCungCapDTO ncc : new NhaCungCapBUS().getNhaCungCapAll()) {
            cbNhaCungCap.addItem(ncc.getTenNhaCungCap());
        }
        cbNhaCungCap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        cbNhaCungCap.setPreferredSize(new Dimension(180, 30));
        sidePanel.add(cbNhaCungCap);

        sidePanel.add(Box.createVerticalStrut(10));

        // Nhân viên nhập
        JLabel lblNV = new JLabel("Nhân viên nhập:");
        lblNV.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(lblNV);
        cbNhanVien = new JComboBox<>();
        cbNhanVien.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        cbNhanVien.setPreferredSize(new Dimension(180, 30));
        for (NhanVienDTO nv : new NhanVienBUS().getNhanVienAll()) {
            cbNhanVien.addItem(nv.getTenNV());
        }
        sidePanel.add(cbNhanVien);
        sidePanel.add(Box.createVerticalGlue()); // Đẩy tổng tiền và nút xuống dưới

        // Tổng tiền
        lblTongTien = new JLabel("Tổng tiền: 0 VNĐ");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 20));
        lblTongTien.setForeground(Color.RED);
        lblTongTien.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(lblTongTien);

        sidePanel.add(Box.createVerticalStrut(10));

        // Nút xác nhận nhập (rộng bằng 8/10 txtMaPhieuNhap)
        btnConfirm = new JButton("Xác nhận nhập");
        // Giảm size chữ để vừa 1 dòng, tăng chiều rộng nút
        btnConfirm.setFont(new Font("Arial", Font.BOLD, 15));
        btnConfirm.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Đặt chiều rộng nút lớn hơn combobox một chút để chữ không bị tràn
        int btnWidth = (int) (txtMaPhieuNhap.getPreferredSize().width * 1.15);
        btnConfirm.setMaximumSize(new Dimension(btnWidth, 38));
        btnConfirm.setPreferredSize(new Dimension(btnWidth, 38));
        sidePanel.add(btnConfirm);

        add(sidePanel, BorderLayout.EAST);


        // Thêm sự kiện cho các nút
        btnAddToList.addActionListener(e -> {
            String maTA = txtMaThucAn.getText().trim();
            String tenTA = txtTenThucAn.getText().trim();
            String donGiaStr = txtDonGia.getText().trim();
            String soLuongStr = txtSoLuong.getText().trim();

            if (maTA.isEmpty() || tenTA.isEmpty() || donGiaStr.isEmpty() || soLuongStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm và nhập số lượng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int soLuong;
            double donGia;
            try {
                soLuong = Integer.parseInt(soLuongStr);
                donGia = Double.parseDouble(donGiaStr);
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng hoặc đơn giá không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double thanhTien = donGia * soLuong;

            DefaultTableModel dsNhapModel = (DefaultTableModel) tableDanhSachNhap.getModel();
            dsNhapModel.addRow(new Object[]{maTA, tenTA, donGia, soLuong, thanhTien});
            updateTongTien();
            // Reset các trường thông tin thức ăn
            txtMaThucAn.setText("");
            txtTenThucAn.setText("");
            txtDonGia.setText("");
            txtSoLuong.setText("");
            tableThucAn.clearSelection(); // Bỏ chọn dòng thức ăn đã thêm

            // Sau khi thêm, có thể reset số lượng về rỗng hoặc 1
            txtSoLuong.setText("");
        });

        // Sự kiện cho nút làm mới danh sách thức ăn
        btnRefreshThucAn.addActionListener(e -> {
            DefaultTableModel thucAnModel = (DefaultTableModel) tableThucAn.getModel();
            thucAnModel.setRowCount(0);
            ThucAnBUS thucAnBUS2 = new ThucAnBUS();
            ArrayList<ThucAnDTO> danhSachThucAnMoi = thucAnBUS2.getAllThucAn();
            for (ThucAnDTO ta : danhSachThucAnMoi) {
                thucAnModel.addRow(new Object[]{
                    ta.getMaThucAn(),
                    ta.getTenThucAn(),
                    ta.getDonGia(),
                    ta.getSoLuong()
                });
            }
        });

        tableThucAn.getSelectionModel().addListSelectionListener(e -> {
            // Đảm bảo chỉ xử lý khi người dùng chọn (không phải khi clear selection)
            if (!e.getValueIsAdjusting() && tableThucAn.getSelectedRow() != -1) {
                int row = tableThucAn.getSelectedRow();
                txtMaThucAn.setText(tableThucAn.getValueAt(row, 0).toString());
                txtTenThucAn.setText(tableThucAn.getValueAt(row, 1).toString());
                txtDonGia.setText(tableThucAn.getValueAt(row, 2).toString());
                txtSoLuong.setText(""); // reset số lượng khi chọn mới

                tableDanhSachNhap.clearSelection();
            }
        });

    // Sự kiện chọn dòng bên table danh sách nhập
        tableDanhSachNhap.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableDanhSachNhap.getSelectedRow() != -1) {
                int row = tableDanhSachNhap.getSelectedRow();
                String maTA = tableDanhSachNhap.getValueAt(row, 0).toString();
                String tenTA = tableDanhSachNhap.getValueAt(row, 1).toString();
                String donGia = tableDanhSachNhap.getValueAt(row, 2).toString();
                String soLuong = tableDanhSachNhap.getValueAt(row, 3).toString();
                txtMaThucAn.setText(maTA);
                txtTenThucAn.setText(tenTA);
                txtDonGia.setText(donGia);
                txtSoLuong.setText(soLuong);

                // Bỏ focus dòng bên danh sách thức ăn
                tableThucAn.clearSelection();
            }
        });

        btnRemoveFromList.addActionListener(e -> {
            int selectedRow = tableDanhSachNhap.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng trong danh sách nhập để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            DefaultTableModel dsNhapModel = (DefaultTableModel) tableDanhSachNhap.getModel();
            dsNhapModel.removeRow(selectedRow);
            updateTongTien();
            // Sau khi xóa, bỏ chọn và reset thông tin sản phẩm
            tableDanhSachNhap.clearSelection();
            txtMaThucAn.setText("");
            txtTenThucAn.setText("");
            txtDonGia.setText("");
            txtSoLuong.setText("");
        });

        btnConfirm.addActionListener(e -> {
            DefaultTableModel dsNhapModel = (DefaultTableModel) tableDanhSachNhap.getModel();
            int rowCount = dsNhapModel.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Danh sách nhập đang trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lấy thông tin phiếu nhập
            int maPhieuNhap = Integer.parseInt(txtMaPhieuNhap.getText().trim());
            String tenNCC = (String) cbNhaCungCap.getSelectedItem();
            String tenNV = (String) cbNhanVien.getSelectedItem();
            double tongTien = 0;
            ArrayList<DTO.ChiTietPhieuNhapDTO> dsChiTiet = new ArrayList<>();

            // Lấy mã nhà cung cấp và mã nhân viên từ tên
            int maNCC = -1, maNV = -1;
            for (NhaCungCapDTO nccItem : new NhaCungCapBUS().getNhaCungCapAll()) {
                if (nccItem.getTenNhaCungCap().equals(tenNCC)) {
                    maNCC = nccItem.getMaNhaCungCap();
                    break;
                }
            }
            for (NhanVienDTO nvItem : new NhanVienBUS().getNhanVienAll()) {
                if (nvItem.getTenNV().equals(tenNV)) {
                    maNV = Integer.parseInt(nvItem.getMaNV());
                    break;
                }
            }

            // Lấy thời gian hiện tại
            String thoiGianTao = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Lấy dữ liệu từng dòng trong bảng danh sách nhập
            for (int i = 0; i < rowCount; i++) {
                int maThucAn = Integer.parseInt(dsNhapModel.getValueAt(i, 0).toString());
                int soLuong = Integer.parseInt(dsNhapModel.getValueAt(i, 3).toString());
                double donGia = Double.parseDouble(dsNhapModel.getValueAt(i, 2).toString());
                double thanhTien = Double.parseDouble(dsNhapModel.getValueAt(i, 4).toString());
                tongTien += thanhTien;

                DTO.ChiTietPhieuNhapDTO ct = new DTO.ChiTietPhieuNhapDTO(maPhieuNhap, maThucAn, soLuong, donGia, thanhTien);
                DAO.ChiTietPhieuNhapDAO ctDAO = new DAO.ChiTietPhieuNhapDAO();
                ctDAO.insert(ct); // Thêm chi tiết phiếu nhập vào CSDL
                dsChiTiet.add(ct);
            }

            // Tạo đối tượng phiếu nhập
            DTO.PhieuNhapDTO phieuNhap = new DTO.PhieuNhapDTO(maPhieuNhap, maNCC, maNV, tongTien, thoiGianTao);

            // Thêm phiếu nhập vào CSDL
            boolean success = new PhieuNhapDAO().insert(phieuNhap) != 0;
            if (success) {
                // Cập nhật lại số lượng tồn kho cho từng thức ăn
                for (DTO.ChiTietPhieuNhapDTO ct : dsChiTiet) {
                    int maThucAn = ct.getMaThucAn();
                    int soLuong = ct.getSoLuong();
                    thucAnBUS.updateSoLuong(maThucAn, soLuong);
                }
                JOptionPane.showMessageDialog(this, "Nhập phiếu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                
                // Cập nhật lại table danh sách thức ăn
                DefaultTableModel thucAnModel = (DefaultTableModel) tableThucAn.getModel();
                thucAnModel.setRowCount(0);
                ArrayList<ThucAnDTO> danhSachThucAnMoi2 = thucAnBUS.getAllThucAn();
                for (ThucAnDTO ta : danhSachThucAnMoi2) {
                    thucAnModel.addRow(new Object[]{
                        ta.getMaThucAn(),
                        ta.getTenThucAn(),
                        ta.getDonGia(),
                        ta.getSoLuong()
                    });
                }

                

                Container parent = SwingUtilities.getWindowAncestor(this);
                if (parent instanceof JDialog) {
                    JDialog dialog = (JDialog) parent;
                    if (dialog.getParent() instanceof PhieuNhapPanel) {
                        ((PhieuNhapPanel) dialog.getParent()).reloadTable();
                    }
                }

                clearForm(); // Xóa form sau khi nhập thành công
                JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this));
                dialog.setTitle("Hóa đơn phiếu nhập");
                dialog.setModal(true);
                dialog.setLayout(new BorderLayout(10, 10));
                dialog.setSize(600, 500);
                dialog.setLocationRelativeTo(this);

                // Panel thông tin phiếu nhập
                JPanel infoPanelDialog = new JPanel(new GridLayout(5, 2, 8, 8));
                infoPanelDialog.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu nhập"));
                infoPanelDialog.add(new JLabel("Mã phiếu nhập:"));
                infoPanelDialog.add(new JLabel(String.valueOf(maPhieuNhap)));
                infoPanelDialog.add(new JLabel("Nhà cung cấp:"));
                infoPanelDialog.add(new JLabel(tenNCC));
                infoPanelDialog.add(new JLabel("Nhân viên nhập:"));
                infoPanelDialog.add(new JLabel(tenNV));
                infoPanelDialog.add(new JLabel("Thời gian:"));
                infoPanelDialog.add(new JLabel(thoiGianTao));
                infoPanelDialog.add(new JLabel("Tổng tiền:"));
                infoPanelDialog.add(new JLabel(String.format("%,.0f VNĐ", tongTien)));

                dialog.add(infoPanelDialog, BorderLayout.NORTH);

                // Bảng chi tiết phiếu nhập
                String[] colChiTiet = {"Mã thức ăn", "Tên thức ăn", "Số lượng", "Đơn giá", "Thành tiền"};
                DefaultTableModel chiTietModel = new DefaultTableModel(colChiTiet, 0);
                for (DTO.ChiTietPhieuNhapDTO ct : dsChiTiet) {
                    String tenTA = "";
                    for (ThucAnDTO ta : danhSachThucAn) {
                        if (ta.getMaThucAn() == ct.getMaThucAn()) {
                            tenTA = ta.getTenThucAn();
                            break;
                        }
                    }
                    chiTietModel.addRow(new Object[]{
                        ct.getMaThucAn(),
                        tenTA,
                        ct.getSoLuong(),
                        String.format("%,.0f", ct.getDonGia()),
                        String.format("%,.0f", ct.getThanhTien())
                    });
                }
                JTable tableChiTiet = new JTable(chiTietModel);
                tableChiTiet.setEnabled(false);
                tableChiTiet.setRowHeight(28);
                JScrollPane spChiTiet = new JScrollPane(tableChiTiet);
                spChiTiet.setBorder(BorderFactory.createTitledBorder("Chi tiết nhập"));
                dialog.add(spChiTiet, BorderLayout.CENTER);

                // Nút đóng
                JPanel btnPanelDialog = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                JButton btnClose = new JButton("Đóng");
                btnClose.addActionListener(ev -> {
                    dialog.dispose();
                    // Refresh lại table Danh sách thức ăn sau khi đóng hóa đơn
                    DefaultTableModel thucAnModel2 = (DefaultTableModel) tableThucAn.getModel();
                    thucAnModel2.setRowCount(0);
                    ArrayList<ThucAnDTO> danhSachThucAnMoi = thucAnBUS.getAllThucAn();
                    for (ThucAnDTO ta : danhSachThucAnMoi) {
                        thucAnModel2.addRow(new Object[]{
                            ta.getMaThucAn(),
                            ta.getTenThucAn(),
                            ta.getDonGia(),
                            ta.getSoLuong()
                        });
                    }
                });
                btnPanelDialog.add(btnClose);
                dialog.add(btnPanelDialog, BorderLayout.SOUTH);

                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi nhập phiếu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void updateTongTien() {
        double tong = 0;
        DefaultTableModel dsNhapModel = (DefaultTableModel) tableDanhSachNhap.getModel();
        for (int i = 0; i < dsNhapModel.getRowCount(); i++) {
            Object val = dsNhapModel.getValueAt(i, 4); // cột Thành tiền
            if (val != null) {
                try {
                    tong += Double.parseDouble(val.toString());
                } catch (NumberFormatException ex) {
                    // Bỏ qua nếu dữ liệu lỗi
                }
            }
        }
        lblTongTien.setText("Tổng tiền: " + String.format("%,.0f VNĐ", tong));
    }

    public void clearForm() {
        txtMaPhieuNhap.setText("");
        txtMaThucAn.setText("");
        txtTenThucAn.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        lblTongTien.setText("Tổng tiền: 0 VNĐ");
        DefaultTableModel dsNhapModel = (DefaultTableModel) tableDanhSachNhap.getModel();
        dsNhapModel.setRowCount(0); // Xóa tất cả dòng trong bảng danh sách nhập
    }

    
}