package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import dao.GheDAO;
import dao.SuatChieuDAO;
import dao.VeDAO;
import entity.Ghe;
import entity.NhanVien;
import entity.SuatChieu;
import entity.Ve;

public class FormVe extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private SuatChieu currentSuat;
    private GheDAO ghe_dao;

    private JButton btnGhe, btnXacNhan;
    private JTable table;
    private JComboBox<String> cbSC;
    private JComboBox<String> cbPhim;
    private JTextField txtPhong;

    private ArrayList<String> selectedSeats = new ArrayList<>();
    private DefaultTableModel model;

    private List<SuatChieu> dsSuat = new ArrayList<>();

    public FormVe(){
    	

        ghe_dao = new GheDAO();

        setLayout(new BorderLayout());

        // ================= FORM =================
        JPanel pnForm = new JPanel();
        pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
        pnForm.setBorder(BorderFactory.createTitledBorder("Chọn Phim Và Suất Chiếu"));

        Dimension lblSize = new Dimension(100, 25);
        Dimension txtSize = new Dimension(800, 25);

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblPhim = new JLabel("Phim:");
        lblPhim.setPreferredSize(lblSize);

        cbPhim = new JComboBox<>();
        cbPhim.setPreferredSize(txtSize);

        row1.add(lblPhim);
        row1.add(cbPhim);
        
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblSC = new JLabel("Suất chiếu:");
        lblSC.setPreferredSize(lblSize);

        cbSC = new JComboBox<>();
        cbSC.setPreferredSize(txtSize);

        row2.add(lblSC);
        row2.add(cbSC);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblPhong = new JLabel("Phòng:");
        lblPhong.setPreferredSize(lblSize);

        txtPhong = new JTextField();
        txtPhong.setPreferredSize(txtSize);
        txtPhong.setEditable(false);

        row3.add(lblPhong);
        row3.add(txtPhong);

        JPanel row4 = new JPanel();

        btnGhe = new JButton("Chọn Ghế");
        btnGhe.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnGhe.setBackground(new Color(250, 193, 7));
        btnGhe.setPreferredSize(new Dimension(200, 40));

        row4.add(btnGhe);

        pnForm.add(row1);
        pnForm.add(row2);
        pnForm.add(row3);
        pnForm.add(Box.createVerticalStrut(10));
        pnForm.add(row4);

        add(pnForm, BorderLayout.NORTH);

        // ================= TABLE =================
        String[] cols = {"", ""};

        Object[][] data = {
                {"Tên phim:",    ""},
                {"Tên phòng:",   ""},
                {"Thời lượng:",  ""},
                {"Thể loại:",    ""},
                {"Thời gian:",   ""},
                {"Ghế đã chọn:", ""},
                {"Tổng tiền:",   ""}
        };

        model = new DefaultTableModel(data, cols);
        table = new JTable(model);
        table.setRowHeight(30);

        add(table, BorderLayout.CENTER);

        // ================= BUTTON =================
        btnXacNhan = new JButton("Xác Nhận Đặt Vé");
        btnXacNhan.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnXacNhan.setBackground(new Color(250, 193, 7));

        add(btnXacNhan, BorderLayout.SOUTH);

        btnGhe.addActionListener(this);
        btnXacNhan.addActionListener(this);

        taoDuLieuPhim();
        cbPhim.addActionListener(e -> {
            locSuatTheoPhim();
        });

        cbSC.addActionListener(e -> {
            String ma = (String) cbSC.getSelectedItem();
            if (ma == null) return;
            currentSuat = getSuat(ma);
            selectedSeats.clear();
            capNhatBang();
        });
    }

 // ================= LOAD DỮ LIỆU TỪ DATABASE =================
    private void taoDuLieuPhim() {

        dsSuat.clear();
        cbSC.removeAllItems();
        cbPhim.removeAllItems();

        try {

            SuatChieuDAO sc_dao = new SuatChieuDAO();
            dsSuat = sc_dao.getAllSuatChieu();

            // đổ danh sách phim không trùng
            for (SuatChieu sc : dsSuat) {

                String tenPhim = sc.getPhim().getTenPhim();

                boolean exists = false;

                for (int i = 0; i < cbPhim.getItemCount(); i++) {
                    if (cbPhim.getItemAt(i).equals(tenPhim)) {
                        exists = true;
                        break;
                    }
                }

                if (!exists)
                    cbPhim.addItem(tenPhim);
            }

            if (cbPhim.getItemCount() > 0) {
                cbPhim.setSelectedIndex(0);
                locSuatTheoPhim();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu!");
        }
    }

    private SuatChieu getSuat(String ma) {
        for (SuatChieu sc : dsSuat) {
            if (sc.getMaSuat().equals(ma))
                return sc;
        }
        return null;
    }
    
    private void locSuatTheoPhim() {

        cbSC.removeAllItems();

        String phimChon = (String) cbPhim.getSelectedItem();

        for (SuatChieu sc : dsSuat) {

            if (sc.getPhim().getTenPhim().equals(phimChon)) {
                cbSC.addItem(sc.getMaSuat());
            }
        }

        if (cbSC.getItemCount() > 0) {
            cbSC.setSelectedIndex(0);
            currentSuat = getSuat((String) cbSC.getSelectedItem());
            capNhatBang();
        }
    }

    // ================= EVENT =================
    @Override
    public void actionPerformed(ActionEvent e) {

        // ===== CHỌN GHẾ =====
        if (e.getSource() == btnGhe) {

            String maSuat = cbSC.getSelectedItem().toString();
            currentSuat = getSuat(maSuat);

            Window w = SwingUtilities.getWindowAncestor(this);

            SeatDialog dialog = new SeatDialog(
                    (JFrame) w,
                    this,
                    ghe_dao,
                    currentSuat
            );

            dialog.setVisible(true);
        }

        // ================= XÁC NHẬN =================
        if (e.getSource() == btnXacNhan) {

            if (selectedSeats == null || selectedSeats.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn ghế trước khi đặt!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int hoi = JOptionPane.showConfirmDialog(this,
                    "Xác nhận đặt vé?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION);

            if (hoi == JOptionPane.YES_OPTION) {
            	VeDAO veDAO = new VeDAO();

            	// lấy số bắt đầu
                int stt = Integer.parseInt(
                        veDAO.taoMaVeMoi().substring(2)
                );
                
                boolean ok = ghe_dao.datNhieuGhe(selectedSeats, currentSuat.getMaSuat());

                if (!ok) {
                    JOptionPane.showMessageDialog(this, "Đặt ghế thất bại!");
                    return;
                }

                for (String maGhe : selectedSeats) {

                    String maVe = String.format("VE%03d", stt++);

                    boolean vip =
                            (maGhe.charAt(0) >= 'D' &&
                             maGhe.charAt(0) <= 'H');

                    Ghe ghe = new Ghe(maGhe, vip, 1, currentSuat);

                    Ve ve = new Ve(
                            maVe,
                            LocalDate.now(),
                            "DA_DAT",
                            null,
                            ghe,
                            currentSuat
                    );

                    ve.setGiaVe(vip ? 70000 : 50000);
                    ve.setNhanVien(new NhanVien("TC0001"));

                    veDAO.themVe(ve);
                }

                // refresh danh sách vé
                if (FormDanhSachVe.instance != null) {
                    FormDanhSachVe.instance.loadData();
                }

                SuatChieu sc = getSuat((String) cbSC.getSelectedItem());

                FormInVe form = new FormInVe(
                        sc.getPhim().getTenPhim(),
                        sc.getPhong().getMaPhong(),
                        sc.getPhim().getThoiLuong(),
                        sc.getPhim().getTheLoai(),
                        sc.getThoiGian(),
                        selectedSeats,
                        tongTien()
                );

                form.setLocationRelativeTo(this);
                form.setVisible(true);

                selectedSeats.clear();
                capNhatBang();
            }
        }
    }

    // ================= NHẬN GHẾ TỪ SeatDialog =================
    public void setSelectedSeats(List<String> seats, SuatChieu suat) {

        selectedSeats.clear();
        selectedSeats.addAll(seats);

        currentSuat = suat;

        capNhatBang();
    }

    // ================= UPDATE TABLE =================
    private void capNhatBang() {

        if (currentSuat == null) return;

        model.setValueAt(currentSuat.getPhim().getTenPhim(),       0, 1);
        model.setValueAt(currentSuat.getPhong().getMaPhong(),      1, 1);
        model.setValueAt(currentSuat.getPhim().getThoiLuong() + " phút", 2, 1);
        model.setValueAt(currentSuat.getPhim().getTheLoai(),       3, 1);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
        model.setValueAt(currentSuat.getThoiGian().format(fmt),    4, 1);

        model.setValueAt(String.join(", ", selectedSeats),         5, 1);
        model.setValueAt(tongTien() + " VNĐ",                      6, 1);

        
        txtPhong.setText(currentSuat.getPhong().getMaPhong());
    }

    // ================= TÍNH TIỀN =================
    private double tongTien() {

        double tong = 0;

        for (String ma : selectedSeats) {

            boolean vip = (ma.charAt(0) >= 'D' && ma.charAt(0) <= 'H');

            Ghe ghe = new Ghe(ma, vip, 1, currentSuat);
            Ve  ve  = new Ve("TEMP", LocalDate.now(), "", null, ghe, currentSuat);

            tong += ve.getGiaVe();
        }

        return tong;
    }
}