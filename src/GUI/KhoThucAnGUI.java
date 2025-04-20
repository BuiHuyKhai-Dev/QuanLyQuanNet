package GUI;

import javax.swing.*;
import java.awt.*;

public class KhoThucAnGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public KhoThucAnGUI() {
        setTitle("Quản lý quán net");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Khởi tạo CardLayout và JPanel chính
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Thêm giao diện các panel
        QuanLyThucAn thucAnPanel = new QuanLyThucAn();
        KhoThucAn khoThucAnPanel = new KhoThucAn();
        mainPanel.add(thucAnPanel, "ThucAn");
        mainPanel.add(khoThucAnPanel, "KhoThucAn");

        // Thêm nút để chuyển đổi giữa các panel
        JPanel buttonPanel = new JPanel();
        JButton btnThucAn = new JButton("Quản lý Thức Ăn");
        JButton btnKhoThucAn = new JButton("Quản lý Kho Thức Ăn");

        // Lắng nghe sự kiện nút
        btnThucAn.addActionListener(e -> cardLayout.show(mainPanel, "ThucAn"));
        btnKhoThucAn.addActionListener(e -> cardLayout.show(mainPanel, "KhoThucAn"));

        // Thêm các nút vào panel trên cùng
        buttonPanel.add(btnThucAn);
        buttonPanel.add(btnKhoThucAn);

        // Thêm các panel vào JFrame
        add(buttonPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
        // Hiển thị panel Quản lý Thực Ăn mặc định
        cardLayout.show(mainPanel, "ThucAn");
    }

}
