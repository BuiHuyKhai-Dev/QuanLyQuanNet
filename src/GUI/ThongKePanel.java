package GUI;

import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.*;

import DTO.DonHangThucAnDTO;
import DTO.PhieuNhapDTO;
import DTO.SuDungMayDTO;
import BUS.HoaDonThucAnBUS;
import DAO.SuDungMayDAO;
import BUS.PhieuNhapBUS;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class ThongKePanel extends JPanel {
    private JComboBox<Integer> cboNam;
    private JComboBox<String> cboKieuThongKe;
    private JLabel lblTongDoanhThu;
    private JButton btnLamMoi;
    private ChartPanel chartPanel;

    private ArrayList<DonHangThucAnDTO> listDonHang;
    private ArrayList<SuDungMayDTO> listSuDungMay;
    private ArrayList<PhieuNhapDTO> listPhieuNhap;

    public ThongKePanel() {
        loadData();

        setLayout(new BorderLayout());

        cboNam = new JComboBox<>();
        cboNam.setPreferredSize(new Dimension(100, 30));
        cboNam.addActionListener(e -> updateChart()); // thêm dòng này
        cboNam.setPreferredSize(new Dimension(100, 30));

        cboKieuThongKe = new JComboBox<>(new String[] { "Theo quý", "Theo năm (5 năm gần nhất)" });
        cboKieuThongKe.setPreferredSize(new Dimension(200, 30));
        cboKieuThongKe.addActionListener(e -> updateChart());

        lblTongDoanhThu = new JLabel("Tổng doanh thu: 0 VND");

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.addActionListener(e -> {
            loadData();
            loadNamVaoComboBox();
            updateChart();
        });

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Chọn năm:"));
        topPanel.add(cboNam);
        topPanel.add(cboKieuThongKe);
        topPanel.add(btnLamMoi);
        topPanel.add(lblTongDoanhThu);

        add(topPanel, BorderLayout.NORTH);

        chartPanel = new ChartPanel(null);
        add(chartPanel, BorderLayout.CENTER);

        loadNamVaoComboBox();
        updateChart();
    }

    private void loadData() {
        listDonHang = new HoaDonThucAnBUS().selectAll();
        listSuDungMay = new SuDungMayDAO().selectAll();
        listPhieuNhap = new PhieuNhapBUS().getAll();
    }

    private void loadNamVaoComboBox() {
        cboNam.removeAllItems();
        Set<Integer> years = new TreeSet<>();

        for (DonHangThucAnDTO d : listDonHang) {
            if (d.getNgayDat() != null)
                years.add(d.getNgayDat().toLocalDateTime().getYear());
        }

        for (SuDungMayDTO s : listSuDungMay) {
            if (s.getThoiGianBatDau() != null)
                years.add(s.getThoiGianBatDau().toLocalDateTime().getYear());
        }

        for (PhieuNhapDTO p : listPhieuNhap) {
            try {
                Timestamp ts = Timestamp.valueOf(p.getThoiGianTao());
                years.add(ts.toLocalDateTime().getYear());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int currentYear = LocalDateTime.now().getYear();
        for (int i = 4; i >= 0; i--) {
            int year = currentYear - i;
            if (years.contains(year)) {
                cboNam.addItem(year);
            }
        }

        if (cboNam.getItemCount() > 0) {
            cboNam.setSelectedItem(currentYear);
        }
    }

    private void updateChart() {
        if (cboNam.getSelectedItem() == null) return;

        int nam = (int) cboNam.getSelectedItem();
        String kieu = (String) cboKieuThongKe.getSelectedItem();

        JFreeChart chart = kieu.equals("Theo quý") ? createChartTheoQuy(nam) : createChartTheoNam();
        chartPanel.setChart(chart);
    }

    private JFreeChart createChartTheoQuy(int nam) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double[] doanhThuQuy = new double[4];
        double tong = 0;

        for (DonHangThucAnDTO d : listDonHang) {
            if (d.getNgayDat() != null) {
                LocalDateTime dt = d.getNgayDat().toLocalDateTime();
                if (dt.getYear() == nam) {
                    int quy = (dt.getMonthValue() - 1) / 3;
                    doanhThuQuy[quy] += d.getTongTien();
                    tong += d.getTongTien();
                }
            }
        }

        for (SuDungMayDTO s : listSuDungMay) {
            if (s.getThoiGianBatDau() != null) {
                LocalDateTime dt = s.getThoiGianBatDau().toLocalDateTime();
                if (dt.getYear() == nam) {
                    int quy = (dt.getMonthValue() - 1) / 3;
                    doanhThuQuy[quy] += s.getChiPhi();
                    tong += s.getChiPhi();
                }
            }
        }

        for (PhieuNhapDTO p : listPhieuNhap) {
            try {
                Timestamp ts = Timestamp.valueOf(p.getThoiGianTao());
                LocalDateTime dt = ts.toLocalDateTime();
                if (dt.getYear() == nam) {
                    int quy = (dt.getMonthValue() - 1) / 3;
                    doanhThuQuy[quy] -= p.getTongTien();
                    tong -= p.getTongTien();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 4; i++) {
            dataset.addValue(doanhThuQuy[i], "Doanh thu", "Quý " + (i + 1));
        }

        lblTongDoanhThu.setText("Tổng doanh thu năm " + nam + ": " + String.format("%,.0f VND", tong));
        return taoBieuDo(dataset, "Doanh thu theo quý - " + nam, "Quý");
    }

    private JFreeChart createChartTheoNam() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double tong = 0;
        int currentYear = LocalDateTime.now().getYear();

        for (int year = currentYear - 4; year <= currentYear; year++) {
            double doanhThuNam = 0;

            for (DonHangThucAnDTO d : listDonHang) {
                if (d.getNgayDat() != null && d.getNgayDat().toLocalDateTime().getYear() == year) {
                    doanhThuNam += d.getTongTien();
                }
            }

            for (SuDungMayDTO s : listSuDungMay) {
                if (s.getThoiGianBatDau() != null && s.getThoiGianBatDau().toLocalDateTime().getYear() == year) {
                    doanhThuNam += s.getChiPhi();
                }
            }

            for (PhieuNhapDTO p : listPhieuNhap) {
                try {
                    Timestamp ts = Timestamp.valueOf(p.getThoiGianTao());
                    if (ts.toLocalDateTime().getYear() == year) {
                        doanhThuNam -= p.getTongTien();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            tong += doanhThuNam;
            dataset.addValue(doanhThuNam, "Doanh thu", String.valueOf(year));
        }

        lblTongDoanhThu.setText("Tổng doanh thu 5 năm gần đây: " + String.format("%,.0f VND", tong));
        return taoBieuDo(dataset, "Doanh thu theo năm (5 năm gần đây)", "Năm");
    }

    private JFreeChart taoBieuDo(DefaultCategoryDataset dataset, String tieuDe, String trucX) {
        JFreeChart lineChart = ChartFactory.createLineChart(
            tieuDe,
            trucX,
            "Doanh thu (VND)",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );

        CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();

        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-4.0, -4.0, 8.0, 8.0));
        renderer.setSeriesPaint(0, new Color(40, 120, 220));
        renderer.setSeriesStroke(0, new BasicStroke(2.5f));

        plot.setRenderer(renderer);
        plot.setOutlineVisible(false);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.lightGray);

        return lineChart;
    }
}
