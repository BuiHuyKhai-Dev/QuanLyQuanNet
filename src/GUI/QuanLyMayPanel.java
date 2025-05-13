package GUI;

import GUI.QLDM.ChinhSuaSDM;
import GUI.QLDM.SuDungMayGUI;
import GUI.QLDM.maytinh;
import java.awt.*;
import javax.swing.*;

public class QuanLyMayPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public QuanLyMayPanel() {
        setLayout(new BorderLayout());

        // --- Panel menu ngang phía trên ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBackground(new Color(230, 230, 230));

        // --- Tạo các nút chức năng ---
        JButton btnXemTinhTrang = new JButton("🖥  Tình trạng máy");
        JButton btnXemLichSu = new JButton("📜  Lịch sử sử dụng");
        JButton btnChinhLichSu = new JButton("✏️  Lịch sử toàn bộ máy");

        // Kích thước thống nhất
        Dimension btnSize = new Dimension(180, 40);
        btnXemTinhTrang.setPreferredSize(btnSize);
        btnXemLichSu.setPreferredSize(btnSize);
        btnChinhLichSu.setPreferredSize(btnSize);

        // Thêm nút vào top panel
        topPanel.add(btnXemTinhTrang);
        topPanel.add(btnXemLichSu);
        topPanel.add(btnChinhLichSu);

        // --- Panel trung tâm dùng CardLayout ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(new maytinh(), "TinhTrang");
        cardPanel.add(new SuDungMayGUI(), "LichSu");
        cardPanel.add(new ChinhSuaSDM(), "ChinhSua");

        // --- Gắn sự kiện nút để chuyển card ---
        btnXemTinhTrang.addActionListener(e -> cardLayout.show(cardPanel, "TinhTrang"));
        btnXemLichSu.addActionListener(e -> cardLayout.show(cardPanel, "LichSu"));
        btnChinhLichSu.addActionListener(e -> cardLayout.show(cardPanel, "ChinhSua"));

        // --- Thêm vào giao diện chính ---
        add(topPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550);
        // setLocationRelativeTo(null);
        setVisible(true);
    }
}
