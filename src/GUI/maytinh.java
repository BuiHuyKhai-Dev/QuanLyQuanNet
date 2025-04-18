package GUI;
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
    }

    private JPanel createMayPanel(MayTinhDTO may) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(120, 120));
        panel.setLayout(new BorderLayout());

        JLabel lblTen = new JLabel(may.getTenMay(), SwingConstants.CENTER);
        JLabel lblTrangThai = new JLabel(getTrangThaiText(may.getTrangThai()), SwingConstants.CENTER);
        mapTrangThaiLabel.put(may, lblTrangThai);

        JButton btnChinhSuaMay = new JButton("Chỉnh sửa");
        btnChinhSuaMay.setVisible(false); // Chỉ hiển thị khi panel được chọn

        panel.add(lblTen, BorderLayout.NORTH);
        panel.add(lblTrangThai, BorderLayout.CENTER);
        panel.add(btnChinhSuaMay, BorderLayout.SOUTH);

        Color color = getColorForTrangThai(may.getTrangThai());
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (may.getTrangThai() == 2) {
            startTimer(may);
        }

        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (selectedPanel != null && selectedPanel != panel) {
                    selectedPanel.setBackground(previousColor);
                    // Ẩn nút chỉnh sửa của panel cũ
                    for (Component c : selectedPanel.getComponents()) {
                        if (c instanceof JButton) {
                            c.setVisible(false);
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
        dialog.pack();
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
            if (!khDAL.tonTaiMaKH(maKH)) {
                JOptionPane.showMessageDialog(dialog, "Mã khách hàng không tồn tại!");
                return;
            }

            // Lưu thời gian bắt đầu vào map
            long now = System.currentTimeMillis();
            mapStartTime.put(selectedMay, now);
            startTimer(selectedMay);

            // Tạo SuDungMayDTO mới
            SuDungMayDTO sdm = new SuDungMayDTO();
            sdm.setMaKhachHang(maKH);
            sdm.setMaMay(selectedMay.getMaMay());
            sdm.setThoiGianBatDau(new java.sql.Timestamp(now));
            sdm.setThoiGianKetThuc(null);  // Thời gian kết thúc sẽ được cập nhật khi trạng thái thay đổi
            sdm.setTongThoiGian(0);
            sdm.setChiPhi(0.0);

            SuDungMayDAO sdmDAL = new SuDungMayDAO();
            boolean success = sdmDAL.insert(sdm);
            if (!success) {
                JOptionPane.showMessageDialog(dialog, "Lỗi khi lưu dữ liệu sử dụng máy!");
                return;
            }
        } else {
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

    dialog.pack();
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}


    private void startTimer(MayTinhDTO may) {
        if (mapTimer.containsKey(may)) return;

        long startTime = mapStartTime.getOrDefault(may, System.currentTimeMillis());
        mapStartTime.put(may, startTime);

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
