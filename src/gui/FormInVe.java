package gui;


import com.itextpdf.kernel.pdf.PdfWriter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;

public class FormInVe extends JFrame {

	private static final long serialVersionUID = 1L;
	private String tenPhim, maPhong, theLoai;
    private LocalDateTime thoiGian;
    private int thoiLuong;
    private ArrayList<String> gheDaChon;
    private double tongTien;

    private JPanel ticketPanel;

    public FormInVe(String tenPhim,
                    String maPhong,
                    int thoiLuong,
                    String theLoai,
                    LocalDateTime thoiGian,
                    ArrayList<String> gheDaChon,
                    double tongTien) {

        this.tenPhim = tenPhim;
        this.maPhong = maPhong;
        this.thoiLuong = thoiLuong;
        this.theLoai = theLoai;
        this.thoiGian = thoiGian;
        this.gheDaChon = gheDaChon;
        this.tongTien = tongTien;

        setTitle("VÉ XEM PHIM");
        setSize(400, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        ticketPanel = new JPanel();
        ticketPanel.setLayout(new BoxLayout(ticketPanel, BoxLayout.Y_AXIS));
        ticketPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        ticketPanel.setBackground(Color.WHITE);

        Font titleFont = new Font("Arial", Font.BOLD, 20);
        Font normalFont = new Font("Arial", Font.PLAIN, 14);

        // ===== TIÊU ĐỀ =====
        JLabel lblTitle = new JLabel("VÉ XEM PHIM");
        lblTitle.setFont(titleFont);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        ticketPanel.add(lblTitle);
        ticketPanel.add(Box.createVerticalStrut(8));
        ticketPanel.add(new JSeparator());
        ticketPanel.add(Box.createVerticalStrut(8));

        // ===== FORMAT DỮ LIỆU =====
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
        String thoiGianFormat = this.thoiGian.format(formatter);

        @SuppressWarnings("deprecation")
		NumberFormat vn = NumberFormat.getInstance(new Locale("vi", "VN"));
        String tongTienFormat = vn.format(this.tongTien);

        // ===== THÔNG TIN =====
        JPanel infoPanel = new JPanel(new GridLayout(6, 2, 5, 4));
        infoPanel.setBackground(Color.WHITE);

        addRow(infoPanel, "Tên phim:", this.tenPhim, normalFont);
        addRow(infoPanel, "Tên phòng:", this.maPhong, normalFont);
        addRow(infoPanel, "Thời lượng:", this.thoiLuong + " phút", normalFont);
        addRow(infoPanel, "Thể loại:", this.theLoai, normalFont);
        addRow(infoPanel, "Thời gian:", thoiGianFormat, normalFont);
        addRow(infoPanel, "Ghế:", String.join(", ", this.gheDaChon), normalFont);

        ticketPanel.add(infoPanel);
        ticketPanel.add(Box.createVerticalStrut(8));
        ticketPanel.add(new JSeparator());
        ticketPanel.add(Box.createVerticalStrut(12));

        // ===== TỔNG TIỀN =====
        JLabel lblTongTien = new JLabel("TỔNG TIỀN: " + tongTienFormat + " VNĐ");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongTien.setForeground(Color.RED);
        lblTongTien.setAlignmentX(Component.CENTER_ALIGNMENT);

        ticketPanel.add(lblTongTien);
        ticketPanel.add(Box.createVerticalStrut(15));

        // ===== NÚT IN =====
        JButton btnIn = new JButton("In");
        btnIn.addActionListener(e -> inVePDF());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(btnIn);

        add(ticketPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addRow(JPanel panel, String label, String value, Font font) {
        JLabel lbl1 = new JLabel("<html>" + label + "</html>");
        lbl1.setFont(font);

        JLabel lbl2 = new JLabel("<html>" + value + "</html>");
        lbl2.setFont(font);

        panel.add(lbl1);
        panel.add(lbl2);
    }
    
    private void inVePDF() {
        try {
            this.pack();
            this.setVisible(true);

            ticketPanel.revalidate();
            ticketPanel.repaint();

            int width = ticketPanel.getWidth();
            int height = ticketPanel.getHeight();

            if (width <= 0 || height <= 0) {
                Dimension size = ticketPanel.getPreferredSize();
                width = size.width;
                height = size.height;
                ticketPanel.setSize(width, height);
                ticketPanel.doLayout();
                ticketPanel.validate();
            }

            BufferedImage image = new BufferedImage(
                    width,
                    height,
                    BufferedImage.TYPE_INT_RGB
            );

            Graphics2D g2 = image.createGraphics();
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, width, height);
            ticketPanel.paint(g2);
            g2.dispose();

            File dir = new File("IN");
            if (!dir.exists()) dir.mkdirs();

            String filePath = "IN" + File.separator + "VeXemPhim.pdf";

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);

            ImageData data = ImageDataFactory.create(baos.toByteArray());

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            com.itextpdf.layout.element.Image pdfImage =
                    new com.itextpdf.layout.element.Image(data);

            pdfImage.setAutoScale(true);
            document.add(pdfImage);
            document.close();

            JOptionPane.showMessageDialog(this,
                    "In vé thành công!\nLưu tại: " + filePath);

            dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi in vé: " + e.getMessage());
        }
    }
}