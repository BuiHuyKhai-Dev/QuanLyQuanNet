/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import DAL.DatMayDAL;
import DAO.SuDungMayDAO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import DTO.MayTinhDTO;
import DTO.SuDungMayDTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

public class SuDungMayGUI extends JPanel {
    private JPanel panelMay;
    private JTable tableLichSu;
    private JScrollPane scrollPane;
    private int maMayDaChon = -1;

    public SuDungMayGUI() {
        setLayout(new BorderLayout());

        // Panel chứa các máy
        panelMay = new JPanel();
        panelMay.setLayout(new GridLayout(0, 4, 10, 10)); // 4 cột
        panelMay.setBackground(Color.WHITE);
        add(panelMay, BorderLayout.NORTH);

        // Bảng lịch sử đặt máy
        tableLichSu = new JTable();
        scrollPane = new JScrollPane(tableLichSu);
        scrollPane.setVisible(false);
        add(scrollPane, BorderLayout.CENTER); // Chuyển vị trí vào giữa

        // Load danh sách máy
        hienThiDanhSachMay();
    } 

    private void hienThiDanhSachMay() {
        DatMayDAL mayDAO = new DatMayDAL();
        ArrayList<MayTinhDTO> danhSachMay = mayDAO.selectAll();

        for (MayTinhDTO may : danhSachMay) {
            JPanel mayPanel = new JPanel();
            mayPanel.setLayout(new BorderLayout());
            mayPanel.setPreferredSize(new Dimension(120, 100));
            mayPanel.setBackground(Color.WHITE);
            mayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel lblTenMay = new JLabel(may.getTenMay(), SwingConstants.CENTER);
            lblTenMay.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

            JButton btnXemLichSu = new JButton("Xem lịch sử");
            btnXemLichSu.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
            btnXemLichSu.addActionListener(e -> {
                maMayDaChon = may.getMaMay();
                hienThiLichSuDatMay();
            });

            mayPanel.add(lblTenMay, BorderLayout.CENTER);
            mayPanel.add(btnXemLichSu, BorderLayout.SOUTH);

            panelMay.add(mayPanel);
        }

        revalidate();
        repaint();
    }
    

    public void hienThiLichSuDatMay() {
         if (maMayDaChon == -1) return;

    SuDungMayDAO suDungDAO = new SuDungMayDAO();
    ArrayList<SuDungMayDTO> lichSu = suDungDAO.selectAll();

    // Lọc theo máy được chọn
    ArrayList<SuDungMayDTO> lichSuMay = new ArrayList<>();
    for (SuDungMayDTO s : lichSu) {
        if (s.getMaMay() == maMayDaChon) {
            lichSuMay.add(s);
        }
    }

    // Khai báo lại data và columnNames
    String[] columnNames = {"Mã SD", "Mã NV", "Mã KH", "Thời gian bắt đầu", "Thời gian kết thúc", "Tổng TG", "Chi phí", "Mã PM"};
    String[][] data = new String[lichSuMay.size()][7];

    for (int i = 0; i < lichSuMay.size(); i++) {
        SuDungMayDTO s = lichSuMay.get(i);
        data[i][0] = String.valueOf(s.getMaSuDung());
        data[i][0] = String.valueOf(s.getMaNV());
        data[i][1] = String.valueOf(s.getMaKhachHang());
        data[i][2] = s.getThoiGianBatDau().toString();
        data[i][3] = (s.getThoiGianKetThuc() == null) ? "Đang sử dụng" : s.getThoiGianKetThuc().toString();
        data[i][4] = String.valueOf(s.getTongThoiGian());
        data[i][5] = String.valueOf(s.getChiPhi());
    }
    // Tạo bảng
    JTable table = new JTable(data, columnNames);
    JScrollPane scroll = new JScrollPane(table);

    // Tạo nút xuất hóa đơn, ban đầu ẩn
    JButton btnXuatHD = new JButton("Xuất hóa đơn");
    btnXuatHD.setVisible(false);

    // Lắng nghe sự kiện chọn dòng
    table.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
            btnXuatHD.setVisible(true); // Hiện nút khi có dòng được chọn
        }
    });

    // Gắn sự kiện cho nút
    btnXuatHD.addActionListener(e -> {
        int row = table.getSelectedRow();
        if (row != -1) {
            // Lấy dữ liệu dòng được chọn
            String maSD = data[row][0];
            String maKH = data[row][1];
            String batDau = data[row][2];
            String ketThuc = data[row][3];
            String tongTG = data[row][4];
            String chiPhi = data[row][5];

            // Gọi hàm xuất file PDF
            xuatHoaDonPDF(maSD, maKH, batDau, ketThuc, tongTG, chiPhi);
        }
    });

    // Tạo panel chứa bảng và nút
    JPanel panelDialog = new JPanel(new BorderLayout());
    panelDialog.add(scroll, BorderLayout.CENTER);
    panelDialog.add(btnXuatHD, BorderLayout.SOUTH);

    // Tạo dialog
    JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Lịch sử sử dụng máy " + maMayDaChon, true);
    dialog.setSize(800, 400);
    dialog.setLocationRelativeTo(this);
    dialog.add(panelDialog);
    dialog.setVisible(true);

}
    
    public void xuatHoaDonPDF(String maSD, String maKH, String batDau, String ketThuc, String tongTG, String chiPhi) {
    Document document = new Document();
try {
    String filePath = "hoadon_" + maSD + ".pdf";
    PdfWriter.getInstance(document, new FileOutputStream(filePath));
    document.open();

    // Font hỗ trợ tiếng Việt
    BaseFont baseFont = BaseFont.createFont("Font/font-times-new-roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    Font fontTitle = new Font(baseFont, 18, Font.BOLD);
    Font fontBold = new Font(baseFont, 12, Font.BOLD);
    Font fontNormal = new Font(baseFont, 12, Font.NORMAL);

    // Tiêu đề
    Paragraph title = new Paragraph("HÓA ĐƠN SỬ DỤNG MÁY", fontTitle);
    title.setAlignment(Element.ALIGN_CENTER);
    document.add(title);
    document.add(new Paragraph(" ")); // Khoảng cách

    // Thông tin khách hàng và mã hóa đơn
    Paragraph info = new Paragraph();
    info.setFont(fontNormal);
    info.add("Tên khách hàng: " + maKH + "\n");
    info.add("Mã hóa đơn: " + maSD + "\n");
    document.add(info);
    document.add(new Paragraph(" "));

    // Bảng chi tiết sử dụng
    PdfPTable table = new PdfPTable(2);
    table.setWidthPercentage(100);
    table.setSpacingBefore(10f);
    table.setSpacingAfter(10f);
    table.setWidths(new float[]{2f, 3f});

    // Tiêu đề bảng
    PdfPCell header1 = new PdfPCell(new Phrase("Thông tin", fontBold));
    header1.setBackgroundColor(BaseColor.LIGHT_GRAY);
    header1.setPadding(8);
    header1.setHorizontalAlignment(Element.ALIGN_CENTER);
    header1.setBorderWidthBottom(2);

    PdfPCell header2 = new PdfPCell(new Phrase("Chi tiết", fontBold));
    header2.setBackgroundColor(BaseColor.LIGHT_GRAY);
    header2.setPadding(8);
    header2.setHorizontalAlignment(Element.ALIGN_CENTER);
    header2.setBorderWidthBottom(2);

    table.addCell(header1);
    table.addCell(header2);

    // Nội dung bảng
    table.addCell(createCell("Thời gian bắt đầu:", fontNormal, Element.ALIGN_LEFT));
    table.addCell(createCell(batDau, fontNormal, Element.ALIGN_LEFT));

    table.addCell(createCell("Thời gian kết thúc:", fontNormal, Element.ALIGN_LEFT));
    table.addCell(createCell(ketThuc, fontNormal, Element.ALIGN_LEFT));

    table.addCell(createCell("Tổng thời gian (phút):", fontNormal, Element.ALIGN_LEFT));
    table.addCell(createCell(tongTG, fontNormal, Element.ALIGN_LEFT));

    document.add(table);

    // Tổng chi phí
    Paragraph total = new Paragraph("Tổng chi phí: " + chiPhi + " VNĐ", fontBold);
    total.setAlignment(Element.ALIGN_RIGHT);
    document.add(total);
    
     Paragraph footer = new Paragraph("Cảm ơn quý khách đã sử dụng dịch vụ!", fontNormal);
        footer.setSpacingBefore(20);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

    document.close();
    JOptionPane.showMessageDialog(this, "Đã xuất hóa đơn ra file: " + filePath);
} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Lỗi khi xuất hóa đơn!");
}

}
    private PdfPCell createCell(String text, Font font, int align) {
    PdfPCell cell = new PdfPCell(new Phrase(text, font));
    cell.setPadding(8);
    cell.setHorizontalAlignment(align);
    cell.setBorderColor(BaseColor.LIGHT_GRAY);
    return cell;
}


}
