package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class maytinh extends JPanel {

    // Khai báo các thành phần giao diện
    private JComboBox<String> mayTinhComboBox;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JButton datButton;

    // Constructor tạo giao diện Đặt Máy
    public maytinh() {
        setLayout(new BorderLayout());

        // Tiêu đề
        JLabel titleLabel = new JLabel("Đặt Máy", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(titleLabel, BorderLayout.NORTH);

        // Tạo panel con chứa các thành phần
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Mô tả chọn máy tính
        JLabel descriptionLabel = new JLabel("Chọn máy tính bạn muốn đặt:");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(descriptionLabel);

        // Tạo panel cho các máy tính với ô vuông hiển thị trạng thái
        JPanel machinePanel = new JPanel();
        machinePanel.setLayout(new GridLayout(4, 5, 10, 10)); // 4 hàng, 5 cột

        // Tạo các ô vuông cho mỗi máy
        String[] mayTinhList = new String[20];
        Color[] mayTinhColors = new Color[20];
        String[] mayTinhStatuses = new String[20];

        // Giả sử bạn có các trạng thái máy tính (bạn có thể thay đổi tình trạng theo ý muốn)
        for (int i = 0; i < 5; i++) {
            mayTinhList[i] = "Máy " + (i + 1);
            if (i  == 1 || i== 4) { // Máy đang dùng
                mayTinhColors[i] = Color.YELLOW;
                mayTinhStatuses[i] = "Đang dùng";
            } else if (i % 2 == 1) { // Máy bảo trì
                mayTinhColors[i] = Color.GREEN;
                mayTinhStatuses[i] = "Bảo trì";
            } else { // Máy trống
                mayTinhColors[i] = Color.WHITE;
                mayTinhStatuses[i] = "Trống";
            }
            
            // Tạo một panel cho mỗi máy
            JPanel machineSquare = new JPanel();
            machineSquare.setBackground(mayTinhColors[i]);
            machineSquare.setPreferredSize(new Dimension(100, 100)); // Kích thước ô vuông

            JLabel machineLabel = new JLabel(mayTinhList[i]);
            machineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            machineSquare.add(machineLabel);

            JLabel statusLabel = new JLabel(mayTinhStatuses[i]);
            statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
            statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));

            // Thêm label trạng thái vào dưới mỗi ô vuông
            machineSquare.add(statusLabel, BorderLayout.SOUTH);
            machinePanel.add(machineSquare);
            themSuKienClick(machineSquare, statusLabel);
        }

        centerPanel.add(machinePanel);

        // Phần nhập thời gian (dùng JSpinner thay cho JDateChooser)
JPanel timePanel = new JPanel();
timePanel.setLayout(new FlowLayout());

JLabel startLabel = new JLabel("Thời gian bắt đầu:");
SpinnerDateModel startModel = new SpinnerDateModel();
JSpinner startSpinner = new JSpinner(startModel);
startSpinner.setEditor(new JSpinner.DateEditor(startSpinner, "dd/MM/yyyy HH:mm"));
startSpinner.setPreferredSize(new Dimension(150, 25));

JLabel endLabel = new JLabel("Thời gian kết thúc:");
SpinnerDateModel endModel = new SpinnerDateModel();
JSpinner endSpinner = new JSpinner(endModel);
endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, "dd/MM/yyyy HH:mm"));
endSpinner.setPreferredSize(new Dimension(150, 25));

timePanel.add(startLabel);
timePanel.add(startSpinner);
timePanel.add(endLabel);
timePanel.add(endSpinner);

centerPanel.add(timePanel);


        // Nút Đặt
        datButton = new JButton("Đặt Máy");
        datButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi nút được nhấn
                String mayTinh = (String) mayTinhComboBox.getSelectedItem();
                String startTime = startTimeField.getText();
                String endTime = endTimeField.getText();
                JOptionPane.showMessageDialog(maytinh.this, "Đã đặt " + mayTinh + " từ " + startTime + " đến " + endTime);
            }
        });
        centerPanel.add(datButton);

        // Thêm panel con vào panel chính
        this.add(centerPanel, BorderLayout.CENTER);
    }
    
    private void themSuKienClick(JPanel machineSquare, JLabel statusLabel) {
    machineSquare.addMouseListener(new java.awt.event.MouseAdapter() {
        private boolean isDat = false;

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            String status = statusLabel.getText();

            // Chỉ xử lý nếu máy đang trống hoặc đã được đặt
            if (status.equals("Trống") || status.equals("Đang đặt")) {
                if (!isDat) {
                    machineSquare.setBackground(Color.CYAN);
                    statusLabel.setText("Đang đặt");
                    isDat = true;
                } else {
                    machineSquare.setBackground(Color.WHITE);
                    statusLabel.setText("Trống");
                    isDat = false;
                }
            }
            // Nếu máy đang dùng hoặc bảo trì thì không làm gì
        }
    });
}

}
