package GUI;

import BUS.KhoMayTinhBUS;
import BUS.ThucAnBUS;
import DAO.KhoMayTinhDAO;
import DAO.ThucAnDAO;
import DTO.KhoMayTinhDTO;
import DTO.ThucAnDTO;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;



public class QuanLyKhoPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ThucAnBUS thucAnBUS = new ThucAnBUS();
    private ArrayList<ThucAnDTO> thucAnList = thucAnBUS.getAllThucAn();
    private KhoMayTinhBUS khoMayTinhBUS = new KhoMayTinhBUS();
    private KhoMayTinhDAO khoMayTinhDAO = new KhoMayTinhDAO();
    private ArrayList<KhoMayTinhDTO> khoMayTinhList = khoMayTinhDAO.sellectAll();

    public QuanLyKhoPanel() {
        setLayout(new BorderLayout());

        // Thanh điều hướng
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnKhoMayTinh = new JButton("Kho máy tính");
        JButton btnKhoThucAn = new JButton("Kho thức ăn");

        navPanel.add(btnKhoMayTinh);
        navPanel.add(btnKhoThucAn);

        add(navPanel, BorderLayout.NORTH);

        // Thanh công cụ (sắp xếp, tìm kiếm, lọc thời gian)
        JPanel toolPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"Sắp xếp theo tên", "Sắp xếp theo số lượng"});
        JTextField searchField = new JTextField(15);
        JButton btnSearch = new JButton("Tìm kiếm");

        JLabel lblFromDate = new JLabel("Từ:");
        JDateChooser fromDateChooser = new JDateChooser();
        fromDateChooser.setDateFormatString("yyyy-MM-dd");

        JLabel lblToDate = new JLabel("Đến:");
        JDateChooser toDateChooser = new JDateChooser();
        toDateChooser.setDateFormatString("yyyy-MM-dd");

        JButton btnFilter = new JButton("Lọc");

        toolPanel.add(sortComboBox);
        toolPanel.add(searchField);
        toolPanel.add(btnSearch);
        toolPanel.add(lblFromDate);
        toolPanel.add(fromDateChooser);
        toolPanel.add(lblToDate);
        toolPanel.add(toDateChooser);
        toolPanel.add(btnFilter);

        add(toolPanel, BorderLayout.SOUTH);

        // Card layout panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Kho máy tính
        JPanel khoMayTinhPanel = createKhoMayTinhPanel();
        cardPanel.add(khoMayTinhPanel, "Kho máy tính");

        // Kho thức ăn
        JPanel khoThucAnPanel = createKhoThucAnPanel();
        cardPanel.add(khoThucAnPanel, "Kho thức ăn");

        add(cardPanel, BorderLayout.CENTER);

        // Action listeners for navigation buttons
        btnKhoMayTinh.addActionListener(e -> cardLayout.show(cardPanel, "Kho máy tính"));
        btnKhoThucAn.addActionListener(e -> cardLayout.show(cardPanel, "Kho thức ăn"));

        // Action listeners for tool panel
        btnSearch.addActionListener(e -> {
            String keyword = searchField.getText();
            System.out.println("Tìm kiếm: " + keyword);
            // Thêm logic tìm kiếm tại đây
        });

        btnFilter.addActionListener(e -> {
            String fromDate = fromDateChooser.getDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(fromDateChooser.getDate()) : "";
            String toDate = toDateChooser.getDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(toDateChooser.getDate()) : "";
            System.out.println("Lọc từ ngày: " + fromDate + " đến ngày: " + toDate);
            // Thêm logic lọc theo thời gian tại đây
        });

        sortComboBox.addActionListener(e -> {
            String selectedSort = (String) sortComboBox.getSelectedItem();
            System.out.println("Sắp xếp: " + selectedSort);
            // Thêm logic sắp xếp tại đây
        });
    }

    private JPanel createKhoMayTinhPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table model for Kho máy tính
        String[] columns = {"Tên nhà cung cấp", "Mã máy", "Số lượng"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        // Thêm dữ liệu từ cơ sở dữ liệu vào bảng
        ArrayList<KhoMayTinhDTO> khoMayTinhList = new KhoMayTinhDAO().sellectAll();
        for (KhoMayTinhDTO khoMayTinh : khoMayTinhList) {
            // Lấy tên nhà cung cấp từ mã nhà cung cấp
            tableModel.addRow(new Object[]{
                khoMayTinhBUS.getTenMayByMa(khoMayTinh.getMaNCC()),
                khoMayTinh.getMaMay(),
                khoMayTinh.getSoLuong()
            });
        }

        // Table customization
        table.setRowHeight(30);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 25));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setCellSelectionEnabled(false);
        table.setColumnSelectionAllowed(false);
        table.setFocusable(false);
        table.setDefaultEditor(Object.class, null); // Không cho chỉnh sửa ô
        // Center align text in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Set header background color to gray
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(Color.LIGHT_GRAY);
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createKhoThucAnPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table model for Kho thức ăn
        String[] columns = {"Mã thức ăn", "Tên thức ăn", "Đơn giá", "Số lượng tồn kho"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        // Thêm dữ liệu từ cơ sở dữ liệu vào bảng
        ArrayList<ThucAnDTO> ThucAnList = new ThucAnBUS().getAllThucAn();
        for (ThucAnDTO khoThucAn : ThucAnList) {
            tableModel.addRow(new Object[]{
                khoThucAn.getMaThucAn(), // Mã thức ăn
                khoThucAn.getTenThucAn(),
                khoThucAn.getDonGia(),
                khoThucAn.getSoLuong()
            });
        }

        reloadThucAnList(tableModel);

        // Center align text in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Set header background color to gray
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(Color.LIGHT_GRAY);
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Table customization
        table.setRowHeight(30);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 25));
        JScrollPane scrollPane = new JScrollPane(table);

        // Panel chứa các nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Làm mới");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        // Thêm sự kiện cho nút Thêm
        btnAdd.addActionListener(e -> {
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm thức ăn", true);
            dialog.setSize(400, 300);
            dialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;

            // Tên thức ăn
            gbc.gridx = 0;
            gbc.gridy = 1;
            dialog.add(new JLabel("Tên thức ăn:"), gbc);
            gbc.gridx = 1;
            JTextField txtFoodName = new JTextField();
            txtFoodName.setPreferredSize(new Dimension(150, 25));
            dialog.add(txtFoodName, gbc);

            // Đơn giá
            gbc.gridx = 0;
            gbc.gridy++;
            dialog.add(new JLabel("Đơn giá:"), gbc);
            gbc.gridx = 1;
            JTextField txtPrice = new JTextField();
            txtPrice.setPreferredSize(new Dimension(150, 25));
            dialog.add(txtPrice, gbc);

            // Số lượng tồn kho
            gbc.gridx = 0;
            gbc.gridy++;
            dialog.add(new JLabel("Số lượng tồn kho:"), gbc);
            gbc.gridx = 1;
            JTextField txtQuantity = new JTextField("0"); // Mặc định là 0
            txtQuantity.setPreferredSize(new Dimension(150, 25));
            dialog.add(txtQuantity, gbc);

            // Nút Xác nhận
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            JButton btnConfirm = new JButton("Xác nhận");
            btnConfirm.addActionListener(ev -> {
                int foodId = thucAnBUS.getLastID() + 1 ; // Tạo mã thức ăn mới
                String foodName = txtFoodName.getText();
                double price = Double.parseDouble(txtPrice.getText());
                int quantity = Integer.parseInt(txtQuantity.getText());
                String createdAt = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Thời gian tạo

                // Thêm dữ liệu vào cơ sở dữ liệu
                ThucAnDTO newFood = new ThucAnDTO(foodId, foodName, quantity, price, createdAt);
                new ThucAnDAO().insert(newFood);
                // Cập nhật lại danh sách thức ăn
                thucAnList.add(newFood);
                // Cập nhật lại bảng
                reloadThucAnList(tableModel);
                // Đóng dialog
                dialog.dispose();
            });
            dialog.add(btnConfirm, gbc);

            // Nút Hủy
            gbc.gridy++;
            JButton btnCancel = new JButton("Hủy");
            btnCancel.addActionListener(ev -> dialog.dispose());
            dialog.add(btnCancel, gbc);

            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        // Thêm sự kiện cho nút Sửa
        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa thức ăn", true);
            dialog.setSize(400, 300);
            dialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;

            // Mã thức ăn
            dialog.add(new JLabel("Mã thức ăn:"), gbc);
            gbc.gridx = 1;
            JTextField txtFoodId = new JTextField(tableModel.getValueAt(selectedRow, 0).toString());
            txtFoodId.setEditable(false); // Không cho phép chỉnh sửa mã thức ăn
            dialog.add(txtFoodId, gbc);

            // Tên thức ăn
            gbc.gridx = 0;
            gbc.gridy++;
            dialog.add(new JLabel("Tên thức ăn:"), gbc);
            gbc.gridx = 1;
            JTextField txtFoodName = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
            dialog.add(txtFoodName, gbc);

            // Đơn giá
            gbc.gridx = 0;
            gbc.gridy++;
            dialog.add(new JLabel("Đơn giá:"), gbc);
            gbc.gridx = 1;
            JTextField txtPrice = new JTextField(tableModel.getValueAt(selectedRow, 2).toString());
            dialog.add(txtPrice, gbc);

            // Số lượng tồn kho
            gbc.gridx = 0;
            gbc.gridy++;
            dialog.add(new JLabel("Số lượng tồn kho:"), gbc);
            gbc.gridx = 1;
            JTextField txtQuantity = new JTextField(tableModel.getValueAt(selectedRow, 3).toString());
            txtQuantity.setEditable(false);
            dialog.add(txtQuantity, gbc);

            // Nút Xác nhận
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            JButton btnConfirm = new JButton("Xác nhận");
            btnConfirm.addActionListener(ev -> {
                int foodId = Integer.parseInt(txtFoodId.getText());
                String foodName = txtFoodName.getText();
                double price = Double.parseDouble(txtPrice.getText());
                int quantity = Integer.parseInt(txtQuantity.getText());
                String createdAt = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Thời gian tạo

                // Cập nhật dữ liệu vào cơ sở dữ liệu
                ThucAnDTO updatedFood = new ThucAnDTO(foodId, foodName, quantity, price, createdAt);
                new ThucAnDAO().update(updatedFood);
                // Cập nhật lại danh sách thức ăn
                thucAnList.set(selectedRow, updatedFood);
                // Cập nhật lại bảng
                reloadThucAnList(tableModel);
                // Đóng dialog
                dialog.dispose();
            });
            dialog.add(btnConfirm, gbc);

            // Nút Hủy
            gbc.gridy++;
            JButton btnCancel = new JButton("Hủy");
            btnCancel.addActionListener(ev -> dialog.dispose());
            dialog.add(btnCancel, gbc);

            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        // Thêm sự kiện cho nút Xóa
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int foodId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa thức ăn này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            // Xóa dữ liệu khỏi cơ sở dữ liệu
            new ThucAnDAO().delete(foodId);
            // Cập nhật lại danh sách thức ăn
            thucAnList.remove(selectedRow);
            reloadThucAnList(tableModel);
        });

        // Thêm sự kiện cho nút Làm mới
        btnRefresh.addActionListener(e -> {
            // Làm mới bảng
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Xóa tất cả dữ liệu trong bảng
            // Thêm lại dữ liệu từ cơ sở dữ liệu
            ArrayList<ThucAnDTO> khoThucAnList = new ThucAnBUS().getAllThucAn();
            for (ThucAnDTO khoThucAn : khoThucAnList) {
                model.addRow(new Object[]{
                    khoThucAn.getMaThucAn(),
                    khoThucAn.getTenThucAn(),
                    khoThucAn.getDonGia(),
                    khoThucAn.getSoLuong()
                });
            }
        });

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;

        
    }

    private void reloadThucAnList(DefaultTableModel tableModel) {
            // Clear existing data in the table model
            tableModel.setRowCount(0);

            // Reload data from the database
            thucAnList = thucAnBUS.getAllThucAn();

            // Add the updated data to the table model
            for (ThucAnDTO thucAn : thucAnList) {
                tableModel.addRow(new Object[]{
                    thucAn.getMaThucAn(),
                    thucAn.getTenThucAn(),
                    thucAn.getDonGia(),
                    thucAn.getSoLuong()
                });
            }
        }
        

}