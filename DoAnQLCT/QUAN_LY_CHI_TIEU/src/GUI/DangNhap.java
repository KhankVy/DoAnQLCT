package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BUS.UsersBus;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

public class DangNhap extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmail;
	private JPasswordField txtPass;
	//////////////////////////
	private UsersBus bus = new UsersBus();
	private static int currentUserID;
	private static int currentCargories;

	public static int getCurrentUserID() {
		return currentUserID;
	}

	public static void setCurrentUserID(int UserID) {
		currentUserID = UserID;
	}

	public static int getCurrentCargories() {
		return currentCargories;
	}

	public static void setCurrentCargories(int Cargories) {
		currentCargories = Cargories;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DangNhap frame = new DangNhap();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DangNhap() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 496, 327);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("ĐĂNG NHẬP");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel_2.setBounds(176, 28, 158, 29);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(27, 76, 82, 25);
		contentPane.add(lblNewLabel);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(137, 75, 248, 26);
		contentPane.add(txtEmail);

		JLabel lblMtKhu = new JLabel("Mật khẩu");
		lblMtKhu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMtKhu.setBounds(27, 120, 92, 25);
		contentPane.add(lblMtKhu);

		txtPass = new JPasswordField();
		txtPass.setBounds(137, 120, 248, 25);
		contentPane.add(txtPass);

		JButton btnDNhap = new JButton("Đăng Nhập");
		btnDNhap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = txtEmail.getText();
				char[] passwordChars = txtPass.getPassword();
				String password = new String(passwordChars);
				String hashedPassword = hashPassword(password);
				if (bus.authenticateUser(email, hashedPassword)) {
					// Cập nhật ngày cuối cùng đăng nhập
					bus.updateLastLoginDate(email);
					JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
					int userId = bus.getUserIDFromDatabase(email);
					Main.setCurrentUserID(userId);
					Main main = new Main();
					main.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null,
							"Đăng nhập thất bại! Vui lòng kiểm tra lại tên người dùng và mật khẩu.");
				}
			}
		});
		btnDNhap.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDNhap.setBounds(62, 193, 142, 49);
		contentPane.add(btnDNhap);

		JButton btnDKy = new JButton("Đăng Ký");
		btnDKy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DangKy fmdky = new DangKy();
				fmdky.setVisible(true);
				dispose();
			}
		});
		btnDKy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDKy.setBounds(260, 193, 125, 49);
		contentPane.add(btnDKy);
	}

	protected String hashPassword(String pass) {
		// TODO Auto-generated method stub
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hashInBytes = md.digest(pass.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : hashInBytes)
				sb.append(String.format("%02x", b));
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void loginUser(int userID) {
		setCurrentUserID(userID);
		// Proceed with other login operations
	}
}
