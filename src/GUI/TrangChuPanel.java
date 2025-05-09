package GUI;

import java.awt.*;
import javax.swing.*;

public class TrangChuPanel extends JPanel {
    public TrangChuPanel() {
        // Thiết lập layout
        setLayout(new BorderLayout());

        // Tiêu đề chính
        JLabel titleLabel = new JLabel("Bạn đang sử dụng phần mềm Quản lý quán net", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE); // Màu chữ trắng
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Không cần khoảng cách

        // Thêm tiêu đề vào giữa panel
        add(titleLabel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Lấy kích thước của panel
        int width = getWidth();
        int height = getHeight();

        // Vẽ tam giác màu đen
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK); // Màu đen
        int[] xPointsBlack = {0, width, 0};
        int[] yPointsBlack = {0, height, height};
        g2d.fillPolygon(xPointsBlack, yPointsBlack, 3);

        // Vẽ tam giác màu đỏ
        g2d.setColor(Color.RED); // Màu đỏ
        int[] xPointsRed = {0, width, width};
        int[] yPointsRed = {0, 0, height};
        g2d.fillPolygon(xPointsRed, yPointsRed, 3);
    }
}