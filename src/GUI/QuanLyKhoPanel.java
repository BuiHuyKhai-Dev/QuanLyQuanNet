package GUI;

import BUS.KhoMayTinhBUS;
import BUS.KhoThucAnBUS;
import DAO.KhoMayTinhDAO;
import DAO.KhoThucAnDAO;
import DTO.KhoMayTinhDTO;
import DTO.KhoThucAnDTO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser; // Import thư viện JCalendar
import javax.swing.table.DefaultTableCellRenderer;


public class QuanLyKhoPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private KhoMayTinhBUS khoMayTinhBUS = new KhoMayTinhBUS();
    private KhoThucAnBUS khoThucAnBUS = new KhoThucAnBUS();

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
        String[] columns = {"Tên nhà cung cấp", "Tên thức ăn", "Số lượng tồn kho"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        // Thêm dữ liệu từ cơ sở dữ liệu vào bảng
        ArrayList<KhoThucAnDTO> khoThucAnList = new KhoThucAnDAO().getAllKhoThucAn();
        for (KhoThucAnDTO khoThucAn : khoThucAnList) {
            // Lấy tên nhà cung cấp từ mã nhà cung cấp
            tableModel.addRow(new Object[]{
                khoThucAnBUS.getTenNCC(khoThucAn.getmaNCC()),
                khoThucAnBUS.getTenThucAn(khoThucAn.getMaThucAn()),
                khoThucAn.getSoLuong()
            });
        }
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

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}