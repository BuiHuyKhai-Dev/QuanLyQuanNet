package GUI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class QuanLyDatMay extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public QuanLyDatMay() {
        setLayout(new BorderLayout());

        // --- Panel menu bên trái ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(230, 230, 230));
        leftPanel.setPreferredSize(new Dimension(180, getHeight()));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));

        // --- Tạo các nút ---
        JButton btnXemTinhTrang = new JButton("🖥  Tình trạng máy");
        JButton btnXemLichSu = new JButton("📜  Lịch sử sử dụng");

        btnXemTinhTrang.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnXemLichSu.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnXemTinhTrang.setMaximumSize(new Dimension(160, 40));
        btnXemLichSu.setMaximumSize(new Dimension(160, 40));

        // Khoảng cách giữa các nút
        leftPanel.add(btnXemTinhTrang);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        leftPanel.add(btnXemLichSu);

        // --- Panel trung tâm dùng CardLayout ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(new maytinh(), "TinhTrang");
        cardPanel.add(new SuDungMayGUI(), "LichSu");

        // --- Sự kiện chuyển panel ---
        btnXemTinhTrang.addActionListener(e -> cardLayout.show(cardPanel, "TinhTrang"));
        btnXemLichSu.addActionListener(e -> cardLayout.show(cardPanel, "LichSu"));

        // --- Thêm vào giao diện chính ---
        add(leftPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
