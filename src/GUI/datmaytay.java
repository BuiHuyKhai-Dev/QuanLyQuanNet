package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class datmaytay extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public datmaytay() {
        setTitle("Menu Khách Hàng");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ======= Tạo menu =======
        JPanel menuPanel = new JPanel();
        // Sử dụng BoxLayout cho menuPanel để các nút trải từ trên xuống
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS)); // Căn dọc các nút

        // Tạo các nút
        JButton btnDatMay = new JButton("Đặt Máy");
        JButton btnDatDoAn = new JButton("Đặt Đồ Ăn");
        JButton btnPhanAnh = new JButton("Phản Ánh");

        // Thêm các nút vào menuPanel
        menuPanel.add(btnDatMay);
        menuPanel.add(btnDatDoAn);
        menuPanel.add(btnPhanAnh);

        // ======= CardLayout =======
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // ======= Các card =======
        JPanel panelDatMay = new JPanel();
        panelDatMay.add(new JLabel("🖥️ Đây là giao diện Đặt Máy"));

        JPanel panelDatDoAn = new JPanel();
        panelDatDoAn.add(new JLabel("🍔 Đây là giao diện Đặt Đồ Ăn"));

        JPanel panelPhanAnh = new JPanel();
        panelPhanAnh.add(new JLabel("📣 Đây là giao diện Phản Ánh"));

        // ======= Thêm các card vào panel chính =======
        cardPanel.add(new maytinh(), "DatMay");
        cardPanel.add(new thucan(), "DatDoAn");
        cardPanel.add(panelPhanAnh, "PhanAnh");

        // ======= Sự kiện nút =======
        btnDatMay.addActionListener(e -> cardLayout.show(cardPanel, "DatMay"));
        btnDatDoAn.addActionListener(e -> cardLayout.show(cardPanel, "DatDoAn"));
        btnPhanAnh.addActionListener(e -> cardLayout.show(cardPanel, "PhanAnh"));

        // ======= Gộp tất cả vào frame =======
        setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.WEST); // Đặt menuPanel ở phía bên trái
        add(cardPanel, BorderLayout.CENTER); // Đặt cardPanel ở giữa

        // Hiển thị mặc định card đầu tiên
        cardLayout.show(cardPanel, "DatMay");
        setVisible(true);
    }
}
