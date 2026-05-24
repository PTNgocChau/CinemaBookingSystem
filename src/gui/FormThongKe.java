package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import dao.ThongKeDAO;

public class FormThongKe extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final Color BG_MAIN = new Color(245, 245, 245);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color PRIMARY = new Color(250, 193, 7);
    private static final Color INFO = new Color(33, 150, 243);
    private static final Color SUCCESS = new Color(76, 175, 80);
    private static final Color DANGER = new Color(244, 67, 54);
    private static final Color TEXT_DARK = new Color(33, 33, 33);
    private static final Color BORDER = new Color(220, 220, 220);

    private final ThongKeDAO dao = new ThongKeDAO();

    private JSpinner spTuNgay;
    private JSpinner spDenNgay;

    private JLabel lblDoanhThu;
    private JLabel lblSoVe;
    private JLabel lblSoSuat;
    private JLabel lblTyLe;

    private DefaultTableModel modelPhim;
    private DefaultTableModel modelNhanVien;
    private DefaultTableModel modelLapDay;

    private BarChartPanel chartPanel;

    public FormThongKe() {
        setLayout(new BorderLayout(0, 15));
        setBackground(BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(buildHeader(), BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 15));
        center.setOpaque(false);
        center.add(buildSummaryCards(), BorderLayout.NORTH);
        center.add(buildTabbedPane(), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        Calendar cal = Calendar.getInstance();
        spDenNgay.setValue(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -30);
        spTuNgay.setValue(cal.getTime());

        loadData();
    }

    private JPanel buildHeader() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel lblTitle = new JLabel("THỐNG KÊ VÀ BÁO CÁO");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(TEXT_DARK);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        filterPanel.setOpaque(false);

        spTuNgay = buildDateSpinner();
        spDenNgay = buildDateSpinner();

        JButton btnXem = new JButton("Xem thống kê");
        styleButton(btnXem, PRIMARY);
        btnXem.addActionListener(e -> loadData());

        filterPanel.add(new JLabel("Từ ngày:"));
        filterPanel.add(spTuNgay);
        filterPanel.add(new JLabel("Đến ngày:"));
        filterPanel.add(spDenNgay);
        filterPanel.add(btnXem);

        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.add(lblTitle, BorderLayout.WEST);
        row.add(filterPanel, BorderLayout.EAST);

        wrapper.add(row, BorderLayout.CENTER);
        wrapper.add(new JSeparator(), BorderLayout.SOUTH);
        return wrapper;
    }

    private JSpinner buildDateSpinner() {
        JSpinner sp = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(sp, "dd/MM/yyyy");
        sp.setEditor(editor);
        sp.setPreferredSize(new Dimension(120, 30));
        return sp;
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(TEXT_DARK);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker()),
                BorderFactory.createEmptyBorder(6, 14, 6, 14)));
    }

    private JPanel buildSummaryCards() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0));
        panel.setOpaque(false);

        lblDoanhThu = new JLabel("—", SwingConstants.CENTER);
        lblSoVe = new JLabel("—", SwingConstants.CENTER);
        lblSoSuat = new JLabel("—", SwingConstants.CENTER);
        lblTyLe = new JLabel("—", SwingConstants.CENTER);

        panel.add(buildCard("Doanh thu", lblDoanhThu, PRIMARY));
        panel.add(buildCard("Số vé đã bán", lblSoVe, INFO));
        panel.add(buildCard("Số suất chiếu", lblSoSuat, SUCCESS));
        panel.add(buildCard("Tỷ lệ lấp đầy TB", lblTyLe, DANGER));

        return panel;
    }

    private JPanel buildCard(String title, JLabel valueLabel, Color accent) {
        JPanel card = new JPanel(new BorderLayout(5, 8));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, accent),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        BorderFactory.createEmptyBorder(14, 14, 14, 14))));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 13));
        lblTitle.setForeground(new Color(100, 100, 100));

        valueLabel.setFont(new Font("Arial", Font.BOLD, 22));
        valueLabel.setForeground(accent);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JTabbedPane buildTabbedPane() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.PLAIN, 13));

        tabs.addTab("Doanh thu theo ngày", buildChartTab());
        tabs.addTab("Hiệu suất phim", buildPhimTab());
        tabs.addTab("Hiệu suất nhân viên", buildNhanVienTab());
        tabs.addTab("Tỷ lệ lấp đầy ghế", buildLapDayTab());

        return tabs;
    }

    private JPanel buildChartTab() {
        chartPanel = new BarChartPanel();
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CARD_BG);
        wrapper.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        wrapper.add(chartPanel, BorderLayout.CENTER);
        return wrapper;
    }

    private JScrollPane buildPhimTab() {
        String[] cols = {"Tên phim", "Thể loại", "Vé bán", "Doanh thu (VNĐ)", "Tỷ lệ lấp đầy (%)"};
        modelPhim = new DefaultTableModel(cols, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tbl = buildTable(modelPhim);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tbl.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tbl.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tbl.getColumnModel().getColumn(4).setCellRenderer(new ProgressBarRenderer());

        return new JScrollPane(tbl);
    }

    private JScrollPane buildNhanVienTab() {
        String[] cols = {"Hạng", "Nhân viên", "Chức vụ", "Vé bán", "Doanh thu (VNĐ)"};
        modelNhanVien = new DefaultTableModel(cols, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tbl = buildTable(modelNhanVien);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tbl.getColumnModel().getColumn(0).setMaxWidth(60);
        tbl.getColumnModel().getColumn(0).setCellRenderer(new RankCellRenderer());
        tbl.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tbl.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        return new JScrollPane(tbl);
    }

    private JScrollPane buildLapDayTab() {
        String[] cols = {"Mã suất", "Phim", "Ngày chiếu", "Tổng ghế", "Đã đặt", "Tỷ lệ lấp đầy (%)"};
        modelLapDay = new DefaultTableModel(cols, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tbl = buildTable(modelLapDay);
        tbl.getColumnModel().getColumn(5).setCellRenderer(new ProgressBarRenderer());

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tbl.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tbl.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tbl.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tbl.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        return new JScrollPane(tbl);
    }

    private JTable buildTable(DefaultTableModel model) {
        JTable tbl = new JTable(model);
        tbl.setFont(new Font("Arial", Font.PLAIN, 13));
        tbl.setRowHeight(32);
        tbl.setShowHorizontalLines(true);
        tbl.setShowVerticalLines(false);
        tbl.setGridColor(new Color(240, 240, 240));
        tbl.setIntercellSpacing(new Dimension(0, 1));
        tbl.setSelectionBackground(new Color(255, 243, 205));
        tbl.setSelectionForeground(TEXT_DARK);
        tbl.setAutoCreateRowSorter(true);

        JTableHeader header = tbl.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(245, 245, 245));
        header.setForeground(TEXT_DARK);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY));
        header.setReorderingAllowed(false);

        tbl.setDefaultRenderer(Object.class, new StripedRowRenderer());
        return tbl;
    }

    private void loadData() {
        Date tuNgay = new Date(((java.util.Date) spTuNgay.getValue()).getTime());
        Date denNgay = new Date(((java.util.Date) spDenNgay.getValue()).getTime());

        if (tuNgay.after(denNgay)) {
            JOptionPane.showMessageDialog(this,
                    "Ngày bắt đầu không được sau ngày kết thúc.",
                    "Lỗi lọc ngày",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Map<String, Object> kpi = dao.getTongQuan(tuNgay, denNgay);
        double doanhThu = toDouble(kpi.get("doanhThu"));
        int soVe = toInt(kpi.get("soVe"));
        int soSuat = toInt(kpi.get("soSuatChieu"));
        double tyLe = toDouble(kpi.get("tyLeLapDay"));

        lblDoanhThu.setText(formatMoney(doanhThu));
        lblSoVe.setText(String.valueOf(soVe));
        lblSoSuat.setText(String.valueOf(soSuat));
        lblTyLe.setText(String.format("%.1f%%", tyLe));

        chartPanel.setData(dao.getDoanhThuTheoNgay(tuNgay, denNgay));

        modelPhim.setRowCount(0);
        for (Object[] row : dao.getTopPhim(tuNgay, denNgay)) {
            modelPhim.addRow(new Object[] {
                    row[0],
                    row[1],
                    row[2],
                    formatMoney(toDouble(row[3])),
                    toDouble(row[4])
            });
        }

        modelNhanVien.setRowCount(0);
        for (Object[] row : dao.getHieuSuatNhanVien(tuNgay, denNgay)) {
            modelNhanVien.addRow(new Object[] {
                    row[0],
                    row[1],
                    row[2],
                    row[3],
                    formatMoney(toDouble(row[4]))
            });
        }

        modelLapDay.setRowCount(0);
        for (Object[] row : dao.getTyLeLapDay(tuNgay, denNgay)) {
            modelLapDay.addRow(new Object[] {
                    row[0],
                    row[1],
                    row[2],
                    row[3],
                    row[4],
                    toDouble(row[5])
            });
        }
    }

    private double toDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return 0.0;
    }

    private int toInt(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return 0;
    }

    private String formatMoney(double amount) {
        return new DecimalFormat("#,##0").format(amount) + " VNĐ";
    }

    class BarChartPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private List<Object[]> data = new ArrayList<>();

        void setData(List<Object[]> data) {
            this.data = data != null ? data : new ArrayList<>();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int padL = 90;
            int padR = 25;
            int padT = 45;
            int padB = 65;
            int chartW = w - padL - padR;
            int chartH = h - padT - padB;

            g2.setColor(CARD_BG);
            g2.fillRect(0, 0, w, h);

            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.setColor(TEXT_DARK);
            String chartTitle = "Doanh thu theo ngày (đơn vị: nghìn VNĐ)";
            FontMetrics fmTitle = g2.getFontMetrics();
            g2.drawString(chartTitle, (w - fmTitle.stringWidth(chartTitle)) / 2, 28);

            if (data.isEmpty()) {
                g2.setFont(new Font("Arial", Font.ITALIC, 14));
                g2.setColor(Color.GRAY);
                String msg = "Không có dữ liệu trong khoảng thời gian đã chọn";
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(msg, (w - fm.stringWidth(msg)) / 2, h / 2);
                return;
            }

            double maxVal = data.stream().mapToDouble(r -> ((Number) r[1]).doubleValue()).max().orElse(1);
            if (maxVal <= 0) {
                maxVal = 1;
            }

            int gridLines = 5;
            g2.setFont(new Font("Arial", Font.PLAIN, 11));
            for (int i = 0; i <= gridLines; i++) {
                int y = padT + chartH - (int) (chartH * i / (double) gridLines);
                g2.setColor(new Color(235, 235, 235));
                g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                        0, new float[] {4, 4}, 0));
                g2.drawLine(padL, y, padL + chartW, y);

                g2.setStroke(new BasicStroke(1));
                g2.setColor(new Color(130, 130, 130));
                String yLbl = String.format("%,.0fK", maxVal * i / gridLines / 1000.0);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(yLbl, padL - fm.stringWidth(yLbl) - 6, y + 4);
            }

            int n = data.size();
            int gap = Math.max(4, chartW / (n + 1) / 3);
            int barW = Math.max(10, (chartW - gap * (n + 1)) / n);

            for (int i = 0; i < n; i++) {
                Object[] row = data.get(i);
                double val = ((Number) row[1]).doubleValue();
                int soVe = ((Number) row[2]).intValue();
                int barH = (int) (chartH * val / maxVal);
                int x = padL + gap + i * (barW + gap);
                int y = padT + chartH - barH;

                GradientPaint gp = new GradientPaint(x, y, new Color(255, 214, 77), x, y + barH, PRIMARY);
                g2.setPaint(gp);
                g2.fillRoundRect(x, y, barW, barH, 6, 6);

                g2.setColor(PRIMARY.darker());
                g2.drawRoundRect(x, y, barW, barH, 6, 6);

                if (barH > 18) {
                    g2.setFont(new Font("Arial", Font.BOLD, 10));
                    g2.setColor(TEXT_DARK);
                    String veLbl = soVe + " vé";
                    FontMetrics fm = g2.getFontMetrics();
                    int lx = x + (barW - fm.stringWidth(veLbl)) / 2;
                    g2.drawString(veLbl, lx, y - 4);
                }

                String dateStr = row[0].toString();
                if (dateStr.length() >= 10) {
                    dateStr = dateStr.substring(5);
                }
                g2.setFont(new Font("Arial", Font.PLAIN, 10));
                g2.setColor(new Color(80, 80, 80));
                FontMetrics fmX = g2.getFontMetrics();
                AffineTransform orig = g2.getTransform();
                g2.translate(x + barW / 2.0, padT + chartH + 14);
                g2.rotate(Math.toRadians(-40));
                g2.drawString(dateStr, -fmX.stringWidth(dateStr) / 2, 0);
                g2.setTransform(orig);
            }

            g2.setColor(new Color(180, 180, 180));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(padL, padT, padL, padT + chartH);
            g2.drawLine(padL, padT + chartH, padL + chartW, padT + chartH);
        }
    }

    static class ProgressBarRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            JProgressBar bar = new JProgressBar(0, 100);
            bar.setStringPainted(true);
            bar.setOpaque(true);

            double pct = 0.0;
            if (value instanceof Number) {
                pct = ((Number) value).doubleValue();
            }

            bar.setValue((int) pct);
            bar.setString(String.format("%.1f%%", pct));

            if (pct >= 70) {
                bar.setForeground(SUCCESS);
                bar.setBackground(new Color(220, 245, 220));
            } else if (pct >= 40) {
                bar.setForeground(new Color(255, 152, 0));
                bar.setBackground(new Color(255, 243, 225));
            } else {
                bar.setForeground(DANGER);
                bar.setBackground(new Color(255, 235, 235));
            }

            if (isSelected) {
                bar.setBackground(new Color(255, 243, 205));
            }

            bar.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
            return bar;
        }
    }

    static class RankCellRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Arial", Font.BOLD, 13));
            if (!isSelected) {
                if (value instanceof Number && ((Number) value).intValue() <= 3) {
                    setForeground(PRIMARY.darker());
                } else {
                    setForeground(new Color(100, 100, 100));
                }
            }
            return this;
        }
    }

    static class StripedRowRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!isSelected) {
                setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 250, 252));
                setForeground(TEXT_DARK);
            }
            setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
            return this;
        }
    }
}