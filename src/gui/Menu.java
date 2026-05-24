package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Menu extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel pMenu, pView, pIntro;
    private JPanel activeItem = null;
    // ===== THEME CINEMA =====
    Color BG_MAIN = new Color(245, 245, 245);   // light gray (#F5F5F5)
    Color SIDEBAR = new Color(30, 30, 30);      // dark gray (#1E1E1E)

    Color BUTTON = new Color(61, 61, 61);       // gray (#3D3D3D)
    Color HOVER = new Color(90, 90, 90);        // gray hover (#5A5A5A)
    Color ACTIVE = new Color(255, 193, 7);      // yellow (#FFC107)

    Color TEXT = new Color(255, 213, 79);       // yellow text (#FFD54F)
    Color TEXT_ACTIVE = new Color(33, 33, 33);  // dark text (#212121)

    Color HEADER_BG = new Color(255, 224, 130); // light yellow (#FFE082)
	public Menu(String vaiTro) {
    	
    	setTitle("Quản Lý Rạp Phim");
        setSize(1200, 785);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        pIntro = new JPanel();
        pIntro.setBackground(HEADER_BG);
        pIntro.setLayout(new BoxLayout(pIntro, BoxLayout.Y_AXIS));

        pIntro.add(Box.createVerticalGlue()); 
	    
        // ===== SIDEBAR =====
        pMenu = new JPanel();
        pMenu.setPreferredSize(new Dimension(220, 700));
        pMenu.setBackground(SIDEBAR);
        pMenu.setLayout(new BoxLayout(pMenu, BoxLayout.Y_AXIS));
        pMenu.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // ===== HEADER LOGO =====
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(HEADER_BG);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        // ===== LOAD IMAGE =====
        ImageIcon icon = new ImageIcon("src/IMG/logo.png"); 

        Image img = icon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(img));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(lblLogo);

        pMenu.add(header);
        
        int gap = 20;

        pMenu.add(Box.createVerticalStrut(gap));

        JPanel btnPhim = createMenuItem("Phim");
        if(vaiTro.equalsIgnoreCase("nhanVien")) {
            btnPhim.setVisible(false);  
        }
        JPanel btnSuatChieu = createMenuItem("Suất chiếu");
        if(vaiTro.equalsIgnoreCase("nhanVien")) {
            btnSuatChieu.setVisible(false);  
        }
        
        JPanel btnVe = createMenuItem("Vé");
        
        JPanel btnDSVe = createMenuItem("Danh sách vé");
        if(vaiTro.equalsIgnoreCase("nhanVien")) {
            btnDSVe.setVisible(false);  
        }
        JPanel btnNhanVien = createMenuItem("Nhân viên");
        if(vaiTro.equalsIgnoreCase("nhanVien")) {
            btnNhanVien.setVisible(false);  
        }
        
        JPanel btnThongKe = createMenuItem("Thống kê");
        if(vaiTro.equalsIgnoreCase("nhanVien")) {
            btnThongKe.setVisible(false);  
        }

        pMenu.add(btnPhim);
        pMenu.add(Box.createVerticalStrut(gap));
        pMenu.add(btnSuatChieu);
        pMenu.add(Box.createVerticalStrut(gap));
        pMenu.add(btnVe);
        pMenu.add(Box.createVerticalStrut(gap));
        pMenu.add(btnDSVe);
        pMenu.add(Box.createVerticalStrut(gap));
        pMenu.add(btnNhanVien);
        pMenu.add(Box.createVerticalStrut(gap));
        pMenu.add(btnThongKe);

        // ===== SIGN OUT =====
        pMenu.add(Box.createVerticalGlue());

        JPanel btnSignOut = createSignOutButton();
        JPanel signOutWrapper = new JPanel(new BorderLayout());
        signOutWrapper.setBackground(SIDEBAR);
        signOutWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        signOutWrapper.setBorder(BorderFactory.createEmptyBorder(0, 12, 20, 12));
        signOutWrapper.add(btnSignOut, BorderLayout.CENTER);
        pMenu.add(signOutWrapper);

        // ===== VIEW =====
        pView = new JPanel(new BorderLayout());
        pView.setBackground(BG_MAIN);
        
        add(pMenu, BorderLayout.WEST);
        add(pView, BorderLayout.CENTER);
        
        setPanel(new FormVe());
        setActive(btnVe);

        // ===== EVENTS =====
        
        addMenuEvent(btnPhim, () -> {
             setPanel(new FormPhim());
        });

        addMenuEvent(btnSuatChieu, () -> {
            setPanel(new FormSuatChieu());
        });

        addMenuEvent(btnVe, () -> {
            setPanel(new FormVe());
        });

        addMenuEvent(btnDSVe, () -> {
            try {
				setPanel(new FormDanhSachVe());
			} catch (SQLException e) {
				e.printStackTrace();
			}
        });

        addMenuEvent(btnNhanVien, () -> {
            setPanel(new FormNhanVien());
        });
        
        addMenuEvent(btnThongKe, () -> {
            setPanel(new FormThongKe());
        });

    }

    // ===== EVENT MENU =====
    private void addMenuEvent(JPanel btn, Runnable action) {
        btn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setActive(btn);
                action.run();
            }
        });
    }

    // ===== MENU ITEM =====
    private JPanel createMenuItem(String text) {
        JPanel panel = new JPanel(new BorderLayout());

        panel.setPreferredSize(new Dimension(200, 70));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        panel.setBackground(BUTTON);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setForeground(TEXT);
        lbl.setFont(new Font("Arial", Font.BOLD, 15));

        panel.add(lbl, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (panel != activeItem)
                    panel.setBackground(HOVER);
            }

            public void mouseExited(MouseEvent e) {
                if (panel != activeItem)
                    panel.setBackground(BUTTON);
            }
        });

        return panel;
    }

    // ===== ACTIVE =====
    private void setActive(JPanel panel) {
        if (activeItem != null) {
            activeItem.setBackground(BUTTON);
            JLabel lblOld = (JLabel) activeItem.getComponent(0);
            lblOld.setForeground(TEXT);
        }

        panel.setBackground(ACTIVE);
        JLabel lbl = (JLabel) panel.getComponent(0);
        lbl.setForeground(TEXT_ACTIVE);

        activeItem = panel;
    }

    // ===== CHUYỂN PANEL =====
    private void setPanel(Component comp) {
        pView.removeAll();
        pView.add(comp, BorderLayout.CENTER);
        pView.revalidate();
        pView.repaint();
    }

    private JPanel createSignOutButton() {
        Color BTN_BG     = new Color(255, 193, 7, 30);
        Color BTN_BORDER = new Color(255, 193, 7, 80);
        Color BTN_TEXT   = ACTIVE;
        int ARC          = 20; 

        JPanel panel = new JPanel(new BorderLayout(8, 0)) {
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                                    java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BTN_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);
                g2.setColor(BTN_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC, ARC);
                g2.dispose();
            }
        };

        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        JLabel icon = new JLabel("→", SwingConstants.LEFT);
        icon.setForeground(BTN_TEXT);
        icon.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lbl = new JLabel("Đăng xuất", SwingConstants.LEFT);
        lbl.setForeground(BTN_TEXT);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(icon, BorderLayout.WEST);
        panel.add(lbl, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = javax.swing.JOptionPane.showConfirmDialog(
                		Menu.this,
                        "Bạn có chắc muốn đăng xuất?",
                        "Xác nhận",
                        javax.swing.JOptionPane.YES_NO_OPTION
                );

                if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                    dispose();
                    new SignIn().setVisible(true); // mở form đăng nhập
                }
            }
        });
        
        return panel;
    }
}
