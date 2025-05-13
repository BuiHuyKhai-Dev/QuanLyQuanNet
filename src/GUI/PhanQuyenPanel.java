package GUI;

import javax.swing.*;
import java.awt.*;

public class PhanQuyenPanel extends JPanel {
    public PhanQuyenPanel() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Quản lý Phân quyền", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);
    }
}