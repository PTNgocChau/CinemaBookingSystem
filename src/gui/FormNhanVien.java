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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.NhanVienDAO;
import entity.NhanVien;

public class FormNhanVien extends JPanel implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;
	private JTextField txtMa;
	private JTextField txtTen;
	private JTextField txtSDT;
	private JTextField txtEmail;
	private JTextField txtDiaChi;
	private JComboBox<String> cbChucVu;
	private JRadioButton rdNam;
	private JRadioButton rdNu;
	private JRadioButton rdDangLam;
	private JRadioButton rdNghi;
	private JTable table;
	JButton btnTim, btnThem, btnXoa, btnSua, btnXoaRong, btnLamMoi;
	Color BUTTON_COLOR = new Color(250, 193, 7);
	private DefaultTableModel model;
	private NhanVienDAO nhanVien_dao;
	private JTextField txtTim;

	public FormNhanVien() {
		
	    setLayout(new BorderLayout());
	    JPanel pnForm = new JPanel();
	    pnForm.setLayout(new BoxLayout(pnForm, BoxLayout.Y_AXIS));
	    pnForm.setBorder(BorderFactory.createTitledBorder("Hồ Sơ Nhân Viên"));

	    Dimension lblSize = new Dimension(100, 25);
	    Dimension txtSize = new Dimension(Integer.MAX_VALUE, 25);

	    // ===== DATA =====
	    rdNam = new JRadioButton("Nam");
	    rdNam.setSelected(true);
	    
	    rdNu = new JRadioButton("Nữ");
	    ButtonGroup groupGT = new ButtonGroup();
	    groupGT.add(rdNam);
	    groupGT.add(rdNu);

	    rdDangLam = new JRadioButton("Đang làm");
	    rdDangLam.setSelected(true);
	    
	    rdNghi = new JRadioButton("Nghỉ");
	    ButtonGroup groupTT = new ButtonGroup();
	    groupTT.add(rdDangLam);
	    groupTT.add(rdNghi);

	    cbChucVu = new JComboBox<>(new String[]{
	            "Quản lý", "Nhân viên"
	    });
	    cbChucVu.setPreferredSize(new Dimension(0, 25));
	    cbChucVu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

	    // ===== PANEL RADIO =====
	    JPanel pGT = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
	    pGT.add(rdNam);
	    pGT.add(rdNu);
	    pGT.setPreferredSize(new Dimension(0, 25));
	    pGT.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

	    JPanel pTT = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
	    pTT.add(rdDangLam);
	    pTT.add(rdNghi);
	    pTT.setPreferredSize(new Dimension(0, 25));
	    pTT.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
	    
	    txtMa = createTextField(txtSize);
	    txtTen = createTextField(txtSize);
	    txtSDT = createTextField(txtSize);
	    txtEmail = createTextField(txtSize);
	    txtDiaChi = createTextField(txtSize);

	 // ===== ROW 1 =====
	    pnForm.add(createRow(
	            createLabel("Mã Nhân Viên:", lblSize),
	            txtMa,
	            createLabel("Họ Tên:", lblSize),
	            txtTen
	    ));
	    pnForm.add(Box.createVerticalStrut(10));

	    // ===== ROW 2 =====
	    pnForm.add(createRow(
	            createLabel("Số Điện Thoại:", lblSize),
	            txtSDT,
	            createLabel("Giới Tính:", lblSize),
	            pGT
	    ));
	    pnForm.add(Box.createVerticalStrut(10));

	    // ===== ROW 3 =====
	    pnForm.add(createRow(
	            createLabel("Email:", lblSize),
	            txtEmail,
	            createLabel("Chức Vụ:", lblSize),
	            cbChucVu
	    ));
	    pnForm.add(Box.createVerticalStrut(10));

	    // ===== ROW 4 =====
	    pnForm.add(createRow(
	            createLabel("Địa Chỉ:", lblSize),
	            txtDiaChi,
	            createLabel("Trạng Thái:", lblSize),
	            pTT
	    ));

	    // ===== ADD =====
	    add(pnForm, BorderLayout.NORTH);
		

	    JPanel centerPanel = new JPanel(new BorderLayout());

		 // ===== TOP CONTROL (SEARCH + BUTTON) =====
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
	
		// BUTTON
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
		pnTable.setBorder(BorderFactory.createTitledBorder("Danh Sách Nhân Viên"));
		 
		String[] cols = {
				"Mã Nhân Viên", "Họ Tên", "Số Điện Thoại", "Giới Tính", 
				"Email", "Chức Vụ", "Địa Chỉ", "Trạng Thái"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
	
		// ===== ADD =====
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
		nhanVien_dao = new NhanVienDAO();
		docDuLieuTuDatabaseVaoTable();
		
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
	public void docDuLieuTuDatabaseVaoTable() {
		model.setRowCount(0);
		List<NhanVien> list = nhanVien_dao.getAllNhanVien();
		
		for(NhanVien nv : list) {
			model.addRow(new Object[] {
					nv.getMaNhanVien(),
					nv.getHoTen(),
					nv.getSoDienThoai(),
					nv.isGioiTinh() ? "Nam" : "Nữ",
					nv.getEmail(),
					nv.getChucVu(),
					nv.getDiaChi(),
					nv.getTrangThai()
			});
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int row = table.getSelectedRow();
		if(row == -1) return;

		txtMa.setText(model.getValueAt(row, 0).toString());
		txtTen.setText(model.getValueAt(row, 1).toString());
		txtSDT.setText(model.getValueAt(row, 2).toString());
		String gioiTinh = model.getValueAt(row, 3).toString();
		if(gioiTinh.equalsIgnoreCase("Nam")) {
		    rdNam.setSelected(true);
		} else {
		    rdNu.setSelected(true);
		}
		txtEmail.setText(model.getValueAt(row, 4).toString());
		String chucVu = model.getValueAt(row, 5).toString();
		cbChucVu.setSelectedItem(chucVu);
		txtDiaChi.setText(model.getValueAt(row, 6).toString());
		String trangThai = model.getValueAt(row, 7).toString();
	    if(trangThai.equalsIgnoreCase("Đang làm")) {
	        rdDangLam.setSelected(true);
	    } else {
	        rdNghi.setSelected(true);
	    }
	    txtMa.setEditable(false);
		
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	private boolean validData() {
	    String ma = txtMa.getText().trim();
	    String ten = txtTen.getText().trim();
	    String sdt = txtSDT.getText().trim();
	    String email = txtEmail.getText().trim();
	    String diaChi = txtDiaChi.getText().trim();

	    if (!(ma.length() > 0 && ma.matches("^TC\\d{4}$"))) {
	        showMessage("Mã NV phải có dạng TCxxxx (VD: TC0001)", txtMa);
	        return false;
	    }

	    if (!(ten.length() > 0 && ten.matches("^[A-ZÀ-Ỹ][A-ZÀ-Ỹa-zà-ỹ]*(\\s[A-ZÀ-Ỹ][A-ZÀ-Ỹa-zà-ỹ]*)*$"))) {
	    	showMessage("Tên phải viết hoa đầu mỗi từ, chỉ gồm chữ và khoảng trắng", txtTen);
	        return false;
	    }

	    if (!(sdt.length() > 0 && sdt.matches("^(03|05|07|08|09)\\d{8}$"))) {
	        showMessage("SĐT phải 10 số và bắt đầu bằng 03,05,07,08,09", txtSDT);
	        return false;
	    }

	    if (!(email.length() > 0 && email.matches("^[A-Za-z0-9][A-Za-z0-9._%+-]*@gmail\\.com$"))) {
	    	showMessage("Email phải đúng định dạng kết thúc bằng @gmail.com", txtEmail);
	        return false;
	    }

	    if (!(diaChi.length() > 0 && diaChi.matches("^[A-Za-zÀ-Ỹà-ỹ0-9\\s,./-]+$"))) {
	        showMessage("Địa chỉ không hợp lệ", txtDiaChi);
	        return false;
	    }

	    return true;
	}

	private void showMessage(String mess, JTextField txt) {
	    JOptionPane.showMessageDialog(this, mess);
	    txt.requestFocus();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    Object o = e.getSource();

	 // ===== THÊM =====
	 if (o == btnThem) {

	     String ma = txtMa.getText().trim();

	     if (nhanVien_dao.existsMaNV(ma)) {
	         JOptionPane.showMessageDialog(this, "Mã không được trùng");
	         txtMa.requestFocus();
	         return;
	     }
	     
	     if (!validData()) return;

	     try {
	         NhanVien nv = new NhanVien(
	                 ma,
	                 txtTen.getText().trim(),
	                 txtSDT.getText().trim(),
	                 rdNam.isSelected(),
	                 txtEmail.getText().trim(),
	                 cbChucVu.getSelectedItem().toString(),
	                 txtDiaChi.getText().trim(),
	                 rdDangLam.isSelected() ? "Đang làm" : "Nghỉ"
	         );

	         if (nhanVien_dao.themNhanVien(nv)) {
	             docDuLieuTuDatabaseVaoTable();
	             JOptionPane.showMessageDialog(this, "Thêm thành công");
	         } else {
	             JOptionPane.showMessageDialog(this, "Thêm thất bại");
	         }

	     } catch (Exception ex) {
	         ex.printStackTrace(); 
	         JOptionPane.showMessageDialog(this, "Lỗi dữ liệu!");
	     }
	 }

	    // ===== XÓA RỖNG =====
	    if (o == btnXoaRong) {
	        txtMa.setText("");
	        txtTen.setText("");
	        txtSDT.setText("");
	        txtEmail.setText("");
	        txtDiaChi.setText("");
	        cbChucVu.setSelectedIndex(0);

	        rdNam.setSelected(true);     
	        rdDangLam.setSelected(true);

	        txtMa.setEditable(true);
	        table.clearSelection();
	    }

	 // ===== XÓA NHIỀU DÒNG =====
	    if (o == btnXoa) {
	        int[] rows = table.getSelectedRows();

	        if (rows.length == 0) {
	            JOptionPane.showMessageDialog(this, "Chọn ít nhất 1 dòng cần xóa");
	            return;
	        }

	        int hoi = JOptionPane.showConfirmDialog(
	                this,
	                "Bạn có chắc muốn xóa " + rows.length + " nhân viên?",
	                "Xác nhận",
	                JOptionPane.YES_NO_OPTION
	        );

	        if (hoi != JOptionPane.YES_OPTION) return;

	        boolean ok = true;

	        for (int i = rows.length - 1; i >= 0; i--) {
	            String ma = model.getValueAt(rows[i], 0).toString();

	            if (!nhanVien_dao.xoaNhanVien(ma)) {
	                ok = false;
	            }
	        }

	        docDuLieuTuDatabaseVaoTable();
	        table.clearSelection();

	        if (ok) {
	            JOptionPane.showMessageDialog(this, "Xóa thành công");
	        } else {
	            JOptionPane.showMessageDialog(this, "Có vài dòng xóa thất bại");
	        }
	    }

	 // ===== TÌM =====
	    if (o == btnTim) {
	        String str = txtTim.getText().trim();

	        if (str.isEmpty()) { 
	            JOptionPane.showMessageDialog(this, "Nhập mã cần tìm");
	            txtTim.requestFocus();
	            return;
	        }

	        NhanVien nv = nhanVien_dao.timNhanVien(str);

	        if (nv != null) {
	            txtMa.setText(nv.getMaNhanVien());
	            txtTen.setText(nv.getHoTen());
	            txtSDT.setText(nv.getSoDienThoai());
	            txtEmail.setText(nv.getEmail());
	            txtDiaChi.setText(nv.getDiaChi());
	            cbChucVu.setSelectedItem(nv.getChucVu());

	            if (nv.isGioiTinh()) rdNam.setSelected(true);
	            else rdNu.setSelected(true);

	            if (nv.getTrangThai().equals("Đang làm")) rdDangLam.setSelected(true);
	            else rdNghi.setSelected(true);

	            txtMa.setEditable(false);

	            for (int i = 0; i < model.getRowCount(); i++) {
	                if (model.getValueAt(i, 0).toString().equals(str)) {
	                    table.setRowSelectionInterval(i, i);
	                    table.scrollRectToVisible(table.getCellRect(i, 0, true));
	                    break;
	                }
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "Không tìm thấy");
	        }
	    }

	 // ===== SỬA =====
	    if (o == btnSua) {
	        int r = table.getSelectedRow();
	        if (r == -1) {
	            JOptionPane.showMessageDialog(this, "Chọn dòng cần sửa");
	            return;
	        }

	        if (!validData()) return;

	        try {
	            NhanVien nv = new NhanVien(
	                    txtMa.getText().trim(),
	                    txtTen.getText().trim(),
	                    txtSDT.getText().trim(),
	                    rdNam.isSelected(),
	                    txtEmail.getText().trim(),
	                    cbChucVu.getSelectedItem().toString(),
	                    txtDiaChi.getText().trim(),
	                    rdDangLam.isSelected() ? "Đang làm" : "Nghỉ"
	            );

	            if (nhanVien_dao.suaNhanVien(nv)) {
	                docDuLieuTuDatabaseVaoTable();
	                JOptionPane.showMessageDialog(this, "Sửa thành công");
	            } else {
	                JOptionPane.showMessageDialog(this, "Không tìm thấy để sửa");
	            }

	        } catch (Exception ex) {
	            ex.printStackTrace(); 
	            JOptionPane.showMessageDialog(this, "Lỗi hệ thống!");
	        }
	    }
	 // ===== LÀM MỚI =====
	    if (o == btnLamMoi) {
	        docDuLieuTuDatabaseVaoTable();
	        
	        txtMa.setText("");
	        txtTen.setText("");
	        txtSDT.setText("");
	        txtEmail.setText("");
	        txtDiaChi.setText("");
	        txtTim.setText("");

	        cbChucVu.setSelectedIndex(0);

	        rdNam.setSelected(true);
	        rdDangLam.setSelected(true);

	        txtMa.setEditable(true);
	        table.clearSelection();
	        JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu");
	    }
	}
}
