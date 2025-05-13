package GUI;

import javax.swing.*;
import java.awt.*;

public class QuanLyKhoPanel extends JPanel {
    public QuanLyKhoPanel() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Quản lý Kho", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);
    }
}