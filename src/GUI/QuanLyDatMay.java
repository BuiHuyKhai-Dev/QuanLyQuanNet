package GUI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class QuanLyDatMay extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public QuanLyDatMay() {
        setLayout(new BorderLayout());

        // --- Panel chứa 2 nút bên trái ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1, 10, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton btnXemTinhTrang = new JButton("Xem tình trạng máy");
        JButton btnXemLichSu = new JButton("Xem lịch sử sử dụng");

        leftPanel.add(btnXemTinhTrang);
        leftPanel.add(btnXemLichSu);

        // --- Panel trung tâm sử dụng CardLayout ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Tạm thời tạo 2 panel đơn giản để hiển thị
        JPanel tinhTrangPanel = new JPanel();
        tinhTrangPanel.setBackground(Color.LIGHT_GRAY);
        tinhTrangPanel.add(new JLabel("Đây là panel tình trạng máy"));

        JPanel lichSuPanel = new JPanel();
        lichSuPanel.setBackground(Color.WHITE);
        lichSuPanel.add(new JLabel("Đây là panel lịch sử sử dụng"));

        cardPanel.add(new maytinh(), "TinhTrang");
        cardPanel.add(new SuDungMayGUI(), "LichSu");

        // Sự kiện chuyển panel khi bấm nút
        btnXemTinhTrang.addActionListener(e -> cardLayout.show(cardPanel, "TinhTrang"));
        btnXemLichSu.addActionListener(e -> cardLayout.show(cardPanel, "LichSu"));

        // Thêm vào giao diện chính
        add(leftPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        setVisible(true);
    }
}
