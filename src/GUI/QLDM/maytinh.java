package GUI.QLDM;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import DTO.MayTinhDTO;
import DAL.DatMayDAL;
import DAO.KhachHangDAO;
import DAO.SuDungMayDAO;
import DTO.SuDungMayDTO;
import java.sql.*;

public class maytinh extends JPanel {
    private ArrayList<MayTinhDTO> danhSach;
    private Map<MayTinhDTO, JLabel> mapTrangThaiLabel = new HashMap<>();
    private Map<MayTinhDTO, Timer> mapTimer = new HashMap<>();
    private Map<MayTinhDTO, Long> mapStartTime = new HashMap<>();

    private JPanel selectedPanel = null;
    private Color previousColor = null;
    private MayTinhDTO selectedMay = null;

    public maytinh() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        DatMayDAL dal = new DatMayDAL();
        danhSach = dal.selectAll();

        for (MayTinhDTO may : danhSach) {
            JPanel panel = createMayPanel(may);
            add(panel);
        }
         batDauKiemTraTuDong();
    }

    private JPanel createMayPanel(MayTinhDTO may) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setPreferredSize(new Dimension(120, 140));
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // --- Tên máy ở trên ---
    JLabel lblTen = new JLabel(may.getTenMay(), SwingConstants.CENTER);
    panel.add(lblTen, BorderLayout.NORTH);

    // --- Icon máy tính ở giữa ---
    try {
        ImageIcon icon = new ImageIcon(getClass().getResource("../../img/pc.png"));
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(iconLabel, BorderLayout.CENTER);
    } catch (Exception e) {
        JLabel fallback = new JLabel("🖥", SwingConstants.CENTER);
        fallback.setFont(new Font("Arial", Font.PLAIN, 36));
        panel.add(fallback, BorderLayout.CENTER);
    }

    // --- Panel chứa trạng thái và nút chỉnh sửa ở dưới ---
    JPanel bottomPanel = new JPanel(new BorderLayout());

    JLabel lblTrangThai = new JLabel(getTrangThaiText(may.getTrangThai()), SwingConstants.CENTER);
    lblTrangThai.setOpaque(true);
    lblTrangThai.setBackground(getColorForTrangThai(may.getTrangThai()));
    lblTrangThai.setFont(new Font("Arial", Font.PLAIN, 12));
    bottomPanel.add(lblTrangThai, BorderLayout.CENTER);
    mapTrangThaiLabel.put(may, lblTrangThai);

    JButton btnChinhSuaMay = new JButton("Chỉnh sửa");
    btnChinhSuaMay.setVisible(false);
    bottomPanel.add(btnChinhSuaMay, BorderLayout.SOUTH);

    panel.add(bottomPanel, BorderLayout.SOUTH);

    // --- Màu nền chính theo trạng thái ---
    Color color = getColorForTrangThai(may.getTrangThai());
    panel.setBackground(color);

    if (may.getTrangThai() == 2) {
        startTimer(may);
    }

    // --- Xử lý click chọn panel ---
    panel.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (selectedPanel != null && selectedPanel != panel) {
                selectedPanel.setBackground(previousColor);
                for (Component c : selectedPanel.getComponents()) {
                    if (c instanceof JPanel) {
                        for (Component inner : ((JPanel) c).getComponents()) {
                            if (inner instanceof JButton) {
                                inner.setVisible(false);
                            }
                        }
                    }
                }
            }

            if (selectedPanel == panel) {
                panel.setBackground(previousColor);
                selectedPanel = null;
                selectedMay = null;
                btnChinhSuaMay.setVisible(false);
                updateLabelTrangThai(may);
            } else {
                previousColor = panel.getBackground();
                panel.setBackground(Color.CYAN);
                selectedPanel = panel;
                selectedMay = may;
                lblTrangThai.setText("Đang chọn");
                btnChinhSuaMay.setVisible(true);
            }
        }
    });

    btnChinhSuaMay.addActionListener(e -> showEditDialog());

    return panel;
}

    private void showEditDialog() {
    if (selectedMay == null) return;

    JDialog dialog = new JDialog((Frame) null, "Chỉnh sửa máy", true);
    dialog.setLayout(new GridLayout(0, 1));
    JComboBox<String> cbTrangThai = new JComboBox<>(new String[]{"Trống", "Đang sử dụng", "Bảo trì"});
    JTextField tfMaKH = new JTextField();
    tfMaKH.setVisible(false);

    cbTrangThai.addActionListener(e -> {
        String chon = (String) cbTrangThai.getSelectedItem();
        tfMaKH.setVisible("Đang sử dụng".equals(chon));
        dialog.setSize(400, 300);
    });

    dialog.add(new JLabel("Trạng thái:"));
    dialog.add(cbTrangThai);
    dialog.add(new JLabel("Mã khách hàng (nếu có):"));
    dialog.add(tfMaKH);

    JButton btnXacNhan = new JButton("Xác nhận");
    dialog.add(btnXacNhan);
    
    

    btnXacNhan.addActionListener(e2 -> {
        String trangThaiStr = (String) cbTrangThai.getSelectedItem();
        int newTrangThai = 1;
        if ("Trống".equals(trangThaiStr)) newTrangThai = 1;
        else if ("Đang sử dụng".equals(trangThaiStr)) newTrangThai = 2;
        else if ("Bảo trì".equals(trangThaiStr)) newTrangThai = 3;
        
        
        
        // Nếu trạng thái chuyển sang "Đang sử dụng"
        if (newTrangThai == 2) {
            String maKHStr = tfMaKH.getText().trim();
            SuDungMayDAO sdmDAL2 = new SuDungMayDAO();
            try {
                if (sdmDAL2.khachHangDangSuDung(Integer.parseInt(maKHStr))) {
                    JOptionPane.showMessageDialog(dialog, "Khách hàng đang sử dụng máy khác, không thể đặt thêm!");
                    return;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi khi kiểm tra tình trạng khách hàng!");
                ex.printStackTrace();
                return;
            }
            if (maKHStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập mã khách hàng!");
                return;
            }

            int maKH;
            try {
                maKH = Integer.parseInt(maKHStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Mã khách hàng không hợp lệ!");
                return;
            }

            // Kiểm tra mã khách hàng
            KhachHangDAO khDAL = new KhachHangDAO();
            if (khDAL.timTheoMaKH(String.valueOf(maKH))== null) {
                JOptionPane.showMessageDialog(dialog, "Mã khách hàng không tồn tại!");
                return;
            }

            // Lưu thời gian bắt đầu vào map
            long now = System.currentTimeMillis();
            mapStartTime.put(selectedMay, now);
            
            
            // Tạo SuDungMayDTO mới
            SuDungMayDTO sdm = new SuDungMayDTO();
            sdm.setMaKhachHang(maKH);
            sdm.setNV(1); // Giả sử NV là 1
            sdm.setMaMay(selectedMay.getMaMay());
            sdm.setThoiGianBatDau(new java.sql.Timestamp(now));
            sdm.setThoiGianKetThuc(null);  // Thời gian kết thúc sẽ được cập nhật khi trạng thái thay đổi
            sdm.setTongThoiGian(0);
            sdm.setChiPhi(0.0);
            
            DatMayDAL dm1= new DatMayDAL();
            dm1.capnhattrangthai(selectedMay.getMaMay(), newTrangThai);

            SuDungMayDAO sdmDAL = new SuDungMayDAO();
            SuDungMayDAO sdmDAL1 = new SuDungMayDAO();
            boolean thanhCong = sdmDAL.insert(sdm);
            if (thanhCong) {
                JOptionPane.showMessageDialog(this, "Đặt máy thành công!");
                startTimer(selectedMay);
                SuDungMayGUI sd= new SuDungMayGUI();
                sd.hienThiLichSuDatMay(); // cập nhật lại lịch sử
            }
            else{
                JOptionPane.showMessageDialog(dialog, "Lỗi khi lưu dữ liệu sử dụng máy!");
                return;
            }
        } else {
            DatMayDAL dm1= new DatMayDAL();
            dm1.capnhattrangthai(selectedMay.getMaMay(), newTrangThai);
            // Khi trạng thái thay đổi từ "Đang sử dụng" sang trạng thái khác
            if (selectedMay.getTrangThai() == 2) {
                // Cập nhật thời gian kết thúc cho SuDungMay
                DatMayDAL dm= new DatMayDAL();
                try{
                    double gia= dm.layGiaThue(selectedMay.getMaMay());

                    long now = System.currentTimeMillis();
                    SuDungMayDAO sdmDAL = new SuDungMayDAO();
                    boolean updated = sdmDAL.capNhatTrangThaiSuDung(selectedMay.getMaMay(), gia,new java.sql.Timestamp(now));
                    if (!updated) {
                        JOptionPane.showMessageDialog(dialog, "Lỗi khi cập nhật thời gian kết thúc!");
                        return;
                    }
                    capNhatSoDuSauKhiTraMay(selectedMay);
                }
                catch (Exception e){
                    System.out.println("loi");
                }
            }

            stopTimer(selectedMay);
        }

        // Cập nhật lại trạng thái của máy
        selectedMay.setTrangThai(newTrangThai);

        // Cập nhật lại giao diện
        removeAll();
        mapTrangThaiLabel.clear();
        for (MayTinhDTO may : danhSach) {
            JPanel panel = createMayPanel(may);
            add(panel);
        }

        selectedPanel = null;
        selectedMay = null;

        revalidate();
        repaint();
        dialog.dispose();
    });

    dialog.setSize(400, 300);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}
    
    private void capNhatSoDuSauKhiTraMay(MayTinhDTO selectedMay) {
    try {
        SuDungMayDAO sdDAO = new SuDungMayDAO();
        KhachHangDAO khDAO = new KhachHangDAO();
        DatMayDAL datMayDAL = new DatMayDAL();

        // Lấy bản ghi sử dụng hiện tại
        SuDungMayDTO sdm = sdDAO.selectById(selectedMay.getMaMay());
        if (sdm == null || sdm.getThoiGianBatDau() == null) return;

        // Tính thời gian sử dụng
        long startTime = sdm.getThoiGianBatDau().getTime();
        long endTime = System.currentTimeMillis();

        double elapsedHours = (endTime - startTime) / (1000.0 * 60 * 60);

        // Lấy giá thuê
        double giaThue = datMayDAL.layGiaThue(selectedMay.getMaMay());

        // Tính chi phí
        double chiPhi = elapsedHours * giaThue;

        // Lấy số dư tài khoản
        double soDu = khDAO.laySoDuTaiKhoan(sdm.getMaKhachHang());

        // Trừ tiền
        double soDuMoi = soDu - chiPhi;
        if (soDuMoi < 0) soDuMoi = 0;

        // Cập nhật lại số dư
        khDAO.capNhatSoDu(sdm.getMaKhachHang(), soDuMoi);

    } catch (Exception ex) {
        System.out.println("Lỗi khi cập nhật số dư khách: " + ex);
    }
}


    private void startTimer(MayTinhDTO may) {
        if (may != null){
            if (mapTimer.containsKey(may)) return;
            SuDungMayDAO sd= new SuDungMayDAO();
            SuDungMayDTO sdm = sd.selectById(may.getMaMay());
            if (sdm == null) return; // Không có bản ghi sử dụng -> không làm gì cả

            Timestamp thoiGianBatDau = sdm.getThoiGianBatDau();
            if (thoiGianBatDau == null) return;// Giả sử có getter trong DTO
            if (thoiGianBatDau == null) return;

            long startTime = thoiGianBatDau.getTime(); // Lấy millis từ Timestamp

            Timer timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                    long h = elapsed / 3600;
                    long m = (elapsed % 3600) / 60;
                    long s = elapsed % 60;

                    String timeStr = String.format("%02d:%02d:%02d", h, m, s);
                    JLabel lbl = mapTrangThaiLabel.get(may);
                    if (lbl != null) {
                        lbl.setText(timeStr);
                    }
                }
            });

            timer.start();
            mapTimer.put(may, timer);
        }
    }
    
    private void batDauKiemTraTuDong() {
    Timer kiemTraTimer = new Timer(1_000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (MayTinhDTO may : danhSach) {
                if (may.getTrangThai() == 2) { // Chỉ kiểm tra máy đang sử dụng
                    kiemTraVaTuDongTraMayNeuHetTien(may);
                }
            }
        }
    });

    kiemTraTimer.start();
}
    
    public void kiemTraVaTuDongTraMayNeuHetTien(MayTinhDTO may) {
        SuDungMayDAO sdDAO = new SuDungMayDAO();
        KhachHangDAO khDAO = new KhachHangDAO();
        DatMayDAL datMayDAL = new DatMayDAL();

        // Lấy bản ghi sử dụng máy đang hoạt động
        SuDungMayDTO sdm = sdDAO.selectById(may.getMaMay());
        if (sdm == null || sdm.getThoiGianBatDau() == null) return;

        long startTime = sdm.getThoiGianBatDau().getTime();
        long currentTime = System.currentTimeMillis();
        double elapsedHours = (currentTime - startTime) / (1000.0 * 60 * 60); // giờ
        
        try{
        // Lấy giá thuê theo máy
            double giaThue = datMayDAL.layGiaThue(may.getMaMay());

            // Tính chi phí hiện tại
            double chiPhiHienTai = elapsedHours * giaThue;

            // Lấy số dư khách hàng
            double soDu = khDAO.laySoDuTaiKhoan(sdm.getMaKhachHang());

            // Nếu số dư không đủ
            if (chiPhiHienTai >= soDu) {
                // Cập nhật trạng thái máy về "Trống" (1)
                datMayDAL.capnhattrangthai(may.getMaMay(), 1);

                // Cập nhật thời gian kết thúc + tổng thời gian + chi phí vào bản ghi
                Timestamp ketThuc = new Timestamp(currentTime);
                sdDAO.capNhatTrangThaiSuDung(may.getMaMay(), giaThue, ketThuc);

                // Trừ tiền khách
                khDAO.capNhatSoDu(sdm.getMaKhachHang(), soDu - chiPhiHienTai);

                // Dừng timer
                stopTimer(may);

                // Cập nhật trạng thái máy trong danh sách
                may.setTrangThai(1);

                // Cập nhật lại giao diện
                removeAll();
                mapTrangThaiLabel.clear();
                for (MayTinhDTO m : danhSach) {
                    JPanel panel = createMayPanel(m);
                    add(panel);
                }

                selectedPanel = null;
                selectedMay = null;

                revalidate();
                repaint();

                return;
            }
        }
        catch (Exception e){
            System.out.println("loi " + e);
        }
    }
    

    private void stopTimer(MayTinhDTO may) {
        Timer timer = mapTimer.get(may);
        if (timer != null) {
            timer.stop();
            mapTimer.remove(may);
        }
        mapStartTime.remove(may);
    }

    private void updateLabelTrangThai(MayTinhDTO may) {
        JLabel lbl = mapTrangThaiLabel.get(may);
        if (lbl != null) {
            if (may.getTrangThai() == 2 && mapStartTime.containsKey(may)) {
                // Giữ nguyên đồng hồ
            } else {
                lbl.setText(getTrangThaiText(may.getTrangThai()));
            }
        }
    }

    private Color getColorForTrangThai(int trangThai) {
        switch (trangThai) {
            case 1: return Color.WHITE;
            case 2: return Color.YELLOW;
            case 3: return Color.GREEN;
            default: return Color.LIGHT_GRAY;
        }
    }

    private String getTrangThaiText(int trangThai) {
        switch (trangThai) {
            case 1: return "Trống";
            case 2: return "Đang sử dụng";
            case 3: return "Bảo trì";
            default: return "Không rõ";
        }
    }
}