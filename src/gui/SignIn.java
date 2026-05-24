package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.TaiKhoanDAO;

public class SignIn extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
		JTextField txtUser;
		JPasswordField txtPass;
		JButton btnLogin;
		private TaiKhoanDAO tk_dao;
		public SignIn() {
			
		    setTitle("Thinking Cinema - Nhóm 7");
		    setSize(1200, 750);
		    setDefaultCloseOperation(EXIT_ON_CLOSE);
		    setLocationRelativeTo(null);

		    JPanel background = new JPanel() {

				private static final long serialVersionUID = 1L;
				private Image bgImage = new ImageIcon(
		                getClass().getResource("/IMG/background.jpg")
		        ).getImage();

		        @Override
		        protected void paintComponent(Graphics g) {
		            super.paintComponent(g);
		            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
		        }
		    };

		    background.setLayout(new GridBagLayout());
		    setContentPane(background);
		    
		    JPanel pnInput = new JPanel();
		    pnInput.setLayout(new BorderLayout(0, 0)); 
		    pnInput.setPreferredSize(new Dimension(1150, 555));


		    JPanel pnLeft = new JPanel();
		    pnLeft.setOpaque(true);
		
		    pnLeft.setBackground(new Color(255, 255, 204));
		    pnLeft.setPreferredSize(new Dimension(500, 0));
		    pnLeft.setLayout(new GridBagLayout());

		    GridBagConstraints gbcLeft = new GridBagConstraints();
		    gbcLeft.gridx = 0;
		    gbcLeft.insets = new Insets(5, 10, 5, 10);
		    gbcLeft.anchor = GridBagConstraints.CENTER;

		    ImageIcon logoIcon = new ImageIcon(
		            getClass().getResource("/IMG/logo.png"));
		    Image img = logoIcon.getImage()
		            .getScaledInstance(400, 300, Image.SCALE_SMOOTH);
		    JLabel lblLogo = new JLabel(new ImageIcon(img));

		    gbcLeft.gridy = 0;
		    pnLeft.add(lblLogo, gbcLeft);

		    JLabel lblCopyRight = new JLabel(
		            "Copyright © Thinking Cinema - Nhóm 7 ");
		    lblCopyRight.setFont(new Font("Leelawadee UI", Font.ITALIC, 11));
		    lblCopyRight.setForeground(Color.BLACK);

		    gbcLeft.gridy = 1;
		    pnLeft.add(lblCopyRight, gbcLeft);
		    

		    JPanel pnRight = new JPanel(new GridBagLayout());
		    pnRight.setOpaque(true);
		    pnRight.setBackground(Color.WHITE);
		    pnRight.setPreferredSize(new Dimension(420, 420)); 
		    GridBagConstraints gbc = new GridBagConstraints();
		    gbc.fill = GridBagConstraints.HORIZONTAL;
		    gbc.weightx = 1;
		    gbc.gridx = 0;

		    //Title
		    gbc.gridy = 0;
		    gbc.insets = new Insets(10, 10, 10, 10);
		    gbc.anchor = GridBagConstraints.CENTER;

		    JLabel lblTitle = new JLabel("Sign In");
		    lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
		    lblTitle.setForeground(new Color(255, 140, 0));
		    lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

		    pnRight.add(lblTitle, gbc);

		    //UserName
		    gbc.gridy = 1;
		    gbc.insets = new Insets(5, 30, 5, 30);
		    gbc.anchor = GridBagConstraints.WEST;

		    JLabel lblUser = new JLabel("UserName:");
		    lblUser.setFont(new Font("Segoe UI", Font.BOLD, 16));
		    pnRight.add(lblUser, gbc);

		    gbc.gridy = 2;
		    gbc.insets = new Insets(0, 30, 15, 30);
		    txtUser = new JTextField();
		    txtUser.setPreferredSize(new Dimension(300, 40)); 
		    pnRight.add(txtUser, gbc);

		    //Pass
		    gbc.gridy = 3;
		    gbc.insets = new Insets(5, 30, 5, 30);
		    JLabel lblPass = new JLabel("Password:");
		    lblPass.setFont(new Font("Segoe UI", Font.BOLD, 16));
		    pnRight.add(lblPass, gbc);

		    gbc.gridy = 4;
		    gbc.insets = new Insets(0, 30, 20, 30);
		    txtPass = new JPasswordField();
		    txtPass.setPreferredSize(new Dimension(300, 40)); 
		    pnRight.add(txtPass, gbc);

		    //Button
		    gbc.gridy = 5;
		    gbc.insets = new Insets(10, 30, 10, 30);
		    gbc.anchor = GridBagConstraints.CENTER;
		    gbc.fill = GridBagConstraints.NONE;

		    btnLogin = new JButton("Login");
		    btnLogin.setPreferredSize(new Dimension(160, 45));
		    btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
		    btnLogin.setBackground(new Color(255, 153, 0));
		    btnLogin.setForeground(Color.WHITE);
		    pnRight.add(btnLogin, gbc);

		    //Quên
		    gbc.gridy = 6;
		    gbc.insets = new Insets(2, 10, 2, 10);
		    gbc.anchor = GridBagConstraints.CENTER;

		    JLabel lblForgot = new JLabel("Quên mật khẩu?");
		    lblForgot.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		    lblForgot.setForeground(new Color(0, 102, 204));
		    pnRight.add(lblForgot, gbc);

		    //Tạo
		    gbc.gridy = 7;
		    JLabel lblRegister = new JLabel("Tạo tài khoản mới");
		    lblRegister.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		    lblRegister.setForeground(new Color(0, 102, 204));
		    pnRight.add(lblRegister, gbc);
		    
		    pnInput.add(pnLeft, BorderLayout.WEST);
		    pnInput.add(pnRight, BorderLayout.CENTER);

		    background.add(pnInput);

		    btnLogin.addActionListener(this);
		    tk_dao = new TaiKhoanDAO();
	}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == btnLogin) {
				String username = txtUser.getText().trim();
		        String password = new String(txtPass.getPassword()).trim();
		        String vaiTro = tk_dao.checkLogin(username, password);
		        if(vaiTro != null) {
		        	new Menu(vaiTro).setVisible(true);
		            dispose();
		        }
		        else {
		            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
		            txtUser.setText("");
		            txtPass.setText("");
		            txtUser.setEditable(true);
		        }
			}
		}
}

