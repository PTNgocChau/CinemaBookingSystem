package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import dao.VeDAO;

public class FormDanhSachVe extends JPanel implements ActionListener, MouseListener {

	public static FormDanhSachVe instance;
    private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel model;

    private JComboBox<String> cbNgay;
    private JComboBox<String> cbPhim;
    private JComboBox<String> cbSuatChieu;

    Color BUTTON_COLOR = new Color(250, 193, 7);

    private JButton btnLoc;
    private JButton btnReset;
    private JButton btnInVe;
    private JButton btnXoaVe;

    private String maVeSelected = null;

    private VeDAO veDAO = new VeDAO();

    public FormDanhSachVe() throws SQLException {
    	
    	instance = this;



        setLayout(new BorderLayout());

        // ===== TITLE =====
        JLabel lblT = new JLabel("QUẢN LÝ VÉ XEM PHIM", JLabel.CENTER);
        lblT.setFont(new Font("Arial", Font.BOLD, 26));
        lblT.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(lblT, BorderLayout.NORTH);

        // ===== MAIN =====
        JPanel pnMain = new JPanel(new BorderLayout(15, 0));
        pnMain.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(pnMain, BorderLayout.CENTER);

        // ===== TABLE =====
        String[] cols = {
        	    "Mã Vé",
        	    "Phim",
        	    "Suất Chiếu",
        	    "Ghế",
        	    "Ngày Đặt",
        	    "Trạng Thái"
        	};

        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        JPanel pnTable = new JPanel(new BorderLayout());
        pnTable.setBorder(BorderFactory.createTitledBorder("Danh Sách Vé"));
        pnTable.add(new JScrollPane(table), BorderLayout.CENTER);
        pnMain.add(pnTable, BorderLayout.CENTER);

        // ===== RIGHT =====
        JPanel pnRight = new JPanel();
        pnRight.setPreferredSize(new Dimension(270, 0));

        JPanel pnFilter = new JPanel();
        pnFilter.setLayout(new BoxLayout(pnFilter, BoxLayout.Y_AXIS));
        pnFilter.setBorder(BorderFactory.createTitledBorder("Bộ Lọc"));

        Dimension lblSize = new Dimension(90, 25);

        cbNgay = new JComboBox<>(new String[]{"Tất Cả", "..."});
        cbPhim = new JComboBox<>(new String[]{"Tất Cả", "..."});
        cbSuatChieu = new JComboBox<>(new String[]{
                "Tất Cả","SC001","SC002","SC003","SC004",
                "SC005","SC006","SC008","SC009","SC010"
        });

        JComboBox<?>[] cbs = {cbNgay, cbPhim, cbSuatChieu};
        for (JComboBox<?> cb : cbs)
            cb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        pnFilter.add(createRow(createLabel("Theo Ngày:", lblSize), cbNgay));
        pnFilter.add(Box.createVerticalStrut(10));
        pnFilter.add(createRow(createLabel("Theo Phim:", lblSize), cbPhim));
        pnFilter.add(Box.createVerticalStrut(10));
        pnFilter.add(createRow(createLabel("Theo Suất:", lblSize), cbSuatChieu));

        // ===== LỌC =====
        pnFilter.add(Box.createVerticalStrut(15));

        btnLoc = new JButton("Lọc");
        btnReset = new JButton("Reset");

        for (JButton btn : new JButton[]{btnLoc, btnReset}) {
            btn.setBackground(BUTTON_COLOR);
            btn.setForeground(Color.BLACK);
            btn.setPreferredSize(new Dimension(100, 35));
        }

        JPanel pnBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnBtn.setOpaque(false);
        pnBtn.add(btnLoc);
        pnBtn.add(btnReset);
        pnFilter.add(pnBtn);

        // ===== IN VÉ =====
        pnFilter.add(Box.createVerticalStrut(10));

        btnInVe = new JButton("In Vé");
        btnInVe.setBackground(new Color(40, 167, 69));
        btnInVe.setForeground(Color.WHITE);
        btnInVe.setFont(new Font("Arial", Font.BOLD, 14));
        btnInVe.setPreferredSize(new Dimension(210, 40));

        JPanel pnInVe = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnInVe.setOpaque(false);
        pnInVe.add(btnInVe);

        pnFilter.add(pnInVe);

        // ===== XÓA VÉ =====
        btnXoaVe = new JButton("Xóa Vé");
        btnXoaVe.setBackground(Color.RED);
        btnXoaVe.setForeground(Color.WHITE);
        btnXoaVe.setFont(new Font("Arial", Font.BOLD, 14));
        btnXoaVe.setPreferredSize(new Dimension(210, 40));

        JPanel pnXoa = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnXoa.setOpaque(false);
        pnXoa.add(btnXoaVe);

        pnFilter.add(Box.createVerticalStrut(10));
        pnFilter.add(pnXoa);

        pnRight.add(pnFilter);
        pnMain.add(pnRight, BorderLayout.EAST);

        // ================= EVENTS =================
        btnXoaVe.addActionListener(this);
        btnInVe.addActionListener(this);
        table.addMouseListener(this);

        loadData();
    }

    // ================= LOAD DATA =================
    public void loadData() {
        model.setRowCount(0);

        var ds = veDAO.getAllVe();
        System.out.println("So ve load = " + ds.size());

        ds.forEach(v -> {
            model.addRow(new Object[]{
                v.getMaVe(),
                v.getSuatChieu().getPhim().getTenPhim(),
                v.getSuatChieu().getMaSuat(),
                v.getGhe().getMaGhe(),
                v.getNgayDat(),
                v.getTrangThai()
            });
        });

        model.fireTableDataChanged();
    }

    // ================= ACTION =================
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {

        // ================= XÓA VÉ =================
        if (e.getSource() == btnXoaVe) {

            if (maVeSelected == null) {
                JOptionPane.showMessageDialog(this, "Chọn vé cần xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Xóa vé " + maVeSelected + "?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {

                boolean ok = veDAO.xoaVe(maVeSelected);

                if (ok) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");

                    loadData();
                    maVeSelected = null;
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                }
            }
        }

        // ================= IN VÉ =================
        if (e.getSource() == btnInVe) {

            if (maVeSelected == null) {
                JOptionPane.showMessageDialog(this, "Chọn vé cần in!");
                return;
            }

            try {
                // lấy thông tin vé từ DB
                var ve = veDAO.getVeByMa(maVeSelected);

                if (ve == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy vé!");
                    return;
                }

                // mở form in vé
                FormInVe form = new FormInVe(
                        ve.getSuatChieu().getPhim().getTenPhim(),
                        ve.getSuatChieu().getPhong().getMaPhong(),
                        ve.getSuatChieu().getPhim().getThoiLuong(),
                        ve.getSuatChieu().getPhim().getTheLoai(),
                        ve.getSuatChieu().getThoiGian(),
                        new ArrayList<>(java.util.List.of(ve.getGhe().getMaGhe())),
                        ve.getGiaVe()
                );

                form.setLocationRelativeTo(this);
                form.setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi in vé!");
            }
        }
    }

    // ================= TABLE CLICK =================
    @Override
    public void mouseClicked(MouseEvent e) {

        int row = table.getSelectedRow();

        if (row != -1) {
            maVeSelected = table.getValueAt(row, 0).toString();
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // ================= UI HELPERS =================
    private JLabel createLabel(String text, Dimension size) {
        JLabel lbl = new JLabel(text);
        lbl.setPreferredSize(size);
        lbl.setHorizontalAlignment(SwingConstants.LEFT);
        return lbl;
    }

    private JPanel createRow(Component... comps) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for (int i = 0; i < comps.length; i++) {
            row.add(comps[i]);
            if (i < comps.length - 1)
                row.add(Box.createHorizontalStrut(10));
        }

        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return row;
    }
}