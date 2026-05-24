package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import connectDB.ConnectDB;

public class main extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel pIntro;

    // ===== THEME CINEMA =====
    Color BG_MAIN = new Color(245, 245, 245);   // light gray (#F5F5F5)
    Color SIDEBAR = new Color(30, 30, 30);      // dark gray (#1E1E1E)

    Color BUTTON = new Color(61, 61, 61);       // gray (#3D3D3D)
    Color HOVER = new Color(90, 90, 90);        // gray hover (#5A5A5A)
    Color ACTIVE = new Color(255, 193, 7);      // yellow (#FFC107)

    Color TEXT = new Color(255, 213, 79);       // yellow text (#FFD54F)
    Color TEXT_ACTIVE = new Color(33, 33, 33);  // dark text (#212121)

    Color HEADER_BG = new Color(255, 224, 130); // light yellow (#FFE082)

    public main() throws SQLException {
    	ConnectDB.getConnection();
		System.out.println("Connected !!!");
        setTitle("Thinking Cinema");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        pIntro = new JPanel();
        pIntro.setBackground(HEADER_BG);
        pIntro.setLayout(new BoxLayout(pIntro, BoxLayout.Y_AXIS));

        pIntro.add(Box.createVerticalGlue()); 

        // ===== LOGO =====
        ImageIcon logo = new ImageIcon("src/IMG/logo.png");
        Image logoimg = logo.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel lblLogoimg = new JLabel(new ImageIcon(logoimg));
        lblLogoimg.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== TEXT =====
        JLabel lblCopyRight = new JLabel("Welcome Thinking Cinema - Nhóm 7");
        lblCopyRight.setFont(new Font("Leelawadee UI", Font.ITALIC, 20));
        lblCopyRight.setForeground(Color.BLACK);
        lblCopyRight.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== BUTTON =====
        JButton btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setBackground(BUTTON);
        btnDangNhap.setForeground(TEXT);
        btnDangNhap.setFont(new Font("Arial", Font.BOLD, 30));
        btnDangNhap.setMaximumSize(new Dimension(300, 150));
        btnDangNhap.setPreferredSize(new Dimension(200,80));
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ===== ADD COMPONENT =====
        pIntro.add(lblLogoimg);
        pIntro.add(Box.createVerticalStrut(20));
        pIntro.add(lblCopyRight);
        pIntro.add(Box.createVerticalStrut(30));
        pIntro.add(btnDangNhap);

        pIntro.add(Box.createVerticalGlue());
        
        add(pIntro, BorderLayout.CENTER);

        // ===== EVENTS =====
        btnDangNhap.addActionListener(e -> {
            new SignIn().setVisible(true);
            dispose(); 
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            ConnectDB.getInstance().connect();

            try {
                new main().setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}