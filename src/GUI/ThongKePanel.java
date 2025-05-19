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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class ThongKePanel extends JPanel {
    private JComboBox<Integer> cboNam;
    private ChartPanel chartPanel;

    private ArrayList<DonHangThucAnDTO> listDonHang;
    private ArrayList<SuDungMayDTO> listSuDungMay;
    private ArrayList<PhieuNhapDTO> listPhieuNhap;

    public ThongKePanel() {
        // Lấy danh sách từ BUS
        listDonHang = new HoaDonThucAnBUS().selectAll();
        listSuDungMay = new SuDungMayDAO().selectAll();
        listPhieuNhap = new PhieuNhapBUS().getAll();

        setLayout(new BorderLayout());

        cboNam = new JComboBox<>();
        cboNam.setPreferredSize(new Dimension(150, 30));
        loadNamVaoComboBox();
        cboNam.addActionListener(e -> updateChart());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Chọn năm:"));
        topPanel.add(cboNam);
        add(topPanel, BorderLayout.NORTH);

        if (cboNam.getItemCount() > 0) {
            chartPanel = new ChartPanel(createChart((int) cboNam.getSelectedItem()));
        } else {
            chartPanel = new ChartPanel(null);
        }

        add(chartPanel, BorderLayout.CENTER);
    }

    private void loadNamVaoComboBox() {
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

        for (int y : years) {
            cboNam.addItem(y);
        }

        if (!years.isEmpty()) {
            cboNam.setSelectedItem(Collections.max(years));
        }
    }

    private void updateChart() {
        int nam = (int) cboNam.getSelectedItem();
        JFreeChart chart = createChart(nam);
        chartPanel.setChart(chart);
    }

    private JFreeChart createChart(int nam) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double[] doanhThuQuy = new double[4];

        for (DonHangThucAnDTO d : listDonHang) {
            if (d.getNgayDat() != null) {
                LocalDateTime dt = d.getNgayDat().toLocalDateTime();
                if (dt.getYear() == nam) {
                    int quy = (dt.getMonthValue() - 1) / 3;
                    doanhThuQuy[quy] += d.getTongTien();
                }
            }
        }

        for (SuDungMayDTO s : listSuDungMay) {
            if (s.getThoiGianBatDau() != null) {
                LocalDateTime dt = s.getThoiGianBatDau().toLocalDateTime();
                if (dt.getYear() == nam) {
                    int quy = (dt.getMonthValue() - 1) / 3;
                    doanhThuQuy[quy] += s.getChiPhi();
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 4; i++) {
    dataset.addValue(doanhThuQuy[i], "Doanh thu", "Quý " + (i + 1));
    
}

JFreeChart lineChart = ChartFactory.createLineChart(
    "Doanh thu theo quý - " + nam,
    "Quý",
    "Doanh thu (VND)",
    dataset,
    PlotOrientation.VERTICAL,
    true, true, false
);

// Tùy chỉnh giao diện
CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
LineAndShapeRenderer renderer = new LineAndShapeRenderer();

// Hiện dấu chấm tròn tại từng điểm
renderer.setSeriesShapesVisible(0, true);

// Đảm bảo hình tròn được tô kín (mặc định đã tô, nên dòng dưới có thể bỏ qua nếu báo lỗi)
renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-4.0, -4.0, 8.0, 8.0));

// Màu và độ dày
renderer.setSeriesPaint(0, new Color(40, 120, 220));
renderer.setSeriesStroke(0, new BasicStroke(2.5f));

// Áp dụng renderer vào biểu đồ
plot.setRenderer(renderer);

// Nền trắng, tắt đường viền
plot.setOutlineVisible(false);
plot.setBackgroundPaint(Color.white);
plot.setRangeGridlinePaint(Color.lightGray);

return lineChart;

}
}