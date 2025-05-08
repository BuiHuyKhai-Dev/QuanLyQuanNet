package GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import DAL.DBConnect;
import DAO.KhoThucAnDAO;
import DTO.KhoThucAnDTO;
import javax.swing.table.DefaultTableModel;

public class KhoThucAn extends JPanel {
    private JComboBox<String> cboKho, cboThucAn;
    private JTextField txtSoLuong;
    private JButton btnThem, btnSua;
    private JTable tblDanhSach;  // Bảng hiển thị danh sách sản phẩm
    private DefaultTableModel model;

    public KhoThucAn() {
        setLayout(new BorderLayout());
        initComponents();
        loadTenKho();
        loadTenThucAn();
        loadDanhSachThucAn();  // Tải danh sách thức ăn ngay khi khởi tạo
    }

    private void initComponents() {
        JPanel panelInput = new JPanel(new GridLayout(4, 2, 10, 10));
        panelInput.add(new JLabel("Tên Kho:"));
        cboKho = new JComboBox<>();
        panelInput.add(cboKho);

        panelInput.add(new JLabel("Tên Thức Ăn:"));
        cboThucAn = new JComboBox<>();
        panelInput.add(cboThucAn);

        panelInput.add(new JLabel("Số Lượng:"));
        txtSoLuong = new JTextField();
        panelInput.add(txtSoLuong);

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        panelInput.add(btnThem);
        panelInput.add(btnSua);

        add(panelInput, BorderLayout.NORTH);

        // Bảng hiển thị danh sách sản phẩm trong kho
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);

        tblDanhSach = new JTable();
        scrollPane.setViewportView(tblDanhSach);

        // Khởi tạo bảng trực tiếp trong initComponents
        model = new DefaultTableModel();
        tblDanhSach.setModel(model);  // Gắn model cho bảng
        model.addColumn("Mã Thức Ăn");
        model.addColumn("Tên Thức Ăn");
        model.addColumn("Số Lượng");

        btnThem.addActionListener(e -> themKhoThucAn());
        btnSua.addActionListener(e -> suaKhoThucAn());

        // Lắng nghe sự kiện chọn kho
        cboKho.addActionListener(e -> loadDanhSachThucAn());
    }

    private void loadTenKho() {
        cboKho.removeAllItems();  // Xóa hết các item cũ trong ComboBox của Kho
        String sql = "SELECT MaKho, TenKho FROM kho";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int maKho = rs.getInt("MaKho");
                String tenKho = rs.getString("TenKho");
                cboKho.addItem(maKho + " - " + tenKho);  // Thêm item vào ComboBox
            }

            // Chọn kho đầu tiên sau khi load
            if (cboKho.getItemCount() > 0) {
                cboKho.setSelectedIndex(0);  // Chọn kho đầu tiên
                loadDanhSachThucAn();  // Tải danh sách thức ăn cho kho đầu tiên ngay khi mở giao diện
            } else {
                JOptionPane.showMessageDialog(this, "Không có kho nào trong cơ sở dữ liệu.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTenThucAn() {
        cboThucAn.removeAllItems();  // Xóa hết các item cũ trong ComboBox của Thức Ăn
        String sql = "SELECT MaThucAn, TenThucAn FROM thucan";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int maThucAn = rs.getInt("MaThucAn");
                String tenThucAn = rs.getString("TenThucAn");
                cboThucAn.addItem(maThucAn + " - " + tenThucAn);  // Thêm item vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDanhSachThucAn() {
        model.setRowCount(0);  // Xóa dữ liệu cũ trong bảng
        if (cboKho.getSelectedIndex() == -1) return;  // Nếu chưa chọn kho thì không làm gì

        int maKho = Integer.parseInt(cboKho.getSelectedItem().toString().split(" - ")[0]);
        String sql = "SELECT t.MaThucAn, t.TenThucAn, k.SoLuong FROM thucan t " +
                     "JOIN khothucan k ON t.MaThucAn = k.MaThucAn WHERE k.MaKho = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKho);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int maThucAn = rs.getInt("MaThucAn");
                    String tenThucAn = rs.getString("TenThucAn");
                    int soLuong = rs.getInt("SoLuong");
                    model.addRow(new Object[]{maThucAn, tenThucAn, soLuong});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void themKhoThucAn() {
        try {
            int maKho = Integer.parseInt(cboKho.getSelectedItem().toString().split(" - ")[0]);
            int maThucAn = Integer.parseInt(cboThucAn.getSelectedItem().toString().split(" - ")[0]);
            int soLuongMoi = Integer.parseInt(txtSoLuong.getText());
    
            // Kiểm tra xem thức ăn đã tồn tại trong kho chưa
            String checkSql = "SELECT SoLuong FROM khothucan WHERE MaKho = ? AND MaThucAn = ?";
            try (Connection conn = DBConnect.getConnection();
                 PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setInt(1, maKho);
                psCheck.setInt(2, maThucAn);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        // Nếu thức ăn đã tồn tại, cập nhật số lượng
                        int soLuongCu = rs.getInt("SoLuong");
                        int soLuongMoiCapNhat = soLuongCu + soLuongMoi;
    
                        String updateSql = "UPDATE khothucan SET SoLuong = ? WHERE MaKho = ? AND MaThucAn = ?";
                        try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                            psUpdate.setInt(1, soLuongMoiCapNhat);
                            psUpdate.setInt(2, maKho);
                            psUpdate.setInt(3, maThucAn);
                            int rows = psUpdate.executeUpdate();
                            if (rows > 0) {
                                JOptionPane.showMessageDialog(this, "Cập nhật số lượng thành công!");
                                loadDanhSachThucAn();  // Cập nhật lại danh sách sau khi sửa
                            }
                        }
                    } else {
                        // Nếu thức ăn chưa tồn tại, thêm mới vào kho
                        String insertSql = "INSERT INTO khothucan (MaKho, MaThucAn, SoLuong) VALUES (?, ?, ?)";
                        try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                            psInsert.setInt(1, maKho);
                            psInsert.setInt(2, maThucAn);
                            psInsert.setInt(3, soLuongMoi);
                            int rows = psInsert.executeUpdate();
                            if (rows > 0) {
                                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                                loadDanhSachThucAn();  // Cập nhật lại danh sách sau khi thêm
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm dữ liệu: " + e.getMessage());
        }
    }

    private void suaKhoThucAn() {
        try {
            int maKho = Integer.parseInt(cboKho.getSelectedItem().toString().split(" - ")[0]);
            int maThucAn = Integer.parseInt(cboThucAn.getSelectedItem().toString().split(" - ")[0]);
            int soLuong = Integer.parseInt(txtSoLuong.getText());

            String sql = "UPDATE khothucan SET SoLuong = ? WHERE MaKho = ? AND MaThucAn = ?";
            try (Connection conn = DBConnect.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, soLuong);
                ps.setInt(2, maKho);
                ps.setInt(3, maThucAn);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    loadDanhSachThucAn();  // Cập nhật lại danh sách sau khi sửa
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy bản ghi để cập nhật.");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật dữ liệu: " + e.getMessage());
        }
    }

}
