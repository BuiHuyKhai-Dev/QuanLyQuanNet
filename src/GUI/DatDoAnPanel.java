package GUI;

import GUI.QLDA.DonHangThucAnGUI;
import GUI.QLDA.datdoan;
import java.awt.*;
import javax.swing.*;

public class DatDoAnPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public DatDoAnPanel(int quyen, int maKH) {
        setLayout(new BorderLayout());
        Dimension btnSize = new Dimension(180, 40);
        // --- Panel menu ngang phía trên ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBackground(new Color(230, 230, 230));

        // --- Tạo các nút chức năng ---
        JButton btnXemTinhTrang = new JButton("🖥  Đặt đồ ăn");
        if (quyen != 4){
            JButton btnXemLichSu = new JButton("📜  Hóa đơn đặt");
            btnXemLichSu.setPreferredSize(btnSize);
            topPanel.add(btnXemLichSu);
            btnXemLichSu.addActionListener(e -> cardLayout.show(cardPanel, "Hóa đơn đặt"));
        }
        // JButton btnChinhLichSu = new JButton("✏️  Chỉnh sửa lịch sử");

        // Kích thước thống nhất
        
        btnXemTinhTrang.setPreferredSize(btnSize);
        
        // btnChinhLichSu.setPreferredSize(btnSize);

        // Thêm nút vào top panel
        topPanel.add(btnXemTinhTrang);
        
        // topPanel.add(btnChinhLichSu);

        // --- Panel trung tâm dùng CardLayout ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(new datdoan(quyen, maKH), "Đặt đồ ăn");
        cardPanel.add(new DonHangThucAnGUI(), "Hóa đơn đặt");
        // cardPanel.add(new ChinhSuaSDM(), "ChinhSua");

        // --- Gắn sự kiện nút để chuyển card ---
        btnXemTinhTrang.addActionListener(e -> cardLayout.show(cardPanel, "Đặt đồ ăn"));
        
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
