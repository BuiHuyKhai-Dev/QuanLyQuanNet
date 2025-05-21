package GUI;

import BUS.NhaCungCapBUS;
import BUS.NhanVienBUS;
import BUS.PhieuNhapBUS;
import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.Locale;

public class PhieuNhapPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();

    public PhieuNhapPanel() {
        setLayout(new BorderLayout());

        // Thanh menu phía trên
        JPanel topPanel = new JPanel(new BorderLayout());

        // Các nút chức năng phía trái
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Thêm");
        JButton btnDelete = new JButton("Xóa");
        JButton btnDetail = new JButton("Chi tiết");
        JButton btnRefresh = new JButton("Làm mới");
        JButton btnPrintPDF = new JButton("In PDF");
        
        btnPrintPDF.addActionListener(e -> xuatPhieuNhapPDF());

        leftPanel.add(btnAdd);
        leftPanel.add(btnDelete);
        leftPanel.add(btnDetail);
        leftPanel.add(btnPrintPDF);
        leftPanel.add(btnRefresh);

        // ComboBox sắp xếp, ô tìm kiếm và lọc thời gian phía phải
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"Tất cả", "Sắp xếp theo tổng tiền", "Sắp xếp theo ngày nhập"});
        JTextField searchField = new JTextField(15);
        JButton btnSearch = new JButton("Tìm kiếm");

        JLabel lblFromDate = new JLabel("Từ:");
        JDateChooser fromDateChooser = new JDateChooser();
        fromDateChooser.setDateFormatString("yyyy-MM-dd");

        JLabel lblToDate = new JLabel("Đến:");
        JDateChooser toDateChooser = new JDateChooser();
        toDateChooser.setDateFormatString("yyyy-MM-dd");

        JButton btnFilter = new JButton("Lọc");

        rightPanel.add(sortComboBox);
        rightPanel.add(searchField);
        rightPanel.add(btnSearch);
        rightPanel.add(lblFromDate);
        rightPanel.add(fromDateChooser);
        rightPanel.add(lblToDate);
        rightPanel.add(toDateChooser);
        rightPanel.add(btnFilter);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Bảng hiển thị dữ liệu
        String[] columns = {"Mã Phiếu Nhập", "Nhà Cung Cấp", "Nhân viên nhập", "Tổng tiền", "Ngày nhập"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 25));
        //Lấy dữ liệu từ cơ sở dữ liệu
        PhieuNhapBUS  phieuNhapBUS = new PhieuNhapBUS();
        ArrayList<PhieuNhapDTO> listPhieuNhap = phieuNhapBUS.getAll();
        for (PhieuNhapDTO phieuNhap : listPhieuNhap) {
            tableModel.addRow(new Object[]{
                    phieuNhap.getMaPN(),
                    nhaCungCapBUS.getTenNCC(phieuNhap.getMaNCC()),
                    nhanVienBUS.getTenNV(phieuNhap.getMaNV()),
                    phieuNhap.getTongTien(),
                    phieuNhap.getThoiGianTao()
            });
        }
        table.setModel(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null); // Không cho phép chỉnh sửa trực tiếp trên bảng
        table.setFillsViewportHeight(true);
        // Căn chỉnh chữ ở giữa ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Xử lý sự kiện các nút
        btnAdd.addActionListener(e -> {
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm phiếu nhập", true);
            dialog.setContentPane(new PhieuNhapAddPanel());
            dialog.pack();
            dialog.setSize(1200, 800); // hoặc điều chỉnh kích thước phù hợp
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });
        btnDelete.addActionListener(e -> deleteSelectedRow());
        btnDetail.addActionListener(e -> showDetailDialog());
        btnSearch.addActionListener(e -> searchByKeyword(searchField.getText()));
        btnFilter.addActionListener(e -> filterByDate(fromDateChooser, toDateChooser));
        btnRefresh.addActionListener(e -> 
            reloadTable() // Gọi hàm làm mới bảng
        );
    }

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maPhieuNhap = (tableModel.getValueAt(selectedRow, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phiếu nhập #" + maPhieuNhap + " không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        else{
        tableModel.removeRow(selectedRow);
        PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();
        // Confirm deletion
        // Xóa phiếu nhập
        if (phieuNhapDAO.delete(maPhieuNhap) == 1) {
            JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        }
    }

    private void showDetailDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy thông tin phiếu nhập từ bảng
        int maPhieuNhap = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String tenNCC = tableModel.getValueAt(selectedRow, 1).toString();
        String tenNV = tableModel.getValueAt(selectedRow, 2).toString();
        String tongTien = tableModel.getValueAt(selectedRow, 3).toString();

        // Lấy danh sách chi tiết phiếu nhập từ DAO
        ArrayList<DTO.ChiTietPhieuNhapDTO> chiTietList = new DAO.ChiTietPhieuNhapDAO().selectByMaPN(maPhieuNhap);

        // Tạo dialog hiển thị chi tiết phiếu nhập
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this));
        dialog.setTitle("Chi tiết phiếu nhập #" + maPhieuNhap);
        dialog.setSize(650, 500);
        dialog.setLayout(new BorderLayout(10, 10));

        // Panel thông tin chung phiếu nhập
        JPanel infoPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu nhập"));
        infoPanel.add(new JLabel("Mã phiếu nhập:"));
        infoPanel.add(new JLabel(String.valueOf(maPhieuNhap)));
        infoPanel.add(new JLabel("Nhà cung cấp:"));
        infoPanel.add(new JLabel(tenNCC));
        infoPanel.add(new JLabel("Nhân viên nhập:"));
        infoPanel.add(new JLabel(tenNV));
        infoPanel.add(new JLabel("Tổng tiền:"));
        infoPanel.add(new JLabel(tongTien));
        dialog.add(infoPanel, BorderLayout.NORTH);

        // Bảng chi tiết phiếu nhập
        String[] columns = {"Mã Thức Ăn", "Tên Thức Ăn", "Đơn Giá", "Số Lượng", "Thành Tiền"};
        DefaultTableModel detailModel = new DefaultTableModel(columns, 0);
        for (DTO.ChiTietPhieuNhapDTO ct : chiTietList) {
            String tenThucAn = new BUS.ThucAnBUS().getTenThucAn(ct.getMaThucAn());
            detailModel.addRow(new Object[]{
                ct.getMaThucAn(),
                tenThucAn,
                ct.getDonGia(),
                ct.getSoLuong(),
                ct.getThanhTien()
            });
        }
        JTable detailTable = new JTable(detailModel);
        detailTable.setRowHeight(28);
        JScrollPane scrollPane = new JScrollPane(detailTable);

        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(ev -> dialog.dispose());
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnClose);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void searchByKeyword(String keyword) {
        // Logic tìm kiếm theo từ khóa
        JOptionPane.showMessageDialog(this, "Tìm kiếm với từ khóa: " + keyword, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void filterByDate(JDateChooser fromDateChooser, JDateChooser toDateChooser) {
        String fromDate = fromDateChooser.getDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(fromDateChooser.getDate()) : "";
        String toDate = toDateChooser.getDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(toDateChooser.getDate()) : "";

        if (fromDate.isEmpty() || toDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn cả ngày bắt đầu và ngày kết thúc!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Logic lọc theo thời gian
        JOptionPane.showMessageDialog(this, "Lọc từ ngày: " + fromDate + " đến ngày: " + toDate, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    // Hàm này sẽ được gọi khi cần làm mới bảng
    public void reloadTable() {
    tableModel.setRowCount(0);
    ArrayList<PhieuNhapDTO> listPhieuNhap = new PhieuNhapBUS().getAll();
    for (PhieuNhapDTO phieuNhap : listPhieuNhap) {
        tableModel.addRow(new Object[]{
            phieuNhap.getMaPN(),
            nhaCungCapBUS.getTenNCC(phieuNhap.getMaNCC()),
            nhanVienBUS.getTenNV(phieuNhap.getMaNV()),
            phieuNhap.getTongTien(),
            phieuNhap.getThoiGianTao()
        });
    }
}
private void xuatPhieuNhapPDF() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để in!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int maPN = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String tenNCC = tableModel.getValueAt(selectedRow, 1).toString();
        String tenNV = tableModel.getValueAt(selectedRow, 2).toString();
        double tongTien = Double.parseDouble(tableModel.getValueAt(selectedRow, 3).toString());
        String ngayNhap = tableModel.getValueAt(selectedRow, 4).toString();

        ArrayList<DTO.ChiTietPhieuNhapDTO> chiTietList = new DAO.ChiTietPhieuNhapDAO().selectByMaPN(maPN);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu phiếu nhập dưới dạng PDF");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection != JFileChooser.APPROVE_OPTION) return;

        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        if (!filePath.endsWith(".pdf")) {
            filePath += ".pdf";
        }

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Font tiếng Việt
        BaseFont baseFont = BaseFont.createFont("src/GUI/font-times-new-roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontTitle = new Font(baseFont, 18, Font.BOLD);
        Font fontBold = new Font(baseFont, 12, Font.BOLD);
        Font fontNormal = new Font(baseFont, 12);

        // Tiêu đề
        Paragraph title = new Paragraph("HÓA ĐƠN PHIẾU NHẬP", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        // Thông tin chung
        Paragraph info = new Paragraph();
        info.setFont(fontNormal);
        info.add("Mã phiếu nhập: " + maPN + "\n");
        info.add("Nhà cung cấp: " + tenNCC + "\n");
        info.add("Nhân viên nhập: " + tenNV + "\n");
        info.add("Ngày nhập: " + ngayNhap + "\n");
        document.add(info);
        document.add(new Paragraph(" "));

        // Bảng chi tiết
        PdfPTable tableCT = new PdfPTable(5);
        tableCT.setWidthPercentage(100);
        tableCT.setWidths(new int[]{2, 4, 2, 3, 3});
        tableCT.setSpacingBefore(10f);
        tableCT.setSpacingAfter(10f);

        // Tiêu đề bảng có màu
        String[] headers = {"Mã PN", "Mã Thức Ăn", "Số Lượng", "Đơn Giá", "Thành Tiền"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, fontBold));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPadding(8);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderWidthBottom(2);
            tableCT.addCell(cell);
        }

        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        for (DTO.ChiTietPhieuNhapDTO ct : chiTietList) {
            tableCT.addCell(createCell(String.valueOf(ct.getMaPN()), fontNormal, Element.ALIGN_CENTER));
            tableCT.addCell(createCell(String.valueOf(ct.getMaThucAn()), fontNormal, Element.ALIGN_CENTER));
            tableCT.addCell(createCell(String.valueOf(ct.getSoLuong()), fontNormal, Element.ALIGN_CENTER));
            tableCT.addCell(createCell(currencyVN.format(ct.getDonGia()), fontNormal, Element.ALIGN_RIGHT));
            tableCT.addCell(createCell(currencyVN.format(ct.getThanhTien()), fontNormal, Element.ALIGN_RIGHT));
        }

        document.add(tableCT);

        // Tổng tiền
        Paragraph total = new Paragraph("Tổng tiền: " + currencyVN.format(tongTien), fontBold);
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);

        // Footer
        Paragraph footer = new Paragraph("Cảm ơn quý khách!", fontNormal);
        footer.setSpacingBefore(20);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();

        JOptionPane.showMessageDialog(this, "Xuất file PDF thành công tại:\n" + filePath, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception ex) {
        ex.printStackTrace();
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        JOptionPane.showMessageDialog(this, "Lỗi khi in hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}

// Dùng lại hàm hỗ trợ giống hóa đơn sử dụng máy
private PdfPCell createCell(String text, Font font, int align) {
    PdfPCell cell = new PdfPCell(new Phrase(text, font));
    cell.setPadding(8);
    cell.setHorizontalAlignment(align);
    cell.setBorderColor(BaseColor.LIGHT_GRAY);
    return cell;
}

}