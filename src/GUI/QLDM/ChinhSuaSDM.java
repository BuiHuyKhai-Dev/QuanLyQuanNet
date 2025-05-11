
package GUI.QLDM;

import DAO.SuDungMayDAO;
import DTO.SuDungMayDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.InputStream;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.itextpdf.text.Font;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class ChinhSuaSDM extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JButton btnSua, btnXoa, btnTimKiem, btnXuat;
    private SuDungMayDAO suDungMayDAO = new SuDungMayDAO();

    public ChinhSuaSDM() {
        setLayout(new BorderLayout());

        String[] columnNames = {
            "Mã SD", "Mã NV", "Mã KH", "Mã Máy",
            "Thời gian bắt đầu", "Thời gian kết thúc",
            "Tổng TG (giờ)", "Chi phí (VNĐ)"
        };

        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadData();

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTimKiem = new JButton("Tìm kiếm");
        btnXuat = new JButton("Xuất hóa đơn");
        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lamMoiBang();
            }
        });

        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnTimKiem);
        panelButtons.add(btnXuat);
        panelButtons.add(btnLamMoi);

        add(panelButtons, BorderLayout.SOUTH);

        btnSua.addActionListener(e -> suaDong());
        btnXoa.addActionListener(e -> xoaDong());
        btnTimKiem.addActionListener(e -> timKiem());
        btnXuat.addActionListener(e -> xuatHoaDon());
    }

    private void loadData() {
        model.setRowCount(0);
        ArrayList<SuDungMayDTO> list = suDungMayDAO.selectAll();
        for (SuDungMayDTO s : list) {
            model.addRow(new Object[]{
                s.getMaSuDung(),
                s.getMaNV(),
                s.getMaKhachHang(),
                s.getMaMay(),
                s.getThoiGianBatDau(),
                s.getThoiGianKetThuc(),
                s.getTongThoiGian(),
                String.format("%,.2f", s.getChiPhi())
            });
        }
    }

    private void suaDong() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int maSD = (int) model.getValueAt(row, 0);
            JOptionPane.showMessageDialog(this, "Sửa dòng có mã SD: " + maSD);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa.");
        }
    }

    private void xoaDong() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa?", "Xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                model.removeRow(row);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa.");
        }
    }

    private void timKiem() {
    // Tạo panel nhập liệu
    JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

    JTextField tfMaSD = new JTextField();
    JTextField tfMaNV = new JTextField();
    JTextField tfMaKH = new JTextField();
    JTextField tfThoiGianTu = new JTextField();
    JTextField tfThoiGianDen = new JTextField();
    JTextField tfChiPhiTu = new JTextField();
    JTextField tfChiPhiDen = new JTextField();

    panel.add(new JLabel("Mã SD:"));
    panel.add(tfMaSD);
    panel.add(new JLabel("Mã NV:"));
    panel.add(tfMaNV);
    panel.add(new JLabel("Mã KH:"));
    panel.add(tfMaKH);
    panel.add(new JLabel("Thời gian từ (yyyy-MM-dd HH:mm):"));
    panel.add(tfThoiGianTu);
    panel.add(new JLabel("Thời gian đến (yyyy-MM-dd HH:mm):"));
    panel.add(tfThoiGianDen);
    panel.add(new JLabel("Chi phí từ:"));
    panel.add(tfChiPhiTu);
    panel.add(new JLabel("Chi phí đến:"));
    panel.add(tfChiPhiDen);

    int result = JOptionPane.showConfirmDialog(null, panel, "Tìm kiếm nâng cao",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        // Lấy dữ liệu nhập
        String maSD = tfMaSD.getText().trim();
        String maNV = tfMaNV.getText().trim();
        String maKH = tfMaKH.getText().trim();
        String tgTu = tfThoiGianTu.getText().trim();
        String tgDen = tfThoiGianDen.getText().trim();
        String cpTu = tfChiPhiTu.getText().trim();
        String cpDen = tfChiPhiDen.getText().trim();

        // Tải lại danh sách lọc
        model.setRowCount(0);
        ArrayList<SuDungMayDTO> list = suDungMayDAO.selectAll();

        for (SuDungMayDTO s : list) {
            boolean match = true;

            if (!maSD.isEmpty() && !String.valueOf(s.getMaSuDung()).equals(maSD)) match = false;
            if (!maNV.isEmpty() && s.getMaNV() != Integer.parseInt(maNV)) match = false;
            if (!maKH.isEmpty() && s.getMaKhachHang()!= Integer.parseInt(maKH)) match = false;

            if (!tgTu.isEmpty() || !tgDen.isEmpty()) {
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                    
                    // Lấy thời gian bắt đầu từ đối tượng s (kiểu Timestamp)
                    java.util.Date batDau = s.getThoiGianBatDau(); // đã là Timestamp

                    if (!tgTu.isEmpty()) {
                        java.util.Date from = sdf.parse(tgTu);
                        if (batDau.before(from)) match = false;
                    }

                    if (!tgDen.isEmpty()) {
                        java.util.Date to = sdf.parse(tgDen);
                        if (batDau.after(to)) match = false;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi định dạng thời gian. Định dạng đúng: yyyy-MM-dd HH:mm");
                    return;
                }
            }

            if (!cpTu.isEmpty()) {
                try {
                    double min = Double.parseDouble(cpTu);
                    if (s.getChiPhi() < min) match = false;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Chi phí từ không hợp lệ!");
                    return;
                }
            }

            if (!cpDen.isEmpty()) {
                try {
                    double max = Double.parseDouble(cpDen);
                    if (s.getChiPhi() > max) match = false;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Chi phí đến không hợp lệ!");
                    return;
                }
            }

            if (match) {
                model.addRow(new Object[]{
                    s.getMaSuDung(),
                    s.getMaKhachHang(),
                    s.getMaMay(),
                    s.getThoiGianBatDau(),
                    s.getThoiGianKetThuc(),
                    s.getTongThoiGian(),
                    String.format("%,.2f", s.getChiPhi())
                });
            }
        }
    }
}


    private void xuatHoaDon() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xuất hóa đơn.");
            return;
        }

        String maSD = model.getValueAt(row, 0).toString();
        String maKH = model.getValueAt(row, 1).toString();
        String batDau = model.getValueAt(row, 3).toString();
        String ketThuc = model.getValueAt(row, 4).toString();
        String tongTG = model.getValueAt(row, 5).toString();
        String chiPhi = model.getValueAt(row, 6).toString();

        xuatHoaDonPDF(maSD, maKH, batDau, ketThuc, tongTG, chiPhi);
    }

    public void xuatHoaDonPDF(String maSD, String maKH, String batDau, String ketThuc, String tongTG, String chiPhi) {
        Document document = new Document();
        try {
            String filePath = "hoadon_" + maSD + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            InputStream is = maytinh.class.getResourceAsStream("font-times-new-roman.ttf");
            BaseFont baseFont = BaseFont.createFont(
                "font-times-new-roman.ttf",
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED,
                true,
                is.readAllBytes(),
                null
            );
            Font fontTitle = new Font(baseFont, 18, Font.BOLD);
            Font fontBold = new Font(baseFont, 12, Font.BOLD);
            Font fontNormal = new Font(baseFont, 12, Font.NORMAL);

            Paragraph title = new Paragraph("HÓA ĐƠN SỬ DỤNG MÁY", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            Paragraph info = new Paragraph();
            info.setFont(fontNormal);
            info.add("Tên khách hàng: " + maKH + "\n");
            info.add("Mã hóa đơn: " + maSD + "\n");
            document.add(info);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[]{2f, 3f});

            table.addCell(createCell("Thông tin", fontBold, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));
            table.addCell(createCell("Chi tiết", fontBold, Element.ALIGN_CENTER, BaseColor.LIGHT_GRAY));

            table.addCell(createCell("Thời gian bắt đầu:", fontNormal, Element.ALIGN_LEFT));
            table.addCell(createCell(batDau, fontNormal, Element.ALIGN_LEFT));

            table.addCell(createCell("Thời gian kết thúc:", fontNormal, Element.ALIGN_LEFT));
            table.addCell(createCell(ketThuc, fontNormal, Element.ALIGN_LEFT));

            table.addCell(createCell("Tổng thời gian (giờ):", fontNormal, Element.ALIGN_LEFT));
            table.addCell(createCell(tongTG, fontNormal, Element.ALIGN_LEFT));

            document.add(table);

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

    private PdfPCell createCell(String content, Font font, int alignment) {
        return createCell(content, font, alignment, null);
    }

    private PdfPCell createCell(String content, Font font, int alignment, BaseColor bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(8);
        cell.setHorizontalAlignment(alignment);
        if (bgColor != null) {
            cell.setBackgroundColor(bgColor);
        }
        return cell;
    }
    private void lamMoiBang() {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0); // Xóa hết dòng cũ

    // Nạp lại dữ liệu mới (ví dụ từ danh sách `ds`)
    // for (SuDungMayDTO s : danhSachSuDungMay) {
    //     model.addRow(new Object[] {
    //         s.getMa(), s.getMaKH(), s.getMaMay(), s.getThoiGianBatDau(), s.getThoiGianKetThuc()
    //     });
    // }
    }
}
