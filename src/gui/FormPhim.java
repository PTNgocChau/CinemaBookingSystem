package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.PhimDAO;
import entity.Phim;

public class FormPhim extends JPanel implements ActionListener, MouseListener {
    private static final long serialVersionUID = 1L;

    Color BUTTON_COLOR = new Color(250, 193, 7);

    private JComboBox<String> cbTheLoai;
    private JComboBox<String> cbGHTuoi;
    private JComboBox<String> cbNgonNgu;

    private JButton btnTim;
    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnXoaRong;
    private JButton btnLamMoi;

    private JTable table;
    private JTextField txtMaPhim;
    private JTextField txtTenPhim;
    private JTextField txtThoiLuong;
    private JTextField txtTim;

    private DefaultTableModel model;
    private PhimDAO phim_dao;
    public FormPhim(){
    	
    	
    	
        setLayout(new BorderLayout());

        JPanel pnForm = new JPanel();
        pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
        pnForm.setBorder(BorderFactory.createTitledBorder("Thông Tin Phim"));

        Dimension lblSize = new Dimension(100, 25);
        Dimension txtSize = new Dimension(Integer.MAX_VALUE, 25);

        cbTheLoai = new JComboBox<>(new String[] {
                "Hài", "Tình Cảm", "Kinh Dị", "Khoa Học", "Thiếu Nhi", "Hành Động"
        });
        cbTheLoai.setPreferredSize(new Dimension(0, 25));
        cbTheLoai.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        cbGHTuoi = new JComboBox<>(new String[] {
                "P", "K", "T13", "T16", "T18"
        });
        cbGHTuoi.setPreferredSize(new Dimension(0, 25));
        cbGHTuoi.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        cbNgonNgu = new JComboBox<>(new String[] {
                "Phụ Đề", "Lồng Tiếng", "Phụ Đề + Lồng Tiếng"
        });
        cbNgonNgu.setPreferredSize(new Dimension(0, 25));
        cbNgonNgu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        txtMaPhim = createTextField(txtSize);
        txtTenPhim = createTextField(txtSize);
        txtThoiLuong = createTextField(txtSize);

        // ===== ROW 1 =====
        pnForm.add(createRow(
                createLabel("Mã Phim:", lblSize),
                txtMaPhim,
                createLabel("Tên Phim:", lblSize),
                txtTenPhim
        ));
        pnForm.add(Box.createVerticalStrut(10));

        // ===== ROW 2 =====
        pnForm.add(createRow(
                createLabel("Thời Lượng:", lblSize),
                txtThoiLuong,
                createLabel("Thể Loại:", lblSize),
                cbTheLoai
        ));
        pnForm.add(Box.createVerticalStrut(10));

        // ===== ROW 3 =====
        pnForm.add(createRow(
                createLabel("Giới Hạn Tuổi:", lblSize),
                cbGHTuoi,
                createLabel("Ngôn Ngữ:", lblSize),
                cbNgonNgu
        ));
        pnForm.add(Box.createVerticalStrut(10));

        add(pnForm, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());

        // ===== TOP CONTROL =====
        JPanel pnTopControl = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel lblTim = new JLabel("Nhập mã cần tìm:");
        txtTim = new JTextField(15);
        btnTim = new JButton("Tìm");
        btnTim.setBackground(BUTTON_COLOR);
        btnTim.setForeground(Color.BLACK);

        pnTopControl.add(lblTim);
        pnTopControl.add(txtTim);
        pnTopControl.add(btnTim);

        pnTopControl.add(Box.createHorizontalStrut(30));

        btnThem = new JButton("Thêm");
        btnThem.setBackground(BUTTON_COLOR);
        btnThem.setForeground(Color.BLACK);

        btnXoa = new JButton("Xoá");
        btnXoa.setBackground(BUTTON_COLOR);
        btnXoa.setForeground(Color.BLACK);

        btnSua = new JButton("Sửa");
        btnSua.setBackground(BUTTON_COLOR);
        btnSua.setForeground(Color.BLACK);

        btnXoaRong = new JButton("Xoá Rỗng");
        btnXoaRong.setBackground(BUTTON_COLOR);
        btnXoaRong.setForeground(Color.BLACK);
        
        btnLamMoi = new JButton("Làm Mới");
        btnLamMoi.setBackground(BUTTON_COLOR);
        btnLamMoi.setForeground(Color.BLACK);

        pnTopControl.add(btnThem);
        pnTopControl.add(btnSua);
        pnTopControl.add(btnXoa);
        pnTopControl.add(btnXoaRong);
        pnTopControl.add(btnLamMoi);

        // ===== TABLE =====
        JPanel pnTable = new JPanel(new BorderLayout());
        pnTable.setBorder(BorderFactory.createTitledBorder("Danh Sách Phim"));

        String[] cols = {
                "Mã Phim", "Tên Phim", "Thời Lượng", "Thể Loại", "Giới Hạn Tuổi", "Ngôn Ngữ"
        };
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        pnTable.add(scroll, BorderLayout.CENTER);
        centerPanel.add(pnTopControl, BorderLayout.NORTH);
        centerPanel.add(pnTable, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        btnTim.addActionListener(this);
        btnThem.addActionListener(this);
        btnXoaRong.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnLamMoi.addActionListener(this);

        table.addMouseListener(this);

        phim_dao = new PhimDAO();
        docDuLieuDatabaseVaoTable();
    }

    private JLabel createLabel(String text, Dimension size) {
        JLabel lbl = new JLabel(text);
        lbl.setPreferredSize(size);
        lbl.setMinimumSize(size);
        lbl.setMaximumSize(size);
        lbl.setHorizontalAlignment(SwingConstants.LEFT);
        return lbl;
    }

    private JTextField createTextField(Dimension size) {
        JTextField txt = new JTextField();
        txt.setPreferredSize(new Dimension(0, 25));
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        return txt;
    }

    private JPanel createRow(Component... comps) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        for (int i = 0; i < comps.length; i++) {
            row.add(comps[i]);
            if (i < comps.length - 1) {
                row.add(Box.createHorizontalStrut(10));
            }
        }
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return row;
    }

    public void docDuLieuDatabaseVaoTable() {
    		model.setRowCount(0);
    		List<Phim> list = phim_dao.getAllPhim();
    		
    		for(Phim p : list) {
    			model.addRow(new Object[] {
    				
    				p.getMaPhim(),
    				p.getTenPhim(),
    				p.getThoiLuong(),
    				p.getTheLoai(),
    				p.getGioiHanTuoi(),
    				p.getNgonNgu()
    			});
    		}
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) return;

        txtMaPhim.setText(model.getValueAt(row, 0).toString());
        txtTenPhim.setText(model.getValueAt(row, 1).toString());
        txtThoiLuong.setText(model.getValueAt(row, 2).toString());
        cbTheLoai.setSelectedItem(model.getValueAt(row, 3).toString());
        cbGHTuoi.setSelectedItem(model.getValueAt(row, 4).toString());
        cbNgonNgu.setSelectedItem(model.getValueAt(row, 5).toString());

        txtMaPhim.setEditable(false);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == btnThem) {
        	themPhim();
        } else if (o == btnXoa) {
            xoaPhim();
        } else if (o == btnSua) {
            suaPhim();
        } else if (o == btnTim) {
            timPhim();
        } else if (o == btnXoaRong) {
        	xoaRong();
        }else if (o == btnLamMoi) {
            lamMoi();
        }
        
    }
    
    private boolean isValidMaPhim(String ma) {
        return ma.matches("^P\\d{3}$");
    }

    private boolean isValidTenPhim(String ten) {
        return ten.matches("^[\\p{L}0-9][\\p{L}0-9 .,:!?'\\-()]{1,98}[\\p{L}0-9)]$");
    }

    private boolean isValidThoiLuong(int thoiLuong) {
        return thoiLuong > 90;
    }

    private boolean isValidNotEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }
    
    private void lamMoi() {
        try {
            docDuLieuDatabaseVaoTable(); // load lại từ SQL
            xoaRong();                  // xóa trắng form
            JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Làm mới thất bại");
        }
    }
    
    private void themPhim() {
        try {
            String ma = txtMaPhim.getText().trim();
            String ten = txtTenPhim.getText().trim();
            String thoiLuongStr = txtThoiLuong.getText().trim();
            String theLoai = cbTheLoai.getSelectedItem().toString();
            String ghTuoi = cbGHTuoi.getSelectedItem().toString();
            String ngonNgu = cbNgonNgu.getSelectedItem().toString();

            // ===== VALIDATE =====

            if (!isValidNotEmpty(ma)) {
                JOptionPane.showMessageDialog(this, "Mã phim không được rỗng");
                txtMaPhim.requestFocus();
                txtMaPhim.selectAll();
                return;
            }

            if (!isValidMaPhim(ma)) {
                JOptionPane.showMessageDialog(this, "Mã phim phải dạng Pxxx (vd: P001)");
                txtMaPhim.requestFocus();
                txtMaPhim.selectAll();
                return;
            }

            if (!isValidNotEmpty(ten)) {
                JOptionPane.showMessageDialog(this, "Tên phim không được rỗng");
                txtTenPhim.requestFocus();
                txtTenPhim.selectAll();
                return;
            }

            if (!isValidTenPhim(ten)) {
                JOptionPane.showMessageDialog(this, "Tên phim chỉ chứa chữ, số và khoảng trắng");
                txtTenPhim.requestFocus();
                txtTenPhim.selectAll();
                return;
            }

            if (!isValidNotEmpty(thoiLuongStr)) {
                JOptionPane.showMessageDialog(this, "Thời lượng không được rỗng");
                txtThoiLuong.requestFocus();
                txtThoiLuong.selectAll();
                return;
            }

            int thoiLuong;
            try {
                thoiLuong = Integer.parseInt(thoiLuongStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Thời lượng phải là số");
                txtThoiLuong.requestFocus();
                txtThoiLuong.selectAll();
                return;
            }

            if (!isValidThoiLuong(thoiLuong)) {
                JOptionPane.showMessageDialog(this, "Thời lượng phải > 90 phút");
                txtThoiLuong.requestFocus();
                txtThoiLuong.selectAll();
                return;
            }

            if (!isValidNotEmpty(theLoai)) {
                JOptionPane.showMessageDialog(this, "Chọn thể loại");
                cbTheLoai.requestFocus();
                return;
            }

            if (!isValidNotEmpty(ghTuoi)) {
                JOptionPane.showMessageDialog(this, "Chọn giới hạn tuổi");
                cbGHTuoi.requestFocus();
                return;
            }

            if (!isValidNotEmpty(ngonNgu)) {
                JOptionPane.showMessageDialog(this, "Chọn ngôn ngữ");
                cbNgonNgu.requestFocus();
                return;
            }

            if (phim_dao.timPhim(ma) != null) {
                JOptionPane.showMessageDialog(this, "Mã phim không được trùng");
                txtMaPhim.requestFocus();
                txtMaPhim.selectAll();
                return;
            }

            // ===== CREATE =====
            Phim phim = new Phim(ma, ten, thoiLuong, theLoai, ghTuoi, ngonNgu);

            if (phim_dao.themPhim(phim)) {
                model.addRow(new Object[] {
                        phim.getMaPhim(),
                        phim.getTenPhim(),
                        phim.getThoiLuong(),
                        phim.getTheLoai(),
                        phim.getGioiHanTuoi(),
                        phim.getNgonNgu()
                });
            }
            
            xoaRong();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi dữ liệu");
        }
    }
    
    private void xoaPhim() {
        int[] rows = table.getSelectedRows();

        if (rows.length == 0) {
            JOptionPane.showMessageDialog(this, "Chọn ít nhất 1 dòng cần xóa");
            return;
        }

        int hoi = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa " + rows.length + " phim ?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (hoi != JOptionPane.YES_OPTION) return;

        boolean ok = true;

        // Xóa từ dưới lên để tránh lệch index
        for (int i = rows.length - 1; i >= 0; i--) {
            String ma = model.getValueAt(rows[i], 0).toString().trim();

            if (!phim_dao.xoaPhim(ma)) {
                ok = false;
            }
        }

        docDuLieuDatabaseVaoTable();
        xoaRong();

        if (ok) {
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Có vài dòng xóa thất bại");
        }
    }
    
    private void xoaRong() {
        txtMaPhim.setText("");
        txtTenPhim.setText("");
        txtThoiLuong.setText("");
        cbTheLoai.setSelectedIndex(0);
        cbGHTuoi.setSelectedIndex(0);
        cbNgonNgu.setSelectedIndex(0);
        txtTim.setText("");
        txtMaPhim.setEditable(true);
        table.clearSelection();
        txtMaPhim.requestFocus();
    }
    
    private void timPhim() {
        String str = txtTim.getText().trim();

        if (!str.isEmpty()) {
            Phim p = phim_dao.timPhim(str);

            if (p != null) {
                txtMaPhim.setText(p.getMaPhim());
                txtTenPhim.setText(p.getTenPhim());
                txtThoiLuong.setText(String.valueOf(p.getThoiLuong()));
                cbTheLoai.setSelectedItem(p.getTheLoai());
                cbGHTuoi.setSelectedItem(p.getGioiHanTuoi());
                cbNgonNgu.setSelectedItem(p.getNgonNgu());

                txtMaPhim.setEditable(false);

                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).toString().equalsIgnoreCase(str)) {
                        table.setRowSelectionInterval(i, i);
                        table.scrollRectToVisible(table.getCellRect(i, 0, true));
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy");
                txtTim.setText("");
                txtTim.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã cần tìm");
        }
    }
    
    private void suaPhim() {
        int r = table.getSelectedRow();
        if (r == -1) {
            JOptionPane.showMessageDialog(this, "Chọn dòng cần sửa");
            return;
        }

        try {
            String ma = txtMaPhim.getText().trim();
            String ten = txtTenPhim.getText().trim();
            String thoiLuongStr = txtThoiLuong.getText().trim();

            if (!isValidMaPhim(ma)) {
                JOptionPane.showMessageDialog(this, "Mã phim không hợp lệ");
                txtMaPhim.requestFocus();
                txtMaPhim.selectAll();
                return;
            }

            if (!isValidTenPhim(ten)) {
                JOptionPane.showMessageDialog(this, "Tên phim không hợp lệ");
                txtTenPhim.requestFocus();
                txtTenPhim.selectAll();
                return;
            }

            int thoiLuong;
            try {
                thoiLuong = Integer.parseInt(thoiLuongStr);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Thời lượng phải là số");
                txtThoiLuong.requestFocus();
                txtThoiLuong.selectAll();
                return;
            }

            if (!isValidThoiLuong(thoiLuong)) {
                JOptionPane.showMessageDialog(this, "Thời lượng phải > 90");
                txtThoiLuong.requestFocus();
                txtThoiLuong.selectAll();
                return;
            }

            Phim phimMoi = new Phim(
                    ma, ten, thoiLuong,
                    cbTheLoai.getSelectedItem().toString(),
                    cbGHTuoi.getSelectedItem().toString(),
                    cbNgonNgu.getSelectedItem().toString()
            );

            if (phim_dao.suaPhim(phimMoi)) {
                docDuLieuDatabaseVaoTable();
                xoaRong(); 
                JOptionPane.showMessageDialog(this, "Sửa thành công");
  
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi dữ liệu");
        }
    }
}