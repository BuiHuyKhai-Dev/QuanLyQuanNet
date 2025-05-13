package GUI;

import GUI.QLDA.DonHangThucAnGUI;
import GUI.QLDA.datdoan;
import java.awt.*;
import javax.swing.*;

public class DatDoAnPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public DatDoAnPanel() {
        setLayout(new BorderLayout());

        // --- Panel menu ngang phía trên ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBackground(new Color(230, 230, 230));

        // --- Tạo các nút chức năng ---
        JButton btnXemTinhTrang = new JButton("🖥  Đặt đồ ăn");
        JButton btnXemLichSu = new JButton("📜  Hóa đơn đặt");
        // JButton btnChinhLichSu = new JButton("✏️  Chỉnh sửa lịch sử");

        // Kích thước thống nhất
        Dimension btnSize = new Dimension(180, 40);
        btnXemTinhTrang.setPreferredSize(btnSize);
        btnXemLichSu.setPreferredSize(btnSize);
        // btnChinhLichSu.setPreferredSize(btnSize);

        // Thêm nút vào top panel
        topPanel.add(btnXemTinhTrang);
        topPanel.add(btnXemLichSu);
        // topPanel.add(btnChinhLichSu);

        // --- Panel trung tâm dùng CardLayout ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(new datdoan(), "Đặt đồ ăn");
        cardPanel.add(new DonHangThucAnGUI(), "Hóa đơn đặt");
        // cardPanel.add(new ChinhSuaSDM(), "ChinhSua");

        // --- Gắn sự kiện nút để chuyển card ---
        btnXemTinhTrang.addActionListener(e -> cardLayout.show(cardPanel, "Đặt đồ ăn"));
        btnXemLichSu.addActionListener(e -> cardLayout.show(cardPanel, "Hóa đơn đặt"));
        // btnChinhLichSu.addActionListener(e -> cardLayout.show(cardPanel, "ChinhSua"));

        // --- Thêm vào giao diện chính ---
        add(topPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550);
        // setLocationRelativeTo(null);
        setVisible(true);
    }
}
