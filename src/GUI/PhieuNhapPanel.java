package GUI;

import javax.swing.*;
import java.awt.*;

public class PhieuNhapPanel extends JPanel {
    public PhieuNhapPanel() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Quản lý Phiếu nhập", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);
    }
}