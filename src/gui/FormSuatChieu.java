package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dao.SuatChieuDAO;
import entity.Phim;
import entity.Phong;
import entity.SuatChieu;

public class FormSuatChieu extends JPanel implements ActionListener, ListSelectionListener {

    private static final long serialVersionUID = 1L;

    private JTable table;
    private JTextField txtMa, txtPhim, txtNgay, txtThoiGian;
    private JButton btnTim, btnThem, btnXoa, btnSua, btnXoaRong;
    private JTextField txtTim;
    private JComboBox<String> cbPhong;

    private DefaultTableModel model;
    private SuatChieuDAO suatChieu_dao;

    private final DateTimeFormatter fmtNgay = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter fmtGio = DateTimeFormatter.ofPattern("HH:mm");

    public FormSuatChieu() {
    	
   

        suatChieu_dao = new SuatChieuDAO();
        

        setLayout(new BorderLayout());

        JPanel pnForm = new JPanel();
        pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
        pnForm.setBorder(BorderFactory.createTitledBorder("Thông Tin Suất Chiếu"));

        Dimension lblSize = new Dimension(100, 25);
        Dimension txtSize = new Dimension(Integer.MAX_VALUE, 25);

        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        JLabel lblMa = new JLabel("Mã Suất Chiếu:");
        lblMa.setPreferredSize(lblSize);
        txtMa = new JTextField();
        txtMa.setMaximumSize(txtSize);

        JLabel lblPhim = new JLabel("Phim:");
        lblPhim.setPreferredSize(lblSize);
        txtPhim = new JTextField();
        txtPhim.setMaximumSize(txtSize);

        row1.add(lblMa);
        row1.add(Box.createHorizontalStrut(5));
        row1.add(txtMa);
        row1.add(Box.createHorizontalStrut(10));
        row1.add(lblPhim);
        row1.add(Box.createHorizontalStrut(5));
        row1.add(txtPhim);
        pnForm.add(row1);
        pnForm.add(Box.createVerticalStrut(10));

        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        JLabel lblNgay = new JLabel("Ngày Chiếu:");
        lblNgay.setPreferredSize(lblSize);
        txtNgay = new JTextField();
        txtNgay.setMaximumSize(txtSize);

        JLabel lblThoiGian = new JLabel("Thời Gian:");
        lblThoiGian.setPreferredSize(lblSize);
        txtThoiGian = new JTextField();
        txtThoiGian.setMaximumSize(txtSize);

        row2.add(lblNgay);
        row2.add(Box.createHorizontalStrut(5));
        row2.add(txtNgay);
        row2.add(Box.createHorizontalStrut(10));
        row2.add(lblThoiGian);
        row2.add(Box.createHorizontalStrut(5));
        row2.add(txtThoiGian);
        pnForm.add(row2);
        pnForm.add(Box.createVerticalStrut(10));

        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        JLabel lblPhong = new JLabel("Phòng:");
        lblPhong.setPreferredSize(lblSize);
        String[] pbPhong = {"P001", "P002", "P003", "P004", "P005", "P006", "P007", "P008"};
        cbPhong = new JComboBox<>(pbPhong);
        cbPhong.setPreferredSize(new Dimension(300, 25));

        row3.add(lblPhong);
        row3.add(Box.createHorizontalStrut(5));
        row3.add(cbPhong);
        pnForm.add(row3);
        pnForm.add(Box.createVerticalStrut(10));

        add(pnForm, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel pnTopControl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTim = new JLabel("Nhập mã cần tìm:");
        txtTim = new JTextField(15);
        btnTim = new JButton("Tìm");
        btnTim.setBackground(new Color(250, 193, 7));
        btnTim.setForeground(Color.BLACK);

        pnTopControl.add(lblTim);
        pnTopControl.add(txtTim);
        pnTopControl.add(btnTim);

        pnTopControl.add(Box.createHorizontalStrut(30));

        btnThem = new JButton("Thêm");
        btnThem.setBackground(new Color(250, 193, 7));
        btnThem.setForeground(Color.BLACK);

        btnXoa = new JButton("Xoá");
        btnXoa.setBackground(new Color(250, 193, 7));
        btnXoa.setForeground(Color.BLACK);

        btnSua = new JButton("Sửa");
        btnSua.setBackground(new Color(250, 193, 7));
        btnSua.setForeground(Color.BLACK);

        btnXoaRong = new JButton("Xoá Rỗng");
        btnXoaRong.setBackground(new Color(250, 193, 7));
        btnXoaRong.setForeground(Color.BLACK);

        pnTopControl.add(btnThem);
        pnTopControl.add(btnSua);
        pnTopControl.add(btnXoa);
        pnTopControl.add(btnXoaRong);

        JPanel pnTable = new JPanel(new BorderLayout());
        pnTable.setBorder(BorderFactory.createTitledBorder("Danh Sách Suất Chiếu"));

        String[] cols = {"Mã Suất Chiếu", "Tên Phim", "Phòng", "Thời Gian", "Ngày Chiếu"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        pnTable.add(scroll, BorderLayout.CENTER);
        centerPanel.add(pnTopControl, BorderLayout.NORTH);
        centerPanel.add(pnTable, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        btnTim.addActionListener(this);
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnXoaRong.addActionListener(this);
        table.getSelectionModel().addListSelectionListener(this);

        docDuLieuDatabaseVaoTable();
    }
    public void docDuLieuDatabaseVaoTable() {
        model.setRowCount(0);

        List<SuatChieu> ds = suatChieu_dao.getAllSuatChieu();
        if (ds == null) return;

        for (int i = 0; i < ds.size(); i++) {
            SuatChieu sc = ds.get(i);

            model.addRow(new Object[]{
                    sc.getMaSuat(),
                    sc.getPhim() != null ? sc.getPhim().getMaPhim() : "",
                    sc.getPhong() != null ? sc.getPhong().getMaPhong() : "",
                    sc.getThoiGian() != null ? sc.getThoiGian().toLocalTime().format(fmtGio) : "",
                    sc.getNgayChieu() != null ? sc.getNgayChieu().format(fmtNgay) : ""
            });
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnThem) {
            themSuatChieu();
        } else if (src == btnSua) {
            suaSuatChieu();
        } else if (src == btnXoa) {
            xoaSuatChieu();
        } else if (src == btnTim) {
            timSuatChieu();
        } else if (src == btnXoaRong) {
            xoaRong();
        }
    }
    
    private void themSuatChieu() {
        SuatChieu sc = laySuatChieuTuForm();
        if (sc == null) return;

        if (suatChieu_dao.themSuatChieu(sc)) {
            JOptionPane.showMessageDialog(this, "Thêm suất chiếu thành công!");
            docDuLieuDatabaseVaoTable();
            xoaRong();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại.");
        }
    }
    
    private void suaSuatChieu() {
        SuatChieu sc = laySuatChieuTuForm();
        if (sc == null) return;

        if (suatChieu_dao.suaSuatChieu(sc)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            docDuLieuDatabaseVaoTable();
            xoaRong();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại.");
        }
    }
    

    private void xoaSuatChieu() {
        String maSuat = txtMa.getText().trim();

        if (maSuat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập mã suất chiếu cần xoá.");
            return;
        }

        int hoi = JOptionPane.showConfirmDialog(
                this,
                "Xác nhận xoá suất chiếu này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (hoi != JOptionPane.YES_OPTION) return;

        if (suatChieu_dao.xoaSuatChieu(maSuat)) {
            JOptionPane.showMessageDialog(this, "Xoá thành công!");
            docDuLieuDatabaseVaoTable();
            xoaRong();
        } else {
            JOptionPane.showMessageDialog(this, "Xoá thất bại.");
        }
    }
    
    private void timSuatChieu() {
        String ma = txtTim.getText().trim();

        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập mã cần tìm.");
            return;
        }

        SuatChieu sc = suatChieu_dao.timSuatChieu(ma);

        if (sc == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy.");
            return;
        }

        model.setRowCount(0);

        model.addRow(new Object[]{
            sc.getMaSuat(),
            sc.getPhim() != null ? sc.getPhim().getMaPhim() : "",
            sc.getPhong() != null ? sc.getPhong().getMaPhong() : "",
            sc.getThoiGian() != null ? sc.getThoiGian().toLocalTime().format(fmtGio) : "",
            sc.getNgayChieu() != null ? sc.getNgayChieu().format(fmtNgay) : ""
        });
    }
    
    private SuatChieu laySuatChieuTuForm() {
        String maSuat = txtMa.getText().trim();
        String maPhim = txtPhim.getText().trim();
        String ngayStr = txtNgay.getText().trim();
        String gioStr = txtThoiGian.getText().trim();
        String maPhong = cbPhong.getSelectedItem().toString();

        if (maSuat.isEmpty() || maPhim.isEmpty() || ngayStr.isEmpty() || gioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
            return null;
        }

        try {
            LocalDate ngayChieu = LocalDate.parse(ngayStr, fmtNgay);
            LocalDateTime thoiGian = LocalDateTime.of(ngayChieu, java.time.LocalTime.parse(gioStr, fmtGio));

            Phim phim = new Phim();
            phim.setMaPhim(maPhim);

            Phong phong = new Phong();
            phong.setMaPhong(maPhong);

            return new SuatChieu(maSuat, phim, ngayChieu, phong, thoiGian);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Sai định dạng ngày/giờ.\nNgày: yyyy-MM-dd\nGiờ: HH:mm");
            return null;
        }
    }
    private void xoaRong() {
        txtMa.setText("");
        txtPhim.setText("");
        txtNgay.setText("");
        txtThoiGian.setText("");
        txtTim.setText("");
        cbPhong.setSelectedIndex(0);
        table.clearSelection();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;

        int row = table.getSelectedRow();
        if (row == -1) return;

        txtMa.setText(model.getValueAt(row, 0).toString());
        txtPhim.setText(model.getValueAt(row, 1).toString());
        cbPhong.setSelectedItem(model.getValueAt(row, 2).toString());
        txtThoiGian.setText(model.getValueAt(row, 3).toString());
        txtNgay.setText(model.getValueAt(row, 4).toString());
    }
}
